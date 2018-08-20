package com.hangzhou.zhb.myelves.utils;

import java.io.File;

/**
 * Created by Unborn on 2016/10/12.
 *
 */

public class PathManger {
    public static File getApkDir() {
        String path = Constants.APK_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
