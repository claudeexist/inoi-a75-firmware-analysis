# Decompiled JADX Source Evidence

This folder contains selected Java-like source files produced by `jadx` from the embedded DEX payloads. These files are included so readers can inspect the code evidence directly in GitHub.

The full decompiled output remains in the analysis workspace on `node2`. This repository includes the files most directly referenced by the malware-code analysis.

## First-Stage Loader

- [services.java](first_stage/com/system/framework/media/services.java)
- [c.java](first_stage/com/system/framework/media/a/c.java)
- [d.java](first_stage/com/system/framework/media/a/d.java)
- [f.java](first_stage/com/system/framework/media/a/f.java)
- [h.java](first_stage/com/system/framework/media/a/h.java)
- [i.java](first_stage/com/system/framework/media/a/i.java)

## Nested Payload A: Package Install/Uninstall

- [AppLog.java](nested_a/com/android/packageinstaller/extplog/AppLog.java)
- [a.java](nested_a/com/android/packageinstaller/extplog/a.java)
- [b.java](nested_a/com/android/packageinstaller/extplog/b.java)
- [c.java](nested_a/com/android/packageinstaller/extplog/c.java)
- [d.java](nested_a/com/android/packageinstaller/extplog/d.java)

## Nested Payload B: Network/Task/Plugin Loader

- [services.java](nested_b/com/android/systemupdate/services.java)
- [a.java](nested_b/com/android/systemupdate/a/a.java)
- [b.java](nested_b/com/android/systemupdate/a/b.java)
- [c.java](nested_b/com/android/systemupdate/a/c.java)
- [d.java](nested_b/com/android/systemupdate/a/d.java)
- [e.java](nested_b/com/android/systemupdate/a/e.java)
- [f.java](nested_b/com/android/systemupdate/a/f.java)
- [a/a/d.java](nested_b/com/android/systemupdate/a/a/d.java)
- [a/a/g.java](nested_b/com/android/systemupdate/a/a/g.java)
- [a/a/h.java](nested_b/com/android/systemupdate/a/a/h.java)
- [a/a/l.java](nested_b/com/android/systemupdate/a/a/l.java)
- [a/a/o.java](nested_b/com/android/systemupdate/a/a/o.java)
- [a/a/p.java](nested_b/com/android/systemupdate/a/a/p.java)
