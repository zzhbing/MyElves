package com.hangzhou.zhb.myelves.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by zhb on 2017/8/25.
 * 公共类
 */

public class Global {
	private static SharedPreferences g_Shared;

	private static void setG_Shared(Context context) {
		Global.g_Shared = context.getSharedPreferences(Constants.PARAMS,
				Context.MODE_PRIVATE);
	}

	private static SharedPreferences getG_Shared() {
		return g_Shared;
	}

	/**打开一个activity,并finish当前activity*/
	public static void goNextActivityAndFinish(final Context context,
											   final @SuppressWarnings("rawtypes") Class class1,
											   final Bundle dataName) {
		if (null == dataName) {
			Intent intent = new Intent(context, class1);
			context.startActivity(intent);
			((Activity) context).finish();
		} else {
			Intent intent = new Intent(context, class1);
			intent.putExtras(dataName);
			context.startActivity(intent);
			((Activity) context).finish();
		}
	}

	/**打开一个activity*/
	public static void goNextActivity(final Context context,
									  final @SuppressWarnings("rawtypes") Class class1,
									  final Bundle dataName) {
		if (null == dataName) {
			Intent intent = new Intent(context, class1);
			context.startActivity(intent);
		} else {
			Intent intent = new Intent(context, class1);
			intent.putExtras(dataName);
			context.startActivity(intent);
		}
	}

	/**个推生成的clientId*/
	public static void setClientId(Context context, String ClientId) {
		setG_Shared(context);
		SharedPreferences.Editor editor = getG_Shared().edit();
		editor.putString("ClientId", ClientId);
		editor.apply();
	}

	/**个推生成的clientId*/
	public static String getClientId(Context context) {
		setG_Shared(context);
		return getG_Shared().getString("ClientId", "");
	}

	/**保留一定量的推送消息，作为系统消息*/
	public static void setTuiSongListResponse(Context context, String TuiSongListResponse) {
		setG_Shared(context);
		SharedPreferences.Editor editor = getG_Shared().edit();
		editor.putString("TuiSongListResponse", TuiSongListResponse);
		editor.apply();
	}

	/**保留一定量的推送消息，作为系统消息*/
	public static String getTuiSongListResponse(Context context) {
		setG_Shared(context);
		return getG_Shared().getString("TuiSongListResponse", "");
	}
}
