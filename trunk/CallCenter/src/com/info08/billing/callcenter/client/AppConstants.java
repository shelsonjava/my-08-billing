package com.info08.billing.callcenter.client;

import com.google.gwt.i18n.client.Constants;

/**
 * <p>
 * ეს არის ინტერფეის რომელიც გამოიყენება იმისათვის რომ აღწეროს პროგრამაში
 * გამოყენებადი წარწერები კომპონენტებზე. ამ ინტერფეისის იმპლემენტაცია არის ფაილი
 * რომელშიც მოცემულია უკვე მნიშვნელობები უნიკოდის მხარდამჭერ კოდირებაში.
 * ცვლადების მნიშვნელობები შეგიძლიათ ნახოთ პროექტში :
 * com.info08.billing.callcenter.client.AppConstants.properties.
 * <p>
 * სისტემას დღეისათვის გააჩნია მხოლოდ ქართული ენის მხარდაჭერა თუმცა
 * განვითარებადია და შესაძლებელია სხვა ენების დამატება.
 * 
 * მიმდენარე კლასში გამოყენებულია ე.წ. Google Web Toolkit ბიბლიოთეკის
 * ინტერნაციონალიზაციის კლასი : com.google.gwt.i18n.client.Constants. რომლის
 * იმპლიმენტაციასაც აკეთებს მოცემული კლასი, კლასის სტრუქტურა არის მარტივი და
 * მისი ფუნქციათა სახელები არის მიმთითებლები მხოლოდ მათ მნიშვნელობაზე და არ
 * მოიცავა რაიმე ბიზნეს ლოგიკას. ბიბლიოთეკა შეგიძლიათ მოიპოვოთ :
 * <p>
 * <blockquote>
 * 
 * <pre>
 * <a href="http://code.google.com/webtoolkit">Google Web Toolkit</a>
 * </pre>
 * 
 * </blockquote>
 * 
 * @author პაატა ლომინაძე
 * @version 1.0.0.1
 * @since 1.0.0.1
 * 
 */
