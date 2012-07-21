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
 * The persistent class for the CORPORATE_CLIENTS database table.
 * 
 */
@Entity
@Table(name = "CORPORATE_CLIENTS")
public class CorporateClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CORPORATE_CLIENTS_GENERATOR", sequenceName = "SEQ_CORPORATE_CLIENTS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CORPORATE_CLIENTS_GENERATOR")
	@Column(name = "CORPORATE_CLIENT_ID")
	private Long corporate_client_id;

	@Basic
	@Column(name = "BLOCK")
	private Long block;

	@Basic
	@Column(name = "MAX_CALL_COUNT")
	private Long max_call_count;

	@Basic
	@Column(name = "CONTRACT_END_DATE")
	private Timestamp contract_end_date;

	@Basic
	@Column(name = "GOVERMENT")
	private Long goverment;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "CALL_PRICE")
	private BigDecimal call_price;

	@Basic
	@Column(name = "PRICE_TYPE")
	private Long price_type;

	@Basic
	@Column(name = "SMS_WARNING")
	private Long sms_warning;

	@Basic
	@Column(name = "CONTRACT_START_DATE")
	private Timestamp contract_start_date;

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

	public CorporateClient() {
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
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

	public Long getCorporate_client_id() {
		return corporate_client_id;
	}

	public void setCorporate_client_id(Long corporate_client_id) {
		this.corporate_client_id = corporate_client_id;
	}

	public Long getMax_call_count() {
		return max_call_count;
	}

	public void setMax_call_count(Long max_call_count) {
		this.max_call_count = max_call_count;
	}

	public Timestamp getContract_end_date() {
		return contract_end_date;
	}

	public void setContract_end_date(Timestamp contract_end_date) {
		this.contract_end_date = contract_end_date;
	}

	public Long getGoverment() {
		return goverment;
	}

	public void setGoverment(Long goverment) {
		this.goverment = goverment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getCall_price() {
		return call_price;
	}

	public void setCall_price(BigDecimal call_price) {
		this.call_price = call_price;
	}

	public Timestamp getContract_start_date() {
		return contract_start_date;
	}

	public void setContract_start_date(Timestamp contract_start_date) {
		this.contract_start_date = contract_start_date;
	}

}