package com.info08.billing.callcenter.client.content.misc;

import java.util.Date;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.misc.DlgAddEditChurchCalendar;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class TabChurchCalendar extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem calendarStateItem;
	private ComboBoxItem secCalendarTypeItem;
	private CheckboxItem byCalendarDayItem;
	private DateItem calendarDayItem;
	private TextItem descriptionItem;
	private TextItem commentItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton activateBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabChurchCalendar() {
		try {
			setTitle(CallCenter.constants.menuOrthCalendar());
			setCanClose(true);

			datasource = DataSource.get("CalChurchDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(780);
			searchForm.setTitleWidth(300);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			calendarStateItem = new ComboBoxItem();
			calendarStateItem.setTitle(CallCenter.constants.type());
			calendarStateItem.setWidth(250);
			calendarStateItem.setName("calendar_state_id");
			calendarStateItem.setValueMap(ClientMapUtil.getInstance()
					.getCalendarStates());
			calendarStateItem.setAddUnknownValues(false);

			secCalendarTypeItem = new ComboBoxItem();
			secCalendarTypeItem.setTitle(CallCenter.constants.chruchCalEvent());
			secCalendarTypeItem.setWidth(250);
			secCalendarTypeItem.setName("event");
			secCalendarTypeItem.setFetchMissingValues(true);
			secCalendarTypeItem.setFilterLocally(false);
			secCalendarTypeItem.setAddUnknownValues(false);
			secCalendarTypeItem.setOptionOperationId("searchChurchCalForCB");
			secCalendarTypeItem.setOptionDataSource(datasource);
			secCalendarTypeItem.setValueField("event_id");
			secCalendarTypeItem.setDisplayField("event");
			secCalendarTypeItem.setOptionCriteria(new Criteria());
			secCalendarTypeItem.setAutoFetchData(false);
			secCalendarTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = secCalendarTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("event_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("event_id", nullO);
						}
					}
				}
			});

			calendarDayItem = new DateItem();
			calendarDayItem.setTitle(CallCenter.constants.date());
			calendarDayItem.setWidth(250);
			calendarDayItem.setValue(new Date());
			calendarDayItem.setName("calendar_day");
			calendarDayItem.setHint(CallCenter.constants.choose());

			descriptionItem = new TextItem();
			descriptionItem.setTitle(CallCenter.constants.description());
			descriptionItem.setName("calendar_description");
			descriptionItem.setWidth(250);

			commentItem = new TextItem();
			commentItem.setTitle(CallCenter.constants.comment());
			commentItem.setName("calendar_comment");
			commentItem.setWidth(250);

			byCalendarDayItem = new CheckboxItem();
			byCalendarDayItem.setTitle(CallCenter.constants.searchByDate());
			byCalendarDayItem.setWidth(250);
			byCalendarDayItem.setName("buCalDaySearch");
			byCalendarDayItem.setValue(false);

			searchForm.setFields(calendarStateItem, secCalendarTypeItem,
					descriptionItem, commentItem, byCalendarDayItem,
					calendarDayItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(780);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(780);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenter.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenter.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			disableBtn = new ToolStripButton(CallCenter.constants.disable(),
					"deleteIcon.png");
			disableBtn.setLayoutAlign(Alignment.LEFT);
			disableBtn.setWidth(50);
			toolStrip.addButton(disableBtn);

			activateBtn = new ToolStripButton(CallCenter.constants.enable(),
					"restoreIcon.gif");
			activateBtn.setLayoutAlign(Alignment.LEFT);
			activateBtn.setWidth(50);
			toolStrip.addButton(activateBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth(780);
			listGrid.setHeight(300);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllChurchCalendars");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("calendar_day").setTitle(CallCenter.constants.date());
			datasource.getField("event").setTitle(CallCenter.constants.moonPhase());
			datasource.getField("state").setTitle(CallCenter.constants.type());
			datasource.getField("calendar_description").setTitle(CallCenter.constants.description());

			datasource.getField("rec_date").setTitle(CallCenter.constants.recDate());
			datasource.getField("rec_user").setTitle(CallCenter.constants.recUser());
			datasource.getField("upd_date").setTitle(CallCenter.constants.updDate());
			datasource.getField("upd_user").setTitle(CallCenter.constants.updUser());

			ListGridField calendar_day = new ListGridField("calendar_day",CallCenter.constants.date(), 150);
			ListGridField event = new ListGridField("event",CallCenter.constants.moonPhase(), 150);
			ListGridField state = new ListGridField("state",CallCenter.constants.type(), 80);
			ListGridField calendar_description = new ListGridField("calendar_description", CallCenter.constants.description(), 350);

			calendar_day.setAlign(Alignment.LEFT);
			event.setAlign(Alignment.LEFT);
			state.setAlign(Alignment.CENTER);

			listGrid.setFields(calendar_day, event, state,calendar_description);

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
					calendarStateItem.clearValue();
					secCalendarTypeItem.clearValue();
					calendarDayItem.clearValue();
					descriptionItem.clearValue();
					commentItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditChurchCalendar dlgAddEditChurchCalendar = new DlgAddEditChurchCalendar(
							listGrid, null);
					dlgAddEditChurchCalendar.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditChurchCalendar dlgAddEditChurchCalendar = new DlgAddEditChurchCalendar(
							listGrid, listGridRecord);
					dlgAddEditChurchCalendar.show();
				}
			});
			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenter.constants.recordAlrDisabled());
						return;
					}
					final Integer calendar_id = listGridRecord
							.getAttributeAsInt("calendar_id");
					SC.ask(CallCenter.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(calendar_id, 1);
									}
								}
							});
				}
			});
			activateBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted.equals(0)) {
						SC.say(CallCenter.constants.recordAlrEnabled());
						return;
					}
					final Integer calendar_id = listGridRecord
							.getAttributeAsInt("calendar_id");
					SC.ask(CallCenter.constants.askForEnable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(calendar_id, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(780);
			Tab tabDetViewer = new Tab(CallCenter.constants.view());
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(750);
			tabDetViewer.setPane(detailViewer);

			listGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(listGrid);
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditChurchCalendar dlgAddEditChurchCalendar = new DlgAddEditChurchCalendar(
							listGrid, listGridRecord);
					dlgAddEditChurchCalendar.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String calendar_state_id = calendarStateItem.getValueAsString();
			Criteria criteria = new Criteria();
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
			boolean checkedDate = byCalendarDayItem.getValueAsBoolean();
			if (checkedDate) {
				Date calendar_day = calendarDayItem.getValueAsDate();
				if (calendar_day != null) {
					criteria.setAttribute("calendar_day", calendar_day);
				}
			}
			String calendar_description = descriptionItem.getValueAsString();
			if (calendar_description != null
					&& !calendar_description.trim().equals("")) {
				criteria.setAttribute("calendar_description",
						calendar_description);
			}
			String calendar_comment = commentItem.getValueAsString();
			if (calendar_comment != null && !calendar_comment.trim().equals("")) {
				criteria.setAttribute("calendar_comment", calendar_comment);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllChurchCalendars");
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

	private void changeStatus(Integer calendar_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("calendar_id", calendar_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateChurchCalendarStatus");
			listGrid.updateData(record, new DSCallback() {
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
