package com.info08.billing.callcenterbk.shared.entity.facts;

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
 * The persistent class for the SECULAR_CALENDAR database table.
 * 
 */
@Entity
@Table(name = "FACTS", schema = "ccare")
public class Facts implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_FACT_GENERATOR", sequenceName = "SEQ_FACT")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FACT_GENERATOR")
	@Column(name = "FACT_ID")
	private Long fact_id;

	@Basic
	@Column(name = "ADDITIONAL_COMMENT")
	private String additional_comment;

	@Basic
	@Column(name = "FACT_DATE")
	private Timestamp fact_date;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "FACT_TYPE_ID")
	private Long fact_type_id;

	@Basic
	@Column(name = "FACT_STATUS_ID")
	private Long fact_status_id;

	@Basic
	@Column(name = "SUNUP")
	private String sunup;

	@Transient
	private String fact_type_name;

	@Transient
	private String fact_status_name;

	@Transient
	private String loggedUserName;

	public Facts() {
	}

	public Long getFact_id() {
		return fact_id;
	}

	public void setFact_id(Long fact_id) {
		this.fact_id = fact_id;
	}

	public String getAdditional_comment() {
		return additional_comment;
	}

	public void setAdditional_comment(String additional_comment) {
		this.additional_comment = additional_comment;
	}

	public Timestamp getFact_date() {
		return fact_date;
	}

	public void setFact_date(Timestamp fact_date) {
		this.fact_date = fact_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getFact_type_id() {
		return fact_type_id;
	}

	public void setFact_type_id(Long fact_type_id) {
		this.fact_type_id = fact_type_id;
	}

	public Long getFact_status_id() {
		return fact_status_id;
	}

	public void setFact_status_id(Long fact_status_id) {
		this.fact_status_id = fact_status_id;
	}

	public String getSunup() {
		return sunup;
	}

	public void setSunup(String sunup) {
		this.sunup = sunup;
	}

	public String getFact_type_name() {
		return fact_type_name;
	}

	public void setFact_type_name(String fact_type_name) {
		this.fact_type_name = fact_type_name;
	}

	public String getFact_status_name() {
		return fact_status_name;
	}

	public void setFact_status_name(String fact_status_name) {
		this.fact_status_name = fact_status_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}