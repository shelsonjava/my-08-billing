package com.info08.billing.callcenterbk.server.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class SMSSenderGeocell implements MessageListener {

//	Logger logger = Logger.getLogger(SMSSenderGeocell.class.getName());
//	private Connection connection;
//	protected MessageProducer producer;
//	private Session session;
//	private int count;
//	protected long start;
//	private Topic topic;
//	private String url = "tcp://localhost:61617";
//	//private String geocellUrl = "http://91.151.128.64:7777/pls/sms/phttp2sms.Process/src=%s&dst=%s&txt=%s";
//	private String magtiUrl = "http://81.95.160.47/mt/sendsms?username=jeocnobari&password=jeo123&client_id=414&service_id=%s&to=%s&text=%s";
//	private String from = "11808";

	public void run() throws JMSException {
		try {
//			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
//					url);
//			connection = factory.createConnection();
//			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			topic = session.createTopic("smstopic.messages");
//			MessageConsumer consumer = session.createConsumer(topic);
//			consumer.setMessageListener(this);
//
//			connection.start();
//
//			producer = session.createProducer(topic);
//			System.out.println("Waiting for messages...");
		} catch (Exception e) {
//			logger.error("Error While Initialize Message Listener : ", e);
		}
	}

	public void onMessage(Message message) {
		try {
//			if (count == 0) {
//				start = System.currentTimeMillis();
//			}
//			if (++count % 10 == 0) {
//				System.out.println("Received " + count + " messages.");
//			}
//			ObjectMessage objectMessage = (ObjectMessage) message;
//			SentSMSHist logSMS = (SentSMSHist) objectMessage.getObject();
//			String phone = logSMS.getReciever_number();
//			String smsTxt = logSMS.getMessage_context();
//			sendOverHttp(phone, smsTxt);
//			System.out.println("logSMS = " + logSMS);
		} catch (Exception e) {
//			logger.error("Error While On Message : ", e);
		}
	}

//	private void sendOverHttp(String phone, String smsTxt) {
//		HttpClient client = new HttpClient();
//		GetMethod method = null;
//		try {
//			String lUrl = String.format(magtiUrl, from, phone, smsTxt);
//			System.out.println("lUrl = " + lUrl);
//			
//			client.getParams().setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, new Long(2000));
//	        client.getParams().setParameter(HttpClientParams.SO_TIMEOUT, new Integer(2000));
//
//			method = new GetMethod(lUrl);
//			
//			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3, false));
//			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(2000));
//			method.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, new Integer(2000));
//			
//			
//			int statusCode = client.executeMethod(method);
//			if (statusCode != HttpStatus.SC_OK) {
//				System.err.println("Method failed: " + method.getStatusLine());
//			}
//			byte[] responseBody = method.getResponseBody();
//			System.out.println(new String(responseBody));
//		} catch (Exception e) {
//			logger.error("Error While Sending Message Over HTTP : ", e);
//		} finally {
//			if (method != null) {
//				method.releaseConnection();
//			}
//		}
//	}

}
