package com.info08.billing.callcenter.shared.entity.admin;

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
import javax.persistence.Transient;


/**
 * The persistent class for the FIXED_OPERATOR_PREFIXES database table.
 * 
 */
@NamedQueries({
	@NamedQuery(
				name="FixedOperatorPrefixe.getAll",
				query="select e from FixedOperatorPrefixe e where e.deleted = 0")
})
@Entity
@Table(name="FIXED_OPERATOR_PREFIXES", schema="INFO")
public class FixedOperatorPrefixe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIXED_OPERATOR_PREFIXES_ID_GENERATOR", sequenceName="SEQ_FIXED_OPER_PREF")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIXED_OPERATOR_PREFIXES_ID_GENERATOR")
	private Long id;

	@Basic
	@Column(name="PREFIX")
	private String prefix;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;
    
	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String loggedUserName;

    public FixedOperatorPrefixe() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
}