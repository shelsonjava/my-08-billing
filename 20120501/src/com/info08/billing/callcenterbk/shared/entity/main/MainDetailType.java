package com.info08.billing.callcenterbk.shared.entity.main;

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
 * The persistent class for the MAIN_DETAIL_TYPES database table.
 * 
 */
@Entity
@Table(name="MAIN_DETAIL_TYPES", schema="ccare")
public class MainDetailType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MAIN_DETAIL_TYPES_MAINDETAILTYPEID_GENERATOR", sequenceName="MAIN_DETAIL_TYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MAIN_DETAIL_TYPES_MAINDETAILTYPEID_GENERATOR")
	@Column(name="MAIN_DETAIL_TYPE_ID")
	private Long main_detail_type_id;

	@Basic
	@Column(name="CRITERIA_TYPE")
	private Long criteria_type;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="MAIN_DETAIL_TYPE_NAME_ENG")
	private String main_detail_type_name_eng;

	@Basic
	@Column(name="MAIN_DETAIL_TYPE_NAME_GEO")
	private String main_detail_type_name_geo;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="SEARCHER_ZONE")
	private Long searcher_zone;

	@Basic
	@Column(name="SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

	@Basic
	@Column(name="VISIBLE_OPTION")
	private Long visible_option;
	
	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;
	
	@Transient
    private String loggedUserName;
	
	@Transient
	private String service_name_geo;

    public MainDetailType() {
    }

	public Long getMain_detail_type_id() {
		return main_detail_type_id;
	}

	public void setMain_detail_type_id(Long main_detail_type_id) {
		this.main_detail_type_id = main_detail_type_id;
	}

	public Long getCriteria_type() {
		return criteria_type;
	}

	public void setCriteria_type(Long criteria_type) {
		this.criteria_type = criteria_type;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getMain_detail_type_name_eng() {
		return main_detail_type_name_eng;
	}

	public void setMain_detail_type_name_eng(String main_detail_type_name_eng) {
		this.main_detail_type_name_eng = main_detail_type_name_eng;
	}

	public String getMain_detail_type_name_geo() {
		return main_detail_type_name_geo;
	}

	public void setMain_detail_type_name_geo(String main_detail_type_name_geo) {
		this.main_detail_type_name_geo = main_detail_type_name_geo;
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

	public Long getSearcher_zone() {
		return searcher_zone;
	}

	public void setSearcher_zone(Long searcher_zone) {
		this.searcher_zone = searcher_zone;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Long getVisible_option() {
		return visible_option;
	}

	public void setVisible_option(Long visible_option) {
		this.visible_option = visible_option;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getService_name_geo() {
		return service_name_geo;
	}

	public void setService_name_geo(String service_name_geo) {
		this.service_name_geo = service_name_geo;
	}
}