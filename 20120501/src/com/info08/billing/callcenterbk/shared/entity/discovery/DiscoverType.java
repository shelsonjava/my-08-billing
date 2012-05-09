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
 * The persistent class for the DISCOVER_TYPES database table.
 * 
 */
@Entity
@Table(name="DISCOVER_TYPES",schema="ccare")
public class DiscoverType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DISCOVER_TYPES_DISCOVERTYPEID_GENERATOR", sequenceName="DISCOVER_TYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DISCOVER_TYPES_DISCOVERTYPEID_GENERATOR")
	@Column(name="DISCOVER_TYPE_ID")
	private Long discover_type_id;

	@Basic
	@Column(name="DISCOVER_TYPE")
	private String discover_type;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Column(name="REC_USER")
	private String rec_user;
	
	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Column(name="UPD_USER")
	private String upd_user;
	
	@Basic
	@Column(name="DELETED")
	private Long deleted;
	
	@Transient
	private String loggedUserName;

    public DiscoverType() {
    }

	public Long getDiscover_type_id() {
		return discover_type_id;
	}

	public void setDiscover_type_id(Long discover_type_id) {
		this.discover_type_id = discover_type_id;
	}

	public String getDiscover_type() {
		return discover_type;
	}

	public void setDiscover_type(String discover_type) {
		this.discover_type = discover_type;
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