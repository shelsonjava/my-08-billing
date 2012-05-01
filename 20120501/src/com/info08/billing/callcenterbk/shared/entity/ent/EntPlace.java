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
 * The persistent class for the ENT_PLACES database table.
 * 
 */
@Entity
@Table(name="ENT_PLACES", schema="PAATA")
public class EntPlace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ENT_PLACES_ENTPLACEID_GENERATOR", sequenceName="ENT_PLACE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENT_PLACES_ENTPLACEID_GENERATOR")
	@Column(name="ENT_PLACE_ID")
	private Long ent_place_id;

	@Basic	
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="ENT_PLACE_ENG")
	private String ent_place_eng;

	@Basic
	@Column(name="ENT_PLACE_GEO")
	private String ent_place_geo;

	@Basic
	@Column(name="ENT_TYPE_ID")
	private Long ent_type_id;

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
	@Column(name="RESERVATION")
	private Long reservation;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
    private String loggedUserName;
	
	@Transient
    private String ent_type_geo;
	
	@Transient
    private String org_name;

    public EntPlace() {
    }

	public Long getEnt_place_id() {
		return ent_place_id;
	}

	public void setEnt_place_id(Long ent_place_id) {
		this.ent_place_id = ent_place_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getEnt_place_eng() {
		return ent_place_eng;
	}

	public void setEnt_place_eng(String ent_place_eng) {
		this.ent_place_eng = ent_place_eng;
	}

	public String getEnt_place_geo() {
		return ent_place_geo;
	}

	public void setEnt_place_geo(String ent_place_geo) {
		this.ent_place_geo = ent_place_geo;
	}

	public Long getEnt_type_id() {
		return ent_type_id;
	}

	public void setEnt_type_id(Long ent_type_id) {
		this.ent_type_id = ent_type_id;
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

	public Long getReservation() {
		return reservation;
	}

	public void setReservation(Long reservation) {
		this.reservation = reservation;
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

	public String getEnt_type_geo() {
		return ent_type_geo;
	}

	public void setEnt_type_geo(String ent_type_geo) {
		this.ent_type_geo = ent_type_geo;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
}