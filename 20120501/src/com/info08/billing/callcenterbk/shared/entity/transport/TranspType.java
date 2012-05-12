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
 * The persistent class for the TRANSPORT_TYPES database table.
 * 
 */
@Entity
@Table(name="TRANSP_TYPES",schema="ccare")
public class TranspType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seq_transp_type_id", sequenceName="seq_transp_type_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_transp_type_id")
	@Column(name="TRANSP_TYPE_ID")
	private Long transp_type_id;

	@Basic
	@Column(name="NAME_DESCR")
	private String name_descr;
	
	@Basic
	@Column(name="KIND")
	private Long kind;

	@Transient
	private String loggedUserName;
	
	@Transient
	private String kindDescr;

    public TranspType() {
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

	public Long getKind() {
		return kind;
	}

	public void setKind(Long kind) {
		this.kind = kind;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getKindDescr() {
		return kindDescr;
	}

	public void setKindDescr(String kindDescr) {
		this.kindDescr = kindDescr;
	}
}