package com.info08.billing.callcenterbk.shared.entity.descriptions;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DESCRIPTIONS")
public class Description implements Serializable {

	private static final long serialVersionUID = 5321693077115104783L;

	@Id
	@Column(name = "DESCRIPTION_ID")
	private Long description_id;

	@Basic
	@Column(name = "DESCRIPTION", length = 400)
	private String description;

	@Basic
	@Column(name = "DESCRIPTION_TYPE_ID")
	private Long description_type_id;

	@Basic
	@Column(name = "PARRENT_DESCRIPTION_ID")
	private Long parrent_description_id;

	public Long getDescription_id() {
		return description_id;
	}

	public void setDescription_id(Long description_id) {
		this.description_id = description_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDescription_type_id() {
		return description_type_id;
	}

	public void setDescription_type_id(Long description_type_id) {
		this.description_type_id = description_type_id;
	}

	public Long getParrent_description_id() {
		return parrent_description_id;
	}

	public void setParrent_description_id(Long parrent_description_id) {
		this.parrent_description_id = parrent_description_id;
	}	
}
