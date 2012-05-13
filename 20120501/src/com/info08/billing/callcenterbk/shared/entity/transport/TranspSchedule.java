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
@Table(name="TRANSP_SCHEDULES")
public class TranspSchedule implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_TRANSP_SCHEDULE_ID", sequenceName="SEQ_TRANSP_SCHEDULE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TRANSP_SCHEDULE_ID")
	@Column(name="TRANSP_SCHEDULE_ID")
	private Long transp_schedule_id;

	@Basic
	@Column(name="DAYS")
	private Long days;

	@Basic
	@Column(name="ARRIVAL_TIME")
	private Timestamp arrival_time;
	
	@Basic
	@Column(name="DEPART_TIME")
	private Timestamp depart_time;
	
	@Basic
	@Column(name="TRANSP_MODE_DESCR")
	private Long transp_model_descr;
	
	@Basic
	@Column(name="REMARK")
	private String remark;
	
	@Basic
	@Column(name="PRICE_DESCR")
	private String price_descr;
	
	@Basic
	@Column(name="TRANSP_COMPANY_ID")
	private Long transp_company_id;
	
	@Basic
	@Column(name="transp_type_id")
	private Long transp_type_id;
	
	@Basic
	@Column(name="IN_TRANSPORT_PLACE_ID")
	private Long in_transport_place_id;

	@Basic
	@Column(name="OUT_TRANSPORT_PLACE_ID")
	private Long out_transport_place_id;

	@Basic
	@Column(name="TRANSPORT_PLANE_ID")
	private Long transport_plane_id;

	@Basic
	@Column(name="IMPORTANT")
	private String important;
	
	@Transient
    private String loggedUserName;
	
	@Transient
    private String transport_type;
	
	@Transient
    private String depart_station;
	
	@Transient
    private String arrival_station;
	
	@Transient
    private String transp_company;
	
	@Transient
    private String days_descr;
    
	@Transient
    private String transp_resource;

    public TranspSchedule() {
    }

	public Long getTransp_schedule_id() {
		return transp_schedule_id;
	}

	public void setTransp_schedule_id(Long transp_schedule_id) {
		this.transp_schedule_id = transp_schedule_id;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Timestamp getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Timestamp arrival_time) {
		this.arrival_time = arrival_time;
	}

	public Timestamp getDepart_time() {
		return depart_time;
	}

	public void setDepart_time(Timestamp depart_time) {
		this.depart_time = depart_time;
	}

	public Long getTransp_model_descr() {
		return transp_model_descr;
	}

	public void setTransp_model_descr(Long transp_model_descr) {
		this.transp_model_descr = transp_model_descr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPrice_descr() {
		return price_descr;
	}

	public void setPrice_descr(String price_descr) {
		this.price_descr = price_descr;
	}

	public Long getTransp_company_id() {
		return transp_company_id;
	}

	public void setTransp_company_id(Long transp_company_id) {
		this.transp_company_id = transp_company_id;
	}

	public Long getTransp_type_id() {
		return transp_type_id;
	}

	public void setTransp_type_id(Long transp_type_id) {
		this.transp_type_id = transp_type_id;
	}

	public Long getIn_transport_place_id() {
		return in_transport_place_id;
	}

	public void setIn_transport_place_id(Long in_transport_place_id) {
		this.in_transport_place_id = in_transport_place_id;
	}

	public Long getOut_transport_place_id() {
		return out_transport_place_id;
	}

	public void setOut_transport_place_id(Long out_transport_place_id) {
		this.out_transport_place_id = out_transport_place_id;
	}

	public Long getTransport_plane_id() {
		return transport_plane_id;
	}

	public void setTransport_plane_id(Long transport_plane_id) {
		this.transport_plane_id = transport_plane_id;
	}

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getTransport_type() {
		return transport_type;
	}

	public void setTransport_type(String transport_type) {
		this.transport_type = transport_type;
	}

	public String getDepart_station() {
		return depart_station;
	}

	public void setDepart_station(String depart_station) {
		this.depart_station = depart_station;
	}

	public String getArrival_station() {
		return arrival_station;
	}

	public void setArrival_station(String arrival_station) {
		this.arrival_station = arrival_station;
	}

	public String getTransp_company() {
		return transp_company;
	}

	public void setTransp_company(String transp_company) {
		this.transp_company = transp_company;
	}

	public String getDays_descr() {
		return days_descr;
	}

	public void setDays_descr(String days_descr) {
		this.days_descr = days_descr;
	}

	public String getTransp_resource() {
		return transp_resource;
	}

	public void setTransp_resource(String transp_resource) {
		this.transp_resource = transp_resource;
	}
}