# Social App Abuse Investigation

This document tracks the investigation into whether the malicious firmware code has behavior related to WhatsApp, Instagram, LINE, Facebook, SMS/MMS, or other user-facing apps.

The purpose is to separate confirmed firmware evidence from unconfirmed user-impact claims. Many affected users report WhatsApp/Instagram account abuse, but the report should only claim a specific abuse mechanism when the firmware code or runtime network evidence supports it.

## Current Status

Confirmed so far:

- both infected native libraries contain global symbols and strings for social, messaging, installer, browser, contacts, settings, and Android framework packages
- the same package-symbol table exists in both 32-bit and 64-bit `libandroid_runtime.so`
- the same native data area also contains hidden storage paths, `/data/data/`, reflection-related strings, and `DexClassLoader` strings
- the libraries import file/string APIs such as `open`, `read`, `write`, `stat`, `lstat`, `rename`, `unlink`, `opendir`, `readdir`, `strcat`, `strcmp`, `strncmp`, `strstr`, `dlopen`, and `dlsym`
- the firmware already contains a confirmed staged DEX loader and remote task/plugin capability documented in [Malware Execution Flow](08-malware-execution-flow.md)

Not confirmed yet:

- direct reading of WhatsApp databases such as `msgstore.db` or `wa.db`
- direct theft of session cookies, account tokens, or credentials from WhatsApp, Instagram, Telegram, or LINE
- direct code that sends WhatsApp/Instagram/Telegram messages from the firmware sample itself
- Telegram-specific package references in the currently extracted payload/native strings
- the exact runtime task delivered by the command server to affected devices

## Native Package Table

The package strings are not only loose text strings. They are referenced by named global symbols in the infected native libraries.

See the full evidence table: [social-package-symbol-map.md](../evidence/native_analysis/social-package-symbol-map.md).

Important package symbols include:

| Symbol | Value | Meaning |
| --- | --- | --- |
| `appkpackagea` | `com.whatsapp` | WhatsApp package |
| `appkpackageb` | `com.facebook.katana` | Facebook package |
| `appkpackagec` | `com.instagram.android` | Instagram package |
| `appkpackaged` | `jp.naver.line.android` | LINE package |
| `appkpackagee` | `com.twitter.android` | Twitter/X Android package |
| `mmsspackagea` | `com.android.mms` | AOSP MMS/SMS app |
| `mmsspackageb` | `com.android.messaging` | Android Messaging app |
| `mmsspackagec` | `com.google.android.apps.messaging` | Google Messages app |
| `contactspackage` | `com.android.contacts` | Contacts provider/app |
| `installerpackagea` | `com.google.android.packageinstaller` | Google package installer |
| `installerpackageb` | `com.android.packageinstaller` | Android package installer |

Relevant path/loader symbols include:

| Symbol | Value |
| --- | --- |
| `databasedir` | `/data/data/` |
| `sogloabledirlocaltmpa` | `/data/local/tmp/.SystemConfig` |
| `sogloabledirlocaltmpb` | `/data/local/tmp/.SystemData` |
| `sogloabledirsdcarda` | `/sdcard/.SystemConfig` |
| `sogloabledirsdcardb` | `/sdcard/.SystemData` |
| `sogloabledirmediaa` | `/sdcard/Android/media/JpgConfig` |
| `sogloabledirmediab` | `/sdcard/Android/media/JpgData` |
| `xxbclasspackage` | `com.system.framework.media.services` |
| `xxbfuncname` | `onCreate` |
| `classOfDexClassLoader` | `dalvik/system/DexClassLoader` |
| `loadClass` | `loadClass` |

## Interpretation

The native package table is strong evidence that the malware has package-aware behavior. The table explicitly names social apps, messaging apps, package installers, contacts, settings, browsers, Google Play services, Play Store, launcher, dialer, and Android framework components.

The presence of `/data/data/` next to these package names is concerning because `/data/data/<package>` is where Android app-private data normally lives. However, this alone does not prove that the current firmware sample reads WhatsApp or Instagram private databases. The next step is to identify the native function that combines `databasedir` with the `appkpackage*` symbols, then prove what it does with the resulting path.

The current safe claim is:

> The infected native runtime library contains a package-aware configuration table that explicitly includes WhatsApp, Instagram, Facebook, LINE, Twitter/X, SMS/MMS apps, contacts, browsers, package installers, and `/data/data/`. This supports suspicion of targeted app-aware behavior, but the exact action against each app still requires native xref/decompilation or runtime evidence.

## Abuse Hypotheses To Test

The user reports are consistent with several possible mechanisms. Each one requires a different proof:

| Hypothesis | Evidence needed |
| --- | --- |
| Reads local WhatsApp/Instagram app data | code that builds `/data/data/com.whatsapp/...` or `/data/data/com.instagram.android/...`, then opens/reads/copies database/preferences/token files |
| Uses notification/accessibility automation | references to `AccessibilityService`, `NotificationListenerService`, `AccessibilityNodeInfo`, `performAction`, `dispatchGesture`, notification reply APIs, or runtime permission abuse |
| Installs a secondary abuse module | remote task response that downloads an APK/JAR/DEX, plus decompiled payload showing social-app abuse |
| Sends accounts/session data to C2 | network request body or decrypted JSON containing account/session/cookie/token/database data |
| Uses contacts/SMS for propagation | code that reads contacts/SMS/MMS or sends messages, plus network/task evidence |

## What To Analyze Next

Priority native targets:

- `load_jm_model`
- `DEXNewClassLoaderExt`
- functions that reference `appkpackage*`, `mmsspackage*`, `databasedir`, and `xxbclasspackage`
- functions that call imported file APIs such as `open`, `read`, `write`, `opendir`, `readdir`, `stat`, `strcat`, and `strstr`
- `.init_array` and Android runtime registration functions that may trigger malware startup

Tooling plan:

- continue radare2 symbol/xref analysis
- use Ghidra headless if radare2 xrefs remain incomplete
- export decompiled native functions into evidence files only after confirming they are relevant
- keep claims conservative until a function or runtime capture proves the exact action

## Current Conclusion

The current firmware evidence strengthens the case that the malware is interested in social and messaging apps. It does not yet prove the exact reported WhatsApp/Instagram abuse mechanism.

Because the Java payload already supports remote encrypted tasking and dynamic plugin execution, it is also possible that the social-account abuse module is delivered at runtime and is not fully present in the static firmware sample. Dynamic network capture with dummy accounts remains necessary to prove the live abuse path.
