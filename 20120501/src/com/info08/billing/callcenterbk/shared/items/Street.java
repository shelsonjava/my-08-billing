package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

@Deprecated
public class Street implements Serializable {

	private static final long serialVersionUID = -5016417466764808176L;
	private Long street_id;
	private Long city_id;
	private String street_name_geo;
	private Long deleted;
	private String street_location_geo;
	private Long city_region_id;
	private Long street_district_id;

	public Long getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getStreet_name_geo() {
		return street_name_geo;
	}

	public void setStreet_name_geo(String street_name_geo) {
		this.street_name_geo = street_name_geo;
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

	public Long getCity_region_id() {
		return city_region_id;
	}

	public void setCity_region_id(Long city_region_id) {
		this.city_region_id = city_region_id;
	}

	public Long getStreet_district_id() {
		return street_district_id;
	}

	public void setStreet_district_id(Long street_district_id) {
		this.street_district_id = street_district_id;
	}
}
