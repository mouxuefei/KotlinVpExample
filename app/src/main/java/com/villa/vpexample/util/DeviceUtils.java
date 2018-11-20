package com.villa.vpexample.util;

import android.annotation.SuppressLint;
import android.provider.Settings;
import android.text.TextUtils;

import com.exmple.corelib.base.LibApplication;
import com.hankkin.library.utils.MSPUtils;

import java.util.UUID;

public class DeviceUtils {


    private static String getAndroidID() {
        @SuppressLint("HardwareIds") String id = Settings.Secure.getString(
                LibApplication.Companion.getMContext().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }

    private static String getDeviceUUid()
    {
        String androidId =getAndroidID();
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)androidId.hashCode() << 32));
        return deviceUuid.toString();
    }

    private static String getAppUUid() {
        String uuid =MSPUtils.INSTANCE.getString("uuid");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            //这里需要保存到SharedPreference中
            MSPUtils.INSTANCE.saveObject("uuid",uuid);
        }
        return uuid;
    }

    public static String getUUID() {
        String uuid = getDeviceUUid();
        if (TextUtils.isEmpty(uuid)) {
            uuid = getAppUUid();
        }
        return uuid;
    }
}
