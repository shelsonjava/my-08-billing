package com.info08.billing.callcenterbk.shared.entity.correction;

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

/**
 * The persistent class for the LOG_PERSONELL_NOTES database table.
 * 
 */
@Entity
@Table(name = "CORR_USR_STAT")
public class CorrUsrStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CORR_USR_STAT", sequenceName = "SEQ_CORR_USR_STAT")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CORR_USR_STAT")
	@Column(name = "ID")
	private Long id;

	@Basic
	@Column(name = "USER_NAME")
	private String user_name;

	@Basic
	@Column(name = "ACT_DATE")
	private Timestamp act_date;

	@Basic
	@Column(name = "MMYY")
	private Long mmyy;

	
	@Basic
	@Column(name = "NEW_ORG")
	private Long new_org;
	
	@Basic
	@Column(name = "DEL_ORG")
	private Long del_org;
	
	@Basic
	@Column(name = "NEW_PHONE")
	private Long new_phone;
	
	@Basic
	@Column(name = "PHONE_UPD")
	private Long phone_upd;
	
	@Basic
	@Column(name = "DEL_PHONE")
	private Long del_phone;
	
	@Basic
	@Column(name = "ADDRESS")
	private Long address;
	
	@Basic
	@Column(name = "DIRECTOR")
	private Long director;
	
	@Basic
	@Column(name = "IDENT_CODE")
	private Long ident_code;
	
	
	@Basic
	@Column(name = "WORK_HOUR_DAYY_OFF")
	private Long work_hour_dayy_off;
	
	@Basic
	@Column(name = "WEB_SITE")
	private Long web_site;
	
	@Basic
	@Column(name = "EMAIL")
	private Long email;
	
	@Basic
	@Column(name = "SOC_NETWORK")
	private Long soc_network;
	
	@Basic
	@Column(name = "PART_BANK")
	private Long part_bank;
	
	@Basic
	@Column(name = "ORG_COMMENT")
	private Long org_comment;
	
	@Basic
	@Column(name = "FOUNDED_DATE")
	private Long founded_date;
	
	@Basic
	@Column(name = "OTHER")
	private Long other;
	
	@Basic
	@Column(name = "NEW_SUBS")
	private Long new_subs;
	
	@Basic
	@Column(name = "UPDATE_SUBS")
	private Long update_subs;
	
	@Basic
	@Column(name = "DEL_SUBS")
	private Long del_subs;
	
	

	public CorrUsrStat() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public Timestamp getAct_date() {
		return act_date;
	}



	public void setAct_date(Timestamp act_date) {
		this.act_date = act_date;
	}



	public Long getMmyy() {
		return mmyy;
	}



	public void setMmyy(Long mmyy) {
		this.mmyy = mmyy;
	}



	public Long getNew_org() {
		return new_org;
	}



	public void setNew_org(Long new_org) {
		this.new_org = new_org;
	}



	public Long getDel_org() {
		return del_org;
	}



	public void setDel_org(Long del_org) {
		this.del_org = del_org;
	}



	public Long getNew_phone() {
		return new_phone;
	}



	public void setNew_phone(Long new_phone) {
		this.new_phone = new_phone;
	}



	public Long getPhone_upd() {
		return phone_upd;
	}



	public void setPhone_upd(Long phone_upd) {
		this.phone_upd = phone_upd;
	}



	public Long getDel_phone() {
		return del_phone;
	}



	public void setDel_phone(Long del_phone) {
		this.del_phone = del_phone;
	}



	public Long getAddress() {
		return address;
	}



	public void setAddress(Long address) {
		this.address = address;
	}



	public Long getDirector() {
		return director;
	}



	public void setDirector(Long director) {
		this.director = director;
	}



	public Long getIdent_code() {
		return ident_code;
	}



	public void setIdent_code(Long ident_code) {
		this.ident_code = ident_code;
	}



	public Long getWork_hour_dayy_off() {
		return work_hour_dayy_off;
	}



	public void setWork_hour_dayy_off(Long work_hour_dayy_off) {
		this.work_hour_dayy_off = work_hour_dayy_off;
	}



	public Long getWeb_site() {
		return web_site;
	}



	public void setWeb_site(Long web_site) {
		this.web_site = web_site;
	}



	public Long getEmail() {
		return email;
	}



	public void setEmail(Long email) {
		this.email = email;
	}



	public Long getSoc_network() {
		return soc_network;
	}



	public void setSoc_network(Long soc_network) {
		this.soc_network = soc_network;
	}



	public Long getPart_bank() {
		return part_bank;
	}



	public void setPart_bank(Long part_bank) {
		this.part_bank = part_bank;
	}



	public Long getOrg_comment() {
		return org_comment;
	}



	public void setOrg_comment(Long org_comment) {
		this.org_comment = org_comment;
	}



	public Long getFounded_date() {
		return founded_date;
	}



	public void setFounded_date(Long founded_date) {
		this.founded_date = founded_date;
	}



	public Long getOther() {
		return other;
	}



	public void setOther(Long other) {
		this.other = other;
	}



	public Long getNew_subs() {
		return new_subs;
	}



	public void setNew_subs(Long new_subs) {
		this.new_subs = new_subs;
	}



	public Long getUpdate_subs() {
		return update_subs;
	}



	public void setUpdate_subs(Long update_subs) {
		this.update_subs = update_subs;
	}



	public Long getDel_subs() {
		return del_subs;
	}



	public void setDel_subs(Long del_subs) {
		this.del_subs = del_subs;
	}

	
}