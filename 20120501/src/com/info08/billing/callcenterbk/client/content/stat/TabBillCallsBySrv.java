package com.info08.billing.callcenterbk.client.content.stat;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.cube.CellRecord;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabBillCallsBySrv extends Tab {

	private VLayout mainLayout;
	//protected FacetChart chart;
	private CubeGrid cubeGrid;

	private DateItem dateItemStart;
	private DateItem dateItemEnd;
	private SelectItem typeItem;
	private SelectItem callTypeItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// search form
	private DynamicForm searchForm;
	private DynamicForm searchForm1;

	private CheckboxItem chbItemOrg;
	private CheckboxItem chbItemAbon;
	private CheckboxItem chbItemFindByNum;
	private CheckboxItem chbItemCodesInfo;
	private CheckboxItem chbItemGadartva;
	private CheckboxItem chbItemExactTime;
	private CheckboxItem chbItemTickReserv;
	private CheckboxItem chbItemSportInfo;
	private CheckboxItem chbItemCityTransp;
	private CheckboxItem chbItemTransp;
	private CheckboxItem chbItemAlarmClock;
	private CheckboxItem chbItemCurrRate;
	private CheckboxItem chbItemEntPoster;
	private CheckboxItem chbItemStreetInfo;
	private CheckboxItem chbItemEmail;
	private CheckboxItem chbItemGeorgPostIdx;
	private CheckboxItem chbItemWeather;
	private CheckboxItem chbItemWebSites;
	private CheckboxItem chbItemIdxsInfo;
	private CheckboxItem chbItemCalendar;
	private CheckboxItem chbItemNonStandartInfo;
	private CheckboxItem chbItemDistBetwCities;
	private CheckboxItem chbItemOrthCalendar;
	private CheckboxItem chbItemHourDateAndOther;
	private CheckboxItem chbItemAviaScheduler;
	private CheckboxItem chbItemMonumGard;
	private CheckboxItem chbItemSMSInfo;
	private CheckboxItem chbItemCallRules;
	private CheckboxItem chbItemRailScheduler;

	private CheckboxItem selectServices;

	public TabBillCallsBySrv() {
		try {

			setTitle(CallCenter.constants.callsTotalBySrv());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(200);
			searchForm.setNumCols(1);
			searchForm.setTitleOrientation(TitleOrientation.TOP);

			searchForm1 = new DynamicForm();
			searchForm1.setAutoFocus(true);
			searchForm1.setWidth(700);
			searchForm1.setHeight(50);
			searchForm1.setNumCols(12);
			searchForm1.setTitleWidth(10);

			chbItemOrg = new CheckboxItem();
			chbItemOrg.setTitle(CallCenter.constants.organization());
			chbItemOrg.setName("chbItemOrg");
			chbItemOrg.setValue(true);
			chbItemOrg.setWidth(100);

			chbItemAbon = new CheckboxItem();
			chbItemAbon.setTitle(CallCenter.constants.abonent());
			chbItemAbon.setName("chbItemAbon");
			chbItemAbon.setValue(true);
			chbItemAbon.setWidth(100);

			chbItemFindByNum = new CheckboxItem();
			chbItemFindByNum.setTitle(CallCenter.constants.findByNumber());
			chbItemFindByNum.setName("chbItemFindByNum");
			chbItemFindByNum.setValue(true);
			chbItemFindByNum.setWidth(100);

			chbItemCodesInfo = new CheckboxItem();
			chbItemCodesInfo.setTitle(CallCenter.constants.codesInfo());
			chbItemCodesInfo.setName("chbItemCodesInfo");
			chbItemCodesInfo.setValue(true);
			chbItemCodesInfo.setWidth(100);

			chbItemGadartva = new CheckboxItem();
			chbItemGadartva.setTitle(CallCenter.constants.gadartva());
			chbItemGadartva.setName("chbItemGadartva");
			chbItemGadartva.setValue(true);
			chbItemGadartva.setWidth(100);

			chbItemExactTime = new CheckboxItem();
			chbItemExactTime.setTitle(CallCenter.constants.exactTime());
			chbItemExactTime.setName("checkboxItem6");
			chbItemExactTime.setValue(true);
			chbItemExactTime.setWidth(100);

			chbItemTickReserv = new CheckboxItem();
			chbItemTickReserv.setTitle(CallCenter.constants.ticketReserv());
			chbItemTickReserv.setName("chbItemTickReserv");
			chbItemTickReserv.setValue(true);
			chbItemTickReserv.setWidth(100);

			chbItemSportInfo = new CheckboxItem();
			chbItemSportInfo.setTitle(CallCenter.constants.sport());
			chbItemSportInfo.setName("chbItemSportInfo");
			chbItemSportInfo.setValue(true);
			chbItemSportInfo.setWidth(100);

			chbItemCityTransp = new CheckboxItem();
			chbItemCityTransp.setTitle(CallCenter.constants.cityTransport());
			chbItemCityTransp.setName("chbItemCityTransp");
			chbItemCityTransp.setValue(true);
			chbItemCityTransp.setWidth(100);

			chbItemTransp = new CheckboxItem();
			chbItemTransp.setTitle(CallCenter.constants.transport());
			chbItemTransp.setName("chbItemTransp");
			chbItemTransp.setValue(true);
			chbItemTransp.setWidth(100);

			chbItemAlarmClock = new CheckboxItem();
			chbItemAlarmClock.setTitle(CallCenter.constants.alarmClock());
			chbItemAlarmClock.setName("chbItemAlarmClock");
			chbItemAlarmClock.setValue(true);
			chbItemAlarmClock.setWidth(100);

			chbItemCurrRate = new CheckboxItem();
			chbItemCurrRate.setTitle(CallCenter.constants.currencyRate());
			chbItemCurrRate.setName("chbItemCurrRate");
			chbItemCurrRate.setValue(true);
			chbItemCurrRate.setWidth(100);

			chbItemEntPoster = new CheckboxItem();
			chbItemEntPoster.setTitle(CallCenter.constants.poster());
			chbItemEntPoster.setName("chbItemEntPoster");
			chbItemEntPoster.setValue(true);
			chbItemEntPoster.setWidth(100);

			chbItemStreetInfo = new CheckboxItem();
			chbItemStreetInfo.setTitle(CallCenter.constants.streetInfoSrv());
			chbItemStreetInfo.setName("chbItemStreetInfo");
			chbItemStreetInfo.setValue(true);
			chbItemStreetInfo.setWidth(100);

			chbItemEmail = new CheckboxItem();
			chbItemEmail.setTitle(CallCenter.constants.eMail());
			chbItemEmail.setName("chbItemEmail");
			chbItemEmail.setValue(true);
			chbItemEmail.setWidth(100);

			chbItemGeorgPostIdx = new CheckboxItem();
			chbItemGeorgPostIdx.setTitle(CallCenter.constants
					.georgPostIndexes());
			chbItemGeorgPostIdx.setName("chbItemGeorgPostIdx");
			chbItemGeorgPostIdx.setValue(true);
			chbItemGeorgPostIdx.setWidth(100);

			chbItemWeather = new CheckboxItem();
			chbItemWeather.setTitle(CallCenter.constants.weatherShort());
			chbItemWeather.setName("chbItemWeather");
			chbItemWeather.setValue(true);
			chbItemWeather.setWidth(100);

			chbItemWebSites = new CheckboxItem();
			chbItemWebSites.setTitle(CallCenter.constants.webSiteSrv());
			chbItemWebSites.setName("chbItemWebSites");
			chbItemWebSites.setValue(true);
			chbItemWebSites.setWidth(100);

			chbItemIdxsInfo = new CheckboxItem();
			chbItemIdxsInfo.setTitle(CallCenter.constants.indexesInfo());
			chbItemIdxsInfo.setName("chbItemIdxsInfo");
			chbItemIdxsInfo.setValue(true);
			chbItemIdxsInfo.setWidth(100);

			chbItemCalendar = new CheckboxItem();
			chbItemCalendar.setTitle(CallCenter.constants.calendarShort());
			chbItemCalendar.setName("chbItemCalendar");
			chbItemCalendar.setValue(true);
			chbItemCalendar.setWidth(100);

			chbItemNonStandartInfo = new CheckboxItem();
			chbItemNonStandartInfo.setTitle(CallCenter.constants
					.nonStandartQuest());
			chbItemNonStandartInfo.setName("chbItemNonStandartInfo");
			chbItemNonStandartInfo.setValue(true);
			chbItemOrg.setWidth(100);

			chbItemDistBetwCities = new CheckboxItem();
			chbItemDistBetwCities.setTitle(CallCenter.constants
					.distBetweenCities());
			chbItemDistBetwCities.setName("chbItemDistBetwCities");
			chbItemDistBetwCities.setValue(true);
			chbItemDistBetwCities.setWidth(100);

			chbItemOrthCalendar = new CheckboxItem();
			chbItemOrthCalendar.setTitle(CallCenter.constants.orthCalendar());
			chbItemOrthCalendar.setName("chbItemOrthCalendar");
			chbItemOrthCalendar.setValue(true);
			chbItemOrthCalendar.setWidth(100);

			chbItemHourDateAndOther = new CheckboxItem();
			chbItemHourDateAndOther.setTitle(CallCenter.constants
					.hourDateAndOther());
			chbItemHourDateAndOther.setName("chbItemHourDateAndOther");
			chbItemHourDateAndOther.setValue(true);
			chbItemHourDateAndOther.setWidth(100);

			chbItemAviaScheduler = new CheckboxItem();
			chbItemAviaScheduler.setTitle(CallCenter.constants.aviaScheduler());
			chbItemAviaScheduler.setName("chbItemAviaScheduler");
			chbItemAviaScheduler.setValue(true);
			chbItemAviaScheduler.setWidth(100);

			chbItemMonumGard = new CheckboxItem();
			chbItemMonumGard.setTitle(CallCenter.constants.monumGard());
			chbItemMonumGard.setName("chbItemMonumGard");
			chbItemMonumGard.setValue(true);
			chbItemMonumGard.setWidth(100);

			chbItemSMSInfo = new CheckboxItem();
			chbItemSMSInfo.setTitle(CallCenter.constants.smsInfo());
			chbItemSMSInfo.setName("chbItemSMSInfo");
			chbItemSMSInfo.setValue(true);
			chbItemSMSInfo.setWidth(100);

			chbItemCallRules = new CheckboxItem();
			chbItemCallRules.setTitle(CallCenter.constants.callRules());
			chbItemCallRules.setName("chbItemCallRules");
			chbItemCallRules.setValue(true);
			chbItemCallRules.setWidth(100);

			chbItemRailScheduler = new CheckboxItem();
			chbItemRailScheduler.setTitle(CallCenter.constants.railScheduler());
			chbItemRailScheduler.setName("chbItemRailScheduler");
			chbItemRailScheduler.setValue(true);
			chbItemRailScheduler.setWidth(100);

			searchForm1.setFields(chbItemOrg, chbItemAbon, chbItemFindByNum,
					chbItemCodesInfo, chbItemGadartva, chbItemExactTime,
					chbItemTickReserv, chbItemSportInfo, chbItemCityTransp,
					chbItemTransp, chbItemAlarmClock, chbItemCurrRate,
					chbItemEntPoster, chbItemStreetInfo, chbItemEmail,
					chbItemGeorgPostIdx, chbItemWeather, chbItemWebSites,
					chbItemIdxsInfo, chbItemCalendar, chbItemNonStandartInfo,
					chbItemDistBetwCities, chbItemOrthCalendar,
					chbItemHourDateAndOther, chbItemAviaScheduler,
					chbItemMonumGard, chbItemSMSInfo, chbItemCallRules,
					chbItemRailScheduler);

			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setHeight(60);
			hLayout.setMembers(searchForm, searchForm1);

			mainLayout.addMember(hLayout);

			dateItemStart = new DateItem();
			dateItemStart.setTitle(CallCenter.constants.date());
			dateItemStart.setName("dateItemStart");
			Date date = new Date();
			CalendarUtil.addDaysToDate(date, -7);
			dateItemStart.setValue(date);

			dateItemEnd = new DateItem();
			dateItemEnd.setTitle(CallCenter.constants.date());
			dateItemEnd.setName("dateItemEnd");

			typeItem = new SelectItem();
			typeItem.setTitle(CallCenter.constants.type());
			typeItem.setName("typeItem");
			typeItem.setValueMap(ClientMapUtil.getInstance()
					.getCallStatChargeTypes());
			typeItem.setDefaultToFirstOption(true);
			typeItem.setWidth(190);

			callTypeItem = new SelectItem();
			callTypeItem.setTitle(CallCenter.constants.type());
			callTypeItem.setName("callTypeItem");
			callTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getCallStatChargeTypes1());
			callTypeItem.setDefaultToFirstOption(true);
			callTypeItem.setWidth(190);

			searchForm.setFields(dateItemStart, dateItemEnd, typeItem,
					callTypeItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.LEFT);

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			selectServices = new CheckboxItem();
			selectServices.setTitle(CallCenter.constants.selectAllServices());
			selectServices.setName("selectServices");
			selectServices.setValue(true);
			selectServices.setWidth(250);

			DynamicForm searchForm2 = new DynamicForm();
			searchForm2.setAutoFocus(true);
			searchForm2.setNumCols(1);
			searchForm2.setTitleWidth(10);

			searchForm2.setFields(selectServices);

			buttonLayout.setMembers(findButton, clearButton, searchForm2);
			mainLayout.addMember(buttonLayout);

			
			cubeGrid = new CubeGrid();
			cubeGrid.setData(new CellRecord[0]);
			cubeGrid.setEnableCharting(true);  
			cubeGrid.setAutoFetchData(false);  
            cubeGrid.setWidth100();  
            cubeGrid.setHeight100();  
            //cubeGrid.setHideEmptyFacetValues(true);  
            //cubeGrid.setShowCellContextMenus(true); 
//            final NumberFormat numberFormat = NumberFormat.getFormat("0,000");  
//            
//            cubeGrid.setCellFormatter(new CellFormatter() {  
//                public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
//                    if (value == null) return null;  
//                    try {  
//                        return numberFormat.format(((Number) value).longValue());  
//                    } catch (Exception e) {  
//                        return value.toString();  
//                    }  
//                }  
//            });  
  
            cubeGrid.setColumnFacets("bill_date_str");  
            cubeGrid.setRowFacets("service_name_geo");
            cubeGrid.setValueProperty("_value");
            cubeGrid.setCellIdProperty("id");  
            //cubeGrid.setHiliteProperty("_hilite");
            
            mainLayout.addMember(cubeGrid);
            
//			chart = new FacetChart();
//			chart.setFacets(new Facet("bill_date_str", "bill_date_str"),
//					new Facet("service_name_geo", "service_name_geo"));
//			chart.setValueProperty("value");
//			chart.setChartType(ChartType.LINE);
//			chart.setTitle(CallCenter.constants.callsTotalBySrvTitle());
//			chart.setValueTitle(CallCenter.constants.callsCount());
//			chart.setShowDataPoints(true);
//			chart.setPointHoverCustomizer(new ChartPointHoverCustomizer() {				
//				@Override
//				public String hoverHTML(Float value, Record record) {
//					return ("<b>" + CallCenter.constants.service() + " : </b> " + record.getAttribute("service_name_geo")+ "<br />"+ 
//							"<b>" + CallCenter.constants.date()+ " : </b> " + record.getAttribute("bill_date_str") + "<br />" + 
//							"<b>" + CallCenter.constants.weekDay()+ " : </b> " + record.getAttribute("week_day") + "<br />" +
//							"<b>" + ((record.getAttribute("count_type")!=null && record.getAttributeAsInt("count_type").equals(0)) ?CallCenter.constants.callsCount():CallCenter.constants.percentShort()) + " : </b> " + record.getAttribute("value") + "<br /><hr width=250 >");
//				}
//			});
//			mainLayout.addMember(chart);
			setPane(mainLayout);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					dateItemEnd.setValue(new Date());
					Date date = new Date();
					CalendarUtil.addDaysToDate(date, -7);
					dateItemStart.setValue(date);
					typeItem.clearValue();
				}
			});

			selectServices.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					boolean value = selectServices.getValueAsBoolean();
					chbItemOrg.setValue(value);
					chbItemAbon.setValue(value);
					chbItemFindByNum.setValue(value);
					chbItemCodesInfo.setValue(value);
					chbItemGadartva.setValue(value);
					chbItemExactTime.setValue(value);
					chbItemTickReserv.setValue(value);
					chbItemSportInfo.setValue(value);
					chbItemCityTransp.setValue(value);
					chbItemTransp.setValue(value);
					chbItemAlarmClock.setValue(value);
					chbItemCurrRate.setValue(value);
					chbItemEntPoster.setValue(value);
					chbItemGeorgPostIdx.setValue(value);
					chbItemEmail.setValue(value);
					chbItemWeather.setValue(value);
					chbItemWebSites.setValue(value);
					chbItemIdxsInfo.setValue(value);
					chbItemCalendar.setValue(value);
					chbItemNonStandartInfo.setValue(value);
					chbItemDistBetwCities.setValue(value);
					chbItemOrthCalendar.setValue(value);
					chbItemHourDateAndOther.setValue(value);
					chbItemAviaScheduler.setValue(value);
					chbItemMonumGard.setValue(value);
					chbItemSMSInfo.setValue(value);
					chbItemCallRules.setValue(value);
					chbItemRailScheduler.setValue(value);
					chbItemStreetInfo.setValue(value);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			DataSource dataSource = DataSource.get("BillCallsBySrvDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchBillCallsBySrv");
			Criteria criteria = new Criteria();

			String typeStr = typeItem.getValueAsString();
			if (typeStr == null || typeStr.trim().equals("")) {
				SC.say(CallCenter.constants.warning(),
						CallCenter.constants.chooseType());
				return;
			}
			String typeStr1 = callTypeItem.getValueAsString();
			if (typeStr1 == null || typeStr1.trim().equals("")) {
				SC.say(CallCenter.constants.warning(),
						CallCenter.constants.chooseType());
				return;
			}
			criteria.setAttribute("type", new Integer(typeStr));
			criteria.setAttribute("count_type", new Integer(typeStr1));
			Date startDate = dateItemStart.getValueAsDate();
			Date endDate = dateItemEnd.getValueAsDate();
			criteria.setAttribute("bill_date_start", startDate);
			criteria.setAttribute("bill_date_end", endDate);
			boolean isSelMin1 = false;
			if (chbItemOrg.getValueAsBoolean()) {
				criteria.setAttribute("servicesId1", new Integer(3));
				isSelMin1 = true;
			}
			if (chbItemAbon.getValueAsBoolean()) {
				criteria.setAttribute("servicesId2", new Integer(7));
				isSelMin1 = true;
			}
			if (chbItemFindByNum.getValueAsBoolean()) {
				criteria.setAttribute("servicesId3", new Integer(46));
				isSelMin1 = true;
			}
			if (chbItemCodesInfo.getValueAsBoolean()) {
				criteria.setAttribute("servicesId4", new Integer(5));
				isSelMin1 = true;
			}
			if (chbItemGadartva.getValueAsBoolean()) {
				criteria.setAttribute("servicesId5", new Integer(60));
				isSelMin1 = true;
			}
			if (chbItemExactTime.getValueAsBoolean()) {
				criteria.setAttribute("servicesId6", new Integer(44));
				isSelMin1 = true;
			}
			if (chbItemTickReserv.getValueAsBoolean()) {
				criteria.setAttribute("servicesId7", new Integer(27));
				isSelMin1 = true;
			}
			if (chbItemSportInfo.getValueAsBoolean()) {
				criteria.setAttribute("servicesId8", new Integer(24));
				isSelMin1 = true;
			}
			if (chbItemCityTransp.getValueAsBoolean()) {
				criteria.setAttribute("servicesId9", new Integer(26));
				isSelMin1 = true;
			}
			if (chbItemTransp.getValueAsBoolean()) {
				criteria.setAttribute("servicesId10", new Integer(13));
				isSelMin1 = true;
			}
			if (chbItemAlarmClock.getValueAsBoolean()) {
				criteria.setAttribute("servicesId11", new Integer(18));
				isSelMin1 = true;
			}
			if (chbItemCurrRate.getValueAsBoolean()) {
				criteria.setAttribute("servicesId12", new Integer(11));
				isSelMin1 = true;
			}
			if (chbItemEntPoster.getValueAsBoolean()) {
				criteria.setAttribute("servicesId13", new Integer(10));
				isSelMin1 = true;
			}
			if (chbItemStreetInfo.getValueAsBoolean()) {
				criteria.setAttribute("servicesId14", new Integer(4));
				isSelMin1 = true;
			}
			if (chbItemEmail.getValueAsBoolean()) {
				criteria.setAttribute("servicesId15", new Integer(53));
				isSelMin1 = true;
			}
			if (chbItemGeorgPostIdx.getValueAsBoolean()) {
				criteria.setAttribute("servicesId16", new Integer(35));
				isSelMin1 = true;
			}
			if (chbItemWeather.getValueAsBoolean()) {
				criteria.setAttribute("servicesId17", new Integer(12));
				isSelMin1 = true;
			}
			if (chbItemWebSites.getValueAsBoolean()) {
				criteria.setAttribute("servicesId18", new Integer(63));
				isSelMin1 = true;
			}
			if (chbItemIdxsInfo.getValueAsBoolean()) {
				criteria.setAttribute("servicesId19", new Integer(48));
				isSelMin1 = true;
			}
			if (chbItemCalendar.getValueAsBoolean()) {
				criteria.setAttribute("servicesId20", new Integer(42));
				isSelMin1 = true;
			}
			if (chbItemNonStandartInfo.getValueAsBoolean()) {
				criteria.setAttribute("servicesId21", new Integer(32));
				isSelMin1 = true;
			}
			if (chbItemDistBetwCities.getValueAsBoolean()) {
				criteria.setAttribute("servicesId22", new Integer(31));
				isSelMin1 = true;
			}
			if (chbItemOrthCalendar.getValueAsBoolean()) {
				criteria.setAttribute("servicesId23", new Integer(22));
				isSelMin1 = true;
			}
			if (chbItemHourDateAndOther.getValueAsBoolean()) {
				criteria.setAttribute("servicesId24", new Integer(6));
				isSelMin1 = true;
			}
			if (chbItemAviaScheduler.getValueAsBoolean()) {
				criteria.setAttribute("servicesId25", new Integer(19));
				isSelMin1 = true;
			}
			if (chbItemMonumGard.getValueAsBoolean()) {
				criteria.setAttribute("servicesId26", new Integer(51));
				isSelMin1 = true;
			}
			if (chbItemSMSInfo.getValueAsBoolean()) {
				criteria.setAttribute("servicesId27", new Integer(34));
				isSelMin1 = true;
			}
			if (chbItemCallRules.getValueAsBoolean()) {
				criteria.setAttribute("servicesId28", new Integer(49));
				isSelMin1 = true;
			}
			if (chbItemRailScheduler.getValueAsBoolean()) {
				criteria.setAttribute("servicesId29", new Integer(20));
				isSelMin1 = true;
			}
			if (!isSelMin1) {
				SC.say(CallCenter.constants.warning(),
						CallCenter.constants.selectOneService());
				return;
			}

			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					  cubeGrid.setData(response.getData());  
					//chart.setData(response.getData());
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
