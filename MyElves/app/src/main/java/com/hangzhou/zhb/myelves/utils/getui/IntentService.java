package com.hangzhou.zhb.myelves.utils.getui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.hangzhou.zhb.myelves.R;
import com.hangzhou.zhb.myelves.response.getui.TuiSongListResponse;
import com.hangzhou.zhb.myelves.response.getui.TuiSongResponse;
import com.hangzhou.zhb.myelves.utils.Constants;
import com.hangzhou.zhb.myelves.utils.Global;
import com.hangzhou.zhb.myelves.utils.MyLog;
import com.hangzhou.zhb.myelves.utils.MyTime;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class IntentService extends GTIntentService {

	private Gson gson;
	List<TuiSongResponse> temp;

	public IntentService() {

	}

	@Override
	public void onReceiveServicePid(Context context, int pid) {
	}

	@Override
	public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
		byte[] payload = msg.getPayload();
		if(payload != null) {
			Constants.NOTIFICATIONID++;
			String data = new String(payload);
			gson = new Gson();
			TuiSongResponse tuiSongMessage = gson.fromJson(data, TuiSongResponse.class);
			if(tuiSongMessage != null) {
				if(tuiSongMessage.mt != null){
					if(tuiSongMessage.content != null){
						// : 2018/1/31 内容条件为null
						if(tuiSongMessage.content.content != null){
							saveInfo(tuiSongMessage);
							WakeUpBroadcast();
							toActivity(context,tuiSongMessage);
						}else {
							MyLog.e("透传通知消息体tuiSongMessage.content.message为空");
						}
					}else {
						MyLog.e("透传通知消息体tuiSongMessage.content为空");
					}
				}else {
					MyLog.e("透传通知消息体mt为空");
				}
			}else {
				MyLog.e("透传通知消息体tuiSongMessage为空");
			}
		}else {
			MyLog.e("payload为空");
		}
	}

	@Override
	public void onReceiveClientId(Context context, String clientId) {
//		Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
		Global.setClientId(context,clientId);
	}

	@Override
	public void onReceiveOnlineState(Context context, boolean online) {
	}

	@Override
	public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
	}

	/**
	 * 唤醒广播，对界面进行修改
	 */
	private void WakeUpBroadcast(){
		Intent intentSystemMessage = new Intent();
		intentSystemMessage.setAction("net.enjoyor.ywb_hz.module.systeminfo.BroadcastReceiverHelper");
		sendBroadcast(intentSystemMessage);

		Intent intentMainMessage = new Intent();
		intentMainMessage.setAction("net.enjoyor.ywb_hz.module.main.BroadcastReceiverHelper");
		sendBroadcast(intentMainMessage);
	}

	/**
	 * 跳转到不同activity
	 * @param context
	 * @param tuiSongMessage
	 */
	private void toActivity(Context context,TuiSongResponse tuiSongMessage){
//		Intent intent = new Intent(context, TestSmoothActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putString("mt","1");
//		intent.putExtras(bundle);
//		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
//		showNotification(getApplicationContext(),pi,"凡间精灵","凡间精灵","凡间精灵","凡间精灵",tuiSongMessage);
	}

	/**
	 * 保存接收到的通知
	 * @param tuiSongMessage
	 */
	private void saveInfo(TuiSongResponse tuiSongMessage){
		tuiSongMessage.content.time = MyTime.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss");

		//保存每一条获取到的通知
		TuiSongListResponse tuiSongListResponse = gson.fromJson(Global.getTuiSongListResponse(this), TuiSongListResponse.class);
		if(tuiSongListResponse == null) {
			tuiSongListResponse = new TuiSongListResponse();
			tuiSongListResponse.TuiSongResponseList_1 = new ArrayList<>();
			tuiSongListResponse.TuiSongResponseList_2 = new ArrayList<>();
			tuiSongListResponse.TuiSongResponseList_3 = new ArrayList<>();
			tuiSongListResponse.TuiSongResponseList_4 = new ArrayList<>();
			tuiSongListResponse.TuiSongResponseList_5 = new ArrayList<>();
		}
		//通知数不可超过100
		int size = tuiSongListResponse.TuiSongResponseList_1.size();
		//限制100条已读
		if(size > 100){
			for(int i = size - 1;i < 100;i--){
				if(i < 0){
					break;
				}
				if(tuiSongListResponse.TuiSongResponseList_1.get(i).content != null){
					if(tuiSongListResponse.TuiSongResponseList_1.get(i).content.seen){
						tuiSongListResponse.TuiSongResponseList_1.remove(i);
					}
				}
			}
		}
		temp = new ArrayList<>();//临时转存通知历史记录
		temp.add(tuiSongMessage);
		temp.addAll(tuiSongListResponse.TuiSongResponseList_1);
		tuiSongListResponse.TuiSongResponseList_1.clear();
		tuiSongListResponse.TuiSongResponseList_1.addAll(temp);
		Global.setTuiSongListResponse(this, gson.toJson(tuiSongListResponse));
	}

	/**
	 * 生成通知
	 * @param context
	 * @param pi
	 * 例如：
	 * Intent intentYiChe = new Intent(context, YiCheRecordActivity.class);
	 * Bundle bundle = new Bundle();
	 * intentYiChe.putExtras(bundle);
	 * PendingIntent piYiChe = PendingIntent.getActivity(context, 0, intentYiChe, 0);
	 * @param ticker 			标题
	 * @param contentTitle		标题
	 * @param bigContentTitle	标题
	 * @param summaryText		标题
	 * @param tuiSongMessage	显示内容
	 */
	private void showNotification(Context context,PendingIntent pi,
								  String ticker,String contentTitle,String bigContentTitle,String summaryText,
								  TuiSongResponse tuiSongMessage){
		//android O后必须传入NotificationChannel
		if(SDK_INT >= Build.VERSION_CODES.O){
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			if(notificationManager != null){
				NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"1");
				setNotification(builder,context,pi,ticker,contentTitle,bigContentTitle,summaryText,tuiSongMessage);
				notificationManager.notify(Constants.NOTIFICATIONID, builder.build());

				//ChannelId为"1",ChannelName为"Channel1"
				NotificationChannel channel = new NotificationChannel("1",
						"Channel1", NotificationManager.IMPORTANCE_DEFAULT);
				channel.enableLights(true); //是否在桌面icon右上角展示小红点
				channel.setLightColor(Color.YELLOW); //小红点颜色
				channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
				notificationManager.createNotificationChannel(channel);
			}else {
				MyLog.i("notificationManager为null");
			}
		}else {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
			setNotification(builder,context,pi,ticker,contentTitle,bigContentTitle,summaryText,tuiSongMessage);
			NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
			managerCompat.notify(Constants.NOTIFICATIONID, builder.build());
		}
	}

	/**
	 * 设置大布局通知参数
	 * @param builder
	 * @param context
	 * @param pi
	 * @param ticker
	 * @param contentTitle
	 * @param bigContentTitle
	 * @param summaryText
	 * @param tuiSongMessage
	 */
	private void setNotification(NotificationCompat.Builder builder, Context context, PendingIntent pi,
                                 String ticker, String contentTitle, String bigContentTitle, String summaryText,
                                 TuiSongResponse tuiSongMessage){
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
				.setTicker(ticker)
				.setContentTitle(contentTitle)
				.setWhen(System.currentTimeMillis())
				.setContentIntent(pi)
				.setVibrate(new long[]{500, 500, 500, 500, 500, 500})
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND);

		//大布局通知在4.1以后才能使用，BigTextStyle
		NotificationCompat.BigTextStyle textStyle = null;
		if(SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			textStyle = new NotificationCompat.BigTextStyle();
			textStyle.setBigContentTitle(bigContentTitle)
					// 标题
					.setSummaryText(summaryText)
					.bigText(tuiSongMessage.content.content);// 内容
			builder.setStyle(textStyle);
		}
		builder.setContentText(tuiSongMessage.content.content);

		if(SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder.setSmallIcon(R.drawable.logo_alpha);
		} else {
			builder.setSmallIcon(R.mipmap.ic_launcher);
		}
	}
}