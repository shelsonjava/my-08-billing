package com.info08.billing.callcenterbk.client.content.callcenter;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabTest extends Tab {

	private VLayout mainLayout;
	private RecordList newChartData;
	private FacetChart chart;

	public TabTest() {
		try {

			setTitle("Test Tab");
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			chart = new FacetChart();
			chart.setFacets(new Facet("ym", "ym"));
			chart.setValueProperty("chargeCount");
			chart.setChartType(ChartType.LINE);
			chart.setTitle("Portfolio Value");
			chart.setShowDataPoints(true);
			chart.setMinHeight(400);

			DataSource dataSource = DataSource.get("LogSessDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("customSearchStat");

			newChartData = new RecordList();

			dataSource.fetchData(new Criteria(), new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					newChartData = new RecordList(response.getData());
					newChartData.addList(newChartData.toArray());
					chart.setData(newChartData);
				}
			}, dsRequest);
			chart.setData(newChartData);

			mainLayout.addMember(chart);

			setPane(mainLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
