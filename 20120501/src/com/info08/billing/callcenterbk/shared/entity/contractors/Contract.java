package com.info08.billing.callcenterbk.shared.entity.contractors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * The persistent class for the CONTRACTS database table.
 * 
 */
@Entity
@Table(name = "CONTRACTS", schema = "ccare")
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CONTRACT_GENERATOR", sequenceName = "SEQ_CONTRACT")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTRACT_GENERATOR")
	@Column(name = "CONTRACT_ID")
	private Long contract_id;

	@Basic
	@Column(name = "BLOCK")
	private Long block;

	@Basic
	@Column(name = "CRITICAL_NUMBER")
	private Long critical_number;


	@Basic
	@Column(name = "END_DATE")
	private Timestamp end_date;

	@Basic
	@Column(name = "IS_BUDGET")
	private Long is_budget;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "NOTE")
	private String note;

	@Basic
	@Column(name = "PRICE")
	private BigDecimal price;

	@Basic
	@Column(name = "PRICE_TYPE")
	private Long price_type;


	@Basic
	@Column(name = "SMS_WARNING")
	private Long sms_warning;

	@Basic
	@Column(name = "START_DATE")
	private Timestamp start_date;


	@Basic
	@Column(name = "RANGE_CURR_PRICE")
	private BigDecimal range_curr_price;

	@Transient
	private String loggedUserName;

	@Transient
	private String orgName;


	@Transient
	private String price_type_descr;

	@Transient
	private LinkedHashMap<String, LinkedHashMap<String, String>> contractorAdvPrices;

	@Transient
	private LinkedHashMap<String, String> contractorAdvPhones;

	@Transient
	private Long contractor_call_cnt;

	@Transient
	private Double contractor_charges;

	@Transient
	private Long checkContractor;

	public Contract() {
	}

	public Long getContract_id() {
		return contract_id;
	}

	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getCritical_number() {
		return critical_number;
	}

	public void setCritical_number(Long critical_number) {
		this.critical_number = critical_number;
	}


	public Timestamp getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}

	public Long getIs_budget() {
		return is_budget;
	}

	public void setIs_budget(Long is_budget) {
		this.is_budget = is_budget;
	}


	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getPrice_type() {
		return price_type;
	}

	public void setPrice_type(Long price_type) {
		this.price_type = price_type;
	}

	public Long getSms_warning() {
		return sms_warning;
	}

	public void setSms_warning(Long sms_warning) {
		this.sms_warning = sms_warning;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}


	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getContractorAdvPrices() {
		return contractorAdvPrices;
	}

	public void setContractorAdvPrices(
			LinkedHashMap<String, LinkedHashMap<String, String>> contractorAdvPrices) {
		this.contractorAdvPrices = contractorAdvPrices;
	}


	public LinkedHashMap<String, String> getContractorAdvPhones() {
		return contractorAdvPhones;
	}

	public void setContractorAdvPhones(
			LinkedHashMap<String, String> contractorAdvPhones) {
		this.contractorAdvPhones = contractorAdvPhones;
	}

	public Long getContractor_call_cnt() {
		return contractor_call_cnt;
	}

	public void setContractor_call_cnt(Long contractor_call_cnt) {
		this.contractor_call_cnt = contractor_call_cnt;
	}

	public Double getContractor_charges() {
		return contractor_charges;
	}

	public void setContractor_charges(Double contractor_charges) {
		this.contractor_charges = contractor_charges;
	}

	public String getPrice_type_descr() {
		return price_type_descr;
	}

	public void setPrice_type_descr(String price_type_descr) {
		this.price_type_descr = price_type_descr;
	}

	public BigDecimal getRange_curr_price() {
		return range_curr_price;
	}

	public void setRange_curr_price(BigDecimal range_curr_price) {
		this.range_curr_price = range_curr_price;
	}

	public Long getCheckContractor() {
		return checkContractor;
	}

	public void setCheckContractor(Long checkContractor) {
		this.checkContractor = checkContractor;
	}
}