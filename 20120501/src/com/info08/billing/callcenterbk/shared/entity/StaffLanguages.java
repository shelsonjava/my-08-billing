package com.info08.billing.callcenterbk.shared.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "STAFF_LANGUAGES")
public class StaffLanguages implements java.io.Serializable {

	private static final long serialVersionUID = 4693844952773726969L;

	@Id
	@Column(name = "STAFF_LANGUAGE_ID")
	@SequenceGenerator(name = "SEQ_STAFF_LANGUAGES_GENERATOR", sequenceName = "SEQ_STAFF_LANGUAGES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STAFF_LANGUAGES_GENERATOR")
	private long staff_language_id;

	@Basic
	@Column(name = "STAFF_ID")
	private Long staff_id;

	@Basic
	@Column(name = "LANGUAGE_DESCR_ID")
	private Long language_descr_id;

	@Basic
	@Column(name = "LANGUAGE_LEVEL_DESCR_ID")
	private Long language_level_descr_id;

	@Basic
	@Column(name = "CERTIFICATE_DESCR")
	private String certificate_descr;

	@Transient
	private String language_descr;

	@Transient
	private String language_level_descr;

	@Transient
	private String loggedUserName;

	public StaffLanguages() {
	}

	public long getStaff_language_id() {
		return staff_language_id;
	}

	public void setStaff_language_id(long staff_language_id) {
		this.staff_language_id = staff_language_id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public Long getLanguage_descr_id() {
		return language_descr_id;
	}

	public void setLanguage_descr_id(Long language_descr_id) {
		this.language_descr_id = language_descr_id;
	}

	public Long getLanguage_level_descr_id() {
		return language_level_descr_id;
	}

	public void setLanguage_level_descr_id(Long language_level_descr_id) {
		this.language_level_descr_id = language_level_descr_id;
	}

	public String getCertificate_descr() {
		return certificate_descr;
	}

	public void setCertificate_descr(String certificate_descr) {
		this.certificate_descr = certificate_descr;
	}

	public String getLanguage_descr() {
		return language_descr;
	}

	public void setLanguage_descr(String language_descr) {
		this.language_descr = language_descr;
	}

	public String getLanguage_level_descr() {
		return language_level_descr;
	}

	public void setLanguage_level_descr(String language_level_descr) {
		this.language_level_descr = language_level_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
