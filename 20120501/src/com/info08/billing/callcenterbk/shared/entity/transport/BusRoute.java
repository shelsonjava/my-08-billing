package com.info08.billing.callcenterbk.shared.entity.transport;

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
 * The persistent class for the BUS_ROUTES database table.
 * 
 */
@Entity
@Table(name="BUS_ROUTES",schema="PAATA")
public class BusRoute implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BUS_ROUTES_ROUTEID_GENERATOR", sequenceName="ROUTE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BUS_ROUTES_ROUTEID_GENERATOR")
	@Column(name="ROUTE_ID")
	private Long route_id;
	
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
	@Column(name="ROUND_ID")
	private Long round_id;

	@Basic
	@Column(name="ROUTE_NM")
	private String route_nm;

	@Basic
	@Column(name="ROUTE_OLD_NM")
	private String route_old_nm;

	@Basic
	@Column(name="SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
    private String round_descr;
    
    @Transient
    private String service_descr;
    
    @Transient
    private String loggedUserName;

    public BusRoute() {
    }

	public Long getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Long route_id) {
		this.route_id = route_id;
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

	public Long getRound_id() {
		return round_id;
	}

	public void setRound_id(Long round_id) {
		this.round_id = round_id;
	}

	public String getRoute_nm() {
		return route_nm;
	}

	public void setRoute_nm(String route_nm) {
		this.route_nm = route_nm;
	}

	public String getRoute_old_nm() {
		return route_old_nm;
	}

	public void setRoute_old_nm(String route_old_nm) {
		this.route_old_nm = route_old_nm;
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

	public String getRound_descr() {
		return round_descr;
	}

	public void setRound_descr(String round_descr) {
		this.round_descr = round_descr;
	}

	public String getService_descr() {
		return service_descr;
	}

	public void setService_descr(String service_descr) {
		this.service_descr = service_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}