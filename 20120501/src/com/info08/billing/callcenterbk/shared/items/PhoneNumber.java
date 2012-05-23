package com.info08.billing.callcenterbk.shared.items;

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
import javax.persistence.Transient;

@NamedQueries({ @NamedQuery(name = "PhoneNumber.getByPhoneNumber", query = "select e from PhoneNumber e where e.phone=:phone") })
@Entity
@Table(name = "PHONE_NUMBERS", schema = "ccare")
public class PhoneNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8063076539945318221L;

	@Id
	@SequenceGenerator(name = "SEQ_PHONE_NUMBERS_GENERATOR", sequenceName = "SEQ_PHONE_NUMBERS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHONE_NUMBERS_GENERATOR")
	@Column(name = "PHONE_NUMBER_ID")
	private Integer phone_number_Id;

	@Basic
	@Column(name = "PHONE")
	private String phone;

	@Basic
	@Column(name = "PHONE_TYPE_ID")
	private Integer phone_type_id;

	@Transient
	private String phone_type;

	@Basic
	@Column(name = "PHONE_STATE_ID")
	private Integer phone_state_id;

	private String phone_state;

	@Basic
	@Column(name = "IS_PARALLEL")
	private Integer is_parallel;

	@Transient
	private String is_parallel_descr;

	public Integer getPhone_Id() {
		return phone_number_Id;
	}

	public void setPhone_Id(Integer phone_Id) {
		this.phone_number_Id = phone_Id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPhone_state_id() {
		return phone_state_id;
	}

	public void setPhone_state_id(Integer phone_state_id) {
		this.phone_state_id = phone_state_id;
	}

	public Integer getPhone_type_id() {
		return phone_type_id;
	}

	public void setPhone_type_id(Integer phone_type_id) {
		this.phone_type_id = phone_type_id;
	}

	public String getPhone_state() {
		return phone_state;
	}

	public void setPhone_state(String phone_state) {
		this.phone_state = phone_state;
	}

	public String getPhone_type() {
		return phone_type;
	}

	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	public String getIs_parallel_descr() {
		return is_parallel_descr;
	}

	public void setIs_parallel_descr(String is_parallel_descr) {
		this.is_parallel_descr = is_parallel_descr;
	}

	public Integer getIs_parallel() {
		return is_parallel;
	}

	public void setIs_parallel(Integer is_parallel) {
		this.is_parallel = is_parallel;
	}

}
