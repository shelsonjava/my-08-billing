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
 * The persistent class for the STREETS database table.
 * 
 */
@Entity
@Table(name = "STREET_OLD_NAMES", schema = "ccare")
public class StreetsOldNames implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "SEQ_STREET_OLD_NAMES_GENERATOR", sequenceName = "SEQ_STREET_OLD_NAMES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STREET_OLD_NAMES_GENERATOR")
	@Column(name = "STREET_OLD_ID")
	private Long street_old_id;

	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;

	@Column(name = "STREET_ID")
	private Long street_id;

	@Column(name = "STREET_OLD_NAME_DESCR")
	private String street_old_name_descr;

	public StreetsOldNames() {
	}

	public Long getStreet_old_id() {
		return street_old_id;
	}

	public void setStreet_old_id(Long street_old_id) {
		this.street_old_id = street_old_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getStreet_old_name_descr() {
		return street_old_name_descr;
	}

	public void setStreet_old_name_descr(String street_old_name_descr) {
		this.street_old_name_descr = street_old_name_descr;
	}

}