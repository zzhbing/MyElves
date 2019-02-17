package com.hangzhou.zhb.myelves.utils.download;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.hangzhou.zhb.myelves.R;
import com.hangzhou.zhb.myelves.utils.Constants;

import static android.os.Build.VERSION.SDK_INT;

public class UpdateManagerNotification {
	private Context mContext;

	NotificationManager notificationManager;
	NotificationChannel channel;
	NotificationCompat.Builder builder;

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 100){//下完安装，并清除通知
				//android O后必须传入NotificationChannel
				if(SDK_INT >= Build.VERSION_CODES.O){
					notificationManager.cancel(Constants.NOTIFICATIONID_APP);
				}else {
					NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mContext);
					managerCompat.cancel(Constants.NOTIFICATIONID_APP);
				}
			}else if(msg.what >= 0 && msg.what < 100){//下载进度
				builder.setProgress(100, msg.what, false);
				builder.setContentText("下载进度:" + msg.what + "%");
				builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);

				//android O后必须传入NotificationChannel
				if(SDK_INT >= Build.VERSION_CODES.O){
					notificationManager.notify(Constants.NOTIFICATIONID_APP, builder.build());
				}else {
					NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mContext);
					managerCompat.notify(Constants.NOTIFICATIONID_APP, builder.build());
				}
			}
		}
	};

	public UpdateManagerNotification(Context context) {
		this.mContext = context;
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
	 * @param Message	显示内容
	 */
	public void showNotification(Context context, PendingIntent pi,
								  String ticker, String contentTitle, String bigContentTitle, String summaryText,
								  String Message){
		//android O后必须传入NotificationChannel
		if(SDK_INT >= Build.VERSION_CODES.O){
			notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			if(notificationManager != null){
				//ChannelId为"1",ChannelName为"Channel1"
				channel = new NotificationChannel("4",
						"运维通更新通知通道", NotificationManager.IMPORTANCE_DEFAULT);
				channel.enableLights(true); //是否在桌面icon右上角展示小红点
				channel.setLightColor(Color.YELLOW); //小红点颜色
				channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
				channel.setSound(null, null);
				notificationManager.createNotificationChannel(channel);
				builder = new NotificationCompat.Builder(context,"4");
				setNotification(builder,context,pi,ticker,contentTitle,bigContentTitle,summaryText,Message);
				notificationManager.notify(Constants.NOTIFICATIONID_APP, builder.build());
			}
		}else {
			builder = new NotificationCompat.Builder(context,null);
			setNotification(builder,context,pi,ticker,contentTitle,bigContentTitle,summaryText,Message);
			NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
			managerCompat.notify(Constants.NOTIFICATIONID_APP, builder.build());
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
	 * @param Message
	 */
	private void setNotification(NotificationCompat.Builder builder, Context context, PendingIntent pi,
								 String ticker, String contentTitle, String bigContentTitle, String summaryText,
								 String Message){
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
				.setTicker(ticker)
				.setContentTitle(contentTitle)
				.setWhen(System.currentTimeMillis())
				.setContentIntent(pi)
				.setAutoCancel(false)//设置通知被点击一次是否自动取消
				.setOngoing(false)
				.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
				.setProgress(100, 0, false);
		//大布局通知在4.1以后才能使用，BigTextStyle
		NotificationCompat.BigTextStyle textStyle = null;
		if(SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			textStyle = new NotificationCompat.BigTextStyle();
			textStyle.setBigContentTitle(bigContentTitle)
					// 标题
					.setSummaryText(summaryText)
					.bigText(Message);// 内容
			builder.setStyle(textStyle);
		}
		builder.setContentText(Message);

		if(SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder.setSmallIcon(R.drawable.logo_alpha);
		} else {
			builder.setSmallIcon(R.mipmap.ic_launcher);
		}
	}
}
