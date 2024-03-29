package com.info08.billing.callcenter.client.dialogs.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class DlgAddEditMobOperPref extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem operatorItem;
	private TextItem indexItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditMobOperPref(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenter.constants
					.addMobOperatorPref() : CallCenter.constants
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

			operatorItem = new TextItem();
			operatorItem.setTitle(CallCenter.constants.mobOperator());
			operatorItem.setName("operatorItem");
			operatorItem.setWidth(250);

			indexItem = new TextItem();
			indexItem.setTitle(CallCenter.constants.mobOperatorIndex());
			indexItem.setName("indexItem");
			indexItem.setWidth(250);

			dynamicForm.setFields(operatorItem, indexItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
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
			operatorItem.setValue(editRecord.getAttributeAsString("oper"));
			indexItem.setValue(editRecord.getAttributeAsString("prefix"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String oper = operatorItem.getValueAsString();
			if (oper == null || oper.trim().equals("")) {
				SC.say(CallCenter.constants.enterMobOperator());
				return;
			}

			String prefix = indexItem.getValueAsString();
			if (prefix == null || prefix.trim().equals("")) {
				SC.say(CallCenter.constants.enterMobOperatorPrefix());
				return;
			}
			if (prefix.length() < 2 || prefix.length() > 3) {
				SC.say(CallCenter.constants.mobOperPrefIs3Digit());
				return;
			}
			try {
				new Integer(prefix);
			} catch (Exception e) {
				SC.say(CallCenter.constants.mobOperPrefIs3Digit());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("deleted", 0);
			record.setAttribute("oper", oper);
			record.setAttribute("prefix", prefix);

			if (editRecord != null) {
				record.setAttribute("id", editRecord.getAttributeAsInt("id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addMobileOperatorPrefix");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateMobileOperatorPrefix");
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
