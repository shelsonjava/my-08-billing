package com.info08.billing.callcenterbk.shared.entity.session;

import java.io.Serializable;
import java.sql.Timestamp;

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


/**
 * The persistent class for the LOG_SESSIONS database table.
 * 
 */

@NamedQueries({
	@NamedQuery(
				name="CallSession.getByCallSessionId",
				query="select e from CallSession e where e.session_id = :callSessId")
})

@Entity
@Table(name="CALL_SESSIONS")
public class CallSession implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_CALL_SESSION_ID", sequenceName = "SEQ_CALL_SESSION_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CALL_SESSION_ID")
	@Column(name = "CALL_SESSION_ID")
	private Long call_session_id;
	
	@Id
	@Column(name="SESSION_ID")
	private String session_id;

	@Basic
	@Column(name="CALL_KIND")
	private Long call_kind;

	@Basic
	@Column(name="CALL_DURATION")
	private Long call_duration;

	@Basic
    @Column(name="CALL_END_DATE")
	private Timestamp call_end_date;

	@Basic
	@Column(name="REJECT_TYPE")
	private Long reject_type;

	@Basic
	@Column(name="SWITCH_OVER_TYPE")
	private Long switch_ower_type;

	@Basic
	@Column(name="CALL_PHONE")
	private String call_phone;

	@Basic
	@Column(name="CALL_QUALITY")
	private Long call_quality;

	@Basic
    @Column(name="CALL_START_DATE")
	private Timestamp call_start_date;

	@Basic
	@Column(name="UNAME")
	private String uname;

	@Basic
	@Column(name="YEAR_MONTH")
	private Long year_month;

    public CallSession() {
    }

	public Long getCall_session_id() {
		return call_session_id;
	}

	public void setCall_session_id(Long call_session_id) {
		this.call_session_id = call_session_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public Long getCall_kind() {
		return call_kind;
	}

	public void setCall_kind(Long call_kind) {
		this.call_kind = call_kind;
	}

	public Long getCall_duration() {
		return call_duration;
	}

	public void setCall_duration(Long call_duration) {
		this.call_duration = call_duration;
	}

	public Timestamp getCall_end_date() {
		return call_end_date;
	}

	public void setCall_end_date(Timestamp call_end_date) {
		this.call_end_date = call_end_date;
	}

	public Long getReject_type() {
		return reject_type;
	}

	public void setReject_type(Long reject_type) {
		this.reject_type = reject_type;
	}

	public Long getSwitch_ower_type() {
		return switch_ower_type;
	}

	public void setSwitch_ower_type(Long switch_ower_type) {
		this.switch_ower_type = switch_ower_type;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	public Long getCall_quality() {
		return call_quality;
	}

	public void setCall_quality(Long call_quality) {
		this.call_quality = call_quality;
	}

	public Timestamp getCall_start_date() {
		return call_start_date;
	}

	public void setCall_start_date(Timestamp call_start_date) {
		this.call_start_date = call_start_date;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Long getYear_month() {
		return year_month;
	}

	public void setYear_month(Long year_month) {
		this.year_month = year_month;
	}
}