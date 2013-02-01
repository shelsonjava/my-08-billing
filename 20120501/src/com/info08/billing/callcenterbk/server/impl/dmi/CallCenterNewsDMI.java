package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.CallCenterNews;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;

public class CallCenterNewsDMI implements QueryConstants {

	Logger logger = Logger.getLogger(CallCenterNewsDMI.class.getName());

	/**
	 * Adding New CallCenterNews
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public DSResponse addCallCenterNews(CallCenterNews callCenterNews)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addCallCenterNews.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = callCenterNews.getLoggedUserName();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			callCenterNews.setCall_center_news_date(new Timestamp(System
					.currentTimeMillis()));
			oracleManager.persist(callCenterNews);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			Map<?, ?> values = DMIUtils.findRecordById("CallCenterNewsDS",
					"searchAllCallCenterNews",
					callCenterNews.getCall_center_news_id(),
					"call_center_news_id");

			DSResponse dsResponse = new DSResponse();
			dsResponse.setData(values);

			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return dsResponse;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert CallCenterNews Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating CallCenterNews
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public DSResponse updateCallCenterNews(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCallCenterNews.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> record = dsRequest.getValues();

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long call_center_news_id = new Long(record.get(
					"call_center_news_id").toString());
			String call_center_news_text = record.get("call_center_news_text") == null ? null
					: record.get("call_center_news_text").toString();
			Long call_center_warning = new Long(record.get(
					"call_center_warning").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			CallCenterNews callCenterNews = oracleManager.find(
					CallCenterNews.class, call_center_news_id);

			callCenterNews.setCall_center_news_text(call_center_news_text);
			callCenterNews.setCall_center_warning(call_center_warning);
			callCenterNews.setCall_center_news_date(new Timestamp(System
					.currentTimeMillis()));

			oracleManager.merge(callCenterNews);
			oracleManager.flush();
			EMF.commitTransaction(transaction);

			Map<?, ?> values = DMIUtils.findRecordById("CallCenterNewsDS",
					"searchAllCallCenterNews", call_center_news_id,
					"call_center_news_id");

			DSResponse dsResponse = new DSResponse();
			dsResponse.setData(values);

			log += ". Updating Finished SuccessFully. ";
			logger.info(log);

			return dsResponse;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update CallCenterNews Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Delete CallCenterNews
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CallCenterNews removeCallCenterNews(Map record, DSRequest rec)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeCallCenterNews.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long call_center_news_id = new Long(record.get(
					"call_center_news_id").toString());
			String loggedUserName = rec.getOldValues().get("loggedUserName")
					.toString();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			CallCenterNews callCenterNews = oracleManager.find(
					CallCenterNews.class, call_center_news_id);

			oracleManager.remove(callCenterNews);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete CallCenterNews From Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
