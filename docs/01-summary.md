# Summary

This document summarizes the current static analysis findings for the INOI A75 Elegance firmware backup.

## Confirmed Suspicious Files

The following files were identified as the primary suspicious artifacts:

```text
/system/lib/libandroid_runtime.so
/system/lib64/libandroid_runtime.so
```

SHA256:

```text
1a2a9ea70915532453d30f3a211585e4bb8c5e0f667caa230591a2de7170ba5c  /system/lib/libandroid_runtime.so
0a009598815a0d83767ef9d5954f7653bbbed5a0f8e6d91d046dcd0b89fa57cd  /system/lib64/libandroid_runtime.so
```

## Embedded Payload Evidence

Both libraries contained an embedded ZIP/DEX payload.

Embedded ZIP SHA256:

```text
1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2  libandroid_runtime_32_embedded.zip
1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2  libandroid_runtime_64_embedded.zip
```

The embedded payloads were identical between the 32-bit and 64-bit libraries.

Nested DEX payload SHA256:

```text
e0145e4f0979289ca1d7cb7b67d6f49278d1a651f5b04a628aeb46375d35c1ff  nested/a/classes.dex
361acfe97edd118d99a87c96259a1911b8750aee540c2df709a988479b7c4791  nested/b/classes.dex
```

## Notable Indicators

Observed strings and behaviors include:

```text
com.system.framework.media.services
com.android.system.statlib.STMM
DEXNewClassLoaderExt
dexClassLoaderInitArgRtnT
classOfDexClassLoader
/data/local/tmp/.SystemData
/data/local/tmp/.SystemConfig
/sdcard/.SystemData
/sdcard/.SystemConfig
/sdcard/Android/media/JpgData
/sdcard/Android/media/JpgConfig
DexClassLoader
```

The payload also referenced common application package names, including WhatsApp and other high-value apps. These references require careful interpretation and should be tied to code behavior before making user-impact claims.

## Current Scope Result

A search for the unique indicators above across mounted firmware partitions found matches in:

```text
/system/lib/libandroid_runtime.so
/system/lib64/libandroid_runtime.so
super.img
```

`super.img` matched because it contains the `/system` partition.

The same unique indicators were not found in the scanned APK/JAR/APEX files, `boot.img`, or `vendor_boot.img` during this pass.

## Limitations

This is not yet a complete end-to-end forensic report.

Known limitations:

- ClamAV signature scanning was not completed because the fresh signature database was unavailable at the time of analysis.
- Native-code reverse engineering is not complete.
- Dynamic execution/network behavior has not yet been confirmed in an isolated lab.
- No static C2 domain or URL has been confirmed yet.
