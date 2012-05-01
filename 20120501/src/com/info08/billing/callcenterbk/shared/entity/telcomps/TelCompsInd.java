package com.info08.billing.callcenterbk.shared.entity.telcomps;

import java.io.Serializable;

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
 * The persistent class for the TEL_COMPS_IND database table.
 * 
 */
@Entity
@Table(name = "TEL_COMPS_IND", schema = "PAATA")
public class TelCompsInd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TEL_COMPS_IND_INDID_GENERATOR", sequenceName = "TEL_COMPS_IND_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEL_COMPS_IND_INDID_GENERATOR")
	@Column(name = "IND_ID")
	private Long ind_id;

	@Basic
	@Column(name = "CR")
	private Long cr;

	@Basic
	@Column(name = "END_IND")
	private Long end_ind;

	@Basic
	@Column(name = "ST_IND")
	private Long st_ind;

	@Basic
	@Column(name = "TEL_COMP_ID")
	private Long tel_comp_id;
	
	@Basic
	@Column(name = "COUNT_TYPE")
	private Long count_type;
	
	@Transient
	private String loggedUserName;
	
	@Transient
	private String cr_descr;
	
	@Transient
	private String count_type_descr;

	public TelCompsInd() {
	}

	public Long getInd_id() {
		return ind_id;
	}

	public void setInd_id(Long ind_id) {
		this.ind_id = ind_id;
	}

	public Long getCr() {
		return cr;
	}

	public void setCr(Long cr) {
		this.cr = cr;
	}

	public Long getEnd_ind() {
		return end_ind;
	}

	public void setEnd_ind(Long end_ind) {
		this.end_ind = end_ind;
	}

	public Long getSt_ind() {
		return st_ind;
	}

	public void setSt_ind(Long st_ind) {
		this.st_ind = st_ind;
	}

	public Long getTel_comp_id() {
		return tel_comp_id;
	}

	public void setTel_comp_id(Long tel_comp_id) {
		this.tel_comp_id = tel_comp_id;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getCr_descr() {
		return cr_descr;
	}

	public void setCr_descr(String cr_descr) {
		this.cr_descr = cr_descr;
	}

	public Long getCount_type() {
		return count_type;
	}

	public void setCount_type(Long count_type) {
		this.count_type = count_type;
	}

	public String getCount_type_descr() {
		return count_type_descr;
	}

	public void setCount_type_descr(String count_type_descr) {
		this.count_type_descr = count_type_descr;
	}
}