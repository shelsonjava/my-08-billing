package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditBillingComps;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgGetBillingCompsBillByDay;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgGetBillingCompsBillByMonth;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabBillingComps extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem billingCompNameItem;
	private TextItem phoneIndexItem;
	private SelectItem hasCalcItem;

	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	private ToolStripButton billingCompBillByDayBtn;
	private ToolStripButton billingCompBillByMonthBtn;

	private ListGrid billingCompsGrid;
	private DataSource billingCompsDS;

	public TabBillingComps() {
		try {

			setTitle(CallCenterBK.constants.billingComps());
			setCanClose(true);

			billingCompsDS = DataSource.get("BillingCompsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(830);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			billingCompNameItem = new TextItem();
			billingCompNameItem.setTitle(CallCenterBK.constants.companyName());
			billingCompNameItem.setWidth(250);
			billingCompNameItem.setName("billingCompNameItem");

			phoneIndexItem = new TextItem();
			phoneIndexItem.setTitle(CallCenterBK.constants.index());
			phoneIndexItem.setWidth(250);
			phoneIndexItem.setName("phoneIndexItem");

			hasCalcItem = new SelectItem();
			hasCalcItem.setTitle(CallCenterBK.constants.hasCalculation());
			hasCalcItem.setWidth(250);
			hasCalcItem.setName("hasCalcItem");
			hasCalcItem.setDefaultToFirstOption(true);
			hasCalcItem.setValueMap(ClientMapUtil.getInstance()
					.getHasCalculations());

			searchForm.setFields(billingCompNameItem, phoneIndexItem,
					hasCalcItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(830);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			billingCompBillByDayBtn = new ToolStripButton(
					CallCenterBK.constants.telCombBillByDay(), "billing.png");
			billingCompBillByDayBtn.setLayoutAlign(Alignment.LEFT);
			billingCompBillByDayBtn.setWidth(50);
			toolStrip.addButton(billingCompBillByDayBtn);

			billingCompBillByMonthBtn = new ToolStripButton(
					CallCenterBK.constants.telCombBillByMonth(), "billing.png");
			billingCompBillByMonthBtn.setLayoutAlign(Alignment.LEFT);
			billingCompBillByMonthBtn.setWidth(50);
			toolStrip.addButton(billingCompBillByMonthBtn);

			billingCompsGrid = new ListGrid();

			billingCompsGrid.setWidth100();
			billingCompsGrid.setHeight100();
			billingCompsGrid.setAlternateRecordStyles(true);
			billingCompsGrid.setDataSource(billingCompsDS);
			billingCompsGrid.setAutoFetchData(false);
			billingCompsGrid.setShowFilterEditor(false);
			billingCompsGrid.setCanEdit(false);
			billingCompsGrid.setCanRemoveRecords(false);
			billingCompsGrid.setFetchOperation("searchAllBillingComps");
			billingCompsGrid.setShowRowNumbers(true);
			billingCompsGrid.setCanHover(true);
			billingCompsGrid.setShowHover(true);
			billingCompsGrid.setShowHoverComponents(true);
			billingCompsGrid.setWrapCells(true);
			billingCompsGrid.setFixedRecordHeights(false);
			billingCompsGrid.setCanDragSelectText(true);

			ListGridField billing_company_name = new ListGridField(
					"billing_company_name",
					CallCenterBK.constants.companyName());

			ListGridField our_percent = new ListGridField("our_percent",
					CallCenterBK.constants.ourPercent(), 150);

			ListGridField has_calculation_descr = new ListGridField(
					"has_calculation_descr",
					CallCenterBK.constants.hasCalculation(), 150);

			ListGridField call_price = new ListGridField("call_price",
					CallCenterBK.constants.callPrice(), 150);

			our_percent.setAlign(Alignment.CENTER);
			has_calculation_descr.setAlign(Alignment.CENTER);
			call_price.setAlign(Alignment.CENTER);

			billingCompsGrid.setFields(billing_company_name, our_percent,
					has_calculation_descr, call_price);

			mainLayout.addMember(billingCompsGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			billingCompNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			phoneIndexItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					billingCompNameItem.clearValue();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditBillingComps dlgAddEditBillingComp = new DlgAddEditBillingComps(
							billingCompsGrid, null);
					dlgAddEditBillingComp.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = billingCompsGrid
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditBillingComps dlgAddEditBillingComp = new DlgAddEditBillingComps(
							billingCompsGrid, listGridRecord);
					dlgAddEditBillingComp.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = billingCompsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(listGridRecord);
									}
								}
							});
				}
			});

			billingCompsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = billingCompsGrid
									.getSelectedRecord();
							DlgAddEditBillingComps dlgAddEditBillingComp = new DlgAddEditBillingComps(
									billingCompsGrid, listGridRecord);
							dlgAddEditBillingComp.show();
						}
					});

			billingCompBillByDayBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					final ListGridRecord listGridRecord = billingCompsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer billing_company_id = listGridRecord
							.getAttributeAsInt("billing_company_id");
					getBillingCompBillByDay(billing_company_id);
				}
			});
			billingCompBillByMonthBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					final ListGridRecord listGridRecord = billingCompsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer billing_company_id = listGridRecord
							.getAttributeAsInt("billing_company_id");
					getBillingCompBillByMonth(billing_company_id);
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getBillingCompBillByDay(Integer billing_company_id) {
		try {
			DlgGetBillingCompsBillByDay dlgGetBillingCompBillByDay = new DlgGetBillingCompsBillByDay(
					billing_company_id);
			dlgGetBillingCompBillByDay.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getBillingCompBillByMonth(Integer billing_company_id) {
		try {
			DlgGetBillingCompsBillByMonth dlgGetBillingCompBillByMonth = new DlgGetBillingCompsBillByMonth(
					billing_company_id);
			dlgGetBillingCompBillByMonth.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String billing_company_name = billingCompNameItem
					.getValueAsString();
			criteria.setAttribute("billing_company_name", billing_company_name);

			String phoneIndex = phoneIndexItem.getValueAsString();

			if (phoneIndex != null && !phoneIndex.equals("")) {

				try {
					new Long(phoneIndex);
				} catch (Exception e) {
					SC.say(CallCenterBK.constants.invalidPhone());
					return;
				}

				criteria.setAttribute("phoneIndex", new Integer(phoneIndex));
			}

			String has_calculation_str = hasCalcItem.getValueAsString();
			if (has_calculation_str != null
					&& !has_calculation_str.equals("-1")) {
				criteria.setAttribute("has_calculation",
						Integer.parseInt(has_calculation_str));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllBillingComps");
			billingCompsGrid.invalidateCache();
			billingCompsGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void delete(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();

			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("billing_company_id",
					listGridRecord.getAttributeAsInt("billing_company_id"));

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "removeBillingComp");

			billingCompsGrid.removeData(record, new DSCallback() {
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
