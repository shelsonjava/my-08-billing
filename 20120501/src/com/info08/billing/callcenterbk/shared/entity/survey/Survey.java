package com.info08.billing.callcenterbk.shared.entity.survey;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the Survey database table.
 * 
 */
@NamedQueries({ @NamedQuery(name = "Survey.getLocked", query = "select e from Survey e where e.survey_id = :survId and trunc(e.survey_created) >= trunc(sysdate-14) and e.survery_responce_status = 0 ") })
@Entity
@Table(name = "Survey", schema = "ccare")
public class Survey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SURVEY_ID_GENERATOR", sequenceName = "SEQ_SURVEY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SURVEY_ID_GENERATOR")
	@Column(name = "survey_id")
	private Long survey_id;

	@Basic
	@Column(name = "session_call_id")
	private String session_call_id;

	@Basic
	@Column(name = "survey_person")
	private String survey_person;

	@Basic
	@Column(name = "survey_phone")
	private String survey_phone;

	@Basic
	@Column(name = "survey_descript")
	private String survey_descript;

	@Basic
	@Column(name = "survey_kind_id")
	private Long survey_kind_id;

	@Basic
	@Column(name = "survery_responce_status")
	private Long survery_responce_status;

	@Basic
	@Column(name = "survey_done")
	private Long survey_done;

	@Basic
	@Column(name = "p_numb")
	private String p_numb;

	@Basic
	@Column(name = "LOKED_USER")
	private String loked_user;
	
	@Basic
	@Column(name = "SURVEY_CREATED")
	private Timestamp survey_created;

	@Basic
	@Column(name = "survey_reply_type_id")
	private Long survey_reply_type_id;

	@Basic
	@Column(name = "bblocked")
	private Long bblocked;

	@Transient
	private String survey_kind;

	@Transient
	private String survey_reply_type_name;

	@Transient
	private Long personnel_id;

	@Transient
	private Long personnel_id1;

	@Transient
	private Date start_date;

	public Survey() {
	}

	public Long getSurvey_id() {
		return survey_id;
	}

	public void setSurvey_id(Long survey_id) {
		this.survey_id = survey_id;
	}

	public String getSession_call_id() {
		return session_call_id;
	}

	public void setSession_call_id(String session_call_id) {
		this.session_call_id = session_call_id;
	}

	public String getSurvey_person() {
		return survey_person;
	}

	public void setSurvey_person(String survey_person) {
		this.survey_person = survey_person;
	}

	public String getSurvey_phone() {
		return survey_phone;
	}

	public void setSurvey_phone(String survey_phone) {
		this.survey_phone = survey_phone;
	}

	public String getSurvey_descript() {
		return survey_descript;
	}

	public void setSurvey_descript(String survey_descript) {
		this.survey_descript = survey_descript;
	}

	public Long getSurvey_kind_id() {
		return survey_kind_id;
	}

	public void setSurvey_kind_id(Long survey_kind_id) {
		this.survey_kind_id = survey_kind_id;
	}

	public Long getSurvery_responce_status() {
		return survery_responce_status;
	}

	public void setSurvery_responce_status(Long survery_responce_status) {
		this.survery_responce_status = survery_responce_status;
	}

	public Long getSurvey_done() {
		return survey_done;
	}

	public void setSurvey_done(Long survey_done) {
		this.survey_done = survey_done;
	}

	public String getP_numb() {
		return p_numb;
	}

	public void setP_numb(String p_numb) {
		this.p_numb = p_numb;
	}

	public Long getsurvey_reply_type_id() {
		return survey_reply_type_id;
	}

	public void setsurvey_reply_type_id(Long survey_reply_type_id) {
		this.survey_reply_type_id = survey_reply_type_id;
	}

	public String getSurvey_kind() {
		return survey_kind;
	}

	public void setSurvey_kind(String survey_kind) {
		this.survey_kind = survey_kind;
	}

	public String getsurvey_reply_type_name() {
		return survey_reply_type_name;
	}

	public void setsurvey_reply_type_name(String survey_reply_type_name) {
		this.survey_reply_type_name = survey_reply_type_name;
	}

	public Long getBblocked() {
		return bblocked;
	}

	public void setBblocked(Long bblocked) {
		this.bblocked = bblocked;
	}

	public Long getPersonnel_id() {
		return personnel_id;
	}

	public void setPersonnel_id(Long personnel_id) {
		this.personnel_id = personnel_id;
	}

	public Long getPersonnel_id1() {
		return personnel_id1;
	}

	public void setPersonnel_id1(Long personnel_id1) {
		this.personnel_id1 = personnel_id1;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public String getLoked_user() {
		return loked_user;
	}

	public void setLoked_user(String loked_user) {
		this.loked_user = loked_user;
	}

	public Timestamp getSurvey_created() {
		return survey_created;
	}

	public void setSurvey_created(Timestamp survey_created) {
		this.survey_created = survey_created;
	}
}