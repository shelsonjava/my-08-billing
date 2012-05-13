package com.info08.billing.callcenterbk.shared.entity.facts;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the SECULAR_CALENDAR database table.
 * 
 */
@Entity
@Table(name="SECULAR_CALENDAR", schema="ccare")
public class SecularCalendar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SECULAR_CALENDAR_CALENDARID_GENERATOR", sequenceName="SEC_CALENDAR_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SECULAR_CALENDAR_CALENDARID_GENERATOR")
	@Column(name="CALENDAR_ID")
	private Long calendar_id;

	@Basic
	@Column(name="CALENDAR_COMMENT")
	private String calendar_comment;

	@Basic
	@Column(name="CALENDAR_DAY")
	private Timestamp calendar_day;

	@Basic
	@Column(name="CALENDAR_DESCRIPTION")
	private String calendar_description;

	@Basic
	@Column(name="CALENDAR_EVENT_ID")
	private Long calendar_event_id;

	@Basic
	@Column(name="CALENDAR_STATE_ID")
	private Long calendar_state_id;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="SUN_RISE")
	private String sun_rise;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Basic
	@Column(name="DELETED")
	private Long deleted;
	
	@Transient
	private String event;
	
	@Transient
	private String state;
	
	@Transient
	private String loggedUserName;

    public SecularCalendar() {
    }

	public Long getCalendar_id() {
		return calendar_id;
	}

	public void setCalendar_id(Long calendar_id) {
		this.calendar_id = calendar_id;
	}

	public String getCalendar_comment() {
		return calendar_comment;
	}

	public void setCalendar_comment(String calendar_comment) {
		this.calendar_comment = calendar_comment;
	}

	public Timestamp getCalendar_day() {
		return calendar_day;
	}

	public void setCalendar_day(Timestamp calendar_day) {
		this.calendar_day = calendar_day;
	}

	public String getCalendar_description() {
		return calendar_description;
	}

	public void setCalendar_description(String calendar_description) {
		this.calendar_description = calendar_description;
	}

	public Long getCalendar_event_id() {
		return calendar_event_id;
	}

	public void setCalendar_event_id(Long calendar_event_id) {
		this.calendar_event_id = calendar_event_id;
	}

	public Long getCalendar_state_id() {
		return calendar_state_id;
	}

	public void setCalendar_state_id(Long calendar_state_id) {
		this.calendar_state_id = calendar_state_id;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public String getSun_rise() {
		return sun_rise;
	}

	public void setSun_rise(String sun_rise) {
		this.sun_rise = sun_rise;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}