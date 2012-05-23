package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

@Deprecated
public class Street implements Serializable {

	private static final long serialVersionUID = -5016417466764808176L;
	private Long street_id;
	private Long town_id;
	private String street_name;
	private Long deleted;
	private String street_location_geo;
	private Long town_district_id;
	private Long street_district_id;

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getStreet_location_geo() {
		return street_location_geo;
	}

	public void setStreet_location_geo(String street_location_geo) {
		this.street_location_geo = street_location_geo;
	}

	public Long getTown_district_id() {
		return town_district_id;
	}

	public void setTown_district_id(Long town_district_id) {
		this.town_district_id = town_district_id;
	}

	public Long getStreet_district_id() {
		return street_district_id;
	}

	public void setStreet_district_id(Long street_district_id) {
		this.street_district_id = street_district_id;
	}
}
