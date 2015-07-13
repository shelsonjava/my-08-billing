package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.google.gwt.i18n.client.NumberFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
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
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgViewDirOrgStats extends MyWindow {

	private VLayout hLayout;

	private ListGrid listGrid;
	private DataSource statisticsDS;
	private Integer organizationId;
	private Integer ym;
	private boolean isContractor;
	private Integer operator_src;

	public DlgViewDirOrgStats(Integer organizationId, Integer ym,
			boolean isContractor, Integer operator_src) {
		super();
		try {
			this.organizationId = organizationId;
			this.ym = ym;
			this.isContractor = isContractor;
			this.operator_src = operator_src;
			statisticsDS = DataSource.get("StatisticsDS");
			setTitle(CallCenterBK.constants.viewDetail());

			setHeight(700);
			setWidth(500);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			hLayout = new VLayout(5);
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			listGrid = new ListGrid() {
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
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowGridSummary(false);
			listGrid.setShowGroupSummary(false);
			listGrid.setShowAllRecords(true);
			listGrid.setCanSort(false);
			listGrid.setCanReorderFields(false);
			listGrid.setShowRowNumbers(true);
			listGrid.setShowGridSummary(true);
			listGrid.setShowGroupSummary(false);
			listGrid.setShowAllRecords(true);

			ListGridField call_date = new ListGridField("call_date",
					CallCenterBK.constants.date(), 150);
			call_date.setAlign(Alignment.CENTER);

			ListGridField call_count = new ListGridField("call_count",
					CallCenterBK.constants.callsCount());
			call_count.setAlign(Alignment.CENTER);

			call_date.setSummaryFunction(new SummaryFunction() {
				@Override
				public Object getSummaryValue(Record[] records,
						ListGridField field) {
					return CallCenterBK.constants.sum();
				}
			});
			call_count.setSummaryFunction(SummaryFunctionType.SUM);
			call_count.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat.getFormat("#,##0");
						return nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});

			listGrid.setFields(call_date, call_count);

			hLayout.addMember(listGrid);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);
			hLayout.addMember(hLayoutItem);
			addItem(hLayout);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			fillData();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillData() {
		try {
			DSRequest dsRequest = new DSRequest();
			if (isContractor) {
				dsRequest.setOperationId("searchOrgDetailStatisticsContr");
			} else {
				dsRequest.setOperationId("searchOrgDetailStatistics");
			}

			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", organizationId);
			criteria.setAttribute("ym", ym);
			criteria.setAttribute("operator_src", operator_src);

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
