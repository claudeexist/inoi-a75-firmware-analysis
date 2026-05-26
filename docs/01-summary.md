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

## Community Purified ROM Comparison

A community-provided "Stock ROM INOI A75 purified" image was downloaded from the user-provided MediaFire source and unpacked on the analysis VPS. The archive was verified with `unzip -tq`, decompressed, and mounted read-only.

The purified ROM contains replacement or modified versions of both target runtime libraries:

```text
98c418cf1b1c77c359e5ceea38c18b75053b83fbc1d90be6afab752f5784407e  /system/lib/libandroid_runtime.so
e4b98718cceb73b505489ec95a51158228a95b6f7f3a1f9c42578d28f768181f  /system/lib64/libandroid_runtime.so
```

The known Triada-related strings and embedded ZIP/DEX markers found in the original infected libraries were absent from both purified libraries. A strong IOC search across the mounted purified partitions also returned zero matches.

This supports a narrow conclusion: the known injected loader/payload previously documented in `libandroid_runtime.so` was removed from, or replaced in, the tested purified ROM artifact. It does not prove that the complete ROM is safe to flash, that the boot chain is trustworthy, or that no unrelated backdoor exists.

See [Purified ROM Comparison](11-purified-rom-comparison.md) and [Purified ROM Evidence](../evidence/purified_rom/README.md).

## Limitations

This is not yet a complete end-to-end forensic report.

Known limitations:

- ClamAV signature scanning was not completed because the fresh signature database was unavailable at the time of analysis.
- Native-code reverse engineering is not complete.
- Dynamic execution/network behavior has not yet been confirmed in an isolated lab.
- No static C2 domain or URL has been confirmed yet.
- The purified ROM comparison is currently static analysis only and does not validate bootloader, verified boot, rollback protection, update scripts, or runtime behavior on a real device.
