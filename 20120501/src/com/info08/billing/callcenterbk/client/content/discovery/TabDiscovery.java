package com.info08.billing.callcenterbk.client.content.discovery;

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
			discoveryTypeItem.setValueField("discover_type_id");
			discoveryTypeItem.setDisplayField("discover_type");

			discoveryTypeItem.setOptionCriteria(new Criteria());
			discoveryTypeItem.setAutoFetchData(false);

			discoveryTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = discoveryTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("discover_type_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("discover_type_id", nullO);
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
					Integer is_locked = countryRecord
							.getAttributeAsInt("is_locked");
					if (is_locked != null && is_locked.equals(1)) {
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

			ListGridField discover_type = new ListGridField("discover_type",
					CallCenterBK.constants.type(), 100);
			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone(), 80);
			ListGridField contact_phone = new ListGridField("contact_phone",
					CallCenterBK.constants.contactPhone(), 120);
			ListGridField contact_person = new ListGridField("contact_person",
					CallCenterBK.constants.contactPerson(), 150);
			ListGridField discover_txt = new ListGridField("discover_txt",
					CallCenterBK.constants.message());
			ListGridField rec_user = new ListGridField("rec_user",
					CallCenterBK.constants.shortOp(), 50);
			ListGridField rec_date = new ListGridField("rec_date",
					CallCenterBK.constants.time(), 100);
			ListGridField upd_user = new ListGridField("upd_user",
					CallCenterBK.constants.updUser(), 100);

			discover_type.setAlign(Alignment.LEFT);
			phone.setAlign(Alignment.LEFT);
			contact_phone.setAlign(Alignment.LEFT);
			contact_person.setAlign(Alignment.LEFT);
			rec_user.setAlign(Alignment.CENTER);
			rec_date.setAlign(Alignment.CENTER);

			listGrid.setFields(discover_type, phone, contact_phone,
					contact_person, discover_txt, rec_user, rec_date, upd_user);

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
			record.setAttribute("discover_id",
					listGridRecord.getAttributeAsInt("discover_id"));
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

			String discover_type_id = discoveryTypeItem.getValueAsString();
			if (discover_type_id != null && !discover_type_id.trim().equals("")) {
				criteria.setAttribute("discover_type_id", new Integer(
						discover_type_id));
			}

			criteria.setAttribute("execution_status", new Integer(0));
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
	// Integer discover_id = (Integer) recordData.get("discover_id");
	// String call_id = (String) recordData.get("call_id");
	// Integer ccr = (Integer) recordData.get("ccr");
	// Integer deleted = (Integer) recordData.get("deleted");
	// Integer discover_type_id = (Integer) recordData
	// .get("discover_type_id");
	// Integer execution_status = (Integer) recordData
	// .get("execution_status");
	// Integer iscorrect = (Integer) recordData.get("iscorrect");
	// Integer response_type_id = (Integer) recordData
	// .get("response_type_id");
	// Date upd_date = (Date) recordData.get("upd_date");
	// String upd_user = (String) recordData.get("upd_user");
	// String discover_type = (String) recordData.get("discover_type");
	// String phone = (String) recordData.get("phone");
	// String contact_phone = (String) recordData.get("contact_phone");
	// String contact_person = (String) recordData
	// .get("contact_person");
	// String discover_txt = (String) recordData.get("discover_txt");
	// String rec_user = (String) recordData.get("rec_user");
	// Date rec_date = (Date) recordData.get("rec_date");
	// String status_descr = (String) recordData.get("status_descr");
	// Record record = listGrid.getDataAsRecordList().find(
	// "discover_id", discover_id);
	// if (record == null) {
	// record = new Record();
	// }
	// record.setAttribute("discover_id", discover_id);
	// record.setAttribute("call_id", call_id);
	// record.setAttribute("ccr", ccr);
	// record.setAttribute("deleted", deleted);
	// record.setAttribute("discover_type_id", discover_type_id);
	// record.setAttribute("execution_status", execution_status);
	// record.setAttribute("iscorrect", iscorrect);
	// record.setAttribute("response_type_id", response_type_id);
	// record.setAttribute("upd_date", upd_date);
	// record.setAttribute("discover_type", discover_type);
	// record.setAttribute("phone", phone);
	// record.setAttribute("contact_phone", contact_phone);
	// record.setAttribute("contact_person", contact_person);
	// record.setAttribute("discover_txt", discover_txt);
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
