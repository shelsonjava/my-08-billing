package com.info08.billing.callcenterbk.shared.entity.nonstandartinfo;

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
 * The persistent class for the ENT_PLACES database table.
 * 
 */
@Entity
@Table(name = "NON_STANDART_INFO", schema = "ccare")
public class NonStandartInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_NON_STANDART_INFO_GENERATOR", sequenceName = "SEQ_NON_STANDART_INFO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NON_STANDART_INFO_GENERATOR")
	@Column(name = "INFO_ID")
	private Long info_id;

	@Basic
	@Column(name = "INFO_NAME")
	private String info_name;

	@Basic
	@Column(name = "INFO_GROUP_ID")
	private Long info_group_id;

	@Transient
	private String loggedUserName;

	@Transient
	private String info_group_name;

	public NonStandartInfo() {
	}

	public Long getInfo_id() {
		return info_id;
	}

	public void setInfo_id(Long info_id) {
		this.info_id = info_id;
	}

	public String getInfo_name() {
		return info_name;
	}

	public void setInfo_name(String info_name) {
		this.info_name = info_name;
	}

	public Long getInfo_group_id() {
		return info_group_id;
	}

	public void setInfo_group_id(Long info_group_id) {
		this.info_group_id = info_group_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getInfo_group_name() {
		return info_group_name;
	}

	public void setInfo_group_name(String info_group_name) {
		this.info_group_name = info_group_name;
	}

}
