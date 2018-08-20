package com.hangzhou.zhb.myelves.module.base;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hangzhou.zhb.myelves.R;
import com.hangzhou.zhb.myelves.http.MainHttpMethods;
import com.hangzhou.zhb.myelves.utils.MyHandler;
import com.hangzhou.zhb.myelves.utils.MyLog;
import com.hangzhou.zhb.myelves.utils.Utils;
import com.hangzhou.zhb.myelves.utils.getui.IntentService;
import com.hangzhou.zhb.myelves.utils.getui.PushService;
import com.igexin.sdk.PushManager;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by zhb on 2017/8/16.
 * 基础activity
 */
@RuntimePermissions
public class BaseActivity extends AppCompatActivity {
    private MainHttpMethods mainHttpMethods = new MainHttpMethods();
    public MaterialDialog baseDialog;

    public MyHandler myHandler = new MyHandler(this);
    public final int LOGIN_WRONG_WEBSERVICE = 0;
    public final int LOGIN_WRONG_NETWORK = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.i("onCreate 1080*1920");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        BaseApplication.addDestroyActivity(this, this.getLocalClassName());
        //透明状态栏，Android4.4以上有效，参考博客http://blog.csdn.net/carlos1992/article/details/46773059
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // PushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        // com.getui.demo.IntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);

        myHandler.setOnHandleMessageReturnListener(new MyHandler.OnHandleMessageReturnListener() {
            @Override
            public void onHandleMessageReturn(Message msg) {
                switch (msg.what) {
                    case LOGIN_WRONG_WEBSERVICE:
                        DismissProgressDialog();
                        Utils.show(BaseActivity.this, R.string.Wrong_WebService);
                        break;
                    case LOGIN_WRONG_NETWORK:
                        DismissProgressDialog();
                        Utils.show(BaseActivity.this, R.string.Wrong_Network);
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        MyLog.i("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.i("onResume");
        BaseActivityPermissionsDispatcher.storageWithPermissionCheck(this);
        BaseActivityPermissionsDispatcher.showPhoneStateWithPermissionCheck(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.i("onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        MyLog.i("onStop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        MyLog.i("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.i("onRestart");
    }

    public MainHttpMethods getMainHttpMethods() {
        return mainHttpMethods;
    }

    public void ShowProgressDialog(String title, String content, boolean cancelable) {
        baseDialog = new MaterialDialog.Builder(this)
                .title(title)
                .theme(Theme.LIGHT)
                .content(content)
                .progress(true, 0)
                .cancelable(cancelable)
                .progressIndeterminateStyle(false)
                .build();
        if(!baseDialog.isShowing()) {
            baseDialog.show();
        }
    }

    public void DismissProgressDialog() {
        if (baseDialog != null) {
            if (baseDialog.isShowing()) {
                try {
                    baseDialog.dismiss();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void storage() {
        MyLog.i("获取手机存储权限");
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        new MaterialDialog.Builder(this)
                .title(R.string.Dialog_Notice)
                .theme(Theme.LIGHT)
                .content(R.string.Dialog_Storage_content)
                .positiveText(R.string.Dialog_Agree)
                .negativeText(R.string.Dialog_Disagree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.proceed();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.cancel();
                    }
                })
                .cancelable(false)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForStorage() {
//        Utils.show(this, R.string.Dialog_Storage_OnPermissionDenied);
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForStorage() {
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void showPhoneState() {
        MyLog.i("获取手机状态信息权限");
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationaleForPhoneState(final PermissionRequest request) {
        new MaterialDialog.Builder(this)
                .title(R.string.Dialog_Notice)
                .theme(Theme.LIGHT)
                .content(R.string.Dialog_PhoneState_content)
                .positiveText(R.string.Dialog_Agree)
                .negativeText(R.string.Dialog_Disagree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.proceed();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.cancel();
                    }
                })
                .cancelable(false)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void showDeniedForPhoneState() {
        //        Utils.show(this, R.string.Dialog_Storage_OnPermissionDenied);
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void showNeverAskForPhoneState() {
    }
}
