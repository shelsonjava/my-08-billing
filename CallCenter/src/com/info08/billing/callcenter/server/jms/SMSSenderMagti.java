package com.info08.billing.callcenter.server.jms;

import java.io.InputStream;
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
import org.apache.log4j.Logger;

import com.info08.billing.callcenter.shared.entity.LogSMS;
import com.isomorphic.jpa.EMF;

public class SMSSenderMagti implements MessageListener {

	Logger logger = Logger.getLogger(SMSSenderMagti.class.getName());
	private Connection connection;
	protected MessageProducer producer;
	private Session session;
	private int count;
	protected long start;
	private Topic topic;
	private String url = "tcp://localhost:61616";
	// private String urlMgt =
	// "http://81.95.160.47/mt/sendsms?username=jeocnobari&password=jeo123&client_id=414&service_id=%s&to=%s&text=%s";
	// private String from = "16008";
	// http://msg.ge/bi/track.php?username=nolrva&password=as110rva&client_id=3&service_id=0003&message_id=7
	private String urlSMS = "http://msg.ge/bi/sendsms.php?username=nolrva&password=as110rva&client_id=3&service_id=0003&to=%s&text=%s";

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
			LogSMS logSMS = (LogSMS) objectMessage.getObject();
			sendOverHttp(logSMS);
		} catch (Exception e) {
			logger.error("Error While On Message : ", e);
		}
	}

	private void sendOverHttp(LogSMS logSMS) {
		HttpPost method = null;
		HttpClient client = null;
		try {
			String phone = logSMS.getPhone().trim();
			if (phone.trim().length() != 9) {
				logSMS.setStatus(-1L);
				updateSMSLog(logSMS);
				return;
			}
			phone = "995" + phone.trim();
			String smsTxt = logSMS.getSms_text();
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
			InputStream inputStream = httpEntity.getContent();
			byte data[] = new byte[inputStream.available()];
			inputStream.read(data);
			String str = new String(data);
			int idx = str.indexOf("-");
			if (statusCode == HttpStatus.SC_OK) {
				if (idx < 0) {
					logSMS.setStatus(-3L);
				} else {
					String msgId = str.substring((idx + 1), str.length());
					logSMS.setStatus(1L);
					logSMS.setSmsc_message_id(msgId);
				}
			} else {
				logSMS.setStatus(-4L);
			}

			logger.info("SMS Response : " + str + ", phone = " + phone
					+ ", Status = " + logSMS.getStatus());

		} catch (Exception e) {
			logger.error("Error While Sending Message Over HTTP : ", e);
			logSMS.setStatus(-5L);
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
			updateSMSLog(logSMS);
		}
	}

	private void updateSMSLog(LogSMS tmpLogSMS) {
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
