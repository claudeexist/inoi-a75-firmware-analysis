# Deep Analysis Roadmap

This document defines the planned deeper analysis for the suspicious `libandroid_runtime.so` files.

The goal is to explain exactly what is present, what behavior is technically supported by the code, and what remains unproven.

## Target Files

Primary files:

```text
/system/lib/libandroid_runtime.so
/system/lib64/libandroid_runtime.so
```

Known SHA256:

```text
1a2a9ea70915532453d30f3a211585e4bb8c5e0f667caa230591a2de7170ba5c  /system/lib/libandroid_runtime.so
0a009598815a0d83767ef9d5954f7653bbbed5a0f8e6d91d046dcd0b89fa57cd  /system/lib64/libandroid_runtime.so
```

## Evidence Levels

The documentation should use these labels consistently:

```text
Confirmed:
Evidence directly observed in files, hashes, offsets, extracted payloads, or disassembled code.

Strong indication:
Behavior that is strongly suggested by code structure, APIs, strings, and payload design, but still needs deeper reverse engineering or runtime confirmation.

Unconfirmed:
Possible impact or attribution that should not be stated as fact yet.
```

## Questions To Answer

### 1. What was modified in libandroid_runtime.so?

Tasks:

- compare both libraries against a clean Android/vendor baseline if available
- locate the embedded ZIP/DEX offsets
- identify native code that loads or triggers the embedded payload
- map execution flow from Android runtime initialization to payload execution

Expected output:

- [libandroid_runtime.so Analysis](05-libandroid-runtime-analysis.md)
- planned evidence: `evidence/binwalk-libandroid-runtime.txt`
- planned evidence: `evidence/libandroid-runtime-strings.txt`
- planned evidence: `evidence/libandroid-runtime-offsets.txt`

### 2. What is inside the first embedded DEX payload?

Tasks:

- extract the embedded ZIP from both 32-bit and 64-bit libraries
- verify whether both embedded ZIP payloads are identical
- disassemble `classes.dex`
- document classes, methods, strings, and loader behavior

Already observed indicators:

```text
com.system.framework.media.services
DexClassLoader
DEXNewClassLoaderExt
/data/local/tmp/.SystemData
/data/local/tmp/.SystemConfig
/sdcard/.SystemData
/sdcard/.SystemConfig
/sdcard/Android/media/JpgData
/sdcard/Android/media/JpgConfig
```

Expected output:

- superseded by [JADX Malware Code Analysis](06-jadx-malware-code-analysis.md)
- planned evidence: `evidence/embedded-payload-hashes.txt`
- planned evidence: `evidence/first-stage-classes.txt`

### 3. What is inside the nested DEX payloads?

Two nested DEX payloads were decoded from Base64 strings in the first-stage payload.

Known SHA256:

```text
e0145e4f0979289ca1d7cb7b67d6f49278d1a651f5b04a628aeb46375d35c1ff  nested/a/classes.dex
361acfe97edd118d99a87c96259a1911b8750aee540c2df709a988479b7c4791  nested/b/classes.dex
```

Preliminary observations:

- one nested payload contains package-installation related code
- one nested payload contains HTTP/HTTPS related code
- one nested payload references telephony/SMS-related APIs
- one nested payload uses class/package naming such as `com.android.systemupdate`

These observations need deeper method-level documentation before making final behavior claims.

Expected output:

```text
docs/07-nested-payload-analysis.md
evidence/nested-payload-classes.txt
evidence/nested-payload-api-usage.txt
```

### 4. Which applications are referenced or targeted?

Strings in the suspicious files reference multiple package names, including high-value apps.

This must be documented carefully:

- a package-name string alone is not proof that the app is fully compromised
- code paths must be mapped to show how the package name is used
- runtime behavior should be confirmed before claiming interception, account theft, or message abuse

Planned classification:

```text
Referenced package:
The package name appears in strings or code.

Conditionally handled package:
The code checks the package name and executes package-specific logic.

Confirmed impacted package:
Runtime behavior or clear code flow proves malicious action against that app.
```

Expected output:

```text
docs/08-referenced-and-targeted-apps.md
evidence/referenced-packages.txt
evidence/package-specific-code-paths.txt
```

### 5. What are the realistic dangers?

Potential risk areas to investigate:

- code injection into Android runtime or app processes
- dynamic loading of hidden DEX payloads
- silent installation or removal of APKs
- network communication over HTTP/HTTPS
- device and SIM/telephony data collection
- package-specific behavior for selected apps
- persistence through modified system libraries

Important: each danger must be tied to specific evidence.

Do not claim the malware can steal banking credentials, crypto wallets, or WhatsApp messages unless the code or runtime behavior directly supports that claim.

Expected output:

```text
docs/09-impact-and-risk-assessment.md
```

### 6. Is there a command-and-control endpoint?

Current status:

```text
No clear static C2 domain or URL has been confirmed yet.
```

Tasks:

- search decoded strings
- inspect URL construction logic
- inspect config retrieval logic
- monitor network traffic only in an isolated lab

Expected output:

```text
docs/10-network-analysis.md
evidence/network-string-search.txt
evidence/lab-network-observations.txt
```

## Documentation Rule

Every technical claim should include at least one of:

- file path
- hash
- command output
- byte offset
- class/method name
- string indicator
- screenshot/log from controlled analysis

If evidence is incomplete, write it as incomplete.
