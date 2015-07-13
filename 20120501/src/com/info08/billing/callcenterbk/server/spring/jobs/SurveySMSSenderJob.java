package com.info08.billing.callcenterbk.server.spring.jobs;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.impl.dmi.DMIUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.entity.survey.SurveyHistSMS;
import com.isomorphic.jpa.EMF;

public class SurveySMSSenderJob extends TimerTask {

	Logger logger = Logger.getLogger(SurveySMSSenderJob.class.getName());

	public void run() {
		sendSurveySMS();
	}

	public void sendSurveySMS() {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String inetAddress = InetAddress.getLocalHost().getHostAddress();
			//logger.info("inedAddress = "+inetAddress);
			if (!inetAddress.contains("127.0.0") && !inetAddress.contains("192.168.1.3")) {
				String log = "Job. Sending Auto Survey SMS.\n";
				oracleManager = EMF.getEntityManager();
				transaction = EMF.getTransaction(oracleManager);
				List<Map<?, ?>> listData = DMIUtils.findRecordsByCriteria(
						"SurveyDS", "getAllSurveyForSMS", new TreeMap<String, String>());
				if (listData != null && !listData.isEmpty()) {
					log += "=========== SMS List ===========\n";
					for (Map<?, ?> map : listData) {
						
						Long survey_id = DMIUtils.getRowValueLong(map.get("survey_id"));
						String sessionId = DMIUtils.getRowValueSt(map.get("session_call_id"));
						String survey_phone = DMIUtils.getRowValueSt(map.get("survey_phone"));
						if (survey_id == null || survey_phone == null) {
							continue;
						}
						Timestamp rec_date = new Timestamp(
								System.currentTimeMillis());
						SurveyHistSMS surveySmsHist = new SurveyHistSMS();
						surveySmsHist.setSurvey_id(survey_id);
						surveySmsHist.setHist_datetime(rec_date);
						oracleManager
								.createNativeQuery(
										"{call send_sms_to_reciptent(?,?,?,?)}")
								.setParameter(1, Constants.discSMSDefText)
								.setParameter(2, survey_phone)
								.setParameter(3, sessionId).setParameter(4, 0)
								.executeUpdate();
						log += "Phone = " + survey_phone + ", SessionId = "
								+ sessionId + ", Rec. Date = "
								+ rec_date.toString() + "\n";
						oracleManager.persist(surveySmsHist);
					}
					log += "=========== SMS List ===========";
				} else {
					log += "SMS List Is Empty.";
				}
				EMF.commitTransaction(transaction);
				logger.info(log);
			}
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			logger.error(e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}



