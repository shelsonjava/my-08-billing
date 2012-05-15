package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditBlockList;
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

public class TabBlockList extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem orgNameItem;
	private TextItem phoneItem;
	private TextItem noteItem;

	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton restoreBtn;
	private ToolStripButton viewCallCntBtn;

	private ListGrid blockListGrid;
	private DataSource blockListDS;

	public TabBlockList() {
		try {

			setTitle(CallCenterBK.constants.blockPhone());
			setCanClose(true);

			blockListDS = DataSource.get("BlockListDS");

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

			orgNameItem = new TextItem();
			orgNameItem.setTitle(CallCenterBK.constants.orgNameFull());
			orgNameItem.setWidth(250);
			orgNameItem.setName("orgNameItem");

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(250);
			phoneItem.setName("phoneItem");

			noteItem = new TextItem();
			noteItem.setTitle(CallCenterBK.constants.comment());
			noteItem.setWidth(250);
			noteItem.setName("noteItem");

			searchForm.setFields(orgNameItem, phoneItem, noteItem);

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

			restoreBtn = new ToolStripButton(CallCenterBK.constants.enable(),
					"restoreIcon.gif");
			restoreBtn.setLayoutAlign(Alignment.LEFT);
			restoreBtn.setWidth(50);
			toolStrip.addButton(restoreBtn);

			toolStrip.addSeparator();

			viewCallCntBtn = new ToolStripButton(
					CallCenterBK.constants.charges(), "moneySmall.png");
			viewCallCntBtn.setLayoutAlign(Alignment.LEFT);
			viewCallCntBtn.setWidth(50);
			toolStrip.addButton(viewCallCntBtn);

			blockListGrid = new ListGrid() {
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

			blockListGrid.setWidth100();
			blockListGrid.setHeight100();
			blockListGrid.setAlternateRecordStyles(true);
			blockListGrid.setDataSource(blockListDS);
			blockListGrid.setAutoFetchData(false);
			blockListGrid.setShowFilterEditor(false);
			blockListGrid.setCanEdit(false);
			blockListGrid.setCanRemoveRecords(false);
			blockListGrid.setFetchOperation("searchAllBlockList");
			blockListGrid.setShowRowNumbers(true);
			blockListGrid.setCanHover(true);
			blockListGrid.setShowHover(true);
			blockListGrid.setShowHoverComponents(true);
			blockListGrid.setWrapCells(true);
			blockListGrid.setFixedRecordHeights(false);
			blockListGrid.setCanDragSelectText(true);

			ListGridField orgName = new ListGridField("orgName",
					CallCenterBK.constants.orgNameFull());
			ListGridField orgDepName = new ListGridField("orgDepName",
					CallCenterBK.constants.department(), 300);
			ListGridField note = new ListGridField("note",
					CallCenterBK.constants.comment(), 300);

			blockListGrid.setFields(orgName, orgDepName, note);

			mainLayout.addMember(blockListGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			orgNameItem.addKeyPressHandler(new KeyPressHandler() {
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
					orgNameItem.clearValue();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditBlockList dlgAddEditBlockList = new DlgAddEditBlockList(
							blockListGrid, null);
					dlgAddEditBlockList.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditBlockList dlgAddEditBlockList = new DlgAddEditBlockList(
							blockListGrid, listGridRecord);
					dlgAddEditBlockList.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenterBK.constants.recordAlrDisabled());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 1);
									}
								}
							});
				}
			});
			restoreBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted.equals(0)) {
						SC.say(CallCenterBK.constants.recordAlrEnabled());
						return;
					}
					SC.ask(CallCenterBK.constants.askForEnable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 0);
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
							DlgAddEditBlockList dlgAddEditBlockList = new DlgAddEditBlockList(
									blockListGrid, listGridRecord);
							dlgAddEditBlockList.show();
						}
					});

			viewCallCntBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = blockListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer id = listGridRecord.getAttributeAsInt("id");
					showCallCnt(id);
				}

			});

			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void showCallCnt(Integer id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("id", id);

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "getCallCnt");
			blockListGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[0];
						Integer call_cnt = record
								.getAttributeAsInt("call_cnt");
						if (call_cnt == null) {
							call_cnt = 0;
						}
						SC.say((CallCenterBK.constants.contractorCallCnt() + call_cnt));
					}
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String org_name = orgNameItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				String tmp = org_name.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("org_name" + i, item);
					i++;
				}
			}

			String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.equals("")) {
				criteria.setAttribute("phone", new Integer(phone));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllBlockList");
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

	private void changeStatus(ListGridRecord listGridRecord, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("deleted", deleted);
			record.setAttribute("id", listGridRecord.getAttributeAsInt("id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateBlockListStatus");
			blockListGrid.updateData(record, new DSCallback() {
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
