package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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

@NamedQueries({
	@NamedQuery(
				name="StreetEnt.getAllActive",
				query="select e from StreetEnt e where e.deleted = 0")
})

@Entity
@Table(name="STREETS",schema="ccare")
public class StreetEnt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STREETS_STREETID_GENERATOR", sequenceName="street_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREETS_STREETID_GENERATOR")
	@Column(name="STREET_ID")
	private Long street_id;

	@Basic	
	@Column(name="CITY_ID")
	private Long city_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="DESCR_ID_LEVEL_1")
	private Long descr_id_level_1;

	@Basic
	@Column(name="DESCR_ID_LEVEL_2")
	private Long descr_id_level_2;

	@Basic
	@Column(name="DESCR_ID_LEVEL_3")
	private Long descr_id_level_3;

	@Basic
	@Column(name="DESCR_ID_LEVEL_4")
	private Long descr_id_level_4;

	@Basic
	@Column(name="DESCR_ID_LEVEL_5")
	private Long descr_id_level_5;

	@Basic
	@Column(name="DESCR_ID_LEVEL_6")
	private Long descr_id_level_6;

	@Basic
	@Column(name="DESCR_ID_LEVEL_7")
	private Long descr_id_level_7;

	@Basic
	@Column(name="DESCR_ID_LEVEL_8")
	private Long descr_id_level_8;

	@Basic
	@Column(name="DESCR_ID_LEVEL_9")
	private Long descr_id_level_9;
	
	@Basic
	@Column(name="DESCR_ID_LEVEL_10")
	private Long descr_id_level_10;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_1")
	private Long descr_type_id_level_1;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_2")
	private Long descr_type_id_level_2;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_3")
	private Long descr_type_id_level_3;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_4")
	private Long descr_type_id_level_4;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_5")
	private Long descr_type_id_level_5;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_6")
	private Long descr_type_id_level_6;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_7")
	private Long descr_type_id_level_7;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_8")
	private Long descr_type_id_level_8;

	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_9")
	private Long descr_type_id_level_9;
	
	@Basic
	@Column(name="DESCR_TYPE_ID_LEVEL_10")
	private Long descr_type_id_level_10;

	@Basic
	@Column(name="MAP_ID")
	private Long map_id;

	@Basic    
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="RECORD_TYPE")
	private Long record_type;

	@Basic
	@Column(name="STREET_LOCATION_ENG")
	private String street_location_eng;

	@Basic
	@Column(name="STREET_LOCATION_GEO")
	private String street_location_geo;

	@Basic
	@Column(name="STREET_NAME_ENG")
	private String street_name_eng;

	@Basic
	@Column(name="STREET_NAME_GEO")
	private String street_name_geo;

	@Basic
	@Column(name="STREET_NOTE_ENG")
	private String street_note_eng;

	@Basic
	@Column(name="STREET_NOTE_GEO")
	private String street_note_geo;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

	@Basic
	@Column(name="VISIBLE_OPTIONS")
	private Long visible_options;
	
	@Transient
	private String loggedUserName;
	
	@Transient
	private Map<String,String> mapStreDistricts;
	
	@Transient
	private String city_name_geo;

    public StreetEnt() {
    }

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
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

	public Long getMap_id() {
		return map_id;
	}

	public void setMap_id(Long map_id) {
		this.map_id = map_id;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public Long getRecord_type() {
		return record_type;
	}

	public void setRecord_type(Long record_type) {
		this.record_type = record_type;
	}

	public String getStreet_location_eng() {
		return street_location_eng;
	}

	public void setStreet_location_eng(String street_location_eng) {
		this.street_location_eng = street_location_eng;
	}

	public String getStreet_location_geo() {
		return street_location_geo;
	}

	public void setStreet_location_geo(String street_location_geo) {
		this.street_location_geo = street_location_geo;
	}

	public String getStreet_name_eng() {
		return street_name_eng;
	}

	public void setStreet_name_eng(String street_name_eng) {
		this.street_name_eng = street_name_eng;
	}

	public String getStreet_name_geo() {
		return street_name_geo;
	}

	public void setStreet_name_geo(String street_name_geo) {
		this.street_name_geo = street_name_geo;
	}

	public String getStreet_note_eng() {
		return street_note_eng;
	}

	public void setStreet_note_eng(String street_note_eng) {
		this.street_note_eng = street_note_eng;
	}

	public String getStreet_note_geo() {
		return street_note_geo;
	}

	public void setStreet_note_geo(String street_note_geo) {
		this.street_note_geo = street_note_geo;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
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

	public String getCity_name_geo() {
		return city_name_geo;
	}

	public void setCity_name_geo(String city_name_geo) {
		this.city_name_geo = city_name_geo;
	}	
}