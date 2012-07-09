package com.info08.billing.callcenterbk.shared.entity.org;

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
 * The persistent class for the BUSINESS_UNION database table.
 * 
 */
@Entity
@Table(name = "BUSINESS_UNION")
public class BusinessUnion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BUSINESS_UNION_BUSINESSUNIONID_GENERATOR", sequenceName = "BUSINESS_UNION_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUSINESS_UNION_BUSINESSUNIONID_GENERATOR")
	@Column(name = "BUSINESS_UNION_ID")
	private Long business_union_id;

	@Basic
	@Column(name = "BUSINESS_UNION_NAME_ENG")
	private String business_union_name_eng;

	@Basic
	@Column(name = "BUSINESS_UNION_NAME_GEO")
	private String business_union_name_geo;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	public BusinessUnion() {
	}

	public Long getBusiness_union_id() {
		return business_union_id;
	}

	public void setBusiness_union_id(Long business_union_id) {
		this.business_union_id = business_union_id;
	}

	public String getBusiness_union_name_eng() {
		return business_union_name_eng;
	}

	public void setBusiness_union_name_eng(String business_union_name_eng) {
		this.business_union_name_eng = business_union_name_eng;
	}

	public String getBusiness_union_name_geo() {
		return business_union_name_geo;
	}

	public void setBusiness_union_name_geo(String business_union_name_geo) {
		this.business_union_name_geo = business_union_name_geo;
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
}