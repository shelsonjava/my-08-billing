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
	@SequenceGenerator(name = "SEQ_STREET_ID_GENERATOR", sequenceName = "SEQ_STREET_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STREET_ID_GENERATOR")
	@Column(name = "STREETS_ID")
	private Long streets_id;

	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;

	@Basic
	@Column(name = "LEVEL_I")
	private Long level_I;

	@Basic
	@Column(name = "LEVEL_II")
	private Long level_II;

	@Basic
	@Column(name = "LEVEL_III")
	private Long level_III;

	@Basic
	@Column(name = "LEVEL_IV")
	private Long level_IV;

	@Basic
	@Column(name = "LEVEL_V")
	private Long level_V;

	@Basic
	@Column(name = "TYPE_LEVEL_I")
	private Long type_level_I;

	@Basic
	@Column(name = "TYPE_LEVEL_II")
	private Long type_level_II;

	@Basic
	@Column(name = "TYPE_LEVEL_III")
	private Long type_level_III;

	@Basic
	@Column(name = "TYPE_LEVEL_IV")
	private Long type_level_IV;

	@Basic
	@Column(name = "TYPE_LEVEL_V")
	private Long type_level_V;

	@Basic
	@Column(name = "REC_KIND")
	private Long rec_kind;

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
	@Column(name = "IS_VISIBLE")
	private Long is_visible;

	@Basic
	@Column(name = "HIDE_FOR_CALL_CENTER")
	private Long hide_for_call_center;

	@Basic
	@Column(name = "HIDE_FOR_CORRECTION")
	private Long hide_for_correction;

	@Transient
	private String loggedUserName;

	@Transient
	private Map<String, String> mapStreDistricts;

	@Transient
	private String town_name;

	public Street() {
	}

	public Long getStreets_id() {
		return streets_id;
	}

	public void setStreets_id(Long streets_id) {
		this.streets_id = streets_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public Long getLevel_I() {
		return level_I;
	}

	public void setLevel_I(Long level_I) {
		this.level_I = level_I;
	}

	public Long getLevel_II() {
		return level_II;
	}

	public void setLevel_II(Long level_II) {
		this.level_II = level_II;
	}

	public Long getLevel_III() {
		return level_III;
	}

	public void setLevel_III(Long level_III) {
		this.level_III = level_III;
	}

	public Long getLevel_IV() {
		return level_IV;
	}

	public void setLevel_IV(Long level_IV) {
		this.level_IV = level_IV;
	}

	public Long getLevel_V() {
		return level_V;
	}

	public void setLevel_V(Long level_V) {
		this.level_V = level_V;
	}

	public Long getType_level_I() {
		return type_level_I;
	}

	public void setType_level_I(Long type_level_I) {
		this.type_level_I = type_level_I;
	}

	public Long getType_level_II() {
		return type_level_II;
	}

	public void setType_level_II(Long type_level_II) {
		this.type_level_II = type_level_II;
	}

	public Long getType_level_III() {
		return type_level_III;
	}

	public void setType_level_III(Long type_level_III) {
		this.type_level_III = type_level_III;
	}

	public Long getType_level_IV() {
		return type_level_IV;
	}

	public void setType_level_IV(Long type_level_IV) {
		this.type_level_IV = type_level_IV;
	}

	public Long getType_level_V() {
		return type_level_V;
	}

	public void setType_level_V(Long type_level_V) {
		this.type_level_V = type_level_V;
	}

	public Long getRec_kind() {
		return rec_kind;
	}

	public void setRec_kind(Long rec_kind) {
		this.rec_kind = rec_kind;
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

	public Long getIs_visible() {
		return is_visible;
	}

	public void setIs_visible(Long is_visible) {
		this.is_visible = is_visible;
	}

	public Long getHide_for_call_center() {
		return hide_for_call_center;
	}

	public void setHide_for_call_center(Long hide_for_call_center) {
		this.hide_for_call_center = hide_for_call_center;
	}

	public Long getHide_for_correction() {
		return hide_for_correction;
	}

	public void setHide_for_correction(Long hide_for_correction) {
		this.hide_for_correction = hide_for_correction;
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
}