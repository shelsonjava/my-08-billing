package com.info08.billing.callcenterbk.shared.entity.currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

@NamedQueries({
	@NamedQuery(
				name="Rate.getRateByCurr",
				query="select e from Rate e where e.curr_id = :curr_id")
})

@Entity
@Table(name = "RATE", schema = "PAATA")
public class Rate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RATE_RATEID_GENERATOR", sequenceName="RATE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RATE_RATEID_GENERATOR")
	@Column(name="RATE_ID")
	private Long rate_id;

	@Basic
	@Column(name="CURR_ID")
	private Long curr_id;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="MARKET_RATE")
	private BigDecimal market_rate;

	@Basic
	@Column(name="RATE")
	private BigDecimal rate;

	@Basic
	@Column(name="RATE_COEFF")
	private Long rate_coeff;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="SALE_MARKET_RATE")
	private BigDecimal sale_market_rate;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

	@Transient
	private String curr_name_geo;
	
	@Transient
    private String loggedUserName;

    public Rate() {
    }

	public Long getRate_id() {
		return rate_id;
	}

	public void setRate_id(Long rate_id) {
		this.rate_id = rate_id;
	}

	public Long getCurr_id() {
		return curr_id;
	}

	public void setCurr_id(Long curr_id) {
		this.curr_id = curr_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public BigDecimal getMarket_rate() {
		return market_rate;
	}

	public void setMarket_rate(BigDecimal market_rate) {
		this.market_rate = market_rate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getRate_coeff() {
		return rate_coeff;
	}

	public void setRate_coeff(Long rate_coeff) {
		this.rate_coeff = rate_coeff;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public BigDecimal getSale_market_rate() {
		return sale_market_rate;
	}

	public void setSale_market_rate(BigDecimal sale_market_rate) {
		this.sale_market_rate = sale_market_rate;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public String getCurr_name_geo() {
		return curr_name_geo;
	}

	public void setCurr_name_geo(String curr_name_geo) {
		this.curr_name_geo = curr_name_geo;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
	
}