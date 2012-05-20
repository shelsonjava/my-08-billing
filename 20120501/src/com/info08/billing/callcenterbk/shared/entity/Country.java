package com.info08.billing.callcenterbk.shared.entity;

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

@NamedQueries({ @NamedQuery(name = "Country.getAllCountries", query = "select e from Country e order by e.country_id")

})
@Entity
@Table(name = "COUNTRIES")
public class Country implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COUNTRY_ID")
	@SequenceGenerator(name = "SEQ_COUNTRIES_GENERATOR", sequenceName = "SEQ_COUNTRIES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUNTRIES_GENERATOR")
	private Long country_id;

	@Basic
	@Column(name = "COUNTRY_NAME")
	private String country_name;

	@Basic
	@Column(name = "CONTINENT_ID")
	private Long continent_id;

	@Basic
	@Column(name = "PHONE_CODE")
	private String phone_code;

	@Basic
	@Column(name = "SEASON")
	private Long season;

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

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public Long getContinent_id() {
		return continent_id;
	}

	public void setContinent_id(Long continent_id) {
		this.continent_id = continent_id;
	}

	public String getPhone_code() {
		return phone_code;
	}

	public void setPhone_code(String phone_code) {
		this.phone_code = phone_code;
	}

	public Long getSeason() {
		return season;
	}

	public void setSeason(Long season) {
		this.season = season;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

}
