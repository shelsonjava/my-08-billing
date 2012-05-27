package com.info08.billing.callcenterbk.shared.entity.black;

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
 * The persistent class for the BLOCK_LIST database table.
 * 
 */
@Entity
@Table(name = "BLACK_LIST", schema = "ccare")
public class BlackList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_BLACK_LIST_GENERATOR", sequenceName = "SEQ_BLACK_LIST")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BLACK_LIST_GENERATOR")
	@Column(name = "BLACK_LIST_ID")
	private Long black_list_id;

	@Basic
	@Column(name = "TITLE_DESCR")
	private String title_descr;
	
	@Basic
	@Column(name = "STATUS")
	private Long status;

	@Transient
	private String blackListPhones;

	public BlackList() {
	}

	public Long getBlack_list_id() {
		return black_list_id;
	}

	public void setBlack_list_id(Long black_list_id) {
		this.black_list_id = black_list_id;
	}

	public String getTitle_descr() {
		return title_descr;
	}

	public void setTitle_descr(String note) {
		this.title_descr = note;
	}

	public String getBlackListPhones() {
		return blackListPhones;
	}

	public void setBlackListPhones(String blackListPhones) {
		this.blackListPhones = blackListPhones;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
}