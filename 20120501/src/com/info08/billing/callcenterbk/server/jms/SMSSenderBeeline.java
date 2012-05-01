package com.info08.billing.callcenterbk.server.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.smpp.SMSCManager;
import com.info08.billing.callcenterbk.shared.entity.TmpLogSMS;

public class SMSSenderBeeline implements MessageListener {

	private Logger logger = Logger.getLogger(SMSSenderBeeline.class.getName());

	private Connection connection;
	protected MessageProducer producer;
	private Session session;
	private int count;
	protected long start;
	private Topic topic;
	private String url = "tcp://localhost:61617";
	private SMSCManager beelineSMSCManager;
	private String serverIPAddress = "91.184.98.58";
	private int serverPort = 3333;
	private String systemId = "139";
	private String password = "v5k0hW0Z";
	private int reconnectInterval = 5000;
	private String sourceAddr = "16008";

	public void run() throws JMSException {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("beelinesmstopic.messages");
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);

			connection.start();

			producer = session.createProducer(topic);

			beelineSMSCManager = new SMSCManager(serverIPAddress, serverPort,
					systemId, password, reconnectInterval, sourceAddr, null,
					null);
			beelineSMSCManager.connectAndBind();

			System.out.println("Waiting for messages...");
		} catch (Exception e) {
			logger.error("Error While Initialize Message Listener : ", e);
		}
	}

	public void onMessage(Message message) {
		try {
			if (count == 0) {
				start = System.currentTimeMillis();
			}
			if (++count % 10 == 0) {
				System.out.println("Received " + count + " messages.");
			}
			ObjectMessage objectMessage = (ObjectMessage) message;
			TmpLogSMS logSMS = (TmpLogSMS) objectMessage.getObject();
			String phone = logSMS.getPhone();
			String smsTxt = logSMS.getSmsText();
			phone = ("995" + phone);
			System.out.println("Sending SMS. phone = " + phone + ", smsTxt = "
					+ smsTxt);
			beelineSMSCManager.sendMessage(phone, smsTxt);
		} catch (Exception e) {
			logger.error("Error While On Message : ", e);
		}
	}
}
