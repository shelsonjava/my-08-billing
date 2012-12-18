package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
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
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgSendSurvey extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private ComboBoxItem surveyKindItem;
	private TextItem phoneItem;
	private TextItem contactPersonItem;
	private TextItem contactPhoneItem;
	private TextAreaItem messageItem;
	private ChargePanel chargePanel;
	private String sessionId;
	private String userName;

	public DlgSendSurvey(final ChargePanel chargePanel,
			Integer survey_kind_Id, String survey_descript) {
		this.chargePanel = chargePanel;
		try {
			setTitle(CallCenterBK.constants.sendSurvey());

			setHeight(290);
			setWidth(430);
			setShowMinimizeButton(false);
			setIsModal(false);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			setShowCloseButton(true);

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

			surveyKindItem = new ComboBoxItem();
			surveyKindItem.setTitle(CallCenterBK.constants.surveyKind());
			surveyKindItem.setName("survey_kind_name");
			surveyKindItem.setWidth(300);
			surveyKindItem.setFetchMissingValues(true);
			surveyKindItem.setFilterLocally(false);
			surveyKindItem.setAddUnknownValues(false);

			DataSource surveyKindDS = DataSource.get("SurveyKindDS");
			surveyKindItem.setOptionOperationId("searchSurveyKindsForCB");
			surveyKindItem.setOptionDataSource(surveyKindDS);
			surveyKindItem.setValueField("survey_kind_id");
			surveyKindItem.setDisplayField("survey_kind_name");
			surveyKindItem.setOptionCriteria(new Criteria());
			surveyKindItem.setAutoFetchData(false);

			surveyKindItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = surveyKindItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("survey_kind_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("survey_kind_id", nullO);
						}
					}
				}
			});

			if (survey_kind_Id != null) {
				surveyKindItem.setValue(survey_kind_Id);
			}

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setName("phoneItem");
			phoneItem.setWidth(300);
			phoneItem.setKeyPressFilter("[0-9]");

			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession != null && !serverSession.isWebSession()) {
				phoneItem.setValue(serverSession.getPhone());
				sessionId = serverSession.getSessionId();
				userName = serverSession.getUser_name();
			}

			contactPersonItem = new TextItem();
			contactPersonItem.setTitle(CallCenterBK.constants.contactPerson());
			contactPersonItem.setName("contactPersonItem");
			contactPersonItem.setWidth(300);

			contactPhoneItem = new TextItem();
			contactPhoneItem.setTitle(CallCenterBK.constants.contactPhone());
			contactPhoneItem.setName("contactPhoneItem");
			contactPhoneItem.setWidth(300);
			contactPhoneItem.setKeyPressFilter("[0-9]");

			messageItem = new TextAreaItem();
			messageItem.setTitle(CallCenterBK.constants.message());
			messageItem.setName("messageItem");
			messageItem.setWidth(300);

			if (survey_descript != null && !survey_descript.trim().equals("")) {
				messageItem.setValue(survey_descript);
			}

			dynamicForm.setFields(phoneItem, surveyKindItem,
					contactPersonItem, contactPhoneItem, messageItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.send());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					chargePanel.setDlgSendSurvey(null);
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
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}

	}

	public DlgSendSurvey(ChargePanel chargePanel) {
		this(chargePanel, null, null);
	}

	private void save() {
		try {
			if (sessionId == null || userName == null) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (sessionId.trim().equals("") || userName.trim().equals("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}

			String p_numb = phoneItem.getValueAsString();
			if (p_numb == null || p_numb.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			try {
				new Integer(p_numb);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.phoneMustBeNumeric());
				return;
			}

			String survey_descript = messageItem.getValueAsString();
			if (survey_descript == null) {
				survey_descript = "";
			}
			survey_descript = survey_descript.trim();
			String survey_phone = contactPhoneItem.getValueAsString();
			if (survey_phone == null) {
				survey_phone = "";
			}
			survey_phone = survey_phone.trim();

			String survey_person = contactPersonItem.getValueAsString();
			if (survey_person == null) {
				survey_person = "";
			}
			survey_person = survey_person.trim();

			String survey_kind_id_str = surveyKindItem.getValueAsString();
			if (survey_kind_id_str == null
					|| survey_kind_id_str.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.chooseSurveyKind());
				return;
			}
			Integer survey_kind_id = null;
			try {
				survey_kind_id = new Integer(survey_kind_id_str);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.incorrDiscTypeSelected());
				return;
			}
			if (survey_descript.equalsIgnoreCase("")
					&& survey_phone.equalsIgnoreCase("")
					&& survey_person.equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.enterSomeSurveyParam());
				return;
			}

			String survey_kind_name = surveyKindItem.getValueAsString();
			if (survey_kind_name == null || survey_kind_name.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterType());
				return;
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("p_numb", p_numb);
			record.setAttribute("session_call_id", sessionId);
			record.setAttribute("survey_descript", survey_descript);
			record.setAttribute("survey_phone", survey_phone);
			record.setAttribute("survey_person", survey_person);
			record.setAttribute("survey_kind_id", survey_kind_id);
			record.setAttribute("survey_creator", userName);

			DSRequest req = new DSRequest();
			DataSource surveyDS = DataSource.get("SurveyDS");
			req.setAttribute("operationId", "sendSurvey");
			surveyDS.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					chargePanel.setDlgSendSurvey(null);
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
