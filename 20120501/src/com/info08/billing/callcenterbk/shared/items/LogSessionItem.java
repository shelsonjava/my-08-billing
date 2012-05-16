package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogSessionItem implements Serializable {

	private static final long serialVersionUID = 7026086622425435823L;
	private Integer user_id;
	private Integer year_month;
	private String session_id;
	private String uname;
	private String full_user_name;
	private Timestamp call_start_date;
	private String call_phone;
	private Integer call_duration;
	private String reject_type;
	private Integer chargeCount;
	private Integer call_quality;
	private String call_quality_desc;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getYear_month() {
		return year_month;
	}

	public void setYear_month(Integer year_month) {
		this.year_month = year_month;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getFull_user_name() {
		return full_user_name;
	}

	public void setFull_user_name(String full_user_name) {
		this.full_user_name = full_user_name;
	}

	public Timestamp getCall_start_date() {
		return call_start_date;
	}

	public void setCall_start_date(Timestamp call_start_date) {
		this.call_start_date = call_start_date;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	public Integer getCall_duration() {
		return call_duration;
	}

	public void setCall_duration(Integer call_duration) {
		this.call_duration = call_duration;
	}

	public String getReject_type() {
		return reject_type;
	}

	public void setReject_type(String reject_type) {
		this.reject_type = reject_type;
	}

	public Integer getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Integer chargeCount) {
		this.chargeCount = chargeCount;
	}

	public Integer getCall_quality() {
		return call_quality;
	}

	public void setCall_quality(Integer call_quality) {
		this.call_quality = call_quality;
	}

	public String getCall_quality_desc() {
		return call_quality_desc;
	}

	public void setCall_quality_desc(String call_quality_desc) {
		this.call_quality_desc = call_quality_desc;
	}
}
