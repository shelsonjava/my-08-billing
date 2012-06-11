package com.info08.billing.callcenterbk.client.utils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.Record;

public class HistoryResult {

	private static final String RNC_NAME = "_rcn";
	private static final String ON_USER_NAME = "_on_user";
	private static final String OFF_USER_NAME = "_off_user";
	private static final String START_NAME = "_hist_start_date";
	private static final String END_NAME = "_hist_end_date";

	private String on_user;
	private String off_user;
	private Date start;
	private Date end;

	public HistoryResult(Record rec) {
		Map<?, ?> map = rec.toMap();
		Set<?> keys = map.keySet();
		Long lngValue = 0L;
		String maxRcn = null;
		for (Object key : keys) {
			if (key == null)
				continue;
			String fieldName = key.toString().trim().toLowerCase();
			if (fieldName.endsWith(RNC_NAME.toLowerCase())) {
				Object obj = map.get(key);
				if (obj == null)
					continue;
				try {
					Long curValue = new Long(obj.toString());
					if (curValue.longValue() > lngValue.longValue()) {
						lngValue = curValue;
						maxRcn = fieldName;
					}
				} catch (Exception e) {

				}
			}
			if (maxRcn != null) {
				String prefix = maxRcn.substring(0,
						maxRcn.length() - RNC_NAME.length());
				on_user = getStringForField(prefix + ON_USER_NAME, map);
				off_user = getStringForField(prefix + OFF_USER_NAME, map);
				start = getDateForField(prefix + START_NAME, map);
				end = getDateForField(prefix + END_NAME, map);
			}
		}
	}

	private Object getValueForField(String name, Map<?, ?> map) {
		Set<?> keys = map.keySet();
		for (Object key : keys) {
			if (key == null)
				continue;
			String fieldName = key.toString().trim().toLowerCase();
			if (fieldName.equalsIgnoreCase(name))
				return map.get(key);

		}
		return null;
	}

	private Date getDateForField(String name, Map<?, ?> map) {
		Object obj = getValueForField(name, map);
		if (obj != null)
			return (Date) obj;
		return null;
	}

	private String getStringForField(String name, Map<?, ?> map) {
		Object obj = getValueForField(name, map);
		if (obj != null)
			return obj.toString().trim();
		return null;
	}

	public String getOn_user() {
		return on_user;
	}

	public String getOff_user() {
		return off_user;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

}
