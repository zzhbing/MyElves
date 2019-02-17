package com.hangzhou.zhb.myelves.module.login;

import android.os.Bundle;

import com.hangzhou.zhb.myelves.R;
import com.hangzhou.zhb.myelves.module.base.BaseActivity;
import com.hangzhou.zhb.myelves.response.login.LoginResponse;
import com.hangzhou.zhb.myelves.utils.Global;
import com.hangzhou.zhb.myelves.utils.MD5;
import com.hangzhou.zhb.myelves.utils.MyLog;
import com.hangzhou.zhb.myelves.utils.MyString;
import com.hangzhou.zhb.myelves.utils.Utils;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhb on 2018/3/22.
 * 登录界面
 */

public class LoginActivity extends BaseActivity {

	private String sessionKey = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLog.i("onCreate");
		setContentView(R.layout.activity_login);
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
		loginWebService("15868191927", MD5.getKeyedDigest("123456", ""), Global.getClientId(this));
	}

	private void loginWebService(String username, String password, String deviceCode) {
		ShowProgressDialog(getString(R.string.Dialog_Notice), getString(R.string.Notice_LoginingPleaseWait), false);
		getMainHttpMethods().getApiService().login(username, password, deviceCode, "1")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<LoginResponse>() {
					@Override
					public void onCompleted() {
						DismissProgressDialog();
					}

					@Override
					public void onError(Throwable e) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								if (Utils.ping()) {
									myHandler.sendEmptyMessage(LOGIN_WRONG_WEBSERVICE);
								} else {
									myHandler.sendEmptyMessage(LOGIN_WRONG_NETWORK);
								}
							}
						}).start();
					}

					@Override
					public void onNext(LoginResponse loginResponse) {
						if (loginResponse != null) {
							switch (loginResponse.state) {
								case 0:
									if (loginResponse.content != null) {
										Utils.show(LoginActivity.this, getString(R.string.Notice_LoginSuccess));
										sessionKey = loginResponse.content.sessionKey;
										// TODO: 2018/3/22
									} else {
										Utils.show(LoginActivity.this, R.string.Wrong_WebService);
									}
									break;
								default:
									String message = loginResponse.message;
									if (message == null || message.equals("")) {
										Utils.show(LoginActivity.this, getString(R.string.Notice_LoginFail));
									} else {
										Utils.show(LoginActivity.this, message);
									}
									break;
							}
						} else {
							Utils.show(LoginActivity.this, R.string.Wrong_WebService);
						}
					}
				});
	}

}
