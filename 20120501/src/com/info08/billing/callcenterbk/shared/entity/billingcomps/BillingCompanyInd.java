package com.info08.billing.callcenterbk.shared.entity.billingcomps;

import java.io.Serializable;

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
 * The persistent class for the BILLING_COMPANIES_IND database table.
 * 
 */
@Entity
@Table(name = "BILLING_COMPANIES_IND", schema = "ccare")
public class BillingCompanyInd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_BILLING_COMPANIES_IND_GENERATOR", sequenceName = "SEQ_BILLING_COMPANIES_IND")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BILLING_COMPANIES_IND_GENERATOR")
	@Column(name = "BILL_INDEX_ID")
	private Long bill_index_id;

	@Basic
	@Column(name = "APPLIED_WHOLLY")
	private Long applied_wholly;

	@Basic
	@Column(name = "BILL_INDEX_END")
	private Long bill_index_end;

	@Basic
	@Column(name = "BILL_INDEX_START")
	private Long bill_index_start;

	@Basic
	@Column(name = "BILLING_COMPANY_ID")
	private Long billing_company_id;

	@Basic
	@Column(name = "CALCUL_TYPE")
	private Long calcul_type;

	@Transient
	private String loggedUserName;

	@Transient
	private String applied_wholly_descr;

	@Transient
	private String calcul_type_descr;

	public BillingCompanyInd() {
	}

	public Long getBill_index_id() {
		return bill_index_id;
	}

	public void setBill_index_id(Long bill_index_id) {
		this.bill_index_id = bill_index_id;
	}

	public Long getApplied_wholly() {
		return applied_wholly;
	}

	public void setApplied_wholly(Long applied_wholly) {
		this.applied_wholly = applied_wholly;
	}

	public Long getBill_index_end() {
		return bill_index_end;
	}

	public void setBill_index_end(Long bill_index_end) {
		this.bill_index_end = bill_index_end;
	}

	public Long getBill_index_start() {
		return bill_index_start;
	}

	public void setBill_index_start(Long bill_index_start) {
		this.bill_index_start = bill_index_start;
	}

	public Long getBilling_company_id() {
		return billing_company_id;
	}

	public void setBilling_company_id(Long billing_company_id) {
		this.billing_company_id = billing_company_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getApplied_wholly_descr() {
		return applied_wholly_descr;
	}

	public void setApplied_wholly_descr(String applied_wholly_descr) {
		this.applied_wholly_descr = applied_wholly_descr;
	}

	public Long getCalcul_type() {
		return calcul_type;
	}

	public void setCalcul_type(Long calcul_type) {
		this.calcul_type = calcul_type;
	}

	public String getCalcul_type_descr() {
		return calcul_type_descr;
	}

	public void setCalcul_type_descr(String calcul_type_descr) {
		this.calcul_type_descr = calcul_type_descr;
	}
}