package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.menu.CallCenterStackSelection;
import com.smartgwt.client.data.Criteria;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabFindCallCenterNews extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private TextItem call_center_news_textItem;
	private DateItem call_center_news_dateItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource callCenterNewsDS;
	private CallCenterStackSelection callCenterStackSelection;

	public TabFindCallCenterNews(
			CallCenterStackSelection callCenterStackSelection) {
		try {
			this.callCenterStackSelection = callCenterStackSelection;
			setTitle(CallCenterBK.constants.news());
			setCanClose(true);

			callCenterNewsDS = DataSource.get("CallCenterNewsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setNumCols(4);
			searchForm.setTitleWidth(75);
			mainLayout.addMember(searchForm);

			call_center_news_textItem = new TextItem();
			call_center_news_textItem.setTitle(CallCenterBK.constants
					.description());
			call_center_news_textItem.setName("call_center_news_textItem");
			call_center_news_textItem.setWidth(175);

			call_center_news_dateItem = new DateItem();
			call_center_news_dateItem.setName("call_center_news_dateItem");
			call_center_news_dateItem.setWidth(175);
			call_center_news_dateItem.setTitle(CallCenterBK.constants.date());
			call_center_news_dateItem.setUseTextField(true);

			SpacerItem spacerItem2 = new SpacerItem();
			spacerItem2.setWidth(500);
			spacerItem2.setName("spacerItem2");
			spacerItem2.setColSpan(4);

			searchForm.setFields(spacerItem2, call_center_news_textItem,
					call_center_news_dateItem);

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
			toolStrip.setWidth(900);
			ToolStripButton readBtn = new ToolStripButton();
			readBtn.setTitle(CallCenterBK.constants.read());
			toolStrip.addButton(readBtn);
			mainLayout.addMember(toolStrip);

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord gridRecord = (ListGridRecord) record;
					if (gridRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer call_center_warning = gridRecord
							.getAttributeAsInt("call_center_warning");
					if (call_center_warning != null
							&& call_center_warning.equals(1)) {
						return "color:red;";
					} else {
						Integer news_status = gridRecord
								.getAttributeAsInt("news_status");
						if (news_status != null && news_status.equals(0)) {
							return "color:red;";
						} else {
							return super.getCellCSSText(record, rowNum, colNum);
						}
					}
				};
			};

			listGrid.setWidth(900);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(callCenterNewsDS);
			listGrid.setAutoFetchData(true);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllCallCenterNews");
			Criteria criteria = new Criteria();
			criteria.setAttribute("user_id", CommonSingleton.getInstance()
					.getSessionPerson().getUser_id());
			listGrid.setCriteria(criteria);
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);

			ListGridField dt = new ListGridField("call_center_news_date_day",
					CallCenterBK.constants.date(), 70);
			dt.setAlign(Alignment.LEFT);

			ListGridField hr = new ListGridField("call_center_news_date_time",
					CallCenterBK.constants.hour(), 70);
			hr.setAlign(Alignment.LEFT);

			ListGridField mn = new ListGridField("call_center_news_text",
					CallCenterBK.constants.description());
			mn.setAlign(Alignment.LEFT);

			listGrid.setFields(dt, hr, mn);

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
					call_center_news_textItem.clearValue();
					call_center_news_dateItem.clearValue();
				}
			});

			call_center_news_textItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			readBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					try {
						readNews();
					} catch (Exception e) {
						e.printStackTrace();
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

	private void readNews() {
		try {
			ListGridRecord listGridRecord = listGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			Integer call_center_news_id = listGridRecord
					.getAttributeAsInt("call_center_news_id");
			if (call_center_news_id == null) {
				SC.say(CallCenterBK.constants.invalidRecord());
				return;
			}

			DataSource dataSource = DataSource.get("CallCenterNewsDS");

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("call_center_news_id", call_center_news_id);
			record.setAttribute("user_id", CommonSingleton.getInstance()
					.getSessionPerson().getUser_id());

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateUsrCCNews");
			dataSource.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					callCenterStackSelection.redrawNewsTitle();
					search();					
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String call_center_news_text = call_center_news_textItem
					.getValueAsString();
			if (call_center_news_text != null
					&& !call_center_news_text.trim().equals("")) {
				String tmp = call_center_news_text.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("call_center_news_text" + i, item);
					i++;
				}
			}
			try {
				Date call_center_news_date = call_center_news_dateItem
						.getValueAsDate();
				if (call_center_news_date != null) {
					criteria.setAttribute("call_center_news_date",
							call_center_news_date);
				}
			} catch (Exception e) {

			}
			criteria.setAttribute("user_id", CommonSingleton.getInstance()
					.getSessionPerson().getUser_id());

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllCallCenterNews");
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
