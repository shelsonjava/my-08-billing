package com.info08.billing.callcenter.server.items;

import java.io.InputStream;
import java.io.Serializable;

public class UserImageObject implements Serializable {

	private static final long serialVersionUID = -6833162309387408338L;
	private String title;
	private InputStream imageTest;
	private long imageTest_filesize;
	private String imageTest_filename;
	private String image_ext;
	private int code;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public InputStream getImageTest() {
		return imageTest;
	}

	public void setImageTest(InputStream imageTest) {
		this.imageTest = imageTest;
	}

	public long getImageTest_filesize() {
		return imageTest_filesize;
	}

	public void setImageTest_filesize(long imageTest_filesize) {
		this.imageTest_filesize = imageTest_filesize;
	}

	public String getImageTest_filename() {
		return imageTest_filename;
	}

	public void setImageTest_filename(String imageTest_filename) {
		this.imageTest_filename = imageTest_filename;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getImage_ext() {
		return image_ext;
	}

	public void setImage_ext(String image_ext) {
		this.image_ext = image_ext;
	}
}
