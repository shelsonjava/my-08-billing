package com.info08.billing.callcenterbk.server.spring.jobs;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.jms.SMSSenderMagti;
import com.info08.billing.callcenterbk.shared.entity.SentSMSHist;
import com.isomorphic.jpa.EMF;

public class SMSSenderJob extends TimerTask {

	private Logger logger = Logger.getLogger(SMSSenderJob.class.getName());
	private SMSSenderMagti magtiListener;
	private String url = "tcp://localhost:61617";
	private Connection connection;
	private Session session;
	private MessageProducer publisher;
	private Topic topic;
	private ActiveMQConnectionFactory factory;

	public void run() {
		try {
			if (magtiListener == null) {
				magtiListener = new SMSSenderMagti();
				magtiListener.run();
				logger.info("Creating New Message Listener Instance For Magti ");
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
			sendSMS();
		} catch (Exception e) {
			logger.error("Error while Initialize Sms Sender Job : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void sendSMS() {
		EntityManager oracleManager = null;
		Object transaction = null;
		StringBuilder log = new StringBuilder();
		try {
			if (InetAddress.getLocalHost().getHostAddress()
					.contains("127.0.0.")) {
				return;
			}
			oracleManager = EMF.getEntityManager();
			if (oracleManager == null) {
				log.append("SMS Batch List Error. Couldn't Initialiaze Database.");
				logger.info(log.toString());
				return;
			}

			ArrayList<SentSMSHist> resultList = (ArrayList<SentSMSHist>) oracleManager
					.createNamedQuery("SentSMSHist.getForSending")
					.getResultList();
			if (resultList == null || resultList.isEmpty()) {
				//log.append("SMS Batch List Is Empty.");
				//logger.info(log.toString());
				return;
			}
			
			transaction = EMF.getTransaction(oracleManager);

			for (SentSMSHist logSMS : resultList) {
				String phone = logSMS.getReciever_number();
				if (phone == null || phone.trim().length() != 9) {
					logSMS.setHist_status_id(-1L);
					oracleManager.merge(logSMS);
					continue;
				}
				logSMS.setHist_status_id(-2L);
				oracleManager.merge(logSMS);

				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(logSMS);
				publisher.send(objectMessage);
			}
			
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