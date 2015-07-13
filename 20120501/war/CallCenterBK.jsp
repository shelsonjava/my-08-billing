<%@ taglib uri="/WEB-INF/iscTaglib.xml" prefix="isomorphic"%>
<?xml version="1.0" encoding="UTF-8"?>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">	
	<meta name="gwt:property" content="locale=en">
	<meta name="author" content="Paata Lominadze" />
	<meta name="Description" content="Call Center (HTML5 SIP client using WebRTC framework) and Billing Application" />
	<meta name="Keywords" content="Call Center, sipML5, VoIP, HTML5, WebRTC, RTCWeb, SIP, IMS, Video chat, VP8, live demo, Billing " />
	
	<link type="text/css" rel="stylesheet" href="CallCenterBK.css">
	<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon" />
	<title>Info Billing 08</title>
	 <script>
    	var isomorphicDir="callcenterbk/sc/"
    </script>
    <script type='text/javascript'>   
            var logger =  undefined; //console;
    </script>	
        
    <script>window.isomorphicDir="callcenterbk/sc/"</script>
 	<script src=callcenterbk/sc/modules/ISC_FileLoader.js></script>
 	<script src=javascript/keyboard.js></script>
 	
 	<isomorphic:loadISC skin="EnterpriseBlue" modulesDir="modules/"
       includeModules="Core,Foundation,Containers,Grids,Forms,DataBinding" />
 	
 	
 	<script type="text/javascript" src="callcenterbk/callcenterbk.nocache.js"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=CallSessDS,LogSessChDS,ServiceDS,UsersDS,OperatorWarnsDS,SentSMSHist,OrgInfoByPhoneDS,BillingCompsDS,BillingCompsIndDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=SessSurvHistDS,OrgDS,SubscriberDS,NameDS,FamilyNameDS,CitiesDS,TownDistrictDS,DescriptionsDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=AbPhonesDS,imageTestDS,PermisssionsDS,CountryDS,DepartmentDS,ContinentDS,TownsDS,StatisticsDS,OrgDepartmentDS,AddressDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=StreetKindDS,StreetNamesDS,StreetsDS,StreetOldNamesDS,DistBetweenTownsDS,DistrictIndexesDS,VillageIndexesDS,StreetIndexDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=TranspTypeDS,TranspCompDS,TranspStatDS,PubTranspDirDS,PubTranspDirStreetDS,TranspScheduleDS,TranspItemsDS,TranspResDS"></script>	
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=EventCategoryDS,EventOwnerDS,EventDS,CurrencyDS,CurrencyCourseDS,FactsDS,FactStatusDS,FactTypeDS,FactsDescriptorDS,MainDetTypeDS,MainDetailDS,CallCenterDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=SurveyReplyTypeDS,SurveyDS,SurveyKindDS,OrgActDS,BusinessSecDS,BusinessUnionDS,MainDetDS,StatisticsByBillingCompDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=LandlineIndexesDS,GSMIndexesDS,TreatmentsDS,CallSessExpDS1,CallCenterNewsDS,CCServiceLog,LockDS,OrgDepPhoneDS,FindTransportDS,PhoneViewDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=OrgParrAndChildByIdDS,BillCallsBySrvDS,MySQLSubsDS,CorporateClientsDS,ContractorsPricesDS,CorpClientPhonesDS,BlackListDS,CountryIndexesDS,OperatorBreaksDS,StaffDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=PartniorNumbersDS,PartniorNumbersPriorityDS,StaffEducationDS,ClosedOpenedDS,StaffComputerSkillsDS,StaffLanguagesDS,StaffPhonesDS,StaffWorksDS,StaffRelativeDS,StaffFamousPeopleDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=OrgPriorityTypeDS,OrgPrioritiesDS,OrgPrioritiesDS1,UnknownNumbersDS,FreeOfChargePhoneDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=OrgPrioritiesStatisticsDS,WebSiteGroupsDS,WebSitesDS,NoneStandartInfoGroupsDS,NonStandartInfosDS"></script>
	<script type="text/javascript" src="callcenterbk/sc/DataSourceLoader?dataSource=ImageDataDS,StatisticOrgCorrectDS,CorrUserStatDS,OperatorsDS,OrgPriorListDS"></script>
	

<script src="js/jquery-1.7.2.min.js"></script>
	<script>!window.jQuery && document.write('<script src="/js/jquery-1.7.2.min.js"><\/script>');</script>
	<script src="js/highcharts.js"></script>
	<script src="js/highcharts-more.js"></script>
	<script src="js/highstock.src.js"></script>
	<script src="js/exporting.js"></script>
	
	<script type="text/javascript">
		if (isc.Menu) {
    		isc.Menu.addProperties({
        	iconBodyStyleName:"myMenuMain",
    	});
	}
	</script>
	
	<script src="js/SIPml-api.js?svn=222"></script>
	<script src="js/mysip.js"></script>
	<script src="js/addon.user.js"></script>
</head>
<body>
	 <iframe src="javascript:''" id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>
	 <!-- Audio Files -->
	 <audio id="audio_remote" autoplay="autoplay" />
	 <audio id="ringtone" src="audio/ringtone.wav" />
	 <audio id="ringbacktone" loop src="audio/ringbacktone.wav" />
	 <audio id="dtmfTone" src="audio/dtmf.wav" />
</body>
</html>