package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.correction.DlgAddEditFirstName;
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
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addFirstNameBtn;
	private ToolStripButton editFirstiNameBtn;
	private ToolStripButton deleteFirstNameBtn;
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
			searchForm.setNumCols(1);
			mainLayout.addMember(searchForm);

			firstNameItem = new TextItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setWidth(400);
			firstNameItem.setName("firstname");

			searchForm.setFields(firstNameItem);

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

			toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

			ListGridField firstName = new ListGridField("firstname", "სახელი");

			final ListGrid firstNamesGrid = new ListGrid();
			firstNamesGrid.setWidth(530);
			firstNamesGrid.setHeight100();
			firstNamesGrid.setAlternateRecordStyles(true);
			firstNamesGrid.setDataSource(firstNameDS);
			firstNamesGrid.setAutoFetchData(false);
			firstNamesGrid.setShowFilterEditor(false);
			firstNamesGrid.setCanEdit(false);
			firstNamesGrid.setCanRemoveRecords(false);
			firstNamesGrid.setFetchOperation("searchFromDB");
			firstNamesGrid.setFields(firstName);

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
					Integer firstname_id = listGridRecord
							.getAttributeAsInt("firstname_id");
					if (firstname_id == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}
					String firstiName = listGridRecord
							.getAttributeAsString("firstname");
					DlgAddEditFirstName dlgAddEditFirstName = new DlgAddEditFirstName(
							firstname_id, firstiName, firstNameDS);
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
													"firstname_id",
													listGridRecord
															.getAttributeAsInt("firstname_id"));
											record.setAttribute(
													"loggedUserName",
													CommonSingleton
															.getInstance()
															.getSessionPerson()
															.getUser_name());

											DSRequest req = new DSRequest();
											req.setAttribute("operationId",
													"removeFirstName");
											firstNamesGrid.removeData(record,
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
