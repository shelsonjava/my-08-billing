package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

@Deprecated
public class CityRegion implements Serializable {

	private static final long serialVersionUID = -7349948363380325805L;

	private Long city_region_id;
	private Long city_id;
	private String city_region_name_geo;
	private String city_region_name_eng;
	private Long deleted;

	public Long getCity_region_id() {
		return city_region_id;
	}

	public void setCity_region_id(Long city_region_id) {
		this.city_region_id = city_region_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getCity_region_name_geo() {
		return city_region_name_geo;
	}

	public void setCity_region_name_geo(String city_region_name_geo) {
		this.city_region_name_geo = city_region_name_geo;
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
