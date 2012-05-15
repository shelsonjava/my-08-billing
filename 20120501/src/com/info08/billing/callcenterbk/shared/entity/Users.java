package com.info08.billing.callcenterbk.shared.entity;

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
		@NamedQuery(name = "Users.selectByUserName", query = "select e from Users e where e.user_name = :usrName"),
		@NamedQuery(name = "Users.getAllUsers", query = "select e from Users e order by e.user_id")

})
@Entity
@Table(name = "USERS", schema = "ccare")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "USER_ID")
	@SequenceGenerator(name = "SEQ_USERS_GENERATOR", sequenceName = "SEQ_USERS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USERS_GENERATOR")
	private long user_id;

	@Basic
	@Column(name = "USER_FIRSTNAME", nullable = false, length = 100)
	private String user_firstname;

	@Basic
	@Column(name = "USER_LASTNAME", nullable = false, length = 150)
	private String user_lastname;

	@Basic
	@Column(name = "DEPARTMENT_ID", nullable = false)
	private Long department_id;

	@Basic
	@Column(name = "USER_NAME", nullable = false, length = 30)
	private String user_name;

	@Basic
	@Column(name = "USER_PASSWORD", nullable = false, length = 6)
	private String user_password;

	@SuppressWarnings("unused")
	@Transient
	private String fullPersonName;

	@Transient
	private String loggedUserName;

	@Transient
	private String department_name;

	@Transient
	private Map<String, String> userPerms;

	public Users() {
	}

	public Users(long user_id) {
		this.user_id = user_id;
	}

	public Users(long user_id, String user_firstname, String user_lastname,
			Long department_id, String user_name, String user_password,
			String department_name) {
		this.user_id = user_id;
		this.user_firstname = user_firstname;
		this.user_lastname = user_lastname;
		this.department_id = department_id;
		this.user_name = user_name;
		this.user_password = user_password;
		this.department_name = department_name;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_firstname() {
		return user_firstname;
	}

	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}

	public String getUser_lastname() {
		return user_lastname;
	}

	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getFullPersonName() {
		return getUser_name() + " ( " + getUser_firstname() + " "
				+ getUser_lastname() + " ) ";
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

}
