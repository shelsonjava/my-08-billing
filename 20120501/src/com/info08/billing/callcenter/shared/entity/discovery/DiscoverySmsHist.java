package com.info08.billing.callcenter.shared.entity.discovery;

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
 * The persistent class for the DISCOVERY_SMS_HIST database table.
 * 
 */
@Entity
@Table(name="DISCOVERY_SMS_HIST", schema="INFO")
public class DiscoverySmsHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DISCOVERY_SMS_HIST_ID_GENERATOR", sequenceName="SEQ_DISC_SMS_HIST")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DISCOVERY_SMS_HIST_ID_GENERATOR")
	private Long id;

	@Basic
	@Column(name="DISCOVERY_ID")
	private Long discovery_id;

    @Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

    public DiscoverySmsHist() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiscovery_id() {
		return discovery_id;
	}

	public void setDiscovery_id(Long discovery_id) {
		this.discovery_id = discovery_id;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}
}