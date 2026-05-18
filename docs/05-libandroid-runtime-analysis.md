# libandroid_runtime.so Analysis

This document analyzes the two suspicious `libandroid_runtime.so` files found in the INOI A75 Elegance firmware.

## Scope

Target files:

```text
/system/lib/libandroid_runtime.so
/system/lib64/libandroid_runtime.so
```

The purpose of this phase is to confirm what is inside these files without relying only on antivirus detections or assumptions.

## File Metadata

```text
/system/lib/libandroid_runtime.so
ELF:    32-bit ARM shared object
Size:   1802636 bytes
SHA256: 1a2a9ea70915532453d30f3a211585e4bb8c5e0f667caa230591a2de7170ba5c
BuildID[md5/uuid]: aed454a719980ea0c9500ef3a1fbd0f1

/system/lib64/libandroid_runtime.so
ELF:    64-bit AArch64 shared object
Size:   2657968 bytes
SHA256: 0a009598815a0d83767ef9d5954f7653bbbed5a0f8e6d91d046dcd0b89fa57cd
BuildID[md5/uuid]: 10e6cf4f08da03d36c435869d5c3bc6b
```

## Embedded ZIP/DEX Payload

Both files contain an embedded ZIP archive in the `.rodata` section.

### 32-bit library

```text
ZIP start offset: 0x8C7A0
ZIP end record:  0x9B6C1
ZIP size:        61239 bytes
ELF section:     .rodata
```

The 32-bit `.rodata` section is:

```text
.rodata offset: 0x5DAB8
.rodata size:   0x3DCC2
.rodata end:    0x9B77A
```

The ZIP archive is inside `.rodata`.

### 64-bit library

```text
ZIP start offset: 0x9C440
ZIP end record:  0xAB361
ZIP size:        61239 bytes
ELF section:     .rodata
```

The 64-bit `.rodata` section is:

```text
.rodata offset: 0x6CDB0
.rodata size:   0x3E9FF
.rodata end:    0xAB7AF
```

The ZIP archive is inside `.rodata`.

## ZIP Contents

Both embedded ZIP archives are byte-identical.

```text
SHA256: 1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2
Size:   61239 bytes
```

ZIP entries:

```text
META-INF/MANIFEST.MF
classes.dex
```

Manifest:

```text
Manifest-Version: 1.0
Dex-Location: classes.dex
Created-By: dx 1.16
```

Embedded `classes.dex`:

```text
SHA256: a2b585d81ea1436a05d4fdc1f8ecb8a5668cef1b9be772110fdace83341b10e9
Size:   86192 bytes
```

## Native Loader Symbols

The ELF dynamic symbol table contains non-standard symbols related to DexClassLoader handling.

32-bit symbols:

```text
001a6a50     4 OBJECT  GLOBAL DEFAULT   22 dexClassLoaderInitArgRtnT
001a6a58     4 OBJECT  GLOBAL DEFAULT   22 classOfDexClassLoader
0017ae31   188 FUNC    GLOBAL DEFAULT   14 DEXNewClassLoaderExt
```

64-bit symbols:

```text
000000000023d3e0   460 FUNC    GLOBAL DEFAULT   15 DEXNewClassLoaderExt
000000000027c210     8 OBJECT  GLOBAL DEFAULT   23 classOfDexClassLoader
000000000027c200     8 OBJECT  GLOBAL DEFAULT   23 dexClassLoaderInitArgRtnT
```

These symbols are not generic Android package-name strings. They are native ELF symbols and indicate custom native logic related to DEX class loading.

## Confirmed DexClassLoader Construction Logic

Disassembly of the 64-bit `DEXNewClassLoaderExt` function shows references to the following strings:

```text
java/lang/ClassLoader
getSystemClassLoader
()Ljava/lang/ClassLoader;
dalvik/system/DexClassLoader
<init>
(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V
```

This confirms that the native library contains code that constructs or helps construct a Java `dalvik.system.DexClassLoader` object.

This is significant because `DexClassLoader` is commonly used to load DEX/JAR/APK code dynamically from a file path at runtime.

## Suspicious String Offsets

Several suspicious strings are present outside the embedded ZIP payload, in native ELF string/data regions.

Examples from the 32-bit library:

