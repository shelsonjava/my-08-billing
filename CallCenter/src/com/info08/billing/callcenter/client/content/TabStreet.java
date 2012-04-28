package com.info08.billing.callcenter.client.content;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.address.DlgAddEditStreet;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.Constants;
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
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
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

public class TabStreet extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem streetNameGeoItem;
	private TextItem streetLocationItem;
	private ComboBoxItem recordTypeItem;
	private ComboBoxItem citiesItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton restoreBtn;
	private ToolStripButton exportButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabStreet() {
		try {
			setTitle("ქუჩების მართვა");
			setCanClose(true);

			datasource = DataSource.get("StreetsNewDS");

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

			streetNameGeoItem = new TextItem();
			streetNameGeoItem.setTitle("ქუჩის დასახელება");
			streetNameGeoItem.setWidth(300);
			streetNameGeoItem.setName("street_name_geo");

			streetLocationItem = new TextItem();
			streetLocationItem.setTitle("ქუჩის აღწერა");
			streetLocationItem.setWidth(300);
			streetLocationItem.setName("street_location_geo");

			recordTypeItem = new ComboBoxItem();
			recordTypeItem.setTitle("ჩანაწერის ტიპი");
			recordTypeItem.setWidth(300);
			recordTypeItem.setName("record_type");
			recordTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getStreetRecTypes());

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle("ქალაქი");
			citiesItem.setName("city_name_geo");
			citiesItem.setWidth(300);
			citiesItem.setFetchMissingValues(true);
			citiesItem.setFilterLocally(false);
			citiesItem.setAddUnknownValues(false);

			citiesItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = citiesItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("city_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("city_id", nullO);
						}
					}
				}
			});

			searchForm.setFields(streetNameGeoItem, streetLocationItem,
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

			restoreBtn = new ToolStripButton("აღდგენა", "person_add.png");
			restoreBtn.setLayoutAlign(Alignment.LEFT);
			restoreBtn.setWidth(50);
			toolStrip.addButton(restoreBtn);

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
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
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

			datasource.getField("street_name_geo").setTitle(
					"ძველი დასახელება (ქართ.)");
			datasource.getField("street_location_geo").setTitle(
					"ქუჩის აღწერა (ქართ.)");
			datasource.getField("city_region_name_geo").setTitle("რაიონი");
			datasource.getField("city_name_geo").setTitle("ქალაქი");
			datasource.getField("rec_user").setTitle("შემქმნელი");
			datasource.getField("rec_date").setTitle("თარიღი");
			datasource.getField("upd_user").setTitle("ვინ განაახლა");

			ListGridField street_name_geo = new ListGridField(
					"street_name_geo", "დასახელება (ქართულად)", 250);

			ListGridField oldName = new ListGridField("oldName",
					CallCenter.constants.oldStreetName(), 250);
			oldName.setAlign(Alignment.LEFT);

			ListGridField street_location_geo = new ListGridField(
					"street_location_geo", "ქუჩის აღწერა (ქართ.)");
			ListGridField city_name_geo = new ListGridField("city_name_geo",
					"ქალაქი", 120);
			ListGridField rec_user = new ListGridField("rec_user", "შემქმნელი",
					90);
			ListGridField rec_date = new ListGridField("rec_date", "თარიღი",
					120);
			ListGridField upd_user = new ListGridField("upd_user",
					"ვინ განაახლა", 100);

			city_name_geo.setAlign(Alignment.CENTER);
			rec_user.setAlign(Alignment.CENTER);
			rec_date.setAlign(Alignment.CENTER);
			upd_user.setAlign(Alignment.CENTER);

			listGrid.setFields(street_name_geo, oldName, street_location_geo,
					city_name_geo, rec_user, rec_date, upd_user);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			streetNameGeoItem.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			streetLocationItem.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					streetLocationItem.clearValue();
					streetNameGeoItem.clearValue();
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
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say("ჩანაწერი უკვე გაუქმებულია !");
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
										changeStatus(street_id, 1);
									}
								}
							});
				}
			});
			restoreBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted.equals(0)) {
						SC.say("ჩანაწერი უკვე აღდგენილია !");
						return;
					}
					final Integer street_id = listGridRecord
							.getAttributeAsInt("street_id");
					if (street_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(street_id, 0);
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
					dsRequestProperties
							.setOperationId("fetchAllCountriesFromDB");
					listGrid.exportData(dsRequestProperties);
				}
			});
			TabSet tabSet = new TabSet();
			tabSet.setWidth100();
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(800);
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

					DlgAddEditStreet dlgAddEditStreet = new DlgAddEditStreet(
							listGrid, listGridRecord);
					dlgAddEditStreet.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
			fillFields();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillFields() {
		try {
			DataSource citiesDS = DataSource.get("CityDS");
			citiesItem.setOptionOperationId("searchCitiesFromDBForCombosAll");
			citiesItem.setOptionDataSource(citiesDS);
			citiesItem.setValueField("city_id");
			citiesItem.setDisplayField("city_name_geo");

			Criteria criteria = new Criteria();
			// criteria.setAttribute("country_id", 194);
			citiesItem.setOptionCriteria(criteria);
			citiesItem.setAutoFetchData(false);
			citiesItem.setValue(Constants.defCityTbilisiId);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String street_name_geo = streetNameGeoItem.getValueAsString();
			String street_location_geo = streetLocationItem.getValueAsString();
			String record_type = recordTypeItem.getValueAsString();
			String city_id = citiesItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("street_name_geo", street_name_geo);
			criteria.setAttribute("street_location_geo", street_location_geo);
			if (record_type != null) {
				criteria.setAttribute("record_type", new Integer(record_type));
			}
			if (city_id != null) {
				criteria.setAttribute("city_id", new Integer(city_id));
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

	private void changeStatus(Integer street_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("street_id", street_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateStreetEntStatus");
			datasource.updateData(record, new DSCallback() {
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
