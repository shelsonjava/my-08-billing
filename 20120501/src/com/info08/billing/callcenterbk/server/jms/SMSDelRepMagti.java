package com.info08.billing.callcenterbk.server.jms;

import java.io.InputStream;

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

import com.info08.billing.callcenterbk.shared.entity.LogSMS;
import com.isomorphic.jpa.EMF;

public class SMSDelRepMagti implements MessageListener {

	Logger logger = Logger.getLogger(SMSDelRepMagti.class.getName());
	private Connection connection;
	protected MessageProducer producer;
	private Session session;
	private int count;
	protected long start;
	private Topic topic;
	private String url = "tcp://localhost:61616";
	private String urlMgt = "http://81.95.160.47/bi/track.php?username=jeocnobari&password=jeo123&client_id=414&service_id=%s&message_id=%s";
	private String from = "16008";

	public void run() throws JMSException {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("mgtdelrepsmstopic.messages");
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);
			connection.start();
			producer = session.createProducer(topic);
			logger.info("Waiting for messages (Delivery Reporter )...");
		} catch (Exception e) {
			logger.error(
					"Error While Initialize Message Listener (Delivery Report): ",
					e);
		}
	}

	public void onMessage(Message message) {
		try {
			if (count == 0) {
				start = System.currentTimeMillis();
			}
			if (++count % 10 == 0) {
				logger.info("Delivery Reporter. Received " + count
						+ " messages.");
			}
			ObjectMessage objectMessage = (ObjectMessage) message;
			LogSMS logSMS = (LogSMS) objectMessage.getObject();
			getDeliveryReport(logSMS);
		} catch (Exception e) {
			logger.error("Error While On Message(Delivery Report) : ", e);
		}
	}

	private void getDeliveryReport(LogSMS logSMS) {
		HttpPost method = null;
		HttpClient client = null;
		try {
			String messageId = logSMS.getSmsc_message_id();
			String lUrl = String.format(urlMgt, from, messageId);
			logger.info("Sengind SMS. lUrl = " + lUrl);

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);

			client = new DefaultHttpClient(httpParams);
			method = new HttpPost(lUrl);

			HttpResponse httpResponse = client.execute(method);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();
			byte data[] = new byte[inputStream.available()];
			inputStream.read(data);
			String str = new String(data);
			logger.info("HTTP Response (Delivery Report): " + str
					+ ", messageId = " + messageId);
			if (statusCode == HttpStatus.SC_OK) {
				Long delRepStatus = Long.parseLong(str);
				logSMS.setDelivered(delRepStatus);
				if (!delRepStatus.equals(1L)) {
					Long delRepFailCount = logSMS.getDel_rep_fail_counter();
					if (delRepFailCount == null) {
						delRepFailCount = 0L;
					}
					delRepFailCount = (delRepFailCount.longValue() + 1);
					logSMS.setDel_rep_fail_counter(delRepFailCount);
				}
				updateSMSLog(logSMS);
			} else {
				Long delRepFailCount = logSMS.getDel_rep_fail_counter();
				if (delRepFailCount == null) {
					delRepFailCount = 0L;
				}
				delRepFailCount = (delRepFailCount.longValue() + 1);
				logSMS.setDel_rep_fail_counter(delRepFailCount);
				updateSMSLog(logSMS);
			}
		} catch (Exception e) {
			logger.error("Error While Sending Message Over HTTP : ", e);
			Long delRepFailCount = logSMS.getDel_rep_fail_counter();
			if (delRepFailCount == null) {
				delRepFailCount = 0L;
			}
			delRepFailCount = (delRepFailCount.longValue() + 1);
			logSMS.setDel_rep_fail_counter(delRepFailCount);
			updateSMSLog(logSMS);
		} finally {
			if (method != null) {
				method.abort();
			}
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().shutdown();
			}
		}
	}

	private void updateSMSLog(LogSMS tmpLogSMS) {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long delivered = tmpLogSMS.getDelivered();
			Long failCounter = tmpLogSMS.getDel_rep_fail_counter();
			if (!delivered.equals(1L) && failCounter.longValue() > 300) {
				tmpLogSMS.setStatus(-10000300L);
			}
			oracleManager.merge(tmpLogSMS);
			EMF.commitTransaction(transaction);
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			logger.error("Error while update SMS log (Delivery Report) : ", e);
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
