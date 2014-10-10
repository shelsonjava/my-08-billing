package com.info08.billing.callcenterbk.client.content.control;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabControlRemark extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem userSenderItem;
	private ComboBoxItem userReceiverItem;
	private DateItem dateItem;
	private SelectItem statusItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource warnDS;

	public TabControlRemark() {
		try {

			setTitle(CallCenterBK.constants.remarks());
			setCanClose(true);

			warnDS = DataSource.get("OperatorWarnsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setNumCols(2);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			dateItem = new DateItem();
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setWidth(250);
			dateItem.setUseTextField(true);
			dateItem.setValue(new Date());

			statusItem = new SelectItem();
			statusItem.setName("statusItem");
			statusItem.setWidth(250);
			statusItem.setTitle(CallCenterBK.constants.status());
			statusItem.setValueMap(ClientMapUtil.getInstance()
					.getWarnStatuses());
			statusItem.setDefaultToFirstOption(true);

			userSenderItem = new ComboBoxItem();
			userSenderItem.setTitle(CallCenterBK.constants.sender());
			userSenderItem.setWidth(250);
			userSenderItem.setName("user_sender_id");
			ClientUtils.fillCombo(userSenderItem, "UsersDS", "getUserForCombo",
					"user_id", "fullUserName");

			userReceiverItem = new ComboBoxItem();
			userReceiverItem.setTitle(CallCenterBK.constants.sender());
			userReceiverItem.setWidth(250);
			userReceiverItem.setName("user_receiver_id");
			ClientUtils.fillCombo(userReceiverItem, "UsersDS",
					"getUserForCombo", "user_id", "fullUserName");

			searchForm.setFields(userSenderItem, userReceiverItem, dateItem,
					statusItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(500);
			toolStrip.setPadding(5);

			ToolStripButton playButton = new ToolStripButton(
					CallCenterBK.constants.listenRecord(), "play.png");
			playButton.setLayoutAlign(Alignment.LEFT);
			playButton.setWidth(50);
			toolStrip.addButton(playButton);

			mainLayout.addMember(toolStrip);

			listGrid = new ListGrid() {

				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer status = countryRecord
							.getAttributeAsInt("hist_status_id");
					if (status == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					if (!status.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};

			};
			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(warnDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("findSMSLog");
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowRowNumbers(true);

			ListGridField warn_send_date = new ListGridField("warn_send_date",
					CallCenterBK.constants.date(), 100);
			warn_send_date.setAlign(Alignment.LEFT);

			ListGridField warn_sender = new ListGridField("warn_sender",
					CallCenterBK.constants.sender(), 200);
			warn_sender.setAlign(Alignment.LEFT);

			ListGridField operator = new ListGridField("operator",
					CallCenterBK.constants.receiver(), 200);
			operator.setAlign(Alignment.LEFT);

			ListGridField warning = new ListGridField("warning",
					CallCenterBK.constants.warning());
			operator.setAlign(Alignment.LEFT);

			ListGridField importantDescr = new ListGridField("importantDescr",
					CallCenterBK.constants.important(), 100);
			importantDescr.setAlign(Alignment.LEFT);

			ListGridField status_descr = new ListGridField("status_descr",
					CallCenterBK.constants.status(), 100);
			status_descr.setAlign(Alignment.LEFT);

			ListGridField phone_number = new ListGridField("phone_number",
					CallCenterBK.constants.phone(), 80);
			phone_number.setAlign(Alignment.LEFT);

			listGrid.setFields(warn_send_date, warn_sender, operator, warning,
					importantDescr, status_descr, phone_number);

			mainLayout.addMember(listGrid);

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					userSenderItem.clearValue();
					dateItem.setValue(new Date());
					statusItem.clearValue();
					userReceiverItem.clearValue();
				}
			});

			playButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							try {

								ListGridRecord listGridRecord = listGrid
										.getSelectedRecord();
								if (listGridRecord == null) {
									SC.say(CallCenterBK.constants
											.pleaseSelrecord());
									return;
								}
								String call_session_id = listGridRecord
										.getAttributeAsString("call_session_id");
								Date date = listGridRecord
										.getAttributeAsDate("call_start_date");
								ClientUtils.getURL(call_session_id, date);
							} catch (Exception e) {
								SC.say(e.toString());
							}
						}
					});

			setPane(mainLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String sender_user_id = userSenderItem.getValueAsString();
			if (sender_user_id != null && !sender_user_id.trim().equals("")) {
				criteria.setAttribute("sender_user_id", sender_user_id);
			}
			String receiver_user_id = userReceiverItem.getValueAsString();
			if (receiver_user_id != null && !receiver_user_id.trim().equals("")) {
				criteria.setAttribute("receiver_user_id", receiver_user_id);
			}
			try {
				Date message_sent_time = dateItem.getValueAsDate();
				if (message_sent_time != null) {
					criteria.setAttribute("date", message_sent_time);
				}
			} catch (Exception e) {
			}

			String str_status = statusItem.getValueAsString();
			if (str_status != null && !str_status.trim().equals("-1")) {
				Integer status = Integer.parseInt(str_status);
				criteria.setAttribute("delivered", status);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "operatorWarnsSeach");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
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
