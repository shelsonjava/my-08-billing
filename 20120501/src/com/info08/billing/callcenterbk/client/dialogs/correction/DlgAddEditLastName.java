package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditLastName extends MyWindow {

	private VLayout hLayout;
	private TextItem lastNameItem;
	private DynamicForm form;

	public DlgAddEditLastName(final Integer familyname_id, String lastName,
			final DataSource FamilyNameDS) {
		super();
		setWidth(400);
		setHeight(130);
		setTitle(familyname_id == null ? "ახალი გვარი" : "გვარის შესწორება");
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
		form.setDataSource(FamilyNameDS);

		lastNameItem = new TextItem();
		lastNameItem.setTitle("გვარი");
		lastNameItem.setValue(lastName);
		lastNameItem.setName("lastname");
		lastNameItem.setWidth("100%");

		HiddenItem lastNameIdItem = null;
		if (familyname_id != null) {
			lastNameIdItem = new HiddenItem();
			lastNameIdItem.setValue(familyname_id);
			lastNameIdItem.setName("familyname_id");
			lastNameIdItem.setVisible(false);
		}
		if (familyname_id == null) {
			form.setFields(lastNameItem);
		} else {
			form.setFields(lastNameItem, lastNameIdItem);
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
					String familyname = lastNameItem.getValueAsString();
					if (familyname == null || familyname.trim().equals("")) {
						SC.say("ცარიელი გვარის შენახვა შეუძლებელია!");
						return;
					}
					com.smartgwt.client.rpc.RPCManager.startQueue();
					Record record = new Record();
					record.setAttribute("familyname", familyname);
					record.setAttribute("familyname_id", familyname_id);
					record.setAttribute("loggedUserName", CommonSingleton
							.getInstance().getSessionPerson().getUser_name());

					DSRequest req = new DSRequest();
					if (familyname_id == null) {
						req.setAttribute("operationId", "addLastName");
						FamilyNameDS.addData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								destroy();
							}
						}, req);
					} else {
						req.setAttribute("operationId", "updateLastName");
						FamilyNameDS.updateData(record, new DSCallback() {
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
