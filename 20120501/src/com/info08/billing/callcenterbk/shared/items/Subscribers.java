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
import javax.persistence.Transient;

@Entity
@Table(name = "SUBSCRIBERS", schema = "ccare")
public class Subscribers implements Serializable {

	private static final long serialVersionUID = 7026086622425435823L;

	@Id
	@SequenceGenerator(name = "SEQ_SUBSCRIBER_ID_GENERATOR", sequenceName = "SEQ_SUBSCRIBER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBSCRIBER_ID_GENERATOR")
	@Column(name = "SUBSCRIBER_ID")
	private Integer subscriber_id;

	@Basic
	@Column(name = "ADDR_ID")
	private Integer addr_id;
	
	@Basic
	@Column(name = "NAME_ID")
	private Integer name_id;

	@Transient
	private String name;

	@Basic
	@Column(name = "FAMILY_NAME_ID")
	private Integer family_name_id;

	@Transient
	private String lastname;
	

	public Integer getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(Integer subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getName_id() {
		return name_id;
	}

	public void setName_id(Integer name_id) {
		this.name_id = name_id;
	}

	public Integer getFamily_name_id() {
		return family_name_id;
	}

	public void setFamily_name_id(Integer family_name_id) {
		this.family_name_id = family_name_id;
	}

	public Integer getAddr_id() {
		return addr_id;
	}

	public void setAddr_id(Integer addr_id) {
		this.addr_id = addr_id;
	}

}
