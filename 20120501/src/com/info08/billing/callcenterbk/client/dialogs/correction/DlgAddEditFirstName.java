package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditFirstName extends Window {

	private VLayout hLayout;
	private TextItem firstNameItem;
	private DynamicForm form;

	public DlgAddEditFirstName(final Integer firstNameId, String firstName,
			final DataSource firstNameDS) {

		setWidth(400);
		setHeight(130);
		setTitle(firstNameId == null ? "ახალი სახელი" : "სახელის შესწორება");
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

		form = new DynamicForm();
		form.setPadding(10);
		form.setAutoFocus(true);
		form.setWidth100();
		form.setTitleWidth(80);
		form.setNumCols(2);
		form.setDataSource(firstNameDS);

		firstNameItem = new TextItem();
		firstNameItem.setTitle("სახელი");
		firstNameItem.setValue(firstName);
		firstNameItem.setName("firstname");
		firstNameItem.setWidth("100%");

		HiddenItem firstNameIdItem = null;
		if (firstNameId != null) {
			firstNameIdItem = new HiddenItem();
			firstNameIdItem.setValue(firstNameId);
			firstNameIdItem.setName("firstname_Id");
			firstNameIdItem.setVisible(false);
		}
		if (firstNameId == null) {
			form.setFields(firstNameItem);
		} else {
			form.setFields(firstNameItem, firstNameIdItem);
		}

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);
		hLayoutItem.setMargin(10);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
		cancItem.setWidth(100);

		hLayoutItem.setMembers(saveItem, cancItem);
		hLayout.setMembers(form, hLayoutItem);
		addItem(hLayout);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		saveItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try {
					String firstName = firstNameItem.getValueAsString();
					if (firstName == null || firstName.trim().equals("")) {
						SC.say("ცარიელი სახელის შენახვა შეუძლებელია!");
						return;
					}
					com.smartgwt.client.rpc.RPCManager.startQueue();
					Record record = new Record();
					record.setAttribute("firstname", firstName);
					record.setAttribute("firstname_Id", firstNameId);
					record.setAttribute("loggedUserName", CommonSingleton
							.getInstance().getSessionPerson().getUser_name());

					DSRequest req = new DSRequest();
					if (firstNameId == null) {
						req.setAttribute("operationId", "addFirstName");
						firstNameDS.addData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								destroy();
							}
						}, req);
					} else {
						req.setAttribute("operationId", "updateFirstName");
						firstNameDS.updateData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								destroy();
							}
						}, req);
					}
					com.smartgwt.client.rpc.RPCManager.sendQueue();
				} catch (Exception e) {
					SC.say(e.toString());
				}
			}
		});
	}
}
