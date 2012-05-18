package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewDistrictVillageIndexes;
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

public class TabFindDistrictVillageIndexes extends Tab {

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
	private DataSource villageIndexesDS;

	public TabFindDistrictVillageIndexes() {
		setTitle(CallCenterBK.constants.regIndexes());
		setCanClose(true);

		villageIndexesDS = DataSource.get("VillageIndexesDS");

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
		regCountryNameItem.setTitle(CallCenterBK.constants.regCountry());
		regCountryNameItem.setName("regCountryNameItem");
		regCountryNameItem.setWidth(250);

		indexItem = new TextItem();
		indexItem.setTitle(CallCenterBK.constants.index());
		indexItem.setName("indexItem");
		indexItem.setWidth(250);

		searchForm.setFields(regCountryNameItem, indexItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(500);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer district_center = countryRecord
						.getAttributeAsInt("district_center");
				if (district_center != null && district_center.equals(-1) && colNum == 1) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};
		listGrid.setWidth(500);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(villageIndexesDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchVillageIndexes");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setCanDragSelectText(true);

		ListGridField district_index_name = new ListGridField("district_index_name",
				CallCenterBK.constants.region());
		district_index_name.setAlign(Alignment.LEFT);
		district_index_name.setCanFilter(false);

		ListGridField village_index_name = new ListGridField("village_index_name",
				CallCenterBK.constants.cityCountry());
		village_index_name.setAlign(Alignment.LEFT);
		village_index_name.setCanFilter(true);

		ListGridField village_index = new ListGridField("village_index",
				CallCenterBK.constants.index());
		village_index.setAlign(Alignment.LEFT);
		village_index.setCanFilter(true);

		listGrid.setFields(district_index_name, village_index_name, village_index);

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
				DlgViewDistrictVillageIndexes dlgViewGeoInd = new DlgViewDistrictVillageIndexes(listGrid,
						villageIndexesDS, listGrid.getSelectedRecord());
				dlgViewGeoInd.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

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
					criteria.setAttribute("full_text" + i, item);
					i++;
				}
				
				
			}
			String village_index = indexItem.getValueAsString();
			if (village_index != null && !village_index.trim().equals("")) {
				criteria.setAttribute("village_index", village_index);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchVillageIndexes");
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
