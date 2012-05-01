package com.info08.billing.callcenterbk.shared.entity.ent;

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
 * The persistent class for the ENT_POSTERS database table.
 * 
 */
@Entity
@Table(name="ENT_POSTERS", schema="PAATA")
public class EntPoster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ENT_POSTERS_ENTPOSTERID_GENERATOR", sequenceName="ENT_POSTER_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENT_POSTERS_ENTPOSTERID_GENERATOR")
	@Column(name="ENT_POSTER_ID")
	private Long ent_poster_id;

	@Basic	
	@Column(name="COMMENT_ENG")
	private String comment_eng;

	@Basic
	@Column(name="COMMENT_GEO")
	private String comment_geo;

	@Basic	
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="DT_CRIT")
	private Long dt_crit;

	@Basic
	@Column(name="DT_VIEW_CRIT")
	private Long dt_view_crit;

	@Basic
	@Column(name="ENT_PLACE_ID")
	private Long ent_place_id;

	@Basic
	@Column(name="ENT_POSTER_ENG")
	private String ent_poster_eng;

	@Basic
	@Column(name="ENT_POSTER_GEO")
	private String ent_poster_geo;

	@Basic
	@Column(name="POSTER_DATE")
	private Timestamp poster_date;

	@Basic
	@Column(name="POSTER_PRICE_ENG")
	private String poster_price_eng;

	@Basic
	@Column(name="POSTER_PRICE_GEO")
	private String poster_price_geo;

	@Basic
	@Column(name="POSTER_TIME")
	private String poster_time;

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
	@Column(name="SMS_COMMENT")
	private String sms_comment;

	@Transient
    private String loggedUserName;
	
	@Transient
	private String ent_place_geo;
	
	@Transient
	private Long ent_type_id;
	
	
    public EntPoster() {
    }

	public Long getEnt_poster_id() {
		return ent_poster_id;
	}

	public void setEnt_poster_id(Long ent_poster_id) {
		this.ent_poster_id = ent_poster_id;
	}

	public String getComment_eng() {
		return comment_eng;
	}

	public void setComment_eng(String comment_eng) {
		this.comment_eng = comment_eng;
	}

	public String getComment_geo() {
		return comment_geo;
	}

	public void setComment_geo(String comment_geo) {
		this.comment_geo = comment_geo;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getDt_crit() {
		return dt_crit;
	}

	public void setDt_crit(Long dt_crit) {
		this.dt_crit = dt_crit;
	}

	public Long getDt_view_crit() {
		return dt_view_crit;
	}

	public void setDt_view_crit(Long dt_view_crit) {
		this.dt_view_crit = dt_view_crit;
	}

	public Long getEnt_place_id() {
		return ent_place_id;
	}

	public void setEnt_place_id(Long ent_place_id) {
		this.ent_place_id = ent_place_id;
	}

	public String getEnt_poster_eng() {
		return ent_poster_eng;
	}

	public void setEnt_poster_eng(String ent_poster_eng) {
		this.ent_poster_eng = ent_poster_eng;
	}

	public String getEnt_poster_geo() {
		return ent_poster_geo;
	}

	public void setEnt_poster_geo(String ent_poster_geo) {
		this.ent_poster_geo = ent_poster_geo;
	}

	public Timestamp getPoster_date() {
		return poster_date;
	}

	public void setPoster_date(Timestamp poster_date) {
		this.poster_date = poster_date;
	}

	public String getPoster_price_eng() {
		return poster_price_eng;
	}

	public void setPoster_price_eng(String poster_price_eng) {
		this.poster_price_eng = poster_price_eng;
	}

	public String getPoster_price_geo() {
		return poster_price_geo;
	}

	public void setPoster_price_geo(String poster_price_geo) {
		this.poster_price_geo = poster_price_geo;
	}

	public String getPoster_time() {
		return poster_time;
	}

	public void setPoster_time(String poster_time) {
		this.poster_time = poster_time;
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

	public String getEnt_place_geo() {
		return ent_place_geo;
	}

	public void setEnt_place_geo(String ent_place_geo) {
		this.ent_place_geo = ent_place_geo;
	}

	public String getSms_comment() {
		return sms_comment;
	}

	public void setSms_comment(String sms_comment) {
		this.sms_comment = sms_comment;
	}

	public Long getEnt_type_id() {
		return ent_type_id;
	}

	public void setEnt_type_id(Long ent_type_id) {
		this.ent_type_id = ent_type_id;
	}
}