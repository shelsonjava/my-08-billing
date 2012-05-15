package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

public class Departments implements Serializable {

	private static final long serialVersionUID = -180371369122938828L;
	private Integer department_id;
	private String department_name;

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

}
