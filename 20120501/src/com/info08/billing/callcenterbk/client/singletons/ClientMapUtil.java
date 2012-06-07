package com.info08.billing.callcenterbk.client.singletons;

import java.util.LinkedHashMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;

public class ClientMapUtil {
	private static ClientMapUtil instance;

	public static ClientMapUtil getInstance() {
		if (instance == null) {
			instance = new ClientMapUtil();
		}
		return instance;
	}

	private LinkedHashMap<String, String> mapOpClose;
	private LinkedHashMap<String, String> mapParall;
	private LinkedHashMap<String, String> mapStatuses;
	private LinkedHashMap<String, String> mapStates;
	private LinkedHashMap<String, String> mapTypes;
	private LinkedHashMap<String, String> addrMapOpClose;
	private LinkedHashMap<String, String> mapGender;
	private LinkedHashMap<String, String> mapFamilyStatus;
	private LinkedHashMap<String, String> isCapital;
	private LinkedHashMap<String, String> streetRecTypes;
	private LinkedHashMap<String, String> raionCentTypes;
	private LinkedHashMap<String, String> transpTypeInt;
	private LinkedHashMap<String, String> transpRoundType;
	private LinkedHashMap<String, String> transpTypeCustom;
	private LinkedHashMap<String, String> transpTypeCustom1;
	private LinkedHashMap<String, String> routeDirTypes;
	private LinkedHashMap<String, String> statuses;
	private LinkedHashMap<String, String> reservations;
	private LinkedHashMap<String, String> secCalendarTypes;
	private LinkedHashMap<String, String> calendarStates;
	private LinkedHashMap<String, String> orgStatuses;
	private LinkedHashMap<String, String> orgNoteCrits;
	private LinkedHashMap<String, String> indexSearchTypes;
	private LinkedHashMap<String, String> smsStatuses;
	private LinkedHashMap<String, String> callStatChargeTypes;
	private LinkedHashMap<String, String> callStatChargeTypes1;
	private LinkedHashMap<String, String> weekDays;
	private LinkedHashMap<String, String> contractorTypes;
	private LinkedHashMap<String, String> contractorTypes1;
	private LinkedHashMap<String, String> billingCompIndTypes;
	private LinkedHashMap<String, String> contractorPriceTypes;
	private LinkedHashMap<String, String> newsTypes;
	private LinkedHashMap<String, String> searchNewsTypes;
	private LinkedHashMap<String, String> limitTypes;
	private LinkedHashMap<String, String> hasCalculations;
	private LinkedHashMap<String, String> hasCalculations1;
	private LinkedHashMap<String, String> billingCompIndTypes1;
	private LinkedHashMap<String, String> callTypes;
	private LinkedHashMap<String, String> allYesAndNo;
	private LinkedHashMap<String, String> yesAndNo;

