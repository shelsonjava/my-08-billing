package com.info08.billing.callcenterbk.shared.wtest;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;

public class TestMain {
	public static void main(String[] args) {
		
		String urlSMS = "http://msg.ge/bi/sendsms.php?username=nolrva&password=as110rvasw&client_id=3&service_id=0003&to=%s&text=%s";
		

		HttpGet method = null;
		HttpClient client = null;
		try {
			String phone = "599693939";
			phone = "995" + phone.trim();
			String smsTxt = "KKKK";
			smsTxt = URLEncoder.encode(smsTxt, "UTF-8");
			String lUrl = String.format(urlSMS, phone, smsTxt);
			System.out.println("Sengind SMS. lUrl = " + lUrl);

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);

			client = new DefaultHttpClient(httpParams);
			method = new HttpGet(lUrl);

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
				//logSMS.setHist_status_id(-9L);
				System.out.println("-9L");
			} else {
				str = EntityUtils.toString(httpEntity);
				if (statusCode == HttpStatus.SC_OK) {
					
					Long histStatusId = Long.parseLong(str.substring(0,4));
					Long messageId = Long.parseLong(str.substring(5,str.length()));
					
					System.out.println("histStatusId = " + histStatusId+", Message id = "+messageId);
					
				} else {
//					logSMS.setHist_status_id(-4L);
					System.out.println("-4L");
				}
			}
			System.out.println("SMS Response : " + str + ", phone = " + phone
					+ ", Status = " + 0);

		} catch (Exception e) {
//			logger.error("Error While Sending Message Over HTTP : ", e);
//			logSMS.setHist_status_id(-5L);
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
//			updateSMSLog(logSMS);
		}
		
	}
}
