package com.info08.billing.callcenter.client.dialogs.discovery;

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

public class DlgAddEditDiscoveryRType extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem responseTypeItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditDiscoveryRType(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenter.constants
					.addStatus() : CallCenter.constants
					.editStatus());

			setHeight(100);
			setWidth(430);
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
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			responseTypeItem = new TextItem();
			responseTypeItem.setTitle(CallCenter.constants.status());
			responseTypeItem.setName("response_type");
			responseTypeItem.setWidth(300);

			dynamicForm.setFields(responseTypeItem);

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
			responseTypeItem.setValue(editRecord.getAttributeAsString("response_type"));			
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String response_type = responseTypeItem.getValueAsString();
			if (response_type == null || response_type.trim().equals("")) {
				SC.say(CallCenter.constants.enterStatus());
				return;
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("deleted", 0);
			record.setAttribute("response_type", response_type);

			if (editRecord != null) {
				record.setAttribute("response_type_id",editRecord.getAttributeAsInt("response_type_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addDiscoverRtypes");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateDiscoverRtypes");
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
