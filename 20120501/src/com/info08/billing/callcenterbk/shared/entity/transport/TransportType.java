package com.info08.billing.callcenterbk.shared.entity.transport;

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
 * The persistent class for the TRANSPORT_TYPES database table.
 * 
 */
@Entity
@Table(name="TRANSPORT_TYPES",schema="ccare")
public class TransportType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRANSPORT_TYPES_TRANSPORTTYPEID_GENERATOR", sequenceName="TRANSPORT_TYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRANSPORT_TYPES_TRANSPORTTYPEID_GENERATOR")
	@Column(name="TRANSPORT_TYPE_ID")
	private Long transport_type_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="INTERCITY")
	private Long intercity;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="TRANSPORT_TYPE_NAME_ENG")
	private String transport_type_name_eng;

	@Basic
	@Column(name="TRANSPORT_TYPE_NAME_GEO")
	private String transport_type_name_geo;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String loggedUserName;
	
	@Transient
	private String intercityDescr;

    public TransportType() {
    }

	public Long getTransport_type_id() {
		return transport_type_id;
	}

	public void setTransport_type_id(Long transport_type_id) {
		this.transport_type_id = transport_type_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getIntercity() {
		return intercity;
	}

	public void setIntercity(Long intercity) {
		this.intercity = intercity;
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

	public String getTransport_type_name_eng() {
		return transport_type_name_eng;
	}

	public void setTransport_type_name_eng(String transport_type_name_eng) {
		this.transport_type_name_eng = transport_type_name_eng;
	}

	public String getTransport_type_name_geo() {
		return transport_type_name_geo;
	}

	public void setTransport_type_name_geo(String transport_type_name_geo) {
		this.transport_type_name_geo = transport_type_name_geo;
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

	public String getIntercityDescr() {
		return intercityDescr;
	}

	public void setIntercityDescr(String intercityDescr) {
		this.intercityDescr = intercityDescr;
	}
}