package com.info08.billing.callcenterbk.client.content.misc;

import java.util.Date;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.misc.DlgAddEditFact;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

public class TabFacts extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private SelectItem factStatusItem;
	private SelectItem factDescriptorItem;
	private SelectItem factTypeItem;
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
	// private ToolStripButton activateBtn;
	private ToolStripButton copyBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabFacts() {
		try {
			setTitle(CallCenterBK.constants.menuCalendar());
			setCanClose(true);

			datasource = DataSource.get("FactsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(780);
			searchForm.setTitleWidth(300);
			searchForm.setNumCols(3);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			DataSource factStatusDS = DataSource.get("FactStatusDS");
			factStatusItem = new SelectItem();
			factStatusItem.setTitle(CallCenterBK.constants.factStatus());
			factStatusItem.setWidth(250);
			factStatusItem.setName("fact_status_id");
			factStatusItem.setOptionDataSource(factStatusDS);
			factStatusItem.setValueField("fact_status_id");
			factStatusItem.setDisplayField("fact_status_name");
			factStatusItem.setAddUnknownValues(false);

			DataSource factsDescriptorDS = DataSource.get("FactsDescriptorDS");
			factDescriptorItem = new SelectItem();
			factDescriptorItem
					.setTitle(CallCenterBK.constants.factDescriptor());
			factDescriptorItem.setWidth(250);
			factDescriptorItem.setName("facts_descriptor_id");
			factDescriptorItem.setOptionDataSource(factsDescriptorDS);
			factDescriptorItem.setValueField("facts_descriptor_id");
			factDescriptorItem.setDisplayField("facts_descriptor_name");
			factDescriptorItem.setAddUnknownValues(false);

			factDescriptorItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					descriptorChanged(event.getValue());
				}
			});

			DataSource factTypeDS = DataSource.get("FactTypeDS");
			factTypeItem = new SelectItem();
			factTypeItem.setTitle(CallCenterBK.constants.factType());
			factTypeItem.setWidth(250);
			factTypeItem.setName("fact_type_id");
			factTypeItem.setOptionDataSource(factTypeDS);
			factTypeItem.setValueField("fact_type_id");
			factTypeItem.setDisplayField("fact_type_name");
			factTypeItem.setAddUnknownValues(false);

			descriptorChanged(100001);

			calendarDayItem = new DateItem();
			calendarDayItem.setTitle(CallCenterBK.constants.date());
			calendarDayItem.setWidth(250);
			calendarDayItem.setName("dateItem");
			calendarDayItem.setUseTextField(true);
			calendarDayItem.setColSpan(2);

			descriptionItem = new TextItem();
			descriptionItem.setTitle(CallCenterBK.constants.description());
			descriptionItem.setName("remark");
			descriptionItem.setWidth(750);
			descriptionItem.setColSpan(3);

			commentItem = new TextItem();
			commentItem.setTitle(CallCenterBK.constants.comment());
			commentItem.setName("additional_comment");
			commentItem.setWidth(750);
			commentItem.setColSpan(3);

			byCalendarDayItem = new CheckboxItem();
			byCalendarDayItem.setTitle(CallCenterBK.constants.searchByDate());
			byCalendarDayItem.setWidth(300);
			byCalendarDayItem.setName("buCalDaySearch");
			byCalendarDayItem.setValue(false);

			searchForm.setFields(factDescriptorItem, factTypeItem,
					factStatusItem, byCalendarDayItem, calendarDayItem,
					descriptionItem, commentItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(780);
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

			// activateBtn = new
			// ToolStripButton(CallCenterBK.constants.enable(),
			// "restoreIcon.gif");
			// activateBtn.setLayoutAlign(Alignment.LEFT);
			// activateBtn.setWidth(50);
			// toolStrip.addButton(activateBtn);

			toolStrip.addSeparator();

			copyBtn = new ToolStripButton(CallCenterBK.constants.copy(),
					"copy.png");
			copyBtn.setLayoutAlign(Alignment.LEFT);
			copyBtn.setWidth(50);
			toolStrip.addButton(copyBtn);

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
			listGrid.setHeight(260);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllFacts");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("fact_date").setTitle(
					CallCenterBK.constants.date());
			datasource.getField("fact_type_name").setTitle(
					CallCenterBK.constants.moonPhase());
			datasource.getField("fact_status_name").setTitle(
					CallCenterBK.constants.type());
			datasource.getField("sunup").setTitle(
					CallCenterBK.constants.order());
			datasource.getField("remark").setTitle(
					CallCenterBK.constants.description());

			datasource.getField("additional_comment").setTitle(
					CallCenterBK.constants.comment());

			ListGridField calendar_day = new ListGridField("fact_date",
					CallCenterBK.constants.date(), 150);
			ListGridField event = new ListGridField("fact_type_name",
					CallCenterBK.constants.moonPhase(), 150);
			ListGridField state = new ListGridField("fact_status_name",
					CallCenterBK.constants.type(), 80);
			ListGridField sun_rise = new ListGridField("sunup",
					CallCenterBK.constants.sunRise(), 100);
			ListGridField calendar_description = new ListGridField("remark",
					CallCenterBK.constants.description(), 250);

			calendar_day.setAlign(Alignment.LEFT);
			event.setAlign(Alignment.LEFT);
			state.setAlign(Alignment.CENTER);
			sun_rise.setAlign(Alignment.CENTER);

			listGrid.setFields(calendar_day, event, state, sun_rise,
					calendar_description);

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
					factStatusItem.clearValue();
					factTypeItem.clearValue();
					calendarDayItem.clearValue();
					descriptionItem.clearValue();
					commentItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditFact dlgEditSecCalendar = new DlgAddEditFact(
							listGrid, null, false);
					dlgEditSecCalendar.show();
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
					DlgAddEditFact dlgEditSecCalendar = new DlgAddEditFact(
							listGrid, listGridRecord, false);
					dlgEditSecCalendar.show();
				}
			});

			copyBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditFact dlgEditSecCalendar = new DlgAddEditFact(
							listGrid, listGridRecord, true);
					dlgEditSecCalendar.show();
				}
			});

			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					final Integer fact_id = listGridRecord
							.getAttributeAsInt("fact_id");
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(fact_id, 1);
									}
								}
							});
				}
			});
			// activateBtn.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// ListGridRecord listGridRecord = listGrid
			// .getSelectedRecord();
			// if (listGridRecord == null) {
			// SC.say(CallCenterBK.constants.pleaseSelrecord());
			// return;
			// }
			// Integer deleted = listGridRecord
			// .getAttributeAsInt("deleted");
			// if (deleted.equals(0)) {
			// SC.say(CallCenterBK.constants.recordAlrEnabled());
			// return;
			// }
			// final Integer calendar_id = listGridRecord
			// .getAttributeAsInt("calendar_id");
			// SC.ask(CallCenterBK.constants.askForEnable(),
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// changeStatus(calendar_id, 0);
			// }
			// }
			// });
			// }
			// });

			TabSet tabSet = new TabSet();
			tabSet.setWidth(780);
			Tab tabDetViewer = new Tab(CallCenterBK.constants.view());
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
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditFact dlgEditSecCalendar = new DlgAddEditFact(
							listGrid, listGridRecord, false);
					dlgEditSecCalendar.show();
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

	protected void descriptorChanged(Object value) {
		factTypeItem.setValue((Object) null);
		Criteria cr = new Criteria();
		cr.setAttribute("PKuni_", HTMLPanel.createUniqueId());
		cr.setAttribute("facts_descriptor_id", value);
		factTypeItem.setOptionCriteria(cr);
	}

	private void search() {
		try {

			String calendar_state_id = factStatusItem.getValueAsString();
			Criteria criteria = new Criteria();
			if (calendar_state_id != null
					&& !calendar_state_id.trim().equals("")) {
				criteria.setAttribute("fact_status_id", new Integer(
						calendar_state_id));
			}
			String calendar_event_id = factTypeItem.getValueAsString();
			if (calendar_event_id != null
					&& !calendar_event_id.trim().equals("")) {
				criteria.setAttribute("fact_type_id", new Integer(
						calendar_event_id));
			}
			boolean checkedDate = byCalendarDayItem.getValueAsBoolean();
			if (checkedDate) {
				Date calendar_day = calendarDayItem.getValueAsDate();
				if (calendar_day != null) {
					criteria.setAttribute("fact_date", calendar_day);
				}
			}
			String calendar_description = descriptionItem.getValueAsString();
			if (calendar_description != null
					&& !calendar_description.trim().equals("")) {
				criteria.setAttribute("remark", calendar_description);
			}
			String calendar_comment = commentItem.getValueAsString();
			if (calendar_comment != null && !calendar_comment.trim().equals("")) {
				criteria.setAttribute("additional_comment", calendar_comment);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllFacts");
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
			record.setAttribute("fact_id", calendar_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeFacts");
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
