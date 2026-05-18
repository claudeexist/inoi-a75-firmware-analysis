package com.android.systemupdate.a.a;

import android.content.Context;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: SIMInfo.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class l {
    private static int c(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            Method method = Class.forName("android.telephony.TelephonyManager").getDeclaredMethod("getSmsDefaultSim", new Class[0]);
            method.setAccessible(true);
            Object index = method.invoke(tm, new Object[0]);
            return ((Integer) index).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    private static int d(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            Method method = Class.forName("android.telephony.TelephonyManager").getDeclaredMethod("getDefaultDataPhoneId", new Class[0]);
            method.setAccessible(true);
            Object index = method.invoke(tm, new Object[0]);
            return ((Integer) index).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    private static int a() {
        try {
            Method method = Class.forName("android.telephony.SmsManager").getDeclaredMethod("getDefault", new Class[0]);
            method.setAccessible(true);
            Object param = method.invoke(null, new Object[0]);
            Method method2 = Class.forName("android.telephony.SmsManager").getDeclaredMethod("getPreferredSmsSubscription", new Class[0]);
            method2.setAccessible(true);
            Object index = method2.invoke(param, new Object[0]);
            return ((Integer) index).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    public static String a(int cardIndex) {
        String name = cardIndex == 1 ? "iphonesubinfo2" : "iphonesubinfo";
        try {
            Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            method.setAccessible(true);
            Object param = method.invoke(null, name);
            if (param == null && cardIndex == 1) {
                param = method.invoke(null, "iphonesubinfo1");
            }
            if (param == null) {
                return null;
            }
            Method method2 = Class.forName("com.android.internal.telephony.IPhoneSubInfo$Stub").getDeclaredMethod("asInterface", IBinder.class);
            method2.setAccessible(true);
            Object stubObj = method2.invoke(null, param);
            return (String) stubObj.getClass().getMethod("getSubscriberId", new Class[0]).invoke(stubObj, new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public static String a(Context mContext) {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService("phone");
            return tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String a(Context mContext, int cardIndex) {
        if (cardIndex < 0) {
            return "";
        }
        String imsi = a(cardIndex);
        if (imsi == null) {
            return a(mContext);
        }
        return imsi;
    }

    public static String b(Context context) {
        String imsi;
        String imsi2;
        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService("phone");
            String imsi3 = tManager.getSubscriberId();
            imsi = imsi3;
        } catch (Throwable th) {
            imsi = null;
        }
        if (imsi != null && imsi.length() > 2) {
            return imsi;
        }
        int index = c(context);
        if (index == -1 && (index = d(context)) == -1) {
            index = a();
        }
        if (index == 0 || index == 1) {
            imsi2 = a(context, index);
            if (imsi2 == null || imsi2.length() < 2) {
                if (index == 0) {
                    imsi2 = a(context, 1);
                } else {
                    imsi2 = a(context, 0);
                }
            }
        } else {
            imsi2 = a(context, 0);
            if (imsi2 == null || imsi2.length() < 2) {
                imsi2 = a(context, 1);
            }
        }
        return imsi2 == null ? "" : imsi2;
    }
}
