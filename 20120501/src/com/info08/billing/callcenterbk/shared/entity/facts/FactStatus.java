package com.info08.billing.callcenterbk.shared.entity.facts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CALENDAR_STATE database table.
 * 
 */
@Entity
@Table(name = "v_FACTS_STATUSES")
public class FactStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FACT_STATUS_ID")
	private Long fact_status_id;

	@Basic
	@Column(name = "FACT_STATUS_NAME")
	private String fact_status_name;

	public FactStatus() {
	}

	public Long getFact_status_id() {
		return fact_status_id;
	}

	public void setFact_status_id(Long fact_status_id) {
		this.fact_status_id = fact_status_id;
	}

	public String getFact_status_name() {
		return fact_status_name;
	}

	public void setFact_status_name(String fact_status_name) {
		this.fact_status_name = fact_status_name;
	}
}