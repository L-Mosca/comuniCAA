package com.example.comunicaa.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

class PermissionCodes {
    companion object {
        const val REQUEST_PICK_IMAGE_PERMISSION_CODE = 100
        const val REQUEST_CAMERA_PERMISSION_CODE = 200
        const val REQUEST_RECORD_AUDIO_PERMISSION_CODE = 300
        const val REQUEST_PICK_AUDIO_PERMISSION_CODE = 400
    }
}

fun Activity.hasPermission(
    permissionRequestCode: Int,
    permissionsCheck: Array<String>
): Boolean {
    val permissions = ArrayList<String>()

    permissionsCheck.forEach {
        if (ActivityCompat.checkSelfPermission(this, it)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(it)
        }
    }

    if (permissions.isNotEmpty()) {
        val array = permissions.toTypedArray()
        ActivityCompat.requestPermissions(this, array, permissionRequestCode)
        return false
    }

    return true
}

fun Activity.checkPickImagePermission(onPermissionGranted: () -> Unit) {
    val permissions: Array<String> = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        else ->
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
    }

    if (hasPermission(
            PermissionCodes.REQUEST_PICK_IMAGE_PERMISSION_CODE,
            permissions
        )
    ) onPermissionGranted()
}

fun Activity.checkCameraPermission(onPermissionGranted: () -> Unit) {
    val permissions: Array<String> = arrayOf(Manifest.permission.CAMERA)

    if (hasPermission(
            PermissionCodes.REQUEST_CAMERA_PERMISSION_CODE,
            permissions
        )
    ) onPermissionGranted()
}

fun Activity.checkRecordAudioPermission(onPermissionGranted: () -> Unit) {
    val permissions: Array<String> =
        arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    if (hasPermission(
            PermissionCodes.REQUEST_RECORD_AUDIO_PERMISSION_CODE,
            permissions
        )
    ) onPermissionGranted()
    else requestPermissions(permissions, PermissionCodes.REQUEST_RECORD_AUDIO_PERMISSION_CODE)
}

fun Activity.checkPickAudioPermission(onPermissionGranted: () -> Unit) {
    val permissions: Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    if (hasPermission(
            PermissionCodes.REQUEST_PICK_AUDIO_PERMISSION_CODE,
            permissions
        )
    ) onPermissionGranted()
}