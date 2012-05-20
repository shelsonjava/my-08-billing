package com.info08.billing.callcenterbk.client.content;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditCity;
import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditCountry;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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

public class TabStreetNames extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem cityNameGeoItem;
	private TextItem cityNameEngItem;
	private TextItem cityCodeItem;
	private ComboBoxItem countryItem;
	private ComboBoxItem cityTypeItem;
	private TextItem ofGMTItem;
	private TextItem ofGMTWinterItem;
	private ComboBoxItem isCapitalItem;
	private TextItem cityNewCodeItem;

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

	public TabStreetNames() {
		try {
			setTitle("ქვეყნების მართვა");
			setCanClose(true);

			datasource = DataSource.get("CityDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(6);
			mainLayout.addMember(searchForm);

			cityNameGeoItem = new TextItem();
			cityNameGeoItem.setTitle("დასახელება(ქართ.)");
			cityNameGeoItem.setWidth(200);
			cityNameGeoItem.setName("city_name_geo");

			cityNameEngItem = new TextItem();
			cityNameEngItem.setTitle("დასახელება(ინგლ.)");
			cityNameEngItem.setWidth(200);
			cityNameEngItem.setName("city_name_eng");

			cityCodeItem = new TextItem();
			cityCodeItem.setTitle("ქალაქის კოდი");
			cityCodeItem.setWidth(200);
			cityCodeItem.setName("city_code");

			final DataSource countryDS = DataSource.get("CountryDS");

			countryItem = new ComboBoxItem();
			countryItem.setTitle("ქვეყანა");
			countryItem.setWidth(200);
			countryItem.setName("country_id");

			cityTypeItem = new ComboBoxItem();
			cityTypeItem.setTitle("ქალაქის ტიპი");
			cityTypeItem.setWidth(200);
			cityTypeItem.setName("city_type_id");

			ofGMTItem = new TextItem();
			ofGMTItem.setTitle("დრო");
			ofGMTItem.setWidth(200);
			ofGMTItem.setName("of_gmt");

			ofGMTWinterItem = new TextItem();
			ofGMTWinterItem.setTitle("ზამთრის დრო");
			ofGMTWinterItem.setWidth(200);
			ofGMTWinterItem.setName("of_gmt_winter");

			cityNewCodeItem = new TextItem();
			cityNewCodeItem.setTitle("ქალაქის კოდი (ახალი)");
			cityNewCodeItem.setWidth(200);
			cityNewCodeItem.setName("city_new_code");

			isCapitalItem = new ComboBoxItem();
			isCapitalItem.setTitle("დედაქალაქი");
			isCapitalItem.setWidth(200);
			isCapitalItem.setName("is_capital");
			isCapitalItem.setValueMap(ClientMapUtil.getInstance()
					.getIsCapital());

			searchForm.setFields(cityNameGeoItem, cityNameEngItem,
					cityTypeItem, countryItem, cityCodeItem, cityNewCodeItem,
					isCapitalItem, ofGMTItem, ofGMTWinterItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
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
			listGrid.setFetchOperation("citiesSearchFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("city_name_geo").setTitle("დასახელება (ქართ.)");
			datasource.getField("city_name_eng").setTitle("დასახელება (ინგლ.)");
			datasource.getField("city_code").setTitle("ქალაქის კოდი");
			datasource.getField("city_new_code")
					.setTitle("ქალაქის კოდი(ახალი)");
			datasource.getField("cityType").setTitle("ქალაქის ტიპი");
			datasource.getField("countryName").setTitle("ქვეყანა");
			datasource.getField("isCapitalText").setTitle("დედაქალაქი");
			datasource.getField("of_gmt").setTitle("დრო");
			datasource.getField("of_gmt_winter").setTitle("ზამთრის დრო");
			datasource.getField("rec_user").setTitle("შემქმნელი");
			datasource.getField("rec_date").setTitle("თარიღი");
			// datasoutce.getField("upd_user").setTitle("განაახლა");
			// datasoutce.getField("upd_date").setTitle("როდის განახლდა");

			ListGridField city_name_geo = new ListGridField("city_name_geo",
					"დასახელება (ქართ.)", 150);
			// ListGridField city_name_eng = new ListGridField("city_name_eng",
			// "დასახელება (ინგლ.)", 150);
			ListGridField city_code = new ListGridField("city_code",
					"ქალაქის კოდი", 100);
			ListGridField city_new_code = new ListGridField("city_new_code",
					"ქალაქის კოდი(ახალი)", 150);
			ListGridField cityType = new ListGridField("cityType",
					"ქალაქის ტიპი", 100);
			ListGridField countryName = new ListGridField("countryName",
					"ქვეყანა", 150);
			ListGridField isCapitalText = new ListGridField("isCapitalText",
					"დედაქალაქი", 100);
			ListGridField of_gmt = new ListGridField("of_gmt", "დრო", 100);
			ListGridField of_gmt_winter = new ListGridField("of_gmt_winter",
					"ზამთრის დრო", 100);
			// ListGridField rec_user = new ListGridField("rec_user",
			// "შემქმნელი",90);
			// ListGridField rec_date = new ListGridField("rec_date",
			// "თარიღი",120);
			// ListGridField upd_user = new ListGridField("upd_user",
			// "განაახლა",90);

			// rec_date.setAlign(Alignment.CENTER);

			listGrid.setFields(city_name_geo, city_code, city_new_code,
					cityType, countryName, isCapitalText, of_gmt, of_gmt_winter);
			// city_name_eng, rec_user, rec_date, upd_user

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchCity();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					cityNameEngItem.clearValue();
					cityNameGeoItem.clearValue();
					cityCodeItem.clearValue();
					cityNewCodeItem.clearValue();
					cityTypeItem.clearValue();
					ofGMTItem.clearValue();
					ofGMTWinterItem.clearValue();
					isCapitalItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditCity addEditUserNew = new DlgAddEditCity(
							listGrid, null);
					addEditUserNew.show();
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

					DlgAddEditCity addEditUserNew = new DlgAddEditCity(
							listGrid, listGridRecord);
					addEditUserNew.show();
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
					final Integer city_id = listGridRecord
							.getAttributeAsInt("city_id");
					if (city_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(city_id, 1);
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
					final Integer city_id = listGridRecord
							.getAttributeAsInt("city_id");
					if (city_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(city_id, 0);
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

					DlgAddEditCountry addEditUserNew = new DlgAddEditCountry(
							listGrid, listGridRecord, countryDS);
					addEditUserNew.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
			fillCombos();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCombos() {
		try {
			final DataSource countryDS = DataSource.get("CountryDS");

			if (countryDS != null) {
				countryItem.setOptionOperationId("countriesSearchCached");
				countryItem.setOptionDataSource(countryDS);
				countryItem.setValueField("country_id");
				countryItem.setDisplayField("country_name");
				countryItem.setAutoFetchData(true);
				Criteria optionCriteria = new Criteria();
				optionCriteria.setAttribute("wtmpId123", "country_id"
						+ HTMLPanel.createUniqueId());
				countryItem.setOptionCriteria(optionCriteria);
				countryItem.fetchData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						countryItem.setValue(194);
					}
				});
			}
			final DataSource cityTypeDS = DataSource.get("CityTypeDS");
			if (cityTypeDS != null) {
				cityTypeItem.setOptionOperationId("cityTypesSearch");
				cityTypeItem.setOptionDataSource(cityTypeDS);
				cityTypeItem.setValueField("city_type_id");
				cityTypeItem.setDisplayField("city_type_geo");
				cityTypeItem.setAutoFetchData(true);
				Criteria optionCriteria = new Criteria();
				optionCriteria.setAttribute("wtmpId234", "city_type_id"
						+ HTMLPanel.createUniqueId());
				cityTypeItem.setOptionCriteria(optionCriteria);
				cityTypeItem.fetchData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void searchCity() {
		try {
			String city_name_geo = cityNameGeoItem.getValueAsString();
			String city_name_eng = cityNameEngItem.getValueAsString();
			String country_id = countryItem.getValueAsString();
			String city_type_id = cityTypeItem.getValueAsString();
			String is_capital = isCapitalItem.getValueAsString();
			String city_code = cityCodeItem.getValueAsString();
			String city_new_code = cityNewCodeItem.getValueAsString();
			String of_gmt = ofGMTItem.getValueAsString();
			String of_gmt_winter = ofGMTWinterItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("city_name_geo", city_name_geo);
			criteria.setAttribute("city_name_eng", city_name_eng);
			criteria.setAttribute("country_id", country_id);
			criteria.setAttribute("city_type_id", city_type_id);
			criteria.setAttribute("is_capital", is_capital);
			criteria.setAttribute("city_code", city_code);
			criteria.setAttribute("city_new_code", city_new_code);
			criteria.setAttribute("of_gmt", of_gmt);
			criteria.setAttribute("of_gmt_winter", of_gmt_winter);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "citiesSearchFromDB");
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

	private void changeStatus(Integer city_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("city_id", city_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "cityStatusUpdate");
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
