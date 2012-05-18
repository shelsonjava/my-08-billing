package com.info08.billing.callcenterbk.client.dialogs.survey;

import java.sql.Timestamp;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditSurveyCallCenterNews extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem call_center_news_textItem;
	private SelectItem callCenterWarningItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditSurveyCallCenterNews(ListGrid listGrid,
			ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addNews()
					: CallCenterBK.constants.editNews());

			setHeight(130);
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
			hLayout.setPadding(10);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			call_center_news_textItem = new TextItem();
			call_center_news_textItem.setTitle(CallCenterBK.constants.news());
			call_center_news_textItem.setName("call_center_news_textItem");
			call_center_news_textItem.setWidth(300);

			callCenterWarningItem = new SelectItem();
			callCenterWarningItem.setTitle(CallCenterBK.constants.newsType());
			callCenterWarningItem.setWidth(300);
			callCenterWarningItem.setName("callCenterWarningItem");
			callCenterWarningItem.setDefaultToFirstOption(true);
			callCenterWarningItem.setValueMap(ClientMapUtil.getInstance()
					.getNewsTypes());

			dynamicForm.setFields(call_center_news_textItem,
					callCenterWarningItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setHeight100();
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
			call_center_news_textItem.setValue(editRecord
					.getAttributeAsString("call_center_news_text"));
			callCenterWarningItem.setValue(editRecord
					.getAttributeAsInt("call_center_warning"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String call_center_news_text = call_center_news_textItem
					.getValueAsString();
			if (call_center_news_text == null
					|| call_center_news_text.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterType());
				return;
			}
			Long call_center_warning = new Long(
					callCenterWarningItem.getValueAsString());

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("call_center_news_text", call_center_news_text);
			record.setAttribute("call_center_warning", call_center_warning);
			record.setAttribute("call_center_news_date",
					new Timestamp(System.currentTimeMillis()));

			if (editRecord != null) {
				record.setAttribute("call_center_news_id",
						editRecord.getAttributeAsInt("call_center_news_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addCallCenterNews");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateCallCenterNews");
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
