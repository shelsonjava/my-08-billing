package com.info08.billing.callcenterbk.shared.wtest;

import java.util.Arrays;

public class TestMain {
	public static void main(String[] args) {
		try {
			 Integer a[] = {1,5,2};
			 Integer b[] = {1,5,2};
			 boolean result = Arrays.equals(a, b);
			 System.out.println("result = "+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
