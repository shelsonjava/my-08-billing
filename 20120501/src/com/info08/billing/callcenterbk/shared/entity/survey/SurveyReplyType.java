package com.info08.billing.callcenterbk.shared.entity.survey;

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
 * The persistent class for the survey_reply_type database table.
 * 
 */
@Entity
@Table(name = "survey_reply_type", schema = "ccare")
public class SurveyReplyType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SURVEY_REPLY_TYPE_GENERATOR", sequenceName = "SEQ_SURVEY_REPLY_TYPE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SURVEY_REPLY_TYPE_GENERATOR")
	@Column(name = "survey_reply_type_id")
	private Long survey_reply_type_id;

	@Basic
	@Column(name = "survey_reply_type_name")
	private String survey_reply_type_name;

	@Transient
	private String loggedUserName;

	public SurveyReplyType() {
	}

	public Long getSurvey_reply_type_id() {
		return survey_reply_type_id;
	}

	public void setSurvey_reply_type_id(Long survey_reply_type_id) {
		this.survey_reply_type_id = survey_reply_type_id;
	}

	public String getSurvey_reply_type_name() {
		return survey_reply_type_name;
	}

	public void setSurvey_reply_type_name(String survey_reply_type_name) {
		this.survey_reply_type_name = survey_reply_type_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}