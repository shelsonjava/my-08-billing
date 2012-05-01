package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.LinkedHashMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxEvent;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItemDataChangedHandler;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
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
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditBlockList extends Window {

	private VLayout hLayout;

	private MyComboBoxItem myComboBoxItemOrg;
	private MyComboBoxItem myComboBoxItemOrgDetails;

	private DynamicForm dynamicForm;
	private DynamicForm dynamicForm1;

	private TextAreaItem noteItem;
	private RadioGroupItem blockListType;

	private ListGridRecord editRecord;
	private ListGrid listGrid;
	private ListGrid listGridPhones;

	private ToolStripButton addBtn1;
	private ToolStripButton deleteBtn1;

	public DlgAddEditBlockList(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addBlockList()
					: CallCenterBK.constants.editBlockList());

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

			myComboBoxItemOrg = new MyComboBoxItem("org_name",
					CallCenterBK.constants.orgNameFull(), 168, 580);
			myComboBoxItemOrg.setMyDlgHeight(400);
			myComboBoxItemOrg.setMyDlgWidth(600);
			DataSource orgDS = DataSource.get("OrgDS");
			myComboBoxItemOrg.setMyDataSource(orgDS);
			myComboBoxItemOrg
					.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			myComboBoxItemOrg.setMyIdField("main_id");
			myComboBoxItemOrg.setMyDisplayField("org_name");
			myComboBoxItemOrg.setMyChooserTitle(CallCenterBK.constants
					.organization());

			hLayout.addMember(myComboBoxItemOrg);

			myComboBoxItemOrgDetails = new MyComboBoxItem("main_detail_geo",
					CallCenterBK.constants.department(), 168, 580);
			myComboBoxItemOrgDetails.setMyDlgHeight(400);
			myComboBoxItemOrgDetails.setMyDlgWidth(600);
			DataSource mainDetDS = DataSource.get("MainDetDS");
			myComboBoxItemOrgDetails.setMyDataSource(mainDetDS);
			myComboBoxItemOrgDetails
					.setMyDataSourceOperation("searchMainDetailsAdv");
			myComboBoxItemOrgDetails.setMyIdField("main_detail_id");
			myComboBoxItemOrgDetails.setMyDisplayField("main_detail_geo");
			myComboBoxItemOrgDetails.setMyChooserTitle(CallCenterBK.constants
					.department());
			Criteria myCriteria = new Criteria();
			myCriteria.setAttribute("main_id", -1000);
			myComboBoxItemOrgDetails.setMyCriteria(myCriteria);

			hLayout.addMember(myComboBoxItemOrgDetails);

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
			noteItem.setName("noteItem");
			noteItem.setColSpan(4);

			dynamicForm.setFields(noteItem);

			dynamicForm1 = new DynamicForm();
			dynamicForm1.setAutoFocus(true);
			dynamicForm1.setWidth100();
			dynamicForm1.setTitleWidth(2);
			dynamicForm1.setNumCols(2);
			dynamicForm1.setTitleSuffix(" ");
			hLayout.addMember(dynamicForm1);

			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("1", CallCenterBK.constants.contrPhonesAll());
			map.put("2", CallCenterBK.constants.contrPhonesOnlyList());
			map.put("3", CallCenterBK.constants.contrPhonesExceptList());
			map.put("4", CallCenterBK.constants.onlyNumberList());
			blockListType = new RadioGroupItem();
			blockListType.setWidth(750);
			blockListType.setVertical(false);
			blockListType.setValueMap(map);
			blockListType.setTitle(" ");
			blockListType.setValue("1");

			dynamicForm1.setFields(blockListType);

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

			addBtn1.setDisabled(true);
			deleteBtn1.setDisabled(true);

			BlockListPhoneClientDS blockListPhoneClientDS = BlockListPhoneClientDS
					.getInstance();

			listGridPhones = new ListGrid() {
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
			listGridPhones.setHeight100();
			listGridPhones.setWidth100();
			listGridPhones.setDataSource(blockListPhoneClientDS);
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

			blockListType.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					drawByPhoneListType(event.getValue().toString().equals("1"));
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
					DlgAddEditBlockListPhones dlgAddEditBlockListPhones = new DlgAddEditBlockListPhones(
							listGridPhones);
					dlgAddEditBlockListPhones.show();
				}
			});

			myComboBoxItemOrg
					.addDataChangedHandler(new MyComboBoxItemDataChangedHandler() {
						@Override
						public void onDataChanged(MyComboBoxEvent event) {
							Integer main_id = event.getSelectedId();
							myComboBoxItemOrgDetails.setMyId(-1);
							myComboBoxItemOrgDetails.setMyValue("");
							if (main_id != null && main_id.intValue() > 0) {
								Criteria myCriteria = new Criteria();
								myCriteria.setAttribute("main_id", main_id);
								myComboBoxItemOrgDetails
										.setMyCriteria(myCriteria);
							}
						}
					});
			addItem(hLayout);
			fillFields();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void drawByPhoneListType(Boolean value) {
		try {
			addBtn1.setDisabled(value);
			deleteBtn1.setDisabled(value);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			Criteria criteria = new Criteria();
			DSRequest dsRequest = new DSRequest();
			DataSource blockListPhonesDS = DataSource.get("BlockListPhonesDS");
			DSRequest dsRequest1 = new DSRequest();
			dsRequest1.setOperationId("searchBlockListPhones");
			blockListPhonesDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							listGridPhones.addData(record);
						}
					}
				}
			}, dsRequest);
			Integer main_id = editRecord.getAttributeAsInt("main_id");
			if (main_id != null && main_id.intValue() > 0) {
				String org_name = editRecord.getAttributeAsString("orgName");
				myComboBoxItemOrg.setMyId(main_id);
				myComboBoxItemOrg.setMyValue(org_name);

				Criteria myCriteria = myComboBoxItemOrgDetails.getMyCriteria();
				if (myCriteria == null) {
					myCriteria = new Criteria();
				}
				myCriteria.setAttribute("main_id", main_id);
				myComboBoxItemOrgDetails.setMyCriteria(myCriteria);
			}

			Integer main_detail_id = editRecord
					.getAttributeAsInt("main_detail_id");

			if (main_detail_id != null && main_detail_id.intValue() > 0) {
				String orgDepName = editRecord
						.getAttributeAsString("orgDepName");
				myComboBoxItemOrgDetails.setMyId(main_detail_id);
				myComboBoxItemOrgDetails.setMyValue(orgDepName);
			}

			String note = editRecord.getAttributeAsString("note");
			if (note != null && !note.trim().equals("")) {
				noteItem.setValue(note);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			Integer main_id = myComboBoxItemOrg.getMyId();
			if (main_id == null || main_id.intValue() <= 0) {
				SC.say(CallCenterBK.constants.plzSelectOrg());
				return;
			}
			Integer main_detail_id = myComboBoxItemOrgDetails.getMyId();
			String note = noteItem.getValueAsString();
			String phone_list_type_str = blockListType.getValueAsString();
			Integer phone_list_type = new Integer(phone_list_type_str);
			LinkedHashMap<String, String> blockListPhones = new LinkedHashMap<String, String>();

			RecordList recordList = listGridPhones.getDataAsRecordList();
			if (recordList == null || recordList.isEmpty()) {
				SC.say(CallCenterBK.constants.phonesListIsEmpty());
				return;
			}
			int length = recordList.getLength();
			for (int i = 0; i < length; i++) {
				Record record = recordList.get(i);
				String phone = record.getAttributeAsString("phone");
				blockListPhones.put(phone, phone);
			}
			Record record = new Record();
			if (editRecord != null) {
				record.setAttribute("id", editRecord.getAttributeAsInt("id"));
			}
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("upd_user", loggedUser);
			record.setAttribute("deleted", 0);
			record.setAttribute("main_id", main_id);
			record.setAttribute("main_detail_id", main_detail_id);
			record.setAttribute("note", note);
			record.setAttribute("phone_list_type", phone_list_type);

			if (blockListPhones != null && !blockListPhones.isEmpty()) {
				checkContractPhones(record);
			} else {
				saveContract(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void checkContractPhones(final Record record) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "checkContractorNumbers");
			listGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					saveContract(record);
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveContract(Record record) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addBlockList");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateBlockList");
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
