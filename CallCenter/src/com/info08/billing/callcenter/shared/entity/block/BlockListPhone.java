package com.info08.billing.callcenter.shared.entity.block;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BLOCK_LIST_PHONES database table.
 * 
 */
@Entity
@Table(name="BLOCK_LIST_PHONES", schema="INFO")
public class BlockListPhone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BLOCK_LIST_PHONES_ID_GENERATOR", sequenceName="SEQ_BLOCK_LIST_PHONES")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BLOCK_LIST_PHONES_ID_GENERATOR")
	private Long id;

	@Basic
	@Column(name="BLOCK_LIST_ID")
	private Long block_list_id;

	@Basic
	@Column(name="PHONE")
	private String phone;

    public BlockListPhone() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBlock_list_id() {
		return block_list_id;
	}

	public void setBlock_list_id(Long block_list_id) {
		this.block_list_id = block_list_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}