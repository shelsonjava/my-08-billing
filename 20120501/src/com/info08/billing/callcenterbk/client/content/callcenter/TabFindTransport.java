package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewTransport;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindTransport extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private SelectItem transportTypeItem;
	private TextItem routeNumItem;
	private DateItem dateItem;
	private ComboBoxItem cityFromItem;
	private ComboBoxItem cityToItem;
	private SelectItem cityDaysItem;
	private ComboBoxItem countryFromItem;
	private ComboBoxItem countryToItem;
	private SelectItem countryDaysItem;
	private Label commentLabel;
	// private TextItem commentItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButtonByRoute;
	private IButton findButtonByCity;
	private IButton findButtonByCountry;
	private IButton clear;
	// private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource transportDS;

	public TabFindTransport() {
		setTitle(CallCenterBK.constants.findTransport());
		setCanClose(true);

		transportDS = DataSource.get("FindTransportDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(false);
		searchForm.setWidth(750);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		transportTypeItem = new SelectItem();
		transportTypeItem.setTitle(CallCenterBK.constants.transportType());
		transportTypeItem.setWidth(250);
		transportTypeItem.setName("name_descr");
		transportTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspTypeCustom1());
		transportTypeItem.setDefaultValue(new Integer(1000005));

		transportTypeItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String transp_type_id_str = transportTypeItem
						.getValueAsString();
				if (transp_type_id_str != null
						&& !transp_type_id_str.trim().equals("")) {
					try {
						Integer transp_type_id = Integer
								.parseInt(transp_type_id_str);

						commentLabel.setVisible((transp_type_id.intValue() == 1));

						Criteria lCriteria = cityFromItem.getOptionCriteria();
						if (lCriteria != null) {
							lCriteria.setAttribute("transp_type_id",
									transp_type_id);
							cityFromItem.setOptionCriteria(lCriteria);
						}
						Criteria lCriteria1 = cityToItem.getOptionCriteria();
						if (lCriteria1 != null) {
							lCriteria1.setAttribute("transp_type_id",
									transp_type_id);
							cityToItem.setOptionCriteria(lCriteria1);
						}

						Criteria lCriteria2 = countryFromItem
								.getOptionCriteria();
						if (lCriteria2 != null) {
							lCriteria2.setAttribute("transp_type_id",
									transp_type_id);
							countryFromItem.setOptionCriteria(lCriteria2);
						}
						Criteria lCriteria3 = countryToItem.getOptionCriteria();
						if (lCriteria3 != null) {
							lCriteria3.setAttribute("transp_type_id",
									transp_type_id);
							countryToItem.setOptionCriteria(lCriteria3);
						}

					} catch (Exception e) {
						return;
					}
				}
			}
		});

		routeNumItem = new TextItem();
		routeNumItem.setTitle(CallCenterBK.constants.routeNumber());
		routeNumItem.setWidth(250);
		routeNumItem.setName("routeNumItem");

		dateItem = new DateItem();
		dateItem.setCellStyle("fontRed");
		dateItem.setTitle(CallCenterBK.constants.currentDate());
		dateItem.setWidth(250);
		dateItem.setName("dateItem");
		dateItem.setCanFocus(false);
		dateItem.setValue(new Date());
		dateItem.setUseTextField(true);

		cityFromItem = new ComboBoxItem();
		cityFromItem.setTitle(CallCenterBK.constants.cityFrom());
		cityFromItem.setName("cityFromItem");
		cityFromItem.setWidth(250);
		cityFromItem.setFetchMissingValues(true);
		cityFromItem.setFilterLocally(false);
		cityFromItem.setAddUnknownValues(false);
		cityFromItem.setCompleteOnTab(true);

		DataSource cityDS = DataSource.get("CityDS");
		cityFromItem.setOptionOperationId("searchCitiesByTransportType");
		cityFromItem.setOptionDataSource(cityDS);
		cityFromItem.setValueField("city_id");
		cityFromItem.setDisplayField("city_name_geo");
		Criteria criteriaFromCity = new Criteria();
		criteriaFromCity.setAttribute("transp_type_id", 1000005);
		cityFromItem.setOptionCriteria(criteriaFromCity);
		cityFromItem.setAutoFetchData(false);

		cityFromItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityFromItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});

		cityToItem = new ComboBoxItem();
		cityToItem.setTitle(CallCenterBK.constants.cityTo());
		cityToItem.setName("cityToItem");
		cityToItem.setWidth(250);
		cityToItem.setFetchMissingValues(true);
		cityToItem.setFilterLocally(false);
		cityToItem.setAddUnknownValues(false);
		cityToItem.setCompleteOnTab(true);

		cityToItem.setOptionOperationId("searchCitiesByTransportType");
		cityToItem.setOptionDataSource(cityDS);
		cityToItem.setValueField("city_id");
		cityToItem.setDisplayField("city_name_geo");
		Criteria criteriaToCity = new Criteria();
		criteriaToCity.setAttribute("transp_type_id", 1000005);
		cityToItem.setOptionCriteria(criteriaToCity);
		cityToItem.setAutoFetchData(false);

		cityToItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityToItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});

		countryFromItem = new ComboBoxItem();
		countryFromItem.setTitle(CallCenterBK.constants.countryFrom());
		countryFromItem.setName("countryFromItem");
		countryFromItem.setWidth(250);
		countryFromItem.setFetchMissingValues(true);
		countryFromItem.setFilterLocally(false);
		countryFromItem.setAddUnknownValues(false);
		countryFromItem.setCompleteOnTab(true);

		DataSource countryDS = DataSource.get("CountryDS");
		countryFromItem.setOptionOperationId("searchCountriesByTransportType");
		countryFromItem.setOptionDataSource(countryDS);
		countryFromItem.setValueField("country_id");
		countryFromItem.setDisplayField("country_name_geo");
		Criteria criteriaFromCountry = new Criteria();
		criteriaFromCountry.setAttribute("transp_type_id", 1000005);
		countryFromItem.setOptionCriteria(criteriaFromCountry);
		countryFromItem.setAutoFetchData(false);

		countryFromItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = countryFromItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("country_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("country_id", nullO);
					}
				}
			}
		});

		countryToItem = new ComboBoxItem();
		countryToItem.setTitle(CallCenterBK.constants.countryTo());
		countryToItem.setName("countryToItem");
		countryToItem.setWidth(250);
		countryToItem.setFetchMissingValues(true);
		countryToItem.setFilterLocally(false);
		countryToItem.setAddUnknownValues(false);
		countryToItem.setCompleteOnTab(true);

		countryToItem.setOptionOperationId("searchCountriesByTransportType");
		countryToItem.setOptionDataSource(countryDS);
		countryToItem.setValueField("country_id");
		countryToItem.setDisplayField("country_name_geo");
		Criteria criteriaToCountry = new Criteria();
		criteriaToCountry.setAttribute("transp_type_id", 1000005);
		countryToItem.setOptionCriteria(criteriaToCountry);
		countryToItem.setAutoFetchData(false);

		countryToItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = countryToItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("country_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("country_id", nullO);
					}
				}
			}
		});

		cityDaysItem = new SelectItem();
		cityDaysItem.setName("cityDaysItem");
		cityDaysItem.setValueMap(ClientMapUtil.getInstance().getWeekDays());
		cityDaysItem.setWidth(250);
		cityDaysItem.setTitle(CallCenterBK.constants.days());
		cityDaysItem.setDefaultToFirstOption(true);

		countryDaysItem = new SelectItem();
		countryDaysItem.setName("countryDaysItem");
		countryDaysItem.setValueMap(ClientMapUtil.getInstance().getWeekDays());
		countryDaysItem.setWidth(250);
		countryDaysItem.setTitle(CallCenterBK.constants.days());
		countryDaysItem.setDefaultToFirstOption(true);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(750);
		spacerItem.setColSpan(3);

		searchForm.setFields(transportTypeItem, routeNumItem, dateItem,
				spacerItem, cityFromItem, cityToItem, cityDaysItem,
				countryFromItem, countryToItem, countryDaysItem);

		cityFromItem.setValue(Constants.defCityTbilisiId);
		cityToItem.setValue(Constants.defCityTbilisiId);
		countryFromItem.setValue(Constants.defCountryGeorgiaId);
		countryToItem.setValue(Constants.defCountryGeorgiaId);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight100();
		spacer.setWidth(20);

		findButtonByRoute = new IButton();
		findButtonByRoute.setTitle(CallCenterBK.constants.findTranspByRoute());

		findButtonByCity = new IButton();
		findButtonByCity.setTitle(CallCenterBK.constants.findTranspByCity());

		findButtonByCountry = new IButton();
		findButtonByCountry.setTitle(CallCenterBK.constants
				.findTranspByCountry());

		clear = new IButton();
		clear.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findButtonByCity, spacer, findButtonByCountry,
				spacer, findButtonByRoute, spacer, clear);
		mainLayout.addMember(buttonLayout);

		commentLabel = new Label(CallCenterBK.constants.transportTextConstant());
		commentLabel.setWidth100();
		commentLabel.setHeight(15);
		commentLabel.setStyleName("fontRed");
		mainLayout.addMember(commentLabel);

		listGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {

				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer note_crit = countryRecord
						.getAttributeAsInt("note_crit");
				if (colNum == 1) {
					return "color:red;";
				} else if (colNum == 4) {
					return "color:green;";
				} else if (colNum == 7) {
					return "color:green;";
				} else if (colNum == 11 && note_crit != null
						&& note_crit.equals(-1)) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};

		listGrid.setWidth(1040);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(transportDS);
		listGrid.setAutoFetchData(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("findAllTransportsCC");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField trip_criteria = new ListGridField("trip_criteria",
				CallCenterBK.constants.routeNumber(), 60);
		trip_criteria.setAlign(Alignment.LEFT);
		trip_criteria.setCanFilter(true);

		ListGridField ocity_name_geo = new ListGridField("ocity_name_geo",
				CallCenterBK.constants.stationFrom(), 100);
		ocity_name_geo.setAlign(Alignment.LEFT);
		ocity_name_geo.setCanFilter(true);

		ListGridField icity_name_geo = new ListGridField("icity_name_geo",
				CallCenterBK.constants.stationTo(), 100);
		icity_name_geo.setAlign(Alignment.LEFT);
		icity_name_geo.setCanFilter(true);

		ListGridField ostation = new ListGridField("ostation",
				CallCenterBK.constants.station(), 100);
		ostation.setAlign(Alignment.LEFT);
		ostation.setCanFilter(true);

		ListGridField istation = new ListGridField("istation",
				CallCenterBK.constants.station(), 100);
		istation.setAlign(Alignment.LEFT);
		istation.setCanFilter(true);

		ListGridField out_time = new ListGridField("c_out_time",
				CallCenterBK.constants.outTime(), 70);
		out_time.setAlign(Alignment.CENTER);
		out_time.setCanFilter(false);

		ListGridField in_time = new ListGridField("c_in_time",
				CallCenterBK.constants.inTime(), 70);
		in_time.setAlign(Alignment.CENTER);
		in_time.setCanFilter(false);

		ListGridField cmt = new ListGridField("cmt",
				CallCenterBK.constants.ctm(), 70);
		cmt.setAlign(Alignment.CENTER);
		cmt.setCanFilter(false);

		ListGridField transport_company_geo = new ListGridField(
				"transport_company_geo",
				CallCenterBK.constants.transportCompShort(), 100);
		transport_company_geo.setAlign(Alignment.LEFT);
		transport_company_geo.setCanFilter(true);

		ListGridField transport_plane_geo = new ListGridField(
				"transport_plane_geo",
				CallCenterBK.constants.transportTypeShort(), 100);
		transport_plane_geo.setAlign(Alignment.CENTER);
		transport_plane_geo.setCanFilter(true);

		ListGridField days_descr = new ListGridField("days_descr",
				CallCenterBK.constants.days());
		days_descr.setAlign(Alignment.LEFT);
		days_descr.setCanFilter(true);

		ListGridField alarm = new ListGridField("alarm", "*", 20);
		alarm.setAlign(Alignment.CENTER);
		alarm.setCanFilter(false);

		listGrid.setFields(trip_criteria, ocity_name_geo, ostation, out_time,
				icity_name_geo, istation, in_time, cmt, transport_plane_geo,
				transport_company_geo, days_descr, alarm);

		mainLayout.addMember(listGrid);

		findButtonByRoute.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(1);
			}
		});
		findButtonByCity.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(2);
			}
		});
		findButtonByCountry.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(3);
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DataSource dataSourceTr = DataSource.get("TransportDS");
				String transportTypeId = transportTypeItem.getValueAsString();
				DlgViewTransport dlgViewTransport = new DlgViewTransport(
						listGrid, dataSourceTr, listGrid.getSelectedRecord(),
						new Integer(transportTypeId));
				dlgViewTransport.show();
			}
		});
		routeNumItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search(1);
				}
			}
		});

		clear.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				routeNumItem.clearValue();
				cityFromItem.setValue(Constants.defCityTbilisiId);
				cityToItem.setValue(Constants.defCityTbilisiId);
				countryFromItem.setValue(Constants.defCountryGeorgiaId);
				countryToItem.setValue(Constants.defCountryGeorgiaId);
				cityDaysItem.clearValue();
				countryDaysItem.clearValue();
			}
		});

		setPane(mainLayout);
	}

	private void search(int type) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);
			DSRequest dsRequest = new DSRequest();
			String transp_type_id = transportTypeItem.getValueAsString();
			if (transp_type_id != null && !transp_type_id.trim().equals("")) {
				criteria.setAttribute("transp_type_id", new Integer(
						transp_type_id));
			}
			switch (type) {
			case 1: // route
				String trip_criteria = routeNumItem.getValueAsString();
				if (trip_criteria != null && !trip_criteria.trim().equals("")) {
					criteria.setAttribute("trip_criteria_param", trip_criteria);
				}
				break;
			case 2:
				String o_city_id = cityFromItem.getValueAsString();
				if (o_city_id != null && !o_city_id.trim().equals("")) {
					criteria.setAttribute("o_city_id", new Integer(o_city_id));
				}
				String i_city_id = cityToItem.getValueAsString();
				if (i_city_id != null && !i_city_id.trim().equals("")) {
					criteria.setAttribute("i_city_id", new Integer(i_city_id));
				}

				if (o_city_id != null && !o_city_id.trim().equals("")
						&& i_city_id != null && !i_city_id.trim().equals("")
						&& o_city_id.equals(i_city_id)) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.chooseDifCities());
					return;
				}

				String c_days = cityDaysItem.getValueAsString();
				if (c_days != null && !c_days.trim().equals("")) {
					criteria.setAttribute("c_days", new Integer(c_days));
				}

				break;
			case 3:
				String o_country_id = countryFromItem.getValueAsString();
				if (o_country_id != null && !o_country_id.trim().equals("")) {
					criteria.setAttribute("o_country_id", new Integer(
							o_country_id));
				}
				String i_country_id = countryToItem.getValueAsString();
				if (i_country_id != null && !i_country_id.trim().equals("")) {
					criteria.setAttribute("i_country_id", new Integer(
							i_country_id));
				}

				if (o_country_id != null && !o_country_id.trim().equals("")
						&& i_country_id != null
						&& !i_country_id.trim().equals("")
						&& o_country_id.equals(i_country_id)) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.chooseDifCountries());
					return;
				}

				String co_days = countryDaysItem.getValueAsString();
				if (co_days != null && !co_days.trim().equals("")) {

					criteria.setAttribute("c_days", new Integer(co_days));
				}

				break;
			default:
				break;
			}
			dsRequest.setAttribute("operationId", "findAllTransportsCC");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
