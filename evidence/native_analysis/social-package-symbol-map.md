# Native Social Package Symbol Map

This evidence file records native symbol names and their resolved string values from the infected `libandroid_runtime.so` files.

The symbols were collected with `readelf -Ws`, and string values were resolved from the native pointer table in `.data`.

## 32-bit Library

Firmware file:

- `/system/lib/libandroid_runtime.so`
- SHA256: `1a2a9ea70915532453d30f3a211585e4bb8c5e0f667caa230591a2de7170ba5c`

| Symbol | Virtual address | Value |
| --- | ---: | --- |
| `jm_model_class` | `0x1a6940` | `/com.android.system.statlib.STMM` |
| `jm_model_name` | `0x1a6944` | `/.google_service_config` |
| `jm_model_dir` | `0x1a6948` | `/.cofigs` |
| `preInitialized` | `0x1a6950` | `<pre-initialized>` |
| `currentApplicationSign` | `0x1a695c` | `()Landroid/app/Application;` |
| `currentApplication` | `0x1a6960` | `currentApplication` |
| `xxbfuncArgRtnT` | `0x1a698c` | `(I)V` |
| `xxbfuncname` | `0x1a6990` | `onCreate` |
| `xxbclasspackage` | `0x1a6994` | `com.system.framework.media.services` |
| `appkpackagel` | `0x1a69a0` | `com.google.android.dialer` |
| `appkpackagek` | `0x1a69a4` | `com.android.dialer` |
| `appkpackagej` | `0x1a69a8` | `com.android.phone` |
| `appkpackagei` | `0x1a69ac` | `com.android.launcher3` |
| `appkpackageh` | `0x1a69b0` | `com.android.quicksearchbox` |
| `appkpackageg` | `0x1a69b4` | `com.google.android.gms` |
| `appkpackagef` | `0x1a69b8` | `com.android.vending` |
| `appkpackagee` | `0x1a69bc` | `com.twitter.android` |
| `appkpackaged` | `0x1a69c0` | `jp.naver.line.android` |
| `appkpackagec` | `0x1a69c4` | `com.instagram.android` |
| `appkpackageb` | `0x1a69c8` | `com.facebook.katana` |
| `appkpackagea` | `0x1a69cc` | `com.whatsapp` |
| `contactspackage` | `0x1a69d0` | `com.android.contacts` |
| `browserpackageb` | `0x1a69d4` | `com.android.chrome` |
| `installerpackageb` | `0x1a69d8` | `com.android.packageinstaller` |
| `installerpackagea` | `0x1a69dc` | `com.google.android.packageinstaller` |
| `systemuipackage` | `0x1a69e0` | `com.android.systemui` |
| `browserpackagea` | `0x1a69e4` | `com.android.browser` |
| `mmsspackagec` | `0x1a69e8` | `com.google.android.apps.messaging` |
| `mmsspackageb` | `0x1a69ec` | `com.android.messaging` |
| `mmsspackagea` | `0x1a69f0` | `com.android.mms` |
| `settingspackage` | `0x1a69f4` | `com.android.settings` |
| `androidframework` | `0x1a69f8` | `android` |
| `sogloabledirmediab` | `0x1a6a0c` | `/sdcard/Android/media/JpgData` |
| `sogloabledirmediaa` | `0x1a6a10` | `/sdcard/Android/media/JpgConfig` |
| `sogloabledirlocaltmpb` | `0x1a6a14` | `/data/local/tmp/.SystemData` |
| `sogloabledirlocaltmpa` | `0x1a6a18` | `/data/local/tmp/.SystemConfig` |
| `sogloabledirsdcardb` | `0x1a6a1c` | `/sdcard/.SystemData` |
| `sogloabledirsdcarda` | `0x1a6a20` | `/sdcard/.SystemConfig` |
| `databasedir` | `0x1a6a24` | `/data/data/` |
| `loadClass` | `0x1a6a4c` | `loadClass` |
| `dexClassLoaderInitArgRtnT` | `0x1a6a50` | `(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V` |
| `classOfDexClassLoader` | `0x1a6a58` | `dalvik/system/DexClassLoader` |

