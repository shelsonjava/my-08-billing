package com.info08.billing.callcenterbk.shared.entity.control;

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
 * The persistent class for the LOG_PERSONELL_NOTES database table.
 * 
 */
@NamedQueries({
	@NamedQuery(
				name="LogPersonellNote.getByOperator",
				query="select e from LogPersonellNote e where e.ym = to_char(sysdate, 'YYmm') and trunc(e.rec_date) >= trunc(sysdate - 10) and e.visible_options = 0 and e.received = 0 and e.user_name = :userName ")
})
@Entity
@Table(name="LOG_PERSONELL_NOTES",schema="PAATA")
public class LogPersonellNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_PERSONELL_NOTES_NOTEID_GENERATOR", sequenceName="NOTE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_PERSONELL_NOTES_NOTEID_GENERATOR")
	@Column(name="NOTE_ID")
	private Long note_id;

    @Basic
	@Column(name="CALL_DATE")
	private Timestamp call_date;

    @Basic
    @Column(name="NOTE")
	private String note;

    @Basic
    @Column(name="PARTICULAR")
	private Long particular;

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
    @Column(name="RECEIVED")
	private Long received;

    @Basic
	@Column(name="SESSION_ID")
	private String session_id;

    @Basic
	@Column(name="USER_NAME")
	private String user_name;

    @Basic
	@Column(name="VISIBLE_OPTIONS")
	private Long visible_options;

    @Basic
    @Column(name="YM")
	private Long ym;
    
    @Basic
	@Column(name="UPD_DATE")
	private Timestamp upd_date;
    
    @Basic
	@Column(name="UPD_USER")
	private String upd_user;
    

    public LogPersonellNote() {
    }

	public Long getNote_id() {
		return note_id;
	}

	public void setNote_id(Long note_id) {
		this.note_id = note_id;
	}

	public Timestamp getCall_date() {
		return call_date;
	}

	public void setCall_date(Timestamp call_date) {
		this.call_date = call_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getParticular() {
		return particular;
	}

	public void setParticular(Long particular) {
		this.particular = particular;
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

	public Long getReceived() {
		return received;
	}

	public void setReceived(Long received) {
		this.received = received;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getVisible_options() {
		return visible_options;
	}

	public void setVisible_options(Long visible_options) {
		this.visible_options = visible_options;
	}

	public Long getYm() {
		return ym;
	}

	public void setYm(Long ym) {
		this.ym = ym;
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