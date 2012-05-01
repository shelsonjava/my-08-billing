package com.info08.billing.callcenterbk.shared.entity.discovery;

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
 * The persistent class for the DISCOVER_RTYPES database table.
 * 
 */
@Entity
@Table(name="DISCOVER_RTYPES",schema="PAATA")
public class DiscoverRtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DISCOVER_RTYPES_RESPONSETYPEID_GENERATOR", sequenceName="DISCOVER_RESPONSE_TYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DISCOVER_RTYPES_RESPONSETYPEID_GENERATOR")
	@Column(name="RESPONSE_TYPE_ID")
	private Long response_type_id;

	@Basic
	@Column(name="RESPONSE_TYPE")
	private String response_type;
	
	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Column(name="REC_USER")
	private String rec_user;
	
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
	private String loggedUserName;

    public DiscoverRtype() {
    }

	public Long getResponse_type_id() {
		return response_type_id;
	}

	public void setResponse_type_id(Long response_type_id) {
		this.response_type_id = response_type_id;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
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

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}
}