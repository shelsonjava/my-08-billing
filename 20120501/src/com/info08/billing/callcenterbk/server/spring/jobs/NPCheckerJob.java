package com.info08.billing.callcenterbk.server.spring.jobs;

import java.net.InetAddress;
import java.util.List;
import java.util.TimerTask;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.jms.NPChecker;
import com.isomorphic.jpa.EMF;

public class NPCheckerJob extends TimerTask {

	private Logger logger = Logger.getLogger(NPCheckerJob.class.getName());
	private NPChecker npChecker;
	private String url = "tcp://localhost:61617";
	private Connection connection;
	private Session session;
	private MessageProducer publisher;
	private Topic topic;
	private ActiveMQConnectionFactory factory;

	public NPCheckerJob() {
		try {
			if (npChecker == null) {
				npChecker = new NPChecker();
				npChecker.run();
			}
			if (factory == null) {
				factory = new ActiveMQConnectionFactory(url);
			}
			if (connection == null) {
				connection = factory.createConnection();
			}
			if (session == null) {
				session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
			}
			if (topic == null) {
				topic = session.createTopic("mgtsmstopic.messages");
			}
			if (publisher == null) {
				publisher = session.createProducer(topic);
			}
			publisher.setDeliveryMode(DeliveryMode.PERSISTENT);
			logger.info("Number Portability Initialization Finished Successfully.");
		} catch (Exception e) {
			logger.error("Error while Initialize NP Checker : ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public void run() {

		EntityManager oracleManager = null;
		Object transaction = null;
		boolean flag = true;
		StringBuilder log = new StringBuilder();
		try {
			String inetAddress = InetAddress.getLocalHost().getHostAddress();
			if (flag || !inetAddress.contains("127.0.0.")) {
				log.append("Wrong ip address. Ignoring");
				logger.info(log.toString());
				return;
			}
			System.out.println("Check .... ");
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			List resultList = (List) oracleManager.createNativeQuery(
					QueryConstants.Q_GET_PORT_CHECK_PHONES).getResultList();
			if (resultList == null || resultList.isEmpty()) {
				return;
			}
			for (Object object : resultList) {
				Object row[] = (Object[]) object;
				if (row == null || row[0] == null || row[1] == null) {
					continue;
				}
				Long call_session_id = new Long(row[0].toString());
				String phone = row[1].toString();
				System.out.println("phone = " + phone);
				oracleManager
						.createNativeQuery(QueryConstants.Q_GET_UPDATE_SESSION)
						.setParameter(1, 1212).setParameter(2, call_session_id)
						.executeUpdate();
			}
			// logger.info(log.toString());

			// if (inetAddress.contains("192.168.1.7")) {
			// return;
			// }
			// oracleManager = EMF.getEntityManager();
			// if (oracleManager == null) {
			// log.append("SMS Batch List Error. Couldn't Initialiaze Database.");
			// logger.info(log.toString());
			// return;
			// }
			//
			// ArrayList<SentSMSHist> resultList = (ArrayList<SentSMSHist>)
			// oracleManager
			// .createNamedQuery("SentSMSHist.getForSending")
			// .getResultList();
			// if (resultList == null || resultList.isEmpty()) {
			// //log.append("SMS Batch List Is Empty.");
			// //logger.info(log.toString());
			// return;
			// }
			//
			// transaction = EMF.getTransaction(oracleManager);
			//
			// for (SentSMSHist logSMS : resultList) {
			// String phone = logSMS.getReciever_number();
			// if (phone == null || phone.trim().length() != 9) {
			// logSMS.setHist_status_id(-1L);
			// oracleManager.merge(logSMS);
			// continue;
			// }
			// logSMS.setHist_status_id(-2L);
			// oracleManager.merge(logSMS);
			//
			// ObjectMessage objectMessage = session.createObjectMessage();
			// objectMessage.setObject(logSMS);
			// publisher.send(objectMessage);
			// }

			EMF.commitTransaction(transaction);
		} catch (Exception e) {
			logger.error(e.toString());
			EMF.rollbackTransaction(transaction);
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}

	}
}