package com.info08.billing.callcenterbk.client.content.stat;

import java.util.Date;

import com.google.gwt.i18n.client.NumberFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
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

public class TabStatOrgCorrectUser extends Tab {

	private VLayout mainLayout;

	private DateItem dateItem;
	private CheckboxItem byMonthItem;
	private TextItem userItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// search form
	private DynamicForm searchForm;

	private ListGrid listGrid;
	private DataSource statsByBillingpDS;

	private ToolStripButton statN1Btn;
	private ToolStripButton excelBtn;

	public TabStatOrgCorrectUser(TabSet tabSet) {
		try {

			statsByBillingpDS = DataSource.get("CorrUserStatDS");

			setTitle(CallCenterBK.constants.statisticOrgCorrect());
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

			Date date = new Date();

			dateItem = new DateItem();
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setValue(date);
			dateItem.setWidth(200);

			byMonthItem = new CheckboxItem();
			byMonthItem.setTitle(CallCenterBK.constants.byMonth());
			byMonthItem.setName("byMonthItem");
			byMonthItem.setWidth(200);

			userItem = new TextItem();
			userItem.setTitle(CallCenterBK.constants.username());
			userItem.setName("userItem");
			userItem.setWidth(200);

			searchForm.setFields(dateItem, byMonthItem, userItem);

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

			toolStrip.addSeparator();

			excelBtn = new ToolStripButton(
					CallCenterBK.constants.export_to_excel(), "excel.png");
			excelBtn.setLayoutAlign(Alignment.LEFT);
			excelBtn.setWidth(50);
			toolStrip.addButton(excelBtn);

			listGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer cnt_type = countryRecord
							.getAttributeAsInt("cnt_type");
					if (cnt_type == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					if (cnt_type.equals(2)) {
						return "color:red;";
					}
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(false);
			listGrid.setDataSource(statsByBillingpDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchStat");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setHeaderHeight(40);
			listGrid.setCanSort(false);
			listGrid.setCanDrag(false);
			listGrid.setCanGroupBy(false);
			listGrid.setCanDragReposition(false);
			listGrid.setCanResizeFields(false);
			listGrid.setCanAutoFitFields(false);
			listGrid.setShowHeaderContextMenu(false);
			listGrid.setCanDragRecordsOut(false);
			listGrid.setCanReorderFields(false);
			listGrid.setShowGridSummary(true);
			listGrid.setShowGroupSummary(false);
			listGrid.setShowAllRecords(true);

			ListGridField act_date = new ListGridField("act_date",
					CallCenterBK.constants.date(), 100);
			ListGridField user_name = new ListGridField("user_name",
					CallCenterBK.constants.usernameShort(), 100);
			ListGridField new_org = new ListGridField("new_org",
					CallCenterBK.constants.newOrgShort(), 50);
			ListGridField del_org = new ListGridField("del_org",
					CallCenterBK.constants.delOrgShort(), 50);
			ListGridField new_phone = new ListGridField("new_phone",
					CallCenterBK.constants.newPhoneShort(), 50);
			ListGridField phone_upd = new ListGridField("phone_upd",
					CallCenterBK.constants.phoneUpd(), 50);
			ListGridField del_phone = new ListGridField("del_phone",
					CallCenterBK.constants.delPhone(), 50);
			ListGridField address = new ListGridField("address",
					CallCenterBK.constants.addressShort(), 50);
			ListGridField director = new ListGridField("director",
					CallCenterBK.constants.directorShort(), 55);
			ListGridField ident_code = new ListGridField("ident_code",
					CallCenterBK.constants.identCodeShort(), 50);
			ListGridField work_hour_dayy_off = new ListGridField(
					"work_hour_dayy_off",
					CallCenterBK.constants.workHourDayOffShort(), 70);
			ListGridField web_site = new ListGridField("web_site",
					CallCenterBK.constants.webSiteShort(), 50);
			ListGridField email = new ListGridField("email",
					CallCenterBK.constants.eMail(), 50);
			ListGridField soc_network = new ListGridField("soc_network",
					CallCenterBK.constants.socialAddressShort(), 50);
			ListGridField part_bank = new ListGridField("part_bank",
					CallCenterBK.constants.partnerBankShort1(), 50);
			ListGridField org_comment = new ListGridField("org_comment",
					CallCenterBK.constants.orgCommentShort(), 50);
			ListGridField founded_date = new ListGridField("founded_date",
					CallCenterBK.constants.foundedShort(), 50);
			ListGridField other = new ListGridField("other",
					CallCenterBK.constants.other(), 50);
			ListGridField new_subs = new ListGridField("new_subs",
					CallCenterBK.constants.newSubsShort(), 50);
			ListGridField update_subs = new ListGridField("update_subs",
					CallCenterBK.constants.updateSubsShort(), 50);
			ListGridField del_subs = new ListGridField("del_subs",
					CallCenterBK.constants.delSubsShort(), 50);
			ListGridField sum = new ListGridField("sum_cnt",
					CallCenterBK.constants.sum(), 50);

			act_date.setAlign(Alignment.CENTER);
			user_name.setAlign(Alignment.LEFT);
			new_org.setAlign(Alignment.CENTER);
			del_org.setAlign(Alignment.CENTER);
			new_phone.setAlign(Alignment.CENTER);
			phone_upd.setAlign(Alignment.CENTER);
			del_phone.setAlign(Alignment.CENTER);
			address.setAlign(Alignment.CENTER);
			ident_code.setAlign(Alignment.CENTER);
			work_hour_dayy_off.setAlign(Alignment.CENTER);
			web_site.setAlign(Alignment.CENTER);
			email.setAlign(Alignment.CENTER);
			soc_network.setAlign(Alignment.CENTER);
			part_bank.setAlign(Alignment.CENTER);
			org_comment.setAlign(Alignment.CENTER);
			founded_date.setAlign(Alignment.CENTER);
			other.setAlign(Alignment.CENTER);
			new_subs.setAlign(Alignment.CENTER);
			update_subs.setAlign(Alignment.CENTER);
			del_subs.setAlign(Alignment.CENTER);
			sum.setAlign(Alignment.CENTER);
			director.setAlign(Alignment.CENTER);
			work_hour_dayy_off.setWrap(true);
			new_org.setWrap(true);
			del_org.setWrap(true);
			new_phone.setWrap(true);
			phone_upd.setWrap(true);
			del_phone.setWrap(true);
			ident_code.setWrap(true);
			soc_network.setWrap(true);
			part_bank.setWrap(true);
			founded_date.setWrap(true);
			new_subs.setWrap(true);
			update_subs.setWrap(true);
			del_subs.setWrap(true);

			act_date.setSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.sum();
				}
			});
			act_date.addSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.avarage();
				}
			});

			new_org.setCellFormatter(new CellFormatter() {
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
			new_org.setSummaryFunction(SummaryFunctionType.SUM);
			new_org.addSummaryFunction(SummaryFunctionType.AVG);

			del_org.setCellFormatter(new CellFormatter() {
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
			del_org.setSummaryFunction(SummaryFunctionType.SUM);
			del_org.addSummaryFunction(SummaryFunctionType.AVG);

			new_phone.setCellFormatter(new CellFormatter() {
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
			new_phone.setSummaryFunction(SummaryFunctionType.SUM);
			new_phone.addSummaryFunction(SummaryFunctionType.AVG);

			phone_upd.setCellFormatter(new CellFormatter() {
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
			phone_upd.setSummaryFunction(SummaryFunctionType.SUM);
			phone_upd.addSummaryFunction(SummaryFunctionType.AVG);

			del_phone.setCellFormatter(new CellFormatter() {
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
			del_phone.setSummaryFunction(SummaryFunctionType.SUM);
			del_phone.addSummaryFunction(SummaryFunctionType.AVG);

			address.setCellFormatter(new CellFormatter() {
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
			address.setSummaryFunction(SummaryFunctionType.SUM);
			address.addSummaryFunction(SummaryFunctionType.AVG);

			director.setCellFormatter(new CellFormatter() {
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
			director.setSummaryFunction(SummaryFunctionType.SUM);
			director.addSummaryFunction(SummaryFunctionType.AVG);

			ident_code.setCellFormatter(new CellFormatter() {
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
			ident_code.setSummaryFunction(SummaryFunctionType.SUM);
			ident_code.addSummaryFunction(SummaryFunctionType.AVG);

			work_hour_dayy_off.setCellFormatter(new CellFormatter() {
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
			work_hour_dayy_off.setSummaryFunction(SummaryFunctionType.SUM);
			work_hour_dayy_off.addSummaryFunction(SummaryFunctionType.AVG);

			web_site.setCellFormatter(new CellFormatter() {
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
			web_site.setSummaryFunction(SummaryFunctionType.SUM);
			web_site.addSummaryFunction(SummaryFunctionType.AVG);

			email.setCellFormatter(new CellFormatter() {
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
			email.setSummaryFunction(SummaryFunctionType.SUM);
			email.addSummaryFunction(SummaryFunctionType.AVG);

			soc_network.setCellFormatter(new CellFormatter() {
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
			soc_network.setSummaryFunction(SummaryFunctionType.SUM);
			soc_network.addSummaryFunction(SummaryFunctionType.AVG);

			part_bank.setCellFormatter(new CellFormatter() {
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
			part_bank.setSummaryFunction(SummaryFunctionType.SUM);
			part_bank.addSummaryFunction(SummaryFunctionType.AVG);

			org_comment.setCellFormatter(new CellFormatter() {
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
			org_comment.setSummaryFunction(SummaryFunctionType.SUM);
			org_comment.addSummaryFunction(SummaryFunctionType.AVG);

			founded_date.setCellFormatter(new CellFormatter() {
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
			founded_date.setSummaryFunction(SummaryFunctionType.SUM);
			founded_date.addSummaryFunction(SummaryFunctionType.AVG);

			other.setCellFormatter(new CellFormatter() {
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
			other.setSummaryFunction(SummaryFunctionType.SUM);
			other.addSummaryFunction(SummaryFunctionType.AVG);

			new_subs.setCellFormatter(new CellFormatter() {
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
			new_subs.setSummaryFunction(SummaryFunctionType.SUM);
			new_subs.addSummaryFunction(SummaryFunctionType.AVG);

			update_subs.setCellFormatter(new CellFormatter() {
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
			update_subs.setSummaryFunction(SummaryFunctionType.SUM);
			update_subs.addSummaryFunction(SummaryFunctionType.AVG);

			del_subs.setCellFormatter(new CellFormatter() {
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
			del_subs.setSummaryFunction(SummaryFunctionType.SUM);
			del_subs.addSummaryFunction(SummaryFunctionType.AVG);

			sum.setCellFormatter(new CellFormatter() {
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
			sum.setSummaryFunction(SummaryFunctionType.SUM);
			sum.addSummaryFunction(SummaryFunctionType.AVG);

			listGrid.setFields(act_date, user_name, new_org, del_org,
					new_phone, phone_upd, del_phone, address, director,
					ident_code, work_hour_dayy_off, web_site, email,
					soc_network, part_bank, org_comment, founded_date, other,
					new_subs, update_subs, del_subs, sum);

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

			userItem.addKeyPressHandler(new KeyPressHandler() {
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
					dateItem.setValue(new Date());
					byMonthItem.setValue(false);
					userItem.clearValue();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search(boolean isExport) {
		try {
			DSRequest dsRequest = new DSRequest();
			Criteria criteria = new Criteria();

			Date date = dateItem.getValueAsDate();
			boolean byMonth = byMonthItem.getValueAsBoolean().booleanValue();
			if (byMonth) {
				dsRequest.setOperationId("searchCorrUserStatsByMonth");
				criteria.setAttribute("mmyy", date);
			} else {
				dsRequest.setOperationId("searchCorrUserStats");
				criteria.setAttribute("act_date", date);
			}
			String user_name = userItem.getValueAsString();
			if (user_name != null && !user_name.trim().equals("")) {
				criteria.setAttribute("user_name", user_name);
			}

			if (isExport) {
				dsRequest.setExportAs((ExportFormat) EnumUtil.getEnum(
						ExportFormat.values(), "xls"));
				dsRequest.setExportDisplay(ExportDisplay.DOWNLOAD);

				dsRequest.setExportFields(new String[] { "act_date",
						"user_name", "new_org", "del_org", "new_phone",
						"phone_upd", "del_phone", "address", "director",
						"ident_code", "work_hour_dayy_off", "web_site",
						"email", "soc_network", "part_bank", "org_comment",
						"founded_date", "other", "new_subs", "update_subs",
						"del_subs", "sum_cnt" });

				listGrid.getDataSource().exportData(criteria, dsRequest,
						new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
							}
						});

			} else {
				listGrid.invalidateCache();
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
