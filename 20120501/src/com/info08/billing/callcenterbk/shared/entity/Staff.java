package com.info08.billing.callcenterbk.shared.entity;

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

@Entity
@Table(name = "STAFF")
public class Staff implements java.io.Serializable {

	private static final long serialVersionUID = 4653344952773726969L;

	@Id
	@Column(name = "STAFF_ID")
	@SequenceGenerator(name = "SEQ_STAFF_GENERATOR", sequenceName = "SEQ_STAFF")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_GENERATOR")
	private long staff_id;

	@Basic
	@Column(name = "DOC_NUM")
	private String doc_num;

	@Basic
	@Column(name = "LAST_NAME")
	private String last_name;

	@Basic
	@Column(name = "FIRST_NAME")
	private String first_name;

	@Basic
	@Column(name = "GENDER_ID")
	private Long gender_id;

	@Basic
	@Column(name = "NATIONALITY_ID")
	private Long nationality_id;

	@Basic
	@Column(name = "DOB")
	private Timestamp dob;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "FAMILY_STATUS_ID")
	private Long family_status_id;

	@Basic
	@Column(name = "start_DATE")
	private Timestamp start_date;

	@Basic
	@Column(name = "DEPARTMENT_ID")
	private Long department_id;

	@Basic
	@Column(name = "ADDRESS_ID")
	private Long address_id;

	@Basic
	@Column(name = "DOCUMENT_ADDRESS_ID")
	private Long document_address_id;
	
	@Basic
	@Column(name = "IMAGE_ID")
	private Long image_id;

	@Transient
	private String loggedUserName;

	@Transient
	private String gender_descr;

	@Transient
	private String nationality_descr;

	@Transient
	private String family_status_descr;

	@Transient
	private String department_name;

	@Transient
	private String dob_descr;

	@Transient
	private String start_date_descr;

	public Staff() {
	}

	public long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}

	public String getDoc_num() {
		return doc_num;
	}

	public void setDoc_num(String doc_num) {
		this.doc_num = doc_num;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public Long getGender_id() {
		return gender_id;
	}

	public void setGender_id(Long gender_id) {
		this.gender_id = gender_id;
	}

	public Long getNationality_id() {
		return nationality_id;
	}

	public void setNationality_id(Long nationality_id) {
		this.nationality_id = nationality_id;
	}

	public Timestamp getDob() {
		return dob;
	}

	public void setDob(Timestamp dob) {
		this.dob = dob;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getFamily_status_id() {
		return family_status_id;
	}

	public void setFamily_status_id(Long family_status_id) {
		this.family_status_id = family_status_id;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public String getStart_date_descr() {
		return start_date_descr;
	}

	public void setStart_date_descr(String start_date_descr) {
		this.start_date_descr = start_date_descr;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getGender_descr() {
		return gender_descr;
	}

	public void setGender_descr(String gender_descr) {
		this.gender_descr = gender_descr;
	}

	public String getNationality_descr() {
		return nationality_descr;
	}

	public void setNationality_descr(String nationality_descr) {
		this.nationality_descr = nationality_descr;
	}

	public String getFamily_status_descr() {
		return family_status_descr;
	}

	public void setFamily_status_descr(String family_status_descr) {
		this.family_status_descr = family_status_descr;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDob_descr() {
		return dob_descr;
	}

	public void setDob_descr(String dob_descr) {
		this.dob_descr = dob_descr;
	}

	public Long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	public Long getDocument_address_id() {
		return document_address_id;
	}

	public void setDocument_address_id(Long document_address_id) {
		this.document_address_id = document_address_id;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}

}
