package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditBlackList;
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

public class TabBlackList extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem phoneItem;
	private TextItem title_descrItem;

	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton viewCallCntBtn;

	private ListGrid blockListGrid;
	private DataSource blackListDS;

	public TabBlackList() {
		try {

			setTitle(CallCenterBK.constants.blockPhone());
			setCanClose(true);

			blackListDS = DataSource.get("BlackListDS");

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

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(250);
			phoneItem.setName("phone");

			title_descrItem = new TextItem();
			title_descrItem.setTitle(CallCenterBK.constants.comment());
			title_descrItem.setWidth(250);
			title_descrItem.setName("title_descr");

			searchForm.setFields(phoneItem, title_descrItem);

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

			viewCallCntBtn = new ToolStripButton(
					CallCenterBK.constants.charges(), "moneySmall.png");
			viewCallCntBtn.setLayoutAlign(Alignment.LEFT);
			viewCallCntBtn.setWidth(50);
			toolStrip.addButton(viewCallCntBtn);

			blockListGrid = new ListGrid();

			blockListGrid.setWidth100();
			blockListGrid.setHeight100();
			blockListGrid.setAlternateRecordStyles(true);
			blockListGrid.setDataSource(blackListDS);
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

			ListGridField note = new ListGridField("title_descr",
					CallCenterBK.constants.comment(), 300);

			blockListGrid.setFields(note);

			mainLayout.addMember(blockListGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new DlgAddEditBlackList(blockListGrid, null);

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

					new DlgAddEditBlackList(blockListGrid, listGridRecord);
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

			blockListGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = blockListGrid
									.getSelectedRecord();
							new DlgAddEditBlackList(blockListGrid,
									listGridRecord);
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
						Integer call_cnt = record.getAttributeAsInt("call_cnt");
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

			String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.equals("")) {
				criteria.setAttribute("phone", new Integer(phone));
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllBlackList");
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
					.getSessionPerson().getUser_name());
			record.setAttribute("black_list_id", listGridRecord.getAttributeAsInt("black_list_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeBlackList");
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
