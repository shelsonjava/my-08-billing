package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewStreet;
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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindStreets extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem citiesItem;
	private TextItem streetNameItem;
	private TextItem indexItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource streetsDS;

	public TabFindStreets() {
		setTitle(CallCenterBK.constants.streetsFind());
		setCanClose(true);

		streetsDS = DataSource.get("StreetsNewDS");

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

		citiesItem = new ComboBoxItem();
		citiesItem.setTitle(CallCenterBK.constants.city());
		citiesItem.setName("town_name");
		citiesItem.setWidth(250);
		citiesItem.setFetchMissingValues(true);
		citiesItem.setFilterLocally(false);
		citiesItem.setAddUnknownValues(false);

		DataSource townsDS = DataSource.get("TownsDS");
		citiesItem.setOptionOperationId("searchCitiesFromDBForCombos");
		citiesItem.setOptionDataSource(townsDS);
		citiesItem.setValueField("town_id");
		citiesItem.setDisplayField("town_name");
		Criteria criteria = new Criteria();
		// criteria.setAttribute("country_id", Constants.defCountryGeorgiaId);
		citiesItem.setOptionCriteria(criteria);
		citiesItem.setAutoFetchData(false);

		citiesItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = citiesItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("town_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("town_id", nullO);
					}
				}
			}
		});
		citiesItem.setValue(Constants.defCityTbilisiId);

		streetNameItem = new TextItem();
		streetNameItem.setTitle(CallCenterBK.constants.street());
		streetNameItem.setName("streetNameItem");
		streetNameItem.setWidth(250);

		indexItem = new TextItem();
		indexItem.setTitle(CallCenterBK.constants.index());
		indexItem.setName("indexItem");
		indexItem.setWidth(250);

		searchForm.setFields(citiesItem, streetNameItem, indexItem);
		searchForm.focusInItem(streetNameItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid() {
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				if (colNum == 3) {
					return "color:red;";
				}
				return super.getCellCSSText(record, rowNum, colNum);
			}
		};
		listGrid.setWidth(1030);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(streetsDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchStreetForCallCenter");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField town_name = new ListGridField("town_name",
				CallCenterBK.constants.city(), 120);
		town_name.setAlign(Alignment.LEFT);

		ListGridField street_name = new ListGridField("street_name",
				CallCenterBK.constants.street(), 170);
		street_name.setAlign(Alignment.LEFT);

		ListGridField oldName = new ListGridField("oldName",
				CallCenterBK.constants.oldStreetName(), 170);
		oldName.setAlign(Alignment.LEFT);

		ListGridField streetIndex = new ListGridField("streetIndex",
				CallCenterBK.constants.indexes(), 120);
		streetIndex.setAlign(Alignment.LEFT);

		ListGridField streetDistrict = new ListGridField("streetDistrict",
				CallCenterBK.constants.district(), 120);
		streetDistrict.setAlign(Alignment.LEFT);

		ListGridField street_location_geo = new ListGridField(
				"street_location_geo", CallCenterBK.constants.streetDescr());
		street_location_geo.setAlign(Alignment.LEFT);

		listGrid.setFields(town_name, street_name, oldName,
				streetIndex, streetDistrict, street_location_geo);

		mainLayout.addMember(listGrid);

		findButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search();
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				streetNameItem.clearValue();
				indexItem.clearValue();
			}
		});

		streetNameItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		indexItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewStreet dlgViewStreet = new DlgViewStreet(streetsDS,
						listGrid.getSelectedRecord());
				dlgViewStreet.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String town_id = citiesItem.getValueAsString();
			String streetIndex = indexItem.getValueAsString();
			String street_name = streetNameItem.getValueAsString();

			if ((streetIndex == null || streetIndex.trim().equals(""))
					&& (street_name == null || street_name.trim()
							.equals(""))) {
				SC.say(CallCenterBK.constants.plzEnterStreetNameOrIdx());
				return;
			}

			if (town_id != null && !town_id.trim().equals("")) {
				criteria.setAttribute("town_id", new Integer(town_id));
			}

			if (streetIndex != null && !streetIndex.trim().equals("")) {
				criteria.setAttribute("streetIndex", streetIndex);
			}

			if (street_name != null && !street_name.trim().equals("")) {
				String tmp = street_name.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					criteria.setAttribute("street_name" + i, item);
					i++;
				}
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchStreetForCallCenter");
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
