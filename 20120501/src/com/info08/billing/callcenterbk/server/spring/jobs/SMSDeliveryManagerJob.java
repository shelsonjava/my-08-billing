package com.info08.billing.callcenterbk.server.spring.jobs;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.TreeMap;

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
import com.info08.billing.callcenterbk.shared.entity.TmpLogSMS;
import com.info08.billing.callcenterbk.shared.entity.admin.GSMIndexes;
import com.isomorphic.jpa.EMF;

public class SMSDeliveryManagerJob extends TimerTask {

	private Logger logger = Logger.getLogger(SMSDeliveryManagerJob.class
			.getName());
	private SMSSenderMagti magtiListener;
	private String url = "tcp://localhost:61617";
	private Connection connection;
	private Session session;
	private MessageProducer publisher;
	private Topic topic;
	private TreeMap<String, GSMIndexes> prefs;

	public void run() {
		try {
			if (magtiListener == null) {
				magtiListener = new SMSSenderMagti();
				magtiListener.run();
				logger.info("Creating New Message Listener Instance For Magti ");
			}
			sendSMS();
		} catch (Exception e) {
			logger.error("Error while Initialize Sms Sender Job : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void initMobOperPrefixes(EntityManager oracleManager) {
		try {
			if (prefs == null) {
				prefs = new TreeMap<String, GSMIndexes>();
			}
			prefs.clear();
			oracleManager = EMF.getEntityManager();
			ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
					.createNamedQuery("MobileOperatorPrefixe.getAll")
					.getResultList();
			if (list != null && !list.isEmpty()) {
				for (GSMIndexes fix : list) {
					prefs.put(fix.getPrefix(), fix);
				}
			}
		} catch (Exception e) {
			logger.error("Error while loading Mobile Operator Prefixes : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void sendSMS() {
		EntityManager oracleManager = null;
		Object transaction = null;
		StringBuilder log = new StringBuilder();
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			if (oracleManager == null) {
				log.append("SMS Batch List Error. Couldn't Initialiaze Database.");
				logger.info(log.toString());
				return;
			}
			if (transaction == null) {
				log.append("SMS Batch List Error. Couldn't Initialiaze Database.");
				logger.info(log.toString());
				return;
			}
			initMobOperPrefixes(oracleManager);

			ArrayList<TmpLogSMS> resultList = (ArrayList<TmpLogSMS>) oracleManager
					.createNamedQuery("TmpLogSMS.getForSending")
					.getResultList();
			if (resultList == null || resultList.isEmpty()) {
				//log.append("SMS Batch List Is Empty.");
				//logger.info(log.toString());
				return;
			}

			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("mgtsmstopic.messages");
			publisher = session.createProducer(topic);
			publisher.setDeliveryMode(DeliveryMode.PERSISTENT);

			for (TmpLogSMS logSMS : resultList) {
				String phone = logSMS.getPhone();
				if (phone == null || phone.trim().length() != 9) {
					logSMS.setStatus(-10000100L);
					oracleManager.merge(logSMS);
					continue;
				}
				String prefix = phone.substring(0, 3);
				GSMIndexes prefixe = prefs.get(prefix);
				if (prefixe == null) {
					logSMS.setStatus(-10000101L);
					oracleManager.merge(logSMS);
					continue;
				}
				logSMS.setOperator(prefixe.getOper());
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(logSMS);
				publisher.send(objectMessage);
			}

			connection.stop();
			connection.close();
			EMF.commitTransaction(transaction);
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