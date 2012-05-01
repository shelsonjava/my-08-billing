package com.info08.billing.callcenter.client.content;

import com.info08.billing.callcenter.client.dialogs.address.DlgAddEditCountry;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class TabCountry extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem countryNameGeoItem;
	private TextItem countryNameEngItem;
	private TextItem countryCodeItem;
	private ComboBoxItem continentItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton restoreBtn;
	private ToolStripButton exportButton;
	private ListGrid listGrid;
	private DataSource datasoutce;

	public TabCountry() {
		try {
			setTitle("ქვეყნების მართვა");
			setCanClose(true);

			datasoutce = DataSource.get("CountryDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			countryNameGeoItem = new TextItem();
			countryNameGeoItem.setTitle("დასახელება (ქართულად)");
			countryNameGeoItem.setWidth(250);
			countryNameGeoItem.setName("country_name_geo");

			countryNameEngItem = new TextItem();
			countryNameEngItem.setTitle("დასახელება (ინგლისურად)");
			countryNameEngItem.setWidth(250);
			countryNameEngItem.setName("country_name_eng");

			countryCodeItem = new TextItem();
			countryCodeItem.setTitle("ქვეყნის კოდი");
			countryCodeItem.setWidth(250);
			countryCodeItem.setName("country_code");

			continentItem = new ComboBoxItem();
			continentItem.setTitle("კონტინენტი");
			continentItem.setWidth(250);
			continentItem.setName("world_region_id");
			final DataSource worldRegionDS = DataSource.get("WorldRegionDS");
			continentItem.setOptionOperationId("worldRegionsSearch");
			continentItem.setAutoFetchData(true);
			continentItem.setOptionDataSource(worldRegionDS);
			continentItem.setValueField("world_region_id");
			continentItem.setDisplayField("world_region_name_geo");

			searchForm.setFields(countryNameGeoItem, countryNameEngItem,
					countryCodeItem, continentItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(835);
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

			listGrid.setWidth(835);
			listGrid.setHeight(280);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasoutce);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("countriesSearchFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasoutce.getField("country_name_geo").setTitle(
					"დასახელება (ქართ.)");
			datasoutce.getField("country_name_eng").setTitle(
					"დასახელება (ინგლ.)");
			datasoutce.getField("country_code").setTitle("კოდი");
			datasoutce.getField("worldRegion").setTitle("რეგიონი");
			datasoutce.getField("rec_user").setTitle("შემქმნელი");
			datasoutce.getField("rec_date").setTitle("თარიღი");
			datasoutce.getField("upd_user").setTitle("განაახლა");
			datasoutce.getField("upd_date").setTitle("როდის განახლდა");

			ListGridField country_name_geo = new ListGridField(
					"country_name_geo", "დასახელება (ქართ.)", 150);
			ListGridField country_name_eng = new ListGridField(
					"country_name_eng", "დასახელება (ინგლ.)", 150);
			ListGridField country_code = new ListGridField("country_code",
					"კოდი", 60);
			ListGridField worldRegion = new ListGridField("worldRegion",
					"რეგიონი", 100);
			ListGridField rec_user = new ListGridField("rec_user", "შემქმნელი",
					90);
			ListGridField rec_date = new ListGridField("rec_date", "თარიღი",
					120);
			ListGridField upd_user = new ListGridField("upd_user", "განაახლა",
					90);

			rec_user.setCanEdit(false);
			rec_date.setCanEdit(false);
			upd_user.setCanEdit(false);

			rec_date.setAlign(Alignment.CENTER);

			listGrid.setFields(country_name_geo, country_name_eng,
					country_code, worldRegion, rec_user, rec_date, upd_user);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Criteria criteria = listGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);

					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId",
							"countriesSearchFromDB");
					listGrid.invalidateCache();
					listGrid.filterData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId",
							"countriesSearchFromDB");
					listGrid.clearCriteria(new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditCountry addEditUserNew = new DlgAddEditCountry(
							listGrid, null, worldRegionDS);
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

					DlgAddEditCountry addEditUserNew = new DlgAddEditCountry(
							listGrid, listGridRecord, worldRegionDS);
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
					final Integer country_id = listGridRecord
							.getAttributeAsInt("country_id");
					if (country_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(country_id, 1);
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
					final Integer country_id = listGridRecord
							.getAttributeAsInt("country_id");
					if (country_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(country_id, 0);
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
			tabSet.setWidth(835);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasoutce);
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
							listGrid, listGridRecord, worldRegionDS);
					addEditUserNew.show();
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

	private void changeStatus(Integer country_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("country_id", country_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "countryStatusUpdate");
			datasoutce.updateData(record, new DSCallback() {
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
