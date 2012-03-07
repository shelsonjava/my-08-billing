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


/**
 * The persistent class for the STREET_DISTRICT database table.
 * 
 */
@Entity
@Table(name="STREET_DISTRICT",schema="info")
public class StreetDistrict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STREET_DISTRICT_STREETDISTRICTID_GENERATOR", sequenceName="street_district_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREET_DISTRICT_STREETDISTRICTID_GENERATOR")
	@Column(name="STREET_DISTRICT_ID")
	private Long street_district_id;

	@Basic	
	@Column(name="CITY_ID")
	private Long city_id;

	@Basic
	@Column(name="CITY_REGION_ID")
	private Long city_region_id;

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
	@Column(name="STREET_ID")
	private Long street_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

    public StreetDistrict() {
    }

	public Long getStreet_district_id() {
		return street_district_id;
	}

	public void setStreet_district_id(Long street_district_id) {
		this.street_district_id = street_district_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getCity_region_id() {
		return city_region_id;
	}

	public void setCity_region_id(Long city_region_id) {
		this.city_region_id = city_region_id;
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

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}
}