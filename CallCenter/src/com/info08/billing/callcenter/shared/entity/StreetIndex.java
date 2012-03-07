package com.info08.billing.callcenter.shared.entity;

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
 * The persistent class for the STREET_INDEX database table.
 * 
 */
@Entity
@Table(name="STREET_INDEX",schema="info")
public class StreetIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STREET_INDEX_STREETINDEXID_GENERATOR", sequenceName="street_index_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREET_INDEX_STREETINDEXID_GENERATOR")
	@Column(name="STREET_INDEX_ID")
	private Long street_index_id;

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
	@Column(name="STREET_COMMENT")
	private String street_comment;

	@Basic
	@Column(name="STREET_ID")
	private Long street_id;

	@Basic
	@Column(name="STREET_INDEX")
	private String street_index;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String streetName;
	
	@Transient
	private String loggedUserName;

    public StreetIndex() {
    }

	public Long getStreet_index_id() {
		return street_index_id;
	}

	public void setStreet_index_id(Long street_index_id) {
		this.street_index_id = street_index_id;
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

	public String getStreet_comment() {
		return street_comment;
	}

	public void setStreet_comment(String street_comment) {
		this.street_comment = street_comment;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getStreet_index() {
		return street_index;
	}

	public void setStreet_index(String street_index) {
		this.street_index = street_index;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}