public interface AppConstants extends Constants {
	String save();
	String close();
	String addEntPoster();
	String modifyEntPoster();
	String entType();
	String entPlace();
	String entPosterName();
	String comment();
	String date();
	String choose();
	String time();
	String price();
	String smsComment();
	String timeCrit();
	String dtViewCrit();
	String chooseEntType();
	String chooseEntPlace();
	String enterEntPosterGeo();
	String manageCurrency();
	String country();
	String currencyName();
	String currencyAbbr();
	String find();
	String clear();
	String add();
	String modify();
	String disable();
	String enable();
	String order();
	String recDate();
	String recUser();
	String updDate();
	String updUser();
	String pleaseSelrecord();
	String pleaseSelOnerecord();
	String recordAlrDisabled();
	String askForDisable();
	String recordAlrEnabled();
	String askForEnable();
	String view();
	String addCurrencyTitle();
	String modifyCurrencyTitle();
	String chooseCountry();
	String enterCurrencyName();
	String enterCurrencyAbbr();
	String currOrderMustBeNum();
	String status();
	String rates();
	String rateCoeff();
	String rate();
	String marketRate();
	String salesMarketRate();
	String enterRateCoeff();
	String rateCoeffIsInteger();
	String rateIsFloat();
	String marketRateIsFloat();
	String saleMarketRateIsFloat();
	String menuEntType();
	String menuEntPlace();
	String menuEntPoster();
	String menuCurrency();
	String menuCalendar();
	String menuOrthCalendar();
	String miscActions();
	String actionsList();
	String moonPhase();
	String description();
	String type();
	String sunRise();
	String searchByDate();
	String addSecCalendar();
	String editSecCalendar();
	String chooseType();
	String chooseMoonPhase();
	String enterSunRise();
	String chruchCalEvent();
	String menuWebSiteGroup();
	String menuWebSites();
	String menuMiscCateg();
	String menuMisc();
	String service();
	String enterDescription();
	String addSiteGroupTitle();
	String editSiteGroupTitle();
	String street();
	String webSite();
	String group();
	String addSiteTitle();
	String editSiteTitle();
	String chooseSiteGroup();
	String enterWebSite();
	String addNonStandInofCatTitle();
	String editNonStandInofCatTitle();
	String addNonStandInfoTitle();
	String editNonStandInfoTitle();
	String discoveryActions();
	String menuDiscoveryStatuses();
	String menuDiscoveryTypes();
	String menuDiscovery();
	String addStatus();
	String editStatus();
	String addType();
	String editType();
	String enterType();
	String enterStatus();
	String phone();
	String phones();
	String contactPhone();
	String contactPerson();
	String message();
	String shortOp();
	String manageOrgs();
	String orgName();
	String orgNameEng();
	String orgAddress();
	String director();
	String identCode();
	String workinghours();
	String founded();
	String webaddress();
	String dayOffs();
	String workPersonCountity();
	String index();
	String identCodeNew();
	String orgInfo();
	String cantDropItself();
	String parrentAlrContChild();
	String warning();
	String askForNodeChange();
	String sort();
	String invalidTreeRecord();
	String childListIsEmpty();
	String sortOrgs();
	String sortUp();
	String sortDown();
	String plsRestRecBefStatChange();
	String eMail();
	String postIndex();
	String legalAddress();
	String orgCommonInfo();
	String orgAddressHeaderReal();
	String orgAddressHeaderLegal();
	String city();
	String cityRegion();
	String streetDescr();
	String activity();
	String extraPriority();
	String state();
	String streetIdx();
	String oldAddress();
	String openClose();
	String home();
	String block();
	String appartment();
	String addInfo();
	String thisOrgActAlreadyChoosen();
	String noteCrit();
	String orgPartnerBank();
	String callCenterActions();
	String organization();
	String abonentFind();
	String codes();
	String transport();
	String tbilisiTransport();
	String poster();
	String valute();
	String streetsFind();
	String regIndexes();
	String findByNumber();
	String distBetweenCities();
	String exactTime();
	String calendar();
	String orthCalendar();
	String sites();
	String sport();
	String wiki();
	String weather();
	String nonStandartInfo();
	String findOrg();
	String department();
	String orgFoundDateStart();
	String orgFoundDateEnd();
	String orgNameAndComment();
	String realAddress();
	String number();
	String identCodeAndNew();
	String username();
	String currentphone();
	String currentname();
	String prevphone();
	String prevname();
	String charge();
	String charges();
	String discovery();
	String maininfo();
	String departments();
	String orgSMSAddInfo();
	String orgSMSStreetInfo();
	String orgSMSStreetInfo1();
	String orgSMSDepInfo();
	String users();
	String mobOpIndexes();
	String fixedOpIndexes();
	String menuAdmin();
	String mobOperator();
	String mobOperatorIndex();
	String addMobOperatorPref();
	String editMobOperatorPref();
	String enterMobOperator();
	String enterMobOperatorPrefix();
	String mobOperPrefIs3Digit();
	String sendDiscovery();
	String discoveryType();
	String send();
	String notCallCenterUser();
	String chooseDiscoveryType();
	String enterSomeDiscoveryParam();
	String incorrDiscTypeSelected();
	String addMobSubsInfo();
	String viewChargeInfo();
	String phoneIsNotMobile();
	String addAbonentName();
	String editAbonentName();
	String abonentName();
	String sex();
	String male();
	String female();
	String plzSelectSex();
	String plzEnterAbonentName();
	String phoneIsMobile();
	String viewNumberCharges();
	String call();
	String invalidService();
	String plzSelectDepart();
	String legalStatuse();
	String common();
	String phoneShort();
	String cntShort();
	String amount();
	String phoneState();
	String moxs();
	String daf();
	String addressInfoTitle();
	String smsDidNotSent();
	String chargeDidNotMade();
	String chargeImpPhoneIsMobile();
	String findOrgEnterAnyParam();
	String findAbonent();
	String name();
	String lastName();
	String address();
	String enterAbonentSearchParam();
	String abonentInfoSMS();
	String codesTextConstant();
	String countryCode();
	String cityCode();
	String searchByCodeCountrCityAndOper();
	String findByCountry();
	String findByCity();
	String findByOperator();
	String operators();
	String code();
	String findCodes();
	String region();
	String gmtoff();
	String gmtoffwinter();
	String ctm();
	String enterIndexSearchParams();
	String enterIndexSearchParamCode();
	String countryCodeShort();
	String operator();	
	String smsCodesInfo();
	String smsCountryInfo();
	String smsCityInfo();
	String countryOperators();
	String smsCountryOpers();
	String orgGridComment();
	String transportType();
	String routeNumber();
	String days();
	String transportTextConstant();
	String cityFrom();
	String cityTo();
	String countryFrom();
	String countryTo();
	String stationFrom();
	String stationTo();
	String station();
	String outTime();
	String inTime();
	String transportCompShort();
	String transportTypeShort();
	String findTranspByRoute();
	String findTranspByCity();
	String findTranspByCountry();
	String invalidDate();
	String findTransport();
	String outPlace();
	String currentDate();
	String findInCityTransport();
	String smsExactTime();
	String errorInvalidSystemTime();
	String currTimeIs();
	String distance();
	String smsCityDistance();
	String regCountry();
	String cityCountry();
	String smsGeoInd();
	String information();
	String findSites();
	String smsWebSiteInfo();
	String category();
	String smsNonStandartInfo();
	String smsOrthCalendarInfo();
	String smsCalendarInfo();
	String pleaseEnterPhone();
	String dasaxeleba();
	String smsFindByNumber();
	String selRecordIsNotOrg();
	String invalidOrganization();
	String orgNotFound();
	String smsWeather();
	String serviceIsNotYetImpl();
	String smsSport();
	String findPoster();
	String entPosterCategory();
	String entPosterType();
	String entPosterNameAndComm();	
	String invalidEntPlaceSelected();
	String smsPosterCurrentRow();
	String smsPosterCurrentDay();
	String no();
	String routeDirDescr();
	String smsRouteDirForward();
	String smsRouteDirBackward();
	String smsInCityTranspInfo();
	String errorRoutePartsNotFound();
	String errorRoutePartsNotFound1();
	String oldStreetName();
	String indexes();
	String district();
	String plzEnterStreetNameOrIdx();
	String streetInfo();
	String smsStreetInfo();
	String currencyAbbrShort();
	String chooseCurrFrom();
	String chooseCurrTo();
	String calculate();
	String invalidAmount();
	String currFrom();
	String currTo();
	String ratesNotFound();
	String unitCalcAmmNat();
	String calcAmountNat();
	String unitCalcAmmBank();
	String calcAmountBank();
	String fromAndToCurrIsSame();
	String smsCurrencyRate();
	String smsCurrencyAmountRate();
	String smsCurrencyAmountRateBank();
	String smsCurrencyRateBank();
	String currSMSWarning();
	String hour();
	String news();
	String schedule();
	String operScheduleNotFound();
	String mainNews();
	String info();
	String descAndComment();
	String flag();
	String enterAmount();
	String chooseDifCountries();
	String chooseDifCities();
	String copy();
	String copyCalendar();
	String mainIerarch();
	String refresh();
	String orgAlreadyOpened();
	String partnerBank();
	String fullOrgAddress();
	String orgParrentsNotFound();
	String orgChildsNotFound();
	String contPhoneShort();
	String depsNotFoundMessage();
	String phoneMustBeNumeric();
	String byMonth();
	String discoverySent();
	String streetOldName();
	String newCode();
	String addressHide();
	String smsIndexInfo();
	String smsIndexInfo1();
	String oldCode();
	String callInfo();
	String resolve();
	String phoneStatus();
	String resolveDiscovery();
	String saveRecord();
	String listenRecord();
	String sms();
	String invalidSession();
	String invalidSessionDate();
	String sessionNotFound();
	String removeCharge();
	String manageCharges();
	String invalidRecord();
	String durationShort();
	String addCharge();
	String chargeCount();
	String chooseService();
	String enterChargeCount();
	String invalidChargeCount();
	String phoneIsMobileShort();
	String sendSMS();
	String invalidPhone();
	String smsTextIsEmpty();
	String chooseStatus();
	String menuDiscoveryHist();
	String autoText();
	String remarks();
	String remark();
	String persNotesIsEmpty();
	String sender();
	String sendDate();
	String receiver();
	String agree();
	String changeStatus();
	String discNotAuthUser();
	String parrent();
	String child();
	String supperOrg();
	String doesntHaveParrent();
	String supperOrgNotFound();
	String addChildOrg();
	String addNewOrg();
	String addChildOrg1();
	String addNewOrg1();
	String editOrg();
	String smsLog();
	String smsDelivered();
	String smsNotDelivered();
	String all();
	String callsStatByDay();
	String menuStat();
	String callsTotalBySrv();
	String callsCount();
	String callsTotalBySrvTitle();
	String abonent();
	String codesInfo();
	String gadartva();
	String ticketReserv();
	String cityTransport();
	String alarmClock();
	String currencyRate();
	String streetInfoSrv();
	String georgPostIndexes();
	String weatherShort();
	String webSiteSrv();
	String indexesInfo();
	String calendarShort();
	String nonStandartQuest();
	String hourDateAndOther();
	String aviaScheduler();
	String monumGard();
	String smsInfo();
	String callRules();
	String railScheduler();
	String selectAllServices();
	String selectOneService();
	String countity();
	String percentage();
	String weekDay();
	String percentShort();
	String autoRefresh();
	String billAllSrvs();
	String allInMoney();
	String moneyInGel();
	String wdayEvery();
	String wdayMonday();
	String wdayTuesday();
	String wdayWednesday();
	String wdayThursday();
	String wdayFriday();
	String wdaySaturday();
	String wdaySunday();
	String contractors();
	String orgNameFull();
	String startDate();
	String endDate();
	String budget();
	String commercial();
	String contractorType();
	String isBlockable();
	String smsWarnable();
	String contractorCritCallNumb();
	String addContractor();
	String editContractor();
	String normalPrice();
	String advancedPrice();
	String startCount();
	String endCount();
	String plzEnterStartPrice();
	String invalidStartPrice();
	String plzEnterEndPrice();
	String invalidEndPrice();
	String plzEnterPrice();
	String invalidPrice();
	String endPriceMustBeMoreThenStartPrice();
	String invalidPriceRange();
	String priceRangeMustBeMoreThen99();
	String advPriceListIsEmpty();
	String invalidPriceRange1();
	String priceListMustContZero();
	String plzSelectOrg();
	String plzSelectContractorType();
	String plzSelectContrStartDate();
	String plzSelectContrEndDate();
	String endDateMustBeAfterStartDate();
	String endDateMustBeAfterSysDate();
	String startDateMustNotBeAfterSysDate();
	String deleteConfirm();
	String blockPhone();
	String unBlockPhone();
	String phoneList();
	String onlyThisNumbers();
	String contrPhonesAll();
	String contrPhonesOnlyList();
	String contrPhonesExceptList();
	String contrPhonesOnlyListNoBlock();
	String contrPhonesExceptListNoBlock();
	String addContractorPhones();
	String plzEnterStartPhoneList();
	String phoneAlreadyExists();
	String phonesListIsEmpty();
	String shoose();
	String contractorCallCnt();
	String onlyNumberList();
	String addBlockList();
	String editBlockList();
	String addBlockListPhones();
	String contractorCharges();
	String blockPhoneList();
	String priceType();
	String reCalcCopntrRangePrice();
	String currentPrice();
	String noOrgOrDepSelected();
	String setCurrentPrice();
	String isNotAdvancePriceType();
	String currentPriceShort();
	String limit();
	String incorrectCriticalNumber();
	String telComps();
	String companyName();
	String addTelComp();
	String editTelComp();
	String companyNameFull();
	String startIndex();
	String endIndex();
	String partial();
	String full();
	String addTelCompInd();
	String editTelCompInd();
	String plzEnterStartIndex();
	String plzEnterEndIndex();
	String invalidStartIndex();
	String invalidEndIndex();
	String contractorsBilling();
	String yearMonth();
	String download();
	String plzEnterYearMonth();
	String invalidYearMonth();
	String contractorsBillingFull();
	String generateBillAgain();
	String telCombBillByDay();
	String telCombBillByMonth();
	String chooseDate();
	String plzEnterDate();
	String simple();
	String advanced();
	String whithLimit();
	String whithOutLimit();
	String statisticFull();
	String direct();
	String nonDirect();
	String government();
	String sum();
	String magti();
	String geocell();
	String beeline();
	String mobile();
	String avarage();
	String graph();
	String pleaseSearchData();
	String directEmail();
	String nonDirectEmail();
	String count();
	String graphAmount();
	String statisticFullDay();
	String statisticFullMonth();
	String ymStart();
	String ymEnd();
	String plzEnterStartMonthCorrectly();
	String plzEnterEndMonthCorrectly();
	String yearMonthOnly();
	String prevMonthPercent();
	String billing();
	String billingDetailed();
	String billing1();
	String billingDetailed1();
	String ourPercent();
	String plzEnterTelCompName();
	String plzEnterOurPercent();
	String invalidOurPercent();
}


