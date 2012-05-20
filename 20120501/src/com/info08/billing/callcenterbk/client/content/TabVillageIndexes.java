package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditVillageIndexes;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class TabVillageIndexes extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem districtIndexesItem;
	private TextItem villageIndexNameItem;
	private TextItem villageIndexItem;
	private ComboBoxItem districtCenterItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabVillageIndexes() {
		try {
			setTitle("ინდექსების მართვა");
			setCanClose(true);

			datasource = DataSource.get("VillageIndexesDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			districtIndexesItem = new ComboBoxItem();
			districtIndexesItem.setTitle("რაიონი");
			districtIndexesItem.setWidth(350);
			districtIndexesItem.setName("districtIndexesItem");
			districtIndexesItem.setFetchMissingValues(true);
			districtIndexesItem.setFilterLocally(false);

			villageIndexNameItem = new TextItem();
			villageIndexNameItem.setTitle("დასახელება");
			villageIndexNameItem.setWidth(350);
			villageIndexNameItem.setName("villageIndexNameItem");

			villageIndexItem = new TextItem();
			villageIndexItem.setTitle("ინდექსი");
			villageIndexItem.setWidth(350);
			villageIndexItem.setName("villageIndexItem");

			districtCenterItem = new ComboBoxItem();
			districtCenterItem.setTitle("ტიპი");
			districtCenterItem.setWidth(350);
			districtCenterItem.setName("districtCenterItem");
			districtCenterItem.setValueMap(ClientMapUtil.getInstance()
					.getRaionCentTypes());
			districtCenterItem.setDefaultToFirstOption(true);

			searchForm.setFields(districtIndexesItem, villageIndexNameItem,
					villageIndexItem, districtCenterItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(880);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "person_add.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton("გაუქმება", "person_delete.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			listGrid.setWidth(880);
			listGrid.setHeight(300);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("district_index_name").setTitle("რეგიონი");
			datasource.getField("village_index_name").setTitle("დასახელება");
			datasource.getField("district_center_descr").setTitle("ტიპი");
			datasource.getField("village_index").setTitle("ინდექსი");

			ListGridField district_index_name = new ListGridField(
					"district_index_name", "რეგიონი", 150);
			ListGridField village_index_name = new ListGridField(
					"village_index_name", "დასახელება", 150);
			ListGridField district_center_descr = new ListGridField(
					"district_center_descr", "ტიპი", 100);
			ListGridField village_index = new ListGridField("village_index",
					"ინდექსი", 100);

			village_index.setAlign(Alignment.CENTER);
			district_center_descr.setAlign(Alignment.CENTER);

			listGrid.setFields(district_index_name, village_index_name,
					district_center_descr, village_index);

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
					villageIndexNameItem.clearValue();
					villageIndexItem.clearValue();
					districtCenterItem.clearValue();
					districtIndexesItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditVillageIndexes dlgAddEditVillageIndexes = new DlgAddEditVillageIndexes(
							listGrid, null);
					dlgAddEditVillageIndexes.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditVillageIndexes dlgAddEditVillageIndexes = new DlgAddEditVillageIndexes(
							listGrid, listGridRecord);
					dlgAddEditVillageIndexes.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					final Integer village_index_id = listGridRecord
							.getAttributeAsInt("village_index_id");
					if (village_index_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(village_index_id);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(880);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(860);
			tabDetViewer.setPane(detailViewer);

			listGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(listGrid);
				}
			});
			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) { //TODO
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditVillageIndexes dlgAddEditVillageIndexes = new DlgAddEditVillageIndexes(
							listGrid, listGridRecord);
					dlgAddEditVillageIndexes.show();
				}
			});
			fillCombos();
			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCombos() {
		try {
			DataSource districtIndexesDS = DataSource.get("DistrictIndexesDS");
			districtIndexesItem
					.setOptionOperationId("searchVillageDistrictIndexes");
			districtIndexesItem.setOptionDataSource(districtIndexesDS);
			districtIndexesItem.setValueField("district_index_id");
			districtIndexesItem.setDisplayField("district_index_name");
			districtIndexesItem.setAutoFetchData(true);
			districtIndexesItem.fetchData(new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String district_index_id = districtIndexesItem.getValueAsString();
			String village_index_name = villageIndexNameItem.getValueAsString();
			String village_index = villageIndexItem.getValueAsString();
			String district_center = districtCenterItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("district_index_id", district_index_id);
			criteria.setAttribute("village_index_name", village_index_name);
			criteria.setAttribute("village_index", village_index);
			criteria.setAttribute("district_center", district_center);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchVillageDistrictIndexes");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void delete(Integer village_index_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("village_index_id", village_index_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteVillageIndexes");
			listGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
