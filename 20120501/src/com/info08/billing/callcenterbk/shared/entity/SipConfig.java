package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;

public class SipConfig implements Serializable {
	private static final long serialVersionUID = -9204240834633290649L;

	private Long id;
	private Long config_id;
	private String config_name;
	private String config_value;
	private String param_1;
	private String param_2;
	private Long param_3;
	private Long param_4;
	private Long order_id;

	public SipConfig() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConfig_id() {
		return config_id;
	}

	public void setConfig_id(Long config_id) {
		this.config_id = config_id;
	}

	public String getConfig_name() {
		return config_name;
	}

	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}

	public String getConfig_value() {
		return config_value;
	}

	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}

	public String getParam_1() {
		return param_1;
	}

	public void setParam_1(String param_1) {
		this.param_1 = param_1;
	}

	public String getParam_2() {
		return param_2;
	}

	public void setParam_2(String param_2) {
		this.param_2 = param_2;
	}

	public Long getParam_3() {
		return param_3;
	}

	public void setParam_3(Long param_3) {
		this.param_3 = param_3;
	}

	public Long getParam_4() {
		return param_4;
	}

	public void setParam_4(Long param_4) {
		this.param_4 = param_4;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
}
