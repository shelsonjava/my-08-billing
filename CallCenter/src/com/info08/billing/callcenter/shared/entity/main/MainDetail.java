package com.info08.billing.callcenter.shared.entity.main;

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
 * The persistent class for the MAIN_DETAILS database table.
 * 
 */
@Entity
@Table(name="MAIN_DETAILS", schema="INFO")
public class MainDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MAIN_DETAILS_MAINDETAILID_GENERATOR", sequenceName="MAIN_DETAIL_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MAIN_DETAILS_MAINDETAILID_GENERATOR")
	@Column(name="MAIN_DETAIL_ID")
	private Long main_detail_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="FIELDS_ORDER")
	private Long fields_order;

	@Basic
	@Column(name="MAIN_DETAIL_ENG")
	private String main_detail_eng;

	@Basic
	@Column(name="MAIN_DETAIL_GEO")
	private String main_detail_geo;

	@Basic
	@Column(name="MAIN_DETAIL_MASTER_ID")
	private Long main_detail_master_id;

	@Basic
	@Column(name="MAIN_DETAIL_NOTE_ENG")
	private String main_detail_note_eng;

	@Basic
	@Column(name="MAIN_DETAIL_NOTE_GEO")
	private String main_detail_note_geo;

	@Basic
	@Column(name="MAIN_DETAIL_TYPE_ID")
	private Long main_detail_type_id;

	@Basic
	@Column(name="MAIN_ID")
	private Long main_id;

	@Basic
	@Column(name="OLD_ID")
	private Long old_id;

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
	private String main_detail_type_name_geo;

    public MainDetail() {
    }

	public Long getMain_detail_id() {
		return main_detail_id;
	}

	public void setMain_detail_id(Long main_detail_id) {
		this.main_detail_id = main_detail_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getFields_order() {
		return fields_order;
	}

	public void setFields_order(Long fields_order) {
		this.fields_order = fields_order;
	}

	public String getMain_detail_eng() {
		return main_detail_eng;
	}

	public void setMain_detail_eng(String main_detail_eng) {
		this.main_detail_eng = main_detail_eng;
	}

	public String getMain_detail_geo() {
		return main_detail_geo;
	}

	public void setMain_detail_geo(String main_detail_geo) {
		this.main_detail_geo = main_detail_geo;
	}

	public Long getMain_detail_master_id() {
		return main_detail_master_id;
	}

	public void setMain_detail_master_id(Long main_detail_master_id) {
		this.main_detail_master_id = main_detail_master_id;
	}

	public String getMain_detail_note_eng() {
		return main_detail_note_eng;
	}

	public void setMain_detail_note_eng(String main_detail_note_eng) {
		this.main_detail_note_eng = main_detail_note_eng;
	}

	public String getMain_detail_note_geo() {
		return main_detail_note_geo;
	}

	public void setMain_detail_note_geo(String main_detail_note_geo) {
		this.main_detail_note_geo = main_detail_note_geo;
	}

	public Long getMain_detail_type_id() {
		return main_detail_type_id;
	}

	public void setMain_detail_type_id(Long main_detail_type_id) {
		this.main_detail_type_id = main_detail_type_id;
	}

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
	}

	public Long getOld_id() {
		return old_id;
	}

	public void setOld_id(Long old_id) {
		this.old_id = old_id;
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

	public String getMain_detail_type_name_geo() {
		return main_detail_type_name_geo;
	}

	public void setMain_detail_type_name_geo(String main_detail_type_name_geo) {
		this.main_detail_type_name_geo = main_detail_type_name_geo;
	}
}