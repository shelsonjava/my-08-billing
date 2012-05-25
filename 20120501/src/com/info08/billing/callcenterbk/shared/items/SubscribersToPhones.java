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
@Table(name = "SUBSCRIBER_TO_PHONES", schema = "ccare")
public class SubscribersToPhones implements Serializable {

	private static final long serialVersionUID = 7026086622425435823L;

	@Id
	@SequenceGenerator(name = "SEQ_SUBSCRIBER_TO_PHONES_ID_GENERATOR", sequenceName = "SEQ_SUBSCRIBER_TO_PHONES_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBSCRIBER_TO_PHONES_ID_GENERATOR")
	@Column(name = "SUBSCRIBER_TO_PHONES_ID")
	private Integer subscriber_to_phones_id;

	@Basic
	@Column(name = "SUBSCRIBER_ID")
	private Integer subscriber_id;
	@Basic
	@Column(name = "PHONE_NUMBER_ID")
	private Integer phone_number_id;
	@Basic
	@Column(name = "HIDDEN_BY_REQUEST")
	private Integer hidden_by_request;
	@Basic
	@Column(name = "PHONE_CONTRACT_TYPE")
	private Integer phone_contract_type;

	public SubscribersToPhones() {
	}
	
	public Integer getSubscriber_to_phones_id() {
		return subscriber_to_phones_id;
	}

	public void setSubscriber_to_phones_id(Integer subscriber_to_phones_id) {
		this.subscriber_to_phones_id = subscriber_to_phones_id;
	}

	public Integer getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(Integer subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	public Integer getPhone_number_id() {
		return phone_number_id;
	}

	public void setPhone_number_id(Integer phone_number_id) {
		this.phone_number_id = phone_number_id;
	}

	public Integer getHidden_by_request() {
		return hidden_by_request;
	}

	public void setHidden_by_request(Integer hidden_by_request) {
		this.hidden_by_request = hidden_by_request;
	}

	public Integer getPhone_contract_type() {
		return phone_contract_type;
	}

	public void setPhone_contract_type(Integer phone_contract_type) {
		this.phone_contract_type = phone_contract_type;
	}

}
