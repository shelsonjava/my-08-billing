package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrgInfoByPhone implements Serializable {

	private static final long serialVersionUID = 7026086622425435823L;
	private Long organization_id;
	private String org_name;
	private String note;
	private String workinghours;
	private String director;
	private String identcode;
	private String founded;
	private String legaladdress;
	private String mail;
	private String webaddress;
	private String org_info;
	private String contactperson;
	private String dayoffs;
	private Long statuse;
	private Long legal_statuse_id;
	private String legal_statuse;
	private Long partnerbank_id;
	private String partnerbank;
	private String workpersoncountity;
	private String upd_user;
	private Timestamp upd_date;
	private String ind;
	private Long note_crit;
	private String org_name_eng;
	private Long extra_priority;
	private String new_identcode;

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getIdentcode() {
		return identcode;
	}

	public void setIdentcode(String identcode) {
		this.identcode = identcode;
	}

	public String getFounded() {
		return founded;
	}

	public void setFounded(String founded) {
		this.founded = founded;
	}

	public String getLegaladdress() {
		return legaladdress;
	}

	public void setLegaladdress(String legaladdress) {
		this.legaladdress = legaladdress;
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

	public Long getStatuse() {
		return statuse;
	}

	public void setStatuse(Long statuse) {
		this.statuse = statuse;
	}

	public Long getLegal_statuse_id() {
		return legal_statuse_id;
	}

	public void setLegal_statuse_id(Long legal_statuse_id) {
		this.legal_statuse_id = legal_statuse_id;
	}

	public String getLegal_statuse() {
		return legal_statuse;
	}

	public void setLegal_statuse(String legal_statuse) {
		this.legal_statuse = legal_statuse;
	}

	public Long getPartnerbank_id() {
		return partnerbank_id;
	}

	public void setPartnerbank_id(Long partnerbank_id) {
		this.partnerbank_id = partnerbank_id;
	}

	public String getPartnerbank() {
		return partnerbank;
	}

	public void setPartnerbank(String partnerbank) {
		this.partnerbank = partnerbank;
	}

	public String getWorkpersoncountity() {
		return workpersoncountity;
	}

	public void setWorkpersoncountity(String workpersoncountity) {
		this.workpersoncountity = workpersoncountity;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public Long getNote_crit() {
		return note_crit;
	}

	public void setNote_crit(Long note_crit) {
		this.note_crit = note_crit;
	}

	public String getOrg_name_eng() {
		return org_name_eng;
	}

	public void setOrg_name_eng(String org_name_eng) {
		this.org_name_eng = org_name_eng;
	}

	public Long getExtra_priority() {
		return extra_priority;
	}

	public void setExtra_priority(Long extra_priority) {
		this.extra_priority = extra_priority;
	}

	public String getNew_identcode() {
		return new_identcode;
	}

	public void setNew_identcode(String new_identcode) {
		this.new_identcode = new_identcode;
	}
}
