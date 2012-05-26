package com.info08.billing.callcenterbk.client.content;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditTown;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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

public class TabTowns extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem cityNameGeoItem;
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
	private ToolStripButton exportButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabTowns() {
		try {
			setTitle("ქალაქების მართვა");
			setCanClose(true);

			datasource = DataSource.get("TownsDS");

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
			cityNameGeoItem.setTitle("დასახელება");
			cityNameGeoItem.setWidth(200);
			cityNameGeoItem.setName("town_name");

			cityCodeItem = new TextItem();
			cityCodeItem.setTitle("ქალაქის კოდი");
			cityCodeItem.setWidth(200);
			cityCodeItem.setName("town_code");

			countryItem = new ComboBoxItem();
			countryItem.setTitle("ქვეყანა");
			countryItem.setWidth(200);
			countryItem.setName("country_id");

			cityTypeItem = new ComboBoxItem();
			cityTypeItem.setTitle("ქალაქის ტიპი");
			cityTypeItem.setWidth(200);
			cityTypeItem.setName("town_type_id");

			ofGMTItem = new TextItem();
			ofGMTItem.setTitle("დრო");
			ofGMTItem.setWidth(200);
			ofGMTItem.setName("normal_gmt");

			ofGMTWinterItem = new TextItem();
			ofGMTWinterItem.setTitle("ზამთრის დრო");
			ofGMTWinterItem.setWidth(200);
			ofGMTWinterItem.setName("winter_gmt");

			cityNewCodeItem = new TextItem();
			cityNewCodeItem.setTitle("ქალაქის კოდი (ახალი)");
			cityNewCodeItem.setWidth(200);
			cityNewCodeItem.setName("town_new_code");

			isCapitalItem = new ComboBoxItem();
			isCapitalItem.setTitle("დედაქალაქი");
			isCapitalItem.setWidth(200);
			isCapitalItem.setName("capital_town");
			isCapitalItem.setValueMap(ClientMapUtil.getInstance()
					.getIsCapital());

			searchForm.setFields(cityNameGeoItem, cityTypeItem, countryItem,
					cityCodeItem, cityNewCodeItem, isCapitalItem, ofGMTItem,
					ofGMTWinterItem);

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
			listGrid.setFetchOperation("citiesSearchFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("town_name").setTitle("დასახელება");
			datasource.getField("town_code").setTitle("ქალაქის კოდი");
			datasource.getField("town_new_code")
					.setTitle("ქალაქის კოდი(ახალი)");
			datasource.getField("town_type_name").setTitle("ქალაქის ტიპი");
			datasource.getField("country_name").setTitle("ქვეყანა");
			datasource.getField("capital_town").setTitle("დედაქალაქი");
			datasource.getField("capital_town_name").setTitle("დედაქალაქი");
			datasource.getField("normal_gmt").setTitle("დრო");
			datasource.getField("winter_gmt").setTitle("ზამთრის დრო");
			// datasoutce.getField("upd_user").setTitle("განაახლა");
			// datasoutce.getField("upd_date").setTitle("როდის განახლდა");

			ListGridField town_name = new ListGridField("town_name",
					"დასახელება ", 150);
			ListGridField town_code = new ListGridField("town_code",
					"ქალაქის კოდი", 100);
			ListGridField town_new_code = new ListGridField("town_new_code",
					"ქალაქის კოდი(ახალი)", 150);
			ListGridField town_type_name = new ListGridField("town_type_name",
					"ქალაქის ტიპი", 100);
			ListGridField countryName = new ListGridField("country_name",
					"ქვეყანა", 150);
			ListGridField isCapitalText = new ListGridField("capital_town_name",
					"დედაქალაქი", 100);
			ListGridField normal_gmt = new ListGridField("normal_gmt", "დრო",
					100);
			ListGridField winter_gmt = new ListGridField("winter_gmt",
					"ზამთრის დრო", 100);


			// rec_date.setAlign(Alignment.CENTER);

			listGrid.setFields(town_name, town_code, town_new_code, town_type_name,
					countryName, isCapitalText, normal_gmt, winter_gmt);
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
					DlgAddEditTown addEditUserNew = new DlgAddEditTown(
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

					DlgAddEditTown addEditUserNew = new DlgAddEditTown(
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
					final Integer town_id = listGridRecord
							.getAttributeAsInt("town_id");
					if (town_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(town_id);
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
							.setOperationId("citiesSearchFromDB");
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

					DlgAddEditTown addEditUserNew = new DlgAddEditTown(
							listGrid, listGridRecord);
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
						countryItem.setValue(Constants.defCountryGeorgiaId);
					}
				});
			}
			final DataSource descriptionsDS = DataSource.get("DescriptionsDS");

			if (descriptionsDS != null) {
				cityTypeItem
						.setOptionOperationId("searchDescriptionsOrderById");
				cityTypeItem.setOptionDataSource(descriptionsDS);
				cityTypeItem.setValueField("description_id");
				cityTypeItem.setDisplayField("description");
				cityTypeItem.setAutoFetchData(true);
				Criteria optionCriteria = new Criteria();
				optionCriteria.setAttribute("description_type_id", "59000");
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
			String town_name = cityNameGeoItem.getValueAsString();
			String country_id = countryItem.getValueAsString();
			String city_type_id = cityTypeItem.getValueAsString();
			String capital_town = isCapitalItem.getValueAsString();
			String town_code = cityCodeItem.getValueAsString();
			String town_new_code = cityNewCodeItem.getValueAsString();
			String normal_gmt = ofGMTItem.getValueAsString();
			String winter_gmt = ofGMTWinterItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("town_name", town_name);
			criteria.setAttribute("country_id", country_id);
			criteria.setAttribute("town_type_id", city_type_id);
			criteria.setAttribute("capital_town", capital_town);
			criteria.setAttribute("town_code", town_code);
			criteria.setAttribute("town_new_code", town_new_code);
			criteria.setAttribute("normal_gmt", normal_gmt);
			criteria.setAttribute("winter_gmt", winter_gmt);

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

	private void delete(Integer town_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("town_id", town_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "townDelete");
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
