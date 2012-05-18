package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the CALL_CENTER_NEWS database table.
 * 
 */
@Entity
@Table(name = "CALL_CENTER_NEWS", schema = "ccare")
public class CallCenterNews implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CALL_CENTER_NEWS_GENERATOR", sequenceName = "SEQ_CALL_CENTER_NEWS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CALL_CENTER_NEWS_GENERATOR")
	@Column(name = "CALL_CENTER_NEWS_ID")
	private Long call_center_news_id;

	@Basic
	@Column(name = "CALL_CENTER_NEWS_TEXT")
	private String call_center_news_text;

	@Basic	
	@Column(name = "CALL_CENTER_NEWS_DATE")
	private Timestamp call_center_news_date;

	@Basic
	@Column(name = "CALL_CENTER_WARNING")
	private Long call_center_warning;

	@Transient
	private String loggedUserName;

	public CallCenterNews() {
	}

	public Long getCall_center_news_id() {
		return call_center_news_id;
	}

	public void setCall_center_news_id(Long call_center_news_id) {
		this.call_center_news_id = call_center_news_id;
	}

	public String getCall_center_news_text() {
		return call_center_news_text;
	}

	public void setCall_center_news_text(String call_center_news_text) {
		this.call_center_news_text = call_center_news_text;
	}

	public Timestamp getCall_center_news_date() {
		return call_center_news_date;
	}

	public void setCall_center_news_date(Timestamp call_center_news_date) {
		this.call_center_news_date = call_center_news_date;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Long getCall_center_warning() {
		return call_center_warning;
	}

	public void setCall_center_warning(Long call_center_warning) {
		this.call_center_warning = call_center_warning;
	}

}