package com.fphoenixcorneae.palette

import android.view.View
import android.widget.TextView
import androidx.core.util.Pair
import java.util.*

/**
 * @desc：调色板目标
 * @date：2021/11/01 16:47
 */
class PaletteTarget(
    val swatch: Swatch = Swatch.Vibrant
) {

    var targetsBackground: ArrayList<Pair<View, Swatch.Color>>? = arrayListOf()
    var targetsText: ArrayList<Pair<TextView, Swatch.Color>>? = arrayListOf()
    var targetCrossFade = false
    var targetCrossFadeDuration = DEFAULT_CROSS_FADE_DURATION

    fun clear() {
        targetsBackground?.clear()
        targetsText?.clear()

        targetsBackground = null
        targetsText = null

        targetCrossFade = false
        targetCrossFadeDuration = DEFAULT_CROSS_FADE_DURATION
    }

    companion object {
        const val DEFAULT_CROSS_FADE_DURATION = 300
    }
}