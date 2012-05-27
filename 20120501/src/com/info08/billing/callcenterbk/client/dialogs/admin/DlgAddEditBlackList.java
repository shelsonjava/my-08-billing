package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.Map;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditBlackList extends Window {

	private VLayout hLayout;

	private DynamicForm dynamicForm;

	private TextAreaItem noteItem;
	private ListGridRecord editRecord;
	private ListGrid listGrid;
	private ListGrid listGridPhones;
	private String phones;

	private ToolStripButton addBtn1;
	private ToolStripButton deleteBtn1;
	private Long blackListId;

	public DlgAddEditBlackList(ListGrid listGrid, ListGridRecord pRecord) {

		this.editRecord = pRecord;
		this.listGrid = listGrid;
		try {
			phones = "";
			setTitle(pRecord == null ? CallCenterBK.constants.addBlockList()
					: CallCenterBK.constants.editBlockList());
			if (pRecord != null) {
				DSRequest dsRequest = new DSRequest();
				dsRequest.setOperationId("searchAllBlackList");
				Criteria criteria = new Criteria();
				String black_list_id = "black_list_id";
				criteria.setAttribute(black_list_id,
						pRecord.getAttribute(black_list_id));
				criteria.setAttribute("with_phones", 1);
				listGrid.getDataSource().fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length == 1) {
							phones = records[0].getAttribute("blackListPhones");
							showWindow();
						} else {
							SC.warn("NO records found!!!");
							destroy();
						}
					}
				}, dsRequest);
			} else
				showWindow();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
			destroy();
		}

	}

	protected void showWindow() {
		try {
			setHeight(600);
			setWidth(800);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			hLayout = new VLayout(5);
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(4);
			hLayout.addMember(dynamicForm);

			noteItem = new TextAreaItem();
			noteItem.setTitle(CallCenterBK.constants.comment());
			noteItem.setWidth(600);
			noteItem.setHeight(50);
			noteItem.setName("title_descr");
			noteItem.setColSpan(4);

			dynamicForm.setFields(noteItem);

			ToolStrip toolStrip1 = new ToolStrip();
			toolStrip1.setWidth100();
			toolStrip1.setPadding(5);
			hLayout.addMember(toolStrip1);

			addBtn1 = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn1.setLayoutAlign(Alignment.LEFT);
			addBtn1.setWidth(50);
			toolStrip1.addButton(addBtn1);

			deleteBtn1 = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn1.setLayoutAlign(Alignment.LEFT);
			deleteBtn1.setWidth(50);
			toolStrip1.addButton(deleteBtn1);

			// addBtn1.setDisabled(true);
			// deleteBtn1.setDisabled(true);

			BlackListPhoneClientDS blackListPhoneClientDS = BlackListPhoneClientDS
					.getInstance();

			listGridPhones = new ListGrid();
			listGridPhones.setHeight100();
			listGridPhones.setWidth100();
			listGridPhones.setDataSource(blackListPhoneClientDS);
			listGridPhones.setDataPageSize(50);
			listGridPhones.setAutoFetchData(true);
			listGridPhones.setSelectionType(SelectionStyle.MULTIPLE);
			listGridPhones.setUseAllDataSourceFields(false);
			listGridPhones.setShowFilterEditor(true);
			listGridPhones.setFilterOnKeypress(true);
			listGridPhones.setCanDragSelectText(true);

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone());
			phone.setAlign(Alignment.CENTER);
			listGridPhones.setFields(phone);
			hLayout.addMember(listGridPhones);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					save();
				}
			});

			deleteBtn1.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord records[] = listGridPhones
							.getSelectedRecords();
					if (records == null || records.length <= 0) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.deleteConfirm(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										for (ListGridRecord record : records) {
											listGridPhones.removeData(record);
										}
									}
								}
							});
				}
			});

			addBtn1.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new DlgAddEditBlockListPhones(listGridPhones, blackListId,
							listGrid.getDataSource()).show();

				}
			});

			addItem(hLayout);
			fillFields();
			show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
			destroy();
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}

			blackListId = Long.parseLong(editRecord
					.getAttribute("black_list_id"));
			String phoneList[] = phones.split(",");
			Record[] records = new Record[phoneList.length];
			for (int i = 0; i < records.length; i++) {
				records[i] = new Record();
				records[i].setAttribute("phone", phoneList[i].trim());
				listGridPhones.addData(records[i]);
			}
			dynamicForm.setValues(editRecord.toMap());
			
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			RecordList recordList = listGridPhones.getDataAsRecordList();
			if (recordList == null || recordList.isEmpty()) {
				SC.say(CallCenterBK.constants.phonesListIsEmpty());
				return;
			}

			int length = recordList.getLength();
			String blockListPhones = "";
			for (int i = 0; i < length; i++) {
				Record record = recordList.get(i);
				String phone = record.getAttributeAsString("phone");
				if (blockListPhones.length() > 0)
					blockListPhones += ",";
				blockListPhones += phone;
			}

			Map<String, Object> data = ClientUtils.fillMapFromForm(null,
					dynamicForm);
			Record newRecord = ClientUtils.setRecordMap(data, editRecord);

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			newRecord.setAttribute("loggedUserName", loggedUser);
			newRecord.setAttribute("blackListPhones", blockListPhones);
			saveRecord(newRecord);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveRecord(Record record) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addBlackList");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateBlackList");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
