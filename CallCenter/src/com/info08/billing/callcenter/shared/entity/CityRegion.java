package com.info08.billing.callcenter.shared.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the CITY_REGIONS database table.
 * 
 */
@NamedQueries({
	@NamedQuery(
				name="CityRegion.getAllActive",
				query="select e from CityRegion e where e.deleted = 0 order by e.city_region_id")
})
@Entity
@Table(name="CITY_REGIONS", schema="info")
public class CityRegion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CITY_REGIONS_CITYREGIONID_GENERATOR", sequenceName="city_region_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_REGIONS_CITYREGIONID_GENERATOR")
	@Column(name="CITY_REGION_ID")
	private Long city_region_id;

	@Basic	
	@Column(name="CITY_ID")
	private Long city_id;

	@Basic
	@Column(name="CITY_REGION_NAME_ENG")
	private String city_region_name_eng;

	@Basic
	@Column(name="CITY_REGION_NAME_GEO")
	private String city_region_name_geo;

	@Basic
	@Column(name="CITY_REGION_TYPE_ID")
	private Long city_region_type_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="MAP_ID")
	private Long map_id;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String loggedUserName;

    public CityRegion() {
    }

	public Long getCity_region_id() {
		return city_region_id;
	}

	public void setCity_region_id(Long city_region_id) {
		this.city_region_id = city_region_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getCity_region_name_eng() {
		return city_region_name_eng;
	}

	public void setCity_region_name_eng(String city_region_name_eng) {
		this.city_region_name_eng = city_region_name_eng;
	}

	public String getCity_region_name_geo() {
		return city_region_name_geo;
	}

	public void setCity_region_name_geo(String city_region_name_geo) {
		this.city_region_name_geo = city_region_name_geo;
	}

	public Long getCity_region_type_id() {
		return city_region_type_id;
	}

	public void setCity_region_type_id(Long city_region_type_id) {
		this.city_region_type_id = city_region_type_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getMap_id() {
		return map_id;
	}

	public void setMap_id(Long map_id) {
		this.map_id = map_id;
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
}