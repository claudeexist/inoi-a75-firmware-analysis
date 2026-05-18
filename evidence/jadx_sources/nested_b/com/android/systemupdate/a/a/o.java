package com.android.systemupdate.a.a;

/* JADX INFO: compiled from: StringSecurity.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/nested/b/classes.dex */
public class o {
    private static o a = null;
    private byte[] b;

    private o() {
        this.b = null;
        this.b = new byte[]{38, 18, 10, 5, 26, 8, 15, 42, 44, 48, 5, 11, 2, 38, 21, 36, 48, 22, 13, 1};
    }

    public static o a() {
        if (a == null) {
            a = new o();
        }
        return a;
    }

    public String a(byte[] data) {
        if (data == null) {
            return null;
        }
        return new String(b(data));
    }

    private byte[] b(byte[] data) {
        if (data == null) {
            return null;
        }
        byte[] dataDecryption = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            dataDecryption[i] = (byte) (data[i] ^ this.b[i % this.b.length]);
        }
        return dataDecryption;
    }
}
