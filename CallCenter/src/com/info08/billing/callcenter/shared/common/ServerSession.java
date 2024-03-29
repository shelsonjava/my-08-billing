package com.info08.billing.callcenter.shared.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.info08.billing.callcenter.shared.entity.Person;
import com.info08.billing.callcenter.shared.entity.callcenter.MyMobbase;

public class ServerSession implements Serializable {

	private static final long serialVersionUID = -1814238115292961188L;
	private String sessionId;
	private String parentId;
	private String phone;
	private String machineIP;
	private String userName;
	private String phoneDescription;
	private String abonentName;
	private boolean webSession = true;
	private int chcount = 0;
	private Long sex = -1L;
	private Double cbd = 0.0;
	private String debts = "";
	private int yearMonth;
	private Person user;
	private int callType;
	private ServerSession prevSession;
	private MyMobbase myMobbase;
	private boolean phoneIsMobile;
	private Long main_id;
	private String specialAlertMessage;
	private Long unreadPersNotesCount;
	private String callCenterReqMsg;
	
	private boolean isContractorPhone;
	private Long contractorId;
	private Timestamp contractorStartDate;
	private Timestamp contractorEndDate;
	private Long contractorCriticalNumber;
	private Long contractorIsBudget;
	private Long contractorPriceType;
	private Double contractorCallPrice;
	private Long contractorCallCnt;
	private Long contractorBlock;
	private Long contractorMainId;
	private Long contractorMainDetailId;
	private boolean contractorNeedBlock;

	@SuppressWarnings("deprecation")
	public ServerSession() {
		Date date = new Date();
		int month = date.getMonth() + 1;
		int year = date.getYear() + 1900;
		String syear = Integer.toString(year);
		String str = syear.substring(2) + ""
				+ (month < 10 ? "0" + month : month);
		yearMonth = Integer.parseInt(str);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMachineIP() {
		return machineIP;
	}

	public void setMachineIP(String machineIP) {
		this.machineIP = machineIP;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneDescription() {
		return phoneDescription;
	}

	public void setPhoneDescription(String phoneDescription) {
		this.phoneDescription = phoneDescription;
	}

	public String getAbonentName() {
		return abonentName;
	}

	public void setAbonentName(String abonentName) {
		this.abonentName = abonentName;
	}

	public boolean isWebSession() {
		return webSession;
	}

	public void setWebSession(boolean webSession) {
		this.webSession = webSession;
	}

	public int getChcount() {
		return chcount;
	}

	public void setChcount(int chcount) {
		this.chcount = chcount;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Double getCbd() {
		return cbd;
	}

	public void setCbd(Double cbd) {
		this.cbd = cbd;
	}

	public String getDebts() {
		return debts;
	}

	public void setDebts(String debts) {
		this.debts = debts;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public int getCallType() {
		return callType;
	}

	public void setCallType(int callType) {
		this.callType = callType;
	}

	public ServerSession getPrevSession() {
		return prevSession;
	}

	public void setPrevSession(ServerSession prevSession) {
		this.prevSession = prevSession;
	}

	public MyMobbase getMyMobbase() {
		return myMobbase;
	}

	public void setMyMobbase(MyMobbase myMobbase) {
		this.myMobbase = myMobbase;
	}

	public boolean isPhoneIsMobile() {
		return phoneIsMobile;
	}

	public void setPhoneIsMobile(boolean phoneIsMobile) {
		this.phoneIsMobile = phoneIsMobile;
	}

	public Long getMain_id() {
		return main_id;
	}

	public void setMain_id(Long main_id) {
		this.main_id = main_id;
	}

	public String getSpecialAlertMessage() {
		return specialAlertMessage;
	}

	public void setSpecialAlertMessage(String specialAlertMessage) {
		this.specialAlertMessage = specialAlertMessage;
	}

	public Long getUnreadPersNotesCount() {
		return unreadPersNotesCount;
	}

	public void setUnreadPersNotesCount(Long unreadPersNotesCount) {
		this.unreadPersNotesCount = unreadPersNotesCount;
	}

	public String getCallCenterReqMsg() {
		return callCenterReqMsg;
	}

	public void setCallCenterReqMsg(String callCenterReqMsg) {
		this.callCenterReqMsg = callCenterReqMsg;
	}

	public boolean isContractorPhone() {
		return isContractorPhone;
	}

	public void setContractorPhone(boolean isContractorPhone) {
		this.isContractorPhone = isContractorPhone;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public Timestamp getContractorStartDate() {
		return contractorStartDate;
	}

	public void setContractorStartDate(Timestamp contractorStartDate) {
		this.contractorStartDate = contractorStartDate;
	}

	public Timestamp getContractorEndDate() {
		return contractorEndDate;
	}

	public void setContractorEndDate(Timestamp contractorEndDate) {
		this.contractorEndDate = contractorEndDate;
	}

	public Long getContractorCriticalNumber() {
		return contractorCriticalNumber;
	}

	public void setContractorCriticalNumber(Long contractorCriticalNumber) {
		this.contractorCriticalNumber = contractorCriticalNumber;
	}

	public Long getContractorIsBudget() {
		return contractorIsBudget;
	}

	public void setContractorIsBudget(Long contractorIsBudget) {
		this.contractorIsBudget = contractorIsBudget;
	}

	public Long getContractorPriceType() {
		return contractorPriceType;
	}

	public void setContractorPriceType(Long contractorPriceType) {
		this.contractorPriceType = contractorPriceType;
	}

	public Double getContractorCallPrice() {
		return contractorCallPrice;
	}

	public void setContractorCallPrice(Double contractorCallPrice) {
		this.contractorCallPrice = contractorCallPrice;
	}

	public Long getContractorCallCnt() {
		return contractorCallCnt;
	}

	public void setContractorCallCnt(Long contractorCallCnt) {
		this.contractorCallCnt = contractorCallCnt;
	}

	public Long getContractorBlock() {
		return contractorBlock;
	}

	public void setContractorBlock(Long contractorBlock) {
		this.contractorBlock = contractorBlock;
	}

	public Long getContractorMainId() {
		return contractorMainId;
	}

	public void setContractorMainId(Long contractorMainId) {
		this.contractorMainId = contractorMainId;
	}

	public Long getContractorMainDetailId() {
		return contractorMainDetailId;
	}

	public void setContractorMainDetailId(Long contractorMainDetailId) {
		this.contractorMainDetailId = contractorMainDetailId;
	}

	public boolean isContractorNeedBlock() {
		return contractorNeedBlock;
	}

	public void setContractorNeedBlock(boolean contractorNeedBlock) {
		this.contractorNeedBlock = contractorNeedBlock;
	}
}
