package com.info08.billing.callcenterbk.shared.entity;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries({
		@NamedQuery(
					name = "SentSMSHist.getBySessionId", 
					query = "select e from SentSMSHist e where e.session_call_id = :sessId"),
		@NamedQuery(
					name = "SentSMSHist.getForSending", 
					query = "select e from SentSMSHist e where trunc(e.message_sent_time) >= trunc(sysdate-1) and e.hist_status_id = 0 and rownum < 20 order by e.sent_sms_hist_id"),
		@NamedQuery(
					name = "SentSMSHist.getForSendingTest", 
					query = "select e from SentSMSHist e where trunc(e.message_sent_time) >= trunc(sysdate-1) and e.hist_status_id = -1000 and rownum < 20 order by e.sent_sms_hist_id"),					
		@NamedQuery(
					name = "SentSMSHist.getDeliveryReport", 
					query = "select e from SentSMSHist e where trunc(e.message_sent_time) >= trunc(sysdate-1) and e.hist_status_id = 1 and e.message_done <> 1 order by e.sent_sms_hist_id"),					
		@NamedQuery(
					name = "SentSMSHist.getBySessionIdCount", 
					query = "select count(e) from SentSMSHist e where e.session_call_id = :sessId")
})
@Entity
@Table(name = "SENT_SMS_HIST")
public class SentSMSHist implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "SENT_SMS_HIST_ID")
	private Long sent_sms_hist_id;

	@Basic
	@Column(name = "HIST_STATUS_ID")
	private Long hist_status_id;

	@Basic
	@Column(name = "MESSAGE_TYPE")
	private Long message_type;

	@Basic
	@Column(name = "RECIEVER_NUMBER", length = 30)
	private String reciever_number;

	@Basic
	@Column(name = "MESSAGE_CONTEXT", length = 4000)
	private String message_context;

	@Basic
	@Column(name = "MESSAGE_SENT_TIME")
	private Timestamp message_sent_time;

	@Basic
	@Column(name = "SESSION_CALL_ID", length = 200)
	private String session_call_id;

	@Basic
	@Column(name = "CREATOR_USER", length = 30)
	private String creator_user;

	@Basic
	@Column(name = "SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name = "UNSUCCESS")
	private Long unsuccess;
	
	@Basic
	@Column(name = "MESSAGE_DONE")
	private Long message_done;
	
	@Basic
	@Column(name = "UNSUCCESS_COUNT")
	private Long unsuccess_count;
	
	@Basic
	@Column(name = "GSM_OPERATOR_MSG_ID", length = 20)
	private String gsm_operator_msg_id;
	
	
	@Transient
	private String operator;
	
	@Transient
	private String message_type_descr;
	
	@Transient
	private String hist_status_descr;
	
	public SentSMSHist() {
	}


	public Long getSent_sms_hist_id() {
		return sent_sms_hist_id;
	}


	public void setSent_sms_hist_id(Long sent_sms_hist_id) {
		this.sent_sms_hist_id = sent_sms_hist_id;
	}


	public Long getHist_status_id() {
		return hist_status_id;
	}


	public void setHist_status_id(Long hist_status_id) {
		this.hist_status_id = hist_status_id;
	}


	public Long getMessage_type() {
		return message_type;
	}


	public void setMessage_type(Long message_type) {
		this.message_type = message_type;
	}


	public String getReciever_number() {
		return reciever_number;
	}


	public void setReciever_number(String reciever_number) {
		this.reciever_number = reciever_number;
	}


	public String getMessage_context() {
		return message_context;
	}


	public void setMessage_context(String message_context) {
		this.message_context = message_context;
	}


	public Timestamp getMessage_sent_time() {
		return message_sent_time;
	}


	public void setMessage_sent_time(Timestamp message_sent_time) {
		this.message_sent_time = message_sent_time;
	}


	public String getSession_call_id() {
		return session_call_id;
	}


	public void setSession_call_id(String session_call_id) {
		this.session_call_id = session_call_id;
	}


	public String getCreator_user() {
		return creator_user;
	}


	public void setCreator_user(String creator_user) {
		this.creator_user = creator_user;
	}


	public Long getService_id() {
		return service_id;
	}


	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}


	public Long getUnsuccess() {
		return unsuccess;
	}


	public void setUnsuccess(Long unsuccess) {
		this.unsuccess = unsuccess;
	}


	public Long getMessage_done() {
		return message_done;
	}


	public void setMessage_done(Long message_done) {
		this.message_done = message_done;
	}


	public Long getUnsuccess_count() {
		return unsuccess_count;
	}


	public void setUnsuccess_count(Long unsuccess_count) {
		this.unsuccess_count = unsuccess_count;
	}


	public String getGsm_operator_msg_id() {
		return gsm_operator_msg_id;
	}


	public void setGsm_operator_msg_id(String gsm_operator_msg_id) {
		this.gsm_operator_msg_id = gsm_operator_msg_id;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getMessage_type_descr() {
		return message_type_descr;
	}


	public void setMessage_type_descr(String message_type_descr) {
		this.message_type_descr = message_type_descr;
	}


	public String getHist_status_descr() {
		return hist_status_descr;
	}


	public void setHist_status_descr(String hist_status_descr) {
		this.hist_status_descr = hist_status_descr;
	}
}
