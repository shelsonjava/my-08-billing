package com.info08.billing.callcenter.shared.entity.block;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the BLOCK_LIST database table.
 * 
 */
@Entity
@Table(name="BLOCK_LIST", schema="INFO")
public class BlockList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BLOCK_LIST_ID_GENERATOR", sequenceName="SEQ_BLOCK_LIST")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BLOCK_LIST_ID_GENERATOR")
	private Long id;

	@Basic
	@Column(name="BLOCK_TYPE")
	private Long block_type;

	@Basic
	@Column(name="DELETED")
	private Long deleted;

	@Basic
	@Column(name="MAIN_DETAIL_ID")
	private Long main_detail_id;

	@Basic
	@Column(name="MAIN_ID")
	private Long main_id;

	@Basic
	@Column(name="NOTE")
	private String note;

	@Basic
	@Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="STATUS")
	private Long status;

	@Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;
	
	@Transient
	private String loggedUserName;

	@Transient
	private String orgName;

	@Transient
	private String orgDepName;
	
	@Transient
	private LinkedHashMap<String, String> blockListPhones;

    public BlockList() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBlock_type() {
		return block_type;
	}

	public void setBlock_type(Long block_type) {
		this.block_type = block_type;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getMain_detail_id() {
		return main_detail_id;
	}

	public void setMain_detail_id(Long main_detail_id) {
		this.main_detail_id = main_detail_id;
	}

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDepName() {
		return orgDepName;
	}

	public void setOrgDepName(String orgDepName) {
		this.orgDepName = orgDepName;
	}

	public LinkedHashMap<String, String> getBlockListPhones() {
		return blockListPhones;
	}

	public void setBlockListPhones(LinkedHashMap<String, String> blockListPhones) {
		this.blockListPhones = blockListPhones;
	}
}