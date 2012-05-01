package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewGeoInd;
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

public class TabFindGeoInd extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private TextItem regCountryNameItem;
	private TextItem indexItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource geoIndCountryDS;

	public TabFindGeoInd() {
		setTitle(CallCenter.constants.regIndexes());
		setCanClose(true);

		geoIndCountryDS = DataSource.get("GeoIndCountryDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(500);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		regCountryNameItem = new TextItem();
		regCountryNameItem.setTitle(CallCenter.constants.regCountry());
		regCountryNameItem.setName("regCountryNameItem");
		regCountryNameItem.setWidth(250);

		indexItem = new TextItem();
		indexItem.setTitle(CallCenter.constants.index());
		indexItem.setName("indexItem");
		indexItem.setWidth(250);

		searchForm.setFields(regCountryNameItem, indexItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(500);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenter.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenter.constants.clear());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer is_center = countryRecord
						.getAttributeAsInt("is_center");
				if (is_center != null && is_center.equals(-1) && colNum == 1) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};
		listGrid.setWidth(500);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(geoIndCountryDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchGeoIndRegCountry");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setCanDragSelectText(true);

		ListGridField regionName = new ListGridField("regionName",
				CallCenter.constants.region());
		regionName.setAlign(Alignment.LEFT);
		regionName.setCanFilter(false);

		ListGridField geo_country_geo = new ListGridField("geo_country_geo",
				CallCenter.constants.cityCountry());
		geo_country_geo.setAlign(Alignment.LEFT);
		geo_country_geo.setCanFilter(true);

		ListGridField geo_index = new ListGridField("geo_index",
				CallCenter.constants.index());
		geo_index.setAlign(Alignment.LEFT);
		geo_index.setCanFilter(true);

		listGrid.setFields(regionName, geo_country_geo, geo_index);

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
				regCountryNameItem.clearValue();
				indexItem.clearValue();
			}
		});

		regCountryNameItem.addKeyPressHandler(new KeyPressHandler() {
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
				DlgViewGeoInd dlgViewGeoInd = new DlgViewGeoInd(listGrid,
						geoIndCountryDS, listGrid.getSelectedRecord());
				dlgViewGeoInd.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String regionName = regCountryNameItem.getValueAsString();
			if (regionName != null && !regionName.trim().equals("")) {
				
				
				String tmp = regionName.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("fullText" + i, item);
					i++;
				}
				
				
			}
			String geo_index = indexItem.getValueAsString();
			if (geo_index != null && !geo_index.trim().equals("")) {
				criteria.setAttribute("geo_index", geo_index);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchGeoIndRegCountry");
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
