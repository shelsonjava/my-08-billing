package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

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

	public TabFindCallCenterNews() {
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
		call_center_news_textItem
				.setTitle(CallCenterBK.constants.description());
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

		listGrid = new ListGrid();
		listGrid.setWidth(900);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(callCenterNewsDS);
		listGrid.setAutoFetchData(true);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllCallCenterNews");
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

		setPane(mainLayout);
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