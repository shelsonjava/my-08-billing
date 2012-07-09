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

@Entity
@Table(name = "NON_STANDART_INFO_GROUPS")
public class NonStandartInfoGroups implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_NON_STANDART_INFO_GROUPS_GENERATOR", sequenceName = "SEQ_NON_STANDART_INFO_GROUPS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NON_STANDART_INFO_GROUPS_GENERATOR")
	@Column(name = "INFO_GROUP_ID")
	private Long info_group_id;

	@Basic
	@Column(name = "INFO_GROUP_NAME")
	private String info_group_name;

	@Transient
	private String loggedUserName;

	public NonStandartInfoGroups() {
	}

	public Long getInfo_group_id() {
		return info_group_id;
	}

	public void setInfo_group_id(Long info_group_id) {
		this.info_group_id = info_group_id;
	}

	public String getInfo_group_name() {
		return info_group_name;
	}

	public void setInfo_group_name(String info_group_name) {
		this.info_group_name = info_group_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
