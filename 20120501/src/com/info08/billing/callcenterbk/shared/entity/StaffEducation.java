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
@Table(name = "STAFF_EDUCATION")
public class StaffEducation implements java.io.Serializable {

	private static final long serialVersionUID = 4673344952773726969L;

	@Id
	@Column(name = "STAFF_EDUCATION_ID")
	@SequenceGenerator(name = "SEQ_STAFF_EDUCATION_GENERATOR", sequenceName = "SEQ_STAFF_EDUCATION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_EDUCATION_GENERATOR")
	private long staff_education_id;

	@Basic
	@Column(name = "STAFF_ID")
	private Long staff_id;

	@Basic
	@Column(name = "COLLEGE_NAME")
	private String college_name;

	@Basic
	@Column(name = "FACULTY_NAME")
	private String faculty_name;

	@Basic
	@Column(name = "DEGREE_DESCR_ID")
	private Long degree_descr_id;

	@Basic
	@Column(name = "START_YEAR")
	private Long start_year;

	@Basic
	@Column(name = "END_YEAR")
	private Long end_year;

	@Transient
	private String loggedUserName;

	@Transient
	private String degree_descr;

	public StaffEducation() {
	}

	public long getStaff_education_id() {
		return staff_education_id;
	}

	public void setStaff_education_id(long staff_education_id) {
		this.staff_education_id = staff_education_id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	public String getFaculty_name() {
		return faculty_name;
	}

	public void setFaculty_name(String faculty_name) {
		this.faculty_name = faculty_name;
	}

	public Long getDegree_descr_id() {
		return degree_descr_id;
	}

	public void setDegree_descr_id(Long degree_descr_id) {
		this.degree_descr_id = degree_descr_id;
	}

	public Long getStart_year() {
		return start_year;
	}

	public void setStart_year(Long start_year) {
		this.start_year = start_year;
	}

	public Long getEnd_year() {
		return end_year;
	}

	public void setEnd_year(Long end_year) {
		this.end_year = end_year;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getDegree_descr() {
		return degree_descr;
	}

	public void setDegree_descr(String degree_descr) {
		this.degree_descr = degree_descr;
	}

}
