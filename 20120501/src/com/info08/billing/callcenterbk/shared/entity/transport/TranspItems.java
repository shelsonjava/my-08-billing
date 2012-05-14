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
 * The persistent class for the TRANSPORT_DETAILS database table.
 * 
 */

@Entity
@Table(name="TRANSP_ITEMS")
public class TranspItems implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_TRANSP_ITEM_ID", sequenceName="SEQ_TRANSP_ITEM_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TRANSP_ITEM_ID")
	@Column(name="TRANSPORT_DETAIL_ID")
	private Long transp_item_id;

	@Basic
	@Column(name="ARRIVAL_TIME")
	private Timestamp arrival_time;

	@Basic
	@Column(name="DEPARTURE_TIME")
	private Timestamp  departure_time;

	@Basic
	@Column(name="DELAY_TIME")
	private Timestamp delay_time;

	@Basic
	@Column(name="ITEM_ORDER")
	private Long item_order;

	@Basic
	@Column(name="TRANSP_SCHEDULE_ID")
	private Long transp_schedule_id;
	
	@Basic
	@Column(name="DEPARTURE_TRANSP_STAT_ID")
	private Long departure_transp_stat_id;
	
	@Basic
	@Column(name="ARRIVAL_TRANSP_STAT_ID")
	private Long arrival_transp_stat_id;
	
	@Basic
	@Column(name="TRANSP_STATION_ID")
	private Long transp_station_id;

	@Transient
    private String loggedUserName;
	
	@Transient
    private String depart_station;

    public TranspItems() {
    }

	public Long getTransp_item_id() {
		return transp_item_id;
	}

	public void setTransp_item_id(Long transp_item_id) {
		this.transp_item_id = transp_item_id;
	}

	public Timestamp getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Timestamp arrival_time) {
		this.arrival_time = arrival_time;
	}

	public Timestamp getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(Timestamp departure_time) {
		this.departure_time = departure_time;
	}

	public Timestamp getDelay_time() {
		return delay_time;
	}

	public void setDelay_time(Timestamp delay_time) {
		this.delay_time = delay_time;
	}

	public Long getItem_order() {
		return item_order;
	}

	public void setItem_order(Long item_order) {
		this.item_order = item_order;
	}

	public Long getTransp_schedule_id() {
		return transp_schedule_id;
	}

	public void setTransp_schedule_id(Long transp_schedule_id) {
		this.transp_schedule_id = transp_schedule_id;
	}

	public Long getDeparture_transp_stat_id() {
		return departure_transp_stat_id;
	}

	public void setDeparture_transp_stat_id(Long departure_transp_stat_id) {
		this.departure_transp_stat_id = departure_transp_stat_id;
	}

	public Long getArrival_transp_stat_id() {
		return arrival_transp_stat_id;
	}

	public void setArrival_transp_stat_id(Long arrival_transp_stat_id) {
		this.arrival_transp_stat_id = arrival_transp_stat_id;
	}

	public Long getTransp_station_id() {
		return transp_station_id;
	}

	public void setTransp_station_id(Long transp_station_id) {
		this.transp_station_id = transp_station_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getDepart_station() {
		return depart_station;
	}

	public void setDepart_station(String depart_station) {
		this.depart_station = depart_station;
	}
}