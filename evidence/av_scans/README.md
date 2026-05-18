# Antivirus Scan Screenshots

This directory stores antivirus scan screenshots supplied as evidence for the INOI A75 Elegance firmware analysis.

## Files

| Screenshot | SHA256 | Notes |
| --- | --- | --- |
| [Screenshot_20260518-104926.png](screenshots/Screenshot_20260518-104926.png) | `ab4cb7159bd5f9ad6c252f743ef4e67186fb36fe4eec06c092beb8d2ed9d7a7a` | Kaspersky report showing `HEUR:Backdoor.AndroidOS.Triada.ag` on `/system/lib/libandroid_runtime.so` and `/system/lib64/libandroid_runtime.so`. |
| [Screenshot_20260518-105148.png](screenshots/Screenshot_20260518-105148.png) | `8dbf465c2b81d48647ccea0fb20a3e40646374d472a5f26e3ad8ab18549e724b` | Dr.Web report showing `Android.Triada.6320` and `Android.Triada.6304`. |

See [Antivirus Detection Evidence](../../docs/07-antivirus-detection-evidence.md) for the full correlation between antivirus detections and firmware code evidence.
