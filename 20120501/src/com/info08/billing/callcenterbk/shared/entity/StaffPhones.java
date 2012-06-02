package com.info08.billing.callcenterbk.shared.entity;

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
@Table(name = "STAFF_PHONES")
public class StaffPhones implements java.io.Serializable {

	private static final long serialVersionUID = 4993844952773726969L;

	@Id
	@Column(name = "STAFF_PHONE_ID")
	@SequenceGenerator(name = "SEQ_STAFF_PHONES_GENERATOR", sequenceName = "SEQ_STAFF_PHONES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_PHONES_GENERATOR")
	private long staff_phone_id;

	@Basic
	@Column(name = "STAFF_ID")
	private Long staff_id;

	@Basic
	@Column(name = "STAFF_PHONE_TYPE_ID")
	private Long staff_phone_type_id;

	@Basic
	@Column(name = "STAFF_PHONE")
	private String staff_phone;

	@Transient
	private String staff_phone_type;

	@Transient
	private String loggedUserName;

	public StaffPhones() {
	}

	public long getStaff_phone_id() {
		return staff_phone_id;
	}

	public void setStaff_phone_id(long staff_phone_id) {
		this.staff_phone_id = staff_phone_id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public Long getStaff_phone_type_id() {
		return staff_phone_type_id;
	}

	public void setStaff_phone_type_id(Long staff_phone_type_id) {
		this.staff_phone_type_id = staff_phone_type_id;
	}

	public String getStaff_phone() {
		return staff_phone;
	}

	public void setStaff_phone(String staff_phone) {
		this.staff_phone = staff_phone;
	}

	public String getStaff_phone_type() {
		return staff_phone_type;
	}

	public void setStaff_phone_type(String staff_phone_type) {
		this.staff_phone_type = staff_phone_type;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}
