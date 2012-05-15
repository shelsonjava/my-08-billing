package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditGSMIndexes extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem gsmCompanyItem;
	private TextItem gsmIndexItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditGSMIndexes(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants
					.addMobOperatorPref() : CallCenterBK.constants
					.editMobOperatorPref());

			setHeight(135);
			setWidth(450);
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
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			gsmCompanyItem = new TextItem();
			gsmCompanyItem.setTitle(CallCenterBK.constants.mobOperator());
			gsmCompanyItem.setName("gsmCompanyItem");
			gsmCompanyItem.setWidth(250);

			gsmIndexItem = new TextItem();
			gsmIndexItem.setTitle(CallCenterBK.constants.mobOperatorIndex());
			gsmIndexItem.setName("gsmIndexItem");
			gsmIndexItem.setWidth(250);

			dynamicForm.setFields(gsmCompanyItem, gsmIndexItem);

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
			gsmCompanyItem.setValue(editRecord.getAttributeAsString("gsm_company"));
			gsmIndexItem.setValue(editRecord.getAttributeAsString("gsm_index"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String gsm_company = gsmCompanyItem.getValueAsString();
			if (gsm_company == null || gsm_company.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterMobOperator());
				return;
			}

			String gsm_index = gsmIndexItem.getValueAsString();
			if (gsm_index == null || gsm_index.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterMobOperatorPrefix());
				return;
			}
			if (gsm_index.length() < 2 || gsm_index.length() > 3) {
				SC.say(CallCenterBK.constants.mobOperPrefIs3Digit());
				return;
			}
			try {
				new Integer(gsm_index);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.mobOperPrefIs3Digit());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("gsm_company", gsm_company);
			record.setAttribute("gsm_index", gsm_index);

			if (editRecord != null) {
				record.setAttribute("gsm_index_id", editRecord.getAttributeAsInt("gsm_index_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addGSMIndexes");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateGSMIndexes");
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
