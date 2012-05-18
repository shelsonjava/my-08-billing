package com.info08.billing.callcenterbk.client.content.survey;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.survey.DlgAddEditSurveyCallCenterNews;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabSurveyCallCenterNews extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem call_center_news_textItem;
	private DateItem call_center_news_dateFromItem;
	private DateItem call_center_news_dateToItem;
	private SelectItem callCenterWarningItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabSurveyCallCenterNews() {
		try {
			setTitle(CallCenterBK.constants.menuSurveyKinds());
			setCanClose(true);

			datasource = DataSource.get("CallCenterNewsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			call_center_news_textItem = new TextItem();
			call_center_news_textItem.setTitle(CallCenterBK.constants.news());
			call_center_news_textItem.setName("call_center_news_text");
			call_center_news_textItem.setWidth(300);

			callCenterWarningItem = new SelectItem();
			callCenterWarningItem.setTitle(CallCenterBK.constants.newsType());
			callCenterWarningItem.setWidth(300);
			callCenterWarningItem.setName("callCenterWarningItem");
			callCenterWarningItem.setDefaultToFirstOption(true);
			callCenterWarningItem.setValueMap(ClientMapUtil.getInstance()
					.getSearchNewsTypes());

			call_center_news_dateFromItem = new DateItem();
			call_center_news_dateFromItem.setUseTextField(true);
			call_center_news_dateFromItem.setTitle(CallCenterBK.constants
					.dateFrom());
			call_center_news_dateFromItem
					.setName("call_center_news_dateFromItem");
			call_center_news_dateFromItem.setWidth(300);

			call_center_news_dateToItem = new DateItem();
			call_center_news_dateToItem.setUseTextField(true);
			call_center_news_dateToItem.setTitle(CallCenterBK.constants
					.dateTo());
			call_center_news_dateToItem.setName("call_center_news_dateToItem");
			call_center_news_dateToItem.setWidth(300);

			searchForm.setFields(call_center_news_textItem,
					callCenterWarningItem, call_center_news_dateFromItem,
					call_center_news_dateToItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(780);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			disableBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			disableBtn.setLayoutAlign(Alignment.LEFT);
			disableBtn.setWidth(50);
			toolStrip.addButton(disableBtn);

			toolStrip.addSeparator();

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
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth(780);
			listGrid.setHeight(430);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllCallCenterNews");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("call_center_news_text").setTitle(
					CallCenterBK.constants.type());

			ListGridField call_center_news_date = new ListGridField(
					"call_center_news_date", CallCenterBK.constants.date(), 150);

			ListGridField call_center_news_text = new ListGridField(
					"call_center_news_text", CallCenterBK.constants.news());

			call_center_news_text.setAlign(Alignment.LEFT);
			call_center_news_date.setAlign(Alignment.LEFT);

			listGrid.setFields(call_center_news_date, call_center_news_text);

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
					call_center_news_dateFromItem.clearValue();
					call_center_news_dateToItem.clearValue();
					callCenterWarningItem.setValue(-1);
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditSurveyCallCenterNews addEditSurveyCallCenterNews = new DlgAddEditSurveyCallCenterNews(
							listGrid, null);
					addEditSurveyCallCenterNews.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditSurveyCallCenterNews addEditSurveyCallCenterNews = new DlgAddEditSurveyCallCenterNews(
							listGrid, listGridRecord);
					addEditSurveyCallCenterNews.show();
				}
			});
			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(listGridRecord);
									}
								}
							});
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			Long call_center_warning = new Long(
					callCenterWarningItem.getValueAsString());
			criteria.setAttribute("call_center_warning", call_center_warning);

			String call_center_news_text = call_center_news_textItem
					.getValueAsString();
			if (call_center_news_text != null
					&& !call_center_news_text.trim().equals("")) {
				criteria.setAttribute("call_center_news_text",
						call_center_news_text);
			}
			try {
				Date call_center_news_date_from = call_center_news_dateFromItem
						.getValueAsDate();
				if (call_center_news_date_from != null) {
					criteria.setAttribute("call_center_news_date_from",
							call_center_news_date_from);
				}
				Date call_center_news_date_to = call_center_news_dateToItem
						.getValueAsDate();
				if (call_center_news_date_to != null) {
					criteria.setAttribute("call_center_news_date_to",
							call_center_news_date_to);
				}
			} catch (Exception e) {
			}

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
			SC.say(e.toString());
		}
	}

	private void delete(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			record.setAttribute("call_center_news_id",
					listGridRecord.getAttributeAsInt("call_center_news_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeCallCenterNews");
			listGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
