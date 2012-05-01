package com.info08.billing.callcenterbk.shared.entity;

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

@NamedQueries({ 
		@NamedQuery(
					name = "CityType.getAllCityTypes", 
					query = "select e from CityType e where e.deleted = 0 order by e.city_type_id")
})
@Entity
@Table(name = "city_types", schema = "info")
public class CityType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CITY_TYPE_ID")
	@SequenceGenerator(name = "seq_city_type_gen", sequenceName = "city_type_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city_type_gen")
	private Long city_type_id;

	@Basic
	@Column(name = "CITY_TYPE_GEO")
	private String city_type_geo;

	@Basic
	@Column(name = "CITY_TYPE_ENG")
	private String city_type_eng;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;
	
	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	public CityType() {
	}

	public Long getCity_type_id() {
		return city_type_id;
	}

	public void setCity_type_id(Long city_type_id) {
		this.city_type_id = city_type_id;
	}

	public String getCity_type_geo() {
		return city_type_geo;
	}

	public void setCity_type_geo(String city_type_geo) {
		this.city_type_geo = city_type_geo;
	}

	public String getCity_type_eng() {
		return city_type_eng;
	}

	public void setCity_type_eng(String city_type_eng) {
		this.city_type_eng = city_type_eng;
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

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}
}
