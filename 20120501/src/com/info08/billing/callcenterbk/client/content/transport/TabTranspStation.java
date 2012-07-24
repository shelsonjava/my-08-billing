package com.info08.billing.callcenterbk.client.content.transport;

import com.info08.billing.callcenterbk.client.dialogs.transport.DlgAddEditTranspStation;
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
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
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

public class TabTranspStation extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem transpStatGeoItem;
	private ComboBoxItem transportTypeItem;
	private ComboBoxItem cityItem;

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

	public TabTranspStation() {
		try {
			setTitle("სატრანსპორტო სადგურების მართვა");
			setCanClose(true);

			datasource = DataSource.get("TranspStatDS");

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

			transpStatGeoItem = new TextItem();
			transpStatGeoItem.setTitle("დასახელება(ქართ.)");
			transpStatGeoItem.setWidth(350);
			transpStatGeoItem.setName("transpStatGeoItem");

			transportTypeItem = new ComboBoxItem();
			transportTypeItem.setTitle("ტრანსპორტის ტიპი");
			transportTypeItem.setWidth(350);
			transportTypeItem.setName("name_descr");
			transportTypeItem.setFetchMissingValues(true);
			transportTypeItem.setFilterLocally(false);
			transportTypeItem.setAddUnknownValues(false);

			DataSource TranspTypeDS = DataSource.get("TranspTypeDS");
			transportTypeItem
					.setOptionOperationId("searchAllTransportTypesForCombos");
			transportTypeItem.setOptionDataSource(TranspTypeDS);
			transportTypeItem.setValueField("transp_type_id");
			transportTypeItem.setDisplayField("name_descr");

			Criteria criteria = new Criteria();
			transportTypeItem.setOptionCriteria(criteria);
			transportTypeItem.setAutoFetchData(false);

			transportTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transportTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transp_type_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transp_type_id", nullO);
						}
					}
				}
			});

			cityItem = new ComboBoxItem();
			cityItem.setTitle("ქალაქი");
			cityItem.setWidth(350);
			cityItem.setName("town_name");
			cityItem.setFetchMissingValues(true);
			cityItem.setFilterLocally(false);
			cityItem.setAddUnknownValues(false);

			DataSource townsDS = DataSource.get("TownsDS");
			cityItem.setOptionOperationId("searchCitiesFromDBForCombos");
			cityItem.setOptionDataSource(townsDS);
			cityItem.setValueField("town_id");
			cityItem.setDisplayField("town_name");

			Criteria criteriaCity = new Criteria();
			cityItem.setOptionCriteria(criteriaCity);
			cityItem.setAutoFetchData(false);

			cityItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = cityItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("town_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("town_id", nullO);
						}
					}
				}
			});

			searchForm
					.setFields(transpStatGeoItem, transportTypeItem, cityItem);

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

			addBtn = new ToolStripButton("დამატება", "addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton("გაუქმება", "deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth(880);
			listGrid.setHeight(350);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllTransportStations");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("name_descr").setTitle("დასახელება (ქართ.)");
			datasource.getField("transpport_type").setTitle("ტრანსპორტის ტიპი");
			datasource.getField("city_descr").setTitle("ქალაქი");

			ListGridField name_descr = new ListGridField("name_descr",
					"დასახელება (ქართ.)");
			ListGridField transpport_type = new ListGridField(
					"transpport_type", "ტრანსპორტის ტიპი", 250);
			ListGridField city_descr = new ListGridField("city_descr",
					"ქალაქი", 250);

			listGrid.setFields(name_descr, transpport_type, city_descr);

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
					transpStatGeoItem.clearValue();
					transportTypeItem.clearValue();
					cityItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTranspStation dlgAddEditTranspStation = new DlgAddEditTranspStation(
							listGrid, null);
					dlgAddEditTranspStation.show();
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
					DlgAddEditTranspStation dlgAddEditTranspStation = new DlgAddEditTranspStation(
							listGrid, listGridRecord);
					dlgAddEditTranspStation.show();
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
					final Integer transp_stat_id = listGridRecord
							.getAttributeAsInt("transp_stat_id");
					if (transp_stat_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										remove(transp_stat_id);
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
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					DlgAddEditTranspStation dlgAddEditTranspStation = new DlgAddEditTranspStation(
							listGrid, listGridRecord);
					dlgAddEditTranspStation.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String name_descr = transpStatGeoItem.getValueAsString();
			String transp_type_id = transportTypeItem.getValueAsString();
			String town_id = cityItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("name_descr", name_descr);
			if (transp_type_id != null) {
				criteria.setAttribute("transp_type_id", new Integer(
						transp_type_id));
			}
			if (town_id != null) {
				criteria.setAttribute("town_id", new Integer(town_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllTransportStations");
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

	private void remove(Integer transp_stat_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("transp_stat_id", transp_stat_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeTransportStation");
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
