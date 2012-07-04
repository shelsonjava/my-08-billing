package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ 
		@NamedQuery(
				name = "Service.ServicePrice", 
				query = "select e from ServicePrice e order by e.service_price_id ")
})
@Entity
@Table(name = "SERVICE_PRICES")
public class ServicePrice implements Serializable {

	private static final long serialVersionUID = -1569784555696331538L;

	@Id
	@Column(name = "SERVICE_PRICE_ID")
	private Long service_price_id;
	
	@Basic
	@Column(name = "SERVICE_DESCRIPTION")
	private String service_description;
	
	@Basic
	@Column(name = "NORMAL_PRICE")
	private Double normal_price;
	
	@Basic
	@Column(name = "ORG_PRICE")
	private Double org_price;
	
	@Basic
	@Column(name = "DESCRIPTION_SQL")
	private String description_sql;
	
	@Basic
	@Column(name = "SQL_PARAM_COUNT")
	private Long sql_param_count;

	public ServicePrice() {
	}

	public Long getService_price_id() {
		return service_price_id;
	}

	public void setService_price_id(Long service_price_id) {
		this.service_price_id = service_price_id;
	}

	public String getService_description() {
		return service_description;
	}

	public void setService_description(String service_description) {
		this.service_description = service_description;
	}

	public Double getNormal_price() {
		return normal_price;
	}

	public void setNormal_price(Double normal_price) {
		this.normal_price = normal_price;
	}

	public Double getOrg_price() {
		return org_price;
	}

	public void setOrg_price(Double org_price) {
		this.org_price = org_price;
	}

	public String getDescription_sql() {
		return description_sql;
	}

	public void setDescription_sql(String description_sql) {
		this.description_sql = description_sql;
	}

	public Long getSql_param_count() {
		return sql_param_count;
	}

	public void setSql_param_count(Long sql_param_count) {
		this.sql_param_count = sql_param_count;
	}
}
