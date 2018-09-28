package com.example.animdemo;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
	
	private static int deviceWidth = 0;
	private static int deviceHeight = 0;
	
	public static int getDeviceWidth() {
		return deviceWidth;
	}

	public static void setDeviceWidth(int width) {
		deviceWidth = width;
	}

	public static int getDeviceHeight() {
		return deviceHeight;
	}

	public static void setDeviceHeight(int height) {
		deviceHeight = height;
	}
	
	/**
	 * 获得屏幕的分辨率
	 */
	public static void countDevicePixels(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			if (dm.widthPixels > dm.heightPixels) {
				deviceWidth = dm.heightPixels;
				deviceHeight = dm.widthPixels;
			} else {
				deviceWidth = dm.widthPixels;
				deviceHeight = dm.heightPixels;
			}

		int dpi = activity.getResources().getDisplayMetrics().densityDpi;
		double inch = Math.sqrt(Math.pow(deviceWidth, 2) + Math.pow(deviceHeight, 2))/ dpi;
		inch = Math.round(inch * 10) / 10.0;
	}
	
	public static int dp2px(float dp, Context context) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	}
}
