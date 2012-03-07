package com.info08.billing.callcenter.client.content;

import java.util.LinkedHashMap;

import com.info08.billing.callcenter.client.dialogs.correction.DlgAddEditLastName;
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

public class TabAbonentLastNames extends Tab {

	private VLayout mainLayout;
	private DataSource lastNameDS;
	private DynamicForm searchForm;
	private TextItem lastNameItem;
	private SelectItem statusItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addLastNameBtn;
	private ToolStripButton editLastNameBtn;
	private ToolStripButton deleteLastNameBtn;
	private ToolStripButton restoreLastNameBtn;
	private ToolStripButton exportButton;

	public TabAbonentLastNames() {
		try {

			setTitle("აბონენტების გვარების მართვა");
			setCanClose(true);

			lastNameDS = DataSource.get("LastNameDS");			

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

			lastNameItem = new TextItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setWidth("100%");
			lastNameItem.setName("lastname");

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

			searchForm.setFields(lastNameItem, statusItem);

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

			addLastNameBtn = new ToolStripButton("დამატება", "new.png");
			addLastNameBtn.setLayoutAlign(Alignment.LEFT);
			addLastNameBtn.setWidth(50);
			toolStrip.addButton(addLastNameBtn);

			editLastNameBtn = new ToolStripButton("შეცვლა", "edit.png");
			editLastNameBtn.setLayoutAlign(Alignment.LEFT);
			editLastNameBtn.setWidth(50);
			toolStrip.addButton(editLastNameBtn);

			deleteLastNameBtn = new ToolStripButton("გაუქმება", "delete.png");
			deleteLastNameBtn.setLayoutAlign(Alignment.LEFT);
			deleteLastNameBtn.setWidth(50);
			toolStrip.addButton(deleteLastNameBtn);

			restoreLastNameBtn = new ToolStripButton("აღდგენა", "restore.png");
			restoreLastNameBtn.setLayoutAlign(Alignment.LEFT);
			restoreLastNameBtn.setWidth(50);
			toolStrip.addButton(restoreLastNameBtn);

			toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

			ListGridField lastName = new ListGridField("lastname", "სახელი",
					250);
			ListGridField rec_date = new ListGridField("rec_date", "თარიღი",
					120);
			ListGridField deletedText = new ListGridField("deletedText",
					"სტატუსი", 110);
			rec_date.setAlign(Alignment.CENTER);
			deletedText.setAlign(Alignment.CENTER);

			final ListGrid lastNamesGrid = new ListGrid();
			lastNamesGrid.setWidth(530);
			lastNamesGrid.setHeight100();
			lastNamesGrid.setAlternateRecordStyles(true);
			lastNamesGrid.setDataSource(lastNameDS);
			lastNamesGrid.setAutoFetchData(false);
			lastNamesGrid.setShowFilterEditor(false);
			lastNamesGrid.setCanEdit(false);
			lastNamesGrid.setCanRemoveRecords(false);
			lastNamesGrid.setFetchOperation("lastNameSearch");
			lastNamesGrid.setFields(lastName, rec_date, deletedText);

			mainLayout.addMember(lastNamesGrid);

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search(lastNamesGrid);
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					lastNamesGrid.clearCriteria();
					Criteria criteria = lastNamesGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);
				}
			});
			lastNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search(lastNamesGrid);
					}
				}
			});

			addLastNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditLastName dlgAddEditLastName = new DlgAddEditLastName(
							null, "", lastNameDS);
					dlgAddEditLastName.show();
				}
			});
			editLastNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = lastNamesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer lastname_Id = listGridRecord
							.getAttributeAsInt("lastname_Id");
					if (lastname_Id == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}
					String lastName = listGridRecord
							.getAttributeAsString("lastname");
					DlgAddEditLastName dlgAddEditLastName = new DlgAddEditLastName(
							lastname_Id, lastName, lastNameDS);
					dlgAddEditLastName.show();
				}
			});

			deleteLastNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = lastNamesGrid
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
													"lastname_Id",
													listGridRecord
															.getAttributeAsInt("lastname_Id"));
											record.setAttribute(
													"loggedUserName",
													CommonSingleton
															.getInstance()
															.getSessionPerson()
															.getUserName());
											record.setAttribute("deleted", -1);

											DSRequest req = new DSRequest();
											req.setAttribute("operationId",
													"updateLastNameStatus");
											lastNameDS.updateData(record,
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
			restoreLastNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = lastNamesGrid
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
													"lastname_Id",
													listGridRecord
															.getAttributeAsInt("lastname_Id"));
											record.setAttribute(
													"loggedUserName",
													CommonSingleton
															.getInstance()
															.getSessionPerson()
															.getUserName());
											record.setAttribute("deleted", 0);

											DSRequest req = new DSRequest();
											req.setAttribute("operationId",
													"updateLastNameStatus");
											lastNameDS.updateData(record,
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
					dsRequestProperties.setExportAs((ExportFormat) EnumUtil.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("searchFromDB");
					lastNamesGrid.exportData(dsRequestProperties);
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
