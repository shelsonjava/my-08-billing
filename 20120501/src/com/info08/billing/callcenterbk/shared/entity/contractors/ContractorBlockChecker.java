package com.info08.billing.callcenterbk.shared.entity.contractors;

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
 * The persistent class for the CONTRACTOR_BLOCK_CHECKER database table.
 * 
 */
@NamedQueries({
	@NamedQuery(
				name="ContractorBlockChecker.getPendingContractors",
				query="select e from ContractorBlockChecker e where trunc(e.rec_date) >= trunc(sysdate-10) and e.status = 0 ")
})
@Entity
@Table(name = "CONTRACTOR_BLOCK_CHECKER", schema = "INFO")
public class ContractorBlockChecker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTRACTOR_BLOCK_CHECKER_ID_GENERATOR", sequenceName = "SEQ_CONTR_BLOCK_CHECKER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_BLOCK_CHECKER_ID_GENERATOR")
	private Long id;

	@Column(name = "CONTRACT_ID")
	private Long contract_id;

	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name = "STATUS")
	private Long status;

	@Basic
	@Column(name = "UPD_DATE")
	private Timestamp updDate;

	public ContractorBlockChecker() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContract_id() {
		return contract_id;
	}

	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Timestamp getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Timestamp updDate) {
		this.updDate = updDate;
	}
}