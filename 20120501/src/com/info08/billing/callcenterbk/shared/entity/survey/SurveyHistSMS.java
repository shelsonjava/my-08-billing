package com.info08.billing.callcenterbk.shared.entity.survey;

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


/**
 * The persistent class for the SURVEY_HIST_SMS database table.
 * 
 */
@Entity
@Table(name="SURVEY_HIST_SMS")
public class SurveyHistSMS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_SURVEY_HIST_SMS_GENERATOR", sequenceName="SEQ_SURVEY_HIST_SMS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_SURVEY_HIST_SMS_GENERATOR")
	@Column(name = "HIST_ID")
	private Long hist_id;

	@Basic
	@Column(name="survey_id")
	private Long survey_id;

    @Basic
	@Column(name="HIST_DATETIME")
	private Timestamp hist_datetime;

    public SurveyHistSMS() {
    }

	public Long getHist_id() {
		return hist_id;
	}

	public void setHist_id(Long hist_id) {
		this.hist_id = hist_id;
	}

	public Long getSurvey_id() {
		return survey_id;
	}

	public void setSurvey_id(Long survey_id) {
		this.survey_id = survey_id;
	}

	public Timestamp getHist_datetime() {
		return hist_datetime;
	}

	public void setHist_datetime(Timestamp rec_date) {
		this.hist_datetime = rec_date;
	}
}