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
 * The persistent class for the GEO_IND_COUNTRY database table.
 * 
 */
@Entity
@Table(name = "VILLAGE_INDEXES")
public class VillageIndexes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_VILLAGE_INDEXES_GENERATOR", sequenceName = "SEQ_VILLAGE_INDEXES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VILLAGE_INDEXES_GENERATOR")
	@Column(name = "VILLAGE_INDEX_ID")
	private Long village_index_id;

	@Basic
	@Column(name = "DISTRICT_INDEX_ID")
	private Long district_index_id;

	@Basic
	@Column(name = "VILLAGE_INDEX_NAME")
	private String village_index_name;

	@Basic
	@Column(name = "VILLAGE_INDEX")
	private String village_index;

	@Basic
	@Column(name = "DISTRICT_CENTER")
	private Long district_center;

	@Transient
	private String loggedUserName;

	@Transient
	private String district_index_name;

	@Transient
	private String district_center_descr;

	public VillageIndexes() {
	}

	public Long getVillage_index_id() {
		return village_index_id;
	}

	public void setVillage_index_id(Long village_index_id) {
		this.village_index_id = village_index_id;
	}

	public Long getDistrict_index_id() {
		return district_index_id;
	}

	public void setDistrict_index_id(Long district_index_id) {
		this.district_index_id = district_index_id;
	}

	public String getVillage_index_name() {
		return village_index_name;
	}

	public void setVillage_index_name(String village_index_name) {
		this.village_index_name = village_index_name;
	}

	public String getVillage_index() {
		return village_index;
	}

	public void setVillage_index(String village_index) {
		this.village_index = village_index;
	}

	public Long getDistrict_center() {
		return district_center;
	}

	public void setDistrict_center(Long district_center) {
		this.district_center = district_center;
	}

	public String getDistrict_index_name() {
		return district_index_name;
	}

	public void setDistrict_index_name(String district_index_name) {
		this.district_index_name = district_index_name;
	}

	public String getDistrict_center_descr() {
		return district_center_descr;
	}

	public void setDistrict_center_descr(String district_center_descr) {
		this.district_center_descr = district_center_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}