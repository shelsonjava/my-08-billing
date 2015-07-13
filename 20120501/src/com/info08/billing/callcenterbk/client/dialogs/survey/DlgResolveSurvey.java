package com.info08.billing.callcenterbk.client.dialogs.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgResolveSurvey extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private SelectItem survResponseType;

	private IButton sendSMSButton;

	private ListGridRecord listGridRecord;
	private DlgSurveyManager surveyManager;

	public DlgResolveSurvey(DlgSurveyManager surveyyManager,
			ListGridRecord listGridRecord) {
		super();
		try {
			this.surveyManager = surveyyManager;
			this.listGridRecord = listGridRecord;
			setTitle(CallCenterBK.constants.resolveSurvey());

			setHeight(130);
			setWidth(400);
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

			survResponseType = new SelectItem();
			survResponseType.setTitle(CallCenterBK.constants.status());
			survResponseType.setName("discResponseType");
			survResponseType.setWidth("100%");

			DataSource surveyReplyTypeDS = DataSource.get("SurveyReplyTypeDS");
			survResponseType.setOptionOperationId("searchSurveyReplyTypesForCB");
			survResponseType.setOptionDataSource(surveyReplyTypeDS);
			survResponseType.setValueField("survey_reply_type_id");
			survResponseType.setDisplayField("survey_reply_type_name");
			survResponseType.setAutoFetchData(true);

			dynamicForm.setFields(survResponseType);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			sendSMSButton = new IButton();
			sendSMSButton.setTitle(CallCenterBK.constants.save());
			sendSMSButton.setWidth(100);

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
					saveSurvey();
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveSurvey() {
		try {
			String survey_reply_type_id_str = survResponseType.getValueAsString();
			if (survey_reply_type_id_str == null
					|| survey_reply_type_id_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.chooseStatus());
				return;
			}

			String rec_user = CommonSingleton.getInstance().getSessionPerson()
					.getUser_name();

			CanvasDisableTimer.addCanvasClickTimer(sendSMSButton);

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("survey_reply_type_id", new Integer(
					survey_reply_type_id_str));
			record.setAttribute("upd_user", rec_user);
			record.setAttribute("survey_id",
					listGridRecord.getAttributeAsInt("survey_id"));

			DSRequest req = new DSRequest();
			DataSource surveyDS = DataSource.get("SurveyDS");
			req.setAttribute("operationId", "resolveSurvey");
			surveyDS.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
					surveyManager.destroy();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
