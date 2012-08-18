package com.info08.billing.callcenterbk.client.content.stat;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgViewStatByBillingCompGraph;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabStatOrgCorrect extends Tab {

	private VLayout mainLayout;

	private DateItem dateItem1;
	private DateItem dateItem2;
	private DateItem dateItem3;
	private DateItem dateItem4;
	private DateItem dateItem5;
	private DateItem dateItem6;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// search form
	private DynamicForm searchForm;

	private ListGrid listGrid;
	private DataSource statsByBillingpDS;

	private ToolStripButton statN1Btn;

	public TabStatOrgCorrect(TabSet tabSet) {
		try {

			statsByBillingpDS = DataSource.get("StatisticOrgCorrectDS");

			setTitle(CallCenterBK.constants.statisticByBillingComp());
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

			dateItem1 = new DateItem();
			dateItem1.setTitle(CallCenterBK.constants.date());
			dateItem1.setName("dateItem1");
			dateItem1.setValue(date);
			dateItem1.setWidth(200);
			dateItem1.setUseTextField(true);

			dateItem2 = new DateItem();
			dateItem2.setTitle(CallCenterBK.constants.date());
			dateItem2.setName("dateItem2");
			dateItem2.setValue(date);
			dateItem2.setWidth(200);
			dateItem2.setUseTextField(true);

			dateItem3 = new DateItem();
			dateItem3.setTitle(CallCenterBK.constants.date());
			dateItem3.setName("dateItem3");
			dateItem3.setWidth(200);
			dateItem3.setUseTextField(true);

			dateItem4 = new DateItem();
			dateItem4.setTitle(CallCenterBK.constants.date());
			dateItem4.setName("dateItem4");
			dateItem4.setWidth(200);
			dateItem4.setUseTextField(true);

			dateItem5 = new DateItem();
			dateItem5.setTitle(CallCenterBK.constants.date());
			dateItem5.setName("dateItem5");
			dateItem5.setWidth(200);
			dateItem5.setUseTextField(true);

			dateItem6 = new DateItem();
			dateItem6.setTitle(CallCenterBK.constants.date());
			dateItem6.setName("dateItem6");
			dateItem6.setWidth(200);
			dateItem6.setUseTextField(true);

			searchForm.setFields(dateItem1, dateItem2, dateItem3, dateItem4,
					dateItem5, dateItem6);

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

			ListGridField stat_date_descr = new ListGridField("stat_date_descr",
					CallCenterBK.constants.date(), 100);
			ListGridField full_cnt = new ListGridField("full_cnt",
					CallCenterBK.constants.fullCount(), 50);
			ListGridField name_cnt = new ListGridField("name_cnt",
					CallCenterBK.constants.orgName(), 80);
			ListGridField phone_cnt = new ListGridField("phone_cnt",
					CallCenterBK.constants.phones(), 70);
			ListGridField ph_addr_cnt = new ListGridField("ph_addr_cnt",
					CallCenterBK.constants.address(), 80);
			ListGridField mail_cnt = new ListGridField("mail_cnt",
					CallCenterBK.constants.eMail(), 70);
			ListGridField web_cnt = new ListGridField("web_cnt",
					CallCenterBK.constants.webaddress(), 80);
			ListGridField director_cnt = new ListGridField("director_cnt",
					CallCenterBK.constants.director(), 80);
			ListGridField part_bank_cnt = new ListGridField("part_bank_cnt",
					CallCenterBK.constants.partnerBankShort(), 80);
			ListGridField staff_count_cnt = new ListGridField(
					"staff_count_cnt",
					CallCenterBK.constants.workPersonCountity(), 120);
			ListGridField fdate_cnt = new ListGridField("fdate_cnt",
					CallCenterBK.constants.founded(), 120);
			ListGridField ident_code_cnt = new ListGridField("ident_code_cnt",
					CallCenterBK.constants.identCodeShort(), 80);
			ListGridField legal_stat_cnt = new ListGridField("legal_stat_cnt",
					CallCenterBK.constants.legalStatuse(), 130);
			ListGridField dayoffs_cnt = new ListGridField("dayoffs_cnt",
					CallCenterBK.constants.dayOffs(), 130);

			stat_date_descr.setAlign(Alignment.CENTER);
			full_cnt.setAlign(Alignment.CENTER);
			name_cnt.setAlign(Alignment.CENTER);
			phone_cnt.setAlign(Alignment.CENTER);
			ph_addr_cnt.setAlign(Alignment.CENTER);
			mail_cnt.setAlign(Alignment.CENTER);
			web_cnt.setAlign(Alignment.CENTER);
			director_cnt.setAlign(Alignment.CENTER);
			part_bank_cnt.setAlign(Alignment.CENTER);
			staff_count_cnt.setAlign(Alignment.CENTER);
			fdate_cnt.setAlign(Alignment.CENTER);
			ident_code_cnt.setAlign(Alignment.CENTER);
			legal_stat_cnt.setAlign(Alignment.CENTER);
			dayoffs_cnt.setAlign(Alignment.CENTER);

			listGrid.setFields(stat_date_descr, full_cnt, name_cnt, phone_cnt,
					ph_addr_cnt, mail_cnt, web_cnt, director_cnt,
					part_bank_cnt, staff_count_cnt, fdate_cnt, ident_code_cnt,
					legal_stat_cnt, dayoffs_cnt);

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
					dateItem1.setValue(new Date());
					dateItem2.setValue(new Date());
					dateItem3.clearValue();
					dateItem4.clearValue();
					dateItem5.clearValue();
					dateItem6.clearValue();
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
			Date date1 = dateItem1.getValueAsDate();
			Date date2 = dateItem2.getValueAsDate();
			Date date3 = dateItem3.getValueAsDate();
			Date date4 = dateItem4.getValueAsDate();
			Date date5 = dateItem5.getValueAsDate();
			Date date6 = dateItem6.getValueAsDate();

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchStat");
			Criteria criteria = new Criteria();
			criteria.setAttribute("stat_date1", date1);
			criteria.setAttribute("stat_date2", date2);
			criteria.setAttribute("stat_date3", date3);
			criteria.setAttribute("stat_date4", date4);
			criteria.setAttribute("stat_date5", date5);
			criteria.setAttribute("stat_date6", date6);

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
