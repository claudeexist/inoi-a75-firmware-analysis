package com.android.packageinstaller.extplog;

import android.content.Context;

/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/a/classes.dex */
public class AppLog {
    public static void onCreate(Context context) {
        onCreate(context, 0);
    }

    public static void onCreate(Context context, int type) {
        onCreate(context, type, null);
    }

    public static void onCreate(Context context, int type, String chl) {
        b.a().a(context);
    }

    public static void onDestroy(Context context) {
        onDestroy(context, 0);
    }

    public static void onDestroy(Context context, int type) {
        onDestroy(context, type, null);
    }

    public static void onDestroy(Context context, int type, String chl) {
        b.a().b();
    }
}
