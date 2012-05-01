package com.info08.billing.callcenterbk.client.content.stat;

import com.smartgwt.client.data.Record;

public class SimpleChartData extends Record {

	public SimpleChartData(String region, String product, Integer sales) {
		setAttribute("region", region);
		setAttribute("product", product);
		setAttribute("sales", sales);
	}

	public static SimpleChartData[] getData() {
		return new SimpleChartData[] { new SimpleChartData("West", "Cars", 37),
				new SimpleChartData("North", "Cars", 29),
				new SimpleChartData("East", "Cars", 80),
				new SimpleChartData("South", "Cars", 87),

				new SimpleChartData("West", "Trucks", 23),
				new SimpleChartData("North", "Trucks", 45),
				new SimpleChartData("East", "Trucks", 32),
				new SimpleChartData("South", "Trucks", 67),

				new SimpleChartData("West", "Motorcycles", 12),
				new SimpleChartData("North", "Motorcycles", 4),
				new SimpleChartData("East", "Motorcycles", 23),
				new SimpleChartData("South", "Motorcycles", 45) };
	}

}