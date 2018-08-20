package com.zhb.us.ijkplayerdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@RuntimePermissions
public class MainActivity extends Activity {
	VideoPlayerIJK ijkPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bb);

		//加载so文件
		try {
			IjkMediaPlayer.loadLibrariesOnce(null);
			IjkMediaPlayer.native_profileBegin("libijkplayer.so");
		} catch (Exception e) {
			this.finish();
		}

		ijkPlayer = findViewById(R.id.ijk_player);
		ijkPlayer.setListener(new VideoPlayerListener() {
			@Override
			public void onBufferingUpdate(IMediaPlayer mp, int percent) {
				Log.d("bb", "onBufferingUpdate: ");
			}

			@Override
			public void onCompletion(IMediaPlayer mp) {
				Log.d("bb", "onCompletion: ");
				mp.seekTo(0);
			}

			@Override
			public boolean onError(IMediaPlayer mp, int what, int extra) {
				Log.d("bb", "onError: ");
				return false;
			}

			@Override
			public boolean onInfo(IMediaPlayer mp, int what, int extra) {
				Log.d("bb", "onInfo: ");
				return false;
			}

			@Override
			public void onPrepared(IMediaPlayer mp) {
				Log.d("bb", "onPrepared: ");
				mp.start();
			}

			@Override
			public void onSeekComplete(IMediaPlayer mp) {
				Log.d("bb", "onSeekComplete: ");
				mp.start();
			}

			@Override
			public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
				//获取到视频的宽和高
				Log.d("bb", "onVideoSizeChanged: ");
			}
		});

		MainActivityPermissionsDispatcher.storageWithPermissionCheck(this);
	}

	public void loadVideo(String path) {
		ijkPlayer.setVideoPath(path);
	}

	@Override
	protected void onStop() {
		super.onStop();
		IjkMediaPlayer.native_profileEnd();
	}

	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.floating_button:
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, 1);
				break;
		}
	}

	String path;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
				path = uri.getPath();
				return;
			}
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
				path = getPath(this, uri);
				Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
				loadVideo(path);
			} else {//4.4以下下系统调用方法
				path = getRealPathFromURI(uri);
				Toast.makeText(MainActivity.this, path + " ", Toast.LENGTH_SHORT).show();
				loadVideo(path);
			}
		}
	}

	public String getRealPathFromURI(Uri contentUri) {
		String res = null;
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
			cursor.close();
		}
		return res;
	}

	/**
	 * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
	 */
	@SuppressLint ("NewApi")
	public String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{split[1]};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public String getDataColumn(Context context, Uri uri, String selection,
								String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// NOTE: delegate the permission handling to generated method
		MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
	}

	@NeedsPermission (Manifest.permission.WRITE_EXTERNAL_STORAGE)
	void storage() {
	}

	@OnShowRationale (Manifest.permission.WRITE_EXTERNAL_STORAGE)
	void showRationaleForStorage(final PermissionRequest request) {
		request.proceed();
	}

	@OnPermissionDenied (Manifest.permission.WRITE_EXTERNAL_STORAGE)
	void showDeniedForStorage() {

	}

	@OnNeverAskAgain (Manifest.permission.WRITE_EXTERNAL_STORAGE)
	void showNeverAskForStorage() {
	}
}
