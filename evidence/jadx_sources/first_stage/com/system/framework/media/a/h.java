package com.system.framework.media.a;

/* JADX INFO: compiled from: StringSecurity.java */
/* JADX INFO: loaded from: /root/firmware-lab/inoi-a75/extracted/embedded_payloads/64/classes.dex */
public class h {
    private static h a = null;
    private byte[] b;

    private h() {
        this.b = null;
        this.b = new byte[]{25, 3, 33, 48, 12, 35, 13, 36, 1, 16, 11, 26, 11, 3, 9, 20, 49, 30, 12, 36};
    }

    public static h a() {
        if (a == null) {
            a = new h();
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
