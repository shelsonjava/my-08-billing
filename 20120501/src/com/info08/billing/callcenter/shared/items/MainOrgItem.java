package com.info08.billing.callcenter.shared.items;

import java.io.Serializable;

public class MainOrgItem implements Serializable {

	private static final long serialVersionUID = 3545127200925659586L;

	private Long main_id;
	private Long main_master_id;
	private String org_name;
	private String identcode;
	private String new_identcode;
	private String director;
	private String legaladdress;
	private String founded;
	private String mail;
	private String webaddress;
	private String org_info;
	private String contactperson;
	private String dayoffs;
	private String workpersoncountity;
	private String ind;
	private String org_name_eng;
	private String real_address;
	private String note;
	private String workinghours;
	private Long deleted;
	private Long legal_statuse_id;
	private Long statuse;

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
	}

	public Long getMain_master_id() {
		return main_master_id;
	}

	public void setMain_master_id(Long main_master_id) {
		this.main_master_id = main_master_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getIdentcode() {
		return identcode;
	}

	public void setIdentcode(String identcode) {
		this.identcode = identcode;
	}

	public String getNew_identcode() {
		return new_identcode;
	}

	public void setNew_identcode(String new_identcode) {
		this.new_identcode = new_identcode;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getLegaladdress() {
		return legaladdress;
	}

	public void setLegaladdress(String legaladdress) {
		this.legaladdress = legaladdress;
	}

	public String getFounded() {
		return founded;
	}

	public void setFounded(String founded) {
		this.founded = founded;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getOrg_info() {
		return org_info;
	}

	public void setOrg_info(String org_info) {
		this.org_info = org_info;
	}

	public String getContactperson() {
		return contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getDayoffs() {
		return dayoffs;
	}

	public void setDayoffs(String dayoffs) {
		this.dayoffs = dayoffs;
	}

	public String getWorkpersoncountity() {
		return workpersoncountity;
	}

	public void setWorkpersoncountity(String workpersoncountity) {
		this.workpersoncountity = workpersoncountity;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getOrg_name_eng() {
		return org_name_eng;
	}

	public void setOrg_name_eng(String org_name_eng) {
		this.org_name_eng = org_name_eng;
	}

	public String getReal_address() {
		return real_address;
	}

	public void setReal_address(String real_address) {
		this.real_address = real_address;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getLegal_statuse_id() {
		return legal_statuse_id;
	}

	public void setLegal_statuse_id(Long legal_statuse_id) {
		this.legal_statuse_id = legal_statuse_id;
	}

	public Long getStatuse() {
		return statuse;
	}

	public void setStatuse(Long statuse) {
		this.statuse = statuse;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public String getWorkinghours() {
		return workinghours;
	}
	public void setWorkinghours(String workinghours) {
		this.workinghours = workinghours;
	}
}
