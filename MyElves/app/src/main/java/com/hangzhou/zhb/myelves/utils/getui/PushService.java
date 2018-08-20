package com.hangzhou.zhb.myelves.utils.getui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.igexin.sdk.GTServiceManager;

/**
 * Created by zhb on 2017/8/17.
 *
 */

public class PushService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		GTServiceManager.getInstance().onCreate(this);
		Log.e("bb", "PushService");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		switch (GTServiceManager.getInstance().onStartCommand(this, intent, flags, startId)) {
			case 0:
				return Service.START_STICKY_COMPATIBILITY;
			case 1:
				return Service.START_STICKY;
			case 2:
				return Service.START_NOT_STICKY;
			case 3:
				return Service.START_REDELIVER_INTENT;
			default:
				return Service.START_STICKY;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return GTServiceManager.getInstance().onBind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		GTServiceManager.getInstance().onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		GTServiceManager.getInstance().onLowMemory();
	}
}