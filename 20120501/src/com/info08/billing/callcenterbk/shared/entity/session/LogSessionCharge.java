package com.info08.billing.callcenterbk.shared.entity.session;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the LOG_SESSION_CHARGES database table.
 * 
 */
@Entity
@Table(name = "LOG_SESSION_CHARGES", schema = "INFO")
public class LogSessionCharge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "PRICE")
	private Long price;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name = "SESSION_ID")
	private String session_id;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Basic
	@Column(name = "YM")
	private Long ym;

	@Transient
	private Long chargeCount;

	@Transient
	private String phone;

	@Transient
	private String loggedUserName;

	@Transient
	private String operator;

	@Transient
	private Long duration;

	@Transient
	private Timestamp start_date;

	public LogSessionCharge() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Long getYm() {
		return ym;
	}

	public void setYm(Long ym) {
		this.ym = ym;
	}

	public Long getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Long chargeCount) {
		this.chargeCount = chargeCount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}