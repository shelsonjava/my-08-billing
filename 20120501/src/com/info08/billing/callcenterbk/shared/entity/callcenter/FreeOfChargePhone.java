package com.info08.billing.callcenterbk.shared.entity.callcenter;

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

@Entity
@Table(name = "FREE_OF_CHARGE_PHONE")
public class FreeOfChargePhone implements Serializable {
	private static final long serialVersionUID = 1001L;

	@Id
	@Column(name = "PHONE_NUMBER_ID")
	@SequenceGenerator(name = "SEQ_FREE_OF_CHARGE_PHONE_GENERATOR", sequenceName = "SEQ_FREE_OF_CHARGE_PHONE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FREE_OF_CHARGE_PHONE_GENERATOR")
	private Long phone_number_id;

	@Basic
	@Column(name = "PHONE_NUMBER")
	private String phone_number;

	@Basic
	@Column(name = "START_DATE")
	private Timestamp start_date;

	@Basic
	@Column(name = "END_DATE")
	private Timestamp end_date;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "OPERATOR_SRC")
	private Long operator_src;

	@Transient
	private String loggedUserName;

	@Transient
	private String start_date_descr;

	@Transient
	private String end_date_descr;

	public FreeOfChargePhone() {
	}

	public Long getPhone_number_id() {
		return phone_number_id;
	}

	public void setPhone_number_id(Long phone_number_id) {
		this.phone_number_id = phone_number_id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public Timestamp getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getStart_date_descr() {
		return start_date_descr;
	}

	public void setStart_date_descr(String start_date_descr) {
		this.start_date_descr = start_date_descr;
	}

	public String getEnd_date_descr() {
		return end_date_descr;
	}

	public void setEnd_date_descr(String end_date_descr) {
		this.end_date_descr = end_date_descr;
	}

	public Long getOperator_src() {
		return operator_src;
	}

	public void setOperator_src(Long operator_src) {
		this.operator_src = operator_src;
	}

}