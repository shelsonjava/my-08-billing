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

@NamedQueries({ @NamedQuery(name = "Towns.getAllTowns", query = "select e from Towns e order by e.town_id") })
@Entity
@Table(name = "towns")
public class Towns implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TOWN_ID")
	@SequenceGenerator(name = "SEQ_TOWNS_ID_GENERATOR", sequenceName = "SEQ_TOWNS_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TOWNS_ID_GENERATOR")
	private Long town_id;

	@Basic
	@Column(name = "TOWN_NAME")
	private String town_name;

	@Basic
	@Column(name = "COUNTRY_ID")
	private Long country_id;

	@Basic
	@Column(name = "TOWN_TYPE_ID")
	private Long town_type_id;

	@Basic
	@Column(name = "COUNTRY_DISTRICT_ID")
	private Long country_district_id;

	@Basic
	@Column(name = "NORMAL_GMT")
	private Long normal_gmt;

	@Basic
	@Column(name = "WINTER_GMT")
	private Long winter_gmt;

	@Basic
	@Column(name = "CAPITAL_TOWN")
	private Long capital_town;

	@Basic
	@Column(name = "TOWN_CODE")
	private String town_code;

	@Basic
	@Column(name = "TOWN_NEW_CODE")
	private String town_new_code;

	@Transient
	private String country_name;

	@Transient
	private String town_type_name;

	@Transient
	private String capital_town_name;

	@Transient
	private String loggedUserName;

	public Towns() {
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public Long getTown_type_id() {
		return town_type_id;
	}

	public void setTown_type_id(Long town_type_id) {
		this.town_type_id = town_type_id;
	}

	public Long getCountry_district_id() {
		return country_district_id;
	}

	public void setCountry_district_id(Long country_district_id) {
		this.country_district_id = country_district_id;
	}

	public Long getNormal_gmt() {
		return normal_gmt;
	}

	public void setNormal_gmt(Long normal_gmt) {
		this.normal_gmt = normal_gmt;
	}

	public Long getWinter_gmt() {
		return winter_gmt;
	}

	public void setWinter_gmt(Long winter_gmt) {
		this.winter_gmt = winter_gmt;
	}

	public Long getCapital_town() {
		return capital_town;
	}

	public void setCapital_town(Long capital_town) {
		this.capital_town = capital_town;
	}

	public String getTown_code() {
		return town_code;
	}

	public void setTown_code(String town_code) {
		this.town_code = town_code;
	}

	public String getTown_new_code() {
		return town_new_code;
	}

	public void setTown_new_code(String town_new_code) {
		this.town_new_code = town_new_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getTown_type_name() {
		return town_type_name;
	}

	public void setTown_type_name(String town_type_name) {
		this.town_type_name = town_type_name;
	}

	public String getCapital_town_name() {
		return capital_town_name;
	}

	public void setCapital_town_name(String capital_town_name) {
		this.capital_town_name = capital_town_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
