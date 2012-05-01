package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class FirstName implements Serializable {

	private static final long serialVersionUID = -180371369122938828L;
	private Integer firstname_Id;
	private String firstname;
	private Timestamp rec_date;
	private Integer deleted;
	private String deletedText;
	private String loggedUserName;

	public Integer getFirstname_Id() {
		return firstname_Id;
	}

	public void setFirstname_Id(Integer firstname_Id) {
		this.firstname_Id = firstname_Id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getDeletedText() {
		return deletedText;
	}

	public void setDeletedText(String deletedText) {
		this.deletedText = deletedText;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
