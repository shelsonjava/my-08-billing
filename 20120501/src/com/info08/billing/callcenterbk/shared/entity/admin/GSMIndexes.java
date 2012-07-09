package com.info08.billing.callcenterbk.shared.entity.admin;

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

/**
 * The persistent class for the GSM_INDEXES database table.
 * 
 */
@NamedQueries({ @NamedQuery(name = "GSMIndexes.getAllGSMIndexes", query = "select e from GSMIndexes e") })
@Entity
@Table(name = "GSM_INDEXES")
public class GSMIndexes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GSM_INDEXES_GENERATOR", sequenceName = "SEQ_GSM_INDEXES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GSM_INDEXES_GENERATOR")
	private Long gsm_index_id;

	@Basic
	@Column(name = "GSM_COMPANY")
	private String gsm_company;

	@Basic
	@Column(name = "GSM_INDEX")
	private String gsm_index;

	@Transient
	private String loggedUserName;

	public GSMIndexes() {
	}

	public Long getGsm_index_id() {
		return gsm_index_id;
	}

	public void setGsm_index_id(Long gsm_index_id) {
		this.gsm_index_id = gsm_index_id;
	}

	public String getGsm_company() {
		return gsm_company;
	}

	public void setGsm_company(String gsm_company) {
		this.gsm_company = gsm_company;
	}

	public String getGsm_index() {
		return gsm_index;
	}

	public void setGsm_index(String gsm_index) {
		this.gsm_index = gsm_index;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}