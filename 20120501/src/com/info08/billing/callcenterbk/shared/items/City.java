package com.info08.billing.callcenterbk.shared.items;

import java.io.Serializable;

public class City implements Serializable {

	private static final long serialVersionUID = -7349948363380325805L;
	private Integer town_id;
	private String town_name;
	private Integer capital_town;

	public Integer getTown_id() {
		return town_id;
	}

	public void setTown_id(Integer town_id) {
		this.town_id = town_id;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public Integer getCapital_town() {
		return capital_town;
	}

	public void setCapital_town(Integer capital_town) {
		this.capital_town = capital_town;
	}

}
