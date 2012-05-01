package com.info08.billing.callcenter.shared.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the STREET_TYPES database table.
 * 
 */

@NamedQueries({
	@NamedQuery(
				name="StreetType.getAllActive",
				query="select e from StreetType e where e.deleted = 0")
})

@Entity
@Table(name="STREET_TYPES", schema="info")
public class StreetType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STREET_TYPES_STREETTYPEID_GENERATOR", sequenceName="street_type_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREET_TYPES_STREETTYPEID_GENERATOR")
	@Column(name="STREET_TYPE_ID")
	private Long street_type_Id;

	@Column(name="DELETED")
	private Long deleted;

    @Column(name="REC_DATE")
	private Timestamp rec_date;

	@Column(name="REC_USER")
	private String rec_user;

	@Column(name="SEARCHER_ZONE")
	private Long searcher_zone;

	@Column(name="STREET_TYPE_NAME_ENG")
	private String street_type_name_eng;

	@Column(name="STREET_TYPE_NAME_GEO")
	private String street_type_name_geo;

	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String deletedText;
	
	@Transient
	private String loggedUserName;

    public StreetType() {
    }

	public Long getStreet_type_Id() {
		return street_type_Id;
	}

	public void setStreet_type_Id(Long street_type_Id) {
		this.street_type_Id = street_type_Id;
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

	public Long getSearcher_zone() {
		return searcher_zone;
	}

	public void setSearcher_zone(Long searcher_zone) {
		this.searcher_zone = searcher_zone;
	}

	public String getStreet_type_name_eng() {
		return street_type_name_eng;
	}

	public void setStreet_type_name_eng(String street_type_name_eng) {
		this.street_type_name_eng = street_type_name_eng;
	}

	public String getStreet_type_name_geo() {
		return street_type_name_geo;
	}

	public void setStreet_type_name_geo(String street_type_name_geo) {
		this.street_type_name_geo = street_type_name_geo;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getDeletedText() {
		return deletedText;
	}

	public void setDeletedText(String deletedText) {
		this.deletedText = deletedText;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}