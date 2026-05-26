# Purified ROM Comparison

This document compares the original INOI A75 Elegance firmware analyzed in this repository with a community-provided ROM described by its uploader as "Stock ROM INOI A75 purified".

The goal is narrow: determine whether the known Triada-related artifacts previously found in `libandroid_runtime.so` are still present in the purified ROM, and document what can and cannot be concluded from static analysis.

## Source

The purified ROM was downloaded from a user-provided MediaFire link:

```text
https://www.mediafire.com/file/braa8ikaxj50mkq/STOCK_INOIA75_EROFS.zip/file
```

Archive hash:

```text
e8e2bd81003b7223681e1e1222620df29185b0c4a1011f2301fbba8b7343af0a  STOCK_INOIA75_EROFS.zip
```

Archive size:

```text
3079297584 bytes
```

The ZIP archive passed `unzip -tq` integrity testing during this analysis.

## Extracted Images

The purified ROM archive contains EROFS dynamic partition images compressed with Zstandard:

```text
odm_dlkm.img.zst
product.img.zst
system.img.zst
system_ext.img.zst
vendor.img.zst
vendor_dlkm.img.zst
```

The images were decompressed with `zstd` and mounted read-only with kernel EROFS support.

Mounted file counts:

```text
system       3114
product       501
system_ext    646
vendor       2371
odm_dlkm        4
vendor_dlkm   189
```

## Flash Update Script

The archive includes `META-INF/com/google/android/update-binary`.

Observed properties:

- The file is an ASCII `/sbin/sh` script, not an opaque native binary.
- It extracts `lptools`, `zstd`, and optionally `simg2img` into `/tmp`.
- It reads the current slot from `ro.boot.slot_suffix`.
- It removes and recreates these dynamic partitions for the current slot: `system`, `product`, `system_ext`, `vendor`, `vendor_dlkm`, and `odm_dlkm`.
- It flashes the corresponding images from the ZIP to `/dev/block/mapper/<partition><slot>`.

No network download, credential handling, or extra payload fetch was observed in this script during static review. The script is still destructive by design because it removes and recreates dynamic partitions before flashing images.

See [update-binary review evidence](../evidence/purified_rom/update-binary-review.md).

## Target Runtime Libraries

Original infected firmware:

```text
1a2a9ea70915532453d30f3a211585e4bb8c5e0f667caa230591a2de7170ba5c  /system/lib/libandroid_runtime.so
0a009598815a0d83767ef9d5954f7653bbbed5a0f8e6d91d046dcd0b89fa57cd  /system/lib64/libandroid_runtime.so
```

Purified ROM:

```text
98c418cf1b1c77c359e5ceea38c18b75053b83fbc1d90be6afab752f5784407e  /system/lib/libandroid_runtime.so
e4b98718cceb73b505489ec95a51158228a95b6f7f3a1f9c42578d28f768181f  /system/lib64/libandroid_runtime.so
```

File sizes:

```text
original /system/lib/libandroid_runtime.so     1802636 bytes
purified /system/lib/libandroid_runtime.so     1700580 bytes

original /system/lib64/libandroid_runtime.so   2657968 bytes
purified /system/lib64/libandroid_runtime.so   2546024 bytes
```

Build IDs:

```text
original 32-bit:  aed454a719980ea0c9500ef3a1fbd0f1
original 64-bit:  10e6cf4f08da03d36c435869d5c3bc6b
purified 32-bit:  5ea79754dd87bc2fd7938cc95467a7f6
purified 64-bit:  62677d0957c4be9de9dc78a39815f6fe
```

These differences show that the purified ROM does not contain byte-identical copies of the original infected runtime libraries.

## Removed Known Indicators

The following known indicators were present in the original libraries and absent from both purified libraries:

```text
PK\x03\x04
classes.dex
_c_cvfua1
DEXNewClassLoaderExt
load_jm_model
jm_model_config
com.system.framework.media.services
com.android.system.statlib.STMM
com@system@framework@media@v2306
/.google_service_config
/.cofigs
/ext_oat
com.whatsapp
com.instagram.android
```

The embedded ZIP/DEX markers are especially important because the original analysis showed that the infected libraries carried an embedded Java payload and native loader logic. Their absence in the purified libraries indicates that the known injected payload was removed or the affected libraries were replaced with cleaner builds.

Detailed pattern offsets are preserved in [IOC pattern comparison evidence](../evidence/purified_rom/ioc-pattern-comparison.txt).

## Whole Mounted Partition IOC Search

A strong IOC search was run across the mounted purified partitions using indicators from the previously analyzed loader and payload.

Search result:

