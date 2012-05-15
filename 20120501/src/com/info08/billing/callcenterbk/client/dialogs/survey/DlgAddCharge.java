package com.info08.billing.callcenterbk.client.dialogs.survey;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.shared.common.CommonFunctions;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddCharge extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem nmItem;
	private DateItem dateItem;
	private TextItem operatorItem;

	private ListGrid listGrid;
	private DataSource logSessChDSNew;

	private IButton findButton;
	private IButton clearButton;
	private IButton chargeButton;
	private IButton chargeButton1;

	public DlgAddCharge(String phone, String sOperator, Date callDate) {
		try {
			setTitle(CallCenterBK.constants.addCharge());

			setHeight(500);
			setWidth(600);
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
			dynamicForm.setTitleWidth(150);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			nmItem = new TextItem();
			nmItem.setTitle(CallCenterBK.constants.phone());
			nmItem.setName("phoneItem");
			nmItem.setWidth(250);
			nmItem.setValue(phone);

			dateItem = new DateItem();
			dateItem.setUseTextField(true);
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setWidth(250);
			dateItem.setValue(callDate);

			operatorItem = new TextItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setName("operatorItem");
			operatorItem.setWidth(250);
			operatorItem.setValue(sOperator);

			dynamicForm.setFields(nmItem, dateItem, operatorItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.LEFT);

			chargeButton = new IButton();
			chargeButton.setTitle(CallCenterBK.constants.charge());
			chargeButton.setIcon("moneySmall.png");
			chargeButton.setWidth(150);
			
			chargeButton1 = new IButton();
			chargeButton1.setTitle(CallCenterBK.constants.charge1());
			chargeButton1.setIcon("moneySmall.png");
			chargeButton1.setWidth(150);

			buttonLayout.addMember(chargeButton);
			//buttonLayout.addMember(chargeButton1);
			buttonLayout.addMember(new LayoutSpacer());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.addMember(findButton);
			buttonLayout.addMember(clearButton);
			hLayout.addMember(buttonLayout);

			logSessChDSNew = DataSource.get("LogSessChDSNew");

			listGrid = new ListGrid();
			listGrid.setWidth100();
			listGrid.setHeight100();
			// Criteria criteria = new Criteria();
			// criteria.setAttribute("phone", phone);
			// listGrid.setCriteria(criteria);
			listGrid.setFetchOperation("selectCallsByPhoneInCurrMonthNoGrouped");
			listGrid.setDataSource(logSessChDSNew);
			listGrid.setAutoFetchData(false);
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			// listGrid.setShowFilterEditor(true);
			// listGrid.setFilterOnKeypress(true);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);

			hLayout.addMember(listGrid);

			ListGridField phone_record = new ListGridField("phone",
					CallCenterBK.constants.phone());
			// phone_record.setCanFilter(false);

			ListGridField start_date = new ListGridField("start_date",
					CallCenterBK.constants.recDate(), 150);
			start_date.setAlign(Alignment.CENTER);
			// start_date.setCanFilter(false);

			ListGridField operator = new ListGridField("user_name",
					CallCenterBK.constants.operator(), 100);
			operator.setAlign(Alignment.CENTER);
			// operator.setCanFilter(true);

			ListGridField duration = new ListGridField("duration",
					CallCenterBK.constants.durationShort(), 130);
			duration.setAlign(Alignment.CENTER);
			// duration.setCanFilter(false);

			listGrid.setFields(phone_record, start_date, operator, duration);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			chargeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					makeCharge();
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					nmItem.clearValue();
					dateItem.clearValue();
				}
			});

			nmItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			operatorItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}

			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void makeCharge() {
		try {
			ListGridRecord listGridRecord = listGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			String session_id = listGridRecord
					.getAttributeAsString("session_id");
			Integer ym = listGridRecord.getAttributeAsInt("ym");
			String phone = listGridRecord.getAttributeAsString("phone");
			if (session_id == null || ym == null || phone == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidRecord());
				return;
			}
			
			if (!phone.startsWith("570") && CommonFunctions.isPhoneMobile(phone)) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.phoneIsMobileShort());
				return;
			}

			DlgAddChargeItem addChargeItem = new DlgAddChargeItem(this,
					listGrid, logSessChDSNew, listGridRecord);
			addChargeItem.show();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String phone = nmItem.getValueAsString();
			if (phone == null || phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.pleaseEnterPhone());
				return;
			}

			Date start_date = null;
			try {
				start_date = dateItem.getValueAsDate();
			} catch (Exception e) {

			}
			if (start_date != null) {
				criteria.setAttribute("start_date", start_date);
			}

			String user_name = operatorItem.getValueAsString();
			if (user_name != null && !user_name.trim().equals("")) {
				criteria.setAttribute("user_name", user_name);
			}

			criteria.setAttribute("phone", phone);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"selectCallsByPhoneInCurrMonthNoGrouped");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
