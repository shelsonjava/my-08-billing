package com.info08.billing.callcenter.client.dialogs.org;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgOrgStatusChange extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private SelectItem orgStatusItem;
	private ListGrid orgTreeGrid;
	private ListGridRecord listGridRecord;

	public DlgOrgStatusChange(ListGridRecord listGridRecord,
			ListGrid orgTreeGrid) {
		try {
			this.orgTreeGrid = orgTreeGrid;
			this.listGridRecord = listGridRecord;
			setTitle(CallCenter.constants.sortOrgs());

			setHeight(110);
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

			orgStatusItem = new SelectItem();
			orgStatusItem.setTitle(CallCenter.constants.status());
			orgStatusItem.setName("response_type");
			orgStatusItem.setWidth(300);
			orgStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgStatuses());
			orgStatusItem.setDefaultToFirstOption(true);

			dynamicForm.setFields(orgStatusItem);

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

			if (listGridRecord != null) {
				Integer statuse = listGridRecord.getAttributeAsInt("statuse");
				if (statuse != null) {
					orgStatusItem.setValue(statuse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			record.setAttribute("statuse",
					new Integer(orgStatusItem.getValueAsString()));
			record.setAttribute("main_id",
					listGridRecord.getAttributeAsInt("main_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateMainOrgStatus");
			orgTreeGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
