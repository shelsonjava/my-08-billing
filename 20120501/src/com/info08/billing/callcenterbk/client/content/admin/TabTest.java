package com.info08.billing.callcenterbk.client.content.admin;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabTest extends Tab {

	public TabTest() {
		try {

			setTitle(CallCenterBK.constants.sendSMS());
			setCanClose(true);

			final Chart chart = new Chart()
					.setType(Series.Type.AREA)
					.setChartTitleText("Area chart with negative values")
					.setToolTip(
							new ToolTip().setFormatter(new ToolTipFormatter() {
								public String format(ToolTipData toolTipData) {
									return toolTipData.getSeriesName() + ": "
											+ toolTipData.getYAsLong();
								}
							})).setCredits(new Credits().setEnabled(false));

			chart.getXAxis().setCategories("Apples", "Oranges", "Pears",
					"Grapes", "Bananas");

			chart.addSeries(chart.createSeries().setName("John")
					.setPoints(new Number[] { 5, 3, 4, 7, 2 }));
			chart.addSeries(chart.createSeries().setName("Jane")
					.setPoints(new Number[] { 2, -2, -3, 2, 1 }));
			chart.addSeries(chart.createSeries().setName("Joe")
					.setPoints(new Number[] { 3, 4, 4, -2, 5 }));

			VLayout  mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			
			mainLayout.addMember(chart);
			setPane(mainLayout);
			
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
