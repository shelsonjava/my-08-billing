package com.info08.billing.callcenterbk.shared.common;

import java.io.Serializable;
import java.sql.Timestamp;

public class ServerSessionSurvItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long survey_id;
	private String survey_kind_name;
	private String survey_reply_type_name;
	private String session_call_id;
	private String p_numb;
	private String survey_descript;
	private String survey_phone;
	private Integer survey_kind_id;
	private Integer survey_reply_type_id;
	private String survey_person;
	private Integer survery_responce_status;
	private Integer survey_done;
	private Integer bblocked;
	private String survey_creator;
	private Timestamp survey_created;
	private String loked_user;
	private Timestamp start_date;
	private Integer personnel_id;
	private String operator_src;
	private String survey_stat_descr;
	
	public String getSurvey_kind_name() {
		return survey_kind_name;
	}

	public void setSurvey_kind_name(String survey_kind_name) {
		this.survey_kind_name = survey_kind_name;
	}

	public String getSurvey_reply_type_name() {
		return survey_reply_type_name;
	}

	public void setSurvey_reply_type_name(String survey_reply_type_name) {
		this.survey_reply_type_name = survey_reply_type_name;
	}

	public String getSession_call_id() {
		return session_call_id;
	}

	public void setSession_call_id(String session_call_id) {
		this.session_call_id = session_call_id;
	}

	public String getP_numb() {
		return p_numb;
	}

	public void setP_numb(String p_numb) {
		this.p_numb = p_numb;
	}

	public String getSurvey_descript() {
		return survey_descript;
	}

	public void setSurvey_descript(String survey_descript) {
		this.survey_descript = survey_descript;
	}

	public String getSurvey_phone() {
		return survey_phone;
	}

	public void setSurvey_phone(String survey_phone) {
		this.survey_phone = survey_phone;
	}

	public Integer getSurvey_kind_id() {
		return survey_kind_id;
	}

	public void setSurvey_kind_id(Integer survey_kind_id) {
		this.survey_kind_id = survey_kind_id;
	}

	public Integer getSurvey_reply_type_id() {
		return survey_reply_type_id;
	}

	public void setSurvey_reply_type_id(Integer survey_reply_type_id) {
		this.survey_reply_type_id = survey_reply_type_id;
	}

	public String getSurvey_person() {
		return survey_person;
	}

	public void setSurvey_person(String survey_person) {
		this.survey_person = survey_person;
	}

	public Integer getSurvery_responce_status() {
		return survery_responce_status;
	}

	public void setSurvery_responce_status(Integer survery_responce_status) {
		this.survery_responce_status = survery_responce_status;
	}

	public Integer getSurvey_done() {
		return survey_done;
	}

	public void setSurvey_done(Integer survey_done) {
		this.survey_done = survey_done;
	}

	public Integer getBblocked() {
		return bblocked;
	}

	public void setBblocked(Integer bblocked) {
		this.bblocked = bblocked;
	}

	public String getSurvey_creator() {
		return survey_creator;
	}

	public void setSurvey_creator(String survey_creator) {
		this.survey_creator = survey_creator;
	}

	public Timestamp getSurvey_created() {
		return survey_created;
	}

	public void setSurvey_created(Timestamp survey_created) {
		this.survey_created = survey_created;
	}

	public String getLoked_user() {
		return loked_user;
	}

	public void setLoked_user(String loked_user) {
		this.loked_user = loked_user;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public Integer getPersonnel_id() {
		return personnel_id;
	}

	public void setPersonnel_id(Integer personnel_id) {
		this.personnel_id = personnel_id;
	}

	public String getOperator_src() {
		return operator_src;
	}

	public void setOperator_src(String operator_src) {
		this.operator_src = operator_src;
	}

	public Long getSurvey_id() {
		return survey_id;
	}

	public void setSurvey_id(Long survey_id) {
		this.survey_id = survey_id;
	}
	public String getSurvey_stat_descr() {
		return survey_stat_descr;
	}
	public void setSurvey_stat_descr(String survey_stat_descr) {
		this.survey_stat_descr = survey_stat_descr;
	}
}
