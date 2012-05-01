package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCalendar;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindCalendar extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem calendarStateItem;
	private ComboBoxItem secCalendarTypeItem;
	private CheckboxItem dayOrMonthItem;
	private DateItem calendarDayItem;
	private TextItem descriptionItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource calSecDS;

	public TabFindCalendar() {

		setTitle(CallCenterBK.constants.calendar());
		setCanClose(true);

		calSecDS = DataSource.get("CalSecDS");

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

		secCalendarTypeItem = new ComboBoxItem();
		secCalendarTypeItem.setTitle(CallCenterBK.constants.moonPhase());
		secCalendarTypeItem.setWidth(250);
		secCalendarTypeItem.setName("calendar_event_id");
		secCalendarTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getSecCalendarTypes());
		secCalendarTypeItem.setAddUnknownValues(false);

		calendarDayItem = new DateItem();
		calendarDayItem.setTitle(CallCenterBK.constants.date());
		calendarDayItem.setWidth(250);
		calendarDayItem.setName("dateItem");
		calendarDayItem.setUseTextField(true);

		descriptionItem = new TextItem();
		descriptionItem.setTitle(CallCenterBK.constants.descAndComment());
		descriptionItem.setName("descrItem");
		descriptionItem.setWidth(500);
		descriptionItem.setColSpan(2);

		dayOrMonthItem = new CheckboxItem();
		dayOrMonthItem.setName("dayOrMonthItem");
		dayOrMonthItem.setWidth(250);
		dayOrMonthItem.setTitle(CallCenterBK.constants.byMonth());

		searchForm.setFields(secCalendarTypeItem, calendarStateItem,
				dayOrMonthItem, calendarDayItem, descriptionItem);

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

		listGrid = new ListGrid() {
			@Override
			protected Canvas getExpansionComponent(ListGridRecord record) {
				Canvas canvas = super.getExpansionComponent(record);
				canvas.setMargin(5);
				return canvas;
			}
		};
		listGrid.setWidth(1300);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(calSecDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllSecularCalendars");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField calendar_day = new ListGridField("fcalendar_day",
				CallCenterBK.constants.date(), 70);
		calendar_day.setAlign(Alignment.LEFT);
		calendar_day.setCanFilter(false);

		ListGridField event = new ListGridField("event",
				CallCenterBK.constants.moonPhase(), 150);
		event.setAlign(Alignment.LEFT);
		event.setCanFilter(false);

		ListGridField state = new ListGridField("state",
				CallCenterBK.constants.type(), 70);
		state.setAlign(Alignment.LEFT);
		state.setCanFilter(false);

		ListGridField sun_rise = new ListGridField("sun_rise",
				CallCenterBK.constants.sunRise(), 100);
		sun_rise.setCanFilter(false);

		ListGridField calendar_description = new ListGridField(
				"calendar_description", CallCenterBK.constants.information(), 200);
		calendar_description.setAlign(Alignment.LEFT);
		calendar_description.setCanFilter(true);

		ListGridField calendar_comment = new ListGridField("calendar_comment",
				CallCenterBK.constants.comment());
		calendar_comment.setAlign(Alignment.LEFT);
		calendar_comment.setCanFilter(true);
		calendar_comment.setOptionTextMatchStyle(TextMatchStyle.EXACT);

		listGrid.setFields(calendar_day, event, state, sun_rise,
				calendar_description, calendar_comment);

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
				calendarDayItem.clearValue();
				descriptionItem.clearValue();
				calendarStateItem.clearValue();
				secCalendarTypeItem.clearValue();
			}
		});

		descriptionItem.addKeyPressHandler(new KeyPressHandler() {
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
				DlgViewCalendar dlgViewCalendar = new DlgViewCalendar(listGrid,
						calSecDS, listGrid.getSelectedRecord());
				dlgViewCalendar.show();
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
			try {
				Date calendar_day = calendarDayItem.getValueAsDate();
				if (calendar_day != null) {
					criteria.setAttribute("calendar_day", calendar_day);
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidDate());
				return;
			}

			String calendar_description = descriptionItem.getValueAsString();
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
					criteria.setAttribute("calDescOrComm" + i, item);
					i++;
				}
			}
			Boolean byMonth = dayOrMonthItem.getValueAsBoolean();
			if (byMonth != null && byMonth.booleanValue()) {
				criteria.setAttribute("byMonth", 1);
			}else{
				criteria.setAttribute("byMonth", 0);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllSecularCalendars");
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
