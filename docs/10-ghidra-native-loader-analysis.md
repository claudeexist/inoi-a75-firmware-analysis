# Ghidra Native Loader Analysis

This document summarizes the Ghidra headless analysis of the infected native runtime libraries:

- `/system/lib/libandroid_runtime.so`
- `/system/lib64/libandroid_runtime.so`

The purpose is to prove how the native code uses the embedded payload and package-name table. This is stronger than string evidence alone because it links the strings to executable control flow.

## Tool Result

Ghidra 11.4.2 headless successfully analyzed both libraries on the analysis VPS. Ghidra 12.1 was tested first but its bundled native decompiler/demangler required newer `glibc`/`libstdc++` than the VPS image provides, so the usable output below comes from Ghidra 11.4.2.

Selected raw evidence is documented in [ghidra-native-loader-evidence.md](../evidence/native_analysis/ghidra-native-loader-evidence.md).

## Confirmed Native Loader Flow

Ghidra confirms the native code has two important loader paths.

First, `load_jm_model` reads the current process name from `/proc/<pid>/cmdline`, creates a hidden process-specific directory under `/data/data/<process>/.cofigs/`, writes a hidden file named `.google_service_config`, and sets a Java system property named `jm_model_config`. The configured class string is `/com.android.system.statlib.STMM`.

Second, `___andver_log_println` reads the current process name, compares it against a package table, creates `/data/data/<process>/ext_oat`, writes an embedded JAR as `/com@system@framework@media@v2306.jar`, loads it with `DexClassLoader`, instantiates `com.system.framework.media.services`, and calls `onCreate(I)V`.

The package table is therefore not passive metadata. It is used by native code before the Java payload is loaded.

## Social App Relevance

The package table checked by the native loader includes:

- `com.whatsapp`
- `com.instagram.android`
- `com.facebook.katana`
- `jp.naver.line.android`
- `com.twitter.android`
- SMS/MMS apps
- Contacts
- Browsers
- Package installers
- Play Store / Google Play services
- Dialer / launcher / system UI / settings

Ghidra shows these package strings are compared against the current process name. When the process matches this table, the native code passes a flag into the Java payload entrypoint `onCreate(I)V`.

This supports the claim that the malware is app-aware and explicitly includes WhatsApp and Instagram in its native target/process logic.

## Embedded JAR Evidence

The native symbol `_c_cvfua1` is an embedded JAR payload:

| Architecture | Symbol VA | Size | SHA256 |
| --- | ---: | ---: | --- |
| 32-bit | `0x8c7a0` | `0xef37` | `1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2` |
| 64-bit | `0x9c440` | `0xef37` | `1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2` |

The JAR contains:

- `META-INF/MANIFEST.MF`
- `classes.dex`

Extracted `classes.dex` SHA256:

```text
a2b585d81ea1436a05d4fdc1f8ecb8a5668cef1b9be772110fdace83341b10e9
```

The decompiled Java entrypoint is [services.java](../evidence/jadx_sources/first_stage/com/system/framework/media/services.java).

## What This Proves

Confirmed:

- both native libraries contain the same embedded JAR/DEX payload
- native code writes that JAR into app-private storage under `/data/data/<process>/ext_oat`
- native code loads the JAR with `DexClassLoader`
- native code instantiates `com.system.framework.media.services`
- native code calls `onCreate(I)V`
- the current process name is compared against a table that includes WhatsApp, Instagram, Facebook, LINE, SMS/MMS, contacts, browser, installer, and other system app package names

Not confirmed by this native evidence alone:

- direct theft of WhatsApp/Instagram credentials or cookies
- direct reading of WhatsApp databases such as `msgstore.db` or `wa.db`
- direct message sending from the native library itself
- the exact live task sent by a command server to an affected device

The current static evidence shows a system-level staged loader and package-aware execution logic. The already decompiled nested Java payloads show encrypted network tasking and dynamic plugin execution, so social account abuse may be delivered as a runtime task that is not fully present in the static firmware sample.
