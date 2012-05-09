package com.info08.billing.callcenterbk.shared.entity.calendar;

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


/**
 * The persistent class for the CHURCH_CALENDAR_EVENTS database table.
 * 
 */
@Entity
@Table(name="CHURCH_CALENDAR_EVENTS", schema="ccare")
public class ChurchCalendarEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHURCH_CALENDAR_EVENTS_EVENTID_GENERATOR", sequenceName="TRANSPORT_PLACE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHURCH_CALENDAR_EVENTS_EVENTID_GENERATOR")
	@Column(name="EVENT_ID")
	private Long event_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="EVENT")
	private String event;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

    public ChurchCalendarEvent() {
    }

	public Long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
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
}