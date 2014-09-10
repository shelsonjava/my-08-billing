package com.info08.billing.callcenterbk.shared.wtest;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;

public class TestMain {
	public static void main(String[] args) {
		try {
			CSV csv = CSV.separator(';').quote('\'').skipLines(1)
					.charset("UTF-8").create();
			long time = System.currentTimeMillis();
			final TreeSet<String> phones = new TreeSet<String>();
			phones.add("995599429883");
			phones.add("995591408469");
			phones.add("995598138884");
			phones.add("995431228917");			
			phones.add("995431234058");
			phones.add("995598300005");
			phones.add("995577504400");
			phones.add("995593695961");
			phones.add("995599693939");
			
			csv.read("/home/paata/port_dump_2013-11-29.csv", new CSVReadProc() {
				public void procRow(int rowIndex, String... values) {
					List<String> row = Arrays.asList(values);
					for (String phone : phones) {
						for (String item : row) {
							if (item.equalsIgnoreCase(phone)) {
								System.out.println(rowIndex + ": "
										+ Arrays.asList(values));
								break;
							}
						}
					}
				}
			});
			time = System.currentTimeMillis() - time;
			System.out.println("Time = " + time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
