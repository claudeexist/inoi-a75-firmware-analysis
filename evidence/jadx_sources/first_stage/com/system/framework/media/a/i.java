package com.system.framework.media.a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/64/classes.dex */
public class i {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(str.getBytes("UTF-8"));
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append(String.valueOf(0));
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString().toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            return "";
        }
    }

    public static String a(byte[] data) {
        return h.a().a(data);
    }

    public static String b(byte[] data) {
        return a(a(data));
    }

    public static boolean a(Context context, String permName) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.checkPermission(permName, context.getPackageName()) == 0;
        } catch (Throwable th) {
            return true;
        }
    }

    public static boolean a(Context context) {
        try {
            return context.getPackageCodePath().startsWith("/system/");
        } catch (Throwable th) {
            return false;
        }
    }
}
