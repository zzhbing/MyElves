package com.hangzhou.zhb.myelves.utils.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.hangzhou.zhb.myelves.utils.PathManger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateDownloadRequest{

	private UpdateDownloadListener listener;

	public UpdateDownloadRequest(UpdateDownloadListener listener) {
		this.listener = listener;
	}

	/**
	 * 下载APK文件
	 * @param context
	 * @param url
	 * @param filename
	 */
	public void downLoadApk(final Context context, final String url, final String filename) {
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(context,url,filename);
					sleep(1000);
					installApk(file, context);
				} catch (Exception e) {
					listener.onFailure(e);
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 安装软件包
	 * @param file
	 * @param context
	 */
	private void installApk(File file, final Context context) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 下载文件
	 * @param context
	 * @param path
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	private File getFileFromServer(Context context, String path,String filename) throws Exception {// 单线程从服务器下载软件包
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);

			long beforeTime = System.currentTimeMillis();
            int count = conn.getContentLength(); //文件总大小 字节

			InputStream is = conn.getInputStream();
			File file = new File(PathManger.getApkDir().getAbsoluteFile(),
					filename);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
                //1秒 更新2次进度 非常重要 否则 系统会慢慢卡死
                if (System.currentTimeMillis() - beforeTime > 500) {
					listener.onProgressChanged((int) (((double) total / (double) count) * 100), path);
				}
			}
			fos.close();
			bis.close();
			is.close();

			listener.onFinished(100,path);
			return file;
		} else {
			return null;
		}
	}
}