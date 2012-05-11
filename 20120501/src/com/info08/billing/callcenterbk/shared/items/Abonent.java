package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;
import java.sql.Timestamp;

public class Abonent implements Serializable {

	
	private static final long serialVersionUID = 7026086622425435823L;
	private Integer main_id;
	private Integer abonent_id;
	private Integer address_id;
	private Integer phone_id;
	private Integer firstname_id;
	private Integer lastname_id;
	private Integer city_id;
	private Integer street_id;
	private Integer city_region_id;
	private String firstname;
	private String lastname;
	private String phone;
	private String phone_state;
	private Integer phone_state_id;
	private Timestamp upd_date;
	private String city;
	private String street;
	private String upd_user;
	private Integer abonent_hide;
	private Integer phone_parallel;
	private Integer phone_status_id;
	private Integer phone_type_id;
	private Integer address_hide;
	private String address_suffix_geo;
	private String addr_number;
	private String addr_block;
	private String addr_appt;
	private String addr_descr;
	private String loggedUserName;
	private Integer street_district_id;
	private String street_location_geo;
	private Integer deleted;

	public Integer getMain_id() {
		return main_id;
	}

	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}

	public Integer getAbonent_id() {
		return abonent_id;
	}

	public void setAbonent_id(Integer abonent_id) {
		this.abonent_id = abonent_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone_state() {
		return phone_state;
	}

	public void setPhone_state(String phone_state) {
		this.phone_state = phone_state;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}

	public Integer getFirstname_id() {
		return firstname_id;
	}

	public void setFirstname_id(Integer firstname_id) {
		this.firstname_id = firstname_id;
	}

	public Integer getLastname_id() {
		return lastname_id;
	}

	public void setLastname_id(Integer lastname_id) {
		this.lastname_id = lastname_id;
	}

	public Integer getAbonent_hide() {
		return abonent_hide;
	}

	public void setAbonent_hide(Integer abonent_hide) {
		this.abonent_hide = abonent_hide;
	}

	public Integer getPhone_parallel() {
		return phone_parallel;
	}

	public void setPhone_parallel(Integer phone_parallel) {
		this.phone_parallel = phone_parallel;
	}

	public Integer getPhone_status_id() {
		return phone_status_id;
	}

	public void setPhone_status_id(Integer phone_status_id) {
		this.phone_status_id = phone_status_id;
	}

	public Integer getPhone_state_id() {
		return phone_state_id;
	}

	public void setPhone_state_id(Integer phone_state_id) {
		this.phone_state_id = phone_state_id;
	}

	public Integer getPhone_type_id() {
		return phone_type_id;
	}

	public void setPhone_type_id(Integer phone_type_id) {
		this.phone_type_id = phone_type_id;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public Integer getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Integer street_id) {
		this.street_id = street_id;
	}

	public Integer getCity_region_id() {
		return city_region_id;
	}

	public void setCity_region_id(Integer city_region_id) {
		this.city_region_id = city_region_id;
	}

	public Integer getAddress_hide() {
		return address_hide;
	}

	public void setAddress_hide(Integer address_hide) {
		this.address_hide = address_hide;
	}

	public String getAddress_suffix_geo() {
		return address_suffix_geo;
	}

	public void setAddress_suffix_geo(String address_suffix_geo) {
		this.address_suffix_geo = address_suffix_geo;
	}

	public String getAddr_number() {
		return addr_number;
	}

	public void setAddr_number(String addr_number) {
		this.addr_number = addr_number;
	}

	public String getAddr_block() {
		return addr_block;
	}

	public void setAddr_block(String addr_block) {
		this.addr_block = addr_block;
	}

	public String getAddr_appt() {
		return addr_appt;
	}

	public void setAddr_appt(String addr_appt) {
		this.addr_appt = addr_appt;
	}

	public String getAddr_descr() {
		return addr_descr;
	}

	public void setAddr_descr(String addr_descr) {
		this.addr_descr = addr_descr;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public Integer getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}

	public Integer getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(Integer phone_id) {
		this.phone_id = phone_id;
	}

	public Integer getStreet_district_id() {
		return street_district_id;
	}

	public void setStreet_district_id(Integer street_district_id) {
		this.street_district_id = street_district_id;
	}

	public String getStreet_location_geo() {
		return street_location_geo;
	}

	public void setStreet_location_geo(String street_location_geo) {
		this.street_location_geo = street_location_geo;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "main_id = " + main_id + ", abonent_id = " + abonent_id
				+ ", address_id = " + address_id + ", phone_id = " + phone_id
				+ ", firstname_id = " + firstname_id + ", lastname_id = "
				+ lastname_id + ", city_id = " + city_id + ", street_id = "
				+ street_id + ", city_region_id = " + city_region_id
				+ ", firstname = " + firstname + ", lastname = " + lastname
				+ ", phone = " + phone + ", phone_state = " + phone_state
				+ ", phone_state_id = " + phone_state_id + ", upd_date = "
				+ upd_date + ", city = " + city + ", street = " + street
				+ ", upd_user = " + upd_user + ", abonent_hide = "
				+ abonent_hide + ", phone_parallel = " + phone_parallel
				+ ", phone_status_id = " + phone_status_id
				+ ", phone_type_id = " + phone_type_id + ", address_hide = "
				+ address_hide + ", address_suffix_geo = " + address_suffix_geo
				+ ", addr_number = " + addr_number + ", addr_block = "
				+ addr_block + ", addr_appt = " + addr_appt + ", addr_descr = "
				+ addr_descr + ", street_district_id = " + street_district_id
				+ ", loggedUserName = " + loggedUserName;
	}
}
