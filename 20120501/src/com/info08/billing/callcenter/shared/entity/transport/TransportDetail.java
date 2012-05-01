package com.info08.billing.callcenter.shared.entity.transport;

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
 * The persistent class for the TRANSPORT_DETAILS database table.
 * 
 */

@NamedQueries({
	@NamedQuery(
				name="TransportDetail.getByTransportId",
				query="select e from TransportDetail e where e.transport_id = :transport_id")
})

@Entity
@Table(name="TRANSPORT_DETAILS",schema="INFO")
public class TransportDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRANSPORT_DETAILS_TRANSPORTDETAILID_GENERATOR", sequenceName="TRANSPORT_DETAIL_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRANSPORT_DETAILS_TRANSPORTDETAILID_GENERATOR")
	@Column(name="TRANSPORT_DETAIL_ID")
	private Long transport_detail_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="IN_TIME")
	private Timestamp in_time;

	@Basic
	@Column(name="OUT_TIME")
	private Timestamp  out_time;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="STAY_TIME")
	private Timestamp stay_time;

	@Basic
	@Column(name="TRANSPORT_DETAIL_ORDER")
	private Long transport_detail_order;

	@Basic
	@Column(name="TRANSPORT_ID")
	private Long transport_id;

	@Basic
	@Column(name="TRANSPORT_PLACE_ID")
	private Long transport_place_id;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	
	
	@Basic
	@Column(name="OUT_TRANSPORT_PLACE_ID")
	private Long out_transport_place_id;
	
	
	@Basic
	@Column(name="IN_TRANSPORT_PLACE_ID")
	private Long in_transport_place_id;
	
	@Transient
    private String loggedUserName;
	
	@Transient
    private String transport_place_geo_out;

    public TransportDetail() {
    }

	public Long getTransport_detail_id() {
		return transport_detail_id;
	}

	public void setTransport_detail_id(Long transport_detail_id) {
		this.transport_detail_id = transport_detail_id;
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

	public Timestamp getOut_time() {
		return out_time;
	}

	public void setOut_time(Timestamp out_time) {
		this.out_time = out_time;
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

	public Timestamp getStay_time() {
		return stay_time;
	}

	public void setStay_time(Timestamp stay_time) {
		this.stay_time = stay_time;
	}

	public Long getTransport_detail_order() {
		return transport_detail_order;
	}

	public void setTransport_detail_order(Long transport_detail_order) {
		this.transport_detail_order = transport_detail_order;
	}

	public Long getTransport_id() {
		return transport_id;
	}

	public void setTransport_id(Long transport_id) {
		this.transport_id = transport_id;
	}

	public Long getTransport_place_id() {
		return transport_place_id;
	}

	public void setTransport_place_id(Long transport_place_id) {
		this.transport_place_id = transport_place_id;
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

	public String getTransport_place_geo_out() {
		return transport_place_geo_out;
	}

	public void setTransport_place_geo_out(String transport_place_geo_out) {
		this.transport_place_geo_out = transport_place_geo_out;
	}

	public Long getOut_transport_place_id() {
		return out_transport_place_id;
	}

	public void setOut_transport_place_id(Long out_transport_place_id) {
		this.out_transport_place_id = out_transport_place_id;
	}

	public Long getIn_transport_place_id() {
		return in_transport_place_id;
	}

	public void setIn_transport_place_id(Long in_transport_place_id) {
		this.in_transport_place_id = in_transport_place_id;
	}
}