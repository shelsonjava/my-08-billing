package com.info08.billing.callcenterbk.server.impl.dmi;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.items.UserImageObject;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.servlet.ISCFileItem;

public class UploadImageDMI {

	public UserImageObject add(DSRequest record) throws CallCenterException {
		HttpClient client = null;
		PostMethod filePost = null;
		HttpClient getClient = null;
		GetMethod getMethod = null;
		try {
			UserImageObject newImage = new UserImageObject();
			newImage.setTitle((String) record.getFieldValue("title"));
			System.out.println("UserImageObject - Uploading File ... ");
			String file_name = (String) record.getFieldValue("file_name");
			ISCFileItem file = record.getUploadedFile("imageTest");
			InputStream stream = file.getInputStream();
			String realFileName = file.getFileName();
			System.out.println("realFileName = " + realFileName);
			if (realFileName == null || realFileName.trim().equals("")) {
				throw new CallCenterException("არასწორი სურათი !");
			}
			int index = realFileName.indexOf(".");
			if (index <= 0) {
				throw new CallCenterException("არასწორი სურათი 1!");
			}
			String ext = realFileName.substring(index);
			if (ext == null || ext.trim().equals("")) {
				throw new CallCenterException("არასწორი სურათი 2!");
			}
			if (!ext.equalsIgnoreCase(".jpg") && !ext.equalsIgnoreCase(".gif")
					&& !ext.equalsIgnoreCase(".jpeg") && !ext.equals(".png")) {
				throw new CallCenterException("არასწორი სურათის გაფართოება!");
			}
			file_name += ext;
			String url = Constants.hrImagesDirUrl;
			System.out.println("url = " + url);
			System.out.println("file_name = " + file_name);

			byte arr[] = new byte[stream.available()];
			stream.read(arr);

			filePost = new PostMethod(url);

			filePost.getParams().setBooleanParameter(
					HttpMethodParams.USE_EXPECT_CONTINUE, true);

			ByteArrayPartSource arrayPartSource = new ByteArrayPartSource(
					file.getFileName(), arr);

			Part[] parts = { new StringPart("file_name", file_name),
					new FilePart("image", arrayPartSource) };

			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));

			client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			System.out.println("status = " + status);

			String urlForGet = Constants.hrImagesDirLink;
			urlForGet += file_name;
			getClient = new HttpClient();
			getMethod = new GetMethod(urlForGet);

			getClient.executeMethod(getMethod);

			String response = getMethod.getResponseBodyAsString();
			System.out.println("Get Response = " + response);

			newImage.setImageTest_filesize(file.getSize());
			newImage.setImageTest_filename(file.getFileName());
			newImage.setImage_ext(ext);
			return newImage;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა სურათის ატვირთვისას : "
					+ e.toString());
		} finally {
			if (filePost != null) {
				filePost.releaseConnection();
			}
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
	}

}
