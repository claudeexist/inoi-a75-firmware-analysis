package com.android.systemupdate.a.a;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class p {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(str.getBytes("UTF-8"));
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append(String.valueOf(0));
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString().toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean a(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        } catch (Throwable th) {
            return true;
        }
    }

    public static String a(int size) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int k = new Random().nextInt(3);
            if (k == 0) {
                int m = new Random().nextInt(10) + 48;
                sb.append((char) m);
            } else if (k == 1) {
                int m2 = new Random().nextInt(26) + 97;
                sb.append((char) m2);
            } else {
                int m3 = new Random().nextInt(26) + 65;
                sb.append((char) m3);
            }
        }
        return sb.toString();
    }

    public static String a(byte[] data) {
        return o.a().a(data);
    }

    public static String b(byte[] data) {
        return a(a(data));
    }

    public static String b(Context context) {
        return context == null ? "" : String.valueOf(context.getFilesDir().getAbsolutePath()) + File.separator + a(com.android.systemupdate.a.a.p) + File.separator;
    }

    public static String c(Context context) {
        return h.a(context);
    }

    public static String d(Context context) {
        String imsi;
        String imsi2;
        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService("phone");
            String imsi3 = tManager.getSubscriberId();
            imsi = imsi3;
        } catch (Throwable th) {
            imsi = null;
        }
        if (imsi != null) {
            try {
                if (imsi.length() > 0) {
                    return imsi;
                }
            } catch (Throwable th2) {
                imsi2 = imsi;
            }
        }
        imsi2 = l.b(context);
        return imsi2 == null ? "" : imsi2;
    }

    public static String e(Context context) {
        String macAddress = "";
        try {
            String str = "";
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            while (str != null) {
                str = input.readLine();
                if (!TextUtils.isEmpty(str)) {
                    macAddress = str.trim();
                    break;
                }
            }
        } catch (Throwable th) {
        }
        try {
            if (TextUtils.isEmpty(macAddress) && Build.VERSION.SDK_INT < 23) {
                try {
                    if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
                        WifiManager wifiMgr = (WifiManager) context.getSystemService("wifi");
                        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                        macAddress = wifiInfo.getMacAddress();
                    }
                } catch (Throwable th2) {
                }
            }
            if (TextUtils.isEmpty(macAddress)) {
                macAddress = l();
            }
            if (!TextUtils.isEmpty(macAddress)) {
                return macAddress.toUpperCase(Locale.getDefault());
            }
        } catch (Throwable th3) {
        }
        return macAddress;
    }

    private static String l() {
        String strMacAddr = null;
        try {
            InetAddress ip = m();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 255);
                if (str.length() == 1) {
                    str = String.valueOf(0) + str;
                }
                buffer.append(str);
            }
            strMacAddr = buffer.toString();
            return strMacAddr;
        } catch (Throwable th) {
            return strMacAddr;
        }
    }

    private static InetAddress m() {
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {
                NetworkInterface ni = en_netInterface.nextElement();
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        break;
                    }
                    ip = null;
                }
                if (ip != null) {
                    break;
                }
            }
        } catch (Throwable th) {
        }
        return ip;
    }

    public static int f(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Throwable th) {
            return 0;
        }
    }

    public static String g(Context context) {
        try {
            return context.getPackageName();
        } catch (Throwable th) {
            return "";
        }
    }

    public static int h(Context context) {
        NetworkInfo activeNetInfo;
        NetworkInfo.State state;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connManager == null || (activeNetInfo = connManager.getActiveNetworkInfo()) == null || !activeNetInfo.isAvailable()) {
                return 9;
            }
            NetworkInfo wifiInfo = connManager.getNetworkInfo(1);
            if (wifiInfo != null && (state = wifiInfo.getState()) != null && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING)) {
                return 1;
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                int networkType = telephonyManager.getNetworkType();
                if (networkType == 20) {
                    return 5;
                }
            } catch (Throwable th) {
            }
            NetworkInfo networkInfo = connManager.getNetworkInfo(0);
            if (networkInfo == null) {
                return 9;
            }
            NetworkInfo.State state2 = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (state2 == null) {
                return 9;
            }
            if (state2 != NetworkInfo.State.CONNECTED && state2 != NetworkInfo.State.CONNECTING) {
                return 9;
            }
            switch (activeNetInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 2;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return 3;
                case 13:
                    return 4;
                case 16:
                case 17:
                case 18:
                case 19:
                default:
                    return (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) ? 3 : 9;
                case 20:
                    return 5;
            }
            return 9;
        } catch (Throwable th2) {
            return 9;
        }
    }

    public static String a() {
        return Build.BRAND;
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String c() {
        return Build.VERSION.RELEASE;
    }

    public static int d() {
        return Build.VERSION.SDK_INT;
    }

    public static void a(String sPath, String tPath, int isDecrypt, byte[] google, byte[] config) {
        try {
            if (isDecrypt == 1) {
                new e(google, config).a(sPath, tPath);
            } else {
                File file = new File(sPath);
                if (file.exists()) {
                    file.renameTo(new File(tPath));
                }
            }
        } catch (Throwable th) {
        }
    }

    public static int i(Context context) {
        String proxyAddress;
        int proxyPort;
        try {
            boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= 14;
            if (IS_ICS_OR_LATER) {
                proxyAddress = System.getProperty("http.proxyHost");
                String portStr = System.getProperty("http.proxyPort");
                if (portStr == null) {
                    portStr = "-1";
                }
                proxyPort = Integer.parseInt(portStr);
            } else {
                proxyAddress = Proxy.getHost(context);
                proxyPort = Proxy.getPort(context);
            }
            boolean result = (TextUtils.isEmpty(proxyAddress) || proxyPort == -1) ? false : true;
            return result ? 1 : 0;
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int e() {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (intf.isUp() && intf.getInterfaceAddresses().size() != 0 && ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName()))) {
                        return 1;
                    }
                }
            }
        } catch (Throwable th) {
        }
        return 0;
    }

    public static long j(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            long memfree = (mi.availMem / 1024) / 1024;
            return memfree;
        } catch (Throwable th) {
            return 0L;
        }
    }

    public static long f() {
        try {
            long freesize = (Environment.getDataDirectory().getFreeSpace() / 1024) / 1024;
            return freesize;
        } catch (Throwable th) {
            return 0L;
        }
    }

    public static String k(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            return "";
        }
    }

    public static String g() {
        try {
            return Build.DISPLAY;
        } catch (Throwable th) {
            return "";
        }
    }

    public static long h() {
        try {
            return Build.TIME;
        } catch (Throwable th) {
            return 0L;
        }
    }

    public static int i() {
        try {
            return Calendar.getInstance().get(11);
        } catch (Throwable th) {
            return 0;
        }
    }

    public static boolean l(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.checkPermission("android.permission.INTERNET", context.getPackageName()) == 0;
        } catch (Throwable th) {
            return true;
        }
    }

    public static String j() {
        try {
            String platformProp = b("getprop ro.board.platform").trim();
            if (platformProp == null || platformProp.length() == 0) {
                platformProp = c("ro.board.platform");
            }
            return (platformProp == null || platformProp.length() <= 0) ? "" : platformProp;
        } catch (Throwable th) {
        }
    }

    public static String k() {
        return Build.HARDWARE;
    }

    private static String b(String cmd) throws Throwable {
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
                } catch (Throwable th) {
                    th = th;
                    dis = dis2;
                    if (dis != null) {
                        try {
                            dis.close();
                        } catch (Throwable th2) {
                        }
                    }
                    throw th;
                }
            }
            p.waitFor();
            if (dis2 != null) {
                try {
                    dis2.close();
                } catch (Throwable th3) {
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
        return result;
    }

    private static String c(String name) {
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method getMethod = clazz.getMethod("get", String.class, String.class);
            String prop = (String) getMethod.invoke(clazz, name, "_unknown_");
            if (prop == null || prop.equals("_unknown_")) {
                return null;
            }
            return prop;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String m(Context context) {
        try {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        } catch (Throwable th) {
            return "";
        }
    }
}
