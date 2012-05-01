package com.info08.billing.callcenterbk.server.spring.jobs;

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

import com.info08.billing.callcenterbk.server.jms.SMSDelRepMagti;
import com.info08.billing.callcenterbk.shared.entity.LogSMS;
import com.isomorphic.jpa.EMF;

public class SMSDeliveryRepJob extends TimerTask {

	private Logger logger = Logger.getLogger(SMSDeliveryRepJob.class.getName());
	private SMSDelRepMagti smsDelRepMagti;
	private String url = "tcp://localhost:61617";
	private Connection connection;
	private Session session;
	private MessageProducer publisher;
	private Topic topic;
	private ActiveMQConnectionFactory factory;

	public void run() {
		try {
			if (smsDelRepMagti == null) {
				smsDelRepMagti = new SMSDelRepMagti();
				smsDelRepMagti.run();
				logger.info("Creating New Message Listener Instance For Magti Delivery Report");
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
				topic = session.createTopic("mgtdelrepsmstopic.messages");
			}
			if (publisher == null) {
				publisher = session.createProducer(topic);
			}
			publisher.setDeliveryMode(DeliveryMode.PERSISTENT);
			sendSMS();
		} catch (Exception e) {
			logger.error("Error while Initialize Sms Delivery Report Job : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void sendSMS() {
		EntityManager oracleManager = null;
		StringBuilder log = new StringBuilder();
		try {
			oracleManager = EMF.getEntityManager();

			if (oracleManager == null) {
				log.append("SMS Batch List Error(Delivery Report). Couldn't Initialiaze Database.");
				logger.info(log.toString());
				return;
			}
			ArrayList<LogSMS> resultList = (ArrayList<LogSMS>) oracleManager
					.createNamedQuery("LogSMS.getDeliveryReport")
					.getResultList();
			if (resultList == null || resultList.isEmpty()) {
				log.append("SMS Batch Delivery Report List Is Empty.");
				logger.info(log.toString());
				return;
			}

			for (LogSMS logSMS : resultList) {
				String phone = logSMS.getPhone();
				if (phone == null || phone.trim().length() != 9) {
					logSMS.setStatus(-10000100L);
					oracleManager.merge(logSMS);
					continue;
				}
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(logSMS);
				publisher.send(objectMessage);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}