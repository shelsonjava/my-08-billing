package com.info08.billing.callcenter.client;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class TestMain {
	public static void main(String[] args) {
		try {

			// System.out.println("322123456".endsWith("123456"));
			// //

			// Class.forName("com.mysql.jdbc.Driver");
			// Connection connection = DriverManager.getConnection(
			// "jdbc:mysql://192.168.1.251/asteriskcdrdb", "kaxa", "kaxa");
			// PreparedStatement statement = connection
			// .prepareStatement("select t.code from asteriskcdrdb.block t where t.code like CONCAT('%', ?) ");
			// statement.setString(1, "2");
			// ResultSet resultSet = statement.executeQuery();
			// while (resultSet.next()) {
			// String phone = resultSet.getString(1);
			// System.out.println("phone = " + phone);
			// }
			// statement.close();
			// connection.close();

			// System.out.println("322381075".length());
			//
			// String str = new String("0000-508");
			// int idx = str.indexOf("-");
			//
			//
			// String msgId = str.substring((idx + 1), str.length());
			// System.out.println("msgId = "+msgId);
			//
			// if (true) {
			// return;
			// }

			File _File = new File("/home/kakha/Desktop/2222222222.jpg");
			FileInputStream file = new FileInputStream(_File);
			PostMethod filePost = new PostMethod(
					"http://192.168.1.254:8083/cgi-bin/upload.pl");

			byte arr[] = new byte[file.available()];
			file.read(arr);

			filePost.getParams().setBooleanParameter(
					HttpMethodParams.USE_EXPECT_CONTINUE, true);

			ByteArrayPartSource arrayPartSource = new ByteArrayPartSource(
					_File.getName(), arr);

			// Part[] parts = { new StringPart("op", "upload"),
			// new StringPart("id", "22222222222"),
			// new StringPart("file_name", _File.getName()),
			// new FilePart("image", arrayPartSource) };

			Part[] parts = { new StringPart("op", "save"),
					new StringPart("id", "22222222222"),
					new StringPart("file_name", "1305884041.jpg"),
					new FilePart("image", arrayPartSource) };

			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));

			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			String ret = filePost.getResponseBodyAsString();
			System.out.println("status = " + status + ", ret = " + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
