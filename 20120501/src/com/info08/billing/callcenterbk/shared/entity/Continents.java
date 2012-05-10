package com.info08.billing.callcenterbk.shared.entity;

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

@NamedQueries({
		@NamedQuery(name = "Continents.getAllContinents", query = "select e from Continents e order by e.id")

})
@Entity
@Table(name = "continents", schema = "ccare")
public class Continents implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "seq_cont_id", sequenceName = "seq_cont_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cont_id")
	private Long id;

	@Basic
	@Column(name = "NAME_DESCR")
	private String name_descr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_descr() {
		return name_descr;
	}

	public void setName_descr(String name_descr) {
		this.name_descr = name_descr;
	}
}
