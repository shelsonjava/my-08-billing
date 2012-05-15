package com.info08.billing.callcenterbk.shared.entity.callcenter;

import java.io.Serializable;

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

/**
 * The persistent class for the TREATMENTS database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = "Treatments.getTreatmentByPhoneNumber", query = "select e from Treatments e where e.phone_number = :phone_number ") })
@Entity
@Table(name = "TREATMENTS", schema = "ccare")
public class Treatments implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TREATMENTS_GENERATOR", sequenceName = "SEQ_TREATMENTS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TREATMENTS_GENERATOR")
	private Long treatment_id;

	@Basic
	@Column(name = "PHONE_NUMBER")
	private String phone_number;

	@Basic
	@Column(name = "TREATMENT")
	private String treatment;

	@Basic
	@Column(name = "VISIBLE")
	private Long visible;

	@Basic
	@Column(name = "GENDER")
	private Long gender;

	public Treatments() {
	}

	public Long getTreatment_id() {
		return treatment_id;
	}

	public void setTreatment_id(Long treatment_id) {
		this.treatment_id = treatment_id;
	}

	public Long getVisible() {
		return visible;
	}

	public void setVisible(Long visible) {
		this.visible = visible;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

}