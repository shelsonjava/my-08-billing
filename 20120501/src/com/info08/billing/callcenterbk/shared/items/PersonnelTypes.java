package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class PersonnelTypes implements Serializable {

	private static final long serialVersionUID = -180371369122938828L;
	private Integer personnel_type_id;
	private String personnel_type_name_geo;
	private Timestamp rec_date;
	private String rec_user;
	private Integer deleted;

	public Integer getPersonnel_type_id() {
		return personnel_type_id;
	}

	public void setPersonnel_type_id(Integer personnel_type_id) {
		this.personnel_type_id = personnel_type_id;
	}

	public String getPersonnel_type_name_geo() {
		return personnel_type_name_geo;
	}

	public void setPersonnel_type_name_geo(String personnel_type_name_geo) {
		this.personnel_type_name_geo = personnel_type_name_geo;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
