package com.info08.billing.callcenterbk.shared.entity.org;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the LEGAL_STATUSES database table.
 * 
 */
@Entity
@Table(name="LEGAL_STATUSES", schema="PAATA")
public class LegalStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LEGAL_STATUSES_LEGALSTATUSEID_GENERATOR" ,sequenceName="LEGAL_STATUSE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LEGAL_STATUSES_LEGALSTATUSEID_GENERATOR")
	@Column(name="LEGAL_STATUSE_ID")
	private Long legal_statuse_id;

	@Basic
	@Column(name="LEGAL_STATUSE")
	private String legal_statuse;

    public LegalStatus() {
    }

	public Long getLegal_statuse_id() {
		return legal_statuse_id;
	}

	public void setLegal_statuse_id(Long legal_statuse_id) {
		this.legal_statuse_id = legal_statuse_id;
	}

	public String getLegal_statuse() {
		return legal_statuse;
	}

	public void setLegal_statuse(String legal_statuse) {
		this.legal_statuse = legal_statuse;
	}
}