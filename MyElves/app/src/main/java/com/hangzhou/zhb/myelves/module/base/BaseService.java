package com.hangzhou.zhb.myelves.module.base;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.hangzhou.zhb.myelves.utils.MyLog;

/**
 * Created by zhb on 2017/12/26.
 *
 */

public class BaseService  extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		MyLog.i("onBind");
		return new MyBinder();
	}

	public BaseService() {
	}

	private class MyBinder extends Binder {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		MyLog.i("onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		MyLog.i("onCreate");
	}

	@Override
	public void onDestroy() {
		MyLog.i("onDestroy");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		MyLog.i("onUnbind");
		return true;
	}

	@Override
	public void onRebind(Intent intent) {
		MyLog.i("onRebind");
	}

}
