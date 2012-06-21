package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import java.util.Date;

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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditFreeOfChargePhone extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextAreaItem remarkItem;
	private TextItem phoneNumberItem;

	private DateItem startDateItem;
	private DateItem endDateItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditFreeOfChargePhone(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "უფასო ნომრის დამატება"
				: "უფასო ნომრის მოდიფიცირება");

		setHeight(280);
		setWidth(390);
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
		dynamicForm.setNumCols(1);
		hLayout.addMember(dynamicForm);

		phoneNumberItem = new TextItem();
		phoneNumberItem.setTitle("ნომერი");
		phoneNumberItem.setWidth(250);
		phoneNumberItem.setName("phone_number");

		remarkItem = new TextAreaItem();
		remarkItem.setTitle("კომენტარი");
		remarkItem.setWidth(250);
		remarkItem.setName("remark");

		startDateItem = new DateItem();
		startDateItem.setTitle("საწყისი თარიღი");
		startDateItem.setWidth(250);
		startDateItem.setValue(new Date());
		startDateItem.setName("start_date");
		startDateItem.setUseTextField(true);

		endDateItem = new DateItem();
		endDateItem.setTitle("საბოლოო თარიღი");
		endDateItem.setWidth(250);
		endDateItem.setValue(new Date());
		endDateItem.setName("end_date");
		endDateItem.setUseTextField(true);

		dynamicForm.setFields(phoneNumberItem, startDateItem, endDateItem,
				remarkItem);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
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
			phoneNumberItem.setValue(editRecord.getAttribute("phone_number"));
			remarkItem.setValue(editRecord.getAttribute("remark"));
			startDateItem.setValue(editRecord.getAttributeAsDate("start_date"));
			endDateItem.setValue(editRecord.getAttributeAsDate("end_date"));

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String phone_number = phoneNumberItem.getValueAsString();
			if (phone_number == null
					|| phone_number.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ნომერი !");
				return;
			}

			String remark = remarkItem.getValueAsString();
			if (remark == null || remark.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ კომენტარი !");
				return;
			}

			Date startDate = startDateItem.getValueAsDate();
			if (startDate == null) {
				SC.say("შეიყვანეთ საწყისი თარიღი !");
				return;
			}

			Date endDate = endDateItem.getValueAsDate();
			if (endDate == null) {
				SC.say("შეიყვანეთ საბოლოო თარიღი !");
				return;
			}

			if (startDate.after(endDate)) {
				SC.say("საბოლოო თარიღი უნდა იყოს საწყის თარიღზე მეტი!");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("phone_number", phone_number);
			record.setAttribute("remark", remark);
			record.setAttribute("start_date", startDate);
			record.setAttribute("end_date", endDate);

			if (editRecord != null) {
				record.setAttribute("phone_number_id",
						editRecord.getAttributeAsInt("phone_number_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addFreeOfChargePhone");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateFreeOfChargePhone");
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
