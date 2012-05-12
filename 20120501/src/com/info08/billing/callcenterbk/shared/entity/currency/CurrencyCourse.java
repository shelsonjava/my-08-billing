package com.info08.billing.callcenterbk.shared.entity.currency;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the RATE database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = "CurrencyCourse.getCurrencyCourseByCurrency", query = "select e from CurrencyCourse e where e.currency_id = :currency_id") })
@Entity
@Table(name = "CURRENCY_COURSE", schema = "ccare")
public class CurrencyCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CURRENCY_COURSE_ID_GENERATOR", sequenceName = "SEQ_CURRENCY_COURSE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRENCY_COURSE_ID_GENERATOR")
	@Column(name = "CURRENCY_COURSE_ID")
	private Long currency_course_id;

	@Basic
	@Column(name = "CURRENCY_ID")
	private Long currency_id;

	@Basic
	@Column(name = "BANK_BUY_COURSE")
	private BigDecimal bank_buy_course;

	@Basic
	@Column(name = "NATIONAL_COURSE")
	private BigDecimal national_course;

	@Basic
	@Column(name = "COEFFICIENT")
	private Long coefficient;

	@Basic
	@Column(name = "BANK_SELL_COURSE")
	private BigDecimal bank_sell_course;

	@Transient
	private String name_descr;

	@Transient
	private String loggedUserName;

	public CurrencyCourse() {
	}

	public Long getCurrency_course_id() {
		return currency_course_id;
	}

	public void setCurrency_course_id(Long currency_course_id) {
		this.currency_course_id = currency_course_id;
	}

	public Long getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Long currency_id) {
		this.currency_id = currency_id;
	}

	public BigDecimal getBank_buy_course() {
		return bank_buy_course;
	}

	public void setBank_buy_course(BigDecimal bank_buy_course) {
		this.bank_buy_course = bank_buy_course;
	}

	public BigDecimal getNational_course() {
		return national_course;
	}

	public void setNational_course(BigDecimal national_course) {
		this.national_course = national_course;
	}

	public Long getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Long coefficient) {
		this.coefficient = coefficient;
	}

	public BigDecimal getBank_sell_course() {
		return bank_sell_course;
	}

	public void setBank_sell_course(BigDecimal bank_sell_course) {
		this.bank_sell_course = bank_sell_course;
	}

	public String getName_descr() {
		return name_descr;
	}

	public void setName_descr(String name_descr) {
		this.name_descr = name_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}