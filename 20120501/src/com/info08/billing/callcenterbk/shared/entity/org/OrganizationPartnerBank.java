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
 * The persistent class for the BUSINESS_DETAILS database table.
 * 
 */
@Entity
@Table(name = "ORGANIZATION_PARTNER_BANKS")
public class OrganizationPartnerBank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORG_PART_BANK_ID", sequenceName = "SEQ_ORG_PART_BANK_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORG_PART_BANK_ID")
	@Column(name = "ORG_PART_BANK_ID")
	private Long org_part_bank_id;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "PART_BANK_ORG_ID")
	private Long part_bank_org_id;

	public Long getOrg_part_bank_id() {
		return org_part_bank_id;
	}

	public void setOrg_part_bank_id(Long org_part_bank_id) {
		this.org_part_bank_id = org_part_bank_id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public Long getPart_bank_org_id() {
		return part_bank_org_id;
	}

	public void setPart_bank_org_id(Long part_bank_org_id) {
		this.part_bank_org_id = part_bank_org_id;
	}
}