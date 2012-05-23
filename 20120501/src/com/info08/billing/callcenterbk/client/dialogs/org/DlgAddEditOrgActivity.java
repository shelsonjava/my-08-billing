package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditOrgActivity extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem activityDescriptionItem;
	private TextAreaItem remarkItem;
	private SelectItem isBankActivityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditOrgActivity(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? CallCenterBK.constants.addOrgActivity()
				: CallCenterBK.constants.modifyOrgActivity());

		setHeight(240);
		setWidth(670);
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
		dynamicForm.setTitleWidth(220);
		dynamicForm.setNumCols(2);
		hLayout.addMember(dynamicForm);

		activityDescriptionItem = new TextItem();
		activityDescriptionItem.setTitle(CallCenterBK.constants.orgActivity());
		activityDescriptionItem.setWidth(430);
		activityDescriptionItem.setName("activityDescriptionItem");

		remarkItem = new TextAreaItem();
		remarkItem.setTitle(CallCenterBK.constants.comment());
		remarkItem.setWidth(430);
		remarkItem.setName("remarkItem");

		isBankActivityItem = new SelectItem();
		isBankActivityItem.setName("isBankActivityItem");
		isBankActivityItem.setWidth(430);
		isBankActivityItem.setTitle(CallCenterBK.constants.bankActivity());
		isBankActivityItem.setValueMap(ClientMapUtil.getInstance()
				.getYesAndNo());

		dynamicForm.setFields(activityDescriptionItem, remarkItem,
				isBankActivityItem);

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
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			activityDescriptionItem.setValue(editRecord
					.getAttributeAsString("activity_description"));
			remarkItem.setValue(editRecord.getAttributeAsString("remark"));

			isBankActivityItem.setValue(editRecord
					.getAttributeAsString("is_bank_activity"));

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String activity_description = activityDescriptionItem
					.getValueAsString();
			if (activity_description == null
					|| activity_description.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ დასახელება !");
				return;
			}
			String remark = remarkItem.getValueAsString();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("activity_description", activity_description);
			record.setAttribute("remark", remark);
			record.setAttribute("is_bank_activity",
					Integer.parseInt(isBankActivityItem.getValueAsString()));

			if (editRecord != null) {
				record.setAttribute("org_activity_id",
						editRecord.getAttributeAsInt("org_activity_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addOrgActivity");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateOrgActivity");
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
