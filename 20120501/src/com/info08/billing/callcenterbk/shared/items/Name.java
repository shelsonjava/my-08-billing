package com.info08.billing.callcenterbk.shared.items;

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

@Entity
@Table(name = "NAMES")
public class Name implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_FIRSTNAME_GENERATOR", sequenceName = "SEQ_FIRSTNAME")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIRSTNAME_GENERATOR")
	@Column(name = "NAME_ID")
	private Long name_id;

	@Basic
	@Column(name = "NAME_DESCR")
	private String name_descr;

	@Transient
	private String loggedUserName;

	public Name() {
	}

	public Long getName_id() {
		return name_id;
	}

	public void setName_id(Long name_id) {
		this.name_id = name_id;
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

}
