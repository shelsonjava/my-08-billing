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

	public static void main(String[] args) {
		try {
			System.out.println("599693939 = "+isPhoneChargeable("599693939"));
			System.out.println("570693939 = "+isPhoneChargeable("570693939"));
			System.out.println("577693939 = "+isPhoneChargeable("577693939"));
			System.out.println("593693939 = "+isPhoneChargeable("593693939"));
			System.out.println("2693939 = "+isPhoneChargeable("2693939"));
			System.out.println("322693939 = "+isPhoneChargeable("322693939"));
			System.out.println("790693939 = "+isPhoneChargeable("790693939"));
			System.out.println("791693939 = "+isPhoneChargeable("791693939"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
