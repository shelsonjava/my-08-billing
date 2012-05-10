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
					name = "Country.getAllCountries", 
					query = "select e from Country e where e.deleted = 0 order by e.country_id")

})
@Entity
@Table(name = "countries", schema = "ccare")
public class Country implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COUNTRY_ID")
	@SequenceGenerator(name = "seq_country_gen", sequenceName = "country_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_country_gen")
	private Long country_id;

	@Basic
	@Column(name = "COUNTRY_NAME_GEO")
	private String country_name_geo;

	@Basic
	@Column(name = "COUNTRY_NAME_ENG")
	private String country_name_eng;
	
	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;
	
	@Basic
	@Column(name = "REC_USER")
	private String rec_user;
	
	@Basic
	@Column(name = "DELETED")
	private Long deleted;	
	
	@Basic
	@Column(name = "CONTINENT_ID")
	private Long continent_id;
	
	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;
	
	@Basic
	@Column(name = "COUNTRY_CODE")
	private String country_code;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp upd_date;
	
	@Basic
	@Column(name = "SEASON_ID")
	private Long season_id;
	
	@Transient
	private String continent;
	
	public Country() {
	}

	public Long getCountry_id() {
		return country_id;
	}
	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public String getCountry_name_geo() {
		return country_name_geo;
	}

	public void setCountry_name_geo(String country_name_geo) {
		this.country_name_geo = country_name_geo;
	}

	public String getCountry_name_eng() {
		return country_name_eng;
	}

	public void setCountry_name_eng(String country_name_eng) {
		this.country_name_eng = country_name_eng;
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

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getContinent_id() {
		return continent_id;
	}

	public void setContinent_id(Long continent_id) {
		this.continent_id = continent_id;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public Long getSeason_id() {
		return season_id;
	}

	public void setSeason_id(Long season_id) {
		this.season_id = season_id;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
}
