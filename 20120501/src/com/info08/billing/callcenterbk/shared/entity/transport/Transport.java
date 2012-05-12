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
 * The persistent class for the TRANSPORTS database table.
 * 
 */
@Entity
@Table(name="TRANSPORTS",schema="ccare")
public class Transport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRANSPORTS_TRANSPORTID_GENERATOR", sequenceName="TRANSPORT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRANSPORTS_TRANSPORTID_GENERATOR")
	@Column(name="TRANSPORT_ID")
	private Long transport_id;

	@Basic
	@Column(name="\"DAYS\"")
	private Long days;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="IN_TIME")
	private Timestamp in_time;

	@Basic
	@Column(name="IN_TRANSPORT_PLACE_ID")
	private Long in_transport_place_id;

	@Basic
	@Column(name="NOTE_CRIT")
	private Long note_crit;

	@Basic
	@Column(name="NOTE_ENG")
	private String note_eng;

	@Basic
	@Column(name="NOTE_GEO")
	private String note_geo;

	@Basic
	@Column(name="OUT_TIME")
	private Timestamp out_time;

	@Basic
	@Column(name="OUT_TRANSPORT_PLACE_ID")
	private Long out_transport_place_id;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="TRANSPORT_COMPANY_ID")
	private Long transport_company_id;

	@Basic
	@Column(name="TRANSPORT_NO")
	private String transport_no;

	@Basic
	@Column(name="TRANSPORT_PLANE_ID")
	private Long transport_plane_id;

	@Basic
	@Column(name="TRANSPORT_PRICE_ENG")
	private String transport_price_eng;

	@Basic
	@Column(name="TRANSPORT_PRICE_GEO")
	private String transport_price_geo;

	@Basic
	@Column(name="transp_type_id")
	private Long transport_type_id;

	@Basic
	@Column(name="TRIP_CRITERIA")
	private String trip_criteria;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
    private String loggedUserName;
	
	@Transient
    private String name_descr;
	
	@Transient
    private String transport_place_geo_out;
	
	@Transient
    private String transport_place_geo_in;
	
	@Transient
    private String transport_company_geo;
	
	@Transient
    private String days_descr;
    
	@Transient
    private String transport_plane_geo;

    public Transport() {
    }

	public Long getTransport_id() {
		return transport_id;
	}

	public void setTransport_id(Long transport_id) {
		this.transport_id = transport_id;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Timestamp getIn_time() {
		return in_time;
	}

	public void setIn_time(Timestamp in_time) {
		this.in_time = in_time;
	}

	public Long getIn_transport_place_id() {
		return in_transport_place_id;
	}

	public void setIn_transport_place_id(Long in_transport_place_id) {
		this.in_transport_place_id = in_transport_place_id;
	}

	public Long getNote_crit() {
		return note_crit;
	}

	public void setNote_crit(Long note_crit) {
		this.note_crit = note_crit;
	}

	public String getNote_eng() {
		return note_eng;
	}

	public void setNote_eng(String note_eng) {
		this.note_eng = note_eng;
	}

	public String getNote_geo() {
		return note_geo;
	}

	public void setNote_geo(String note_geo) {
		this.note_geo = note_geo;
	}

	public Timestamp getOut_time() {
		return out_time;
	}

	public void setOut_time(Timestamp out_time) {
		this.out_time = out_time;
	}

	public Long getOut_transport_place_id() {
		return out_transport_place_id;
	}

	public void setOut_transport_place_id(Long out_transport_place_id) {
		this.out_transport_place_id = out_transport_place_id;
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

	public Long getTransport_company_id() {
		return transport_company_id;
	}

	public void setTransport_company_id(Long transport_company_id) {
		this.transport_company_id = transport_company_id;
	}

	public String getTransport_no() {
		return transport_no;
	}

	public void setTransport_no(String transport_no) {
		this.transport_no = transport_no;
	}

	public Long getTransport_plane_id() {
		return transport_plane_id;
	}

	public void setTransport_plane_id(Long transport_plane_id) {
		this.transport_plane_id = transport_plane_id;
	}

	public String getTransport_price_eng() {
		return transport_price_eng;
	}

	public void setTransport_price_eng(String transport_price_eng) {
		this.transport_price_eng = transport_price_eng;
	}

	public String getTransport_price_geo() {
		return transport_price_geo;
	}

	public void setTransport_price_geo(String transport_price_geo) {
		this.transport_price_geo = transport_price_geo;
	}

	public Long getTransport_type_id() {
		return transport_type_id;
	}

	public void setTransport_type_id(Long transport_type_id) {
		this.transport_type_id = transport_type_id;
	}

	public String getTrip_criteria() {
		return trip_criteria;
	}

	public void setTrip_criteria(String trip_criteria) {
		this.trip_criteria = trip_criteria;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
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

	public String getname_descr() {
		return name_descr;
	}

	public void setname_descr(String name_descr) {
		this.name_descr = name_descr;
	}

	public String getTransport_place_geo_out() {
		return transport_place_geo_out;
	}

	public void setTransport_place_geo_out(String transport_place_geo_out) {
		this.transport_place_geo_out = transport_place_geo_out;
	}

	public String getTransport_place_geo_in() {
		return transport_place_geo_in;
	}

	public void setTransport_place_geo_in(String transport_place_geo_in) {
		this.transport_place_geo_in = transport_place_geo_in;
	}

	public String getTransport_company_geo() {
		return transport_company_geo;
	}

	public void setTransport_company_geo(String transport_company_geo) {
		this.transport_company_geo = transport_company_geo;
	}

	public String getDays_descr() {
		return days_descr;
	}

	public void setDays_descr(String days_descr) {
		this.days_descr = days_descr;
	}

	public String getTransport_plane_geo() {
		return transport_plane_geo;
	}

	public void setTransport_plane_geo(String transport_plane_geo) {
		this.transport_plane_geo = transport_plane_geo;
	}
}