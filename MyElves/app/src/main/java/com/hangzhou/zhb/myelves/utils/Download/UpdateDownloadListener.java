package com.hangzhou.zhb.myelves.utils.download;

public interface UpdateDownloadListener {
	void onStarted();
	void onProgressChanged(int progress, String downloadUrl);
	void onFinished(float completeSize, String downloadUrl);
	void onFailure(Exception e);
}