package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;

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
 * The persistent class for the STREET_KINDS database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = "StreetKind.getAllActive", query = "select e from StreetKind e") })
@Entity
@Table(name = "STREET_KINDS")
public class StreetKind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_STREET_KINDS_GENERATOR", sequenceName = "SEQ_STREET_KINDS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STREET_KINDS_GENERATOR")
	@Column(name = "STREET_KIND_ID")
	private Long street_kind_id;

	@Column(name = "STREET_KIND_NAME")
	private String street_kind_name;

	@Transient
	private String loggedUserName;

	public StreetKind() {
	}

	public Long getStreet_kind_id() {
		return street_kind_id;
	}

	public void setStreet_kind_id(Long street_kind_id) {
		this.street_kind_id = street_kind_id;
	}

	public String getStreet_kind_name() {
		return street_kind_name;
	}

	public void setStreet_kind_name(String street_kind_name) {
		this.street_kind_name = street_kind_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}