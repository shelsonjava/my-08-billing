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

@Entity
@Table(name = "WEB_SITE_GROUPS")
public class WebSiteGroups implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_WEB_SITE_GROUPS_GENERATOR", sequenceName = "SEQ_WEB_SITE_GROUPS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WEB_SITE_GROUPS_GENERATOR")
	@Column(name = "WEB_SITE_GROUP_ID")
	private Long web_site_group_id;

	@Basic
	@Column(name = "WEB_SITE_GROUP_NAME")
	private String web_site_group_name;

	@Transient
	private String loggedUserName;

	public WebSiteGroups() {
	}

	public Long getWeb_site_group_id() {
		return web_site_group_id;
	}

	public void setWeb_site_group_id(Long web_site_group_id) {
		this.web_site_group_id = web_site_group_id;
	}

	public String getWeb_site_group_name() {
		return web_site_group_name;
	}

	public void setWeb_site_group_name(String web_site_group_name) {
		this.web_site_group_name = web_site_group_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
