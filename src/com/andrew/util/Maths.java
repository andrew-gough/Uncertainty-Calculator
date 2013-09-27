package com.andrew.util;

import java.math.BigDecimal;
import java.util.List;

public class Maths {

	public Maths() {
		// TODO Auto-generated constructor stub
	}
	
	public static double truncate(double d, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(d);
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}
	
	public static Double roundToSignificantFigures(Double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}

//	public static double roundToSignificantFigures(double num, int n) {
//	    if(num == 0) {
//	        return 0;
//	    }
//
//	    final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
//	    final int power = n - (int) d;
//
//	    final double magnitude = Math.pow(10, power);
//	    final long shifted = Math.round(num*magnitude);
//	    return shifted/magnitude;
//	}
	
	public static double[] listToArray(List<Double> arr){   
	    double[] result = new double[arr.size()];
	    int i = 0;
	    for(Double d : arr) {
	        result[i++] = d.doubleValue();
	    }
	    return result;
	}
	
}
