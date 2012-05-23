package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCityDist;
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
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindDistBetwCity extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem cityFromItem;
	private ComboBoxItem cityToItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource citiesDistDS;

	public TabFindDistBetwCity() {
		setTitle(CallCenterBK.constants.distBetweenCities());
		setCanClose(true);

		citiesDistDS = DataSource.get("CitiesDistDS");

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

		cityFromItem = new ComboBoxItem();
		cityFromItem.setTitle(CallCenterBK.constants.cityFrom());
		cityFromItem.setName("cityFromItem");
		cityFromItem.setWidth(375);
		cityFromItem.setFetchMissingValues(true);
		cityFromItem.setFilterLocally(false);
		cityFromItem.setAddUnknownValues(false);
		cityFromItem.setCompleteOnTab(true);

		DataSource TownsDS = DataSource.get("TownsDS");
		cityFromItem.setOptionOperationId("searchFromDB");
		cityFromItem.setOptionDataSource(TownsDS);
		cityFromItem.setValueField("town_id");
		cityFromItem.setDisplayField("town_name");
		cityFromItem.setAutoFetchData(false);

		cityFromItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityFromItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("town_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("town_id", nullO);
					}
				}
			}
		});

		cityToItem = new ComboBoxItem();
		cityToItem.setTitle(CallCenterBK.constants.cityTo());
		cityToItem.setName("cityToItem");
		cityToItem.setWidth(375);
		cityToItem.setFetchMissingValues(true);
		cityToItem.setFilterLocally(false);
		cityToItem.setAddUnknownValues(false);
		cityToItem.setCompleteOnTab(true);

		cityToItem.setOptionOperationId("searchFromDB");
		cityToItem.setOptionDataSource(TownsDS);
		cityToItem.setValueField("town_id");
		cityToItem.setDisplayField("town_name");
		cityToItem.setAutoFetchData(false);

		cityToItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityToItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("town_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("town_id", nullO);
					}
				}
			}
		});

		searchForm.setFields(cityFromItem, cityToItem);

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

		listGrid = new ListGrid();
		listGrid.setWidth(900);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(citiesDistDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchFromDBForCC");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField cityStart = new ListGridField("cityStart",
				CallCenterBK.constants.cityFrom(), 140);
		cityStart.setAlign(Alignment.LEFT);
		cityStart.setCanFilter(false);

		ListGridField cityEnd = new ListGridField("cityEnd",
				CallCenterBK.constants.cityTo(), 140);
		cityEnd.setAlign(Alignment.LEFT);
		cityEnd.setCanFilter(false);

		ListGridField cityDistTypeDesc = new ListGridField("cityDistTypeDesc",
				CallCenterBK.constants.type(), 140);
		cityDistTypeDesc.setAlign(Alignment.LEFT);
		cityDistTypeDesc.setCanFilter(false);

		ListGridField city_distance_geo = new ListGridField(
				"city_distance_geo", CallCenterBK.constants.distance(), 180);
		city_distance_geo.setAlign(Alignment.LEFT);
		city_distance_geo.setCanFilter(false);

		ListGridField note_geo = new ListGridField("note_geo",
				CallCenterBK.constants.comment());
		note_geo.setAlign(Alignment.LEFT);
		note_geo.setCanFilter(true);

		listGrid.setFields(cityStart, cityEnd, cityDistTypeDesc,
				city_distance_geo, note_geo);

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
				cityFromItem.clearValue();
				cityToItem.clearValue();
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewCityDist dlgViewTransport = new DlgViewCityDist(
						listGrid, citiesDistDS, listGrid.getSelectedRecord());
				dlgViewTransport.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String town_id_start = cityFromItem.getValueAsString();
			if (town_id_start != null && !town_id_start.trim().equals("")) {
				criteria.setAttribute("town_id_start", new Integer(
						town_id_start));
			}
			String town_id_end = cityToItem.getValueAsString();
			if (town_id_end != null && !town_id_end.trim().equals("")) {
				criteria.setAttribute("town_id_end", new Integer(town_id_end));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchFromDBForCC");
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
