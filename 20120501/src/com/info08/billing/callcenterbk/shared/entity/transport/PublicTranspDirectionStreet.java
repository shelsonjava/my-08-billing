package com.info08.billing.callcenterbk.shared.entity.transport;

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
 * The persistent class for the BUS_ROUTE_STREETS database table.
 * 
 */
@Entity
@Table(name = "PUBLIC_TRANSP_DIR_STREET", schema = "ccare")
public class PublicTranspDirectionStreet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_PUBLIC_TRANSP_DIR_STREET_GENERATOR", sequenceName = "SEQ_PUBLIC_TRANSP_DIR_STREET")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PUBLIC_TRANSP_DIR_STREET_GENERATOR")
	@Column(name = "PTS_ID")
	private Long pts_id;

	@Basic
	@Column(name = "REMARKS")
	private String remarks;

	@Basic
	@Column(name = "DIR")
	private Long dir;

	@Basic
	@Column(name = "DIR_ID")
	private Long dir_id;

	@Basic
	@Column(name = "DIR_ORDER")
	private Long dir_order;

	@Basic
	@Column(name = "STREET_ID")
	private Long street_id;

	@Transient
	private String loggedUserName;

	@Transient
	private String street_name;

	@Transient
	private String dir_descr;

	@Transient
	private String descr;

	public PublicTranspDirectionStreet() {
	}

	public Long getPts_id() {
		return pts_id;
	}

	public void setPts_id(Long pts_id) {
		this.pts_id = pts_id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getDir() {
		return dir;
	}

	public void setDir(Long dir) {
		this.dir = dir;
	}

	public Long getDir_id() {
		return dir_id;
	}

	public void setDir_id(Long dir_id) {
		this.dir_id = dir_id;
	}

	public Long getDir_order() {
		return dir_order;
	}

	public void setDir_order(Long dir_order) {
		this.dir_order = dir_order;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getDir_descr() {
		return dir_descr;
	}

	public void setDir_descr(String dir_descr) {
		this.dir_descr = dir_descr;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}