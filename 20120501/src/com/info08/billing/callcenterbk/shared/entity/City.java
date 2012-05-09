package com.info08.billing.callcenterbk.shared.entity;

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

@NamedQueries({ 
		@NamedQuery(
					name = "City.getAllCities", 
					query = "select e from City e where e.deleted = 0 order by e.city_id")
})
@Entity
@Table(name = "cities", schema = "ccare")
public class City implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CITY_ID")
	@SequenceGenerator(name = "seq_city_gen", sequenceName = "city_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city_gen")
	private Long city_id;

	@Basic
	@Column(name = "CITY_NAME_GEO")
	private String city_name_geo;

	@Basic
	@Column(name = "CITY_NAME_ENG")
	private String city_name_eng;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "COUNTRY_ID")
	private Long country_id;

	@Basic
	@Column(name = "MAP_ID")
	private Long map_id;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "CITY_TYPE_ID")
	private Long city_type_id;

	@Basic
	@Column(name = "COUNTRY_REGION_ID")
	private Long country_region_id;

	@Basic
	@Column(name = "OF_GMT")
	private Long of_gmt;

	@Basic
	@Column(name = "OF_GMT_WINTER")
	private Long of_gmt_winter;

	@Basic
	@Column(name = "IS_CAPITAL")
	private Long is_capital;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Basic
	@Column(name = "CITY_CODE")
	private String city_code;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name = "CITY_NEW_CODE")
	private String city_new_code;

	@Transient
	private String countryName;

	@Transient
	private String cityType;
	
	@Transient
	private String isCapitalText;
	
	public City() {
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getCity_name_geo() {
		return city_name_geo;
	}

	public void setCity_name_geo(String city_name_geo) {
		this.city_name_geo = city_name_geo;
	}

	public String getCity_name_eng() {
		return city_name_eng;
	}

	public void setCity_name_eng(String city_name_eng) {
		this.city_name_eng = city_name_eng;
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

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public Long getMap_id() {
		return map_id;
	}

	public void setMap_id(Long map_id) {
		this.map_id = map_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getCity_type_id() {
		return city_type_id;
	}

	public void setCity_type_id(Long city_type_id) {
		this.city_type_id = city_type_id;
	}

	public Long getCountry_region_id() {
		return country_region_id;
	}

	public void setCountry_region_id(Long country_region_id) {
		this.country_region_id = country_region_id;
	}

	public Long getOf_gmt() {
		return of_gmt;
	}

	public void setOf_gmt(Long of_gmt) {
		this.of_gmt = of_gmt;
	}

	public Long getOf_gmt_winter() {
		return of_gmt_winter;
	}

	public void setOf_gmt_winter(Long of_gmt_winter) {
		this.of_gmt_winter = of_gmt_winter;
	}

	public Long getIs_capital() {
		return is_capital;
	}

	public void setIs_capital(Long is_capital) {
		this.is_capital = is_capital;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getCity_new_code() {
		return city_new_code;
	}

	public void setCity_new_code(String city_new_code) {
		this.city_new_code = city_new_code;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getIsCapitalText() {
		return isCapitalText;
	}

	public void setIsCapitalText(String isCapitalText) {
		this.isCapitalText = isCapitalText;
	}	
}
