package com.info08.billing.callcenterbk.shared.entity.org;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ORGANIZATIONS database table.
 * 
 */
@Entity
@Table(name = "ORGANIZATIONS", schema = "ccare")
public class Organizations implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
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
	@Column(name = "IMPORTANT")
	private Long important;

	@Basic
	@Column(name = "PRIORITY")
	private Long priority;

	@Basic
	@Column(name = "SUPER_PRIORITY")
	private Long super_priority;

	public Organizations() {
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

	public Long getImportant() {
		return important;
	}

	public void setImportant(Long important) {
		this.important = important;
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

}