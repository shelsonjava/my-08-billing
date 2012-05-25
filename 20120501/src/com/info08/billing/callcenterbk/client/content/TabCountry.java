package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditCountry;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabCountry extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem countryNameItem;
	private TextItem phoneCodeItem;
	private ComboBoxItem continentItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
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

			countryNameItem = new TextItem();
			countryNameItem.setTitle("დასახელება");
			countryNameItem.setWidth(250);
			countryNameItem.setName("country_name");

			phoneCodeItem = new TextItem();
			phoneCodeItem.setTitle("ქვეყნის კოდი");
			phoneCodeItem.setWidth(250);
			phoneCodeItem.setName("phone_code");

			continentItem = new ComboBoxItem();
			continentItem.setTitle("კონტინენტი");
			continentItem.setWidth(250);
			continentItem.setName("continent_id");
			final DataSource continentDS = DataSource.get("ContinentDS");
			continentItem.setOptionOperationId("continentSearch");
			continentItem.setAutoFetchData(true);
			continentItem.setOptionDataSource(continentDS);
			continentItem.setValueField("id");
			continentItem.setDisplayField("name_descr");

			searchForm.setFields(countryNameItem, phoneCodeItem, continentItem);

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

			listGrid.setWidth(835);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasoutce);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("fetchAllCountriesFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasoutce.getField("country_name").setTitle("დასახელება");
			datasoutce.getField("phone_code").setTitle("კოდი");
			datasoutce.getField("continent").setTitle("კონტინენტი");

			ListGridField country_name = new ListGridField("country_name",
					"დასახელება", 250);
			ListGridField phone_code = new ListGridField("phone_code", "კოდი",
					100);
			ListGridField continent = new ListGridField("continent",
					"კონტინენტი", 150);

			listGrid.setFields(country_name, phone_code, continent);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Criteria criteria = listGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					// Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.setAttribute("country_name",
							countryNameItem.getValueAsString());
					criteria.setAttribute("phone_code",
							phoneCodeItem.getValueAsString());
					if (continentItem.getValueAsString() != null) {
						criteria.setAttribute("continent_id", new Integer(
								continentItem.getValueAsString()));
					}

					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId",
							"fetchAllCountriesFromDB");
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
							"fetchAllCountriesFromDB");
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
							listGrid, null, continentDS);
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
							listGrid, listGridRecord, continentDS);
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
										delete(country_id);
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
							listGrid, listGridRecord, continentDS);
					addEditUserNew.show();
				}
			});

			setPane(mainLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void delete(Integer country_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("country_id", country_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteCountry");
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
