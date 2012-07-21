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
@Table(name = "CORP_CLIENT_PHONES")
public class CorpClientPhones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CONTRACTOR_PHONES_GENERATOR", sequenceName = "SEQ_CONTRACTOR_PHONES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTRACTOR_PHONES_GENERATOR")
	@Column(name = "CORP_CLIENT_PHONE_ID")
	private Long corp_client_phone_id;

	@Basic
	@Column(name = "PHONE_NUMBER")
	private String phone_number;

	@Basic
	@Column(name = "CORPORATE_CLIENT_ID")
	private Long corporate_client_id;

	public CorpClientPhones() {
	}

	public Long getCorp_client_phone_id() {
		return corp_client_phone_id;
	}

	public void setCorp_client_phone_id(Long corp_client_phone_id) {
		this.corp_client_phone_id = corp_client_phone_id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Long getCorporate_client_id() {
		return corporate_client_id;
	}

	public void setCorporate_client_id(Long corporate_client_id) {
		this.corporate_client_id = corporate_client_id;
	}

}