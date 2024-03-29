package com.info08.billing.callcenter.shared.common;

public interface Constants {
	public static final String discSMSDefText = "Tqveni informacia garkvevis procesSia, miiRebT pasuxs mogvianebiT. sainformacio centri 08";
	public static final String sessionFilesUrl = "http://192.168.1.254:8083/cgi-bin/find.pl";
	public static final String hrImagesDirUrl = "http://192.168.1.254:8083/cgi-bin/upload.pl";
	public static final String hrImagesDirLink = "http://192.168.1.254:8083/hrimages/";
	public static final Integer defDiscTypeId = 2;
	public static final Integer defCityTbilisiId = 2208;
	public static final Integer defCountryGeorgiaId = 194;
	public static final int[] dayArray = { 0, 1, 2, 4, 8, 16, 32, 64 };
	public static final String callStart = "start";
	public static final int callTypeAbonent = 0;
	public static final int callTypeOrganization = 3;
	public static final int callTypeMobile = -3;
	public static final int callTypeMobitel = -7;
	public static final int callTypeNoncharge = -8;
	public static final int callTypeVirtualDirect = 11;
	public static final int callTypeVirtualNonDirect = 12;

	public static final int serviceOrganization = 3;
	public static final int serviceAddressInfo = 4;
	public static final int serviceAbonentInfo = 7;
	public static final int serviceCodesInfo = 5;
	public static final int serviceTransportInfo = 13;
	public static final int serviceCurrDateTimeInfo = 44;
	public static final int serviceCityDistInfo = 31;
	public static final int serviceGeoIndInfo = 35;
	public static final int serviceWebSiteInfo = 63;
	public static final int serviceNonStandartInfo = 32;
	public static final int serviceOrthCalendarInfo = 22;
	public static final int serviceCalendarInfo = 42;
	public static final int serviceFindByNumberInfo = 46;
	public static final int serviceWeatherInfo = 12;
	public static final int serviceSportInfo = 24;
	public static final int servicePosterInfo = 10;
	public static final int serviceInCityTransportInfo = 26;
	public static final int serviceCurrencyInfo = 11;
	
	public static final int criticalNumberIgnore = -999999999;
}
