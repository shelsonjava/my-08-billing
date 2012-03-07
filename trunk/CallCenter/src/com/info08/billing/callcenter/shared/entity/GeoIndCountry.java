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
 * The persistent class for the GEO_IND_COUNTRY database table.
 * 
 */
@Entity
@Table(name="GEO_IND_COUNTRY",schema="info")
public class GeoIndCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GEO_IND_COUNTRY_GEOCOUNTRYID_GENERATOR", sequenceName="geo_country_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GEO_IND_COUNTRY_GEOCOUNTRYID_GENERATOR")
	@Column(name="GEO_COUNTRY_ID")
	private Long geo_country_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="GEO_COUNTRY_ENG")
	private String geo_country_eng;

	@Basic
	@Column(name="GEO_COUNTRY_GEO")
	private String geo_country_geo;

	@Basic
	@Column(name="GEO_INDEX")
	private String geo_index;

	@Basic
	@Column(name="IS_CENTER")
	private Long is_center;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="REGION_ID")
	private Long region_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String loggedUserName;
	
	@Transient
	private String regionName;
	
	@Transient
	private String isCenterDescr;


    public GeoIndCountry() {
    }


	public Long getGeo_country_id() {
		return geo_country_id;
	}


	public void setGeo_country_id(Long geo_country_id) {
		this.geo_country_id = geo_country_id;
	}


	public Long getDeleted() {
		return deleted;
	}


	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}


	public String getGeo_country_eng() {
		return geo_country_eng;
	}


	public void setGeo_country_eng(String geo_country_eng) {
		this.geo_country_eng = geo_country_eng;
	}


	public String getGeo_country_geo() {
		return geo_country_geo;
	}


	public void setGeo_country_geo(String geo_country_geo) {
		this.geo_country_geo = geo_country_geo;
	}


	public String getGeo_index() {
		return geo_index;
	}


	public void setGeo_index(String geo_index) {
		this.geo_index = geo_index;
	}


	public Long getIs_center() {
		return is_center;
	}


	public void setIs_center(Long is_center) {
		this.is_center = is_center;
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


	public Long getRegion_id() {
		return region_id;
	}


	public void setRegion_id(Long region_id) {
		this.region_id = region_id;
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


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	public String getIsCenterDescr() {
		return isCenterDescr;
	}


	public void setIsCenterDescr(String isCenterDescr) {
		this.isCenterDescr = isCenterDescr;
	}
}