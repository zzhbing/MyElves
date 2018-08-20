package com.hangzhou.zhb.myelves.module.main;

import android.os.Bundle;

import com.hangzhou.zhb.myelves.R;
import com.hangzhou.zhb.myelves.module.base.BaseActivity;
import com.hangzhou.zhb.myelves.utils.MyLog;

import butterknife.ButterKnife;

/**
 * Created by zhb on 2018/3/22.
 * 主页
 */

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLog.i("onCreate");
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		MyLog.i("onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		MyLog.i("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		MyLog.i("onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		MyLog.i("onStop");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		MyLog.i("onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyLog.i("onRestart");
	}

	private void initView() {

	}

}
