# Reproducible Analysis Plan

This document tracks the intended reproducible workflow for analyzing the firmware.

## 1. Preserve Original Firmware

Record original metadata:

```text
filename
file size
SHA256
source URL or acquisition notes
archive password status, if any
extraction timestamp
analysis host OS/tool versions
```

Do not modify the original archive.

## 2. Extract Firmware Safely

Use an isolated analysis VPS or VM.

Recommended safety controls:

```text
read-only mounts
no personal accounts
no production credentials
snapshots/backups
network monitoring
separate working directory
```

## 3. Extract super.img

Identify whether `super.img` is sparse or raw. Convert/extract logical partitions, then mount them read-only.

Expected partitions from the current backup:

```text
system_a
system_b
product_a
system_ext_a
vendor_a
vendor_dlkm_a
odm_dlkm_a
```

## 4. Search for Known Indicators

Search for unique indicators, not only generic strings.

Useful indicators from the current analysis:

```text
DEXNewClassLoaderExt
com.system.framework.media.services
com.android.system.statlib.STMM
/data/local/tmp/.SystemData
/sdcard/Android/media/JpgData
Dex-Location: classes.dex
```

Generic strings such as `DexClassLoader`, `http`, or `com.whatsapp` should not be used alone as proof because they may appear in legitimate Android components.

## 5. Extract Embedded Payloads

Inspect suspicious native libraries with tools such as:

```text
file
sha256sum
strings
binwalk
zipinfo
dexdump
baksmali
```

When ZIP/DEX payloads are found, extract them, hash them, and disassemble DEX files for review.

## 6. Document Evidence

Each finding should include:

```text
file path
hash
offset, if relevant
tool used
command used
short interpretation
limitations
```

## 7. Next Analysis Tasks

Planned deeper analysis:

- reverse engineer native modifications in `libandroid_runtime.so`
- map payload execution flow from native loader to embedded DEX
- decode remaining strings/configuration if present
- identify whether C2 endpoints are static, encoded, or dynamically generated
- run updated AV signature scans when databases are available
- compare against a known-clean Android build or vendor baseline
- perform dynamic network monitoring only in an isolated lab
