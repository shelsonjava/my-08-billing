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
 * The persistent class for the DISTRICT_INDEXES database table.
 * 
 */
@Entity
@Table(name = "DISTRICT_INDEXES", schema = "ccare")
public class DistrictIndexes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_DISTRICT_INDEXES_GENERATOR", sequenceName = "SEQ_DISTRICT_INDEXES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISTRICT_INDEXES_GENERATOR")
	@Column(name = "DISTRICT_INDEX_ID")
	private Long district_index_id;

	@Basic
	@Column(name = "DISTRICT_INDEX_NAME")
	private String district_index_name;

	@Transient
	private String loggedUserName;

	public DistrictIndexes() {
	}

	public Long getDistrict_index_id() {
		return district_index_id;
	}

	public void setDistrict_index_id(Long district_index_id) {
		this.district_index_id = district_index_id;
	}

	public String getDistrict_index_name() {
		return district_index_name;
	}

	public void setDistrict_index_name(String district_index_name) {
		this.district_index_name = district_index_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}