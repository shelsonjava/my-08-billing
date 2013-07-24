package com.info08.billing.callcenterbk.client.content.stat;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgViewStatByBillingCompGraph;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabStatByBillingComps extends Tab {

	private VLayout mainLayout;

	private DateItem dateItem;
	private SelectItem operatorItem;
	// private DateItem dateItemEnd;
	// actions
	private IButton findButton;
	private IButton clearButton;

	// search form
	private DynamicForm searchForm;

	private ListGrid listGrid;
	private DataSource statsByBillingpDS;

	private ToolStripButton statN1Btn;

	public TabStatByBillingComps(TabSet tabSet) {
		try {

			statsByBillingpDS = DataSource.get("StatisticsByBillingCompDS");

			setTitle(CallCenterBK.constants.statisticByBillingComp());
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

			Date date = new Date();
			CalendarUtil.addMonthsToDate(date, -1);

			dateItem = new DateItem();
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setValue(date);
			dateItem.setWidth(200);

			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(200);
			operatorItem.setName("operator_src");
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");

			searchForm.setFields(operatorItem, dateItem);

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
			toolStrip.setWidth(500);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			statN1Btn = new ToolStripButton(CallCenterBK.constants.graph(),
					"stats.png");
			statN1Btn.setLayoutAlign(Alignment.LEFT);
			statN1Btn.setWidth(50);
			toolStrip.addButton(statN1Btn);

			listGrid = new ListGrid();

			listGrid.setWidth(500);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(false);
			listGrid.setDataSource(statsByBillingpDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchStatsByBillingComp");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setHeaderHeight(40);
			listGrid.setShowGridSummary(true);
			listGrid.setShowGroupSummary(false);
			listGrid.setShowAllRecords(true);

			ListGridField billing_company_name = new ListGridField(
					"billing_company_name",
					CallCenterBK.constants.billingComps(), 250);

			ListGridField calls_cnt = new ListGridField("calls_cnt",
					CallCenterBK.constants.count(), 100);

			ListGridField calls_amm = new ListGridField("calls_amm",
					CallCenterBK.constants.amount(), 100);

			billing_company_name.setAlign(Alignment.LEFT);
			calls_cnt.setAlign(Alignment.CENTER);
			calls_amm.setAlign(Alignment.CENTER);

			billing_company_name.setSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.sum();
				}
			});
			billing_company_name.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.avarage();
				}
			});

			calls_amm.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat.getFormat("#,##0.00");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			calls_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat.getFormat("#,##0.00");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			calls_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			calls_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			calls_amm.setSummaryFunction(SummaryFunctionType.SUM);
			calls_amm.addSummaryFunction(SummaryFunctionType.AVG);

			listGrid.setFields(billing_company_name, calls_cnt, calls_amm);

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

			statN1Btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showGraphDialog();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showGraphDialog() {
		try {
			ListGridRecord records[] = listGrid.getRecords();
			if (records == null || records.length <= 0) {
				SC.say(CallCenterBK.constants.pleaseSearchData());
				return;
			}
			DlgViewStatByBillingCompGraph dlgViewStatFullGraph = new DlgViewStatByBillingCompGraph(
					records);
			dlgViewStatFullGraph.show();
		} catch (Exception e) {
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
			DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyMM");
			final Integer ym = new Integer(dateFormatter.format(date));

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchStatsByBillingComp");
			Criteria criteria = new Criteria();
			criteria.setAttribute("ym", ym);
			Integer operator_src = Integer.parseInt(operatorItem
					.getValueAsString());
			criteria.setAttribute("operator_src", operator_src);
			criteria.setAttribute("uqdfsd", HTMLPanel.createUniqueId());

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
