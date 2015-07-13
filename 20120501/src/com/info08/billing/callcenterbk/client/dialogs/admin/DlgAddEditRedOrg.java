package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.ArrayList;
import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxEvent;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItemDataChangedHandler;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditRedOrg extends MyWindow {

	private VLayout hLayout;

	private MyComboBoxItem myComboBoxItemOrg;

	private DynamicForm dynamicForm;

	private DateItem startDateItem;
	private DateItem endDateItem;
	private DateItem billingDateItem;
	private CheckboxItem debtItem;
	private CheckboxItem smsWarnItem;
	private TextAreaItem remarkItem;
	private TextItem tariffItem;
	private TextItem debtAmountItem;
	private SelectItem operatorItem;
	private TextItem contactPhonesItem;
	private TextItem debetAmountItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	private Integer organization_id;

	public DlgAddEditRedOrg(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(CallCenterBK.constants.red_orgs());

			setHeight(420);
			setWidth(820);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(true);
			setCanDragReposition(true);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			hLayout = new VLayout(5);
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
			MyComboBoxRecord organization_name = new MyComboBoxRecord(
					"organization_name",
					CallCenterBK.constants.parrentOrgName(), true);
			MyComboBoxRecord remark = new MyComboBoxRecord("remark",
					CallCenterBK.constants.comment(), false);
			MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord(
					"full_address_not_hidden",
					CallCenterBK.constants.address(), true);

			fieldRecords.add(organization_name);
			fieldRecords.add(full_address_not_hidden);
			fieldRecords.add(remark);

			String arrCapt[] = new String[2];
			arrCapt[0] = CallCenterBK.constants.orgNameFull();
			arrCapt[1] = CallCenterBK.constants.remark();
			myComboBoxItemOrg = new MyComboBoxItem("organization_name",
					CallCenterBK.constants.orgName(), 168, 769);

			myComboBoxItemOrg.setNameField("parrent_organization_id1");
			myComboBoxItemOrg.setMyDlgHeight(400);
			myComboBoxItemOrg.setMyDlgWidth(900);
			DataSource orgDS = DataSource.get("OrgDS");
			myComboBoxItemOrg.setMyDataSource(orgDS);
			myComboBoxItemOrg
					.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			myComboBoxItemOrg.setMyIdField("organization_id");
			myComboBoxItemOrg.setMyFields(fieldRecords);
			myComboBoxItemOrg.setMyChooserTitle(CallCenterBK.constants
					.organization());

			myComboBoxItemOrg.setMyDataSource(orgDS);
			myComboBoxItemOrg
					.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			myComboBoxItemOrg.setMyIdField("organization_id");
			// myComboBoxItemOrg.setMyDisplayField("organization_name","remark");
			myComboBoxItemOrg.setMyChooserTitle(CallCenterBK.constants
					.organization());
			myComboBoxItemOrg
					.addDataChangedHandler(new MyComboBoxItemDataChangedHandler() {
						@Override
						public void onDataChanged(MyComboBoxEvent event) {
							organization_id = event.getListGridRecord()
									.getAttributeAsInt("organization_id");
						}
					});
			hLayout.addMember(myComboBoxItemOrg);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(4);
			hLayout.addMember(dynamicForm);

			startDateItem = new DateItem();
			startDateItem.setTitle(CallCenterBK.constants.startDate());
			startDateItem.setWidth(250);
			startDateItem.setName("startDateItem");
			startDateItem.setUseTextField(true);

			endDateItem = new DateItem();
			endDateItem.setTitle(CallCenterBK.constants.endDate());
			endDateItem.setWidth(250);
			endDateItem.setName("endDateItem");
			endDateItem.setUseTextField(true);

			billingDateItem = new DateItem();
			billingDateItem.setTitle(CallCenterBK.constants.billing_date());
			billingDateItem.setWidth(250);
			billingDateItem.setName("billingDateItem");
			billingDateItem.setUseTextField(true);

			debtItem = new CheckboxItem();
			debtItem.setTitle(CallCenterBK.constants.debt());
			debtItem.setWidth(200);
			debtItem.setName("blockItem");

			smsWarnItem = new CheckboxItem();
			smsWarnItem.setTitle(CallCenterBK.constants.smsWarnable());
			smsWarnItem.setWidth(200);
			smsWarnItem.setName("smsWarnItem");

			remarkItem = new TextAreaItem();
			remarkItem.setTitle(CallCenterBK.constants.comment());
			remarkItem.setWidth(660);
			remarkItem.setHeight(100);
			remarkItem.setName("noteItem");
			remarkItem.setColSpan(4);

			contactPhonesItem = new TextItem();
			contactPhonesItem.setTitle(CallCenterBK.constants.contactPhone());
			contactPhonesItem.setWidth(660);
			contactPhonesItem.setName("contactPhonesItem");
			contactPhonesItem.setColSpan(4);

			tariffItem = new TextItem();
			tariffItem.setTitle(CallCenterBK.constants.tariff());
			tariffItem.setWidth(200);
			tariffItem.setName("normalPriceItem");
			tariffItem.setKeyPressFilter("[0-9\\.]");

			debtAmountItem = new TextItem();
			debtAmountItem.setTitle(CallCenterBK.constants.debt_amount());
			debtAmountItem.setWidth(200);
			debtAmountItem.setName("termAmountItem");
			debtAmountItem.setKeyPressFilter("[0-9\\.]");

			debetAmountItem = new TextItem();
			debetAmountItem.setTitle(CallCenterBK.constants.debet_amount());
			debetAmountItem.setWidth(250);
			debetAmountItem.setName("debetAmountItem");
			debetAmountItem.setKeyPressFilter("[0-9\\.]");

			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(250);
			operatorItem.setName("operatorItem");
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");

			dynamicForm.setFields(remarkItem, contactPhonesItem,
					billingDateItem, debtItem, startDateItem, smsWarnItem,
					endDateItem, tariffItem, operatorItem, debtAmountItem,
					debetAmountItem);

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
			Integer organization_id = editRecord
					.getAttributeAsInt("organization_id");
			if (organization_id != null) {
				myComboBoxItemOrg.setDataValue(organization_id);
				this.organization_id = organization_id;
			}
			String remark = editRecord.getAttributeAsString("remark");
			if (remark != null && !remark.trim().equals("")) {
				remarkItem.setValue(remark);
			}

			String contact_phones = editRecord
					.getAttributeAsString("contact_phones");
			if (contact_phones != null && !contact_phones.trim().equals("")) {
				contactPhonesItem.setValue(contact_phones);
			}

			Date start_date = editRecord.getAttributeAsDate("start_date");
			if (start_date != null) {
				startDateItem.setValue(start_date);
			}
			Date end_date = editRecord.getAttributeAsDate("end_date");
			if (end_date != null) {
				endDateItem.setValue(end_date);
			}
			Date billing_date = editRecord.getAttributeAsDate("billing_date");
			if (billing_date != null) {
				billingDateItem.setValue(billing_date);
			}
			Integer debt = editRecord.getAttributeAsInt("debt");
			if (debt != null && debt.equals(1)) {
				debtItem.setValue(true);
			}
			Integer sms_warning = editRecord.getAttributeAsInt("sms_warning");
			if (sms_warning != null && sms_warning.equals(1)) {
				smsWarnItem.setValue(true);
			}

			Double tariff = editRecord.getAttributeAsDouble("tariff");
			if (tariff != null) {
				tariffItem.setValue(tariff);
			}
			Double debt_amount = editRecord.getAttributeAsDouble("debt_amount");
			if (debt_amount != null) {
				debtAmountItem.setValue(debt_amount);
			}
			Double debet = editRecord.getAttributeAsDouble("debet");
			if (debet != null) {
				debetAmountItem.setValue(debet);
			}

			Integer operator_src = editRecord.getAttributeAsInt("operator_src");
			if (operator_src != null) {
				operatorItem.setValue(operator_src);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			if (organization_id == null) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}
			String remark = remarkItem.getValueAsString();
			if (remark == null || remark.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}
			String contact_phones = contactPhonesItem.getValueAsString();
			if (contact_phones == null || contact_phones.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}

			Date start_date = null;
			try {
				start_date = startDateItem.getValueAsDate();
				if (start_date == null) {
					SC.say(CallCenterBK.constants.plzEnterAllFields());
					return;
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}
			Date end_date = null;
			try {
				end_date = endDateItem.getValueAsDate();
				if (end_date == null) {
					SC.say(CallCenterBK.constants.plzEnterAllFields());
					return;
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}
			Date billing_date = null;
			try {
				billing_date = billingDateItem.getValueAsDate();
				if (billing_date == null) {
					SC.say(CallCenterBK.constants.plzEnterAllFields());
					return;
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}

			Integer debt = new Integer(0);
			Boolean isDebt = debtItem.getValueAsBoolean();
			if (isDebt != null && isDebt.booleanValue()) {
				debt = new Integer(1);
			}
			Integer sms_warning = new Integer(0);
			Boolean isSmsWarning = smsWarnItem.getValueAsBoolean();
			if (isSmsWarning != null && isSmsWarning.booleanValue()) {
				sms_warning = new Integer(1);
			}

			Integer operator_src = Integer.parseInt(operatorItem
					.getValueAsString());
			String sTariff = tariffItem.getValueAsString();
			if (sTariff == null || sTariff.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}
			String sDebtAmount = debtAmountItem.getValueAsString();
			if (sDebtAmount == null || sDebtAmount.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterAllFields());
				return;
			}

			Double tariff = Double.parseDouble(sTariff);
			Double debt_amount = Double.parseDouble(sDebtAmount);

			Integer pcorporate_client_id = null;

			Record record = new Record();
			if (editRecord != null) {
				pcorporate_client_id = editRecord
						.getAttributeAsInt("corporate_client_id");
				record.setAttribute("corporate_client_id", pcorporate_client_id);
			}
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("upd_user", loggedUser);
			record.setAttribute("organization_id", organization_id);

			record.setAttribute("remark", remark);
			record.setAttribute("start_date", start_date);
			record.setAttribute("end_date", end_date);
			record.setAttribute("billing_date", billing_date);
			record.setAttribute("debt", debt);
			record.setAttribute("sms_warning", sms_warning);
			record.setAttribute("tariff", tariff);
			record.setAttribute("debt_amount", debt_amount);
			record.setAttribute("operator_src", operator_src);
			record.setAttribute("contact_phones", contact_phones);

			String sDebetAmount = debetAmountItem.getValueAsString();
			if (sDebetAmount != null && !sDebetAmount.trim().equals("")) {
				record.setAttribute("debet", Double.parseDouble(sDebetAmount));
			}

			if (editRecord != null) {
				record.setAttribute("id", editRecord.getAttributeAsInt("id"));
			}
			saveRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveRecord(Record record) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addOrgPriorityList");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						listGrid.invalidateCache();
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateEditOrgPriorityList");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						listGrid.invalidateCache();
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
