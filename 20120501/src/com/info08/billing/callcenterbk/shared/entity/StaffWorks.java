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
@Table(name = "STAFF_WORKS")
public class StaffWorks implements java.io.Serializable {

	private static final long serialVersionUID = 4993841952773726969L;

	@Id
	@Column(name = "STAFF_WORK_ID")
	@SequenceGenerator(name = "SEQ_STAFF_WORKS_GENERATOR", sequenceName = "SEQ_STAFF_WORKS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_WORKS_GENERATOR")
	private long staff_work_id;

	@Basic
	@Column(name = "STAFF_ID")
	private Long staff_id;

	@Basic
	@Column(name = "WORK_PLACE")
	private String work_place;

	@Basic
	@Column(name = "WORK_POSITION")
	private String work_position;

	@Basic
	@Column(name = "WORK_START_DATE")
	private Timestamp work_start_date;

	@Basic
	@Column(name = "WORK_END_DATE")
	private Timestamp work_end_date;

	@Transient
	private String work_start_date_descr;

	@Transient
	private String work_end_date_descr;

	@Transient
	private String loggedUserName;

	public StaffWorks() {
	}

	public long getStaff_work_id() {
		return staff_work_id;
	}

	public void setStaff_work_id(long staff_work_id) {
		this.staff_work_id = staff_work_id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public String getWork_place() {
		return work_place;
	}

	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}

	public String getWork_position() {
		return work_position;
	}

	public void setWork_position(String work_position) {
		this.work_position = work_position;
	}

	public Timestamp getWork_start_date() {
		return work_start_date;
	}

	public void setWork_start_date(Timestamp work_start_date) {
		this.work_start_date = work_start_date;
	}

	public Timestamp getWork_end_date() {
		return work_end_date;
	}

	public void setWork_end_date(Timestamp work_end_date) {
		this.work_end_date = work_end_date;
	}

	public String getWork_start_date_descr() {
		return work_start_date_descr;
	}

	public void setWork_start_date_descr(String work_start_date_descr) {
		this.work_start_date_descr = work_start_date_descr;
	}

	public String getWork_end_date_descr() {
		return work_end_date_descr;
	}

	public void setWork_end_date_descr(String work_end_date_descr) {
		this.work_end_date_descr = work_end_date_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
