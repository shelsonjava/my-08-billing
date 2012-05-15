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
 * The persistent class for the survey_kind database table.
 * 
 */
@Entity
@Table(name = "survey_kind", schema = "ccare")
public class SurveyKind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SURVEY_KIND_GENERATOR", sequenceName = "SEQ_SURVEY_KIND")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SURVEY_KIND_GENERATOR")
	@Column(name = "SURVEY_KIND_ID")
	private Long survey_kind_id;

	@Basic
	@Column(name = "SURVEY_KIND_NAME")
	private String survey_kind_name;

	@Transient
	private String loggedUserName;

	public SurveyKind() {
	}

	public Long getSurvey_kind_id() {
		return survey_kind_id;
	}

	public void setSurvey_kind_id(Long survey_kind_id) {
		this.survey_kind_id = survey_kind_id;
	}

	public String getSurvey_kind_name() {
		return survey_kind_name;
	}

	public void setSurvey_kind_name(String survey_kind_name) {
		this.survey_kind_name = survey_kind_name;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}