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
@Table(name = "FIRSTNAMES")
public class FirstName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_FIRSTNAME_GENERATOR", sequenceName = "SEQ_FIRSTNAME")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIRSTNAME_GENERATOR")
	@Column(name = "FIRSTNAME_ID")
	private Long firstname_id;

	@Basic
	@Column(name = "FIRSTNAME")
	private String firstname;

	@Transient
	private String loggedUserName;

	public FirstName() {
	}

	public Long getFirstname_id() {
		return firstname_id;
	}

	public void setFirstname_id(Long firstname_id) {
		this.firstname_id = firstname_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
