package com.hangzhou.zhb.myelves.utils;

import android.util.Log;

import com.hangzhou.zhb.myelves.BuildConfig;

/**
 * Created by zhb on 2017/12/26.
 * log封装
 */

public class MyLog {
	private static String className = "";//类名
	private static String methodName = "";//方法名
	private static int lineNumber = -1;//行数

	private MyLog() {
		/* Protect from instantiations */
	}

	private static boolean isDebuggable() {
		return BuildConfig.DEBUG;
	}

	private static String createLog(String log) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(methodName);
		buffer.append("(").append(className).append(":").append(lineNumber).append(")");
		buffer.append(log);
		return buffer.toString();
	}

	private static void getMethodNames(StackTraceElement[] sElements) {
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
	}


	public static void e(String message) {
		if(!isDebuggable())
			return;

		// Throwable instance must be created before any methods
		getMethodNames(new Throwable().getStackTrace());
		Log.e(className, createLog(message));
	}


	public static void i(String message) {
		if(!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.i(className, createLog(message));
	}

	public static void d(String message) {
		if(!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.d(className, createLog(message));
	}

	public static void v(String message) {
		if(!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.v(className, createLog(message));
	}

	public static void w(String message) {
		if(!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.w(className, createLog(message));
	}

	public static void wtf(String message) {
		if(!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.wtf(className, createLog(message));
	}

}
