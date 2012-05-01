package com.info08.billing.callcenterbk.shared.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ 
		@NamedQuery(
				name = "Service.selectAllServices", 
				query = "select e from Service e where e.deleted = 0 order by e.serviceId ")
})
@Entity
@Table(name = "services", schema = "PAATA")
public class Service implements Serializable {

	private static final long serialVersionUID = -1569784555696331538L;

	@Id
	@Column(name = "SERVICE_ID")
	private long serviceId;
	
	@Column(name = "SERVICE_NAME_GEO", length=255)
	private String serviceNameGeo;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "PRICE_SPECIAL")
	private Double priceSpecial;
	
	@Column(name = "PRICE_DISCOUNT")
	private Double priceDiscount;
	
	@Column(name = "SERVICE_HIDE", nullable = false)
	private Long serviceHide;
	
	@Column(name = "SERVCICE_TYPE_ID")
	private Long serviceTypeId;
	
	@Column(name = "REC_DATE")
	private Date recDate;
	
	@Column(name = "REC_USER", length = 30)
	private String recUser;
	
	@Column(name = "SERVICE_NAME_ENG", length = 255)
	private String serviceNameEng;
	
	@Column(name = "DELETED", nullable = false)
	private Long deleted;
	
	@Column(name = "ADDRESS_SEARCH")
	private Long addressSearch;
	
	@Column(name = "UPD_USER", length = 30)
	private String updUser;

	public Service() {
	}

	public Service(long serviceId) {
		this.serviceId = serviceId;
	}

	public Service(long serviceId, String serviceNameGeo, Double price,
			Double priceSpecial, Double priceDiscount, Long serviceHide,
			Long serviceTypeId, Date recDate, String recUser,
			String serviceNameEng, Long deleted, Long addressSearch,
			String updUser) {
		this.serviceId = serviceId;
		this.serviceNameGeo = serviceNameGeo;
		this.price = price;
		this.priceSpecial = priceSpecial;
		this.priceDiscount = priceDiscount;
		this.serviceHide = serviceHide;
		this.serviceTypeId = serviceTypeId;
		this.recDate = recDate;
		this.recUser = recUser;
		this.serviceNameEng = serviceNameEng;
		this.deleted = deleted;
		this.addressSearch = addressSearch;
		this.updUser = updUser;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceNameGeo() {
		return serviceNameGeo;
	}

	public void setServiceNameGeo(String serviceNameGeo) {
		this.serviceNameGeo = serviceNameGeo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPriceSpecial() {
		return priceSpecial;
	}

	public void setPriceSpecial(Double priceSpecial) {
		this.priceSpecial = priceSpecial;
	}

	public Double getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(Double priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	public Long getServiceHide() {
		return serviceHide;
	}

	public void setServiceHide(Long serviceHide) {
		this.serviceHide = serviceHide;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}

	public String getServiceNameEng() {
		return serviceNameEng;
	}

	public void setServiceNameEng(String serviceNameEng) {
		this.serviceNameEng = serviceNameEng;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getAddressSearch() {
		return addressSearch;
	}

	public void setAddressSearch(Long addressSearch) {
		this.addressSearch = addressSearch;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

}
