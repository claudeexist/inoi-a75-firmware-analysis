package com.android.systemupdate.a.a;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* JADX INFO: compiled from: IMEIHelper.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class h {
    private static String a = null;

    public static String a(Context context) {
        try {
            if (!TextUtils.isEmpty(a)) {
                return a;
            }
            String imei = m.b(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.l), "");
            if (!TextUtils.isEmpty(imei)) {
                a = imei;
                return imei;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (Build.VERSION.SDK_INT >= 23 && (Build.VERSION.SDK_INT < 26 || (imei = telephonyManager.getImei(0)) == null || imei.length() == 0)) {
                imei = telephonyManager.getDeviceId(0);
            }
            if (TextUtils.isEmpty(imei) && (Build.VERSION.SDK_INT < 26 || (imei = telephonyManager.getImei()) == null || imei.length() == 0)) {
                imei = telephonyManager.getDeviceId();
            }
            if (!TextUtils.isEmpty(imei)) {
                a = imei;
                m.a(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.l), imei);
                return imei;
            }
            return "";
        } catch (Throwable th) {
        }
    }
}
