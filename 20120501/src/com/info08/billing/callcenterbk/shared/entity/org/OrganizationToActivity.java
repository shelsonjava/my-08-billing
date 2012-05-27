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
@Table(name = "ORGANIZATION_TO_ACTIVITIES")
public class OrganizationToActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORG_TO_ACT_ID", sequenceName = "SEQ_ORG_TO_ACT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORG_TO_ACT_ID")
	@Column(name = "ORG_TO_ACT_ID")
	private Long org_to_act_id;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "ORG_ACTIVITY_ID")
	private Long org_activity_id;

	public Long getOrg_to_act_id() {
		return org_to_act_id;
	}

	public void setOrg_to_act_id(Long org_to_act_id) {
		this.org_to_act_id = org_to_act_id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public Long getOrg_activity_id() {
		return org_activity_id;
	}

	public void setOrg_activity_id(Long org_activity_id) {
		this.org_activity_id = org_activity_id;
	}
}