package com.info08.billing.callcenter.client.content.stat;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.admin.DlgViewStatFullGraph;
import com.info08.billing.callcenter.client.dialogs.admin.DlgViewStatFullGraphAmount;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
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

public class TabStatFull extends Tab {

	private VLayout mainLayout;

	private DateItem dateItem;
	// private DateItem dateItemEnd;
	// actions
	private IButton findButton;
	private IButton clearButton;

	// search form
	private DynamicForm searchForm;

	private ListGrid listGrid;
	private DataSource statisticsDS;

	private ToolStripButton statN1Btn;
	private ToolStripButton statN2Btn;
	private ToolStripButton printPreviewBtn;

	private Record prevMonthRecord;

	public TabStatFull(TabSet tabSet) {
		try {

			statisticsDS = DataSource.get("StatisticsDS");

			setTitle(CallCenter.constants.statisticFull());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(600);
			searchForm.setNumCols(4);

			mainLayout.addMember(searchForm);

			dateItem = new DateItem();
			dateItem.setTitle(CallCenter.constants.date());
			dateItem.setName("dateItem");
			dateItem.setValue(new Date());
			dateItem.setWidth(200);

			searchForm.setFields(dateItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(287);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			statN1Btn = new ToolStripButton(CallCenter.constants.graph(),
					"stats.png");
			statN1Btn.setLayoutAlign(Alignment.LEFT);
			statN1Btn.setWidth(50);
			toolStrip.addButton(statN1Btn);

			statN2Btn = new ToolStripButton(CallCenter.constants.graphAmount(),
					"stats.png");
			statN2Btn.setLayoutAlign(Alignment.LEFT);
			statN2Btn.setWidth(50);
			toolStrip.addButton(statN2Btn);

			printPreviewBtn = new ToolStripButton(
					CallCenter.constants.printPreview(), "printer.png");
			printPreviewBtn.setLayoutAlign(Alignment.LEFT);
			printPreviewBtn.setWidth(50);
			toolStrip.addButton(printPreviewBtn);

			// printPreviewButton.addClickHandler(new ClickHandler() {
			// public void onClick(ClickEvent event) {
			// Canvas.showPrintPreview(printContainer);
			// }
			// });

			listGrid = new ListGrid() {
				// @Override
				// protected String getBaseStyle(ListGridRecord record,
				// int rowNum, int colNum) {
				// ListGridRecord countryRecord = (ListGridRecord) record;
				// Integer week_day = countryRecord
				// .getAttributeAsInt("week_day");
				//
				// if (week_day != null && week_day.equals(1)) {
				// return "myHighGridCell";
				// } else {
				// return super.getBaseStyle(record, rowNum, colNum);
				// }
				// }
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {

					ListGridRecord listGridRecord = (ListGridRecord) record;
					if (listGridRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer week_day = listGridRecord
							.getAttributeAsInt("week_day");
					if (week_day != null && week_day.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
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

			ListGridField stat_date = new ListGridField("stat_date",
					CallCenter.constants.date(), 80);

			ListGridField abonent_cnt = new ListGridField("abonent_cnt",
					CallCenter.constants.abonent(), 80);

			ListGridField org_contr_comm_cnt = new ListGridField(
					"org_contr_comm_cnt", CallCenter.constants.direct(), 100);

			ListGridField org_non_contr_cnt = new ListGridField(
					"org_non_contr_cnt", CallCenter.constants.nonDirect(), 100);

			ListGridField org_contr_gov_cnt = new ListGridField(
					"org_contr_gov_cnt", CallCenter.constants.government(), 100);

			ListGridField org_sum = new ListGridField("org_sum",
					CallCenter.constants.sum(), 100);

			ListGridField magti_cnt = new ListGridField("magti_cnt",
					CallCenter.constants.magti(), 100);

			ListGridField geocell_cnt = new ListGridField("geocell_cnt",
					CallCenter.constants.geocell(), 100);

			ListGridField beeline_cnt = new ListGridField("beeline_cnt",
					CallCenter.constants.beeline(), 100);

			ListGridField org_contr_email_srv_cnt = new ListGridField(
					"org_contr_email_srv_cnt", CallCenter.constants.direct(),
					100);

			ListGridField org_email_srv_cnt = new ListGridField(
					"org_email_srv_cnt", CallCenter.constants.nonDirect(), 100);

			ListGridField all_sum = new ListGridField("all_sum",
					CallCenter.constants.sum(), 100);

			ListGridField all_amount = new ListGridField("all_amount",
					CallCenter.constants.amount(), 100);

			stat_date.setAlign(Alignment.CENTER);
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

			stat_date.setSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenter.constants.sum();
				}
			});
			stat_date.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenter.constants.avarage();
				}
			});
			stat_date.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenter.constants.prevMonthPercent();
				}
			});

			org_contr_comm_cnt.setCellFormatter(new CellFormatter() {
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

			org_non_contr_cnt.setCellFormatter(new CellFormatter() {
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

			org_contr_gov_cnt.setCellFormatter(new CellFormatter() {
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

			org_sum.setCellFormatter(new CellFormatter() {
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

			magti_cnt.setCellFormatter(new CellFormatter() {
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

			geocell_cnt.setCellFormatter(new CellFormatter() {
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

			beeline_cnt.setCellFormatter(new CellFormatter() {
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

			org_contr_email_srv_cnt.setCellFormatter(new CellFormatter() {
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

			org_email_srv_cnt.setCellFormatter(new CellFormatter() {
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

			all_sum.setCellFormatter(new CellFormatter() {
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
						NumberFormat nf = NumberFormat.getFormat("#,##0.00");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			abonent_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			abonent_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			abonent_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "abonent_cnt");
				}
			});

			org_contr_comm_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_contr_comm_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			org_contr_comm_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records,
							"org_contr_comm_cnt");
				}
			});

			org_non_contr_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_non_contr_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			org_non_contr_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "org_non_contr_cnt");
				}
			});

			org_contr_gov_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_contr_gov_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			org_contr_gov_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "org_contr_gov_cnt");
				}
			});

			org_sum.setSummaryFunction(SummaryFunctionType.SUM);
			org_sum.addSummaryFunction(SummaryFunctionType.AVG);
			org_sum.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "org_sum");
				}
			});

			magti_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			magti_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			magti_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "magti_cnt");
				}
			});

			geocell_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			geocell_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			geocell_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "geocell_cnt");
				}
			});

			beeline_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			beeline_cnt.addSummaryFunction(SummaryFunctionType.AVG);
			beeline_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "beeline_cnt");
				}
			});

			org_contr_email_srv_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_contr_email_srv_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary1(records,
							"org_contr_email_srv_cnt");
				}
			});
			org_contr_email_srv_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records,
							"org_contr_email_srv_cnt");
				}
			});

			org_email_srv_cnt.setSummaryFunction(SummaryFunctionType.SUM);
			org_email_srv_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary1(records,
							"org_email_srv_cnt");
				}
			});
			org_email_srv_cnt.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "org_email_srv_cnt");
				}
			});

			all_sum.setSummaryFunction(SummaryFunctionType.SUM);
			all_sum.addSummaryFunction(SummaryFunctionType.AVG);
			all_sum.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "all_sum");
				}
			});

			all_amount.setSummaryFunction(SummaryFunctionType.SUM);
			all_amount.addSummaryFunction(SummaryFunctionType.AVG);
			all_amount.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return getCustomAvarageSummary(records, "all_amount");
				}
			});

			listGrid.setFields(stat_date, abonent_cnt, org_contr_comm_cnt,
					org_non_contr_cnt, org_contr_gov_cnt, org_sum, magti_cnt,
					geocell_cnt, beeline_cnt, org_contr_email_srv_cnt,
					org_email_srv_cnt, all_sum, all_amount);

			listGrid.setHeaderSpans(
					new HeaderSpan(CallCenter.constants.organization(),
							new String[] { "org_contr_comm_cnt",
									"org_non_contr_cnt", "org_contr_gov_cnt",
									"org_sum" }),
					new HeaderSpan(CallCenter.constants.mobile(), new String[] {
							"magti_cnt", "geocell_cnt", "beeline_cnt" }),
					new HeaderSpan(CallCenter.constants.eMail(), new String[] {
							"org_contr_email_srv_cnt", "org_email_srv_cnt" }));

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

			statN2Btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showGraphDialog1();
				}
			});

			printPreviewBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Canvas.showPrintPreview(listGrid);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private String getCustomAvarageSummary1(Record records[], String fieldName) {
		if (records == null || records.length <= 0) {
			return "0.00";
		}
		double sum = 0;
		int cnt = 0;
		for (Record record : records) {
			Double value = record.getAttributeAsDouble(fieldName);
			if (value == null) {
				continue;
			}
			sum += value.doubleValue();
			cnt++;
		}
		if (sum <= 0) {
			return "0.00";
		}
		double average = sum / cnt;
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		return nf.format(((Number) average).doubleValue());
	}

	private String getCustomAvarageSummary(Record records[], String fieldName) {
		if (records == null || records.length <= 0) {
			return "0.00";
		}
		if (prevMonthRecord == null) {
			return "0.00";
		}
		Double abonent_cnt_prev = prevMonthRecord
				.getAttributeAsDouble(fieldName);
		if (abonent_cnt_prev == null) {
			return "0.00";
		}
		if (abonent_cnt_prev.doubleValue() <= 0) {
			return "0.00";
		}
		double sum = 0;
		int cnt = 0;
		for (Record record : records) {
			Double value = record.getAttributeAsDouble(fieldName);
			if (value == null) {
				continue;
			}
			sum += value.doubleValue();
			cnt++;
		}
		double average = sum / cnt;
		double result = 100 * average / abonent_cnt_prev.doubleValue();
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		return nf.format(((Number) result).doubleValue());
	}

	private void showGraphDialog1() {
		try {
			ListGridRecord records[] = listGrid.getRecords();
			if (records == null || records.length <= 0) {
				SC.say(CallCenter.constants.pleaseSearchData());
				return;
			}
			DlgViewStatFullGraphAmount dlgViewStatFullGraphAmount = new DlgViewStatFullGraphAmount(
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
				SC.say(CallCenter.constants.pleaseSearchData());
				return;
			}
			DlgViewStatFullGraph dlgViewStatFullGraph = new DlgViewStatFullGraph(
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
				SC.say(CallCenter.constants.invalidDate());
				return;
			}
			DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyMM");
			final Integer ym = new Integer(dateFormatter.format(date));

			Date prevDate = date;
			CalendarUtil.addMonthsToDate(prevDate, -1);
			final Integer ym_prev = new Integer(dateFormatter.format(prevDate));

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchPrevStatisticsByMonth");
			Criteria criteria = new Criteria();
			criteria.setAttribute("ym", ym_prev);

			statisticsDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					Record record = null;
					if (records != null && records.length > 0) {
						record = records[0];
					}
					searchData(ym, record);
				}
			}, dsRequest);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void searchData(Integer ym, Record record) {
		try {
			this.prevMonthRecord = record;
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllStatistics");
			Criteria criteria = new Criteria();
			criteria.setAttribute("ym", ym);

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
