package com.info08.billing.callcenterbk.shared.entity.transport;

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
import javax.persistence.Transient;

/**
 * The persistent class for the TRANSPORT_PLACES database table.
 * 
 */

@NamedQueries({
	@NamedQuery(
				name="TransportPlace.getByCityId",
				query="select e from TransportPlace e where e.city_id = :city_id ")
})

@Entity
@Table(name = "TRANSPORT_PLACES", schema = "PAATA")
public class TransportPlace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TRANSPORT_PLACES_TRANSPORTPLACEID_GENERATOR", sequenceName = "TRANSPORT_PLACE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSPORT_PLACES_TRANSPORTPLACEID_GENERATOR")
	@Column(name = "TRANSPORT_PLACE_ID")
	private Long transport_place_id;

	@Basic
	@Column(name = "CITY_ID")
	private Long city_id;

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
	@Column(name = "TRANSPORT_PLACE_ENG")
	private String transport_place_eng;

	@Basic
	@Column(name = "TRANSPORT_PLACE_GEO")
	private String transport_place_geo;
	
	@Basic
	@Column(name = "TRANSPORT_PLACE_GEO_DESCR")
	private String transport_place_geo_descr;

	@Basic
	@Column(name = "TRANSPORT_TYPE_ID")
	private Long transport_type_id;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Transient
	private String loggedUserName;

	@Transient
	private String transport_type_name_geo;

	@Transient
	private String city_name_geo;

	public TransportPlace() {
	}

	public Long getTransport_place_id() {
		return transport_place_id;
	}

	public void setTransport_place_id(Long transport_place_id) {
		this.transport_place_id = transport_place_id;
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

	public String getTransport_place_eng() {
		return transport_place_eng;
	}

	public void setTransport_place_eng(String transport_place_eng) {
		this.transport_place_eng = transport_place_eng;
	}

	public String getTransport_place_geo() {
		return transport_place_geo;
	}

	public void setTransport_place_geo(String transport_place_geo) {
		this.transport_place_geo = transport_place_geo;
	}

	public Long getTransport_type_id() {
		return transport_type_id;
	}

	public void setTransport_type_id(Long transport_type_id) {
		this.transport_type_id = transport_type_id;
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

	public String getTransport_type_name_geo() {
		return transport_type_name_geo;
	}

	public void setTransport_type_name_geo(String transport_type_name_geo) {
		this.transport_type_name_geo = transport_type_name_geo;
	}

	public String getCity_name_geo() {
		return city_name_geo;
	}

	public void setCity_name_geo(String city_name_geo) {
		this.city_name_geo = city_name_geo;
	}

	public String getTransport_place_geo_descr() {
		return transport_place_geo_descr;
	}

	public void setTransport_place_geo_descr(String transport_place_geo_descr) {
		this.transport_place_geo_descr = transport_place_geo_descr;
	}
}