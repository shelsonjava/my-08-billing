package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCallRules;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCountryOpers;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewIndex;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
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
import com.smartgwt.client.widgets.tab.TabSet;

public class TabFindIndexes extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem countryItem;
	private TextItem countyCodeItem;
	private TextItem townItem;
	private TextItem codeItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findByCountryBtn;
	private IButton findByTownBtn;
	private IButton findByOperBtn;
	private IButton callInfoBtn;

	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;
	private ListGrid listGridOperators;

	private TabSet tabSet;

	// DataSource
	private DataSource TownsDS;

	public TabFindIndexes() {
		setTitle(CallCenterBK.constants.findCodes());
		setCanClose(true);

		TownsDS = DataSource.get("TownsDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(false);
		searchForm.setWidth(750);
		searchForm.setNumCols(2);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		countryItem = new ComboBoxItem();
		countryItem.setTitle(CallCenterBK.constants.country());
		countryItem.setName("countryItem");
		countryItem.setWidth(400);
		countryItem.setFetchMissingValues(true);
		countryItem.setFilterLocally(false);
		countryItem.setAddUnknownValues(false);

		DataSource countryDS = DataSource.get("CountryDS");
		countryItem.setOptionOperationId("searchAllCountriesNoContForCombos");
		countryItem.setOptionDataSource(countryDS);
		countryItem.setValueField("country_id");
		countryItem.setDisplayField("country_name");
		countryItem.setOptionCriteria(new Criteria());
		countryItem.setAutoFetchData(false);

		countryItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = countryItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("country_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("country_id", nullO);
					}
				}
			}
		});

		countryItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				countyCodeItem.setValue("");
				ListGridRecord record = countryItem.getSelectedRecord();
				if (record == null) {
					return;
				}
				String phone_code = record.getAttributeAsString("phone_code");
				if (phone_code != null && !phone_code.trim().equals("")) {
					countyCodeItem.setValue(phone_code);
				}
			}
		});

		countyCodeItem = new TextItem();
		countyCodeItem.setTitle(CallCenterBK.constants.countryCode());
		countyCodeItem.setWidth(350);
		countyCodeItem.setName("countyCodeItem");
		countyCodeItem.setCanEdit(false);

		townItem = new TextItem();
		townItem.setTitle(CallCenterBK.constants.town());
		townItem.setName("townItem");
		townItem.setWidth(400);

		codeItem = new TextItem();
		codeItem.setTitle(CallCenterBK.constants.code());
		codeItem.setWidth(350);
		codeItem.setName("codeItem");

		searchForm.setFields(countryItem, countyCodeItem, townItem, codeItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setPadding(2);
		buttonLayout.setAlign(Alignment.LEFT);

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());
		clearButton.setWidth(110);

		findByCountryBtn = new IButton();
		findByCountryBtn.setTitle(CallCenterBK.constants.findByCountry());

		findByTownBtn = new IButton();
		findByTownBtn.setTitle(CallCenterBK.constants.findByTown());

		findByOperBtn = new IButton();
		findByOperBtn.setTitle(CallCenterBK.constants.findByOperator());

		callInfoBtn = new IButton();
		callInfoBtn.setTitle(CallCenterBK.constants.callInfo());

		buttonLayout.addMember(callInfoBtn);
		buttonLayout.addMember(new LayoutSpacer());

		buttonLayout.addMember(findByCountryBtn);
		buttonLayout.addMember(findByTownBtn);
		buttonLayout.addMember(findByOperBtn);
		buttonLayout.addMember(clearButton);

		mainLayout.addMember(buttonLayout);

		tabSet = new TabSet();
		tabSet.setWidth(1300);
		tabSet.setHeight100();
		mainLayout.addMember(tabSet);

		Tab tabMainInfo = new Tab(CallCenterBK.constants.maininfo());
		Tab tabOpersInfo = new Tab(CallCenterBK.constants.operators());

		tabSet.setTabs(tabMainInfo, tabOpersInfo);

		listGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord iRecord = (ListGridRecord) record;
				if (iRecord == null) {
					return super.getCellCSSText(iRecord, rowNum, colNum);
				}
				Integer capital = iRecord.getAttributeAsInt("capital");
				if (capital != null && !capital.equals(0) && colNum == 0) {
					return "color:red;";
				} else {
					Integer seasonid = iRecord.getAttributeAsInt("season");
					if (seasonid != null && seasonid.equals(0) && colNum == 5) {
						// return "background-color: red; color:blue;";
						return "color:red;";
					} else if (seasonid != null && !seasonid.equals(0)
							&& colNum == 6) {
						return "color:red;";
					}
					return super.getCellCSSText(iRecord, rowNum, colNum);
				}
			};
		};

		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(TownsDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchCitiesFromDBForCallCenter");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setCanDragSelectText(true);

		ListGridField townName = new ListGridField("town_name",
				CallCenterBK.constants.town(), 200);
		ListGridField countrycode = new ListGridField("phone_code",
				CallCenterBK.constants.countryCodeShort(), 80);
		ListGridField countryregion = new ListGridField("country_region",
				CallCenterBK.constants.region());
		ListGridField code = new ListGridField("code",
				CallCenterBK.constants.oldCode(), 230);
		ListGridField town_new_code = new ListGridField("town_new_code",
				CallCenterBK.constants.newCode(), 230);
		ListGridField gmtoff = new ListGridField("gmtoff",
				CallCenterBK.constants.gmtoff(), 80);
		ListGridField gmtoffwinter = new ListGridField("gmtoffwinter",
				CallCenterBK.constants.gmtoffwinter(), 80);
		ListGridField ctm = new ListGridField("ctm",
				CallCenterBK.constants.ctm(), 80);

		townName.setAlign(Alignment.LEFT);
		countrycode.setAlign(Alignment.CENTER);
		countryregion.setAlign(Alignment.LEFT);
		code.setAlign(Alignment.CENTER);
		town_new_code.setAlign(Alignment.CENTER);
		gmtoff.setAlign(Alignment.CENTER);
		gmtoffwinter.setAlign(Alignment.CENTER);
		ctm.setAlign(Alignment.CENTER);

		townName.setCanFilter(true);
		countrycode.setCanFilter(false);
		countryregion.setCanFilter(true);
		code.setCanFilter(true);
		gmtoff.setCanFilter(false);
		gmtoffwinter.setCanFilter(false);
		ctm.setCanFilter(false);

		listGrid.setFields(townName, countryregion, countrycode, town_new_code,
				code, gmtoff, gmtoffwinter, ctm);

		tabMainInfo.setPane(listGrid);

		listGridOperators = new ListGrid();

		listGridOperators.setWidth100();
		listGridOperators.setHeight100();
		listGridOperators.setAlternateRecordStyles(true);
		listGridOperators.setDataSource(TownsDS);
		listGridOperators.setAutoFetchData(false);
		listGridOperators.setShowFilterEditor(false);
		listGridOperators.setCanEdit(false);
		listGridOperators.setCanRemoveRecords(false);
		listGridOperators.setFetchOperation("searchCityOpersForCallCenter");
		listGridOperators.setCanSort(false);
		listGridOperators.setCanResizeFields(false);
		listGridOperators.setShowFilterEditor(true);
		listGridOperators.setFilterOnKeypress(true);

		ListGridField stringOne = new ListGridField("stringOne",
				CallCenterBK.constants.operator());
		ListGridField stringTwo = new ListGridField("stringTwo",
				CallCenterBK.constants.codes());

		listGridOperators.setFields(stringOne, stringTwo);

		tabOpersInfo.setPane(listGridOperators);

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				countryItem.clearValue();
				countyCodeItem.clearValue();
				townItem.clearValue();
				codeItem.clearValue();
			}
		});

		findByCountryBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(1);
			}
		});

		findByTownBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(2);
			}
		});

		findByOperBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(3);
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {

				int num = event.getFieldNum();
				if (num == 2) {
					ListGridRecord gridRecord = listGrid.getSelectedRecord();
					if (gridRecord == null) {
						return;
					}
					Integer countryid = gridRecord
							.getAttributeAsInt("country_id");
					if (countryid == null) {
						return;
					}
					countryItem.setValue(countryid);
					countyCodeItem.clearValue();
					townItem.clearValue();
					codeItem.clearValue();
					search(1);

				} else {
					DlgViewIndex dlgViewIndex = new DlgViewIndex(listGrid,
							TownsDS, listGrid.getSelectedRecord());
					dlgViewIndex.show();
				}
			}
		});

		listGridOperators
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						DlgViewCountryOpers dlgViewCountryOpers = new DlgViewCountryOpers(
								listGridOperators, TownsDS, listGridOperators
										.getSelectedRecord());
						dlgViewCountryOpers.show();
					}
				});

		countyCodeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search(1);
				}
			}
		});
		townItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search(2);
				}
			}
		});

		callInfoBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgViewCallRules dlgViewCallRules = new DlgViewCallRules();
				dlgViewCallRules.show();
			}
		});

		setPane(mainLayout);
	}

	private void search(int searchType) {
		try {
			String countryid_str = countryItem.getValueAsString();
			String town_name = townItem.getValueAsString();
			String code = codeItem.getValueAsString();

			if ((countryid_str == null || countryid_str.trim().equals(""))
					&& (town_name == null || town_name.trim().equals(""))
					&& (code == null || code.trim().equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.enterIndexSearchParams());
				return;
			}
			if (searchType == 3) {

				tabSet.selectTab(1);
				Criteria criteriaOpers = new Criteria();
				criteriaOpers.setAttribute("stringTwo", code);

				if (countryid_str != null && !countryid_str.trim().equals("")) {
					criteriaOpers.setAttribute("country_id", new Integer(
							countryid_str));
				}

				DSRequest dsRequest1 = new DSRequest();
				dsRequest1.setAttribute("operationId",
						"searchCityOpersForCallCenter");
				listGridOperators.invalidateCache();
				listGridOperators.fetchData(criteriaOpers, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
					}
				}, dsRequest1);

				listGrid.invalidateCache();
				listGrid.setData(new ListGridRecord[] {});
				return;
			}
			tabSet.selectTab(0);
			Criteria criteria = new Criteria();
			if (searchType == 1 && countryid_str != null
					&& !countryid_str.trim().equals("")) {
				criteria.setAttribute("country_id", new Integer(countryid_str));
			}
			if (town_name != null && !town_name.trim().equals("")) {
				criteria.setAttribute("p_town_name", town_name.trim());
			}
			criteria.setAttribute("codeSearchType", new Integer(searchType));

			if (code != null && !code.trim().equals("")) {
				criteria.setAttribute("p_code", code.trim());
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchCitiesFromDBForCallCenter");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);

			if (searchType == 1 && countryid_str != null
					&& !countryid_str.trim().equals("")) {
				Criteria criteriaOpers = new Criteria();
				criteriaOpers.setAttribute("country_id", new Integer(
						countryid_str));

				DSRequest dsRequest1 = new DSRequest();
				dsRequest1.setAttribute("operationId",
						"searchCityOpersForCallCenter");
				listGridOperators.invalidateCache();
				listGridOperators.fetchData(criteriaOpers, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
					}
				}, dsRequest1);
			} else {
				listGridOperators.invalidateCache();
				listGridOperators.setData(new ListGridRecord[] {});
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
