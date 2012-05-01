package com.info08.billing.callcenter.client.common.components;

import java.util.TreeMap;

public class KeyboardHandler {
	private static KeyboardHandler registrator = null;

	private TreeMap<Integer, TreeMap<Integer, Integer>> tmCodes = new TreeMap<Integer, TreeMap<Integer, Integer>>();

	public synchronized static void registerLanguage(int language_id,
			String codes) {
		if (registrator == null) {
			registrator = new KeyboardHandler();
			createFunction(registrator);
		}
		try {
			String[] each = codes.split(";");
			TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
			for (String code : each) {
				try {
					if (code.trim().length() == 0)
						continue;
					String[] kevalue = code.split(":");
					int key = Integer.parseInt(kevalue[0]);
					int value = Integer.parseInt(kevalue[1]);
					map.put(key, value);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			registrator.tmCodes.put(language_id, map);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static native void createFunction(KeyboardHandler registrator)/*-{
		$wnd.keyboardchange = function(l_id, c_code) {
			return registrator.@com.info08.billing.callcenter.client.common.components.KeyboardHandler::getCharCode(II)(l_id,c_code);
		};
		$wnd.registerglobal();
	}-*/;

	public static native void setLanguage(int lang)/*-{
		$wnd.setLanguage(lang);
	}-*/;

	public int getCharCode(int language_id, int char_code) {
		try {
			TreeMap<Integer, Integer> map = tmCodes.get(language_id);
			if (map != null) {
				Integer value = map.get(char_code);
				if (value != null)
					return value.intValue();
			}
		} catch (Exception e) {
			return char_code;
		}
		return char_code;
	}
}
