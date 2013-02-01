package com.info08.billing.callcenterbk.server.jms;

import java.net.URLEncoder;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.shared.entity.SentSMSHist;
import com.isomorphic.jpa.EMF;

public class SMSSenderMagti implements MessageListener {

	Logger logger = Logger.getLogger(SMSSenderMagti.class.getName());
	private Connection connection;
	protected MessageProducer producer;
	private Session session;
	private int count;
	protected long start;
	private Topic topic;
	private String url = "tcp://localhost:61617";
	// private String urlMgt =
	// "http://81.95.160.47/mt/sendsms?username=jeocnobari&password=jeo123&client_id=414&service_id=%s&to=%s&text=%s";
	// private String from = "16008";
	// http://msg.ge/bi/track.php?username=nolrva&password=as110rva&client_id=3&service_id=0003&message_id=7
	// private String urlSMS =
	// "http://msg.ge/bi/sendsms.php?username=nolrva&password=as110rva&client_id=3&service_id=0003&to=%s&text=%s";
	private String urlSMS = "http://91.151.128.64:7777/pls/sms/phttp2sms.Process?src=11375&dst=%s&txt=%s";

	public void run() throws JMSException {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("mgtsmstopic.messages");
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);
			connection.start();
			producer = session.createProducer(topic);
			logger.info("Waiting for messages...");
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
				logger.info("Received " + count + " messages.");
			}
			ObjectMessage objectMessage = (ObjectMessage) message;
			SentSMSHist logSMS = (SentSMSHist) objectMessage.getObject();
			sendOverHttp(logSMS);
		} catch (Exception e) {
			logger.error("Error While On Message : ", e);
		}
	}

	private void sendOverHttp(SentSMSHist logSMS) {
		HttpPost method = null;
		HttpClient client = null;
		try {
			String phone = logSMS.getReciever_number().trim();
			if (phone.trim().length() != 9) {
				logSMS.setHist_status_id(-1L);
				updateSMSLog(logSMS);
				return;
			}
			phone = "995" + phone.trim();
			String smsTxt = logSMS.getMessage_context();
			smsTxt = URLEncoder.encode(smsTxt, "UTF-8");
			String lUrl = String.format(urlSMS, phone, smsTxt);
			logger.info("Sengind SMS. lUrl = " + lUrl);

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);

			client = new DefaultHttpClient(httpParams);
			method = new HttpPost(lUrl);

			HttpResponse httpResponse = client.execute(method);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity httpEntity = httpResponse.getEntity();
			// InputStream inputStream = httpEntity.getContent();
			// byte data[] = new byte[inputStream.available()];
			// inputStream.read(data);
			// String str = new String(data);
			// int idx = str.indexOf("-");
			String str = null;
			if (httpEntity == null) {
				logSMS.setHist_status_id(-9L);
			} else {
				str = EntityUtils.toString(httpEntity);
				if (statusCode == HttpStatus.SC_OK) {
					if (str == null) {
						logSMS.setHist_status_id(-7L);
					} else if (str != null && str.contains("N")) {
						logSMS.setHist_status_id(-3L);
					} else if (str != null && str.contains("Y")) {
						logSMS.setHist_status_id(1L);
						logSMS.setGsm_operator_msg_id("10000");
					} else {
						logSMS.setHist_status_id(-8L);
					}
				} else {
					logSMS.setHist_status_id(-4L);
				}
			}
			logger.info("SMS Response : " + str + ", phone = " + phone
					+ ", Status = " + logSMS.getHist_status_id());

		} catch (Exception e) {
			logger.error("Error While Sending Message Over HTTP : ", e);
			logSMS.setHist_status_id(-5L);
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
			updateSMSLog(logSMS);
		}
	}

	private void updateSMSLog(SentSMSHist tmpLogSMS) {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			oracleManager.merge(tmpLogSMS);
			EMF.commitTransaction(transaction);
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			logger.error("Error while update SMS log : ", e);
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
