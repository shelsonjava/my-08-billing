package com.info08.billing.callcenterbk.shared.entity.admin;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LANDLINE_INDEXES database table.
 * 
 */
@NamedQueries({ @NamedQuery(name = "LandlineIndexes.getAll", query = "select e from LandlineIndexes e") })
@Entity
@Table(name = "LANDLINE_INDEXES", schema = "ccare")
public class LandlineIndexes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_LANDLINE_INDEXES_GENERATOR", sequenceName = "SEQ_LANDLINE_INDEXES")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LANDLINE_INDEXES_GENERATOR")
	private Long landline_id;

	@Basic
	@Column(name = "LANDLINE_INDEX")
	private String landline_index;

	public LandlineIndexes() {
	}

	public Long getLandline_id() {
		return landline_id;
	}

	public void setLandline_id(Long landline_id) {
		this.landline_id = landline_id;
	}

	public String getLandline_index() {
		return landline_index;
	}

	public void setLandline_index(String landline_index) {
		this.landline_index = landline_index;
	}

}