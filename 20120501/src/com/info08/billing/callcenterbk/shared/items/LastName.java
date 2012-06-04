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
@Table(name = "LASTNAMES")
public class LastName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_LASTNAME_GENERATOR", sequenceName = "SEQ_LASTNAME")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LASTNAME_GENERATOR")
	@Column(name = "LASTNAME_ID")
	private Long lastname_id;

	@Basic
	@Column(name = "LASTNAME")
	private String lastname;

	@Transient
	private String loggedUserName;

	public LastName() {
	}

	public Long getLastname_id() {
		return lastname_id;
	}

	public void setLastname_id(Long lastname_id) {
		this.lastname_id = lastname_id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
