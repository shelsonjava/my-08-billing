package com.info08.billing.callcenter.server.listener.sms;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

public class SMSMessageListener implements ServletContextListener {

	private Logger logger = Logger
			.getLogger(SMSMessageListener.class.getName());

	private String urlMgt = "http://81.95.160.47/mt/sendsms?username=jeocnobari&password=jeo123&client_id=414&service_id=%s&to=%s&text=%s";
	private String from = "16008";

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			logger.info("ServletContextListener destroyed ................ ");
		} catch (Exception e) {
			logger.error(
					"Error While Context Destroyed For SMSMessageListener : ",
					e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent event) {
		EntityManager oracleManager = null;
		StringBuilder log = new StringBuilder();
		try {
			logger.info("ServletContextListener started ................ ");
			if (InetAddress.getLocalHost().getHostAddress()
					.contains("127.0.0.")) {
				return;
			}
			boolean flag = true;
			if (flag) {
				return;
			}

			oracleManager = EMF.getEntityManager();
			if (oracleManager == null) {
				log.append("SMS Batch List Error. Couldn't Initialiaze Database.");
				logger.info(log.toString());
				return;
			}
			ArrayList<LogSMS> resultList = (ArrayList<LogSMS>) oracleManager
					.createNamedQuery("LogSMS.getForSending").getResultList();
			if (resultList == null || resultList.isEmpty()) {
				log.append("SMS Batch List Is Empty.");
				logger.info(log.toString());
				return;
			}

			for (LogSMS logSMS : resultList) {
				try {
					sendOverHttp(logSMS);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			logger.error(
					"Error While Context Initialize For SMSMessageListener : ",
					e);
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
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
			String lUrl = String.format(urlMgt, from, phone, smsTxt);
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
			int idx = str.indexOf("-");
			if (statusCode == HttpStatus.SC_OK) {
				if (idx < 0) {
					logSMS.setStatus(-3L);
				} else {
					String msgId = str.substring((idx + 2), str.length());
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
