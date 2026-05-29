# Original INOI Runtime Patched Library Analysis

This document analyzes a community package named `lib-ori-inoi-patched.zip`.

The package is different from the previously analyzed purified ROM. The purified ROM replaced the affected runtime libraries with different libraries. This package keeps the original INOI runtime library identity and manually patches the suspected malicious code/data inside the original binaries.

## Package Metadata

ZIP SHA256:

```text
dbaa1b2c8190f6e608aa497d4ac147ffefdc4a6f2ff12fc7bfcf47b4082548d8  lib-ori-inoi-patched.zip
```

`module.prop`:

```text
id=inoi_runt_patched
name=INOI runtime patched
version=1.0
versionCode=1
author=CoreShiftD
```

ZIP contents:

```text
system/lib/libandroid_runtime.so
system/lib64/libandroid_runtime.so
```

## Hash And Identity Comparison

Original infected firmware:

```text
1a2a9ea70915532453d30f3a211585e4bb8c5e0f667caa230591a2de7170ba5c  /system/lib/libandroid_runtime.so
0a009598815a0d83767ef9d5954f7653bbbed5a0f8e6d91d046dcd0b89fa57cd  /system/lib64/libandroid_runtime.so
```

Original INOI patched variant:

```text
e2fc5c719fce77b9bbcdd68facfe1f2d5a9d4f1f706597181ed7644329867f20  system/lib/libandroid_runtime.so
3b391662bb15646186ab273c2ad1445607bfae9363a4c6cc68a3e1efe778d4b6  system/lib64/libandroid_runtime.so
```

File sizes are unchanged:

```text
32-bit original: 1802636 bytes
32-bit patched:  1802636 bytes

64-bit original: 2657968 bytes
64-bit patched:  2657968 bytes
```

Build IDs are also unchanged:

```text
32-bit: aed454a719980ea0c9500ef3a1fbd0f1
64-bit: 10e6cf4f08da03d36c435869d5c3bc6b
```

Interpretation: these are not rebuilt clean libraries. They are byte-patched versions of the original infected INOI runtime libraries.

## Changed Sections

32-bit library:

```text
.rodata changed bytes: 62358
.text   changed bytes:    75
```

64-bit library:

```text
.rodata changed bytes: 62358
.text   changed bytes:   135
```

The `.rodata` changes are all zeroing changes. The `.text` changes mostly replace function prologues or branch targets with immediate-return stubs.

See [patch summary evidence](../evidence/original_runtime_patched/patch-summary.txt).

## Embedded Payload Was Zeroed

The original infected libraries exported `_c_cvfua1`, an embedded object containing a ZIP/DEX payload.

Original payload region:

```text
32-bit original: nonzero=60882/61239
64-bit original: nonzero=60882/61239
SHA256: 1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2
```

Patched payload region:

```text
32-bit patched: nonzero=0/61239
64-bit patched: nonzero=0/61239
SHA256: e3ce6872d2e629b07374d6a070a10def4c3955efd4737e1485a4c5479e9c67a4
```

This is the strongest evidence that the embedded Java payload was destroyed. The `_c_cvfua1` symbol still exists, but the bytes it points to are all zero.

## Strings And Loader Data Were Zeroed

The patch zeroes known loader strings and target package strings, including:

```text
jm_model_config
com.system.framework.media.services
com.android.system.statlib.STMM
/com@system@framework@media@v2306.dex
/com@system@framework@media@v2306.jar
/.google_service_config
/.cofigs
/ext_oat
com.whatsapp
com.instagram.android
DexClassLoader string in .rodata
classes.dex embedded payload marker
```

Some suspicious names still remain in `.dynstr`/dynamic symbols:

```text
_c_cvfua1
DEXNewClassLoaderExt
load_jm_model
DexClassLoader
```

This explains why antivirus detection can remain even after the payload has been damaged.

## Native Function Stubbing

The patch also changes native code.

On ARM32 Thumb, several functions are replaced with:

```text
movs r0, #0
bx   lr
```

Example, `load_jm_model`:

```text
old:
0x179d74: push.w {r4, r5, r6, r7, r8, sb, lr}
0x179d78: subw   sp, sp, #0x79c

patched:
0x179d74: movs   r0, #0
0x179d76: bx     lr
```

On ARM64, several functions are replaced with:

```text
mov w0, #0
ret
```

Example, `load_jm_model`:

```text
old:
0x23bb88: sub sp, sp, #0x7a0
0x23bb8c: sub sp, sp, #0x40

patched:
0x23bb88: mov w0, #0
0x23bb8c: ret
```

Other patched functions include `get_pid_name`, `set_app_property`, `write_file`, and multiple internal helper functions such as `_f_cvfubb*` and `_f_cafde*`.

See [native stubs evidence](../evidence/original_runtime_patched/native-stubs.md).

## Is The Malware Dead Code?

Narrow answer: the known embedded payload is dead, and key loader paths are disabled.

More precise interpretation:

- The malicious loader code was not cleanly removed.
- The embedded DEX/ZIP payload was destroyed by zeroing `_c_cvfua1`.
- Many required strings, paths, class names, and package targets were zeroed.
- Several native helper functions were patched to return `0` immediately.
- The library still contains suspicious exported symbols and surrounding loader code.

So this variant is best described as "damaged/neutralized original malware code", not a clean library.

From a practical risk perspective, it is likely that this patched variant cannot execute the documented embedded Java payload because the payload is gone and `load_jm_model` is stubbed. However, it should not be treated as equivalent to a clean upstream `libandroid_runtime.so`, because altered malware code remains inside a core Android runtime library.

## Recommendation

For documentation and research, this sample is useful because it shows how the community patch disables the known loader.

For daily-use security, replacing the infected runtime libraries with known-clean, compatible libraries is still a cleaner approach than keeping a manually damaged malware-modified runtime library. The patched-original variant may reduce the observed Triada behavior, but it leaves a modified and suspicious runtime component in `/system`.

