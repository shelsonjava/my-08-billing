package com.info08.billing.callcenter.shared.entity.session;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the LOG_SESSIONS database table.
 * 
 */
@Entity
@Table(name="LOG_SESSIONS", schema="INFO")
public class LogSession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SESSION_ID")
	private String session_id;

	@Basic
	@Column(name="CALL_TYPE")
	private Long call_type;

	@Basic
	@Column(name="DURATION")
	private Long duration;

	@Basic
    @Column(name="END_DATE")
	private Timestamp end_date;

	@Basic
	@Column(name="HUNGUP")
	private Long hungup;

	@Basic
	@Column(name="IS_NEW_BILL")
	private Long is_new_bill;

	@Basic
	@Column(name="PARENT_ID")
	private Long parent_id;

	@Basic
	@Column(name="PHONE")
	private String phone;

	@Basic
	@Column(name="SESSION_QUALITY")
	private Long session_quality;

	@Basic
    @Column(name="START_DATE")
	private Timestamp start_date;

	@Basic
	@Column(name="USER_NAME")
	private String user_name;

	@Basic
	@Column(name="YM")
	private Long ym;

    public LogSession() {
    }

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public Long getCall_type() {
		return call_type;
	}

	public void setCall_type(Long call_type) {
		this.call_type = call_type;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Timestamp getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}

	public Long getHungup() {
		return hungup;
	}

	public void setHungup(Long hungup) {
		this.hungup = hungup;
	}

	public Long getIs_new_bill() {
		return is_new_bill;
	}

	public void setIs_new_bill(Long is_new_bill) {
		this.is_new_bill = is_new_bill;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getSession_quality() {
		return session_quality;
	}

	public void setSession_quality(Long session_quality) {
		this.session_quality = session_quality;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getYm() {
		return ym;
	}

	public void setYm(Long ym) {
		this.ym = ym;
	}
}