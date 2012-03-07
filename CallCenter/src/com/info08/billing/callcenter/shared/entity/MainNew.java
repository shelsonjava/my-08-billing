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


/**
 * The persistent class for the MAIN_NEWS database table.
 * 
 */
@Entity
@Table(name="MAIN_NEWS", schema="INFO")
public class MainNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MAIN_NEWS_MNID_GENERATOR", sequenceName="MAIN_NEWS_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MAIN_NEWS_MNID_GENERATOR")
	@Column(name="MN_ID")
	private Long mn_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="IS_ACTIVE")
	private Long is_active;

	@Basic
	@Column(name="MN")
	private String mn;

	@Basic
	@Column(name="MN_CRIT")
	private Long mn_crit;

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

    public MainNew() {
    }

	public Long getMn_id() {
		return mn_id;
	}

	public void setMn_id(Long mn_id) {
		this.mn_id = mn_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getIs_active() {
		return is_active;
	}

	public void setIs_active(Long is_active) {
		this.is_active = is_active;
	}

	public String getMn() {
		return mn;
	}

	public void setMn(String mn) {
		this.mn = mn;
	}

	public Long getMn_crit() {
		return mn_crit;
	}

	public void setMn_crit(Long mn_crit) {
		this.mn_crit = mn_crit;
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
}