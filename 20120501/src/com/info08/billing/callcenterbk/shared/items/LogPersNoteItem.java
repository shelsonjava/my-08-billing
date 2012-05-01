package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogPersNoteItem implements Serializable {
	private static final long serialVersionUID = 5355699037925800951L;

	private Integer note_id;
	private String sessionId;
	private String receiver;
	private String sender;
	private String phone;
	private Timestamp rec_date;
	private String visibility;
	private Integer visibilityInt;
	private String particular;
	private Integer particularInt;
	private String note;
	private String loggedUserName;

	public Integer getNote_id() {
		return note_id;
	}

	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Integer getVisibilityInt() {
		return visibilityInt;
	}

	public void setVisibilityInt(Integer visibilityInt) {
		this.visibilityInt = visibilityInt;
	}

	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public Integer getParticularInt() {
		return particularInt;
	}

	public void setParticularInt(Integer particularInt) {
		this.particularInt = particularInt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
