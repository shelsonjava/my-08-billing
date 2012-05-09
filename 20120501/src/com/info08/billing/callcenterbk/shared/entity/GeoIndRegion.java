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
import javax.persistence.Transient;


/**
 * The persistent class for the GEO_IND_REGION database table.
 * 
 */
@Entity
@Table(name="GEO_IND_REGION",schema="ccare")
public class GeoIndRegion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GEO_IND_REGION_REGIONID_GENERATOR", sequenceName="geo_region_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GEO_IND_REGION_REGIONID_GENERATOR")
	@Column(name="REGION_ID")
	private Long region_id;

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
	@Column(name="REGION_NAME_ENG")
	private String region_name_eng;

	@Basic
	@Column(name="REGION_NAME_GEO")
	private String region_name_geo;

	@Basic
	@Column(name="SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String loggedUserName;
	
	@Transient
	private String serviceName;

    public GeoIndRegion() {
    }

	public Long getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Long region_id) {
		this.region_id = region_id;
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

	public String getRegion_name_eng() {
		return region_name_eng;
	}

	public void setRegion_name_eng(String region_name_eng) {
		this.region_name_eng = region_name_eng;
	}

	public String getRegion_name_geo() {
		return region_name_geo;
	}

	public void setRegion_name_geo(String region_name_geo) {
		this.region_name_geo = region_name_geo;
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

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}