	protected ClientMapUtil() {
		mapOpClose = new LinkedHashMap<String, String>();
		mapParall = new LinkedHashMap<String, String>();
		mapStatuses = new LinkedHashMap<String, String>();
		mapStates = new LinkedHashMap<String, String>();
		mapTypes = new LinkedHashMap<String, String>();
		addrMapOpClose = new LinkedHashMap<String, String>();
		mapGender = new LinkedHashMap<String, String>();
		mapFamilyStatus = new LinkedHashMap<String, String>();
		isCapital = new LinkedHashMap<String, String>();
		streetRecTypes = new LinkedHashMap<String, String>();
		raionCentTypes = new LinkedHashMap<String, String>();
		transpTypeInt = new LinkedHashMap<String, String>();
		transpRoundType = new LinkedHashMap<String, String>();
		transpTypeCustom = new LinkedHashMap<String, String>();
		transpTypeCustom1 = new LinkedHashMap<String, String>();
		routeDirTypes = new LinkedHashMap<String, String>();
		statuses = new LinkedHashMap<String, String>();
		reservations = new LinkedHashMap<String, String>();
		secCalendarTypes = new LinkedHashMap<String, String>();
		calendarStates = new LinkedHashMap<String, String>();
		orgStatuses = new LinkedHashMap<String, String>();
		orgNoteCrits = new LinkedHashMap<String, String>();
		indexSearchTypes = new LinkedHashMap<String, String>();
		smsStatuses = new LinkedHashMap<String, String>();
		callStatChargeTypes = new LinkedHashMap<String, String>();
		callStatChargeTypes1 = new LinkedHashMap<String, String>();
		weekDays = new LinkedHashMap<String, String>();
		contractorTypes = new LinkedHashMap<String, String>();
		contractorTypes1 = new LinkedHashMap<String, String>();
		billingCompIndTypes = new LinkedHashMap<String, String>();
		contractorPriceTypes = new LinkedHashMap<String, String>();
		newsTypes = new LinkedHashMap<String, String>();
		searchNewsTypes = new LinkedHashMap<String, String>();
		limitTypes = new LinkedHashMap<String, String>();
		hasCalculations = new LinkedHashMap<String, String>();
		hasCalculations1 = new LinkedHashMap<String, String>();
		billingCompIndTypes1 = new LinkedHashMap<String, String>();
		callTypes = new LinkedHashMap<String, String>();
		allYesAndNo = new LinkedHashMap<String, String>();
		yesAndNo = new LinkedHashMap<String, String>();

		mapOpClose.put("0", "ღია");
		mapOpClose.put("1", "დაფარულია");

		mapParall.put("0", "ჩვეულებრივი");
		mapParall.put("1", "პარალელური");

		mapStatuses.put("53102", "პირდაპირი");
		mapStatuses.put("53100", "აღებულია იჯარით");
		mapStatuses.put("53101", "გაცემულია იჯარით");

		mapStates.put("52100", "სწორი");
		mapStates.put("52103", "არ პასუხობს");
		mapStates.put("52101", "გათიშული");
		mapStates.put("52102", "მოხსნილი");

		mapTypes.put("54100", "კაბელიანი");
		mapTypes.put("54101", "უკაბელო");

		addrMapOpClose.put("0", "ღია");
		addrMapOpClose.put("1", "დაფარულია");

		mapGender.put("0", "ქალი");
		mapGender.put("1", "მამაკაცი");

		mapFamilyStatus.put("1", "დასაოჯახებელი");
		mapFamilyStatus.put("2", "დაოჯახებული");
		mapFamilyStatus.put("3", "განქორწინებული");

		isCapital.put("0", "ჩვეულებრივი");
		isCapital.put("1", "დედაქალაქი");

		streetRecTypes.put("0", "ძველი");
		streetRecTypes.put("1", "ახალი");

		raionCentTypes.put("0", "სოფელი");
		raionCentTypes.put("1", "ქალაქი");		
		raionCentTypes.put("-1", "რაიონული ცენტრი");

		transpTypeInt.put("0", "საქალაქო");
		transpTypeInt.put("1", "საქალაქთაშორისო");
		transpTypeInt.put("2", "საერთაშორისო");

		transpRoundType.put("1", "ჩვეულებრივი");
		transpRoundType.put("2", "წრიული");

		transpTypeCustom.put("1000001", "თბილისის მიკრო ავტობუსების განრიგი");
		transpTypeCustom.put("1000002", "თბილისის ავტობუსების განრიგი");

		transpTypeCustom1.put("1000003", "სარკინიგზო განრიგი");
		transpTypeCustom1.put("1000005", "საავიაციო განრიგი");
		transpTypeCustom1.put("1000004", "საქალაქთაშორისო განრიგი");

		routeDirTypes.put("1", "წინ");
		routeDirTypes.put("2", "უკან");

		statuses.put("0", "აქტიური");
		statuses.put("1", "წაშლილი");

		reservations.put("0", "არ იჯავშნება");
		reservations.put("1", "იჯავშნება");

		secCalendarTypes.put("1", "ახალი მთვარე");
		secCalendarTypes.put("2", "პირველი მეოთხედი");
		secCalendarTypes.put("3", "მეორე ფაზა");
		secCalendarTypes.put("4", "მესამე ფაზა");
		secCalendarTypes.put("5", "უკანასკნელი მეოთხედი");
		secCalendarTypes.put("6", "სავსე მთვარე");

		calendarStates.put("1", "უქმე");
		calendarStates.put("2", "სამუშაო");

		orgStatuses.put("0", "ფუნქციონირებს");
		orgStatuses.put("1", "არ ფუნქციონირებს");
		orgStatuses.put("2", "გაუქმებული");
		orgStatuses.put("3", "ბანკის ორგანიზაცია");

		orgNoteCrits.put("0", "ჩვეულებრივი");
		orgNoteCrits.put("-1", "გაწითლებული");
		
		indexSearchTypes.put("1", CallCenterBK.constants.findByCountry());
		indexSearchTypes.put("2", CallCenterBK.constants.findByTown());
		indexSearchTypes.put("3", CallCenterBK.constants.findByOperator());

		smsStatuses.put("2", CallCenterBK.constants.all());
		smsStatuses.put("1", CallCenterBK.constants.smsDelivered());
		smsStatuses.put("-1", CallCenterBK.constants.smsNotDelivered());

		callStatChargeTypes.put("0", CallCenterBK.constants.all());
		callStatChargeTypes.put("1", CallCenterBK.constants.abonent());
		callStatChargeTypes.put("2", CallCenterBK.constants.organization());

		callStatChargeTypes1.put("0", CallCenterBK.constants.countity());
		callStatChargeTypes1.put("1", CallCenterBK.constants.percentage());

		weekDays.put("0", CallCenterBK.constants.wdayEvery());
		weekDays.put("1", CallCenterBK.constants.wdayMonday());
		weekDays.put("2", CallCenterBK.constants.wdayTuesday());
		weekDays.put("4", CallCenterBK.constants.wdayWednesday());
		weekDays.put("8", CallCenterBK.constants.wdayThursday());
		weekDays.put("16", CallCenterBK.constants.wdayFriday());
		weekDays.put("32", CallCenterBK.constants.wdaySaturday());
		weekDays.put("64", CallCenterBK.constants.wdaySunday());

		contractorTypes.put("-1", CallCenterBK.constants.all());
		contractorTypes.put("0", CallCenterBK.constants.budget());
		contractorTypes.put("1", CallCenterBK.constants.commercial());

		contractorTypes1.put("0", CallCenterBK.constants.budget());
		contractorTypes1.put("1", CallCenterBK.constants.commercial());

		billingCompIndTypes.put("0", CallCenterBK.constants.partial());
		billingCompIndTypes.put("1", CallCenterBK.constants.full());

		contractorPriceTypes.put("-1", CallCenterBK.constants.all());
		contractorPriceTypes.put("0", CallCenterBK.constants.simple());
		contractorPriceTypes.put("1", CallCenterBK.constants.advanced());

		newsTypes.put("0", CallCenterBK.constants.newsTypeNormal());
		newsTypes.put("1", CallCenterBK.constants.newsTypeMain());

		searchNewsTypes.put("-1", " ");
		searchNewsTypes.put("0", CallCenterBK.constants.newsTypeNormal());
		searchNewsTypes.put("1", CallCenterBK.constants.newsTypeMain());

		limitTypes.put("-1", CallCenterBK.constants.all());
		limitTypes.put("0", CallCenterBK.constants.whithLimit());
		limitTypes.put("1", CallCenterBK.constants.whithOutLimit());

		hasCalculations.put("-1", CallCenterBK.constants.all());
		hasCalculations.put("1", CallCenterBK.constants.yes());
		hasCalculations.put("0", CallCenterBK.constants.noInGeo());

		hasCalculations1.put("1", CallCenterBK.constants.yes());
		hasCalculations1.put("0", CallCenterBK.constants.noInGeo());

		billingCompIndTypes1.put("0", CallCenterBK.constants.byCharges());
		billingCompIndTypes1.put("1", CallCenterBK.constants.byCalls());

		callTypes.put("11", CallCenterBK.constants.direct());
		callTypes.put("12", CallCenterBK.constants.nonDirect());

		allYesAndNo.put("-1", CallCenterBK.constants.all());
		allYesAndNo.put("0", CallCenterBK.constants.noInGeo());
		allYesAndNo.put("1", CallCenterBK.constants.yes());

		yesAndNo.put("0", CallCenterBK.constants.noInGeo());
		yesAndNo.put("1", CallCenterBK.constants.yes());
	}

