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
import javax.persistence.Transient;

/**
 * The persistent class for the BUSINESS_DETAILS database table.
 * 
 */
@Entity
@Table(name = "ORGANIZATION_ACTIVITIES")
public class OrganizationActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ORG_ACTIVITY", sequenceName = "SEQ_ORG_ACTIVITY")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORG_ACTIVITY")
	@Column(name = "ORG_ACTIVITY_ID")
	private Long org_activity_id;

	@Basic
	@Column(name = "ACTIVITY_DESCRIPTION")
	private String activity_description;

	@Basic
	@Column(name = "REMARK")
	private String remark;
	
	@Basic
	@Column(name = "IS_BANK_ACTIVITY")
	private Long is_bank_activity;

	@Transient
	private String loggedUserName;
	
	@Transient
	private String is_bank_activity_descr;

	public OrganizationActivity() {
	}

	public Long getOrg_activity_id() {
		return org_activity_id;
	}

	public void setOrg_activity_id(Long org_activity_id) {
		this.org_activity_id = org_activity_id;
	}

	public String getActivity_description() {
		return activity_description;
	}

	public void setActivity_description(String activity_description) {
		this.activity_description = activity_description;
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

	public Long getIs_bank_activity() {
		return is_bank_activity;
	}

	public void setIs_bank_activity(Long is_bank_activity) {
		this.is_bank_activity = is_bank_activity;
	}

	public String getIs_bank_activity_descr() {
		return is_bank_activity_descr;
	}

	public void setIs_bank_activity_descr(String is_bank_activity_descr) {
		this.is_bank_activity_descr = is_bank_activity_descr;
	}
}