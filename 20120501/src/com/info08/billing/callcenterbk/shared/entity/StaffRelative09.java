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
@Table(name = "STAFF_RELATIVE_09")
public class StaffRelative09 implements java.io.Serializable {

	private static final long serialVersionUID = 4693844952773726969L;

	@Id
	@Column(name = "STAFF_RELATIVE_09_ID")
	@SequenceGenerator(name = "SEQ_STAFF_RELATIVE_09_GENERATOR", sequenceName = "SEQ_STAFF_RELATIVE_09")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_RELATIVE_09_GENERATOR")
	private long staff_relative_09_id;

	@Basic
	@Column(name = "STAFF_ID")
	private Long staff_id;

	@Basic
	@Column(name = "FIRST_NAME")
	private String first_name;

	@Basic
	@Column(name = "LAST_NAME")
	private String last_name;

	@Basic
	@Column(name = "POSITION")
	private String position;

	@Transient
	private String loggedUserName;

	public StaffRelative09() {
	}

	public long getStaff_relative_09_id() {
		return staff_relative_09_id;
	}

	public void setStaff_relative_09_id(long staff_relative_09_id) {
		this.staff_relative_09_id = staff_relative_09_id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
