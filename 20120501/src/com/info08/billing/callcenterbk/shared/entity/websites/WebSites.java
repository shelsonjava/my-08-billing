package com.info08.billing.callcenterbk.shared.entity.websites;

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
@Table(name = "WEB_SITES")
public class WebSites implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_WEB_SITES_GENERATOR", sequenceName = "SEQ_WEB_SITES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WEB_SITES_GENERATOR")
	@Column(name = "WEB_SITE_ID")
	private Long web_site_id;

	@Basic
	@Column(name = "ADDRESS")
	private String address;

	@Basic
	@Column(name = "REMARK")
	private String remark;

	@Basic
	@Column(name = "WEB_SITE_GROUP_ID")
	private Long web_site_group_id;

	@Transient
	private String loggedUserName;

	@Transient
	private String web_site_group_name;

	public WebSites() {
	}

	public Long getWeb_site_id() {
		return web_site_id;
	}

	public void setWeb_site_id(Long web_site_id) {
		this.web_site_id = web_site_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWeb_site_group_id() {
		return web_site_group_id;
	}

	public void setWeb_site_group_id(Long web_site_group_id) {
		this.web_site_group_id = web_site_group_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getWeb_site_group_name() {
		return web_site_group_name;
	}

	public void setWeb_site_group_name(String web_site_group_name) {
		this.web_site_group_name = web_site_group_name;
	}

}
