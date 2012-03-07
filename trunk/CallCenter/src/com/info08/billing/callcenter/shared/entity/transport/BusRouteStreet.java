package com.info08.billing.callcenter.shared.entity.transport;

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
 * The persistent class for the BUS_ROUTE_STREETS database table.
 * 
 */
@Entity
@Table(name="BUS_ROUTE_STREETS", schema="INFO")
public class BusRouteStreet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BUS_ROUTE_STREETS_ROUTESTREETID_GENERATOR", sequenceName="ROUTE_STREET_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BUS_ROUTE_STREETS_ROUTESTREETID_GENERATOR")
	@Column(name="ROUTE_STREET_ID")
	private Long route_street_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="NOTES")
	private String notes;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="ROUTE_DIR")
	private Long route_dir;

	@Basic
	@Column(name="ROUTE_ID")
	private Long route_id;

	@Basic
	@Column(name="ROUTE_ORDER")
	private Long route_order;

	@Basic
	@Column(name="STREET_ID")
	private Long street_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
    private String loggedUserName;
	
	@Transient
    private String street_name;
	
	@Transient
    private String route_dir_descr;
	
	@Transient
    private String route_descr;

    public BusRouteStreet() {
    }

	public Long getRoute_street_id() {
		return route_street_id;
	}

	public void setRoute_street_id(Long route_street_id) {
		this.route_street_id = route_street_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public Long getRoute_dir() {
		return route_dir;
	}

	public void setRoute_dir(Long route_dir) {
		this.route_dir = route_dir;
	}

	public Long getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Long route_id) {
		this.route_id = route_id;
	}

	public Long getRoute_order() {
		return route_order;
	}

	public void setRoute_order(Long route_order) {
		this.route_order = route_order;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
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

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getRoute_dir_descr() {
		return route_dir_descr;
	}

	public void setRoute_dir_descr(String route_dir_descr) {
		this.route_dir_descr = route_dir_descr;
	}

	public String getRoute_descr() {
		return route_descr;
	}

	public void setRoute_descr(String route_descr) {
		this.route_descr = route_descr;
	}
}