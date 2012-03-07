package com.info08.billing.callcenter.shared.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the CITY_DISTANCES database table.
 * 
 */
@Entity
@Table(name="CITY_DISTANCES",schema="info")
public class CityDistance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CITY_DISTANCES_CITYDISTANCEID_GENERATOR", sequenceName="city_distance_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_DISTANCES_CITYDISTANCEID_GENERATOR")
	@Column(name="CITY_DISTANCE_ID")
	private Long city_distance_id;

	@Basic
	@Column(name="CITY_DISTANCE_ENG")
	private String city_distance_eng;

	@Basic
	@Column(name="CITY_DISTANCE_GEO")
	private String city_distance_geo;

	@Basic
	@Column(name="CITY_DISTANCE_TYPE")
	private Long city_distance_type;

	@Basic
	@Column(name="CITY_ID_END")
	private Long city_id_end;

	@Basic
	@Column(name="CITY_ID_START")
	private Long city_id_start;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="NOTE_ENG")
	private String note_eng;

	@Basic
	@Column(name="NOTE_GEO")
	private String note_geo;

	@Basic
    @Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
    @Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient 
	private String loggedUserName;
	
	@Transient 
	private String cityStart;
	
	@Transient 
	private String cityEnd;

	@Transient 
	private String cityDistTypeDesc;

    public CityDistance() {
    }

	public Long getCity_distance_id() {
		return city_distance_id;
	}

	public void setCity_distance_id(Long city_distance_id) {
		this.city_distance_id = city_distance_id;
	}

	public String getCity_distance_eng() {
		return city_distance_eng;
	}

	public void setCity_distance_eng(String city_distance_eng) {
		this.city_distance_eng = city_distance_eng;
	}

	public String getCity_distance_geo() {
		return city_distance_geo;
	}

	public void setCity_distance_geo(String city_distance_geo) {
		this.city_distance_geo = city_distance_geo;
	}

	public Long getCity_distance_type() {
		return city_distance_type;
	}

	public void setCity_distance_type(Long city_distance_type) {
		this.city_distance_type = city_distance_type;
	}

	public Long getCity_id_end() {
		return city_id_end;
	}

	public void setCity_id_end(Long city_id_end) {
		this.city_id_end = city_id_end;
	}

	public Long getCity_id_start() {
		return city_id_start;
	}

	public void setCity_id_start(Long city_id_start) {
		this.city_id_start = city_id_start;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getNote_eng() {
		return note_eng;
	}

	public void setNote_eng(String note_eng) {
		this.note_eng = note_eng;
	}

	public String getNote_geo() {
		return note_geo;
	}

	public void setNote_geo(String note_geo) {
		this.note_geo = note_geo;
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

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getCityStart() {
		return cityStart;
	}

	public void setCityStart(String cityStart) {
		this.cityStart = cityStart;
	}

	public String getCityEnd() {
		return cityEnd;
	}

	public void setCityEnd(String cityEnd) {
		this.cityEnd = cityEnd;
	}

	public String getCityDistTypeDesc() {
		return cityDistTypeDesc;
	}

	public void setCityDistTypeDesc(String cityDistTypeDesc) {
		this.cityDistTypeDesc = cityDistTypeDesc;
	}
}