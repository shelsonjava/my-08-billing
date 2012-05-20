package com.info08.billing.callcenterbk.shared.entity.currency;

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
 * The persistent class for the RATE_CURR database table.
 * 
 */
@Entity
@Table(name = "CURRENCY", schema = "ccare")
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CURRENCY_ID_GENERATOR", sequenceName = "SEQ_CURRENCY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRENCY_ID_GENERATOR")
	@Column(name = "CURRENCY_ID")
	private Long currency_id;

	@Basic
	@Column(name = "COUNTRY_ID")
	private Long country_id;

	@Basic
	@Column(name = "CODE")
	private String code;

	@Basic
	@Column(name = "NAME_DESCR")
	private String name_descr;

	@Basic
	@Column(name = "SORT_ORDER")
	private Long sort_order;

	@Transient
	private String country_name;

	@Transient
	private String loggedUserName;

	public Currency() {
	}

	public Long getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Long currency_id) {
		this.currency_id = currency_id;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName_descr() {
		return name_descr;
	}

	public void setName_descr(String name_descr) {
		this.name_descr = name_descr;
	}

	public Long getSort_order() {
		return sort_order;
	}

	public void setSort_order(Long sort_order) {
		this.sort_order = sort_order;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}