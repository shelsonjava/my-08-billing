package com.info08.billing.callcenterbk.shared.entity;

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
@Table(name = "OPERATOR_BREAKS")
public class OperatorBreaks implements java.io.Serializable {

	private static final long serialVersionUID = 4653317752773716984L;

	@Id
	@Column(name = "OPERATOR_BREAK_ID")
	@SequenceGenerator(name = "SEQ_OPERATOR_BREAKS_GENERATOR", sequenceName = "SEQ_OPERATOR_BREAKS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPERATOR_BREAKS_GENERATOR")
	private long operator_break_id;

	@Basic
	@Column(name = "USER_ID")
	private Long user_id;

	@Basic
	@Column(name = "BREAK_DATE")
	private Timestamp break_date;

	@Basic
	@Column(name = "BREAK_TIME")
	private String break_time;

	@Transient
	private String loggedUserName;

	@Transient
	private String fullUserName;

	@Transient
	private String user_firstname;

	@Transient
	private String user_lastname;

	@Transient
	private String user_name;

	@Transient
	private String break_date_text;

	public OperatorBreaks() {
	}

	public long getOperator_break_id() {
		return operator_break_id;
	}

	public void setOperator_break_id(long operator_break_id) {
		this.operator_break_id = operator_break_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Timestamp getBreak_date() {
		return break_date;
	}

	public void setBreak_date(Timestamp break_date) {
		this.break_date = break_date;
	}

	public String getBreak_time() {
		return break_time;
	}

	public void setBreak_time(String break_time) {
		this.break_time = break_time;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getFullUserName() {
		return fullUserName;
	}

	public void setFullUserName(String fullUserName) {
		this.fullUserName = fullUserName;
	}

	public String getUser_firstname() {
		return user_firstname;
	}

	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}

	public String getUser_lastname() {
		return user_lastname;
	}

	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getBreak_date_text() {
		return break_date_text;
	}

	public void setBreak_date_text(String break_date_text) {
		this.break_date_text = break_date_text;
	}

}
