package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.Service;
import com.info08.billing.callcenterbk.shared.entity.session.CallSession;
import com.info08.billing.callcenterbk.shared.entity.session.CallSessionExpense;
import com.info08.billing.callcenterbk.shared.items.CallSessionItem;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class SessQualityDMI implements QueryConstants {

	Logger logger = Logger.getLogger(SessQualityDMI.class.getName());

	/**
	 * Adding Virtual charges (Making new virtual call)
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public DSResponse addChargesWithoutCall(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addChargesWithoutCall.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = dsRequest.getValues().get("loggedUserName")
					.toString();
			String call_phone = dsRequest.getValues().get("call_phone")
					.toString();
			Long service_id = new Long(dsRequest.getValues().get("service_id")
					.toString());
			Long chargeCount = new Long(dsRequest.getValues()
					.get("chargeCount").toString());
			Long virt_call_type = new Long(dsRequest.getValues()
					.get("virt_call_type").toString());
			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
			Long ym = new Long(dateFormat.format(currDate));

			Service service = oracleManager.find(Service.class, service_id);

			CallSession logSession = new CallSession();
			String session_id = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_VIRTUAL_SESSION_ID)
					.getSingleResult().toString();
			logSession.setSession_id(session_id);
			logSession.setCall_kind(virt_call_type);
			logSession.setCall_duration(0L);
			logSession.setCall_end_date(currDate);
			logSession.setReject_type(0L);
			logSession.setSwitch_ower_type(0L);
			logSession.setCall_phone(call_phone);
			logSession.setCall_quality(0L);
			logSession.setCall_start_date(currDate);
			logSession.setUname(loggedUserName);
			logSession.setYear_month(ym);

			oracleManager.persist(logSession);

			for (int i = 0; i < chargeCount.intValue(); i++) {
				CallSessionExpense item = new CallSessionExpense();
				item.setService_id(service_id);
				item.setSession_id(session_id);
				item.setYear_month(ym);
				item.setCharge_date(currDate);
				item.setCharge(service.getPrice());
				oracleManager.persist(item);
			}

			EMF.commitTransaction(transaction);

			DSResponse resp = new DSResponse();
			resp.setData(logSession);
			resp.setStatus(DSResponse.STATUS_SUCCESS);

			log += ". Adding Virtual Charges Finished SuccessFully. ";
			logger.info(log);
			return resp;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Adding Virtual Charges Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public DSResponse update(DSRequest req) throws Exception {
		PreparedStatement updateStmt = null;
		Connection connection = null;
		try {
			Object sessQuality = req.getValues().get("call_quality");
			Object call_session_id = req.getValues().get("call_session_id");

			String log = "Method:SessQualityDMI.update. Params : 1. SessionId = "
					+ call_session_id + " 2. sessQuality= " + sessQuality;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);
			updateStmt = connection.prepareStatement(Q_UPDATE_SESSION_QUALITY);

			updateStmt.setInt(1, Integer.parseInt(sessQuality.toString()));
			updateStmt.setString(2, call_session_id.toString());

			updateStmt.executeUpdate();
			updateStmt.close();
			CallSessionItem sessionItem = getLogSessionItem(new Long(
					call_session_id.toString()));
			DSResponse resp = new DSResponse();
			resp.setData(sessionItem);
			resp.setStatus(DSResponse.STATUS_SUCCESS);

			log += ". Log Session Update Finished SuccessFully";
			logger.info(log);
			connection.commit();
			return resp;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex1: " + e.toString());
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Personal Note Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (updateStmt != null) {
					updateStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	private CallSessionItem getLogSessionItem(Long call_session_id)
			throws CallCenterException {
		PreparedStatement selectNote = null;
		try {
			String log = "Method:SessQualityDMI.getLogSessionItem. Params : 1. call_session_id = "
					+ call_session_id;
			log += ". Result : getLogSessionItem Finished Successfully.";
			CallSessionItem existingRecord = new CallSessionItem();
			DMIUtils.findRecordById("CallSessDS", "customSearch",
					call_session_id, "call_session_id", existingRecord);
			logger.info(log);
			return existingRecord;
		} catch (Exception e) {
			logger.error("Error While Retrieving : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			try {
				if (selectNote != null) {
					selectNote.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
