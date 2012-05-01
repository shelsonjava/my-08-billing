package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.LinkedHashMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditTelComp extends Window {

	private VLayout hLayout;

	private TextItem telCompNameItem;
	private TextItem telCompOurPercentItem;
	private SelectItem hasCalcItem;
	private TextItem callPriceItem;

	private DynamicForm dynamicForm;

	private ListGridRecord editRecord;
	private ListGrid listGridIndexes;
	private ListGrid listGrid;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	public DlgAddEditTelComp(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addTelComp()
					: CallCenterBK.constants.editTelComp());

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

			telCompNameItem = new TextItem();
			telCompNameItem.setTitle(CallCenterBK.constants.companyNameFull());
			telCompNameItem.setWidth("100%");
			telCompNameItem.setName("telCompNameItem");

			telCompOurPercentItem = new TextItem();
			telCompOurPercentItem.setTitle(CallCenterBK.constants.ourPercent());
			telCompOurPercentItem.setWidth("100%");
			telCompOurPercentItem.setName("telCompOurPercentItem");
			telCompOurPercentItem.setKeyPressFilter("[0-9\\.]");

			hasCalcItem = new SelectItem();
			hasCalcItem.setTitle(CallCenterBK.constants.hasCalculation());
			hasCalcItem.setWidth("100%");
			hasCalcItem.setName("hasCalcItem");
			hasCalcItem.setDefaultToFirstOption(true);
			hasCalcItem.setValueMap(ClientMapUtil.getInstance()
					.getHasCalculations1());

			callPriceItem = new TextItem();
			callPriceItem.setTitle(CallCenterBK.constants.callPrice());
			callPriceItem.setWidth("100%");
			callPriceItem.setName("callPriceItem");
			callPriceItem.setKeyPressFilter("[0-9\\.]");

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(250);
			dynamicForm.setNumCols(2);

			dynamicForm.setFields(telCompNameItem, telCompOurPercentItem,
					hasCalcItem, callPriceItem);

			hLayout.addMember(dynamicForm);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(4);
			hLayout.addMember(dynamicForm);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			hLayout.addMember(toolStrip);

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

			TelCompIndClientDS telCompIndClientDS = TelCompIndClientDS
					.getInstance();

			listGridIndexes = new ListGrid();
			listGridIndexes.setHeight100();
			listGridIndexes.setWidth100();
			listGridIndexes.setDataSource(telCompIndClientDS);
			listGridIndexes.setDataPageSize(50);
			listGridIndexes.setAutoFetchData(true);
			listGridIndexes.setSelectionType(SelectionStyle.MULTIPLE);
			listGridIndexes.setUseAllDataSourceFields(false);
			listGridIndexes.setShowFilterEditor(true);
			listGridIndexes.setFilterOnKeypress(true);
			listGridIndexes.setCanDragSelectText(true);
			listGridIndexes.setShowRowNumbers(true);

			ListGridField st_ind = new ListGridField("st_ind",
					CallCenterBK.constants.startIndex());
			st_ind.setAlign(Alignment.CENTER);

			ListGridField end_ind = new ListGridField("end_ind",
					CallCenterBK.constants.endIndex());
			end_ind.setAlign(Alignment.CENTER);

			ListGridField cr_descr = new ListGridField("cr_descr",
					CallCenterBK.constants.type());
			cr_descr.setAlign(Alignment.CENTER);

			ListGridField count_type_descr = new ListGridField(
					"count_type_descr", CallCenterBK.constants.type());
			count_type_descr.setAlign(Alignment.CENTER);

			listGridIndexes.setFields(st_ind, end_ind, cr_descr,
					count_type_descr);

			hLayout.addMember(listGridIndexes);

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

			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord records[] = listGridIndexes
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
											listGridIndexes.removeData(record);
										}
									}
								}
							});
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTelCompInd dlgAddEditTelCompInd = new DlgAddEditTelCompInd(
							listGridIndexes, null);
					dlgAddEditTelCompInd.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGridIndexes
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditTelCompInd dlgAddEditTelCompInd = new DlgAddEditTelCompInd(
							listGridIndexes, listGridRecord);
					dlgAddEditTelCompInd.show();
				}
			});

			listGridIndexes
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = listGridIndexes
									.getSelectedRecord();

							DlgAddEditTelCompInd dlgAddEditTelCompInd = new DlgAddEditTelCompInd(
									listGridIndexes, listGridRecord);
							dlgAddEditTelCompInd.show();
						}
					});

			addItem(hLayout);
			fillFields();

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
			telCompNameItem.setValue(editRecord
					.getAttributeAsString("tel_comp_name_geo"));
			telCompOurPercentItem.setValue(editRecord
					.getAttributeAsString("our_percent"));
			hasCalcItem.setValue(editRecord
					.getAttributeAsInt("has_calculation"));
			callPriceItem.setValue(editRecord
					.getAttributeAsString("call_price"));

			DataSource telCompIndDS = DataSource.get("TelCompIndDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("tel_comp_id",
					editRecord.getAttributeAsInt("tel_comp_id"));
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllTelCompInds");
			telCompIndDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							listGridIndexes.addData(record);
						}
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String tel_comp_name_geo = telCompNameItem.getValueAsString();
			if (tel_comp_name_geo == null
					|| tel_comp_name_geo.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterTelCompName());
				return;
			}
			tel_comp_name_geo = tel_comp_name_geo.trim();

			String our_percent_str = telCompOurPercentItem.getValueAsString();
			if (our_percent_str == null || our_percent_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterOurPercent());
				return;
			}

			Integer has_calculation = new Integer(
					hasCalcItem.getValueAsString());

			Double our_percent = null;
			try {
				our_percent = Double.parseDouble(our_percent_str);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidOurPercent());
				return;
			}
			if (our_percent.intValue() <= 0 && our_percent.intValue() > 100) {
				SC.say(CallCenterBK.constants.invalidOurPercent());
				return;
			}

			String call_price_str = callPriceItem.getValueAsString();
			if (call_price_str == null || call_price_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterCallPrice());
				return;
			}
			Double call_price = null;
			try {
				call_price = Double.parseDouble(call_price_str);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidCallPrice());
				return;
			}
			if (call_price.intValue() <= 0 && call_price.intValue() > 100) {
				SC.say(CallCenterBK.constants.invalidCallPrice());
				return;
			}

			LinkedHashMap<String, LinkedHashMap<String, String>> indexes = new LinkedHashMap<String, LinkedHashMap<String, String>>();
			RecordList recordList = listGridIndexes.getDataAsRecordList();
			int length = recordList.getLength();
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					Record record = recordList.get(i);

					Integer st_ind = record.getAttributeAsInt("st_ind");
					Integer end_ind = record.getAttributeAsInt("end_ind");
					Integer cr = record.getAttributeAsInt("cr");
					Integer count_type = record.getAttributeAsInt("count_type");
					LinkedHashMap<String, String> param = new LinkedHashMap<String, String>();
					param.put("str_end_ind", end_ind.toString());
					param.put("str_cr", cr.toString());
					param.put("str_count_type", count_type.toString());
					indexes.put(st_ind.toString(), param);
				}
			}

			Record record = new Record();
			if (editRecord != null) {
				record.setAttribute("tel_comp_id",
						editRecord.getAttributeAsInt("tel_comp_id"));
			}
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("tel_comp_name_geo", tel_comp_name_geo);
			record.setAttribute("our_percent", our_percent);
			record.setAttribute("call_price", call_price);
			record.setAttribute("telCompIdexes", indexes);
			record.setAttribute("has_calculation", has_calculation);

			saveTelComp(record);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveTelComp(Record record) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addTelComp");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTelComp");
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
