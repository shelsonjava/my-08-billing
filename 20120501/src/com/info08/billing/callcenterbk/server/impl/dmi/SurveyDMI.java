package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.entity.session.LogSession;
import com.info08.billing.callcenterbk.shared.entity.session.LogSessionCharge;
import com.info08.billing.callcenterbk.shared.entity.survey.Survey;
import com.info08.billing.callcenterbk.shared.entity.survey.SurveyKind;
import com.info08.billing.callcenterbk.shared.entity.survey.SurveyReplyType;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class SurveyDMI implements QueryConstants {

	Logger logger = Logger.getLogger(SurveyDMI.class.getName());

	/**
	 * Adding New SurveyReplyType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public SurveyReplyType addSurveyReplyType(SurveyReplyType surveyReplyType)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addSurveyReplyType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = surveyReplyType.getLoggedUserName();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			oracleManager.persist(surveyReplyType);
			oracleManager.flush();

			surveyReplyType = oracleManager.find(SurveyReplyType.class,
					surveyReplyType.getSurvey_reply_type_id());
			surveyReplyType.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return surveyReplyType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert SurveyReplyType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating SurveyReplyType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public SurveyReplyType updateSurveyReplyType(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSurveyReplyType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long survey_reply_type_id = new Long(record.get(
					"survey_reply_type_id").toString());
			String survey_reply_type_name = record
					.get("survey_reply_type_name") == null ? null : record.get(
					"survey_reply_type_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			SurveyReplyType SurveyReplyType = oracleManager.find(
					SurveyReplyType.class, survey_reply_type_id);

			SurveyReplyType.setSurvey_reply_type_name(survey_reply_type_name);

			oracleManager.merge(SurveyReplyType);
			oracleManager.flush();

			SurveyReplyType = oracleManager.find(SurveyReplyType.class,
					survey_reply_type_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return SurveyReplyType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update SurveyReplyType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating SurveyReplyType Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public SurveyReplyType removeSurveyReplyType(Map record, DSRequest rec)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeSurveyReplyType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long survey_reply_type_id = new Long(record.get(
					"survey_reply_type_id").toString());
			String loggedUserName = rec.getOldValues().get("loggedUserName")
					.toString();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			SurveyReplyType surveyReplyType = oracleManager.find(
					SurveyReplyType.class, survey_reply_type_id);

			oracleManager.remove(surveyReplyType);
			oracleManager.flush();

			surveyReplyType = oracleManager.find(SurveyReplyType.class,
					survey_reply_type_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return surveyReplyType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for SurveyReplyType Into Database : ",
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
	 * Adding New SurveyKind
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public SurveyKind addSurveyKind(SurveyKind SurveyKind) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addSurveyKind.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = SurveyKind.getLoggedUserName();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			oracleManager.persist(SurveyKind);
			oracleManager.flush();

			SurveyKind = oracleManager.find(SurveyKind.class,
					SurveyKind.getSurvey_kind_id());
			SurveyKind.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return SurveyKind;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert SurveyKind Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating SurveyKind
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public SurveyKind updateSurveyKind(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSurveyKind.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long survey_kind_id = new Long(record.get("survey_kind_id")
					.toString());
			String survey_kind_name = record.get("survey_kind_name") == null ? null
					: record.get("survey_kind_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			SurveyKind SurveyKind = oracleManager.find(SurveyKind.class,
					survey_kind_id);

			SurveyKind.setSurvey_kind_name(survey_kind_name);

			oracleManager.merge(SurveyKind);
			oracleManager.flush();

			SurveyKind = oracleManager.find(SurveyKind.class, survey_kind_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return SurveyKind;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update SurveyKind Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating SurveyKind Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public SurveyKind removeSurveyKind(Map record, DSRequest req)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeSurveyKind.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long survey_kind_id = new Long(req.getOldValues()
					.get("survey_kind_id").toString());
			String loggedUserName = req.getOldValues().get("loggedUserName")
					.toString();

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			SurveyKind surveyKind = oracleManager.find(SurveyKind.class,
					survey_kind_id);

			oracleManager.remove(surveyKind);
			oracleManager.flush();

			surveyKind = oracleManager.find(SurveyKind.class, survey_kind_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return surveyKind;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for SurveyKind Into Database : ",
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
	 * Taking Survey - Update Survey Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map takeSurvey(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.takeSurvey.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long survey_id = new Long(record.get("survey_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			ArrayList<Survey> surveyList = (ArrayList<Survey>) oracleManager
					.createNamedQuery("Survey.getLocked")
					.setParameter("survId", survey_id).getResultList();
			if (surveyList == null || surveyList.isEmpty()) {
				throw new CallCenterException(
						"გარკვევის ეს ჩანაწერი უკვე გადაწყვეტილია. გთხოვთ სცადოთ სხვა !");
			}
			Survey survey = surveyList.get(0);
			oracleManager.lock(survey, LockModeType.OPTIMISTIC);
			if (survey.getBblocked() != null
					&& survey.getBblocked().equals(1L)
					&& !loggedUserName.equals(survey.getLoked_user())) {
				throw new CallCenterException(
						"გარკვევის ეს ჩანაწერი უკვე აღებულია სხვის მიერ. გთხოვთ სცადოთ სხვა !");
			}
			survey.setBblocked(1L);
			survey.setLoked_user(loggedUserName);
			oracleManager.merge(survey);

			oracleManager.flush();
			EMF.commitTransaction(transaction);

			Map result = DMIUtils.findRecordById("SurveyDS",
					"searchAllSurvey", survey_id, "mysurvey_id");
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return result;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for Survey Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Adding charges From Survey
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public LogSessionCharge addChargesBySurvey(
			LogSessionCharge logSessionCharge) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addChargesBySurvey.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			for (int i = 0; i < logSessionCharge.getChargeCount(); i++) {
				LogSessionCharge item = new LogSessionCharge();
				item.setId(new Long((System.currentTimeMillis() * (i + 1))));
				item.setService_id(logSessionCharge.getService_id());
				item.setSession_id(logSessionCharge.getSession_id());
				item.setUpd_user(logSessionCharge.getUpd_user());
				item.setYm(logSessionCharge.getYm());
				item.setDeleted(0L);
				item.setRec_date(new Timestamp(System.currentTimeMillis()));
				oracleManager.persist(item);
			}

			EMF.commitTransaction(transaction);
			log += ". Adding Charges Finished SuccessFully. ";
			logger.info(log);
			return logSessionCharge;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Adding Charges Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Adding Virtual charges (Making new virtual call)
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public LogSession addChargesWithoutCall(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addChargesWithoutCall.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = dsRequest.getAttribute("loggedUserName")
					.toString();
			String phone = dsRequest.getAttribute("phone").toString();
			Long service_id = new Long(dsRequest.getAttribute("service_id")
					.toString());
			Long chargeCount = new Long(dsRequest.getAttribute("chargeCount")
					.toString());

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
			Long ym = new Long(dateFormat.format(currDate));

			LogSession logSession = new LogSession();
			String session_id = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_VIRTUAL_SESSION_ID)
					.getSingleResult().toString();
			logSession.setSession_id(session_id);
			logSession.setCall_type(new Long(Constants.callTypeVirtualDirect));
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
				oracleManager.persist(item);
			}

			EMF.commitTransaction(transaction);
			log += ". Adding Virtual Charges Finished SuccessFully. ";
			logger.info(log);
			return logSession;
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

	/**
	 * Updating Survey
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map updateSurveyItem(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSurveyItem.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Date recDate = new Date(System.currentTimeMillis());
			Long survey_id = new Long(record.get("survey_id").toString());
			Long survey_reply_type_id = new Long(record.get(
					"survey_reply_type_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			RCNGenerator.getInstance().initRcn(oracleManager,
					new Timestamp(recDate.getTime()), loggedUserName, log);
			Survey survey = oracleManager.find(Survey.class, survey_id);

			survey.setsurvey_reply_type_id(survey_reply_type_id);
			survey.setLoked_user(loggedUserName);

			survey.setBblocked(0L);
			survey.setSurvery_responce_status(1L);

			oracleManager.merge(survey);
			oracleManager.flush();
			EMF.commitTransaction(transaction);
			Map result = DMIUtils.findRecordById("SurveyDS",
					"searchAllSurvey", survey_id, "mysurvey_id");
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return result;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Survey Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
