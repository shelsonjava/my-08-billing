package com.info08.billing.callcenterbk.shared.entity.billingcomps;

import java.io.Serializable;
import java.util.LinkedHashMap;

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
 * The persistent class for the BILLING_COMPANIES database table.
 * 
 */
@Entity
@Table(name = "BILLING_COMPANIES")
public class BillingCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_BILLING_COMPANIES_GENERATOR", sequenceName = "SEQ_BILLING_COMPANIES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BILLING_COMPANIES_GENERATOR")
	@Column(name = "BILLING_COMPANY_ID")
	private Long billing_company_id;

	@Basic
	@Column(name = "BILLING_COMPANY_NAME")
	private String billing_company_name;

	@Basic
	@Column(name = "OUR_PERCENT")
	private Double our_percent;

	@Basic
	@Column(name = "HAS_CALCULATION")
	private Long has_calculation;

	@Basic
	@Column(name = "CALL_PRICE")
	private Double call_price;

	@Transient
	private String has_calculation_descr;

	@Transient
	private LinkedHashMap<String, String> billingCompIdexes;

	@Transient
	private String loggedUserName;

	public BillingCompany() {
	}

	public Long getbilling_company_id() {
		return billing_company_id;
	}

	public void setbilling_company_id(Long billing_company_id) {
		this.billing_company_id = billing_company_id;
	}

	public String getBilling_company_name() {
		return billing_company_name;
	}

	public void setBilling_company_name(String billing_company_name) {
		this.billing_company_name = billing_company_name;
	}

	public LinkedHashMap<String, String> getBillingCompIdexes() {
		return billingCompIdexes;
	}

	public void setBillingCompIdexes(LinkedHashMap<String, String> billingCompIdexes) {
		this.billingCompIdexes = billingCompIdexes;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Double getOur_percent() {
		return our_percent;
	}

	public void setOur_percent(Double our_percent) {
		this.our_percent = our_percent;
	}

	public Long getHas_calculation() {
		return has_calculation;
	}

	public void setHas_calculation(Long has_calculation) {
		this.has_calculation = has_calculation;
	}

	public String getHas_calculation_descr() {
		return has_calculation_descr;
	}

	public void setHas_calculation_descr(String has_calculation_descr) {
		this.has_calculation_descr = has_calculation_descr;
	}

	public Double getCall_price() {
		return call_price;
	}

	public void setCall_price(Double call_price) {
		this.call_price = call_price;
	}
}