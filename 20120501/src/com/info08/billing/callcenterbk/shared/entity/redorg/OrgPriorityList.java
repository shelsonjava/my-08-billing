package com.info08.billing.callcenterbk.shared.entity.redorg;

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
 * The persistent class for the CORPORATE_CLIENTS database table.
 * 
 */
@Entity
@Table(name = "ORG_PRIORITY_LIST")
public class OrgPriorityList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORG_PRIORITY_LIST_GENERATOR", sequenceName = "SEQ_ORG_PRIORITY_LIST")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORG_PRIORITY_LIST_GENERATOR")
	@Column(name = "ID")
	private Long id;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "DEBT")
	private Long debt;

	@Basic
	@Column(name = "TARIFF")
	private Double tariff;

	@Basic
	@Column(name = "BILLING_DATE")
	private Timestamp billing_date;

	@Basic
	@Column(name = "START_DATE")
	private Timestamp start_date;

	@Basic
	@Column(name = "END_DATE")
	private Timestamp end_date;

	@Basic
	@Column(name = "CONTACT_PHONES")
	private String contact_phones;

	@Basic
	@Column(name = "UPDATE_USER")
	private String update_user;

	@Basic
	@Column(name = "UPDATE_DATE")
	private Timestamp update_date;

	@Basic
	@Column(name = "DEBT_AMOUNT")
	private Double debt_amount;

	@Basic
	@Column(name = "OPERATOR_SRC")
	private Long operator_src;

	@Basic
	@Column(name = "SMS_WARNING")
	private Long sms_warning;

	public OrgPriorityList() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getDebt() {
		return debt;
	}

	public void setDebt(Long debt) {
		this.debt = debt;
	}

	public Double getTariff() {
		return tariff;
	}

	public void setTariff(Double tariff) {
		this.tariff = tariff;
	}

	public Timestamp getBilling_date() {
		return billing_date;
	}

	public void setBilling_date(Timestamp billing_date) {
		this.billing_date = billing_date;
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

	public String getContact_phones() {
		return contact_phones;
	}

	public void setContact_phones(String contact_phones) {
		this.contact_phones = contact_phones;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public Double getDebt_amount() {
		return debt_amount;
	}

	public void setDebt_amount(Double debt_amount) {
		this.debt_amount = debt_amount;
	}

	public Long getOperator_src() {
		return operator_src;
	}

	public void setOperator_src(Long operator_src) {
		this.operator_src = operator_src;
	}

	public Long getSms_warning() {
		return sms_warning;
	}

	public void setSms_warning(Long sms_warning) {
		this.sms_warning = sms_warning;
	}
}