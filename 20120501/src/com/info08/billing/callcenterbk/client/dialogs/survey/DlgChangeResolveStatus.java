package com.info08.billing.callcenterbk.client.dialogs.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgChangeResolveStatus extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private SelectItem survResponseType;

	private IButton sendSMSButton;

	private ListGridRecord listGridRecord;
	private ListGrid listGrid;

	public DlgChangeResolveStatus(ListGrid listGrid,
			ListGridRecord listGridRecord) {
		try {
			this.listGridRecord = listGridRecord;
			this.listGrid = listGrid;
			setTitle(CallCenterBK.constants.changeStatus());

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
			survResponseType.setName("survResponseType");
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

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = listGridRecord;

			record.setAttribute("survey_reply_type_id", new Integer(survey_reply_type_id_str));
			record.setAttribute("loggedUserName", rec_user);
			record.setAttribute("survey_id", listGridRecord.getAttributeAsInt("survey_id"));

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateSurveyItem");
			listGrid.getDataSource().updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
					Record records[] = response.getData();
					Record record = records[0];
					String attrs[] = record.getAttributes();
					String txt = "";
					for (String string : attrs) {
						String value = record.getAttributeAsString(string);
						txt += "AttrName: " + string + ", Value = " + value
								+ "\n";
					}
					SC.say("TXT == "+txt);
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
