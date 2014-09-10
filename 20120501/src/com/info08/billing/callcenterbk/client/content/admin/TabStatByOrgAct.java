package com.info08.billing.callcenterbk.client.content.admin;

import java.util.Date;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboboxItemMultClass;
import com.info08.billing.callcenterbk.client.common.components.MyComboboxItemMultiple;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabStatByOrgAct extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private MyComboboxItemMultiple orgActivity;
	private DateItem startDateItem;
	private DateItem endDateItem;

	private IButton sendButton;
	private IButton clearButton;

	private ListGrid listGrid;

	public TabStatByOrgAct() {
		try {

			setTitle(CallCenterBK.constants.charges_by_org_act());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(600);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			orgActivity = new MyComboboxItemMultiple();
			orgActivity.setTitle(CallCenterBK.constants.activity());
			orgActivity.setWidth("100%");
			MyComboboxItemMultClass params = new MyComboboxItemMultClass(
					"OrgActDS", "searchAllBusinesActivitiesForCB",
					"org_activity_id", new String[] { "activity_description" },
					new String[] { CallCenterBK.constants.activity() }, null,
					CallCenterBK.constants.chooseActivity(), 700, 400,
					CallCenterBK.constants.thisRecordAlreadyChoosen());
			orgActivity.setParams(params);

			startDateItem = new DateItem();
			startDateItem.setTitle(CallCenterBK.constants.startDate());
			startDateItem.setName("startDateItem");
			startDateItem.setUseTextField(true);
			startDateItem.setWidth("100%");

			endDateItem = new DateItem();
			endDateItem.setTitle(CallCenterBK.constants.startDate());
			endDateItem.setName("endDateItem");
			endDateItem.setUseTextField(true);
			endDateItem.setWidth("100%");

			searchForm.setFields(orgActivity, startDateItem, endDateItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(600);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			sendButton = new IButton();
			sendButton.setTitle(CallCenterBK.constants.find());
			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());
			buttonLayout.setMembers(sendButton, clearButton);
			mainLayout.addMember(buttonLayout);

			sendButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					orgActivity.clearValues();
				}
			});

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			listGrid.setWidth(600);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(DataSource.get("OrgDS"));
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchOrgAcctivitiesStats");
			listGrid.setCanSort(true);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setDataPageSize(75);
			listGrid.setCanDragSelectText(true);

			ListGridField activity_description = new ListGridField(
					"activity_description", "დასახელება");

			ListGridField cnt = new ListGridField("cnt",
					CallCenterBK.constants.count(), 70);

			listGrid.setFields(activity_description, cnt);

			mainLayout.addMember(listGrid);
			setPane(mainLayout);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Date start_date = startDateItem.getValueAsDate();
			Date end_date = endDateItem.getValueAsDate();
			MyComboboxItemMultClass orgActivities = orgActivity.getParamClass();

			if (start_date == null || end_date == null || orgActivities == null
					|| orgActivities.getValueRecords() == null
					|| orgActivities.getValueRecords().length <= 0) {
				SC.say(CallCenterBK.constants.enter_search_criteria());
				return;
			}

			Criteria criteria = new Criteria();
			criteria.setAttribute("start_date", start_date);
			criteria.setAttribute("end_date", end_date);
			criteria.setAttribute("uniq_id", HTMLPanel.createUniqueId());

			Record records[] = orgActivities.getValueRecords();
			String orgActivitiesCrit = "";
			int i = 0;
			for (Record record : records) {
				Integer org_activity_id = record
						.getAttributeAsInt("org_activity_id");
				if (i > 0) {
					orgActivitiesCrit += ",";
				}
				orgActivitiesCrit += org_activity_id;
				i++;
			}
			criteria.setAttribute("orgActivitiesCrit", orgActivitiesCrit);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchOrgAcctivitiesStats");

			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						com.smartgwt.client.data.DSRequest request) {
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
