package com.hangzhou.zhb.myelves.module.base;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhb on 2017/8/16.
 *
 */

public class BaseApplication extends Application {

    private static Map<String, Activity> destroyMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 添加要销毁的Activity
     *
     * @param activity     activity
     * @param activityName activityName
     */
    public static void addDestroyActivity(Activity activity, String activityName) {
        destroyMap.remove(activityName);
        destroyMap.put(activityName, activity);
    }

    /**
     * 删除要销毁的Activity
     *
     * @param activityName activityName
     */
    public static void removeDestroyActivity(String activityName) {
        destroyMap.remove(activityName);
    }

    /**
     * 销毁指定Activity
     *
     * @param activityName activityName
     */
    public static void destroyActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            if (key.equals(activityName)) {
                destroyMap.get(key).finish();
            }
        }
    }

    /**
     * 销毁所有tActivity
     */
    public static void destroyAll() {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            destroyMap.get(key).finish();
        }
        destroyMap.clear();
    }

    public static boolean isActivityExist(String activity) {
        return destroyMap.get(activity) != null;
    }
}
