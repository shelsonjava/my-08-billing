package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgAddEditTreatments;
import com.info08.billing.callcenterbk.client.dialogs.correction.DlgAddEditSubscriber;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ISaveResult;
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
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
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

public class TabUnknownPhones extends Tab implements ISaveResult {

	private VLayout mainLayout;
	private DataSource UnknownNumbersDS;
	private DynamicForm searchForm;

	private TextItem phoneNumberItem;

	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton bindPhoneNumberBtn;
	private ToolStripButton deleteFirstNameBtn;
	private ToolStripButton exportButton;

	private ListGrid unknownPhonesGrid;
	private ListGridRecord unknownPhonesGridRecord;

	public TabUnknownPhones() {
		try {
			setTitle("უცნობი ნომრების მართვა");
			setCanClose(true);

			UnknownNumbersDS = DataSource.get("UnknownNumbersDS");

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

			phoneNumberItem = new TextItem();
			phoneNumberItem.setTitle("ნომერი");
			phoneNumberItem.setWidth(250);
			phoneNumberItem.setName("phone_number");

			searchForm.setFields(phoneNumberItem);

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

			bindPhoneNumberBtn = new ToolStripButton("შეცვლა", "edit.png");
			bindPhoneNumberBtn.setLayoutAlign(Alignment.LEFT);
			bindPhoneNumberBtn.setWidth(50);
			toolStrip.addButton(bindPhoneNumberBtn);

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

			unknownPhonesGrid = new ListGrid();
			unknownPhonesGrid.setWidth(530);
			unknownPhonesGrid.setHeight100();
			unknownPhonesGrid.setAlternateRecordStyles(true);
			unknownPhonesGrid.setDataSource(UnknownNumbersDS);
			unknownPhonesGrid.setAutoFetchData(false);
			unknownPhonesGrid.setShowFilterEditor(false);
			unknownPhonesGrid.setCanEdit(false);
			unknownPhonesGrid.setCanRemoveRecords(false);
			unknownPhonesGrid.setFetchOperation("searchFromDB");

			ListGridField phone_number = new ListGridField("phone_number",
					"ნომერი");

			unknownPhonesGrid.setFields(phone_number);

			mainLayout.addMember(unknownPhonesGrid);

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					unknownPhonesGrid.clearCriteria();
				}
			});

			KeyPressHandler keyPressHandler = new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			};

			phoneNumberItem.addKeyPressHandler(keyPressHandler);

			bindPhoneNumberBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = unknownPhonesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}

					unknownPhonesGridRecord = listGridRecord;

					final Integer phone_number_id = listGridRecord
							.getAttributeAsInt("phone_number_id");
					if (phone_number_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					String phone_number = listGridRecord
							.getAttributeAsString("phone_number");
					if (phone_number == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}

					DlgAddEditSubscriber dlg = new DlgAddEditSubscriber(null,
							null, phone_number, TabUnknownPhones.this);
					dlg.setCanDrag(true);
					dlg.setCanDragReposition(true);
				}
			});

			unknownPhonesGrid.addDoubleClickHandler(new DoubleClickHandler() {

				@Override
				public void onDoubleClick(DoubleClickEvent event) {

					ListGridRecord listGridRecord = unknownPhonesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer phone_number_id = listGridRecord
							.getAttributeAsInt("phone_number_id");
					if (phone_number_id == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}

					DlgAddEditTreatments dlgAddEditTreatments = new DlgAddEditTreatments(
							unknownPhonesGrid, listGridRecord);
					dlgAddEditTreatments.show();
				}
			});

			deleteFirstNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = unknownPhonesGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					final Integer phone_number_id = listGridRecord
							.getAttributeAsInt("phone_number_id");
					if (phone_number_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(phone_number_id);
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
					unknownPhonesGrid.exportData(dsRequestProperties);
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		Criteria criteria = unknownPhonesGrid.getCriteria();
		if (criteria == null) {
			criteria = new Criteria();
		}
		Criteria formCriteria = searchForm.getValuesAsCriteria();
		criteria.addCriteria(formCriteria);

		unknownPhonesGrid.invalidateCache();
		DSRequest dsRequest = new DSRequest();
		dsRequest.setOperationId("searchFromDB");
		unknownPhonesGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {

			}
		}, dsRequest);
	}

	private void delete(Integer phone_number_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("phone_number_id", phone_number_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeUnknownNumber");
			unknownPhonesGrid.removeData(record, new DSCallback() {
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

	@Override
	public void saved(Record record, Class<?> clazz) {
		if (record != null) {
			delete(unknownPhonesGridRecord.getAttributeAsInt("phone_number_id"));
		}

	}
}
