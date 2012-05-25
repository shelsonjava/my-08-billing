package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;

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
 * The persistent class for the CITY_DISTANCES database table.
 * 
 */
@Entity
@Table(name = "DIST_BETWEEN_TOWNS")
public class DistBetweenTowns implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_DIST_BETWEEN_TOWNS_GENERATOR", sequenceName = "SEQ_DIST_BETWEEN_TOWNS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIST_BETWEEN_TOWNS_GENERATOR")
	@Column(name = "DIST_BETWEEN_TOWNS_ID")
	private Long dist_between_towns_id;

	@Basic
	@Column(name = "DIST_BETWEEN_TOWNS_VALUE")
	private String dist_between_towns_value;

	@Basic
	@Column(name = "TOWN_DISTANCE_TYPE")
	private Long town_distance_type;

	@Basic
	@Column(name = "TOWN_ID_END")
	private Long town_id_end;

	@Basic
	@Column(name = "TOWN_ID_START")
	private Long town_id_start;

	@Basic
	@Column(name = "DIST_BETWEEN_TOWNS_REMARK")
	private String dist_between_towns_remark;

	@Transient
	private String loggedUserName;

	@Transient
	private String town_start;

	@Transient
	private String town_end;

	@Transient
	private String town_distance_type_descr;

	public DistBetweenTowns() {
	}

	public Long getDist_between_towns_id() {
		return dist_between_towns_id;
	}

	public void setDist_between_towns_id(Long dist_between_towns_id) {
		this.dist_between_towns_id = dist_between_towns_id;
	}

	public String getDist_between_towns_value() {
		return dist_between_towns_value;
	}

	public void setDist_between_towns_value(String dist_between_towns_value) {
		this.dist_between_towns_value = dist_between_towns_value;
	}

	public Long getTown_distance_type() {
		return town_distance_type;
	}

	public void setTown_distance_type(Long town_distance_type) {
		this.town_distance_type = town_distance_type;
	}

	public Long getTown_id_end() {
		return town_id_end;
	}

	public void setTown_id_end(Long town_id_end) {
		this.town_id_end = town_id_end;
	}

	public Long getTown_id_start() {
		return town_id_start;
	}

	public void setTown_id_start(Long town_id_start) {
		this.town_id_start = town_id_start;
	}

	public String getDist_between_towns_remark() {
		return dist_between_towns_remark;
	}

	public void setDist_between_towns_remark(String dist_between_towns_remark) {
		this.dist_between_towns_remark = dist_between_towns_remark;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getTown_start() {
		return town_start;
	}

	public void setTown_start(String town_start) {
		this.town_start = town_start;
	}

	public String getTown_end() {
		return town_end;
	}

	public void setTown_end(String town_end) {
		this.town_end = town_end;
	}

	public String getTown_distance_type_descr() {
		return town_distance_type_descr;
	}

	public void setTown_distance_type_descr(String town_distance_type_descr) {
		this.town_distance_type_descr = town_distance_type_descr;
	}

}