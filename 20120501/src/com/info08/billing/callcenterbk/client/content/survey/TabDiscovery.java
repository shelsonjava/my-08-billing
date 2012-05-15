package com.info08.billing.callcenterbk.client.content.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.discovery.DlgDiscoveryManager;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

public class TabDiscovery extends Tab {

	private DynamicForm searchForm;
	private SelectItem discoveryTypeItem;

	private VLayout mainLayout;

	private ToolStripButton resolveDiscBtn;
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	protected DataSource datasource;

	public TabDiscovery() {
		try {
			setTitle(CallCenterBK.constants.menuDiscovery());
			setCanClose(true);

			datasource = DataSource.get("DiscoveryDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			discoveryTypeItem = new SelectItem();
			discoveryTypeItem.setTitle(CallCenterBK.constants.discoveryType());
			discoveryTypeItem.setWidth(300);
			discoveryTypeItem.setName("discoveryTypeItem");

			DataSource discoveryTypeDS = DataSource.get("DiscoveryTypeDS");
			discoveryTypeItem.setOptionOperationId("searchDiscoverTypesForCB");
			discoveryTypeItem.setOptionDataSource(discoveryTypeDS);
			discoveryTypeItem.setValueField("survey_kind_id");
			discoveryTypeItem.setDisplayField("survey_kind_name");

			discoveryTypeItem.setOptionCriteria(new Criteria());
			discoveryTypeItem.setAutoFetchData(false);

			discoveryTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = discoveryTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("survey_kind_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("survey_kind_id", nullO);
						}
					}
				}
			});

			searchForm.setFields(discoveryTypeItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(1300);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			resolveDiscBtn = new ToolStripButton(
					CallCenterBK.constants.resolve(), "yes.png");
			resolveDiscBtn.setLayoutAlign(Alignment.LEFT);
			resolveDiscBtn.setWidth(50);
			toolStrip.addButton(resolveDiscBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer bblocked = countryRecord
							.getAttributeAsInt("bblocked");
					if (bblocked != null && bblocked.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth(1300);
			listGrid.setHeight100();
			listGrid.setDataSource(datasource);
			listGrid.setFetchOperation("searchAllDiscovery");
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowRowNumbers(true);

			ListGridField survey_kind_name = new ListGridField(
					"survey_kind_name", CallCenterBK.constants.type(), 100);
			ListGridField p_numb = new ListGridField("p_numb",
					CallCenterBK.constants.phone(), 80);
			ListGridField survey_phone = new ListGridField("survey_phone",
					CallCenterBK.constants.contactPhone(), 120);
			ListGridField contact_person = new ListGridField("survey_person",
					CallCenterBK.constants.contactPerson(), 150);
			ListGridField survey_descript = new ListGridField(
					"survey_descript", CallCenterBK.constants.message());
			ListGridField rec_user = new ListGridField("survey_creator",
					CallCenterBK.constants.shortOp(), 50);
			ListGridField rec_date = new ListGridField("survey_created",
					CallCenterBK.constants.time(), 100);
			ListGridField upd_user = new ListGridField("loked_user",
					CallCenterBK.constants.updUser(), 100);

			survey_kind_name.setAlign(Alignment.LEFT);
			p_numb.setAlign(Alignment.LEFT);
			survey_phone.setAlign(Alignment.LEFT);
			contact_person.setAlign(Alignment.LEFT);
			rec_user.setAlign(Alignment.CENTER);
			rec_date.setAlign(Alignment.CENTER);

			listGrid.setFields(survey_kind_name, p_numb, survey_phone,
					contact_person, survey_descript, rec_user, rec_date,
					upd_user);

			mainLayout.addMember(listGrid);

			resolveDiscBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord gridRecord = listGrid.getSelectedRecord();
					if (gridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					lockDiscRecord(gridRecord);
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					discoveryTypeItem.clearValue();
				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			// TabSet tabSet = new TabSet();
			// tabSet.setWidth(780);
			// Tab tabDetViewer = new Tab(CallCenter.constants.view());
			// final DetailViewer detailViewer = new DetailViewer();
			// detailViewer.setDataSource(discoveryClientDS);
			// detailViewer.setWidth(750);
			// tabDetViewer.setPane(detailViewer);
			//
			// listGrid.addRecordClickHandler(new RecordClickHandler() {
			// public void onRecordClick(RecordClickEvent event) {
			// detailViewer.viewSelectedData(listGrid);
			// }
			// });

			// listGrid.addRecordDoubleClickHandler(new
			// RecordDoubleClickHandler() {
			// @Override
			// public void onRecordDoubleClick(RecordDoubleClickEvent event) {
			// ListGridRecord listGridRecord = listGrid
			// .getSelectedRecord();
			// if (listGridRecord == null) {
			// SC.say(CallCenter.constants.pleaseSelrecord());
			// return;
			// }
			// DlgAddEditNonStandartInfo dlgAddEditNonStandartInfo = new
			// DlgAddEditNonStandartInfo(
			// listGrid, listGridRecord);
			// dlgAddEditNonStandartInfo.show();
			// }
			// });
			//
			// tabSet.setTabs(tabDetViewer);
			// mainLayout.addMember(tabSet);
			setPane(mainLayout);
			// start();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void lockDiscRecord(final ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("survey_id",
					listGridRecord.getAttributeAsInt("survey_id"));
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "takeDiscover");
			listGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					shorDiscoveryManagerDialog(listGridRecord);
				}
			}, req);

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void shorDiscoveryManagerDialog(ListGridRecord listGridRecord) {
		DlgDiscoveryManager dlgResolveDiscovery = new DlgDiscoveryManager(this,
				datasource, listGridRecord);
		dlgResolveDiscovery.show();
	}

	public void search() {
		try {
			Criteria criteria = new Criteria();

			String survey_kind_id = discoveryTypeItem.getValueAsString();
			if (survey_kind_id != null && !survey_kind_id.trim().equals("")) {
				criteria.setAttribute("survey_kind_id", new Integer(
						survey_kind_id));
			}

			criteria.setAttribute("survery_responce_status", new Integer(0));
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllDiscovery");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	// public void start() {
	// try {
	// boolean flag = true;
	// if (!flag) {
	// return;
	// }
	// Messaging.subscribe("discoveryServlet", new MessagingCallback() {
	// @Override
	// public void execute(Object data) {
	// updateCallsLog(data);
	// }
	// });
	// RPCRequest request = new RPCRequest();
	// request.setContentType("text/html; charset=UTF-8");
	// request.setActionURL("callcenter/discoveryServlet");
	// RPCManager.sendRequest(request);
	// } catch (Exception e) {
	// e.printStackTrace();
	// SC.say(e.toString());
	// }
	// }
	//
	// @SuppressWarnings("unchecked")
	// private void updateCallsLog(Object data) {
	// try {
	// List<LinkedHashMap<?, ?>> callsData = (List<LinkedHashMap<?, ?>>)
	// JSOHelper
	// .convertToJava((JavaScriptObject) data);
	// List<Record> newData = new ArrayList<Record>();
	// for (LinkedHashMap<?, ?> recordData : callsData) {
	// Integer survey_id = (Integer) recordData.get("survey_id");
	// String session_call_id = (String) recordData.get("session_call_id");
	// Integer ccr = (Integer) recordData.get("ccr");
	// Integer deleted = (Integer) recordData.get("deleted");
	// Integer survey_kind_id = (Integer) recordData
	// .get("survey_kind_id");
	// Integer survery_responce_status = (Integer) recordData
	// .get("survery_responce_status");
	// Integer survey_done = (Integer) recordData.get("survey_done");
	// Integer survey_reply_type_id = (Integer) recordData
	// .get("survey_reply_type_id");
	// Date upd_date = (Date) recordData.get("upd_date");
	// String upd_user = (String) recordData.get("upd_user");
	// String survey_kind_name = (String) recordData.get("survey_kind_name");
	// String p_numb = (String) recordData.get("p_numb");
	// String survey_phone = (String) recordData.get("survey_phone");
	// String contact_person = (String) recordData
	// .get("contact_person");
	// String survey_descript = (String) recordData.get("survey_descript");
	// String rec_user = (String) recordData.get("rec_user");
	// Date rec_date = (Date) recordData.get("rec_date");
	// String status_descr = (String) recordData.get("status_descr");
	// Record record = listGrid.getDataAsRecordList().find(
	// "survey_id", survey_id);
	// if (record == null) {
	// record = new Record();
	// }
	// record.setAttribute("survey_id", survey_id);
	// record.setAttribute("session_call_id", session_call_id);
	// record.setAttribute("ccr", ccr);
	// record.setAttribute("deleted", deleted);
	// record.setAttribute("survey_kind_id", survey_kind_id);
	// record.setAttribute("survery_responce_status", survery_responce_status);
	// record.setAttribute("survey_done", survey_done);
	// record.setAttribute("survey_reply_type_id", survey_reply_type_id);
	// record.setAttribute("upd_date", upd_date);
	// record.setAttribute("survey_kind_name", survey_kind_name);
	// record.setAttribute("p_numb", p_numb);
	// record.setAttribute("survey_phone", survey_phone);
	// record.setAttribute("contact_person", contact_person);
	// record.setAttribute("survey_descript", survey_descript);
	// record.setAttribute("rec_user", rec_user);
	// record.setAttribute("rec_date", rec_date);
	// record.setAttribute("status_descr", status_descr);
	// record.setAttribute("upd_user", upd_user);
	// newData.add(record);
	// }
	// DSResponse dsResponse = new DSResponse();
	// dsResponse.setData((Record[]) newData.toArray(new Record[newData
	// .size()]));
	//
	// DSRequest dsRequest = new DSRequest();
	// dsRequest.setContentType("text/html; charset=UTF-8");
	// dsRequest.setOperationType(DSOperationType.UPDATE);
	// DiscoveryClientDS.getInstance().updateCaches(dsResponse, dsRequest);
	// } catch (Exception e) {
	// e.printStackTrace();
	// SC.say(e.toString());
	// }
	// }
}
