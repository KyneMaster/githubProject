package com.duobang.builder.builder_android.util;

import android.os.Build;

/**
 * Description：
 * Created by kang on 2018/3/9.
 */

public class DeviceUtil {
    /**
     * 获取设备厂商
     * <p>如 Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }
}
