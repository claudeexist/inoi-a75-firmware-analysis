# Additional APK Review

The purified ROM adds five package-like files that were not present in the original firmware comparison set.

## Added Files

```text
system/app/Blibli-11.6.0_66615.apk
system/app/Langit_musik5.16.1.1-GP.apk
system/app/inoi_apps_store.apk
system/app/paw-rumble-release-production/paw-rumble-release-production.apk
vendor/app/SensorHub/SensorHub.apk
```

Package-like file counts:

```text
original firmware: 318
purified ROM:      323
```

## Hashes And Package Names

```text
14806b760da3c5c1afd45a1064c106fab1fcdf4f6ad33d44a70011a01f650f0a  Blibli-11.6.0_66615.apk
package: blibli.mobile.commerce
label: Blibli

4bf2252616224345fe01598f96ce1a2954cdc05f4c05bc555e0fecd5804c83f3  Langit_musik5.16.1.1-GP.apk
package: com.melon.langitmusik
label: Langit Musik

918d2fbe1aa08b1267acf1f7a8836d9db80e79cda3bee3158f839c4f39285bc9  inoi_apps_store.apk
package: com.inoi.inoiappsstore
label: Inoi Apps Store

89cda2e086a3a5d6a29947c43f9b30d738be19500914d5a72f1540b8dfa4d2f2  paw-rumble-release-production.apk
package: com.grinsmile.pawrumble
label: Paw Rumble!

4fd1b1e0aff60f20859c23620c872abc47b51f5cd02f988f9eed1f8b8bcc0e58  SensorHub.apk
package: com.mediatek.sensorhub.ui
label: Sensor Test
```

## Signer Certificate Digests

```text
Blibli
DN: CN=Blibli Mobile, OU=IT Development, O=PT. Global Digital Niaga, L=Jakarta, ST=Jakarta, C=ID
SHA-256: a73a18edae5637485b9b4de01054d97cc9718f48621c094814459d2de716fb9b

Langit Musik
DN: CN=melon
SHA-256: d9466066ed59db3a4770963b9f687c24d8fae3d3e0fb879f8f72a282823c14af

Inoi Apps Store
DN: C=62, ST=West Java, L=Depok, O=PRASIMAX, OU=INOI, CN=INOI
SHA-256: 55f788fb77325c8ce8c0dede2e47ef8dcf943558a10dcd6fffd741cb15849471

Paw Rumble!
DN: CN=Android, OU=Android, O=Google Inc., L=Mountain View, ST=California, C=US
SHA-256: 2e43e7d667d418fef9df45238cccdd5cd4152b57c43df60dc798deae4b71c3cf

Sensor Test
DN: EMAILADDRESS=shi@chino-e.com, CN=Android, OU=xunrui, O=chino, L=dongguan, ST=guangdong, C=CN
SHA-256: 54747044617dcb12e1167f80e8676f00441d4d89cec70eeeb0cc8f97bc88adbc
```

## Known Triada IOC Scan

A direct binary string scan of these five added APKs did not find the known Triada indicators used in the previous `libandroid_runtime.so` investigation:

```text
_c_cvfua1
DEXNewClassLoaderExt
load_jm_model
jm_model_config
com.system.framework.media.services
com.android.system.statlib.STMM
com@system@framework@media@v2306
he2o9t
0gubvi
s6jvxl
DexClassLoader
com.whatsapp
com.instagram.android
org.telegram.messenger
```

Absence of these strings does not prove that the APKs are safe. It only means the known Triada indicators from the earlier native-loader analysis were not found in this quick binary string pass.

## Inoi Apps Store Review

`inoi_apps_store.apk` is the most security-relevant added APK found so far.

Requested permissions include:

```text
android.permission.INTERNET
android.permission.REQUEST_DELETE_PACKAGES
android.permission.REQUEST_INSTALL_PACKAGES
android.permission.INSTALL_PACKAGES
android.permission.WRITE_EXTERNAL_STORAGE
android.permission.READ_EXTERNAL_STORAGE
android.permission.MANAGE_EXTERNAL_STORAGE
android.permission.QUERY_ALL_PACKAGES
```

JADX decompilation partially completed with errors, but produced readable source for the app's own package under:

```text
sources/com/inoi/inoiappsstore/
```

Observed in `sources/com/inoi/inoiappsstore/Url/Url.java`:

```text
BASE_URL       = http://203.175.11.220/api/v1/
BASE_URL_IMAGE = http://203.175.11.220/
TOKEN          = hard-coded JWT-like API key, redacted here
```

Observed in `sources/com/inoi/inoiappsstore/Services/ApiService.java`:

```text
X-API-KEY: hard-coded JWT-like API key, redacted here
GET application/list-active
GET application/get-ranking
GET application/{applicationId}
GET application/download/{applicationId}
GET banner/list-active
GET categories/list-active
```

Observed in `sources/com/inoi/inoiappsstore/Fragments/AppsDetailFragment.java`:

- downloads APK files through Android `DownloadManager`
- checks `canRequestPackageInstalls()`
- opens Android unknown-app-source settings if permission is missing
- invokes `android.intent.action.INSTALL_PACKAGE`
- includes a `PackageInstaller` code path when device admin status is active
- invokes `android.intent.action.UNINSTALL_PACKAGE`
- reads installed packages to compare app state

Observed in `resources/AndroidManifest.xml` and `resources/res/xml/device_owner_receiver.xml`:

- declares `com.inoi.inoiappsstore.Services.DeviceOwnerReceiver`
- uses `android.permission.BIND_DEVICE_ADMIN`
- declares device-admin policies including password-related policies

Interpretation:

- This APK is not proven to be Triada.
- It is an app-store style system preload with broad install, uninstall, package-query, and storage permissions.
- It uses plaintext HTTP to `203.175.11.220`, not HTTPS, for API and image/download base URLs.
- The hard-coded API key is a security weakness and was redacted from this repository.
- Because it is preinstalled in `/system/app`, it should be treated as a separate risk item before the purified ROM is considered safe for personal use.

