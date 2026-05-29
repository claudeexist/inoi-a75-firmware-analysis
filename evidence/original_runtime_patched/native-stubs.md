# Native Stub Evidence

This file records representative native code patches found in `lib-ori-inoi-patched.zip`.

## ARM32 Thumb

### `load_jm_model`

File offset:

```text
0x178d74
```

Virtual address:

```text
0x179d74
```

Original:

```text
0x179d74: push.w {r4, r5, r6, r7, r8, sb, lr}
0x179d78: subw   sp, sp, #0x79c
0x179d7c: ldr    r5, [pc, #0x114]
```

Patched:

```text
0x179d74: movs   r0, #0
0x179d76: bx     lr
0x179d78: subw   sp, sp, #0x79c
```

Interpretation: the function returns `0` immediately and does not execute its original body.

### `set_app_property`

File offset:

```text
0x178d0c
```

Virtual address:

```text
0x179d0c
```

Original:

```text
0x179d0c: push.w {r0, r1, r4, r5, r6, r7, r8, lr}
0x179d10: mov    r6, r1
0x179d12: ldr    r3, [r0]
```

Patched:

```text
0x179d0c: movs   r0, #0
0x179d0e: bx     lr
0x179d10: mov    r6, r1
```

## ARM64

### `load_jm_model`

Virtual address and file offset:

```text
0x23bb88
```

Original:

```text
0x23bb88: sub sp, sp, #0x7a0
0x23bb8c: sub sp, sp, #0x40
0x23bb90: stp x21, x22, [sp, #0x10]
```

Patched:

```text
0x23bb88: mov w0, #0
0x23bb8c: ret
0x23bb90: stp x21, x22, [sp, #0x10]
```

Interpretation: the function returns `0` immediately and does not execute its original body.

### `get_pid_name`

Virtual address and file offset:

```text
0x23b998
```

Original:

```text
0x23b998: sub sp, sp, #0x30
0x23b99c: stp x21, x22, [sp, #0x10]
0x23b9a0: stp x19, x20, [sp]
```

Patched:

```text
0x23b998: mov w0, #0
0x23b99c: ret
0x23b9a0: stp x19, x20, [sp]
```

## Patched Function Map

The changed `.text` offsets map to these dynamic symbols or nearby helper functions:

```text
ARM32:
write_file
set_app_property
load_jm_model
___log_println
___andver_log_println
_f_cvfubb2
_f_cvfubb4
_f_cvfubb5
_f_cvfubb6
_f_cvfubb9
_f_cafde3

ARM64:
get_pid_name
set_app_property
load_jm_model
___log_println
___log_init
___andver_log_println
___andver_log_init
_f_cvfubb2
_f_cvfubb3
_f_cvfubb5
_f_cvfubb6
_f_cvfubb7
_f_cvfubb9
_f_cafde1
_f_cafde2
_f_cafde3
_f_cafde4
```

