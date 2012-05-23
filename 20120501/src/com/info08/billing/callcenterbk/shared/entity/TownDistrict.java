package com.info08.billing.callcenterbk.shared.entity;

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
 * The persistent class for the TOWN_DISTRICT database table.
 * 
 */
@NamedQueries({ @NamedQuery(name = "TownDistrict.getAllActive", query = "select e from TownDistrict e order by e.town_district_id") })
@Entity
@Table(name = "TOWN_DISTRICT")
public class TownDistrict implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TOWN_DISTRICT_GENERATOR", sequenceName = "SEQ_TOWN_DISTRICT")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TOWN_DISTRICT_GENERATOR")
	@Column(name = "TOWN_DISTRICT_ID")
	private Long town_district_id;

	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;

	@Basic
	@Column(name = "TOWN_DISTRICT_NAME")
	private String town_district_name;

	@Transient
	private String loggedUserName;

	public TownDistrict() {
	}

	public Long getTown_district_id() {
		return town_district_id;
	}

	public void setTown_district_id(Long town_district_id) {
		this.town_district_id = town_district_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public String getTown_district_name() {
		return town_district_name;
	}

	public void setTown_district_name(String town_district_name) {
		this.town_district_name = town_district_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}