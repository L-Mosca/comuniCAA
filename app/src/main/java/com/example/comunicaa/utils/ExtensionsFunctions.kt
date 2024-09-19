package com.example.comunicaa.utils

import android.util.TypedValue
import android.view.ViewGroup

fun Int.toDpMetric(parent: ViewGroup): Int {
    val pixel = this

    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        pixel.toFloat(),
        parent.context.resources.displayMetrics
    ).toInt()
}