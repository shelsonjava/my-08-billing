package com.info08.billing.callcenterbk.shared.entity.ent;

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
 * The persistent class for the ENT_POSTERS database table.
 * 
 */
@Entity
@Table(name = "EVENT", schema = "ccare")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_EVENT_GENERATOR", sequenceName = "SEQ_EVENT")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT_GENERATOR")
	@Column(name = "EVENT_ID")
	private Long event_id;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "DATE_CRITERIA")
	private Long date_criteria;

	@Basic
	@Column(name = "DATE_VISIBILITY")
	private Long date_visibility;

	@Basic
	@Column(name = "EVENT_OWNER_ID")
	private Long event_owner_id;

	@Basic
	@Column(name = "EVENT_NAME")
	private String event_name;

	@Basic
	@Column(name = "EVENT_DATE")
	private Timestamp event_date;

	@Basic
	@Column(name = "EVENT_PRICE")
	private String event_price;

	@Basic
	@Column(name = "EVENT_TIME")
	private String event_time;

	@Basic
	@Column(name = "SMS_REMARK")
	private String sms_remark;

	@Transient
	private String loggedUserName;

	@Transient
	private String event_owner_name;

	@Transient
	private Long event_category_id;

	public Event() {
	}

	public Long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getDate_criteria() {
		return date_criteria;
	}

	public void setDate_criteria(Long date_criteria) {
		this.date_criteria = date_criteria;
	}

	public Long getDate_visibility() {
		return date_visibility;
	}

	public void setDate_visibility(Long date_visibility) {
		this.date_visibility = date_visibility;
	}

	public Long getEvent_owner_id() {
		return event_owner_id;
	}

	public void setEvent_owner_id(Long event_owner_id) {
		this.event_owner_id = event_owner_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public Timestamp getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Timestamp event_date) {
		this.event_date = event_date;
	}

	public String getEvent_price() {
		return event_price;
	}

	public void setEvent_price(String event_price) {
		this.event_price = event_price;
	}

	public String getEvent_time() {
		return event_time;
	}

	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}

	public String getSms_remark() {
		return sms_remark;
	}

	public void setSms_remark(String sms_remark) {
		this.sms_remark = sms_remark;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getEvent_owner_name() {
		return event_owner_name;
	}

	public void setEvent_owner_name(String event_owner_name) {
		this.event_owner_name = event_owner_name;
	}

	public Long getEvent_category_id() {
		return event_category_id;
	}

	public void setEvent_category_id(Long event_category_id) {
		this.event_category_id = event_category_id;
	}

}