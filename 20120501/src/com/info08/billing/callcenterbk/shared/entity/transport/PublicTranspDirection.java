package com.info08.billing.callcenterbk.shared.entity.transport;

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
 * The persistent class for the BUS_ROUTES database table.
 * 
 */
@Entity
@Table(name = "PUBLIC_TRANSP_DIR", schema = "ccare")
public class PublicTranspDirection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PUBLIC_TRANSP_DIR_ID_GENERATOR", sequenceName = "SEQ_PUBLIC_TRANSP_DIR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUBLIC_TRANSP_DIR_ID_GENERATOR")
	@Column(name = "PT_ID")
	private Long pt_id;

	@Basic
	@Column(name = "CYCLED_ID")
	private Long cycled_id;

	@Basic
	@Column(name = "DIR_NUM")
	private String dir_num;

	@Basic
	@Column(name = "DIR_OLD_NUM")
	private String dir_old_num;

	@Basic
	@Column(name = "SERVICE_ID")
	private Long service_id;

	@Basic
	@Column(name = "REMARK_TYPE")
	private Long remark_type;

	@Transient
	private String remark_type_descr;

	@Transient
	private String cycle_descr;

	@Transient
	private String service_descr;

	@Transient
	private String loggedUserName;

	public PublicTranspDirection() {
	}

	public Long getPt_id() {
		return pt_id;
	}

	public void setPt_id(Long pt_id) {
		this.pt_id = pt_id;
	}

	public Long getCycled_id() {
		return cycled_id;
	}

	public void setCycled_id(Long cycled_id) {
		this.cycled_id = cycled_id;
	}

	public String getDir_num() {
		return dir_num;
	}

	public void setDir_num(String dir_num) {
		this.dir_num = dir_num;
	}

	public String getDir_old_num() {
		return dir_old_num;
	}

	public void setDir_old_num(String dir_old_num) {
		this.dir_old_num = dir_old_num;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public String getService_descr() {
		return service_descr;
	}

	public void setService_descr(String service_descr) {
		this.service_descr = service_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getCycle_descr() {
		return cycle_descr;
	}

	public void setCycle_descr(String cycle_descr) {
		this.cycle_descr = cycle_descr;
	}

	public Long getRemark_type() {
		return remark_type;
	}

	public void setRemark_type(Long remark_type) {
		this.remark_type = remark_type;
	}

	public String getRemark_type_descr() {
		return remark_type_descr;
	}

	public void setRemark_type_descr(String remark_type_descr) {
		this.remark_type_descr = remark_type_descr;
	}

}