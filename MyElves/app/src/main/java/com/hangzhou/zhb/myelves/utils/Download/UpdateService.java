package com.hangzhou.zhb.myelves.utils.Download;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hangzhou.zhb.myelves.R;
import com.hangzhou.zhb.myelves.module.main.MainActivity;

public class UpdateService extends Service {
	UpdateManagerNotification mUpdateManagerNotification;

	/**
	 * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
	 * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
	 * ---------------------------------------------------------------------------------------------
	 * String path = "你的下载路径";
	 * String name = "下载后的文件名";
	 * Intent intent = new Intent(this, UpdateService.class);
	 * Bundle bundle = new Bundle();
	 * bundle.putString("path",path);
	 * bundle.putString("name",name);
	 * intent.putExtras(bundle);
	 * startService(intent);
	 */
	@Override
	public void onCreate() {
		Log.i("bb","onCreate invoke");

		//下载情况通知
		mUpdateManagerNotification = new UpdateManagerNotification(getApplicationContext());
		Intent intentLoGo = new Intent(getApplicationContext(), MainActivity.class);
		PendingIntent piLoGo = PendingIntent.getActivity(getApplicationContext(), 0, intentLoGo, 0);
		mUpdateManagerNotification.showNotification(getApplicationContext(),piLoGo,"软件更新","软件更新",getApplicationContext().getString(R.string.app_name),"软件更新",getApplicationContext().getString(R.string.app_name));

		super.onCreate();
	}

	/**
	 * 绑定服务时才会调用
	 * 必须要实现的方法
	 * @param intent
	 * @return
	 */
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.i("bb","onBind invoke");
		return null;
	}

	/**
	 * 每次通过startService()方法启动Service时都会被回调。
	 * @param intent
	 * @param flags
	 * @param startId
	 * @return
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle bundle = intent.getExtras();
		if(bundle != null){
			String path = bundle.getString("path","");
			String name = bundle.getString("name","");
			UpdateDownloadRequest mUpdateDownloadRequest = new UpdateDownloadRequest(new UpdateDownloadListener() {
				@Override
				public void onStarted() {

				}

				@Override
				public void onProgressChanged(int progress, String downloadUrl) {
					Message message = new Message();
					message.what = progress;
					mUpdateManagerNotification.handler.sendMessage(message);

					Log.i("bb","progress = " + progress);
				}

				@Override
				public void onFinished(float completeSize, String downloadUrl) {
					Message message = new Message();
					message.what = 100;
					mUpdateManagerNotification.handler.sendMessage(message);
				}

				@Override
				public void onFailure(Exception e) {
					Log.e("Exception:/","Update Download Exception = " + e.getMessage());
				}
			});
			mUpdateDownloadRequest.downLoadApk(getApplication(),path,name);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 服务销毁时的回调
	 */
	@Override
	public void onDestroy() {
		Log.i("bb","onDestroy invoke");
		super.onDestroy();
	}
}
