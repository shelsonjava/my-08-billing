package com.info08.billing.callcenter.client.content;

import java.util.LinkedHashMap;

import com.info08.billing.callcenter.client.dialogs.correction.DlgAddEditFirstName;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabAbonentNames extends Tab {

	private VLayout mainLayout;
	private DataSource firstNameDS;
	private DynamicForm searchForm;
	private TextItem firstNameItem;
	private SelectItem statusItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addFirstNameBtn;
	private ToolStripButton editFirstiNameBtn;
	private ToolStripButton deleteFirstNameBtn;
	private ToolStripButton restoreFirstNameBtn;
	private ToolStripButton exportButton;

	public TabAbonentNames() {
		try {
			setTitle("აბონენტების სახელების მართვა");
			setCanClose(true);

			firstNameDS = DataSource.get("FirstNameDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();

			searchForm = new DynamicForm();
			searchForm.setPadding(10);
			searchForm.setAutoFocus(true);
			searchForm.setWidth(540);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			firstNameItem = new TextItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setWidth("100%");
			firstNameItem.setName("firstname");

			statusItem = new SelectItem();
			statusItem.setTitle("სტატუსი");
			statusItem.setType("comboBox");
			statusItem.setName("deleted");

			LinkedHashMap<String, String> mapStatuses = new LinkedHashMap<String, String>();
			mapStatuses.put("0", "აქტიური");
			mapStatuses.put("-1", "გაუქმებული");
			statusItem.setValueMap(mapStatuses);
			statusItem.setDefaultToFirstOption(true);
			statusItem.setWidth("100%");

			searchForm.setFields(firstNameItem, statusItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(540);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);
			buttonLayout.setStyleName("paddingRight");
			mainLayout.addMember(buttonLayout);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(530);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addFirstNameBtn = new ToolStripButton("დამატება", "new.png");
			addFirstNameBtn.setLayoutAlign(Alignment.LEFT);
			addFirstNameBtn.setWidth(50);
			toolStrip.addButton(addFirstNameBtn);

			editFirstiNameBtn = new ToolStripButton("შეცვლა", "edit.png");
			editFirstiNameBtn.setLayoutAlign(Alignment.LEFT);
			editFirstiNameBtn.setWidth(50);
			toolStrip.addButton(editFirstiNameBtn);

			deleteFirstNameBtn = new ToolStripButton("გაუქმება", "delete.png");
			deleteFirstNameBtn.setLayoutAlign(Alignment.LEFT);
			deleteFirstNameBtn.setWidth(50);
			toolStrip.addButton(deleteFirstNameBtn);

			restoreFirstNameBtn = new ToolStripButton("აღდგენა", "restore.png");
			restoreFirstNameBtn.setLayoutAlign(Alignment.LEFT);
			restoreFirstNameBtn.setWidth(50);
			toolStrip.addButton(restoreFirstNameBtn);

			toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

			ListGridField firstName = new ListGridField("firstname", "სახელი",
					250);
			ListGridField rec_date = new ListGridField("rec_date", "თარიღი",
					120);
			ListGridField deletedText = new ListGridField("deletedText",
					"სტატუსი", 110);
			rec_date.setAlign(Alignment.CENTER);
			deletedText.setAlign(Alignment.CENTER);

			final ListGrid firstNamesGrid = new ListGrid();
			firstNamesGrid.setWidth(530);
			firstNamesGrid.setHeight100();
			firstNamesGrid.setAlternateRecordStyles(true);
			firstNamesGrid.setDataSource(firstNameDS);
			firstNamesGrid.setAutoFetchData(false);
			firstNamesGrid.setShowFilterEditor(false);
			firstNamesGrid.setCanEdit(false);
			firstNamesGrid.setCanRemoveRecords(false);
			firstNamesGrid.setFetchOperation("firstNameSearch");
			firstNamesGrid.setFields(firstName, rec_date, deletedText);

			mainLayout.addMember(firstNamesGrid);

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search(firstNamesGrid);
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					firstNamesGrid.clearCriteria();
					Criteria criteria = firstNamesGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);
				}
			});
			firstNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search(firstNamesGrid);
					}
				}
			});

			addFirstNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditFirstName dlgAddEditFirstName = new DlgAddEditFirstName(
							null, "", firstNameDS);
					dlgAddEditFirstName.show();
				}
			});
			editFirstiNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = firstNamesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer firstname_Id = listGridRecord
							.getAttributeAsInt("firstname_Id");
					if (firstname_Id == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}
					String firstiName = listGridRecord
							.getAttributeAsString("firstname");
					DlgAddEditFirstName dlgAddEditFirstName = new DlgAddEditFirstName(
							firstname_Id, firstiName, firstNameDS);
					dlgAddEditFirstName.show();
				}
			});

			deleteFirstNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = firstNamesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer status = listGridRecord
							.getAttributeAsInt("deleted");
					if (status.equals(-1)) {
						SC.say("ჩანაწერი უკვე გაუქმებულია");
						return;
					}

					SC.ask("გაფრთხილება",
							"დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										try {

											com.smartgwt.client.rpc.RPCManager
													.startQueue();
											Record record = new Record();
											record.setAttribute(
													"firstname_Id",
													listGridRecord
															.getAttributeAsInt("firstname_Id"));
											record.setAttribute(
													"loggedUserName",
													CommonSingleton
															.getInstance()
															.getSessionPerson()
															.getUserName());
											record.setAttribute("deleted", -1);

											DSRequest req = new DSRequest();
											req.setAttribute("operationId",
													"updateFirstNameStatus");
											firstNameDS.updateData(record,
													new DSCallback() {
														@Override
														public void execute(
																DSResponse response,
																Object rawData,
																DSRequest request) {
														}
													}, req);
											com.smartgwt.client.rpc.RPCManager
													.sendQueue();

										} catch (Exception e) {
											SC.say(e.toString());
										}
									}
								}
							});
				}
			});
			restoreFirstNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = firstNamesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer status = listGridRecord
							.getAttributeAsInt("deleted");
					if (status.equals(0)) {
						SC.say("ჩანაწერი უკვე აღდგენილია");
						return;
					}
					SC.ask("გაფრთხილება",
							"დარწმუნებული ხართ რომ გნებავთ ჩანაწერის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										try {
											com.smartgwt.client.rpc.RPCManager
													.startQueue();
											Record record = new Record();
											record.setAttribute(
													"firstname_Id",
													listGridRecord
															.getAttributeAsInt("firstname_Id"));
											record.setAttribute(
													"loggedUserName",
													CommonSingleton
															.getInstance()
															.getSessionPerson()
															.getUserName());
											record.setAttribute("deleted", 0);

											DSRequest req = new DSRequest();
											req.setAttribute("operationId",
													"updateFirstNameStatus");
											firstNameDS.updateData(record,
													new DSCallback() {
														@Override
														public void execute(
																DSResponse response,
																Object rawData,
																DSRequest request) {
														}
													}, req);
											com.smartgwt.client.rpc.RPCManager
													.sendQueue();
										} catch (Exception e) {
											SC.say(e.toString());
										}
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
					dsRequestProperties.setOperationId("searchFromDB");
					firstNamesGrid.exportData(dsRequestProperties);
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search(final ListGrid firtNamesGrid) {
		Criteria criteria = firtNamesGrid.getCriteria();
		if (criteria == null) {
			criteria = new Criteria();
		}
		Criteria formCriteria = searchForm.getValuesAsCriteria();
		criteria.addCriteria(formCriteria);

		firtNamesGrid.invalidateCache();
		DSRequest dsRequest = new DSRequest();
		dsRequest.setOperationId("searchFromDB");
		firtNamesGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {

			}
		}, dsRequest);
	}
}
