package com.info08.billing.callcenterbk.shared.entity.contractors;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CORP_CLIENT_PRICE_ITEMS database table.
 * 
 */
@Entity
@Table(name = "CORP_CLIENT_PRICE_ITEMS")
public class CorpClientPriceItems implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CORP_CLIENT_PRICE_ITEMS_GENERATOR", sequenceName = "SEQ_CORP_CLIENT_PRICE_ITEMS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CORP_CLIENT_PRICE_ITEMS_GENERATOR")
	private Long id;

	@Basic
	@Column(name = "CALL_COUNT_END")
	private Long call_count_end;

	@Basic
	@Column(name = "CALL_COUNT_START")
	private Long call_count_start;

	@Basic
	@Column(name = "CORPORATE_CLIENT_ID")
	private Long corporate_client_id;

	@Basic
	@Column(name = "PRICE")
	private BigDecimal price;

	public CorpClientPriceItems() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCall_count_end() {
		return call_count_end;
	}

	public void setCall_count_end(Long call_count_end) {
		this.call_count_end = call_count_end;
	}

	public Long getCall_count_start() {
		return call_count_start;
	}

	public void setCall_count_start(Long call_count_start) {
		this.call_count_start = call_count_start;
	}

	public Long getCorporate_client_id() {
		return corporate_client_id;
	}

	public void setCorporate_client_id(Long corporate_client_id) {
		this.corporate_client_id = corporate_client_id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}