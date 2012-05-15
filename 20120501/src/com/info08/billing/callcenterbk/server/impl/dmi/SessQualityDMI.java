package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.session.LogSession;
import com.info08.billing.callcenterbk.shared.entity.session.LogSessionCharge;
import com.info08.billing.callcenterbk.shared.items.LogSessionItem;
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
			String phone = dsRequest.getValues().get("phone").toString();
			Long service_id = new Long(dsRequest.getValues().get("service_id")
					.toString());
			Long chargeCount = new Long(dsRequest.getValues()
					.get("chargeCount").toString());

			Long virt_call_type = new Long(dsRequest.getValues()
					.get("virt_call_type").toString());

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
			Long ym = new Long(dateFormat.format(currDate));

			LogSession logSession = new LogSession();
			String session_id = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_VIRTUAL_SESSION_ID)
					.getSingleResult().toString();
			logSession.setSession_id(session_id);
			logSession.setCall_type(virt_call_type);
			logSession.setDuration(0L);
			logSession.setEnd_date(currDate);
			logSession.setHungup(0L);
			logSession.setIs_new_bill(1L);
			logSession.setParent_id(0L);
			logSession.setPhone(phone);
			logSession.setSession_quality(0L);
			logSession.setStart_date(currDate);
			logSession.setUser_name(loggedUserName);
			logSession.setYm(ym);

			oracleManager.persist(logSession);

			for (int i = 0; i < chargeCount.intValue(); i++) {
				LogSessionCharge item = new LogSessionCharge();
				item.setId(new Long((System.currentTimeMillis() * (i + 1))));
				item.setService_id(service_id);
				item.setSession_id(session_id);
				item.setUpd_user(loggedUserName);
				item.setYm(ym);
				item.setDeleted(0L);
				item.setRec_date(currDate);
				item.setPrice(0L);
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
			Object sessQuality = req.getValues().get("session_quality");
			Object sessionId = req.getValues().get("sessionId");

			String log = "Method:SessQualityDMI.update. Params : 1. SessionId = "
					+ sessionId + " 2. sessQuality= " + sessQuality;

			DataSource ds = DataSourceManager.get("LogSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);
			updateStmt = connection.prepareStatement(Q_UPDATE_SESSION_QUALITY);

			updateStmt.setInt(1, Integer.parseInt(sessQuality.toString()));
			updateStmt.setString(2, sessionId.toString());

			updateStmt.executeUpdate();
			updateStmt.close();
			LogSessionItem sessionItem = getLogSessionItem(
					sessionId.toString(), connection);
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

	private LogSessionItem getLogSessionItem(String sessionId,
			Connection connection) throws CallCenterException {
		PreparedStatement selectNote = null;
		try {
			String log = "Method:SessQualityDMI.getLogSessionItem. Params : 1. sessionId = "
					+ sessionId;
			selectNote = connection
					.prepareStatement(QueryConstants.Q_GET_LOG_SESSION);
			selectNote.setString(1, sessionId);
			ResultSet resultSetNote = selectNote.executeQuery();
			if (!resultSetNote.next()) {
				log += ". Result : Invalid Log Session From Database: "
						+ sessionId;
				logger.info(log);
				throw new CallCenterException("შეცდომა ჩანაწერის წამოღებისას");
			}

			log += ". Result : getLogSessionItem Finished Successfully.";
			LogSessionItem existingRecord = new LogSessionItem();
			existingRecord.setUser_id(resultSetNote.getInt(1));
			existingRecord.setSession_id(resultSetNote.getString(2));
			existingRecord.setYm(resultSetNote.getInt(3));
			existingRecord.setUser_name(resultSetNote.getString(4));
			existingRecord.setStart_date(resultSetNote.getTimestamp(5));
			existingRecord.setPhone(resultSetNote.getString(6));
			existingRecord.setDuration(resultSetNote.getInt(7));
			existingRecord.setHangUp(resultSetNote.getString(8));
			existingRecord.setChargeCount(resultSetNote.getInt(9));
			existingRecord.setSession_quality(resultSetNote.getInt(10));
			existingRecord.setSession_quality_desc(resultSetNote.getString(11));
			existingRecord.setPerson_name(resultSetNote.getString(12));
			logger.info(log);
			return existingRecord;
		} catch (Exception e) {
			logger.error("Error While Retrieving LogPersNoteItem : ", e);
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
