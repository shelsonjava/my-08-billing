package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the STREET_TO_TOWN_DISTRICTS database table.
 * 
 */
@Entity
@Table(name = "STREET_TO_TOWN_DISTRICTS")
public class StreetToTownDistricts implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_STREET_TO_TOWN_DISTRICTS_GENERATOR", sequenceName = "SEQ_STREET_TO_TOWN_DISTRICTS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STREET_TO_TOWN_DISTRICTS_GENERATOR")
	@Column(name = "STREET_TO_TOWN_DISTRICT_ID")
	private Long street_to_town_district_id;

	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;

	@Basic
	@Column(name = "TOWN_DISTRICT_ID")
	private Long town_district_id;

	@Basic
	@Column(name = "STREET_ID")
	private Long street_id;

	public StreetToTownDistricts() {
	}

	public Long getStreet_to_town_district_id() {
		return street_to_town_district_id;
	}

	public void setStreet_to_town_district_id(Long street_to_town_district_id) {
		this.street_to_town_district_id = street_to_town_district_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public Long getTown_district_id() {
		return town_district_id;
	}

	public void setTown_district_id(Long town_district_id) {
		this.town_district_id = town_district_id;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

}