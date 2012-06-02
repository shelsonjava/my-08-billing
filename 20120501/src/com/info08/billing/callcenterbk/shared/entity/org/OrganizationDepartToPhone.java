package com.info08.billing.callcenterbk.shared.entity.org;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ORGANIZATIONS database table.
 * 
 */
@Entity
@Table(name = "ORGANIZATION_DEPART_TO_PHONES")
public class OrganizationDepartToPhone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORGDEPTOPH_ID", sequenceName = "SEQ_ORGDEPTOPH_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORGDEPTOPH_ID")
	@Column(name = "ORG_DEP_TO_PH_ID")
	private Long org_dep_to_ph_id;

	@Basic
	@Column(name = "ORG_DEPARTMENT_ID")
	private Long org_department_id;

	@Basic
	@Column(name = "PHONE_NUMBER_ID")
	private Long phone_number_id;
	
	@Basic
	@Column(name = "HIDDEN_BY_REQUEST")
	private Long hidden_by_request;
	
	@Basic
	@Column(name = "PHONE_CONTRACT_TYPE")
	private Long phone_contract_type;
	
	@Basic
	@Column(name = "FOR_CONTACT")
	private Long for_contact;
	
	public OrganizationDepartToPhone() {
	}

	public Long getOrg_dep_to_ph_id() {
		return org_dep_to_ph_id;
	}

	public void setOrg_dep_to_ph_id(Long org_dep_to_ph_id) {
		this.org_dep_to_ph_id = org_dep_to_ph_id;
	}

	public Long getOrg_department_id() {
		return org_department_id;
	}

	public void setOrg_department_id(Long org_department_id) {
		this.org_department_id = org_department_id;
	}

	public Long getPhone_number_id() {
		return phone_number_id;
	}

	public void setPhone_number_id(Long phone_number_id) {
		this.phone_number_id = phone_number_id;
	}

	public Long getHidden_by_request() {
		return hidden_by_request;
	}

	public void setHidden_by_request(Long hidden_by_request) {
		this.hidden_by_request = hidden_by_request;
	}

	public Long getPhone_contract_type() {
		return phone_contract_type;
	}

	public void setPhone_contract_type(Long phone_contract_type) {
		this.phone_contract_type = phone_contract_type;
	}

	public Long getFor_contact() {
		return for_contact;
	}

	public void setFor_contact(Long for_contact) {
		this.for_contact = for_contact;
	}
}