	public LinkedHashMap<String, String> getCallTypes() {
		return callTypes;
	}

	public LinkedHashMap<String, String> getBillingCompIndTypes1() {
		return billingCompIndTypes1;
	}

	public LinkedHashMap<String, String> getHasCalculations1() {
		return hasCalculations1;
	}

	public LinkedHashMap<String, String> getHasCalculations() {
		return hasCalculations;
	}

	public LinkedHashMap<String, String> getLimitTypes() {
		return limitTypes;
	}

	public LinkedHashMap<String, String> getContractorPriceTypes() {
		return contractorPriceTypes;
	}

	public LinkedHashMap<String, String> getNewsTypes() {
		return newsTypes;
	}

	public LinkedHashMap<String, String> getSearchNewsTypes() {
		return searchNewsTypes;
	}

	public LinkedHashMap<String, String> getMapOpClose() {
		return mapOpClose;
	}

	public LinkedHashMap<String, String> getMapParall() {
		return mapParall;
	}

	public LinkedHashMap<String, String> getMapStatuses() {
		return mapStatuses;
	}

	public LinkedHashMap<String, String> getMapStates() {
		return mapStates;
	}

	public LinkedHashMap<String, String> getMapTypes() {
		return mapTypes;
	}

	public LinkedHashMap<String, String> getAddrMapOpClose() {
		return addrMapOpClose;
	}

