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
@Table(name = "STAFF_COMPUTER_SKILLS")
public class StaffComputerSkill implements java.io.Serializable {

	private static final long serialVersionUID = 4693344952773726969L;

	@Id
	@Column(name = "STAFF_COMP_SKILL_ID")
	@SequenceGenerator(name = "SEQ_STAFF_COMPUTER_SKILLS_GENERATOR", sequenceName = "SEQ_STAFF_COMPUTER_SKILLS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_COMPUTER_SKILLS_GENERATOR")
	private long staff_comp_skill_id;

	@Basic
	@Column(name = "STAFF_ID")
	private Long staff_id;

	@Basic
	@Column(name = "SOFTWARE")
	private String software;

	@Basic
	@Column(name = "TRAINING_COURSE")
	private String training_course;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Transient
	private String loggedUserName;

	public StaffComputerSkill() {
	}

	public long getStaff_comp_skill_id() {
		return staff_comp_skill_id;
	}

	public void setStaff_comp_skill_id(long staff_comp_skill_id) {
		this.staff_comp_skill_id = staff_comp_skill_id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getTraining_course() {
		return training_course;
	}

	public void setTraining_course(String training_course) {
		this.training_course = training_course;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
