# Firmware Acquisition

This document records the currently analyzed firmware sample and how it was acquired for this research.

## Source

The firmware backup was provided by the device owner/researcher through Google Drive:

```text
https://drive.google.com/file/d/1pIQIczD0nFDaPdPe0OGgH9iLNRN94AQu/view?usp=drivesdk
```

The file was downloaded to an isolated VPS analysis environment before extraction.

## Original Archive Metadata

```text
Filename: Firmware INOI_A750_NEEA_U_V6_20250828.zip
Size:     3448360091 bytes
SHA256:   357434ecde981beb55dbb36126c1cb66c733b555faad690edf68ee5d0a56e4a2
```

## Extracted Firmware Notes

The archive contained Android firmware images, including:

```text
super.img
boot.img
vendor_boot.img
boot-debug.img
vendor_boot-debug.img
vbmeta.img
vbmeta_system.img
vbmeta_vendor.img
userdata.img
preloader_k6789v1_64.bin
md1img.img
build.prop
```

The `super.img` file was extracted into logical partitions for static analysis.

Observed logical partitions:

```text
system_a
system_b
product_a
system_ext_a
vendor_a
vendor_dlkm_a
odm_dlkm_a
```

## Chain of Custody Notes

This analysis currently relies on the firmware archive listed above.

To keep the evidence reproducible:

- preserve the original ZIP hash before extraction
- do not modify original firmware files
- perform extraction in a separate working directory
- record command outputs and tool versions where possible
- mount extracted partitions read-only

## Safety Warning

This firmware is treated as potentially malicious. Do not flash it to a personal device or execute extracted payloads outside an isolated malware analysis environment.
