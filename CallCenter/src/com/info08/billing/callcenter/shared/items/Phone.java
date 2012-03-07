package com.info08.billing.callcenter.shared.items;

import java.sql.Timestamp;

public class Phone{

	private Integer phone_Id;
	private String phone;
	private Timestamp rec_date;
	private String rec_user;
	private Integer deleted;
	private String upd_user;
	private Integer phone_type_id;
	private Integer abonent_id;
	private Integer is_hide;
	private String is_hide_descr;
	private Integer phone_state_id;
	private String phone_state;
	private Integer phone_status_id;
	private String phone_status;
	private String phone_type;
	private String is_parallel_descr;
	private Integer is_parallel;
	private Abonent abonent;
	
	public Integer getPhone_Id() {
		return phone_Id;
	}

	public void setPhone_Id(Integer phone_Id) {
		this.phone_Id = phone_Id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPhone_state_id() {
		return phone_state_id;
	}

	public void setPhone_state_id(Integer phone_state_id) {
		this.phone_state_id = phone_state_id;
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

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Integer getPhone_type_id() {
		return phone_type_id;
	}

	public void setPhone_type_id(Integer phone_type_id) {
		this.phone_type_id = phone_type_id;
	}

	public Integer getAbonent_id() {
		return abonent_id;
	}

	public void setAbonent_id(Integer abonent_id) {
		this.abonent_id = abonent_id;
	}

	public Integer getIs_hide() {
		return is_hide;
	}

	public void setIs_hide(Integer is_hide) {
		this.is_hide = is_hide;
	}

	public String getIs_hide_descr() {
		return is_hide_descr;
	}

	public void setIs_hide_descr(String is_hide_descr) {
		this.is_hide_descr = is_hide_descr;
	}

	public String getPhone_state() {
		return phone_state;
	}

	public void setPhone_state(String phone_state) {
		this.phone_state = phone_state;
	}

	public Integer getPhone_status_id() {
		return phone_status_id;
	}

	public void setPhone_status_id(Integer phone_status_id) {
		this.phone_status_id = phone_status_id;
	}

	public String getPhone_status() {
		return phone_status;
	}

	public void setPhone_status(String phone_status) {
		this.phone_status = phone_status;
	}

	public String getPhone_type() {
		return phone_type;
	}

	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	public String getIs_parallel_descr() {
		return is_parallel_descr;
	}

	public void setIs_parallel_descr(String is_parallel_descr) {
		this.is_parallel_descr = is_parallel_descr;
	}

	public Integer getIs_parallel() {
		return is_parallel;
	}

	public void setIs_parallel(Integer is_parallel) {
		this.is_parallel = is_parallel;
	}

	public Abonent getAbonent() {
		return abonent;
	}

	public void setAbonent(Abonent abonent) {
		this.abonent = abonent;
	}	
}
