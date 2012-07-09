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
import javax.persistence.Transient;

/**
 * The persistent class for the STREET_INDEX database table.
 * 
 */
@Entity
@Table(name = "STREET_INDEXES")
public class StreetIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_STREET_INDEXES_GENERATOR", sequenceName = "SEQ_STREET_INDEXES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STREET_INDEXES_GENERATOR")
	@Column(name = "STREET_INDEX_ID")
	private Long street_index_id;

	@Basic
	@Column(name = "STREET_INDEX_REMARK")
	private String street_index_remark;

	@Basic
	@Column(name = "STREET_ID")
	private Long street_id;

	@Basic
	@Column(name = "STREET_INDEX_VALUE")
	private String street_index_value;

	@Transient
	private String street_name;

	@Transient
	private String loggedUserName;

	public StreetIndex() {
	}

	public Long getStreet_index_id() {
		return street_index_id;
	}

	public void setStreet_index_id(Long street_index_id) {
		this.street_index_id = street_index_id;
	}

	public String getStreet_index_remark() {
		return street_index_remark;
	}

	public void setStreet_index_remark(String street_index_remark) {
		this.street_index_remark = street_index_remark;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getStreet_index_value() {
		return street_index_value;
	}

	public void setStreet_index_value(String street_index_value) {
		this.street_index_value = street_index_value;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

}