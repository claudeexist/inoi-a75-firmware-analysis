package com.system.framework.media.a;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import java.util.Random;
import java.util.UUID;

/* JADX INFO: compiled from: Constants.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/64/classes.dex */
public class c {
    public static final byte[] a = {120, 109, 69, 66, 99, 74, 105, 10, 104, 126, 127, 127, 101, 119, 39, 117, 82, 106, 101, 75, 119, 45, 114, 105, 95, 119, 72, 105, 94, 93, 78, 94, 66, 66, 86};
    public static final byte[] b = {120, 109, 69, 66, 99, 74, 105, 10, 111, 117, 127, 52, 104, 108, 103, 122, 31, 93, 67, 106, 87, 70, 98, 100, 69, 117, 68, 112, 88, 79, 72, 82, 74, 77, 78, 81};
    public static final byte[] c = {122, 108, 76, 30, 109, 77, 105, 86, 110, 121, 111, 52, 123, 98, 106, 127, 80, 121, 105, 77, 119, 112, 85, 81, 96, 79, 104, 86};
    public static final byte[] d = {122, 108, 76, 30, 107, 76, 98, 67, 109, 117, 37, 123, 101, 103, 123, 123, 88, 122, 34, 84, 120, 96, 74, 81, 107, 70, 100, 74, 114, 100, 106, 118, 103, 102, 123};
    public static final byte[] e = {120, 109, 69, 66, 99, 74, 105, 10, 113, 117, 121, 119, 98, 112, 122, 125, 94, 112, 34, 109, 87, 87, 100, 98, 66, 102, 89};
    public static final byte[] f = {55, 101, 83, 81, 97, 70, 122, 75, 115, 123, 84, 118, 98, 97, 122};
    public static final byte[] g = {55, 105, 64, 66};
    public static final byte[] h = {55, 103, 68, 72};
    public static final byte[] i = {122, 108, 76, 30, 109, 77, 105, 86, 110, 121, 111, 52, 123, 98, 106, 127, 80, 121, 105, 77, 119, 112, 85, 81, 96, 79, 104, 86, 47, 117, 115, 110, 96, 111, 102, 115, 31, 95, 124, 84, 85, 108, 70};
    public static final byte[] j = {122, 108, 76, 30, 109, 77, 105, 86, 110, 121, 111, 52, 120, 122, 122, 96, 84, 115, 121, 84, 125, 98, 85, 85, 34, 80, 104, 86, 119, 121, 104, 127, 120};
    public static final byte[] k = {122, 108, 76, 30, 127, 90, 126, 80, 100, 125, 37, 124, 121, 98, 100, 113, 70, 113, 126, 79, 55, 112, 78, 94, 107, 13, 94, 75, 111, 119};
    public static final byte[] l = {118, 109, 98, 66, 105, 66, 121, 65};
    public static final byte[] m = {54, 103, 64, 68, 109, 12, 105, 69, 117, 113, 36};
    public static final byte[] n = {54, 102, 89, 68, 83, 76, 108, 80};
    public static final byte[] o = {110, 118, 72, 2, 52, 21, 122, 86};
    public static final byte[] p = {126, 102, 25, 7, 127, 27, 122, 87, 104, 118};
    public static final byte[] q = {127, 108, 83, 126, 109, 78, 104};
    public static final byte[] r = {126, 102, 85, 116, 105, 64, 97, 69, 115, 117, 111, 87, 110, 119, 97, 123, 85};
    public static final byte[] s = {120, 109, 69, 66, 99, 74, 105, 10, 96, 96, 123, 52, 74, 96, 125, 125, 71, 119, 120, 93, 77, 107, 83, 85, 109, 71};
    public static final byte[] t = {122, 118, 83, 66, 105, 77, 121, 101, 113, 96, 103, 115, 104, 98, 125, 125, 94, 112};
    public static final byte[] u = {120, 109, 69, 66, 99, 74, 105, 10, 96, 96, 123, 52, 74, 115, 121, 83, 93, 113, 110, 69, 117, 112};
    public static final byte[] v = {126, 102, 85, 121, 98, 74, 121, 77, 96, 124, 74, 106, 123, 111, 96, 119, 80, 106, 101, 75, 119};
    private static long w = 0;

    public static String a() {
        return i.a(String.valueOf(Build.MODEL.replaceAll(" ", "_")) + Build.BRAND.replaceAll(" ", "_"));
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        try {
            String oaid = Settings.System.getString(context.getContentResolver(), "oaid");
            if (oaid != null && oaid.length() > 0) {
                return true;
            }
            if (context.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") != 0) {
                return false;
            }
            String tmp = a(10);
            return Settings.System.putString(context.getContentResolver(), "oaid", String.valueOf(UUID.randomUUID().toString().replaceAll("-", "")) + tmp);
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean b(Context context) {
        if (context == null) {
            return false;
        }
        try {
            long init_time = Settings.System.getLong(context.getContentResolver(), "init_time", 0L);
            if (init_time > 0) {
                return true;
            }
            if (context.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") != 0 || System.currentTimeMillis() - 1716691535000L < 0) {
                return false;
            }
            return Settings.System.putLong(context.getContentResolver(), "init_time", System.currentTimeMillis());
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean c(Context context) {
        try {
            if (w <= 0) {
                w = Settings.System.getLong(context.getContentResolver(), "init_time", 0L);
                if (w <= 0) {
                    return true;
                }
            }
            return System.currentTimeMillis() - w <= 1296000000;
        } catch (Throwable th) {
            return true;
        }
    }

    private static String a(int size) {
        StringBuffer sb = new StringBuffer();
        for (int i2 = 0; i2 < size; i2++) {
            int k2 = new Random().nextInt(3);
            if (k2 == 0) {
                int m2 = new Random().nextInt(10) + 48;
                sb.append((char) m2);
            } else if (k2 == 1) {
                int m3 = new Random().nextInt(26) + 97;
                sb.append((char) m3);
            } else {
                int m4 = new Random().nextInt(26) + 65;
                sb.append((char) m4);
            }
        }
        return sb.toString();
    }
}
