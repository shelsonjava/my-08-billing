package com.info08.billing.callcenter.shared.entity.ent;

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
 * The persistent class for the ENT_TYPES database table.
 * 
 */
@Entity
@Table(name="ENT_TYPES", schema="INFO")
public class EntType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ENT_TYPES_ENTTYPEID_GENERATOR", sequenceName="ENT_TYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENT_TYPES_ENTTYPEID_GENERATOR")
	@Column(name="ENT_TYPE_ID")
	private Long ent_type_id;

	@Basic	
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="ENT_TYPE_ENG")
	private String ent_type_eng;

	@Basic
	@Column(name="ENT_TYPE_GEO")
	private String ent_type_geo;

	@Basic
	@Column(name="OLD_ID")
	private Long old_id;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
    private String loggedUserName;

    public EntType() {
    }

	public Long getEnt_type_id() {
		return ent_type_id;
	}

	public void setEnt_type_id(Long ent_type_id) {
		this.ent_type_id = ent_type_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getEnt_type_eng() {
		return ent_type_eng;
	}

	public void setEnt_type_eng(String ent_type_eng) {
		this.ent_type_eng = ent_type_eng;
	}

	public String getEnt_type_geo() {
		return ent_type_geo;
	}

	public void setEnt_type_geo(String ent_type_geo) {
		this.ent_type_geo = ent_type_geo;
	}

	public Long getOld_id() {
		return old_id;
	}

	public void setOld_id(Long old_id) {
		this.old_id = old_id;
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

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
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
}