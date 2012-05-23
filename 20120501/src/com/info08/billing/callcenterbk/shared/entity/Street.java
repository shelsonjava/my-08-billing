package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;
import java.util.Map;

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
 * The persistent class for the STREETS database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = "Street.getAllActive", query = "select e from Street e") })
@Entity
@Table(name = "STREETS")
public class Street implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "STREETS_STREETID_GENERATOR", sequenceName = "street_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STREETS_STREETID_GENERATOR")
	@Column(name = "STREET_ID")
	private Long street_id;

	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_1")
	private Long descr_id_level_1;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_2")
	private Long descr_id_level_2;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_3")
	private Long descr_id_level_3;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_4")
	private Long descr_id_level_4;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_5")
	private Long descr_id_level_5;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_6")
	private Long descr_id_level_6;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_7")
	private Long descr_id_level_7;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_8")
	private Long descr_id_level_8;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_9")
	private Long descr_id_level_9;

	@Basic
	@Column(name = "DESCR_ID_LEVEL_10")
	private Long descr_id_level_10;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_1")
	private Long descr_type_id_level_1;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_2")
	private Long descr_type_id_level_2;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_3")
	private Long descr_type_id_level_3;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_4")
	private Long descr_type_id_level_4;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_5")
	private Long descr_type_id_level_5;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_6")
	private Long descr_type_id_level_6;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_7")
	private Long descr_type_id_level_7;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_8")
	private Long descr_type_id_level_8;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_9")
	private Long descr_type_id_level_9;

	@Basic
	@Column(name = "DESCR_TYPE_ID_LEVEL_10")
	private Long descr_type_id_level_10;

	@Basic
	@Column(name = "RECORD_TYPE")
	private Long record_type;

	@Basic
	@Column(name = "STREET_LOCATION")
	private String street_location;

	@Basic
	@Column(name = "STREET_NAME")
	private String street_name;

	@Basic
	@Column(name = "STREET_REMARK")
	private String street_remark;

	@Basic
	@Column(name = "VISIBLE_OPTIONS")
	private Long visible_options;

	@Transient
	private String loggedUserName;

	@Transient
	private Map<String, String> mapStreDistricts;

	@Transient
	private String town_name;

	public Street() {
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public Long getDescr_id_level_1() {
		return descr_id_level_1;
	}

	public void setDescr_id_level_1(Long descr_id_level_1) {
		this.descr_id_level_1 = descr_id_level_1;
	}

	public Long getDescr_id_level_10() {
		return descr_id_level_10;
	}

	public void setDescr_id_level_10(Long descr_id_level_10) {
		this.descr_id_level_10 = descr_id_level_10;
	}

	public Long getDescr_id_level_2() {
		return descr_id_level_2;
	}

	public void setDescr_id_level_2(Long descr_id_level_2) {
		this.descr_id_level_2 = descr_id_level_2;
	}

	public Long getDescr_id_level_3() {
		return descr_id_level_3;
	}

	public void setDescr_id_level_3(Long descr_id_level_3) {
		this.descr_id_level_3 = descr_id_level_3;
	}

	public Long getDescr_id_level_4() {
		return descr_id_level_4;
	}

	public void setDescr_id_level_4(Long descr_id_level_4) {
		this.descr_id_level_4 = descr_id_level_4;
	}

	public Long getDescr_id_level_5() {
		return descr_id_level_5;
	}

	public void setDescr_id_level_5(Long descr_id_level_5) {
		this.descr_id_level_5 = descr_id_level_5;
	}

	public Long getDescr_id_level_6() {
		return descr_id_level_6;
	}

	public void setDescr_id_level_6(Long descr_id_level_6) {
		this.descr_id_level_6 = descr_id_level_6;
	}

	public Long getDescr_id_level_7() {
		return descr_id_level_7;
	}

	public void setDescr_id_level_7(Long descr_id_level_7) {
		this.descr_id_level_7 = descr_id_level_7;
	}

	public Long getDescr_id_level_8() {
		return descr_id_level_8;
	}

	public void setDescr_id_level_8(Long descr_id_level_8) {
		this.descr_id_level_8 = descr_id_level_8;
	}

	public Long getDescr_id_level_9() {
		return descr_id_level_9;
	}

	public void setDescr_id_level_9(Long descr_id_level_9) {
		this.descr_id_level_9 = descr_id_level_9;
	}

	public Long getDescr_type_id_level_1() {
		return descr_type_id_level_1;
	}

	public void setDescr_type_id_level_1(Long descr_type_id_level_1) {
		this.descr_type_id_level_1 = descr_type_id_level_1;
	}

	public Long getDescr_type_id_level_10() {
		return descr_type_id_level_10;
	}

	public void setDescr_type_id_level_10(Long descr_type_id_level_10) {
		this.descr_type_id_level_10 = descr_type_id_level_10;
	}

	public Long getDescr_type_id_level_2() {
		return descr_type_id_level_2;
	}

	public void setDescr_type_id_level_2(Long descr_type_id_level_2) {
		this.descr_type_id_level_2 = descr_type_id_level_2;
	}

	public Long getDescr_type_id_level_3() {
		return descr_type_id_level_3;
	}

	public void setDescr_type_id_level_3(Long descr_type_id_level_3) {
		this.descr_type_id_level_3 = descr_type_id_level_3;
	}

	public Long getDescr_type_id_level_4() {
		return descr_type_id_level_4;
	}

	public void setDescr_type_id_level_4(Long descr_type_id_level_4) {
		this.descr_type_id_level_4 = descr_type_id_level_4;
	}

	public Long getDescr_type_id_level_5() {
		return descr_type_id_level_5;
	}

	public void setDescr_type_id_level_5(Long descr_type_id_level_5) {
		this.descr_type_id_level_5 = descr_type_id_level_5;
	}

	public Long getDescr_type_id_level_6() {
		return descr_type_id_level_6;
	}

	public void setDescr_type_id_level_6(Long descr_type_id_level_6) {
		this.descr_type_id_level_6 = descr_type_id_level_6;
	}

	public Long getDescr_type_id_level_7() {
		return descr_type_id_level_7;
	}

	public void setDescr_type_id_level_7(Long descr_type_id_level_7) {
		this.descr_type_id_level_7 = descr_type_id_level_7;
	}

	public Long getDescr_type_id_level_8() {
		return descr_type_id_level_8;
	}

	public void setDescr_type_id_level_8(Long descr_type_id_level_8) {
		this.descr_type_id_level_8 = descr_type_id_level_8;
	}

	public Long getDescr_type_id_level_9() {
		return descr_type_id_level_9;
	}

	public void setDescr_type_id_level_9(Long descr_type_id_level_9) {
		this.descr_type_id_level_9 = descr_type_id_level_9;
	}

	public void setRecord_type(Long record_type) {
		this.record_type = record_type;
	}

	public Long getVisible_options() {
		return visible_options;
	}

	public void setVisible_options(Long visible_options) {
		this.visible_options = visible_options;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Map<String, String> getMapStreDistricts() {
		return mapStreDistricts;
	}

	public void setMapStreDistricts(Map<String, String> mapStreDistricts) {
		this.mapStreDistricts = mapStreDistricts;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public String getStreet_location() {
		return street_location;
	}

	public void setStreet_location(String street_location) {
		this.street_location = street_location;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getStreet_remark() {
		return street_remark;
	}

	public void setStreet_remark(String street_remark) {
		this.street_remark = street_remark;
	}

	public Long getRecord_type() {
		return record_type;
	}
}