package com.info08.billing.callcenter.shared.entity.currency;

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
 * The persistent class for the RATE_CURR database table.
 * 
 */
@Entity
@Table(name="RATE_CURR",schema="INFO")
public class RateCurr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RATE_CURR_CURRID_GENERATOR", sequenceName="CURR_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RATE_CURR_CURRID_GENERATOR")
	@Column(name="CURR_ID")
	private Long curr_id;

	@Basic	
	@Column(name="COUNTRY_ID")
	private Long country_id;

	@Basic
	@Column(name="CURR_ABBREV")
	private String curr_abbrev;

	@Basic
	@Column(name="CURR_NAME_ENG")
	private String curr_name_eng;

	@Basic
	@Column(name="CURR_NAME_GEO")
	private String curr_name_geo;

	@Basic
	@Column(name="CURR_ORDER")
	private Long curr_order;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

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
	
	@Basic
	@Column(name="CUSTOM_ORDER")
	private Long custom_order;
	
	@Transient
	private String country_name_geo;
	
	@Transient
    private String loggedUserName;

    public RateCurr() {
    }

	public Long getCurr_id() {
		return curr_id;
	}

	public void setCurr_id(Long curr_id) {
		this.curr_id = curr_id;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public String getCurr_abbrev() {
		return curr_abbrev;
	}

	public void setCurr_abbrev(String curr_abbrev) {
		this.curr_abbrev = curr_abbrev;
	}

	public String getCurr_name_eng() {
		return curr_name_eng;
	}

	public void setCurr_name_eng(String curr_name_eng) {
		this.curr_name_eng = curr_name_eng;
	}

	public String getCurr_name_geo() {
		return curr_name_geo;
	}

	public void setCurr_name_geo(String curr_name_geo) {
		this.curr_name_geo = curr_name_geo;
	}

	public Long getCurr_order() {
		return curr_order;
	}

	public void setCurr_order(Long curr_order) {
		this.curr_order = curr_order;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
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

	public String getCountry_name_geo() {
		return country_name_geo;
	}

	public void setCountry_name_geo(String country_name_geo) {
		this.country_name_geo = country_name_geo;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Long getCustom_order() {
		return custom_order;
	}

	public void setCustom_order(Long custom_order) {
		this.custom_order = custom_order;
	}
}