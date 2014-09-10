package com.info08.billing.callcenterbk.client.content.stat;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgViewStatFullGraphAmountByMonth;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgViewStatFullGraphByMonth;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HeaderSpan;
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

public class TabStatFullByMonth extends Tab {

	private VLayout mainLayout;

	private TextItem ymStartItem;
	private TextItem ymEndItem;
	private SelectItem operatorItem;
	// actions
	private IButton findButton;
	private IButton clearButton;

	// search form
	private DynamicForm searchForm;

	private ListGrid listGrid;
	private DataSource statisticsDS;

	private ToolStripButton statN1Btn;
	private ToolStripButton statN2Btn;
	private ToolStripButton excelBtn;

	public TabStatFullByMonth(TabSet tabSet) {
		try {

			statisticsDS = DataSource.get("StatisticsDS");

			setTitle(CallCenterBK.constants.statisticFull());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(700);
			searchForm.setNumCols(2);
			searchForm.setTitleWidth(150);

			mainLayout.addMember(searchForm);

			ymStartItem = new TextItem();
			ymStartItem.setTitle(CallCenterBK.constants.ymStart());
			ymStartItem.setName("ymStartItem");
			ymStartItem.setWidth(200);

			ymEndItem = new TextItem();
			ymEndItem.setTitle(CallCenterBK.constants.ymEnd());
			ymEndItem.setName("ymEndItem");
			ymEndItem.setWidth(200);

			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(200);
			operatorItem.setName("operator_src");
			// operatorItem.setMultiple(true);
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");

			searchForm.setFields(operatorItem, ymStartItem, ymEndItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(700);
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

			statN1Btn = new ToolStripButton(CallCenterBK.constants.graph(),
					"stats.png");
			statN1Btn.setLayoutAlign(Alignment.LEFT);
			statN1Btn.setWidth(50);
			toolStrip.addButton(statN1Btn);

			statN2Btn = new ToolStripButton(
					CallCenterBK.constants.graphAmount(), "stats.png");
			statN2Btn.setLayoutAlign(Alignment.LEFT);
			statN2Btn.setWidth(50);
			toolStrip.addButton(statN2Btn);

			toolStrip.addSeparator();

			excelBtn = new ToolStripButton(
					CallCenterBK.constants.export_to_excel(), "excel.png");
			excelBtn.setLayoutAlign(Alignment.LEFT);
			excelBtn.setWidth(50);
			toolStrip.addButton(excelBtn);

			listGrid = new ListGrid() {
				@Override
				protected String getBaseStyle(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					Integer week_day = countryRecord
							.getAttributeAsInt("week_day");

					if (week_day != null && week_day.equals(1)) {
						return "myHighGridCell";
					} else {
						return super.getBaseStyle(record, rowNum, colNum);
					}
				}
			};

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(false);
			listGrid.setDataSource(statisticsDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllStatistics");
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

			ListGridField ym = new ListGridField("ym",
					CallCenterBK.constants.yearMonthOnly(), 100);

			ListGridField abonent_cnt = new ListGridField("abonent_cnt",
					CallCenterBK.constants.abonent(), 80);

			ListGridField org_contr_comm_cnt = new ListGridField(
					"org_contr_comm_cnt", CallCenterBK.constants.direct(), 100);

			ListGridField org_non_contr_cnt = new ListGridField(
					"org_non_contr_cnt", CallCenterBK.constants.nonDirect(),
					100);

			ListGridField org_contr_gov_cnt = new ListGridField(
					"org_contr_gov_cnt", CallCenterBK.constants.government(),
					100);

			ListGridField org_sum = new ListGridField("org_sum",
					CallCenterBK.constants.sum(), 100);

			ListGridField magti_cnt = new ListGridField("magti_cnt",
					CallCenterBK.constants.magti(), 100);

			ListGridField geocell_cnt = new ListGridField("geocell_cnt",
					CallCenterBK.constants.geocell(), 100);

			ListGridField beeline_cnt = new ListGridField("beeline_cnt",
					CallCenterBK.constants.beeline(), 100);

			ListGridField org_contr_email_srv_cnt = new ListGridField(
					"org_contr_email_srv_cnt", CallCenterBK.constants.direct(),
					100);

			ListGridField org_email_srv_cnt = new ListGridField(
					"org_email_srv_cnt", CallCenterBK.constants.nonDirect(),
					100);

			ListGridField all_sum = new ListGridField("all_sum",
					CallCenterBK.constants.sum(), 100);

			ListGridField all_amount = new ListGridField("all_amount",
					CallCenterBK.constants.amount(), 100);

			ListGridField cnt_percent = new ListGridField("cnt_percent",
					CallCenterBK.constants.percentage(), 100);

			ym.setAlign(Alignment.CENTER);
			abonent_cnt.setAlign(Alignment.CENTER);
			org_contr_comm_cnt.setAlign(Alignment.CENTER);
			org_non_contr_cnt.setAlign(Alignment.CENTER);
			org_contr_gov_cnt.setAlign(Alignment.CENTER);
			org_sum.setAlign(Alignment.CENTER);
			magti_cnt.setAlign(Alignment.CENTER);
			geocell_cnt.setAlign(Alignment.CENTER);
			beeline_cnt.setAlign(Alignment.CENTER);
			org_contr_email_srv_cnt.setAlign(Alignment.CENTER);
			org_email_srv_cnt.setAlign(Alignment.CENTER);
			all_sum.setAlign(Alignment.CENTER);
			all_amount.setAlign(Alignment.CENTER);
			cnt_percent.setAlign(Alignment.CENTER);

			ym.setSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.sum();
				}
			});
			ym.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.avarage();
				}
			});

			cnt_percent.setShowGridSummary(false);

			cnt_percent.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue()) + "%";
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			org_contr_comm_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			org_non_contr_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			org_contr_gov_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			org_sum.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			magti_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			geocell_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			beeline_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			org_contr_email_srv_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			org_email_srv_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			all_sum.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			all_amount.setCellFormatter(new CellFormatter() {
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

			abonent_cnt.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat
								.getFormat("################");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			abonent_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			abonent_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			org_contr_comm_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_contr_comm_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			org_non_contr_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_non_contr_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			org_contr_gov_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_contr_gov_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			org_sum.setSummaryFunction(SummaryFunctionType.SUM);
			org_sum.addSummaryFunction(SummaryFunctionType.AVG);

			magti_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			magti_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			geocell_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			geocell_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			beeline_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			beeline_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			org_contr_email_srv_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_contr_email_srv_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			org_email_srv_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_email_srv_cnt.addSummaryFunction(SummaryFunctionType.AVG);

			all_sum.setSummaryFunction(SummaryFunctionType.SUM);
			all_sum.addSummaryFunction(SummaryFunctionType.AVG);

			all_amount.setSummaryFunction(SummaryFunctionType.SUM);
			all_amount.addSummaryFunction(SummaryFunctionType.AVG);

			listGrid.setFields(ym, abonent_cnt, org_contr_comm_cnt,
					org_non_contr_cnt, org_contr_gov_cnt, org_sum, magti_cnt,
					geocell_cnt, beeline_cnt, org_contr_email_srv_cnt,
					org_email_srv_cnt, all_sum, all_amount, cnt_percent);

			listGrid.setHeaderSpans(
					new HeaderSpan(CallCenterBK.constants.organization(),
							new String[] { "org_contr_comm_cnt",
									"org_non_contr_cnt", "org_contr_gov_cnt",
									"org_sum" }),
					new HeaderSpan(CallCenterBK.constants.mobile(),
							new String[] { "magti_cnt", "geocell_cnt",
									"beeline_cnt" }), new HeaderSpan(
							CallCenterBK.constants.eMail(), new String[] {
									"org_contr_email_srv_cnt",
									"org_email_srv_cnt" }));

			mainLayout.addMember(listGrid);

			setPane(mainLayout);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search(false);
				}
			});

			excelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search(true);
				}
			});

			ymStartItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search(false);
					}
				}
			});
			ymEndItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search(false);
					}
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ymEndItem.clearValue();
					ymStartItem.clearValue();
				}
			});

			statN1Btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showGraphDialog();
				}
			});

			statN2Btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showGraphDialog1();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showGraphDialog1() {
		try {
			ListGridRecord records[] = listGrid.getRecords();
			if (records == null || records.length <= 0) {
				SC.say(CallCenterBK.constants.pleaseSearchData());
				return;
			}
			DlgViewStatFullGraphAmountByMonth dlgViewStatFullGraphAmount = new DlgViewStatFullGraphAmountByMonth(
					records);
			dlgViewStatFullGraphAmount.show();
		} catch (Exception e) {
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
			DlgViewStatFullGraphByMonth dlgViewStatFullGraph = new DlgViewStatFullGraphByMonth(
					records);
			dlgViewStatFullGraph.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search(boolean isExport) {
		try {
			String ymStart = ymStartItem.getValueAsString();
			if (ymStart == null || ymStart.trim().length() != 4) {
				SC.say(CallCenterBK.constants.plzEnterStartMonthCorrectly());
				return;
			}
			Integer ymStartI = null;
			try {
				ymStartI = Integer.parseInt(ymStart);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzEnterStartMonthCorrectly());
				return;
			}

			String ymEnd = ymEndItem.getValueAsString();
			if (ymEnd == null || ymEnd.trim().length() != 4) {
				SC.say(CallCenterBK.constants.plzEnterEndMonthCorrectly());
				return;
			}
			Integer ymEndI = null;
			try {
				ymEndI = Integer.parseInt(ymEnd);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzEnterEndMonthCorrectly());
				return;
			}

			final String operator_src = operatorItem.getValueAsString();
			if (operator_src == null || operator_src.trim().equals("")) {
				SC.say(CallCenterBK.constants.pleaseSelOnerecord());
				return;
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllStatisticsByMonth");
			Criteria criteria = new Criteria();
			criteria.setAttribute("ym_start", ymStartI);
			criteria.setAttribute("ym_end", ymEndI);
			criteria.setAttribute("operator_src",
					Integer.parseInt(operator_src));
			criteria.setAttribute("unique_id",
					"unique_" + HTMLPanel.createUniqueId());

			if (isExport) {
				dsRequest.setExportAs((ExportFormat) EnumUtil.getEnum(
						ExportFormat.values(), "xls"));
				dsRequest.setExportDisplay(ExportDisplay.DOWNLOAD);

				dsRequest.setExportFields(new String[] { "ym", "abonent_cnt",
						"org_contr_comm_cnt", "org_non_contr_cnt",
						"org_contr_gov_cnt", "org_sum", "magti_cnt",
						"geocell_cnt", "beeline_cnt",
						"org_contr_email_srv_cnt", "org_email_srv_cnt",
						"all_sum", "all_amount" });

				listGrid.getDataSource().exportData(criteria, dsRequest,
						new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
							}
						});

			} else {

				listGrid.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
					}
				}, dsRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
