package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogSessionItem implements Serializable {

	private static final long serialVersionUID = 7026086622425435823L;
	private Integer personnel_id;
	private Integer ym;
	private String session_id;
	private String user_name;
	private String person_name;
	private Timestamp start_date;
	private String phone;
	private Integer duration;
	private String hangUp;
	private Integer chargeCount;
	private Integer session_quality;
	private String session_quality_desc;

	public Integer getPersonnel_id() {
		return personnel_id;
	}

	public void setPersonnel_id(Integer personnel_id) {
		this.personnel_id = personnel_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getHangUp() {
		return hangUp;
	}

	public void setHangUp(String hangUp) {
		this.hangUp = hangUp;
	}

	public Integer getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Integer chargeCount) {
		this.chargeCount = chargeCount;
	}

	public Integer getSession_quality() {
		return session_quality;
	}

	public void setSession_quality(Integer session_quality) {
		this.session_quality = session_quality;
	}

	public String getSession_quality_desc() {
		return session_quality_desc;
	}

	public void setSession_quality_desc(String session_quality_desc) {
		this.session_quality_desc = session_quality_desc;
	}

	public Integer getYm() {
		return ym;
	}

	public void setYm(Integer ym) {
		this.ym = ym;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

}
