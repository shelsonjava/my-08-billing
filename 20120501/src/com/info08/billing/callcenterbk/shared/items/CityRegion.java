package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

@Deprecated
public class CityRegion implements Serializable {

	private static final long serialVersionUID = -7349948363380325805L;

	private Long city_region_id;
	private Long town_id;
	private String town_district_name;
	private String city_region_name_eng;
	private Long deleted;

	public Long getCity_region_id() {
		return city_region_id;
	}

	public void setCity_region_id(Long city_region_id) {
		this.city_region_id = city_region_id;
	}

	public Long getTown_id() {
		return town_id;
	}

	public void setTown_id(Long town_id) {
		this.town_id = town_id;
	}

	public String getTown_district_name() {
		return town_district_name;
	}

	public void setTown_district_name(String town_district_name) {
		this.town_district_name = town_district_name;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getCity_region_name_eng() {
		return city_region_name_eng;
	}

	public void setCity_region_name_eng(String city_region_name_eng) {
		this.city_region_name_eng = city_region_name_eng;
	}
}
