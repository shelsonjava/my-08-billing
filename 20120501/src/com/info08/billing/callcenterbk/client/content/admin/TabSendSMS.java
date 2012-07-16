package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
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
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabSendSMS extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem smsNumber;
	private TextAreaItem sMSTexItem;
	private StaticTextItem stSymbols;

	private IButton sendButton;

	private DataSource smsLog;

	public TabSendSMS() {
		try {

			setTitle(CallCenterBK.constants.sendSMS());
			setCanClose(true);

			smsLog = DataSource.get("LogSessChDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			smsNumber = new TextItem();
			smsNumber.setTitle(CallCenterBK.constants.mobile());
			smsNumber.setWidth(250);
			smsNumber.setName("smsNumber");

			sMSTexItem = new TextAreaItem();
			sMSTexItem.setTitle(CallCenterBK.constants.mobOperatorIndex());
			sMSTexItem.setWidth(550);
			sMSTexItem.setName("gsmIndexItem");

			stSymbols = new StaticTextItem();
			stSymbols.setTitle(CallCenterBK.constants.count());
			stSymbols.setWidth(220);
			stSymbols.setName("stSymbols");

			searchForm.setFields(smsNumber, sMSTexItem, stSymbols);

			sMSTexItem.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					setSymbolCount();
				}
			});

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			sendButton = new IButton();
			sendButton.setTitle(CallCenterBK.constants.send());

			buttonLayout.setMembers(sendButton);
			mainLayout.addMember(buttonLayout);

			sendButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					send();
				}
			});

			setPane(mainLayout);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void send() {
		try {
			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();

			Record rec = new Record();
			rec.setAttribute("sms_text", sMSTexItem.getValue());
			rec.setAttribute("phone", smsNumber.getValue());
			rec.setAttribute("session_id", "-1");
			rec.setAttribute("service_id", "-1");
			rec.setAttribute("rec_user", serverSession.getUser_name());
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "LogSMS");

			smsLog.addData(rec, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					SC.say("SMS-ი გაიგზავნა!!!");
					sMSTexItem.clearValue();
					smsNumber.clearValue();
					setSymbolCount();
				}
			},dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void setSymbolCount() {
		Object obj = sMSTexItem.getValue();
		String sms = obj == null ? "" : obj.toString().trim();
		stSymbols.setValue(sms.length());
	}
}
