package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditTownDistrict;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabTownDistrict extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem townDistrictNameItem;
	private ComboBoxItem townsItem;

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

	public TabTownDistrict() {
		try {
			setTitle("ქალაქის რაიონების მართვა");
			setCanClose(true);

			datasource = DataSource.get("TownDistrictDS");

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

			townDistrictNameItem = new TextItem();
			townDistrictNameItem.setTitle("დასახელება");
			townDistrictNameItem.setWidth(350);
			townDistrictNameItem.setName("town_district_name");

			townsItem = new ComboBoxItem();
			townsItem.setTitle("ქალაქი");
			townsItem.setName("town_id");
			townsItem.setWidth(350);
			townsItem.setFetchMissingValues(false);

			searchForm.setFields(townDistrictNameItem, townsItem);

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
			toolStrip.setWidth(730);
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

			listGrid.setWidth(730);
			listGrid.setHeight(330);
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

			datasource.getField("town_district_name").setTitle("დასახელება");

			ListGridField town_district_name = new ListGridField(
					"town_district_name", "დასახელება", 300);

			listGrid.setFields(town_district_name);

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
					townDistrictNameItem.clearValue();
					townsItem.setValue(Constants.defCityTbilisiId);
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTownDistrict addEditDlgAddEditCityRegion = new DlgAddEditTownDistrict(
							listGrid, null);
					addEditDlgAddEditCityRegion.show();
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

					DlgAddEditTownDistrict addEditDlgAddEditCityRegion = new DlgAddEditTownDistrict(
							listGrid, listGridRecord);
					addEditDlgAddEditCityRegion.show();
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
					final Integer town_district_id = listGridRecord
							.getAttributeAsInt("town_district_id");
					if (town_district_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(town_district_id);
									}
								}
							});
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditTownDistrict addEditDlgAddEditCityRegion = new DlgAddEditTownDistrict(
							listGrid, listGridRecord);
					addEditDlgAddEditCityRegion.show();
				}
			});

			setPane(mainLayout);
			fillCityCombo();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCityCombo() {
		try {
			DataSource townsDS = DataSource.get("TownsDS");
			if (townsDS != null) {
				townsItem.setOptionOperationId("searchFromDB");
				townsItem.setOptionDataSource(townsDS);
				townsItem.setValueField("town_id");
				townsItem.setDisplayField("town_name");
				townsItem.setAutoFetchData(true);
				townsItem.fetchData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						townsItem.setValue(Constants.defCityTbilisiId);
					}
				});
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String town_district_name = townDistrictNameItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("town_district_name", town_district_name);
			ListGridRecord listGridRecord = townsItem.getSelectedRecord();
			if (listGridRecord != null) {
				criteria.setAttribute("town_id",
						listGridRecord.getAttributeAsString("town_id"));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchFromDB");
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

	private void delete(Integer town_district_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("town_district_id", town_district_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteTownDistrict");
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
