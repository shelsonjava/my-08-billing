package com.info08.billing.callcenterbk.shared.entity.callcenter;

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

/**
 * The persistent class for the CALL_CENTER_QUEUE database table.
 * 
 */
@NamedQueries({
		@NamedQuery(name = "CallCenterQueue.getByUserName", query = "select e from CallCenterQueue e where e.op_user_name = :userName"),
		@NamedQuery(name = "CallCenterQueue.getByStatus", query = "select e from CallCenterQueue e where e.status = :status order by e.upd_date") })
@Entity
@Table(name = "CALL_CENTER_QUEUE", schema = "INFO")
public class CallCenterQueue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CALL_CENTER_QUEUE_ID_GENERATOR", sequenceName = "SEQ_CALL_CENT_QUEUE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_CENTER_QUEUE_ID_GENERATOR")
	private Long id;

	@Basic
	@Column(name = "CALL_DURATION")
	private Long call_duration;

	@Basic
	@Column(name = "END_DATE")
	private Timestamp end_date;

	@Basic
	@Column(name = "INCOMMING_DATE")
	private Timestamp incomming_date;

	@Basic
	@Column(name = "OP_USER_NAME")
	private String op_user_name;

	@Basic
	@Column(name = "PHONE")
	private String phone;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "STATUS")
	private Long status;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Basic
	@Column(name = "ABONENT_NAME")
	private String abonent_name;

	public CallCenterQueue() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCall_duration() {
		return call_duration;
	}

	public void setCall_duration(Long call_duration) {
		this.call_duration = call_duration;
	}

	public Timestamp getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}

	public Timestamp getIncomming_date() {
		return incomming_date;
	}

	public void setIncomming_date(Timestamp incomming_date) {
		this.incomming_date = incomming_date;
	}

	public String getOp_user_name() {
		return op_user_name;
	}

	public void setOp_user_name(String op_user_name) {
		this.op_user_name = op_user_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public String getAbonent_name() {
		return abonent_name;
	}

	public void setAbonent_name(String abonent_name) {
		this.abonent_name = abonent_name;
	}
}