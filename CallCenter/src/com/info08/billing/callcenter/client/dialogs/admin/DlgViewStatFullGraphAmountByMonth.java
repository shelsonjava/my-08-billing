package com.info08.billing.callcenter.client.dialogs.admin;

import java.util.ArrayList;

import com.google.gwt.i18n.client.NumberFormat;
import com.info08.billing.callcenter.client.CallCenter;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.chart.ChartPointHoverCustomizer;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgViewStatFullGraphAmountByMonth extends Window {

	private VLayout hLayout;
	protected FacetChart chart;
	private ListGridRecord records[];

	public DlgViewStatFullGraphAmountByMonth(ListGridRecord records[]) {
		try {
			this.records = records;
			setTitle(CallCenter.constants.graphAmount());

			setHeight(700);
			setWidth(1200);
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

			final NumberFormat nf = NumberFormat
					.getFormat("################0.0000");

			chart = new FacetChart();
			chart.setWidth100();
			chart.setHeight100();
			chart.setFacets(new Facet("ym", "ym"), new Facet("service_name",
					"service_name"));

			chart.setValueProperty("value");
			chart.setChartType(ChartType.LINE);
			chart.setTitle(CallCenter.constants.statisticFull());
			chart.setValueTitle(CallCenter.constants.callsCount());
			chart.setShowDataPoints(true);
			chart.setPointHoverCustomizer(new ChartPointHoverCustomizer() {
				@Override
				public String hoverHTML(Float value, Record record) {
					return ("<b>"
							+ CallCenter.constants.yearMonthOnly()
							+ " : </b> "
							+ record.getAttribute("ym")
							+ "<br />"
							+ "<b>"
							+ CallCenter.constants.amount()
							+ " : </b> "
							+ (record.getAttributeAsDouble("value") == null ? nf
									.format(0) : nf.format(record
									.getAttributeAsDouble("value"))) + "<br /><hr width=250 >");
				}
			});

			hLayout.addMember(chart);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			fillData();
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillData() {
		try {
			int size = records.length;
			ArrayList<ListGridRecord> listData = new ArrayList<ListGridRecord>();
			for (int i = 0; i < size; i++) {
				ListGridRecord record = records[i];

				ListGridRecord all_amount = new ListGridRecord();
				all_amount
						.setAttribute("ym", record.getAttributeAsString("ym"));
				all_amount.setAttribute("service_name",
						CallCenter.constants.amount());
				all_amount.setAttribute("value",
						record.getAttributeAsDouble("all_amount"));
				listData.add(all_amount);
			}
			chart.setData(listData.toArray(new ListGridRecord[] {}));
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