```text
0 purified-strong-ioc-search.txt
```

This means the searched strong indicators were not found in mounted purified `system`, `product`, `system_ext`, `vendor`, `odm_dlkm`, or `vendor_dlkm` partitions.

A broader search returned one match inside `GmsCore.apk` for a generic Google Play Services string containing `os.config.ppgl.*`. This is currently treated as an unrelated false positive, not as evidence of Triada.

See [ROM comparison summary evidence](../evidence/purified_rom/rom-comparison-summary.txt).

## Added APKs In The Purified ROM

The purified ROM contains five package-like files that were not present in the original firmware comparison set:

```text
system/app/Blibli-11.6.0_66615.apk
system/app/Langit_musik5.16.1.1-GP.apk
system/app/inoi_apps_store.apk
system/app/paw-rumble-release-production/paw-rumble-release-production.apk
vendor/app/SensorHub/SensorHub.apk
```

This means the purified ROM is not only a minimal malware-removal build. It also changes the preinstalled app set.

No known Triada indicators from the previous `libandroid_runtime.so` analysis were found in these five APKs during a direct binary string scan. However, the additional app set still changes the device risk profile.

Most notably, `inoi_apps_store.apk` requests package installation and broad package visibility permissions:

```text
android.permission.REQUEST_INSTALL_PACKAGES
android.permission.INSTALL_PACKAGES
android.permission.MANAGE_EXTERNAL_STORAGE
android.permission.QUERY_ALL_PACKAGES
android.permission.REQUEST_DELETE_PACKAGES
```

JADX decompilation of `inoi_apps_store.apk` showed:

- API base URL: `http://203.175.11.220/api/v1/`
- image/download base URL: `http://203.175.11.220/`
- a hard-coded JWT-like API key in source code, redacted in this repository
- Retrofit API calls for app listing, app detail, ranking, banners, categories, and download URLs
- code paths that download APK files and invoke Android package installation flows
- a declared Device Admin receiver with password-related policies in `res/xml/device_owner_receiver.xml`

This does not prove that `inoi_apps_store.apk` is malware. It does prove that the purified ROM adds a privileged app-store style component with clear security and privacy implications. Treat it as a separate item requiring deeper review before flashing the ROM on a personal device.

See [additional APK review evidence](../evidence/purified_rom/additional-apk-review.md).

## What The Community Developer Likely Changed

Based on the artifact-level comparison, the developer most likely replaced or rebuilt both `libandroid_runtime.so` files so that the injected native loader and embedded DEX payload were removed.

This analysis did not find evidence that the developer merely blocked domains, disabled the malware with a config flag, or only removed antivirus-detection strings while leaving the known loader intact. The strongest reason is that the original embedded ZIP/DEX markers, loader function strings, payload class names, app package indicators, and runtime file path strings are all absent from the purified libraries.

This is still an inference from static artifacts. The developer's exact method cannot be proven without their build notes, source patch, or reproducible build process.

## Security Interpretation

What this comparison supports:

- The known Triada-related payload previously documented in the original `libandroid_runtime.so` files is absent from the tested purified ROM.
- Both target libraries were changed, not merely renamed.
- The strong known IOC set did not reappear elsewhere in the mounted purified partitions.
- The ZIP flash script appears to flash local partition images and did not show a network-fetch stage in static review.

What this comparison does not prove:

- The ROM is safe to flash on a real device.
- The boot chain, `vbmeta`, boot image, recovery image, or firmware update script is safe.
- There is no unrelated malware, backdoor, adware, or privacy-invasive component.
- Runtime network behavior is clean.
- The ROM is signed, verified, or compatible with every INOI A75 variant.
- The added APKs are safe, privacy-preserving, or appropriate for a security-focused cleaned ROM.

## Next Analysis Steps

Recommended next steps before treating the ROM as safer for daily use:

1. Compare the purified `libandroid_runtime.so` files against a known clean Android 14 baseline or another clean firmware build for the same SoC/vendor tree.
2. Run AV scans on all extracted APK, JAR, APEX, ELF, and firmware blobs from the purified ROM.
3. Inspect `META-INF/com/google/android/update-binary` and any flashing scripts for destructive or unexpected behavior.
4. Compare app lists and privileged permissions between the infected and purified firmware.
5. Boot only on a disposable test device or emulator-like lab setup if possible, then monitor DNS, HTTP(S) SNI, destination IPs, and suspicious background activity.
6. Verify whether the device can retain verified boot or whether flashing requires disabling verification.
7. Decompile and review the added APKs, especially `inoi_apps_store.apk`, before calling the purified ROM safe.
