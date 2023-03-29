package com.tools.timezone.util

import kotlin.math.abs
import kotlin.math.roundToInt

object NumberUtil {
    fun parseAlphaFloat2Int(floatValue: Float): Int {
        return floatValue
            .coerceAtLeast(0.0f)
            .coerceAtMost(1.0f)
            .roundToInt()
    }

    fun coerceAtMost(value: Float, limit: Float): Float {
        return value
            .coerceAtLeast(-abs(limit))
            .coerceAtMost(abs(limit))
    }
}