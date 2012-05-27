package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESSES")
public class Address implements Serializable {

	private static final long serialVersionUID = 8914876851562021816L;

	@Id
	@SequenceGenerator(name = "SEQ_ADDRESS_ID_GENERATOR", sequenceName = "SEQ_ADDRESS_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS_ID_GENERATOR")
	@Column(name = "ADDR_ID")
	private Long addr_id;
	@Basic
	@Column(name = "TOWN_ID")
	private Long town_id;
	@Basic
	@Column(name = "STREET_ID")
	private Long street_id;
	@Basic
	@Column(name = "FULL_ADDRESS")
	private String full_address;
	@Basic
	@Column(name = "HIDDEN_BY_REQUEST")
	private Long hidden_by_request;
	@Basic
	@Column(name = "TOWN_DISTRICT_ID")
	private Long town_district_id;
	@Basic
	@Column(name = "BLOCK")
	private String block;
	@Basic
	@Column(name = "APPT")
	private String appt;
	@Basic
	@Column(name = "DESCR")
	private String descr;
	@Basic
	@Column(name = "ANUMBER")
	private String anumber;

	public Address() {

	}

	public Long getAddr_id() {
		return addr_id;
	}

	public void setAddr_id(Long addr_id) {
		this.addr_id = addr_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public String getFull_address() {
		return full_address;
	}

	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}

	public Long getHidden_by_request() {
		return hidden_by_request;
	}

	public void setHidden_by_request(Long hidden_by_request) {
		this.hidden_by_request = hidden_by_request;
	}

	public Long getTown_district_id() {
		return town_district_id;
	}

	public void setTown_district_id(Long town_district_id) {
		this.town_district_id = town_district_id;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getAppt() {
		return appt;
	}

	public void setAppt(String appt) {
		this.appt = appt;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAnumber() {
		return anumber;
	}

	public void setAnumber(String anumber) {
		this.anumber = anumber;
	}
}
