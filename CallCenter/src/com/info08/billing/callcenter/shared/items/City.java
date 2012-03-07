package com.info08.billing.callcenter.shared.items;

import java.io.Serializable;

public class City implements Serializable {

	private static final long serialVersionUID = -7349948363380325805L;
	private Integer city_id;
	private String city_name_geo;
	private Integer is_capital;

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public String getCity_name_geo() {
		return city_name_geo;
	}

	public void setCity_name_geo(String city_name_geo) {
		this.city_name_geo = city_name_geo;
	}

	public Integer getIs_capital() {
		return is_capital;
	}

	public void setIs_capital(Integer is_capital) {
		this.is_capital = is_capital;
	}

}
