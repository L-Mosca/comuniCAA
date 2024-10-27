package com.example.comunicaa.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.OutputStream

class ImageUtil {
    companion object {
        const val IMAGE_WIDTH = 500
        const val IMAGE_HEIGHT = 500
        const val IMAGE_PREFIX = "comunicaa_img_"
        const val IMAGE_EXTENSION = "png"
        const val IMAGE_QUALITY = 100
    }
}

fun resizeImage(contentResolver: ContentResolver, imageUri: Uri, actionName: String): Uri? {
    val resizedBitmap = resizeBitmap(contentResolver, imageUri)

    val contentValues = buildImagePath()

    val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
        outputStream?.use {
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, ImageUtil.IMAGE_QUALITY, it)
        }
    }

    resizedBitmap.recycle()

    return uri
}

fun buildImageName(): String {
    val timestamp = System.currentTimeMillis()
    return "${ImageUtil.IMAGE_PREFIX}$timestamp.${ImageUtil.IMAGE_EXTENSION}"
}

fun resizeBitmap(contentResolver: ContentResolver, imageUri: Uri): Bitmap {
    val inputStream = contentResolver.openInputStream(imageUri)
    val originalBitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()

    return Bitmap.createScaledBitmap(
        originalBitmap,
        ImageUtil.IMAGE_WIDTH,
        ImageUtil.IMAGE_HEIGHT,
        true
    )
}

fun buildImagePath(): ContentValues {
    return ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, buildImageName())
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.WIDTH, ImageUtil.IMAGE_WIDTH)
        put(MediaStore.Images.Media.HEIGHT, ImageUtil.IMAGE_HEIGHT)
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ComunicaaApp")
    }
}