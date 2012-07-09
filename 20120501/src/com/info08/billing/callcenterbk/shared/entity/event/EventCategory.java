package com.info08.billing.callcenterbk.shared.entity.event;

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
 * The persistent class for the ENT_TYPES database table.
 * 
 */
@Entity
@Table(name = "EVENT_CATEGORY")
public class EventCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_EVENT_CATEGORY_GENERATOR", sequenceName = "SEQ_EVENT_CATEGORY")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT_CATEGORY_GENERATOR")
	@Column(name = "EVENT_CATEGORY_ID")
	private Long event_category_id;

	@Basic
	@Column(name = "EVENT_CATEGORY_NAME")
	private String event_category_name;

	@Transient
	private String loggedUserName;

	public EventCategory() {
	}

	public Long getEvent_category_id() {
		return event_category_id;
	}

	public void setEvent_category_id(Long event_category_id) {
		this.event_category_id = event_category_id;
	}

	public String getEvent_category_name() {
		return event_category_name;
	}

	public void setEvent_category_name(String event_category_name) {
		this.event_category_name = event_category_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}