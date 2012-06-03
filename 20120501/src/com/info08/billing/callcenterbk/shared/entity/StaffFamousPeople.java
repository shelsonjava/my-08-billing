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
@Table(name = "STAFF_FAMOUS_PEOPLE")
public class StaffFamousPeople implements java.io.Serializable {

	private static final long serialVersionUID = 4693844952113726969L;

	@Id
	@Column(name = "STAFF_FAMOUS_PEOPLE_ID")
	@SequenceGenerator(name = "SEQ_STAFF_FAMOUS_PEOPLE_GENERATOR", sequenceName = "SEQ_STAFF_FAMOUS_PEOPLE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_FAMOUS_PEOPLE_GENERATOR")
	private long staff_famous_people_id;

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
	@Column(name = "RELATION_TYPE_ID")
	private Long relation_type_id;

	@Basic
	@Column(name = "SPHERE_OF_ACTIVITY_ID")
	private Long sphere_of_activity_id;

	@Transient
	private String relation_type_id_descr;

	@Transient
	private String sphere_of_activity_id_descr;

	@Transient
	private String loggedUserName;

	public StaffFamousPeople() {
	}

	public long getStaff_famous_people_id() {
		return staff_famous_people_id;
	}

	public void setStaff_famous_people_id(long staff_famous_people_id) {
		this.staff_famous_people_id = staff_famous_people_id;
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

	public Long getRelation_type_id() {
		return relation_type_id;
	}

	public void setRelation_type_id(Long relation_type_id) {
		this.relation_type_id = relation_type_id;
	}

	public Long getSphere_of_activity_id() {
		return sphere_of_activity_id;
	}

	public void setSphere_of_activity_id(Long sphere_of_activity_id) {
		this.sphere_of_activity_id = sphere_of_activity_id;
	}

	public String getRelation_type_id_descr() {
		return relation_type_id_descr;
	}

	public void setRelation_type_id_descr(String relation_type_id_descr) {
		this.relation_type_id_descr = relation_type_id_descr;
	}

	public String getSphere_of_activity_id_descr() {
		return sphere_of_activity_id_descr;
	}

	public void setSphere_of_activity_id_descr(
			String sphere_of_activity_id_descr) {
		this.sphere_of_activity_id_descr = sphere_of_activity_id_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
