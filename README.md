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

Static analysis has now decoded several embedded network endpoint strings. These are documented as static indicators; live operation and ownership are not asserted without dynamic verification.

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

## Firmware Handling

The firmware image may contain malicious code. Do not flash, execute, mount read-write, or analyze it on a personal or production machine.

If firmware files are shared later, they should be accompanied by cryptographic hashes and handled as potentially malicious samples.

## Disclaimer

This is independent security research. It is not an official statement from INOI, Google, Kaspersky, Dr.Web, or any other vendor.

See [DISCLAIMER.md](DISCLAIMER.md) for safety and legal notes.
