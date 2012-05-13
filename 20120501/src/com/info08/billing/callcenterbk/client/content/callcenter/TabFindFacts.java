package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCalendar;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

public class TabFindFacts extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private SelectItem factStatusItem;
	private SelectItem factDescriptorItem;
	private SelectItem factTypeItem;
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
	private DataSource factsDS;

	public TabFindFacts() {

		setTitle(CallCenterBK.constants.calendar());
		setCanClose(true);

		factsDS = DataSource.get("FactsDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(500);
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
		factDescriptorItem.setTitle(CallCenterBK.constants.factDescriptor());
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

		descriptionItem = new TextItem();
		descriptionItem.setTitle(CallCenterBK.constants.descAndComment());
		descriptionItem.setName("descrItem");
		descriptionItem.setWidth(750);
		descriptionItem.setColSpan(3);

		dayOrMonthItem = new CheckboxItem();
		dayOrMonthItem.setName("dayOrMonthItem");
		dayOrMonthItem.setWidth(250);
		dayOrMonthItem.setTitle(CallCenterBK.constants.byMonth());

		searchForm.setFields(factDescriptorItem, factTypeItem, factStatusItem,
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
		listGrid.setDataSource(factsDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllFacts");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField fact_date = new ListGridField("ffact_date",
				CallCenterBK.constants.date(), 70);
		fact_date.setAlign(Alignment.LEFT);
		fact_date.setCanFilter(false);

		ListGridField fact_type_name = new ListGridField("fact_type_name",
				CallCenterBK.constants.moonPhase(), 150);
		fact_type_name.setAlign(Alignment.LEFT);
		fact_type_name.setCanFilter(false);

		ListGridField fact_status_name = new ListGridField("fact_status_name",
				CallCenterBK.constants.type(), 70);
		fact_status_name.setAlign(Alignment.LEFT);
		fact_status_name.setCanFilter(false);

		ListGridField sunup = new ListGridField("sunup",
				CallCenterBK.constants.sunRise(), 100);
		sunup.setCanFilter(false);

		ListGridField remark = new ListGridField("remark",
				CallCenterBK.constants.information(), 200);
		remark.setAlign(Alignment.LEFT);
		remark.setCanFilter(true);

		ListGridField additional_comment = new ListGridField(
				"additional_comment", CallCenterBK.constants.comment());
		additional_comment.setAlign(Alignment.LEFT);
		additional_comment.setCanFilter(true);
		additional_comment.setOptionTextMatchStyle(TextMatchStyle.EXACT);

		listGrid.setFields(fact_date, fact_type_name, fact_status_name, sunup,
				remark, additional_comment);

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
				factStatusItem.clearValue();
				factTypeItem.clearValue();
				dayOrMonthItem.clearValue();
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
						factsDS, listGrid.getSelectedRecord());
				dlgViewCalendar.show();
			}
		});
		setPane(mainLayout);
	}

	private void descriptorChanged(Object value) {
		factTypeItem.setValue((Object)null);
		Criteria cr = new Criteria();
		cr.setAttribute("PKuni_", HTMLPanel.createUniqueId());
		cr.setAttribute("facts_descriptor_id", value);
		factTypeItem.setOptionCriteria(cr);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String fact_status_id = factStatusItem.getValueAsString();
			if (fact_status_id != null && !fact_status_id.trim().equals("")) {
				criteria.setAttribute("fact_status_id", new Integer(
						fact_status_id));
			}
			String fact_type_id = factTypeItem.getValueAsString();
			if (fact_type_id != null && !fact_type_id.trim().equals("")) {
				criteria.setAttribute("fact_type_id", new Integer(fact_type_id));
			}
			try {
				Date fact_date = calendarDayItem.getValueAsDate();
				if (fact_date != null) {
					criteria.setAttribute("fact_date", fact_date);
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidDate());
				return;
			}

			String remark = descriptionItem.getValueAsString();
			if (remark != null && !remark.trim().equals("")) {
				String tmp = remark.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("critRemarc" + i, item);
					i++;
				}
			}
			Boolean byMonth = dayOrMonthItem.getValueAsBoolean();
			if (byMonth != null && byMonth.booleanValue()) {
				criteria.setAttribute("byMonth", 1);
			} else {
				criteria.setAttribute("byMonth", 0);
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
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
