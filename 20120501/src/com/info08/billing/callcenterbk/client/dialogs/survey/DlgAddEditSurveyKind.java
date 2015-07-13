package com.info08.billing.callcenterbk.client.dialogs.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditSurveyKind extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem surveyKindItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditSurveyKind(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addType()
					: CallCenterBK.constants.editType());

			setHeight(100);
			setWidth(430);
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
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			surveyKindItem = new TextItem();
			surveyKindItem.setTitle(CallCenterBK.constants.type());
			surveyKindItem.setName("survey_kind_name");
			surveyKindItem.setWidth(300);

			dynamicForm.setFields(surveyKindItem);

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
			surveyKindItem.setValue(editRecord.getAttributeAsString("survey_kind_name"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String survey_kind_name = surveyKindItem.getValueAsString();
			if (survey_kind_name == null || survey_kind_name.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterType());
				return;
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("deleted", 0);
			record.setAttribute("survey_kind_name", survey_kind_name);

			if (editRecord != null) {
				record.setAttribute("survey_kind_id",
						editRecord.getAttributeAsInt("survey_kind_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addSurveyKind");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateSurveyKind");
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