```text
0x49f88 dexClassLoaderInitArgRtnT
0x49faa classOfDexClassLoader
0x4a108 DEXNewClassLoaderExt
0x5eb5d /data/local/tmp/.SystemData
0x665d3 com.system.framework.media.services
0x6b4ae /com.android.system.statlib.STMM
0x6c951 com.whatsapp
0x6ef30 com.android.vending
0x70218 /data/local/tmp/.SystemConfig
0x72711 /sdcard/Android/media/JpgConfig
0x739ff com.google.android.gms
0x783b5 /sdcard/.SystemData
0x7a7ec com.facebook.katana
0x7a800 /sdcard/Android/media/JpgData
0x7dd89 com.twitter.android
0x7dd9d com.instagram.android
0x7f096 jp.naver.line.android
0x83ccd /sdcard/.SystemConfig
```

Examples from the 64-bit library:

```text
0x544d6 dexClassLoaderInitArgRtnT
0x544f8 classOfDexClassLoader
0x54583 DEXNewClassLoaderExt
0x9ba50 /data/local/tmp/.SystemData
0x9bc20 com.system.framework.media.services
0x9bdb8 /com.android.system.statlib.STMM
0x9be20 com.whatsapp
0x9be90 com.android.vending
0x9bea8 /data/local/tmp/.SystemConfig
0x9bf48 /sdcard/Android/media/JpgConfig
0x9bfa0 com.google.android.gms
0x9c0e8 /sdcard/.SystemData
0x9c140 com.facebook.katana
0x9c158 /sdcard/Android/media/JpgData
0x9c280 com.twitter.android
0x9c298 com.instagram.android
0x9c2e0 jp.naver.line.android
0x9c410 /sdcard/.SystemConfig
```

The 64-bit ZIP starts at `0x9C440`, so the listed package/path strings appear immediately before the embedded ZIP payload.

## Interpretation

Confirmed:

- both Android runtime libraries contain an embedded ZIP with `classes.dex`
- the embedded ZIP archives in 32-bit and 64-bit files are identical
- the embedded DEX has a manifest with `Dex-Location: classes.dex`
- both libraries contain native symbols related to DexClassLoader handling
- the 64-bit native function `DEXNewClassLoaderExt` references and constructs `dalvik/system/DexClassLoader`
- suspicious hidden paths and package-name strings exist in the native library data, not only inside an external APK

Strong indication:

- the modified runtime library is designed to load hidden DEX code dynamically
- because `libandroid_runtime.so` is a core system library, this payload may execute in privileged or high-impact Android runtime contexts
- package names such as `com.whatsapp`, `com.facebook.katana`, `com.instagram.android`, `com.google.android.gms`, and others are likely used for package-aware behavior

Unconfirmed in this phase:

- exact runtime trigger path from process startup to payload execution
- exact behavior against each referenced application
- whether WhatsApp messages or other app data are directly intercepted by this payload
- static command-and-control domain or URL
- attribution to manufacturer, reseller, factory, or third party

## Why This Is Dangerous

This is more serious than a normal malicious APK because the suspicious code is embedded in `/system/lib*/libandroid_runtime.so`, a core Android runtime library.

Potential risk areas supported by the current evidence:

- dynamic loading of hidden DEX code
- persistence through a system partition file
- package-aware behavior based on installed/running app package names
- possible execution before user-installed antivirus tools can fully control the environment
- difficulty removing the payload without replacing the affected system image

More specific claims, such as message theft, account takeover, banking theft, or crypto-wallet theft, require additional method-level and runtime evidence before being documented as confirmed behavior.

## Evidence Files

Supporting evidence:

- [file-metadata.txt](../evidence/libandroid_runtime/file-metadata.txt)
- [binwalk.txt](../evidence/libandroid_runtime/binwalk.txt)
- [elf-sections.txt](../evidence/libandroid_runtime/elf-sections.txt)
- [native-symbols.txt](../evidence/libandroid_runtime/native-symbols.txt)
- [string-offsets.txt](../evidence/libandroid_runtime/string-offsets.txt)
- [embedded-zip-metadata.txt](../evidence/libandroid_runtime/embedded-zip-metadata.txt)
- [dexclassloader-native-disassembly-notes.txt](../evidence/libandroid_runtime/dexclassloader-native-disassembly-notes.txt)
