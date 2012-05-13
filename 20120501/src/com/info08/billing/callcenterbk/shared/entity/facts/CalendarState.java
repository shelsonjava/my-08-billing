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


/**
 * The persistent class for the CALENDAR_STATE database table.
 * 
 */
@Entity
@Table(name="CALENDAR_STATE", schema="ccare")
public class CalendarState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CALENDAR_STATE_STATEID_GENERATOR", sequenceName="TRANSPORT_PLACE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CALENDAR_STATE_STATEID_GENERATOR")
	@Column(name="STATE_ID")
	private Long state_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="STATE")
	private String state;

    public CalendarState() {
    }

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}