package com.info08.billing.callcenterbk.shared.entity.facts;

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
 * The persistent class for the SECULAR_CALENDAR_EVENTS database table.
 * 
 */
@Entity
@Table(name = "facts_type", schema = "ccare")
public class FactType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_fact_type_GENERATOR", sequenceName = "seq_fact_type")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fact_type_GENERATOR")
	@Column(name = "fact_type_id")
	private Long fact_type_id;

	@Basic
	@Column(name = "FACT_TYPE_NAME")
	private String fact_type_name;

	public FactType() {
	}

	public Long getEvent_id() {
		return fact_type_id;
	}

	public void setEvent_id(Long event_id) {
		this.fact_type_id = event_id;
	}

	public String getFact_type_name() {
		return fact_type_name;
	}

	public void setFact_type_name(String fact_type_name) {
		this.fact_type_name = fact_type_name;
	}

}