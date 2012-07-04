package com.info08.billing.callcenterbk.shared.entity.contractors;

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
 * The persistent class for the CONTRACTOR_PHONES database table.
 * 
 */
@Entity
@Table(name = "CONTRACTOR_PHONES", schema = "ccare")
public class ContractorPhone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CONTRACTOR_PHONES_GENERATOR", sequenceName = "SEQ_CONTRACTOR_PHONES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTRACTOR_PHONES_GENERATOR")
	@Column(name = "CP_ID")
	private Long cp_id;

	@Basic
	@Column(name = "PHONE")
	private String phone;

	@Basic
	@Column(name = "CONTRACT_ID")
	private Long contract_id;

	public ContractorPhone() {
	}

	public Long getCp_id() {
		return cp_id;
	}

	public void setCp_id(Long cp_id) {
		this.cp_id = cp_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getContract_id() {
		return contract_id;
	}

	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}
}