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
@Table(name = "FAMILYNAMES")
public class FamilyNames implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_LASTNAME_GENERATOR", sequenceName = "SEQ_LASTNAME")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LASTNAME_GENERATOR")
	@Column(name = "FAMILYNAME_ID")
	private Long familyname_id;

	@Basic
	@Column(name = "FAMILYNAME")
	private String familyname;

	@Transient
	private String loggedUserName;

	public FamilyNames() {
	}

	public Long getFamilyname_id() {
		return familyname_id;
	}

	public void setFamilyname_id(Long familyname_id) {
		this.familyname_id = familyname_id;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

}
