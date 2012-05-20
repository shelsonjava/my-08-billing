package com.info08.billing.callcenterbk.shared.entity.contractors;

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
 * The persistent class for the CONTRACTOR_PHONES database table.
 * 
 */
@Entity
@Table(name = "CONTRACTOR_PHONES", schema = "ccare")
public class ContractorPhone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTRACTOR_PHONES_CPID_GENERATOR", sequenceName = "CPID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_PHONES_CPID_GENERATOR")
	@Column(name = "CP_ID")
	private Long cp_id;

	@Basic
	@Column(name = "DELETED")
	private Long deleted;

	@Basic
	@Column(name = "LIMITED")
	private Long limited;

	@Basic
	@Column(name = "MAIN_DETAIL_ID")
	private Long main_detail_id;

	@Basic
	@Column(name = "ORGANIZATION_ID")
	private Long organization_id;

	@Basic
	@Column(name = "PHONE")
	private String phone;

	@Basic
	@Column(name = "PHONE_ID")
	private Long phone_id;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "REC_USER")
	private String rec_user;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name = "UPD_USER")
	private String upd_user;

	@Basic
	@Column(name = "CONTRACT_ID")
	private Long contract_id;

	public ContractorPhone() {
	}

	public Long getCp_id() {
		return cp_id;
	}

	public void setCp_id(Long cp_id) {
		this.cp_id = cp_id;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getLimited() {
		return limited;
	}

	public void setLimited(Long limited) {
		this.limited = limited;
	}

	public Long getMain_detail_id() {
		return main_detail_id;
	}

	public void setMain_detail_id(Long main_detail_id) {
		this.main_detail_id = main_detail_id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(Long phone_id) {
		this.phone_id = phone_id;
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

	public Long getContract_id() {
		return contract_id;
	}

	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}
}