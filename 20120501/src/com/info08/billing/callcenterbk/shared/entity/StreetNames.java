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
 * The persistent class for the StreetNames database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = "StreetNames.getAllActive", query = "select e from StreetNames e") })
@Entity
@Table(name = "STREET_NAMES")
public class StreetNames implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_STREET_NAMES_GENERATOR", sequenceName = "SEQ_STREET_NAMES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STREET_NAMES_GENERATOR")
	@Column(name = "STREET_NAME_ID")
	private Long street_name_id;

	@Basic
	@Column(name = "STREET_NAME_DESCR")
	private String street_name_descr;

	@Transient
	private String loggedUserName;

	public StreetNames() {
	}

	public Long getStreet_name_id() {
		return street_name_id;
	}

	public void setStreet_name_id(Long street_name_id) {
		this.street_name_id = street_name_id;
	}

	public String getStreet_name_descr() {
		return street_name_descr;
	}

	public void setStreet_name_descr(String street_name_descr) {
		this.street_name_descr = street_name_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}