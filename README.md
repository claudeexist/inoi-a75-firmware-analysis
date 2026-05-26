# INOI A75 Firmware Analysis

Security research documentation for the INOI A75 Elegance firmware.

This repository documents static analysis of suspected preinstalled malware indicators found in an INOI A75 Elegance firmware backup. The goal is to preserve reproducible evidence, explain findings accurately, and provide material that can be reviewed by other researchers or reported to the vendor/service center.

## Status

Preliminary analysis is complete. Deeper analysis is still ongoing.

Current findings should be treated as static-analysis evidence, not as a complete forensic verdict for every possible component in the device supply chain.

## Key Findings So Far

Static analysis found strong evidence of embedded payloads inside these system libraries:

- `/system/lib/libandroid_runtime.so`
- `/system/lib64/libandroid_runtime.so`

Both files contain an embedded ZIP/DEX payload. The payload includes dynamic loading behavior using `DexClassLoader`, references to suspicious class names such as `com.system.framework.media.services`, and strings/logic consistent with a staged Android system-level loader.

The same unique indicators were searched across the extracted firmware partitions. During this analysis, the matching unique indicators were found only in the two `libandroid_runtime.so` files and in `super.img`, which is the container image that includes the `/system` partition.

Static analysis has now decoded several embedded network endpoint strings. These are documented as static indicators; live operation and ownership are not asserted without dynamic verification. Ghidra native analysis also confirms that the package-name table is used by executable native loader logic before the Java payload is loaded; see [Ghidra Native Loader Analysis](docs/10-ghidra-native-loader-analysis.md).

A community-provided "purified" ROM was also compared against the original firmware artifacts. The known Triada-related indicators previously found in both `libandroid_runtime.so` files were not present in the tested purified ROM libraries, and the purified libraries have different hashes, sizes, and Build IDs. This is evidence that the known injected loader/payload was removed or replaced, but it is not a full guarantee that every other firmware component is clean. See [Purified ROM Comparison](docs/11-purified-rom-comparison.md).

## Important Scope Notes

This repository does not currently claim that every partition, boot component, or firmware blob has been fully reverse engineered.

This repository currently documents:

- firmware extraction and partition mounting
- static string and indicator searches
- embedded payload extraction
- DEX/smali inspection
- hashes and reproducible indicators

A complete analysis may still require:

- AV scanning with updated signature databases
- comparison against clean AOSP/vendor builds
- deeper reverse engineering of native code
- dynamic network monitoring in an isolated lab
- vendor confirmation

## Repository Structure

```text
.
├── README.md
├── DISCLAIMER.md
├── SECURITY.md
├── docs/
├── evidence/
└── scripts/
```

## Current Documents

- [Summary](docs/01-summary.md)
- [Reproducible Analysis Plan](docs/02-reproducible-analysis-plan.md)
- [Firmware Acquisition](docs/03-firmware-acquisition.md)
- [Deep Analysis Roadmap](docs/04-deep-analysis-roadmap.md)
- [libandroid_runtime.so Analysis](docs/05-libandroid-runtime-analysis.md)
- [JADX Malware Code Analysis](docs/06-jadx-malware-code-analysis.md)
- [Antivirus Detection Evidence](docs/07-antivirus-detection-evidence.md)
- [Malware Execution Flow](docs/08-malware-execution-flow.md)
- [Social App Abuse Investigation](docs/09-social-app-abuse-investigation.md)
- [Ghidra Native Loader Analysis](docs/10-ghidra-native-loader-analysis.md)
- [Purified ROM Comparison](docs/11-purified-rom-comparison.md)
- [Selected Decompiled JADX Source Evidence](evidence/jadx_sources/README.md)
- [Native Social Package Symbol Map](evidence/native_analysis/social-package-symbol-map.md)
- [Ghidra Native Loader Evidence](evidence/native_analysis/ghidra-native-loader-evidence.md)
- [Antivirus Scan Screenshots](evidence/av_scans/README.md)
- [Purified ROM Evidence](evidence/purified_rom/README.md)

## Firmware Handling

The firmware image may contain malicious code. Do not flash, execute, mount read-write, or analyze it on a personal or production machine.

If firmware files are shared later, they should be accompanied by cryptographic hashes and handled as potentially malicious samples.

## Disclaimer

This is independent security research. It is not an official statement from INOI, Google, Kaspersky, Dr.Web, or any other vendor.

See [DISCLAIMER.md](DISCLAIMER.md) for safety and legal notes.
