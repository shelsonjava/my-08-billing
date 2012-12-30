package com.info08.billing.callcenterbk.client.content.stat;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgViewDirOrgStats;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabStatByOrg extends Tab {

	private VLayout mainLayout;

	private DateItem dateItem;
	private SelectItem typeItem;
	private IButton findButton;
	private IButton clearButton;
	private DynamicForm searchForm;
	private ListGrid listGrid;
	private DataSource statisticsDS;

	private ToolStripButton viewDetail;

	public TabStatByOrg(TabSet tabSet) {
		try {

			statisticsDS = DataSource.get("StatisticsDS");

			setTitle(CallCenterBK.constants.statisticByOrgs());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(600);
			searchForm.setNumCols(2);

			mainLayout.addMember(searchForm);

			dateItem = new DateItem();
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setValue(new Date());
			dateItem.setWidth(200);

			typeItem = new SelectItem();
			typeItem.setTitle(CallCenterBK.constants.type());
			typeItem.setName("typeItem");
			typeItem.setWidth(200);
			typeItem.setValueMap(ClientMapUtil.getInstance()
					.getStatisticTypes());
			typeItem.setDefaultToFirstOption(true);

			searchForm.setFields(dateItem, typeItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(287);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			viewDetail = new ToolStripButton(
					CallCenterBK.constants.viewDetail(), "stats.png");
			viewDetail.setLayoutAlign(Alignment.LEFT);
			viewDetail.setWidth(50);
			toolStrip.addButton(viewDetail);

			listGrid = new ListGrid();

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(false);
			listGrid.setDataSource(statisticsDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchContrStatistics");
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowGridSummary(false);
			listGrid.setShowGroupSummary(false);
			listGrid.setShowAllRecords(false);
			listGrid.setShowRowNumbers(true);

			ListGridField organization = new ListGridField("organization_name",
					CallCenterBK.constants.organization());
			organization.setAlign(Alignment.LEFT);

			ListGridField call_count = new ListGridField("call_count",
					CallCenterBK.constants.count(), 300);
			call_count.setAlign(Alignment.LEFT);

			listGrid.setFields(organization, call_count);

			mainLayout.addMember(listGrid);

			setPane(mainLayout);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					dateItem.setValue(new Date());
				}
			});

			viewDetail.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					final Date date = dateItem.getValueAsDate();
					if (date == null) {
						SC.say(CallCenterBK.constants.invalidDate());
						return;
					}
					DateTimeFormat dateFormatter = DateTimeFormat
							.getFormat("yyMM");
					Integer ym = new Integer(dateFormatter.format(date));

					ListGridRecord record = listGrid.getSelectedRecord();
					if (record == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer organizationId = record
							.getAttributeAsInt("organization_id");
					DlgViewDirOrgStats dlgViewDirOrgStats = new DlgViewDirOrgStats(
							organizationId, ym);
					dlgViewDirOrgStats.show();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			final Date date = dateItem.getValueAsDate();
			if (date == null) {
				SC.say(CallCenterBK.constants.invalidDate());
				return;
			}
			Integer type = Integer.parseInt(typeItem.getValueAsString());
			DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyMM");
			Integer ym = new Integer(dateFormatter.format(date));
			DSRequest dsRequest = new DSRequest();
			if (type == 1) {
				dsRequest.setOperationId("searchContrStatistics");
			} else if (type == 2) {
				dsRequest.setOperationId("searchNonContrStatistics");
			} else if (type == 0) {
				dsRequest.setOperationId("searchContrStatistics");
			}
			Criteria criteria = new Criteria();
			criteria.setAttribute("ym", ym);
			criteria.setAttribute("type", type);
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
