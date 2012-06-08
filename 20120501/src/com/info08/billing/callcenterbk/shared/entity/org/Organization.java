package com.info08.billing.callcenterbk.shared.entity.org;

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
 * The persistent class for the ORGANIZATIONS database table.
 * 
 */
@Entity
@Table(name = "ORGANIZATIONS")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORGANIZATION_ID", sequenceName = "SEQ_ORGANIZATION_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORGANIZATION_ID")
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "PARRENT_ORGANIZATION_ID")
	private Long parrent_organization_id;

	@Basic
	@Column(name = "ORGANIZATION_NAME")
	private String organization_name;

	@Basic
	@Column(name = "ORGANIZATION_NAME_ENG")
	private String organization_name_eng;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "ADDITIONAL_INFO")
	private String additional_info;

	@Basic
	@Column(name = "IDENT_CODE")
	private String ident_code;

	@Basic
	@Column(name = "IDENT_CODE_NEW")
	private String ident_code_new;

	@Basic
	@Column(name = "ORGANIZATION_INDEX")
	private String organization_index;

	@Basic
	@Column(name = "CHIEF")
	private String chief;

	@Basic
	@Column(name = "CONTACT_PERSON")
	private String contact_person;

	@Basic
	@Column(name = "WORK_HOURS")
	private String work_hours;

	@Basic
	@Column(name = "FOUND_DATE")
	private Timestamp found_date;

	@Basic
	@Column(name = "EMAIL_ADDRESS")
	private String email_address;

	@Basic
	@Column(name = "WEB_ADDRESS")
	private String web_address;
	
	@Basic
	@Column(name = "SOCIAL_ADDRESS")
	private String social_address;

	@Basic
	@Column(name = "DAY_OFFS")
	private Long day_offs;

	@Basic
	@Column(name = "STATUS")
	private Long status;

	@Basic
	@Column(name = "LEGAL_FORM_DESC_ID")
	private Long legal_form_desc_id;

	@Basic
	@Column(name = "STAFF_COUNT")
	private Long staff_count;

	@Basic
	@Column(name = "IMPORTANT_REMARK")
	private Long important_remark;

	@Basic
	@Column(name = "PRIORITY")
	private Long priority;

	@Basic
	@Column(name = "SUPER_PRIORITY")
	private Long super_priority;

	@Basic
	@Column(name = "OLD_LEGAL_ADDRESS")
	private String old_legal_address;

	@Basic
	@Column(name = "LEGAL_ADDRESS_ID")
	private Long legal_address_id;

	@Basic
	@Column(name = "PHYSICAL_ADDRESS_ID")
	private Long physical_address_id;
	
	
	@Basic
	@Column(name = "PH_TOWN_ID")
	private Long ph_town_id;

	@Transient
	private String loggedUserName;

	@Transient
	private String day_offs_descr;

	@Transient
	private String legal_form_desc;

	@Transient
	private String real_address_descr;

	@Transient
	private String town_district;

	@Transient
	private String street_index_text;

	@Transient
	private String full_address;

	@Transient
	private Long cust_order;

	@Transient
	private String tree_org_child;

	@Transient
	private String tree_org_parrent;

	@Transient
	private String full_address_not_hidden;

	@Transient
	private String org_allert_by_buss_det;

	@Transient
	private Long street_id;

	@Transient
	private String town_name;

	@Transient
	private String street_location;

	@Transient
	private Long town_id;

	@Transient
	private Long town_district_id;

	@Transient
	private String street_name;

	@Transient
	private Long hidden_by_request;

	@Transient
	private String block;

	@Transient
	private String appt;

	@Transient
	private String descr;

	@Transient
	private String anumber;

	public Organization() {
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public Long getParrent_organization_id() {
		return parrent_organization_id;
	}

	public void setParrent_organization_id(Long parrent_organization_id) {
		this.parrent_organization_id = parrent_organization_id;
	}

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getOrganization_name_eng() {
		return organization_name_eng;
	}

	public void setOrganization_name_eng(String organization_name_eng) {
		this.organization_name_eng = organization_name_eng;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdditional_info() {
		return additional_info;
	}

	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}

	public String getIdent_code() {
		return ident_code;
	}

	public void setIdent_code(String ident_code) {
		this.ident_code = ident_code;
	}

	public String getIdent_code_new() {
		return ident_code_new;
	}

	public void setIdent_code_new(String ident_code_new) {
		this.ident_code_new = ident_code_new;
	}

	public String getOrganization_index() {
		return organization_index;
	}

	public void setOrganization_index(String organization_index) {
		this.organization_index = organization_index;
	}

	public String getChief() {
		return chief;
	}

	public void setChief(String chief) {
		this.chief = chief;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getWork_hours() {
		return work_hours;
	}

	public void setWork_hours(String work_hours) {
		this.work_hours = work_hours;
	}

	public Timestamp getFound_date() {
		return found_date;
	}

	public void setFound_date(Timestamp found_date) {
		this.found_date = found_date;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public String getWeb_address() {
		return web_address;
	}

	public void setWeb_address(String web_address) {
		this.web_address = web_address;
	}

	public Long getDay_offs() {
		return day_offs;
	}

	public void setDay_offs(Long day_offs) {
		this.day_offs = day_offs;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getLegal_form_desc_id() {
		return legal_form_desc_id;
	}

	public void setLegal_form_desc_id(Long legal_form_desc_id) {
		this.legal_form_desc_id = legal_form_desc_id;
	}

	public Long getStaff_count() {
		return staff_count;
	}

	public void setStaff_count(Long staff_count) {
		this.staff_count = staff_count;
	}

	public Long getImportant_remark() {
		return important_remark;
	}

	public void setImportant_remark(Long important_remark) {
		this.important_remark = important_remark;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Long getSuper_priority() {
		return super_priority;
	}

	public void setSuper_priority(Long super_priority) {
		this.super_priority = super_priority;
	}

	public String getOld_legal_address() {
		return old_legal_address;
	}

	public void setOld_legal_address(String old_legal_address) {
		this.old_legal_address = old_legal_address;
	}

	public Long getLegal_address_id() {
		return legal_address_id;
	}

	public void setLegal_address_id(Long legal_address_id) {
		this.legal_address_id = legal_address_id;
	}

	public Long getPhysical_address_id() {
		return physical_address_id;
	}

	public void setPhysical_address_id(Long physical_address_id) {
		this.physical_address_id = physical_address_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getDay_offs_descr() {
		return day_offs_descr;
	}

	public void setDay_offs_descr(String day_offs_descr) {
		this.day_offs_descr = day_offs_descr;
	}

	public String getLegal_form_desc() {
		return legal_form_desc;
	}

	public void setLegal_form_desc(String legal_form_desc) {
		this.legal_form_desc = legal_form_desc;
	}

	public String getReal_address_descr() {
		return real_address_descr;
	}

	public void setReal_address_descr(String real_address_descr) {
		this.real_address_descr = real_address_descr;
	}

	public String getTown_district() {
		return town_district;
	}

	public void setTown_district(String town_district) {
		this.town_district = town_district;
	}

	public String getStreet_index_text() {
		return street_index_text;
	}

	public void setStreet_index_text(String street_index_text) {
		this.street_index_text = street_index_text;
	}

	public String getFull_address() {
		return full_address;
	}

	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}

	public Long getCust_order() {
		return cust_order;
	}

	public void setCust_order(Long cust_order) {
		this.cust_order = cust_order;
	}

	public String getTree_org_child() {
		return tree_org_child;
	}

	public void setTree_org_child(String tree_org_child) {
		this.tree_org_child = tree_org_child;
	}

	public String getTree_org_parrent() {
		return tree_org_parrent;
	}

	public void setTree_org_parrent(String tree_org_parrent) {
		this.tree_org_parrent = tree_org_parrent;
	}

	public String getFull_address_not_hidden() {
		return full_address_not_hidden;
	}

	public void setFull_address_not_hidden(String full_address_not_hidden) {
		this.full_address_not_hidden = full_address_not_hidden;
	}

	public String getOrg_allert_by_buss_det() {
		return org_allert_by_buss_det;
	}

	public void setOrg_allert_by_buss_det(String org_allert_by_buss_det) {
		this.org_allert_by_buss_det = org_allert_by_buss_det;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public String getStreet_location() {
		return street_location;
	}

	public void setStreet_location(String street_location) {
		this.street_location = street_location;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public Long getTown_district_id() {
		return town_district_id;
	}

	public void setTown_district_id(Long town_district_id) {
		this.town_district_id = town_district_id;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public Long getHidden_by_request() {
		return hidden_by_request;
	}

	public void setHidden_by_request(Long hidden_by_request) {
		this.hidden_by_request = hidden_by_request;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getAppt() {
		return appt;
	}

	public void setAppt(String appt) {
		this.appt = appt;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAnumber() {
		return anumber;
	}

	public void setAnumber(String anumber) {
		this.anumber = anumber;
	}

	public String getSocial_address() {
		return social_address;
	}

	public void setSocial_address(String social_address) {
		this.social_address = social_address;
	}

	public Long getPh_town_id() {
		return ph_town_id;
	}

	public void setPh_town_id(Long ph_town_id) {
		this.ph_town_id = ph_town_id;
	}	
}