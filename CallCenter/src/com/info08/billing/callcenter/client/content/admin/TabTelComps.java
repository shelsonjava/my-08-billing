package com.info08.billing.callcenter.client.content.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.admin.DlgAddEditTelComp;
import com.info08.billing.callcenter.client.dialogs.admin.DlgGetTelCompBillByDay;
import com.info08.billing.callcenter.client.dialogs.admin.DlgGetTelCompBillByMonth;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class TabTelComps extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem telCompNameItem;
	private TextItem phoneIndexItem;

	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	private ToolStripButton telCompBillByDayBtn;
	private ToolStripButton telCompBillByMonthBtn;

	private ListGrid blockListGrid;
	private DataSource telCompsDS;

	public TabTelComps() {
		try {

			setTitle(CallCenter.constants.telComps());
			setCanClose(true);

			telCompsDS = DataSource.get("TelCompsDS");

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

			telCompNameItem = new TextItem();
			telCompNameItem.setTitle(CallCenter.constants.companyName());
			telCompNameItem.setWidth(250);
			telCompNameItem.setName("telCompNameItem");

			phoneIndexItem = new TextItem();
			phoneIndexItem.setTitle(CallCenter.constants.index());
			phoneIndexItem.setWidth(250);
			phoneIndexItem.setName("phoneIndexItem");

			searchForm.setFields(telCompNameItem, phoneIndexItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(830);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenter.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenter.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenter.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			telCompBillByDayBtn = new ToolStripButton(
					CallCenter.constants.telCombBillByDay(), "billing.png");
			telCompBillByDayBtn.setLayoutAlign(Alignment.LEFT);
			telCompBillByDayBtn.setWidth(50);
			toolStrip.addButton(telCompBillByDayBtn);

			telCompBillByMonthBtn = new ToolStripButton(
					CallCenter.constants.telCombBillByMonth(), "billing.png");
			telCompBillByMonthBtn.setLayoutAlign(Alignment.LEFT);
			telCompBillByMonthBtn.setWidth(50);
			toolStrip.addButton(telCompBillByMonthBtn);

			blockListGrid = new ListGrid();

			blockListGrid.setWidth100();
			blockListGrid.setHeight100();
			blockListGrid.setAlternateRecordStyles(true);
			blockListGrid.setDataSource(telCompsDS);
			blockListGrid.setAutoFetchData(false);
			blockListGrid.setShowFilterEditor(false);
			blockListGrid.setCanEdit(false);
			blockListGrid.setCanRemoveRecords(false);
			blockListGrid.setFetchOperation("searchAllTelComps");
			blockListGrid.setShowRowNumbers(true);
			blockListGrid.setCanHover(true);
			blockListGrid.setShowHover(true);
			blockListGrid.setShowHoverComponents(true);
			blockListGrid.setWrapCells(true);
			blockListGrid.setFixedRecordHeights(false);
			blockListGrid.setCanDragSelectText(true);

			ListGridField tel_comp_name_geo = new ListGridField(
					"tel_comp_name_geo", CallCenter.constants.companyName());
			blockListGrid.setFields(tel_comp_name_geo);

			mainLayout.addMember(blockListGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			telCompNameItem.addKeyPressHandler(new KeyPressHandler() {
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
					telCompNameItem.clearValue();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTelComp dlgAddEditTelComp = new DlgAddEditTelComp(
							blockListGrid, null);
					dlgAddEditTelComp.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditTelComp dlgAddEditTelComp = new DlgAddEditTelComp(
							blockListGrid, listGridRecord);
					dlgAddEditTelComp.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenter.constants.recordAlrDisabled());
						return;
					}
					SC.ask(CallCenter.constants.askForDisable(),
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

			blockListGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = blockListGrid
									.getSelectedRecord();
							DlgAddEditTelComp dlgAddEditTelComp = new DlgAddEditTelComp(
									blockListGrid, listGridRecord);
							dlgAddEditTelComp.show();
						}
					});

			telCompBillByDayBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					final ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer tel_comp_id = listGridRecord
							.getAttributeAsInt("tel_comp_id");
					getTelCompBillByDay(tel_comp_id);
				}
			});
			telCompBillByMonthBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					final ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer tel_comp_id = listGridRecord
							.getAttributeAsInt("tel_comp_id");
					getTelCompBillByMonth(tel_comp_id);
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getTelCompBillByDay(Integer tel_comp_id) {
		try {
			DlgGetTelCompBillByDay dlgGetTelCompBillByDay = new DlgGetTelCompBillByDay(
					tel_comp_id);
			dlgGetTelCompBillByDay.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getTelCompBillByMonth(Integer tel_comp_id) {
		try {
			DlgGetTelCompBillByMonth dlgGetTelCompBillByMonth = new DlgGetTelCompBillByMonth(
					tel_comp_id);
			dlgGetTelCompBillByMonth.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String tel_comp_name_geo = telCompNameItem.getValueAsString();
			criteria.setAttribute("tel_comp_name_geo", tel_comp_name_geo);

			String phoneIndex = phoneIndexItem.getValueAsString();

			if (phoneIndex != null && !phoneIndex.equals("")) {

				try {
					new Long(phoneIndex);
				} catch (Exception e) {
					SC.say(CallCenter.constants.invalidPhone());
					return;
				}

				criteria.setAttribute("phoneIndex", new Integer(phoneIndex));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllTelComps");
			blockListGrid.invalidateCache();
			blockListGrid.filterData(criteria, new DSCallback() {
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
					.getSessionPerson().getUserName());
			record.setAttribute("tel_comp_id",
					listGridRecord.getAttributeAsInt("tel_comp_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeTelComp");
			blockListGrid.removeData(record, new DSCallback() {
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