## 64-bit Library

Firmware file:

- `/system/lib64/libandroid_runtime.so`
- SHA256: `0a009598815a0d83767ef9d5954f7653bbbed5a0f8e6d91d046dcd0b89fa57cd`

| Symbol | Virtual address | Value |
| --- | ---: | --- |
| `jm_model_class` | `0x27bfe0` | `/com.android.system.statlib.STMM` |
| `jm_model_name` | `0x27bfe8` | `/.google_service_config` |
| `jm_model_dir` | `0x27bff0` | `/.cofigs` |
| `preInitialized` | `0x27c000` | `<pre-initialized>` |
| `currentApplicationSign` | `0x27c018` | `()Landroid/app/Application;` |
| `currentApplication` | `0x27c020` | `currentApplication` |
| `xxbfuncArgRtnT` | `0x27c078` | `(I)V` |
| `xxbfuncname` | `0x27c080` | `onCreate` |
| `xxbclasspackage` | `0x27c088` | `com.system.framework.media.services` |
| `appkpackagel` | `0x27c0a0` | `com.google.android.dialer` |
| `appkpackagek` | `0x27c0a8` | `com.android.dialer` |
| `appkpackagej` | `0x27c0b0` | `com.android.phone` |
| `appkpackagei` | `0x27c0b8` | `com.android.launcher3` |
| `appkpackageh` | `0x27c0c0` | `com.android.quicksearchbox` |
| `appkpackageg` | `0x27c0c8` | `com.google.android.gms` |
| `appkpackagef` | `0x27c0d0` | `com.android.vending` |
| `appkpackagee` | `0x27c0d8` | `com.twitter.android` |
| `appkpackaged` | `0x27c0e0` | `jp.naver.line.android` |
| `appkpackagec` | `0x27c0e8` | `com.instagram.android` |
| `appkpackageb` | `0x27c0f0` | `com.facebook.katana` |
| `appkpackagea` | `0x27c0f8` | `com.whatsapp` |
| `contactspackage` | `0x27c100` | `com.android.contacts` |
| `browserpackageb` | `0x27c108` | `com.android.chrome` |
| `installerpackageb` | `0x27c110` | `com.android.packageinstaller` |
| `installerpackagea` | `0x27c118` | `com.google.android.packageinstaller` |
| `systemuipackage` | `0x27c120` | `com.android.systemui` |
| `browserpackagea` | `0x27c128` | `com.android.browser` |
| `mmsspackagec` | `0x27c130` | `com.google.android.apps.messaging` |
| `mmsspackageb` | `0x27c138` | `com.android.messaging` |
| `mmsspackagea` | `0x27c140` | `com.android.mms` |
| `settingspackage` | `0x27c148` | `com.android.settings` |
| `androidframework` | `0x27c150` | `android` |
| `sogloabledirmediab` | `0x27c178` | `/sdcard/Android/media/JpgData` |
| `sogloabledirmediaa` | `0x27c180` | `/sdcard/Android/media/JpgConfig` |
| `sogloabledirlocaltmpb` | `0x27c188` | `/data/local/tmp/.SystemData` |
| `sogloabledirlocaltmpa` | `0x27c190` | `/data/local/tmp/.SystemConfig` |
| `sogloabledirsdcardb` | `0x27c198` | `/sdcard/.SystemData` |
| `sogloabledirsdcarda` | `0x27c1a0` | `/sdcard/.SystemConfig` |
| `databasedir` | `0x27c1a8` | `/data/data/` |
| `loadClass` | `0x27c1f8` | `loadClass` |
| `dexClassLoaderInitArgRtnT` | `0x27c200` | `(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V` |
| `classOfDexClassLoader` | `0x27c210` | `dalvik/system/DexClassLoader` |

## Notes

- `org.telegram.messenger` was not found in the current native string search.
- The package symbols are named in the native dynamic symbol table, not inferred from random string offsets.
- The same data structure appears in both 32-bit and 64-bit libraries, which supports that this is intentional embedded logic.
- This table does not by itself prove direct credential theft. It proves package-aware native configuration that must be tied to function behavior through xref/decompilation or runtime evidence.
