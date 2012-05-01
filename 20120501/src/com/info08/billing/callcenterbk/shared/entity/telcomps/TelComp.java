package com.info08.billing.callcenterbk.shared.entity.telcomps;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

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
 * The persistent class for the TEL_COMPS database table.
 * 
 */
@Entity
@Table(name = "TEL_COMPS", schema = "INFO")
public class TelComp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TEL_COMPS_TELCOMPID_GENERATOR", sequenceName = "TEL_COMP_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEL_COMPS_TELCOMPID_GENERATOR")
	@Column(name = "TEL_COMP_ID")
	private Long tel_comp_id;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "TEL_COMP_NAME_ENG")
	private String tel_comp_name_eng;

	@Basic
	@Column(name = "TEL_COMP_NAME_GEO")
	private String tel_comp_name_geo;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Basic
	@Column(name = "OUR_PERCENT")
	private Double our_percent;

	@Basic
	@Column(name = "HAS_CALCULATION")
	private Long has_calculation;
	
	@Basic
	@Column(name = "CALL_PRICE")
	private Double call_price;

	@Transient
	private String has_calculation_descr;

	@Transient
	private LinkedHashMap<String, String> telCompIdexes;

	@Transient
	private String loggedUserName;

	public TelComp() {
	}

	public Long getTel_comp_id() {
		return tel_comp_id;
	}

	public void setTel_comp_id(Long tel_comp_id) {
		this.tel_comp_id = tel_comp_id;
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

	public String getTel_comp_name_eng() {
		return tel_comp_name_eng;
	}

	public void setTel_comp_name_eng(String tel_comp_name_eng) {
		this.tel_comp_name_eng = tel_comp_name_eng;
	}

	public String getTel_comp_name_geo() {
		return tel_comp_name_geo;
	}

	public void setTel_comp_name_geo(String tel_comp_name_geo) {
		this.tel_comp_name_geo = tel_comp_name_geo;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public LinkedHashMap<String, String> getTelCompIdexes() {
		return telCompIdexes;
	}

	public void setTelCompIdexes(LinkedHashMap<String, String> telCompIdexes) {
		this.telCompIdexes = telCompIdexes;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Double getOur_percent() {
		return our_percent;
	}

	public void setOur_percent(Double our_percent) {
		this.our_percent = our_percent;
	}

	public Long getHas_calculation() {
		return has_calculation;
	}

	public void setHas_calculation(Long has_calculation) {
		this.has_calculation = has_calculation;
	}

	public String getHas_calculation_descr() {
		return has_calculation_descr;
	}

	public void setHas_calculation_descr(String has_calculation_descr) {
		this.has_calculation_descr = has_calculation_descr;
	}

	public Double getCall_price() {
		return call_price;
	}

	public void setCall_price(Double call_price) {
		this.call_price = call_price;
	}
}