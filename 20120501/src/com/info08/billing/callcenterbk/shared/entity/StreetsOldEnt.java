package com.info08.billing.callcenterbk.shared.entity;

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
 * The persistent class for the STREETS database table.
 * 
 */
@Entity
@Table(name="streets_old",schema="PAATA")
public class StreetsOldEnt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="STREETS_OLD_OLDID_GENERATOR", sequenceName="street_old_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREETS_OLD_OLDID_GENERATOR")
	@Column(name="OLD_ID")
	private Long old_id;

	@Basic	
	@Column(name="CITY_ID")
	private Long city_id;

	@Basic	
	@Column(name="DELETED")
	private Long deleted;

	@Basic
    @Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Column(name="STREET_ID")
	private Long street_id;

	@Column(name="STREET_OLD_NAME_ENG")
	private String street_old_name_eng;

	@Column(name="STREET_OLD_NAME_GEO")
	private String street_old_name_geo;

	@Column(name="UPD_USER")
	private String upd_user;

    public StreetsOldEnt() {
    }

	public Long getOld_id() {
		return old_id;
	}

	public void setOld_id(Long old_id) {
		this.old_id = old_id;
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

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getStreet_old_name_eng() {
		return street_old_name_eng;
	}

	public void setStreet_old_name_eng(String street_old_name_eng) {
		this.street_old_name_eng = street_old_name_eng;
	}

	public String getStreet_old_name_geo() {
		return street_old_name_geo;
	}

	public void setStreet_old_name_geo(String street_old_name_geo) {
		this.street_old_name_geo = street_old_name_geo;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}
}