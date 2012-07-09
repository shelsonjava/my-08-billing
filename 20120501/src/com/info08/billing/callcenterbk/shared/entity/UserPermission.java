package com.info08.billing.callcenterbk.shared.entity;

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
		@NamedQuery(name = "Users.getAllUserPermissions", query = "select e from UserPermission e "),
		@NamedQuery(name = "Users.getAllUserPermissionsByPermissionId", query = "select e from UserPermission e where e.user_id = :user_id")

})
@Entity
@Table(name = "USER_PERMISSION")
public class UserPermission implements java.io.Serializable {

	private static final long serialVersionUID = 4653314952773716984L;

	@Id
	@Column(name = "USER_PERMISSION_ID")
	@SequenceGenerator(name = "SEQ_USER_PERMISSION_GENERATOR", sequenceName = "SEQ_USER_PERMISSION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_PERMISSION_GENERATOR")
	private Long user_permission_id;

	@Basic
	@Column(name = "USER_ID", nullable = false)
	private Long user_id;

	@Basic
	@Column(name = "PERMISSION_ID", nullable = false)
	private Long permission_id;

	public UserPermission() {
	}

	public Long getUser_permission_id() {
		return user_permission_id;
	}

	public void setUser_permission_id(Long user_permission_id) {
		this.user_permission_id = user_permission_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(Long permission_id) {
		this.permission_id = permission_id;
	}

}
