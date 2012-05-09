package com.info08.billing.callcenterbk.shared.entity.contractors;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CONTRACT_PRICE_ITEMS database table.
 * 
 */
@Entity
@Table(name="CONTRACT_PRICE_ITEMS",schema="ccare")
public class ContractPriceItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTRACT_PRICE_ITEMS_ID_GENERATOR", sequenceName="SEQ_CONTR_PRICE_ITEMS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_PRICE_ITEMS_ID_GENERATOR")
	private Long id;

	@Basic	
	@Column(name="CALL_COUNT_END")
	private Long call_count_end;

	@Basic
	@Column(name="CALL_COUNT_START")
	private Long call_count_start;

	@Basic
	@Column(name="CONTRACT_ID")
	private Long contract_id;

	@Basic
	@Column(name="PRICE")
	private BigDecimal price;

    public ContractPriceItem() {
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

	public Long getContract_id() {
		return contract_id;
	}

	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}