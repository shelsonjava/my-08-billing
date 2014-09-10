package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewSubscriber;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindSubscriber extends Tab {

	// search form
	private DynamicForm searchForm;

	// main content layout
	private VLayout mainLayout;

	private TextItem nameItem;
	private TextItem family_nameItem;
	private TextItem phoneItem;
	private ComboBoxItem citiesItem;
	private TextItem streetItem;
	private ComboBoxItem regionItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabFindSubscriber() {
		setTitle(CallCenterBK.constants.findAbonent());
		setCanClose(true);

		datasource = DataSource.get("SubscriberDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(750);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		nameItem = new TextItem();
		nameItem.setTitle(CallCenterBK.constants.name());
		nameItem.setWidth(250);
		nameItem.setName("nameItem");

		family_nameItem = new TextItem();
		family_nameItem.setTitle(CallCenterBK.constants.lastName());
		family_nameItem.setWidth(250);
		family_nameItem.setName("family_nameItem");

		phoneItem = new TextItem();
		phoneItem.setTitle(CallCenterBK.constants.phone());
		phoneItem.setWidth(250);
		phoneItem.setName("phone");

		streetItem = new TextItem();
		streetItem.setTitle(CallCenterBK.constants.street());
		streetItem.setName("street_name_geo");
		streetItem.setWidth(250);
		Map<String, Object> aditionalCriteria = new TreeMap<String, Object>();

		citiesItem = new ComboBoxItem();
		citiesItem.setTitle(CallCenterBK.constants.town());
		citiesItem.setName("city_name_geo");
		citiesItem.setWidth(250);
		ClientUtils.fillCombo(citiesItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name",
				aditionalCriteria);

		regionItem = new ComboBoxItem();
		regionItem.setTitle(CallCenterBK.constants.cityRegion());
		regionItem.setName("city_region_name_geo");
		regionItem.setWidth(250);
		regionItem.setFetchMissingValues(true);
		regionItem.setFilterLocally(false);
		regionItem.setAddUnknownValues(false);

		aditionalCriteria.put("town_id", Constants.defCityTbilisiId.toString());

		ClientUtils.fillCombo(regionItem, "TownDistrictDS",
				"searchCityRegsFromDBForCombos", "town_district_id",
				"town_district_name", aditionalCriteria);

		searchForm.setFields(family_nameItem, nameItem, phoneItem, streetItem,
				citiesItem, regionItem);

		ClientUtils.makeDependancy(citiesItem, "town_id", regionItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid();

		listGrid.setWidth(1000);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(datasource);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("customSearch");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setCanSelectText(true);
		listGrid.setCanDragSelectText(true);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);

		ListGridField name = new ListGridField("name",
				CallCenterBK.constants.name(), 100);
		name.setCanFilter(true);

		ListGridField family_name = new ListGridField("family_name",
				CallCenterBK.constants.lastName(), 150);
		family_name.setCanFilter(true);

		ListGridField city = new ListGridField("town_name",
				CallCenterBK.constants.town(), 100);
		city.setCanFilter(false);

		ListGridField address = new ListGridField("concat_address",
				CallCenterBK.constants.address());
		address.setCanFilter(true);

		ListGridField phone = new ListGridField("shown_phones",
				CallCenterBK.constants.phone(), 100);
		phone.setCanFilter(true);
		ListGridField phoneStateField = new ListGridField("phone_state",
				"მდგომარეობა", 90);
		ListGridField phoneStatusField = new ListGridField(
				"phone_contract_type_desc", CallCenterBK.constants.status(),
				120);

		name.setAlign(Alignment.LEFT);
		family_name.setAlign(Alignment.LEFT);
		city.setAlign(Alignment.LEFT);
		address.setAlign(Alignment.LEFT);
		phone.setAlign(Alignment.CENTER);

		listGrid.setFields(family_name, name, city, address, phone,
				phoneStateField, phoneStatusField);
		mainLayout.addMember(listGrid);

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nameItem.clearValue();
				family_nameItem.clearValue();
				phoneItem.clearValue();
				streetItem.clearValue();
				citiesItem.clearValue();
				regionItem.clearValue();
			}
		});

		findButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search();
			}
		});
		nameItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		KeyPressHandler kp = new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}

			}
		};
		family_nameItem.addKeyPressHandler(kp);
		phoneItem.addKeyPressHandler(kp);
		streetItem.addKeyPressHandler(kp);

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewSubscriber dlgViewAbonent = new DlgViewSubscriber(
						listGrid, datasource, listGrid.getSelectedRecord());
				dlgViewAbonent.show();
			}
		});

		setPane(mainLayout);
	}

	private void search() {
		try {
			String name = nameItem.getValueAsString();
			String family_name = family_nameItem.getValueAsString();
			String phone = phoneItem.getValueAsString();
			String street_id_str = streetItem.getValueAsString();
			String town_id_str = citiesItem.getValueAsString();
			String city_region_id_str = regionItem.getValueAsString();

			if ((name == null || name.trim().equals(""))
					&& (family_name == null || family_name.trim().equals(""))
					&& (phone == null || phone.trim().equals(""))
					&& (street_id_str == null || street_id_str.trim()
							.equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.enterAbonentSearchParam());
				return;
			}
			Criteria criteria = new Criteria();
			if (name != null && !name.trim().equals("")) {
				criteria.setAttribute("name_param", name);
			}
			if (family_name != null && !family_name.trim().equals("")) {
				criteria.setAttribute("family_name_param", family_name);
			}
			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone", phone);
			}
			if (street_id_str != null && !street_id_str.trim().equals("")) {
				String tmp = street_id_str.trim();
				criteria.setAttribute("street_name_param", tmp);
			}
			if (town_id_str != null && !town_id_str.trim().equals("")) {
				criteria.setAttribute("town_id", new Integer(town_id_str));
			}
			if (city_region_id_str != null
					&& !city_region_id_str.trim().equals("")) {
				criteria.setAttribute("town_district_id", new Integer(
						city_region_id_str));
			}
			criteria.setAttribute("for_call_center", 1);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "customSearch");
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
