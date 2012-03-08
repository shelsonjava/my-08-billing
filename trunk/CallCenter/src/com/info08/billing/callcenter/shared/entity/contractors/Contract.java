package com.info08.billing.callcenter.shared.entity.contractors;

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
@Table(name = "CONTRACTS", schema = "INFO")
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTRACTS_CONTRACTID_GENERATOR", sequenceName = "CONTRACT_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTS_CONTRACTID_GENERATOR")
	@Column(name = "CONTRACT_ID")
	private Long contract_id;

	@Basic
	@Column(name = "BLOCK")
	private Long block;

	@Basic
	@Column(name = "CRITICAL_NUMBER")
	private Long critical_number;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "END_DATE")
	private Timestamp end_date;

	@Basic
	@Column(name = "IS_BUDGET")
	private Long is_budget;

	@Basic
	@Column(name = "MAIN_DETAIL_ID")
	private Long main_detail_id;

	@Basic
	@Column(name = "MAIN_ID")
	private Long main_id;

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
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "SMS_WARNING")
	private Long sms_warning;

	@Basic
	@Column(name = "START_DATE")
	private Timestamp start_date;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;
	
	@Basic
	@Column(name = "PHONE_LIST_TYPE")
	private Long phone_list_type;
	
	@Basic
	@Column(name = "RANGE_CURR_PRICE")
	private BigDecimal range_curr_price;

	@Transient
	private String loggedUserName;

	@Transient
	private String orgName;

	@Transient
	private String orgDepName;
	
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

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
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

	public Long getMain_detail_id() {
		return main_detail_id;
	}

	public void setMain_detail_id(Long main_detail_id) {
		this.main_detail_id = main_detail_id;
	}

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
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

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
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

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
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

	public String getOrgDepName() {
		return orgDepName;
	}

	public void setOrgDepName(String orgDepName) {
		this.orgDepName = orgDepName;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getContractorAdvPrices() {
		return contractorAdvPrices;
	}

	public void setContractorAdvPrices(
			LinkedHashMap<String, LinkedHashMap<String, String>> contractorAdvPrices) {
		this.contractorAdvPrices = contractorAdvPrices;
	}

	public Long getPhone_list_type() {
		return phone_list_type;
	}

	public void setPhone_list_type(Long phone_list_type) {
		this.phone_list_type = phone_list_type;
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