# Update-Binary Review

File in ZIP:

```text
META-INF/com/google/android/update-binary
```

File type:

```text
/sbin/sh script, ASCII text executable
```

Static review summary:

- Extracts `META-INF/lptools`, `META-INF/zstd`, and optionally `META-INF/simg2img` into `/tmp`.
- Uses `getprop ro.boot.slot_suffix` to determine the active slot.
- Defines `PARTS="system product system_ext vendor vendor_dlkm odm_dlkm"`.
- Resolves raw image sizes from `.img` or `.img.zst` archive entries.
- Unmounts old mount points.
- Calls `lptools clear-cow`.
- Unmaps and removes old dynamic partitions for the current slot.
- Recreates the dynamic partitions using calculated sizes.
- Maps partitions under `/dev/block/mapper/`.
- Streams local ZIP image contents into the mapped block devices.

Security interpretation:

- No remote URL, downloader, credential handling, or network fetch stage was observed in the script.
- The script is still destructive by design: it removes and recreates dynamic partitions before flashing images.
- This review does not validate the safety of the images being flashed.

