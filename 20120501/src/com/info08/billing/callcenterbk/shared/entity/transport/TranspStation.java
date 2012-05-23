package com.info08.billing.callcenterbk.shared.entity.transport;

import java.io.Serializable;

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
 * The persistent class for the TranspStation database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = "TranspStation.getByCityId", query = "select e from TranspStation e where e.town_id = :town_id ") })
@Entity
@Table(name = "TRANSP_STATIONS")
public class TranspStation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_tranp_station_id", sequenceName = "seq_tranp_station_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tranp_station_id")
	@Column(name = "TRANSP_STAT_ID")
	private Long transp_stat_id;

	@Basic
	@Column(name = "TRANSP_TYPE_ID")
	private Long transp_type_id;

	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;

	@Basic
	@Column(name = "NAME_DESCR")
	private String name_descr;

	@Transient
	private String loggedUserName;

	@Transient
	private String transpport_type;

	@Transient
	private String city_descr;

	public TranspStation() {
	}

	public Long getTransp_stat_id() {
		return transp_stat_id;
	}

	public void setTransp_stat_id(Long transp_stat_id) {
		this.transp_stat_id = transp_stat_id;
	}

	public Long getTransp_type_id() {
		return transp_type_id;
	}

	public void setTransp_type_id(Long transp_type_id) {
		this.transp_type_id = transp_type_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public String getName_descr() {
		return name_descr;
	}

	public void setName_descr(String name_descr) {
		this.name_descr = name_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getTranspport_type() {
		return transpport_type;
	}

	public void setTranspport_type(String transpport_type) {
		this.transpport_type = transpport_type;
	}

	public String getCity_descr() {
		return city_descr;
	}

	public void setCity_descr(String city_descr) {
		this.city_descr = city_descr;
	}
}