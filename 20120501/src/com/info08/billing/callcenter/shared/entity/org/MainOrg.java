package com.info08.billing.callcenter.shared.entity.org;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the MAIN_ORGS database table.
 * 
 */
@Entity
@Table(name="MAIN_ORGS",schema="INFO")
public class MainOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MAIN_ID")
	private Long main_id;

	@Basic	
	@Column(name="CONTACTPERSON")
	private String contactperson;

	@Basic
	@Column(name="DAYOFFS")
	private String dayoffs;

	@Basic
	@Column(name="DIRECTOR")
	private String director;

	@Basic
	@Column(name="EXTRA_PRIORITY")
	private Long extra_priority;

	@Basic
	@Column(name="FOUNDED")
	private String founded;

	@Basic
	@Column(name="IDENTCODE")
	private String identcode;

	@Basic
	@Column(name="IND")
	private String ind;

	@Basic
	@Column(name="LEGAL_STATUSE_ID")
	private Long legal_statuse_id;

	@Basic
	@Column(name="LEGALADDRESS")
	private String legaladdress;

	@Basic
	@Column(name="MAIL")
	private String mail;

	@Basic
	@Column(name="NEW_IDENTCODE")
	private String new_identcode;

	@Basic
	@Column(name="NOTE")
	private String note;

	@Basic
	@Column(name="NOTE_CRIT")
	private Long note_crit;

	@Basic
	@Column(name="ORG_INFO")
	private String org_info;

	@Basic
	@Column(name="ORG_NAME")
	private String org_name;

	@Basic
	@Column(name="ORG_NAME_ENG")
	private String org_name_eng;

	@Basic
	@Column(name="PARTNERBANK_ID")
	private Long partnerbank_id;

	@Basic
	@Column(name="STATUSE")
	private Long statuse;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

	@Basic
	@Column(name="WEBADDRESS")
	private String webaddress;

	@Basic
	@Column(name="WORKINGHOURS")
	private String workinghours;

	@Basic
	@Column(name="WORKPERSONCOUNTITY")
	private String workpersoncountity;

    public MainOrg() {
    }

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Long getExtra_priority() {
		return extra_priority;
	}

	public void setExtra_priority(Long extra_priority) {
		this.extra_priority = extra_priority;
	}

	public String getFounded() {
		return founded;
	}

	public void setFounded(String founded) {
		this.founded = founded;
	}

	public String getIdentcode() {
		return identcode;
	}

	public void setIdentcode(String identcode) {
		this.identcode = identcode;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public Long getLegal_statuse_id() {
		return legal_statuse_id;
	}

	public void setLegal_statuse_id(Long legal_statuse_id) {
		this.legal_statuse_id = legal_statuse_id;
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

	public String getNew_identcode() {
		return new_identcode;
	}

	public void setNew_identcode(String new_identcode) {
		this.new_identcode = new_identcode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getNote_crit() {
		return note_crit;
	}

	public void setNote_crit(Long note_crit) {
		this.note_crit = note_crit;
	}

	public String getOrg_info() {
		return org_info;
	}

	public void setOrg_info(String org_info) {
		this.org_info = org_info;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_name_eng() {
		return org_name_eng;
	}

	public void setOrg_name_eng(String org_name_eng) {
		this.org_name_eng = org_name_eng;
	}

	public Long getPartnerbank_id() {
		return partnerbank_id;
	}

	public void setPartnerbank_id(Long partnerbank_id) {
		this.partnerbank_id = partnerbank_id;
	}

	public Long getStatuse() {
		return statuse;
	}

	public void setStatuse(Long statuse) {
		this.statuse = statuse;
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

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getWorkinghours() {
		return workinghours;
	}

	public void setWorkinghours(String workinghours) {
		this.workinghours = workinghours;
	}

	public String getWorkpersoncountity() {
		return workpersoncountity;
	}

	public void setWorkpersoncountity(String workpersoncountity) {
		this.workpersoncountity = workpersoncountity;
	}
}