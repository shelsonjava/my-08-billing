package com.info08.billing.callcenterbk.shared.entity.admin;

import java.io.Serializable;

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
 * The persistent class for the CountryIndexes database table.
 * 
 */
@NamedQueries({ @NamedQuery(name = "CountryIndexes.getAllIndexes", query = "select e from CountryIndexes e") })
@Entity
@Table(name = "COUNTRY_INDEXES")
public class CountryIndexes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_COUNTRY_INDEXES_GENERATOR", sequenceName = "SEQ_COUNTRY_INDEXES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUNTRY_INDEXES_GENERATOR")
	private Long country_index_id;

	@Basic
	@Column(name = "COUNTRY_ID")
	private Long country_id;

	@Basic
	@Column(name = "COUNTRY_INDEX_VALUE")
	private String country_index_value;

	@Basic
	@Column(name = "COUNTRY_INDEX_REMARK_GEO")
	private String country_index_remark_geo;

	@Basic
	@Column(name = "COUNTRY_INDEX_REMARK_ENG")
	private String country_index_remark_eng;

	@Transient
	private String country_name;

	@Transient
	private String loggedUserName;

	public CountryIndexes() {
	}

	public Long getCountry_index_id() {
		return country_index_id;
	}

	public void setCountry_index_id(Long country_index_id) {
		this.country_index_id = country_index_id;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public String getCountry_index_value() {
		return country_index_value;
	}

	public void setCountry_index_value(String country_index_value) {
		this.country_index_value = country_index_value;
	}

	public String getCountry_index_remark_geo() {
		return country_index_remark_geo;
	}

	public void setCountry_index_remark_geo(String country_index_remark_geo) {
		this.country_index_remark_geo = country_index_remark_geo;
	}

	public String getCountry_index_remark_eng() {
		return country_index_remark_eng;
	}

	public void setCountry_index_remark_eng(String country_index_remark_eng) {
		this.country_index_remark_eng = country_index_remark_eng;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

}