package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class LastName implements Serializable {

	private static final long serialVersionUID = -180371369122938828L;
	private Integer lastname_Id;
	private String lastname;
	private Timestamp rec_date;
	private Integer deleted;
	private String deletedText;
	private String loggedUserName;

	public Integer getLastname_Id() {
		return lastname_Id;
	}

	public void setLastname_Id(Integer lastname_Id) {
		this.lastname_Id = lastname_Id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
