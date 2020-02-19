package com.hangzhou.zhb.myelves.utils.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by zhb on 2017/12/28.
 *	通知工具
 */

public class MyNotification {

	private Context mContext;

	public MyNotification(){

	}

	public MyNotification(Context mContext){
		this.mContext = mContext;
	}

	/**
	 *
	 * @param NotificationID 通知id，相同通知会覆盖，一般递增
	 * @param mTicker 设置通知第一次到达时在状态栏中显示的文本
	 * @param ContentTitle Title
	 * @param ContentText 通知内容
	 * @param BigContentTitle 大布局Title
	 * @param SummaryText 大布局设置细节部分后的第一行文本。
	 * @param bigText 大布局显示长文本内容
	 * @param LargeIcon 大图标 比如R.mipmap.ic_launcher
	 * @param SmallIconLOLLIPOP 版本大于21的小图标 比如 R.drawable.android_32
	 * @param SmallIconLOLLIPOPColor 版本大于21的小图标背景 比如 R.color.Black_000000
	 * @param SmallIcon 小图标 比如 R.mipmap.ic_launcher
	 * @param mClass 待跳转的activity
	 * @param bundle 跳转后待传递的数据包
	 */
	public void addNotification(int NotificationID,
							  String mTicker,
							  String ContentTitle,
							  String ContentText,
							  String BigContentTitle,
							  String SummaryText,
							  String bigText,
							  int LargeIcon,
							  int SmallIconLOLLIPOP,
							  int SmallIconLOLLIPOPColor,
							  int SmallIcon,
							  Class mClass,
							  Bundle bundle){

		//待执行操作
		Intent intent = new Intent();
		if(mClass != null){
			intent.setClass(mContext,mClass);
			if(null == bundle){
				bundle = new Bundle();
			}
			intent.putExtras(bundle);
		}
		PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent, 0);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
				//设置标记和通知中显示的大型图标
		builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), LargeIcon))
				//设置通知第一次到达时在状态栏中显示的文本
				.setTicker(mTicker)
				//设置文本(第二行)的通知,在标准的通知。
				.setContentTitle(ContentTitle)
				//发起时间
				.setWhen(System.currentTimeMillis())
				//提供一个在发出通知时发送的PendingIntent。
				.setContentIntent(pi)
				//震动模式，在程序起动后等待0.5秒后，振动0.5秒，再等待0.5秒后，振动0.5秒，再等待0.5秒后，振动0.5秒
				.setVibrate(new long[]{500, 500, 500, 500, 500, 500})
				//设置此标志将使通知在用户单击面板时自动取消。
				.setAutoCancel(true)
				//设置将使用的默认通知选项。
				.setDefaults(Notification.DEFAULT_SOUND);

		//大布局通知在4.1以后才能使用，部分平台不支持即使用标准通知，BigTextStyle
		if(SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			NotificationCompat.BigTextStyle textStyle = new NotificationCompat.BigTextStyle();
			//在模板的大形式中重写ContentTitle。
			textStyle.setBigContentTitle(BigContentTitle)
					// 标题,在模板的大窗体中设置细节部分后的第一行文本。
					.setSummaryText(SummaryText)
					// 提供更长的文本，以在模板的大形式中显示内容文本。
					.bigText(bigText);
			builder.setStyle(textStyle);
		}
		//在标准通知中设置通知的文本(第二行)。
		builder.setContentText(ContentText);

		if(SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//设置通知布局中使用的小图标。
			builder.setSmallIcon(SmallIconLOLLIPOP);
			builder.setColor(mContext.getResources().getColor(SmallIconLOLLIPOPColor));
		} else {
			//设置通知布局中使用的小图标。
			builder.setSmallIcon(SmallIcon);
		}

		NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mContext);
		managerCompat.notify(NotificationID, builder.build());
	}
}