	public LinkedHashMap<String, String> getMapGender() {
		return mapGender;
	}

	public LinkedHashMap<String, String> getMapFamilyStatus() {
		return mapFamilyStatus;
	}

	public LinkedHashMap<String, String> getIsCapital() {
		return isCapital;
	}

	public LinkedHashMap<String, String> getStreetRecTypes() {
		return streetRecTypes;
	}

	public LinkedHashMap<String, String> getRaionCentTypes() {
		return raionCentTypes;
	}

	public LinkedHashMap<String, String> getTranspTypeInt() {
		return transpTypeInt;
	}

	public LinkedHashMap<String, String> getTranspRoundType() {
		return transpRoundType;
	}

	public LinkedHashMap<String, String> getTranspTypeCustom() {
		return transpTypeCustom;
	}

	public LinkedHashMap<String, String> getRouteDirTypes() {
		return routeDirTypes;
	}

	public LinkedHashMap<String, String> getStatuses() {
		return statuses;
	}

	public LinkedHashMap<String, String> getTranspTypeCustom1() {
		return transpTypeCustom1;
	}

	public LinkedHashMap<String, String> getReservations() {
		return reservations;
	}

	public LinkedHashMap<String, String> getSecCalendarTypes() {
		return secCalendarTypes;
	}

	public LinkedHashMap<String, String> getCalendarStates() {
		return calendarStates;
	}

	public LinkedHashMap<String, String> getOrgStatuses() {
		return orgStatuses;
	}

	public LinkedHashMap<String, String> getOrgNoteCrits() {
		return orgNoteCrits;
	}

	public LinkedHashMap<String, String> getIndexSearchTypes() {
		return indexSearchTypes;
	}

	public LinkedHashMap<String, String> getSmsStatuses() {
		return smsStatuses;
	}

	public LinkedHashMap<String, String> getCallStatChargeTypes() {
		return callStatChargeTypes;
	}

	public LinkedHashMap<String, String> getCallStatChargeTypes1() {
		return callStatChargeTypes1;
	}

	public LinkedHashMap<String, String> getWeekDays() {
		return weekDays;
	}

	public LinkedHashMap<String, String> getContractorTypes() {
		return contractorTypes;
	}

	public LinkedHashMap<String, String> getContractorTypes1() {
		return contractorTypes1;
	}

	public LinkedHashMap<String, String> getBillingCompIndTypes() {
		return billingCompIndTypes;
	}
	
	public LinkedHashMap<String, String> getAllYesAndNo() {
		return allYesAndNo;
	}
	
	public LinkedHashMap<String, String> getYesAndNo() {
		return yesAndNo;
	}
	
}
