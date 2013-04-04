package com.info08.billing.callcenterbk.shared.entity.session;

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
 * The persistent class for the LOG_SESSION_CHARGES database table.
 * 
 */
@Entity
@Table(name = "CALL_SESSION_EXPENSE")
public class CallSessionExpense implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CALL_SESS_EXP_ID", sequenceName = "SEQ_CALL_SESS_EXP_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CALL_SESS_EXP_ID")
	@Column(name = "CSE_ID")
	private Long cse_id;

	@Basic
	@Column(name = "CHARGE")
	private Double charge;

	@Basic
	@Column(name = "CHARGE_DATE")
	private Timestamp charge_date;

	@Basic
	@Column(name = "SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name = "SESSION_ID")
	private String session_id;

	@Basic
	@Column(name = "YEAR_MONTH")
	private Long year_month;

	@Basic
	@Column(name = "CALL_SESSION_ID")
	private Long call_session_id;

	@Basic
	@Column(name = "EVENT_DESCRIPTION")
	private String event_describtion;
	
	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Transient
	private Long chargeCount;

	@Transient
	private String call_phone;

	@Transient
	private String loggedUserName;

	@Transient
	private String operator;

	@Transient
	private Long call_duration;

	@Transient
	private Timestamp call_start_date;

	public CallSessionExpense() {
	}

	public Long getCse_id() {
		return cse_id;
	}

	public void setCse_id(Long cse_id) {
		this.cse_id = cse_id;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}

	public Timestamp getCharge_date() {
		return charge_date;
	}

	public void setCharge_date(Timestamp charge_date) {
		this.charge_date = charge_date;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public Long getYear_month() {
		return year_month;
	}

	public void setYear_month(Long year_month) {
		this.year_month = year_month;
	}

	public Long getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Long chargeCount) {
		this.chargeCount = chargeCount;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getCall_duration() {
		return call_duration;
	}

	public void setCall_duration(Long call_duration) {
		this.call_duration = call_duration;
	}

	public Timestamp getCall_start_date() {
		return call_start_date;
	}

	public void setCall_start_date(Timestamp call_start_date) {
		this.call_start_date = call_start_date;
	}

	public Long getCall_session_id() {
		return call_session_id;
	}

	public void setCall_session_id(Long call_session_id) {
		this.call_session_id = call_session_id;
	}

	public String getEvent_describtion() {
		return event_describtion;
	}

	public void setEvent_describtion(String event_describtion) {
		this.event_describtion = event_describtion;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
}