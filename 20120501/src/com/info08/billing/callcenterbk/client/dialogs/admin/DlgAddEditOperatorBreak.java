package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditOperatorBreak extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private ComboBoxItem operatorFullNameItem;
	private ComboBoxItem operatorUserNameItem;
	private DateItem operatorBreakDateItem;
	private TextItem operatorBreakTimeItem;

	private ListGridRecord operatorBreakRecord;
	private ListGrid operatorBreaksGrid;

	public DlgAddEditOperatorBreak(ListGrid operatorBreaksGrid,
			ListGridRecord operatorBreakRecord) {
		super();
		this.operatorBreakRecord = operatorBreakRecord;
		this.operatorBreaksGrid = operatorBreaksGrid;

		setTitle(operatorBreakRecord == null ? "ახალი განრიგის დამატება"
				: "განრიგის მოდიფიცირება");

		setHeight(190);
		setWidth(520);
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

		operatorBreakTimeItem = new TextItem();
		operatorBreakTimeItem.setTitle("შესვენების დრო");
		operatorBreakTimeItem.setWidth(300);
		operatorBreakTimeItem.setName("break_time");
		operatorBreakTimeItem.setMask("##:##-##:##");
		operatorBreakTimeItem.setHint("<nobr>HH:MM-HH:MM</nobr>");

		operatorFullNameItem = new ComboBoxItem();
		operatorFullNameItem.setWidth(300);
		operatorFullNameItem.setTitle("სახელი/გვარი");
		operatorFullNameItem.setName("fullUserName");
		operatorFullNameItem.setFetchMissingValues(true);
		operatorFullNameItem.setFilterLocally(false);
		operatorFullNameItem.setAddUnknownValues(false);

		operatorUserNameItem = new ComboBoxItem();
		operatorUserNameItem.setTitle("მომხმარებელი");
		operatorUserNameItem.setWidth(300);
		operatorUserNameItem.setName("user_name");
		operatorUserNameItem.setFetchMissingValues(true);
		operatorUserNameItem.setFilterLocally(false);
		operatorUserNameItem.setAddUnknownValues(false);

		operatorBreakDateItem = new DateItem();
		operatorBreakDateItem.setTitle("შესვენების დღე");
		operatorBreakDateItem.setWidth(300);
		operatorBreakDateItem.setValue(new Date());
		operatorBreakDateItem.setName("break_date");
		operatorBreakDateItem.setUseTextField(true);
		operatorBreakDateItem.setTextAlign(Alignment.LEFT);

		ClientUtils.fillCombo(operatorFullNameItem, "UsersDS",
				"getOperatorBreaksComboUserName", "user_id", "fullUserName");
		operatorUserNameItem.setTextMatchStyle(TextMatchStyle.SUBSTRING);
		ClientUtils.fillCombo(operatorUserNameItem, "UsersDS",
				"getOperatorBreaksComboUserName", "user_id", "user_name");
		operatorUserNameItem.setTextMatchStyle(TextMatchStyle.SUBSTRING);

		if (operatorBreakRecord != null) {
			Integer a = operatorBreakRecord.getAttributeAsInt("user_id");
			operatorFullNameItem.setValue(a.intValue());
			operatorUserNameItem.setValue(a.intValue());
			operatorBreakDateItem.setValue(operatorBreakRecord
					.getAttributeAsDate("break_date"));
			operatorBreakTimeItem.setValue(operatorBreakRecord
					.getAttributeAsString("break_time"));

		}

		operatorFullNameItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null) {
					operatorUserNameItem.setValue(event.getValue());
				}

			}
		});

		operatorUserNameItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null) {
					operatorFullNameItem.setValue(event.getValue());
				}

			}
		});

		dynamicForm.setFields(operatorFullNameItem, operatorUserNameItem,
				operatorBreakDateItem, operatorBreakTimeItem);

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
				saveOperatorBreak();
			}
		});

		addItem(hLayout);
	}

	private void saveOperatorBreak() {
		try {

			com.smartgwt.client.rpc.RPCManager.startQueue();

			Long user_id = new Long(operatorFullNameItem.getValueAsString());

			Date breakDate = operatorBreakDateItem.getValueAsDate();
			if (breakDate == null) {
				SC.say(CallCenterBK.constants.chooseDate());
				return;
			}

			String breakTime = operatorBreakTimeItem.getDisplayValue();
			if (breakTime == null || breakTime.equals("")) {
				SC.say(CallCenterBK.constants.chooseBreakTime());
				return;
			}

			Record record = new Record();

			record.setAttribute("user_id", user_id);
			record.setAttribute("break_date", breakDate);
			record.setAttribute("break_time", breakTime);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			if (operatorBreakRecord == null) {
				req.setAttribute("operationId", "addOperatorBreak");
				operatorBreaksGrid.addData(record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				record.setAttribute("operator_break_id", operatorBreakRecord
						.getAttributeAsInt("operator_break_id"));
				req.setAttribute("operationId", "updateOperatorBreak");
				operatorBreaksGrid.updateData(record, new DSCallback() {
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
