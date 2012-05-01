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
					name = "TmpLogSMS.getBySessionId", 
					query = "select e from TmpLogSMS e where e.sessionId = :sessId"),
		@NamedQuery(
					name = "TmpLogSMS.getForSending", 
					query = "select e from TmpLogSMS e where trunc(e.smsDate) >= trunc(sysdate-1) and e.status = 0 order by e.smsId"),
	    @NamedQuery(
					name = "TmpLogSMS.getDeliveryReport", 
					query = "select e from TmpLogSMS e where trunc(e.smsDate) >= trunc(sysdate-1) and e.status > 0 and e.delivered <> 1 order by e.smsId"),					
		@NamedQuery(
					name = "TmpLogSMS.getBySessionIdCount", 
					query = "select count(e) from TmpLogSMS e where e.sessionId = :sessId")
})
@Entity
@Table(name = "tmp_log_sms", schema = "info")
public class TmpLogSMS implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "SMS_ID")
	private long smsId;

	@Basic
	@Column(name = "STATUS")
	private Long status;

	@Basic
	@Column(name = "SMS_TYPE")
	private Long smsType;

	@Basic
	@Column(name = "PHONE", length = 30)
	private String phone;

	@Basic
	@Column(name = "SMS_TEXT", length = 4000)
	private String smsText;

	@Basic
	@Column(name = "SMS_DATE")
	private Timestamp smsDate;

	@Basic
	@Column(name = "SESSION_ID", length = 200)
	private String sessionId;

	@Basic
	@Column(name = "REC_USER", length = 30)
	private String recUser;

	@Basic
	@Column(name = "SERVICE_ID")
	private Long serviceId;

	@Basic
	@Column(name = "NEW_STYLE")
	private Long newStyle;

	@Basic
	@Column(name = "FAILED")
	private Long failed;
	
	
	@Basic
	@Column(name = "DELIVERED")
	private Long delivered;
	
	@Basic
	@Column(name = "DEL_REP_FAIL_COUNTER")
	private Long delRepFailCounter;
	
	@Basic
	@Column(name = "SMSC_MESSAGE_ID", length = 20)
	private String smscMessageId;
	
	
	@Transient
	private String operator;
	

	public TmpLogSMS() {
	}

	public TmpLogSMS(long smsId) {
		this.smsId = smsId;
	}

	public TmpLogSMS(long smsId, Long status, Long smsType, String phone,
			String smsText, Timestamp smsDate, String sessionId,
			String recUser, Long serviceId) {
		this.smsId = smsId;
		this.status = status;
		this.smsType = smsType;
		this.phone = phone;
		this.smsText = smsText;
		this.smsDate = smsDate;
		this.sessionId = sessionId;
		this.recUser = recUser;
		this.serviceId = serviceId;
	}

	public long getSmsId() {
		return smsId;
	}

	public void setSmsId(long smsId) {
		this.smsId = smsId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getSmsType() {
		return smsType;
	}

	public void setSmsType(Long smsType) {
		this.smsType = smsType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public Timestamp getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(Timestamp smsDate) {
		this.smsDate = smsDate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getNewStyle() {
		return newStyle;
	}

	public void setNewStyle(Long newStyle) {
		this.newStyle = newStyle;
	}

	public Long getFailed() {
		return failed;
	}

	public void setFailed(Long failed) {
		this.failed = failed;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getDelivered() {
		return delivered;
	}

	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}

	public Long getDelRepFailCounter() {
		return delRepFailCounter;
	}

	public void setDelRepFailCounter(Long delRepFailCounter) {
		this.delRepFailCounter = delRepFailCounter;
	}

	public String getSmscMessageId() {
		return smscMessageId;
	}

	public void setSmscMessageId(String smscMessageId) {
		this.smscMessageId = smscMessageId;
	}
}
