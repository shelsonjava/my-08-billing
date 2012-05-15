package com.info08.billing.callcenterbk.client.dialogs.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.CommonFunctions;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgSendDiscSMS extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem phoneItem;
	private TextAreaItem smsItem;
	private CheckboxItem autoTextItem;

	private IButton sendSMSButton;

	private ListGridRecord record;

	public DlgSendDiscSMS(ListGridRecord record) {
		try {
			this.record = record;
			setTitle(CallCenterBK.constants.sendSMS());

			setHeight(280);
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
			dynamicForm.setNumCols(1);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(dynamicForm);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setName("phoneItem");
			phoneItem.setWidth("100%");
			phoneItem.setValue(record.getAttributeAsString("phone"));

			smsItem = new TextAreaItem();
			smsItem.setTitle(CallCenterBK.constants.sms());
			smsItem.setName("smsItem");
			smsItem.setWidth("100%");
			smsItem.setHeight(100);

			autoTextItem = new CheckboxItem();
			autoTextItem.setTitle(CallCenterBK.constants.autoText());
			autoTextItem.setName("autoTextItem");
			autoTextItem.setWidth("100%");

			dynamicForm.setFields(phoneItem, autoTextItem, smsItem);

			dynamicForm.focusInItem(smsItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			sendSMSButton = new IButton();
			sendSMSButton.setTitle(CallCenterBK.constants.send());
			sendSMSButton.setIcon("sms.png");
			sendSMSButton.setWidth(150);

			buttonLayout.addMember(sendSMSButton);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			buttonLayout.addMember(cancItem);

			hLayout.addMember(buttonLayout);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			sendSMSButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sendSurveySMS();
				}
			});

			autoTextItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					boolean isSelected = autoTextItem.getValueAsBoolean();
					if (isSelected) {
						smsItem.setValue(Constants.discSMSDefText);
					} else {
						smsItem.setValue("");
					}
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendSurveySMS() {
		try {
			String sms_text = smsItem.getValueAsString();
			if (sms_text == null || sms_text.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.smsTextIsEmpty());
				return;
			}

			String phone = phoneItem.getValueAsString();
			if (phone == null || phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidPhone());
				return;
			}
			if (!CommonFunctions.isPhoneMobile(phone)) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			phone = phone.trim();
			try {
				Long.parseLong(phone);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidPhone());
				return;
			}
			if (phone.length() != 9) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidPhone());
				return;
			}
			
			String session_call_id = record.getAttributeAsString("session_call_id");
			String rec_user = CommonSingleton.getInstance().getSessionPerson()
					.getUserName();

			CanvasDisableTimer.addCanvasClickTimer(sendSMSButton);

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("service_id", Constants.serviceOrganization);
			record.setAttribute("session_id", session_call_id);
			record.setAttribute("sms_text", sms_text);
			record.setAttribute("phone", phone);
			record.setAttribute("rec_user", rec_user);
			record.setAttribute("sms_type", 1);

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMSNew1");
			logSessChDS.addData(record, new DSCallback() {
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
