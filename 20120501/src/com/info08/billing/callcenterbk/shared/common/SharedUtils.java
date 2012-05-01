package com.info08.billing.callcenterbk.shared.common;

import java.util.LinkedHashMap;

public class SharedUtils {
	
	private static SharedUtils instance;

	public static SharedUtils getInstance() {
		if (instance == null) {
			instance = new SharedUtils();
		}
		return instance;
	}
	
	private LinkedHashMap<String, String> mapDistanceTypes;
	
	public SharedUtils() {
		mapDistanceTypes = new LinkedHashMap<String, String>();
		mapDistanceTypes.put("0", "სახმელეთო");		
		mapDistanceTypes.put("1", "საჰაერო");
		mapDistanceTypes.put("2", "საზღვაო");		
	}
	
	public LinkedHashMap<String, String> getMapDistanceTypes() {
		return mapDistanceTypes;
	}
	
	public String getDistanceTypeDescr(String type){
		return mapDistanceTypes.get(type);	
	}
}
