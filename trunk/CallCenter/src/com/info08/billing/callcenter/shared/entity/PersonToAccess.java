package com.info08.billing.callcenter.shared.entity;

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

@NamedQueries({ 
		@NamedQuery(
					name = "Person.getAllPersonToAccess", 
					query = "select e from PersonToAccess e "),
		@NamedQuery(
					name = "Person.getAllPersonToAccessByPersId", 
					query = "select e from PersonToAccess e where e.personelId = :persId")
					
})
@Entity
@Table(name = "personnel_to_access", schema = "info")
public class PersonToAccess implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "PERS_TO_ACC_ID")
	@SequenceGenerator(name = "personnel_to_acc_sequence", sequenceName = "seq_pers_to_acc")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personnel_to_acc_sequence")
	private Long persToAccId;

	@Basic
	@Column(name = "PERSONNEL_ID", nullable = false)
	private Long personelId;

	@Basic
	@Column(name = "ACCESS_ID", nullable = false)
	private Long accessId;

	public PersonToAccess() {
	}

	public Long getPersToAccId() {
		return persToAccId;
	}


	public void setPersToAccId(Long persToAccId) {
		this.persToAccId = persToAccId;
	}


	public Long getPersonelId() {
		return personelId;
	}

	public void setPersonelId(Long personelId) {
		this.personelId = personelId;
	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}
}
