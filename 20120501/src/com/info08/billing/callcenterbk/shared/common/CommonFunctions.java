package com.info08.billing.callcenterbk.shared.common;

public class CommonFunctions {
	public static boolean isPhoneMobile(String phone) {
		return (phone.startsWith("5") || phone.startsWith("790"));
	}

	public static boolean isPhoneChargeable(String phone) {
		if (phone.startsWith("570")) {
			return true;
		}
		return (!phone.startsWith("5") && !phone.startsWith("790"));
	}

	public static String getRomanNumber(int index) {
		switch (index) {
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		default:
			return "XXX";
		}
	}
}
