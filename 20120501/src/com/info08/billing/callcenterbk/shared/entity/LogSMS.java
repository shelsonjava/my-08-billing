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
					name = "LogSMS.getBySessionId", 
					query = "select e from LogSMS e where e.session_id = :sessId"),
		@NamedQuery(
					name = "LogSMS.getForSending", 
					query = "select e from LogSMS e where trunc(e.sms_date) >= trunc(sysdate-1) and e.status = 0 and rownum < 20 order by e.sms_id"),
		@NamedQuery(
					name = "LogSMS.getForSendingTest", 
					query = "select e from LogSMS e where trunc(e.sms_date) >= trunc(sysdate-1) and e.status = -1000 and rownum < 20 order by e.sms_id"),					
		@NamedQuery(
					name = "LogSMS.getDeliveryReport", 
					query = "select e from LogSMS e where trunc(e.sms_date) >= trunc(sysdate-1) and e.status = 1 and e.delivered <> 1 order by e.sms_id"),					
		@NamedQuery(
					name = "LogSMS.getBySessionIdCount", 
					query = "select count(e) from LogSMS e where e.session_id = :sessId")
})
@Entity
@Table(name = "log_sms", schema = "PAATA")
public class LogSMS implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "SMS_ID")
	private Long sms_id;

	@Basic
	@Column(name = "STATUS")
	private Long status;

	@Basic
	@Column(name = "SMS_TYPE")
	private Long sms_type;

	@Basic
	@Column(name = "PHONE", length = 30)
	private String phone;

	@Basic
	@Column(name = "SMS_TEXT", length = 4000)
	private String sms_text;

	@Basic
	@Column(name = "SMS_DATE")
	private Timestamp sms_date;

	@Basic
	@Column(name = "SESSION_ID", length = 200)
	private String session_id;

	@Basic
	@Column(name = "REC_USER", length = 30)
	private String rec_user;

	@Basic
	@Column(name = "SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name = "NEW_STYLE")
	private Long new_style;

	@Basic
	@Column(name = "FAILED")
	private Long failed;
	
	@Basic
	@Column(name = "DELIVERED")
	private Long delivered;
	
	@Basic
	@Column(name = "DEL_REP_FAIL_COUNTER")
	private Long del_rep_fail_counter;
	
	@Basic
	@Column(name = "SMSC_MESSAGE_ID", length = 20)
	private String smsc_message_id;
	
	
	@Transient
	private String operator;
	
	@Transient
	private String sms_type_descr;
	
	@Transient
	private String status_descr;
	
	public LogSMS() {
	}


	public Long getSms_id() {
		return sms_id;
	}


	public void setSms_id(Long sms_id) {
		this.sms_id = sms_id;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public Long getSms_type() {
		return sms_type;
	}


	public void setSms_type(Long sms_type) {
		this.sms_type = sms_type;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getSms_text() {
		return sms_text;
	}


	public void setSms_text(String sms_text) {
		this.sms_text = sms_text;
	}


	public Timestamp getSms_date() {
		return sms_date;
	}


	public void setSms_date(Timestamp sms_date) {
		this.sms_date = sms_date;
	}


	public String getSession_id() {
		return session_id;
	}


	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}


	public String getRec_user() {
		return rec_user;
	}


	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}


	public Long getService_id() {
		return service_id;
	}


	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}


	public Long getNew_style() {
		return new_style;
	}


	public void setNew_style(Long new_style) {
		this.new_style = new_style;
	}


	public Long getFailed() {
		return failed;
	}


	public void setFailed(Long failed) {
		this.failed = failed;
	}


	public Long getDelivered() {
		return delivered;
	}


	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}


	public Long getDel_rep_fail_counter() {
		return del_rep_fail_counter;
	}


	public void setDel_rep_fail_counter(Long del_rep_fail_counter) {
		this.del_rep_fail_counter = del_rep_fail_counter;
	}


	public String getSmsc_message_id() {
		return smsc_message_id;
	}


	public void setSmsc_message_id(String smsc_message_id) {
		this.smsc_message_id = smsc_message_id;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getSms_type_descr() {
		return sms_type_descr;
	}


	public void setSms_type_descr(String sms_type_descr) {
		this.sms_type_descr = sms_type_descr;
	}


	public String getStatus_descr() {
		return status_descr;
	}


	public void setStatus_descr(String status_descr) {
		this.status_descr = status_descr;
	}
}
