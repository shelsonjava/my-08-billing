package com.info08.billing.callcenterbk.shared.entity.control;

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

/**
 * The persistent class for the LOG_PERSONELL_NOTES database table.
 * 
 */
@Entity
@Table(name = "OPERATOR_WARNS", schema = "ccare")
public class OperatorWarn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_OPERATOR_WARNS", sequenceName = "SEQ_OPERATOR_WARNS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPERATOR_WARNS")
	@Column(name = "OPER_WARN_ID")
	private Long oper_warn_id;

	@Basic
	@Column(name = "CALL_SESSION_ID")
	private String call_session_id;

	@Basic
	@Column(name = "OPERATOR")
	private String operator;

	@Basic
	@Column(name = "WARNING")
	private String warning;

	@Basic
	@Column(name = "WARN_SENDER")
	private String warn_sender;

	@Basic
	@Column(name = "WARN_SEND_DATE")
	private Timestamp warn_send_date;

	@Basic
	@Column(name = "HIDDEN")
	private Long hidden;

	@Basic
	@Column(name = "PHONE_NUMBER")
	private String phone_number;

	@Basic
	@Column(name = "IMPORTANT")
	private Long important;

	@Basic
	@Column(name = "DELIVERED")
	private Long delivered;

	@Basic
	@Column(name = "UPDATE_DATE")
	private Timestamp update_date;

	@Basic
	@Column(name = "UPDATE_USER")
	private String update_user;

	public OperatorWarn() {
	}

	public Long getOper_warn_id() {
		return oper_warn_id;
	}

	public void setOper_warn_id(Long oper_warn_id) {
		this.oper_warn_id = oper_warn_id;
	}

	public String getCall_session_id() {
		return call_session_id;
	}

	public void setCall_session_id(String call_session_id) {
		this.call_session_id = call_session_id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getWarn_sender() {
		return warn_sender;
	}

	public void setWarn_sender(String warn_sender) {
		this.warn_sender = warn_sender;
	}

	public Timestamp getWarn_send_date() {
		return warn_send_date;
	}

	public void setWarn_send_date(Timestamp warn_send_date) {
		this.warn_send_date = warn_send_date;
	}

	public Long getHidden() {
		return hidden;
	}

	public void setHidden(Long hidden) {
		this.hidden = hidden;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Long getImportant() {
		return important;
	}

	public void setImportant(Long important) {
		this.important = important;
	}

	public Long getDelivered() {
		return delivered;
	}

	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
}