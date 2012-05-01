package com.info08.billing.callcenterbk.shared.entity;

import java.sql.Timestamp;

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
		@NamedQuery(name = "WorldRegions.getAllWorldRegions", query = "select e from WorldRegions e where e.deleted = 0 order by e.world_region_id")

})
@Entity
@Table(name = "world_regions", schema = "info")
public class WorldRegions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WORLD_REGION_ID")
	@SequenceGenerator(name = "seq_world_region", sequenceName = "world_region_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_world_region")
	private Long world_region_id;

	@Basic
	@Column(name = "WORLD_REGION_NAME_GEO")
	private String world_region_name_geo;

	@Basic
	@Column(name = "WORLD_REGION_NAME_ENG")
	private String world_region_name_eng;
	
	@Basic
	@Column(name = "REC_DATE")
	private Timestamp rec_date;
	
	@Basic
	@Column(name = "REC_USER")
	private String rec_user;
	
	@Basic
	@Column(name = "DELETED")
	private Long deleted;	
	
	public WorldRegions() {
	}

	public Long getWorld_region_id() {
		return world_region_id;
	}

	public void setWorld_region_id(Long world_region_id) {
		this.world_region_id = world_region_id;
	}

	public String getWorld_region_name_geo() {
		return world_region_name_geo;
	}

	public void setWorld_region_name_geo(String world_region_name_geo) {
		this.world_region_name_geo = world_region_name_geo;
	}

	public String getWorld_region_name_eng() {
		return world_region_name_eng;
	}

	public void setWorld_region_name_eng(String world_region_name_eng) {
		this.world_region_name_eng = world_region_name_eng;
	}

	public Timestamp getRec_date() {
		return rec_date;
	}

	public void setRec_date(Timestamp rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_user() {
		return rec_user;
	}

	public void setRec_user(String rec_user) {
		this.rec_user = rec_user;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}
}
