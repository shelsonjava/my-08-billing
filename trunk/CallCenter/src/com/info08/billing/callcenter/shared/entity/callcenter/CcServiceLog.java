package com.info08.billing.callcenter.shared.entity.callcenter;

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
 * The persistent class for the CC_SERVICE_LOG database table.
 * 
 */
@Entity
@Table(name="CC_SERVICE_LOG", schema="INFO")
public class CcServiceLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CC_SERVICE_LOG_ID_GENERATOR", sequenceName="SEQ_CC_SERVICE_LOG")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CC_SERVICE_LOG_ID_GENERATOR")
	private Long id;

    @Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

    @Basic
	@Column(name="SERVICE_ID")
	private Long service_id;

    @Basic
	@Column(name="SESSION_ID")
	private String session_id;

    @Basic
	@Column(name="USER_NAME")
	private String user_name;

    public CcServiceLog() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}