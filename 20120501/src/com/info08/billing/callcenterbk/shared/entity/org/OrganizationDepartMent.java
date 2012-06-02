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
@Table(name = "ORGANIZATION_DEPARTMENT")
public class OrganizationDepartMent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORGANIZATION_DEPARTMENT", sequenceName = "SEQ_ORGANIZATION_DEPARTMENT")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORGANIZATION_DEPARTMENT")
	@Column(name = "ORG_DEPARTMENT_ID")
	private Long org_department_id;

	@Basic
	@Column(name = "PARRENT_DEPARTMENT_ID")
	private Long parrent_department_id;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;
	
	@Basic
	@Column(name = "DEPARTMENT")
	private String department;
	
	@Basic
	@Column(name = "INNER_ORDER")
	private Long inner_order;
	
	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "LEGAL_ADDRESS_ID")
	private Long legal_address_id;

	@Basic
	@Column(name = "PHYSICAL_ADDRESS_ID")
	private Long physical_address_id;
	
	public OrganizationDepartMent() {
	}

	public Long getOrg_department_id() {
		return org_department_id;
	}

	public void setOrg_department_id(Long org_department_id) {
		this.org_department_id = org_department_id;
	}

	public Long getParrent_department_id() {
		return parrent_department_id;
	}

	public void setParrent_department_id(Long parrent_department_id) {
		this.parrent_department_id = parrent_department_id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getInner_order() {
		return inner_order;
	}

	public void setInner_order(Long inner_order) {
		this.inner_order = inner_order;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLegal_address_id() {
		return legal_address_id;
	}

	public void setLegal_address_id(Long legal_address_id) {
		this.legal_address_id = legal_address_id;
	}

	public Long getPhysical_address_id() {
		return physical_address_id;
	}

	public void setPhysical_address_id(Long physical_address_id) {
		this.physical_address_id = physical_address_id;
	}
}