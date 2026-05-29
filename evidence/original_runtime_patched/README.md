# Original Runtime Patch Evidence

This directory contains evidence for `lib-ori-inoi-patched.zip`, a community patch that keeps the original INOI runtime libraries but damages/neutralizes the known Triada loader and embedded payload.

Files:

- [Patch summary](patch-summary.txt)
- [Native stubs](native-stubs.md)

Conclusion supported by this evidence:

- The patched libraries are still derived from the infected original INOI libraries.
- The embedded `_c_cvfua1` ZIP/DEX payload region is fully zeroed.
- Many malicious loader strings are zeroed.
- Several native loader/helper functions are patched to return immediately.
- Suspicious exported symbols remain, so antivirus detection can still be expected.

