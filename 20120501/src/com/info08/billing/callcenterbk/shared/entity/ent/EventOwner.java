package com.info08.billing.callcenterbk.shared.entity.ent;

import java.io.Serializable;

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
 * The persistent class for the ENT_PLACES database table.
 * 
 */
@Entity
@Table(name = "EVENT_OWNER", schema = "ccare")
public class EventOwner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_EVENT_OWNER_GENERATOR", sequenceName = "SEQ_EVENT_OWNER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT_OWNER_GENERATOR")
	@Column(name = "EVENT_OWNER_ID")
	private Long event_owner_id;

	@Basic
	@Column(name = "EVENT_OWNER_NAME")
	private String event_owner_name;

	@Basic
	@Column(name = "EVENT_CATEGORY_ID")
	private Long event_category_id;

	@Basic
	@Column(name = "MAIN_ID")
	private Long main_id;

	@Basic
	@Column(name = "RESERVABLE")
	private Long reservable;

	@Transient
	private String loggedUserName;

	@Transient
	private String event_category_name;

	@Transient
	private String org_name;

	public EventOwner() {
	}

	public Long getEvent_owner_id() {
		return event_owner_id;
	}

	public void setEvent_owner_id(Long event_owner_id) {
		this.event_owner_id = event_owner_id;
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

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
	}

	public Long getReservable() {
		return reservable;
	}

	public void setReservable(Long reservable) {
		this.reservable = reservable;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getEvent_category_name() {
		return event_category_name;
	}

	public void setEvent_category_name(String event_category_name) {
		this.event_category_name = event_category_name;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

}