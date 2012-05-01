package com.info08.billing.callcenterbk.shared.entity.discovery;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the DISCOVER database table.
 * 
 */
@NamedQueries({ 
		@NamedQuery(
					name = "Discover.getLocked", 
					query = "select e from Discover e where e.discover_id = :discId and e.discover_type_id <> 4  and trunc(e.rec_date) >= trunc(sysdate-14) and e.execution_status = 0 ") 
})
@Entity
@Table(name = "DISCOVER", schema = "INFO")
public class Discover implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DISCOVER_DISCOVERID_GENERATOR", sequenceName = "DISCOVER_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCOVER_DISCOVERID_GENERATOR")
	@Column(name = "DISCOVER_ID")
	private Long discover_id;

	@Basic
	@Column(name = "CALL_ID")
	private String call_id;

	@Basic
	@Column(name = "CCR")
	private Long ccr;

	@Basic
	@Column(name = "CONTACT_PERSON")
	private String contact_person;

	@Basic
	@Column(name = "CONTACT_PHONE")
	private String contact_phone;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "DISCOVER_TXT")
	private String discover_txt;

	@Basic
	@Column(name = "DISCOVER_TYPE_ID")
	private Long discover_type_id;

	@Basic
	@Column(name = "EXECUTION_STATUS")
	private Long execution_status;

	@Basic
	@Column(name = "ISCORRECT")
	private Long iscorrect;

	@Basic
	@Column(name = "PHONE")
	private String phone;

	@Basic
	@Column(name = "REC_DATE")
	private Date rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "RESPONSE_TYPE_ID")
	private Long response_type_id;

	@Basic
	@Column(name = "UPD_DATE")
	private Date upd_date;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Basic
	@Column(name = "IS_LOCKED")
	private Long is_locked;

	@Transient
	private String discover_type;

	@Transient
	private String response_type;

	@Transient
	private Long personnel_id;

	@Transient
	private Long personnel_id1;

	@Transient
	private Date start_date;

	public Discover() {
	}

	public Long getDiscover_id() {
		return discover_id;
	}

	public void setDiscover_id(Long discover_id) {
		this.discover_id = discover_id;
	}

	public String getCall_id() {
		return call_id;
	}

	public void setCall_id(String call_id) {
		this.call_id = call_id;
	}

	public Long getCcr() {
		return ccr;
	}

	public void setCcr(Long ccr) {
		this.ccr = ccr;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getDiscover_txt() {
		return discover_txt;
	}

	public void setDiscover_txt(String discover_txt) {
		this.discover_txt = discover_txt;
	}

	public Long getDiscover_type_id() {
		return discover_type_id;
	}

	public void setDiscover_type_id(Long discover_type_id) {
		this.discover_type_id = discover_type_id;
	}

	public Long getExecution_status() {
		return execution_status;
	}

	public void setExecution_status(Long execution_status) {
		this.execution_status = execution_status;
	}

	public Long getIscorrect() {
		return iscorrect;
	}

	public void setIscorrect(Long iscorrect) {
		this.iscorrect = iscorrect;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRec_date() {
		return rec_date;
	}

	public void setRec_date(Date rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public Long getResponse_type_id() {
		return response_type_id;
	}

	public void setResponse_type_id(Long response_type_id) {
		this.response_type_id = response_type_id;
	}

	public Date getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getDiscover_type() {
		return discover_type;
	}

	public void setDiscover_type(String discover_type) {
		this.discover_type = discover_type;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public Long getIs_locked() {
		return is_locked;
	}

	public void setIs_locked(Long is_locked) {
		this.is_locked = is_locked;
	}

	public Long getPersonnel_id() {
		return personnel_id;
	}

	public void setPersonnel_id(Long personnel_id) {
		this.personnel_id = personnel_id;
	}

	public Long getPersonnel_id1() {
		return personnel_id1;
	}

	public void setPersonnel_id1(Long personnel_id1) {
		this.personnel_id1 = personnel_id1;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
}