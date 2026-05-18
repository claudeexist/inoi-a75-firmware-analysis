package com.android.packageinstaller.extplog;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: AppInstallManager.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/a/classes.dex */
public class a {
    public final int a = 2;
    private b b = new b();
    private BinderC0000a c = new BinderC0000a();
    private PackageManager d;
    private Method e;
    private Method f;
    private d g;

    /* JADX INFO: compiled from: AppInstallManager.java */
    class b extends IPackageInstallObserver.Stub {
        b() {
        }

        @Override // android.content.pm.IPackageInstallObserver
        public void packageInstalled(String packageName, int returnCode) throws RemoteException {
            if (a.this.g != null) {
                a.this.g.a(packageName, returnCode);
            }
        }
    }

    /* JADX INFO: renamed from: com.android.packageinstaller.extplog.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AppInstallManager.java */
    class BinderC0000a extends IPackageDeleteObserver.Stub {
        BinderC0000a() {
        }

        @Override // android.content.pm.IPackageDeleteObserver
        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            if (a.this.g != null) {
                a.this.g.b(packageName, returnCode);
            }
        }
    }

    public a(Context context) throws NoSuchMethodException, SecurityException {
        this.d = context.getPackageManager();
        Class<?>[] clsArr = {Uri.class, IPackageInstallObserver.class, Integer.TYPE, String.class};
        Class<?>[] clsArr2 = {String.class, IPackageDeleteObserver.class, Integer.TYPE};
        this.e = this.d.getClass().getMethod("installPackage", clsArr);
        this.f = this.d.getClass().getMethod("deletePackage", clsArr2);
    }

    public void a(d onInstalledPackaged) {
        this.g = onInstalledPackaged;
    }

    public void a(String packagename) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f.invoke(this.d, packagename, this.c, 0);
    }

    public void b(String apkFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(new File(apkFile));
    }

    public void a(File apkFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!apkFile.exists()) {
            throw new IllegalArgumentException();
        }
        Uri packageURI = Uri.fromFile(apkFile);
        a(packageURI);
    }

    public void a(Uri apkFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = this.e;
        PackageManager packageManager = this.d;
        Object[] objArr = new Object[4];
        objArr[0] = apkFile;
        objArr[1] = this.b;
        objArr[2] = 2;
        method.invoke(packageManager, objArr);
    }
}
