package com.info08.billing.callcenter.shared.entity.callcenter;

import java.io.Serializable;
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


/**
 * The persistent class for the MY_MOBBASE database table.
 * 
 */

@NamedQueries({
	@NamedQuery(
				name="MyMobbase.getByPhone",
				query="select e from MyMobbase e where e.phone = :phone ")
})

@Entity
@Table(name="MY_MOBBASE",schema="INFO")
public class MyMobbase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MY_MOBBASE_ID_GENERATOR", sequenceName="MY_MOB_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MY_MOBBASE_ID_GENERATOR")
	private Long id;

	@Basic
	@Column(name="HIDDEN")
	private Long hidden;

	@Basic
	@Column(name="NM")
	private String nm;

	@Basic
	@Column(name="PHONE")
	private String phone;
	
	@Basic
    @Column(name="REC_DATE")
	private Timestamp rec_date;

	@Basic
	@Column(name="REC_USER")
	private String rec_user;

	@Basic
	@Column(name="SEX")
	private Long sex;
	
	@Basic
    @Column(name="UPD_DATE")
	private Timestamp upd_date;

	@Basic
	@Column(name="UPD_USER")
	private String upd_user;

    public MyMobbase() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHidden() {
		return hidden;
	}

	public void setHidden(Long hidden) {
		this.hidden = hidden;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_user() {
		return upd_user;
	}

	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}
}