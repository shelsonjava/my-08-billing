package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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


/**
 * The persistent class for the STREET_DESCR database table.
 * 
 */

@NamedQueries({
	@NamedQuery(
				name="StreetDescr.getAllActive",
				query="select e from StreetDescr e where e.deleted = 0")
})


@Entity
@Table(name="STREET_DESCR",schema="PAATA")
public class StreetDescr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STREET_DESCR_STREETDESCRID_GENERATOR", sequenceName="SEQ_STREET_DESCR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREET_DESCR_STREETDESCRID_GENERATOR")
	@Column(name="STREET_DESCR_ID")
	private Long street_descr_id;

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
	@Column(name="STREET_DESCR_NAME_ENG")
	private String street_descr_name_eng;

	@Basic
	@Column(name="STREET_DESCR_NAME_GEO")
	private String street_descr_name_geo;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

    public StreetDescr() {
    }

	public Long getStreet_descr_id() {
		return street_descr_id;
	}

	public void setStreet_descr_id(Long street_descr_id) {
		this.street_descr_id = street_descr_id;
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

	public String getStreet_descr_name_eng() {
		return street_descr_name_eng;
	}

	public void setStreet_descr_name_eng(String street_descr_name_eng) {
		this.street_descr_name_eng = street_descr_name_eng;
	}

	public String getStreet_descr_name_geo() {
		return street_descr_name_geo;
	}

	public void setStreet_descr_name_geo(String street_descr_name_geo) {
		this.street_descr_name_geo = street_descr_name_geo;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}   
}