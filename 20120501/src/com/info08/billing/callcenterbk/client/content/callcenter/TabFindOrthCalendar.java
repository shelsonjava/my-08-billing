package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrthCalendar;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindOrthCalendar extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem calendarStateItem;
	private ComboBoxItem secCalendarTypeItem;
	private CheckboxItem dayOrMonthItem;
	private DateItem dateItem;
	private TextItem descrItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource calChurchDS;

	public TabFindOrthCalendar() {

		setTitle(CallCenterBK.constants.orthCalendar());
		setCanClose(true);

		calChurchDS = DataSource.get("CalChurchDS");

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

		calendarStateItem = new ComboBoxItem();
		calendarStateItem.setTitle(CallCenterBK.constants.type());
		calendarStateItem.setWidth(250);
		calendarStateItem.setName("calendar_state_id");
		calendarStateItem.setValueMap(ClientMapUtil.getInstance()
				.getCalendarStates());
		calendarStateItem.setAddUnknownValues(false);

		calendarStateItem = new ComboBoxItem();
		calendarStateItem.setTitle(CallCenterBK.constants.type());
		calendarStateItem.setWidth(250);
		calendarStateItem.setName("calendar_state_id");
		calendarStateItem.setValueMap(ClientMapUtil.getInstance()
				.getCalendarStates());
		calendarStateItem.setAddUnknownValues(false);

		secCalendarTypeItem = new ComboBoxItem();
		secCalendarTypeItem.setTitle(CallCenterBK.constants.chruchCalEvent());
		secCalendarTypeItem.setWidth(250);
		secCalendarTypeItem.setName("event");
		secCalendarTypeItem.setFetchMissingValues(true);
		secCalendarTypeItem.setFilterLocally(false);
		secCalendarTypeItem.setAddUnknownValues(false);
		secCalendarTypeItem.setOptionOperationId("searchChurchCalForCB");
		secCalendarTypeItem.setOptionDataSource(calChurchDS);
		secCalendarTypeItem.setValueField("event_id");
		secCalendarTypeItem.setDisplayField("event");
		secCalendarTypeItem.setOptionCriteria(new Criteria());
		secCalendarTypeItem.setAutoFetchData(false);
		secCalendarTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = secCalendarTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("event_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("event_id", nullO);
					}
				}
			}
		});

		dateItem = new DateItem();
		dateItem.setTitle(CallCenterBK.constants.date());
		dateItem.setWidth(250);
		dateItem.setName("dateItem");
		dateItem.setUseTextField(true);

		descrItem = new TextItem();
		descrItem.setTitle(CallCenterBK.constants.description());
		descrItem.setName("descrItem");
		descrItem.setWidth(500);
		descrItem.setColSpan(2);

		dayOrMonthItem = new CheckboxItem();
		dayOrMonthItem.setName("dayOrMonthItem");
		dayOrMonthItem.setWidth(250);
		dayOrMonthItem.setTitle(CallCenterBK.constants.byMonth());

		searchForm.setFields(secCalendarTypeItem, calendarStateItem,
				dayOrMonthItem, dateItem, descrItem);

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
		listGrid.setWidth(750);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(calChurchDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllChurchCalendars");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField calendar_day = new ListGridField("fcalendar_day",
				CallCenterBK.constants.date(), 100);
		calendar_day.setAlign(Alignment.LEFT);
		calendar_day.setCanFilter(false);

		ListGridField event = new ListGridField("event",
				CallCenterBK.constants.category(), 100);
		event.setAlign(Alignment.LEFT);
		event.setCanFilter(false);

		ListGridField state = new ListGridField("state",
				CallCenterBK.constants.type(), 70);
		state.setAlign(Alignment.LEFT);
		state.setCanFilter(false);

		ListGridField calendar_description = new ListGridField(
				"calendar_description", CallCenterBK.constants.information());
		calendar_description.setAlign(Alignment.LEFT);
		calendar_description.setCanFilter(true);

		listGrid.setFields(calendar_day, event, state, calendar_description);

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
				dateItem.clearValue();
				descrItem.clearValue();
				calendarStateItem.clearValue();
				secCalendarTypeItem.clearValue();
			}
		});

		descrItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewOrthCalendar dlgViewOrthCalendar = new DlgViewOrthCalendar(
						listGrid, calChurchDS, listGrid.getSelectedRecord());
				dlgViewOrthCalendar.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String calendar_state_id = calendarStateItem.getValueAsString();
			if (calendar_state_id != null
					&& !calendar_state_id.trim().equals("")) {
				criteria.setAttribute("calendar_state_id", new Integer(
						calendar_state_id));
			}
			String calendar_event_id = secCalendarTypeItem.getValueAsString();
			if (calendar_event_id != null
					&& !calendar_event_id.trim().equals("")) {
				criteria.setAttribute("calendar_event_id", new Integer(
						calendar_event_id));
			}

			String calendar_description = descrItem.getValueAsString();
			if (calendar_description != null
					&& !calendar_description.trim().equals("")) {
				
				String tmp = calendar_description.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("calendar_description" + i, item);
					i++;
				}
			}
			try {
				Date calendar_day = dateItem.getValueAsDate();
				if (calendar_day != null) {
					criteria.setAttribute("calendar_day", calendar_day);
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidDate());
				return;
			}
			Boolean byMonth = dayOrMonthItem.getValueAsBoolean();
			if (byMonth != null && byMonth.booleanValue()) {
				criteria.setAttribute("byMonth", 1);
			}else{
				criteria.setAttribute("byMonth", 0);
			}
			
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllChurchCalendars");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
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
