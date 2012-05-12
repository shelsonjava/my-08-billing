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
 * The persistent class for the TRANSP_COMPANIES database table.
 * 
 */
@Entity
@Table(name="TRANSP_COMPANIES", schema="ccare")
public class TranspCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRANSPORT_COMPANIES_TRANSPORTCOMPANYID_GENERATOR", sequenceName="TRANSPORT_COMPANY_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRANSPORT_COMPANIES_TRANSPORTCOMPANYID_GENERATOR")
	@Column(name="TRANSPORT_COMPANY_ID")
	private Long transp_comp_id;

	@Basic
	@Column(name="transp_type_id")
	private Long transp_type_id;
	
	@Basic
	@Column(name="name_descr")
	private String name_descr;
	
	@Transient
	private String loggedUserName;
	
	@Transient
	private String transport_type;

    public TranspCompany() {
    }

	public Long getTransp_comp_id() {
		return transp_comp_id;
	}

	public void setTransp_comp_id(Long transp_comp_id) {
		this.transp_comp_id = transp_comp_id;
	}

	public Long getTransp_type_id() {
		return transp_type_id;
	}

	public void setTransp_type_id(Long transp_type_id) {
		this.transp_type_id = transp_type_id;
	}

	public String getName_descr() {
		return name_descr;
	}

	public void setName_descr(String name_descr) {
		this.name_descr = name_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getTransport_type() {
		return transport_type;
	}

	public void setTransport_type(String transport_type) {
		this.transport_type = transport_type;
	}
}