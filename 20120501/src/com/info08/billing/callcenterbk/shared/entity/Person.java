package com.info08.billing.callcenterbk.shared.entity;

import java.sql.Timestamp;
import java.util.Map;

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
import javax.persistence.Transient;

@NamedQueries({
		@NamedQuery(name = "Person.selectBuUserName", query = "select e from Person e where e.userName = :usrName"),
		@NamedQuery(name = "Person.getAllPersonel", query = "select e from Person e order by e.personelId")

})
@Entity
@Table(name = "personnel", schema = "info")
public class Person implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "PERSONNEL_ID")
	@SequenceGenerator(name = "personnel_sequence", sequenceName = "personnel_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personnel_sequence")
	private long personelId;

	@Basic
	@Column(name = "PERSONNEL_NAME", nullable = false, length = 100)
	private String personelName;

	@Basic
	@Column(name = "PERSONNEL_SURNAME", nullable = false, length = 150)
	private String personelSurName;

	@Basic
	@Column(name = "PERSONNEL_TYPE_ID", nullable = false)
	private Long personelTypeId;

	@Basic
	@Column(name = "USER_NAME", nullable = false, length = 30)
	private String userName;

	@Basic
	@Column(name = "PASSWORD", nullable = false, length = 6)
	private String password;

	@Basic
	@Column(name = "REC_DATE", nullable = false)
	private Timestamp recDate;

	@Basic
	@Column(name = "REC_USER", nullable = false, length = 30)
	private String recUser;

	@Basic
	@Column(name = "DELETED", nullable = false)
	private Long deleted;

	@Basic
	@Column(name = "ACCESS_ID", nullable = false)
	private Long accessId;

	@Column(name = "UPD_USER", length = 30)
	private String updUser;

	@SuppressWarnings("unused")
	@Transient
	private String fullPersonName;

	@Transient
	private String loggedUserName;

	@Transient
	private String personelType;

	@Transient
	private Map<String, String> userPerms;

	public Person() {
	}

	public Person(long personelId) {
		this.personelId = personelId;
	}

	public Person(long personelId, String personelName, String personelSurName,
			Long personelTypeId, String userName, String password,
			Timestamp recDate, String recUser, Long deleted, Long accessId,
			String updUser, String personelType) {
		this.personelId = personelId;
		this.personelName = personelName;
		this.personelSurName = personelSurName;
		this.personelTypeId = personelTypeId;
		this.userName = userName;
		this.password = password;
		this.recDate = recDate;
		this.recUser = recUser;
		this.deleted = deleted;
		this.accessId = accessId;
		this.updUser = updUser;
		this.personelType = personelType;
	}

	public long getPersonelId() {
		return personelId;
	}

	public void setPersonelId(long personelId) {
		this.personelId = personelId;
	}

	public String getPersonelName() {
		return personelName;
	}

	public void setPersonelName(String personelName) {
		this.personelName = personelName;
	}

	public String getPersonelSurName() {
		return personelSurName;
	}

	public void setPersonelSurName(String personelSurName) {
		this.personelSurName = personelSurName;
	}

	public Long getPersonelTypeId() {
		return personelTypeId;
	}

	public void setPersonelTypeId(Long personelTypeId) {
		this.personelTypeId = personelTypeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRecDate() {
		return recDate;
	}

	public void setRecDate(Timestamp recDate) {
		this.recDate = recDate;
	}

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getFullPersonName() {
		return getUserName() + " ( " + getPersonelName() + " "
				+ getPersonelSurName() + " ) ";
	}

	public void setFullPersonName(String fullPersonName) {
		this.fullPersonName = fullPersonName;
	}

	public Map<String, String> getUserPerms() {
		return userPerms;
	}

	public void setUserPerms(Map<String, String> userPerms) {
		this.userPerms = userPerms;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getPersonelType() {
		return personelType;
	}

	public void setPersonelType(String personelType) {
		this.personelType = personelType;
	}
}
