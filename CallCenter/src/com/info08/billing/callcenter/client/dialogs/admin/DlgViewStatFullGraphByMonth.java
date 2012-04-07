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

public class DlgViewStatFullGraphByMonth extends Window {

	private VLayout hLayout;
	protected FacetChart chart;
	private ListGridRecord records[];

	public DlgViewStatFullGraphByMonth(ListGridRecord records[]) {
		try {
			this.records = records;
			setTitle(CallCenter.constants.graph());

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

			final NumberFormat nf = NumberFormat.getFormat("################");

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
							+ CallCenter.constants.service()
							+ " : </b> "
							+ record.getAttribute("service_name")
							+ "<br />"
							+ "<b>"
							+ CallCenter.constants.callsCount()
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
				ListGridRecord abonent_cnt = new ListGridRecord();
				abonent_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				abonent_cnt.setAttribute("service_name",
						CallCenter.constants.abonent());
				abonent_cnt.setAttribute("value",
						record.getAttributeAsDouble("abonent_cnt"));

				ListGridRecord org_contr_comm_cnt = new ListGridRecord();
				org_contr_comm_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				org_contr_comm_cnt.setAttribute("service_name",
						CallCenter.constants.direct());
				org_contr_comm_cnt.setAttribute("value",
						record.getAttributeAsDouble("org_contr_comm_cnt"));

				ListGridRecord org_non_contr_cnt = new ListGridRecord();
				org_non_contr_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				org_non_contr_cnt.setAttribute("service_name",
						CallCenter.constants.nonDirect());
				org_non_contr_cnt.setAttribute("value",
						record.getAttributeAsDouble("org_non_contr_cnt"));

				ListGridRecord org_contr_gov_cnt = new ListGridRecord();
				org_contr_gov_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				org_contr_gov_cnt.setAttribute("service_name",
						CallCenter.constants.government());
				org_contr_gov_cnt.setAttribute("value",
						record.getAttributeAsDouble("org_contr_gov_cnt"));

				ListGridRecord magti_cnt = new ListGridRecord();
				magti_cnt.setAttribute("ym", record.getAttributeAsString("ym"));
				magti_cnt.setAttribute("service_name",
						CallCenter.constants.magti());
				magti_cnt.setAttribute("value",
						record.getAttributeAsDouble("magti_cnt"));
				magti_cnt.setAttribute("week_day_descr",
						record.getAttributeAsString("week_day_descr"));

				ListGridRecord geocell_cnt = new ListGridRecord();
				geocell_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				geocell_cnt.setAttribute("service_name",
						CallCenter.constants.geocell());
				geocell_cnt.setAttribute("value",
						record.getAttributeAsDouble("geocell_cnt"));

				ListGridRecord beeline_cnt = new ListGridRecord();
				beeline_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				beeline_cnt.setAttribute("service_name",
						CallCenter.constants.beeline());
				beeline_cnt.setAttribute("value",
						record.getAttributeAsDouble("beeline_cnt"));

				ListGridRecord org_contr_email_srv_cnt = new ListGridRecord();
				org_contr_email_srv_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				org_contr_email_srv_cnt.setAttribute("service_name",
						CallCenter.constants.directEmail() + ".1");
				org_contr_email_srv_cnt.setAttribute("value",
						record.getAttributeAsDouble("org_contr_email_srv_cnt"));

				ListGridRecord org_email_srv_cnt = new ListGridRecord();
				org_email_srv_cnt.setAttribute("ym",
						record.getAttributeAsString("ym"));
				org_email_srv_cnt.setAttribute("service_name",
						CallCenter.constants.nonDirectEmail() + ".1");
				org_email_srv_cnt.setAttribute("value",
						record.getAttributeAsDouble("org_email_srv_cnt"));

				listData.add(abonent_cnt);
				listData.add(org_contr_comm_cnt);
				listData.add(org_non_contr_cnt);
				listData.add(org_contr_gov_cnt);
				listData.add(magti_cnt);
				listData.add(geocell_cnt);
				listData.add(beeline_cnt);
				listData.add(org_contr_email_srv_cnt);
				listData.add(org_email_srv_cnt);
			}
			chart.setData(listData.toArray(new ListGridRecord[] {}));
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
