package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditStreet;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
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
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabStreet extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem streetNameItem;
	private TextItem streetLocationItem;
	private ComboBoxItem recordTypeItem;
	private ComboBoxItem citiesItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton exportButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabStreet() {
		try {
			setTitle("ქუჩების მართვა");
			setCanClose(true);

			datasource = DataSource.get("StreetsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(1);
			mainLayout.addMember(searchForm);

			streetNameItem = new TextItem();
			streetNameItem.setTitle("ქუჩის დასახელება");
			streetNameItem.setWidth(300);
			streetNameItem.setName("street_name");
			streetNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			streetLocationItem = new TextItem();
			streetLocationItem.setTitle("ქუჩის აღწერა");
			streetLocationItem.setWidth(300);
			streetLocationItem.setName("street_location");

			streetLocationItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			recordTypeItem = new ComboBoxItem();
			recordTypeItem.setTitle("ჩანაწერის ტიპი");
			recordTypeItem.setWidth(300);
			recordTypeItem.setName("record_type");
			recordTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getStreetRecTypes());

			recordTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}

				}
			});

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle("ქალაქი");
			citiesItem.setName("town_name");
			citiesItem.setWidth(300);
			citiesItem.setFetchMissingValues(true);
			citiesItem.setFilterLocally(false);
			citiesItem.setAddUnknownValues(false);

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
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			searchForm.setFields(streetNameItem, streetLocationItem,
					recordTypeItem, citiesItem);

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
			toolStrip.setWidth100();
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

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

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

			listGrid.setWidth100();
			listGrid.setHeight(280);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("fetchStreetsFromDB");
			listGrid.setCanSort(true);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setDataPageSize(75);
			listGrid.setCanDragSelectText(true);

			datasource.getField("street_name").setTitle("ძველი დასახელება");
			datasource.getField("street_location").setTitle("ქუჩის აღწერა");
			datasource.getField("town_district_name").setTitle("რაიონი");
			datasource.getField("town_name").setTitle("ქალაქი");

			ListGridField street_name = new ListGridField("street_name",
					"დასახელება", 250);

			ListGridField street_old_name_descr = new ListGridField(
					"street_old_name_descr",
					CallCenterBK.constants.oldStreetName(), 250);
			street_old_name_descr.setAlign(Alignment.LEFT);

			ListGridField street_location = new ListGridField(
					"street_location", "ქუჩის აღწერა");

			ListGridField town_name = new ListGridField("town_name", "ქალაქი",
					120);

			town_name.setAlign(Alignment.CENTER);

			listGrid.setFields(street_name, street_old_name_descr,
					street_location, town_name);

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
					streetLocationItem.clearValue();
					streetNameItem.clearValue();
					recordTypeItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditStreet dlgAddEditStreet = new DlgAddEditStreet(
							listGrid, null);
					dlgAddEditStreet.show();
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
					DlgAddEditStreet dlgAddEditStreet = new DlgAddEditStreet(
							listGrid, listGridRecord);
					dlgAddEditStreet.show();
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

					final Integer street_id = listGridRecord
							.getAttributeAsInt("street_id");
					if (street_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(street_id);
									}
								}
							});
				}
			});

			exportButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					com.smartgwt.client.data.DSRequest dsRequestProperties = new com.smartgwt.client.data.DSRequest();
					dsRequestProperties.setExportAs((ExportFormat) EnumUtil
							.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties
							.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("fetchStreetsFromDB");
					listGrid.exportData(dsRequestProperties);
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

					DlgAddEditStreet dlgAddEditStreet = new DlgAddEditStreet(
							listGrid, listGridRecord);
					dlgAddEditStreet.show();
				}
			});

			setPane(mainLayout);
			fillFields();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillFields() {
		try {
			// DataSource townsDS = DataSource.get("TownsDS");
			// citiesItem.setOptionOperationId("searchCitiesFromDBForCombosAll");
			// citiesItem.setOptionDataSource(townsDS);
			// citiesItem.setValueField("town_id");
			// citiesItem.setDisplayField("town_name");
			// citiesItem.setAutoFetchData(true);
			// citiesItem.setValue(Constants.defCityTbilisiId);

			ClientUtils.fillCombo(citiesItem, "TownsDS",
					"searchCitiesFromDBForCombosAll", "town_id", "town_name");
			citiesItem.setValue(Constants.defCityTbilisiId);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String street_name = streetNameItem.getValueAsString();
			String street_location = streetLocationItem.getValueAsString();
			String record_type = recordTypeItem.getValueAsString();
			String town_id = citiesItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("street_name", street_name);
			criteria.setAttribute("street_location", street_location);
			if (record_type != null) {
				criteria.setAttribute("record_type", new Integer(record_type));
			}
			if (town_id != null) {
				criteria.setAttribute("town_id", new Integer(town_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "fetchStreetsFromDB");
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

	private void delete(Integer street_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("street_id", street_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteStreet");
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
