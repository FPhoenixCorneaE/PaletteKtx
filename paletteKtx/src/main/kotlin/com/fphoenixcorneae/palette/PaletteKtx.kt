package com.fphoenixcorneae.palette

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.collection.LruCache
import androidx.core.util.Pair
import androidx.palette.graphics.Palette
import java.util.*

/**
 * @desc：位图调色板
 * @date：2021/11/01 15:20
 */
open class PaletteKtx {

    /** 缓存 Key */
    private var mData: Any? = null
    private val mTargets = LinkedList<PaletteTarget>()
    private val mOnPaletteLoadedList: ArrayList<OnPaletteLoaded> by lazy { arrayListOf() }
    private var mPaletteBuilderInterceptor: PaletteBuilderInterceptor? = null
    private var mSkipCache = false

    open fun data(date: Any?) = apply {
        mData = date
    }

    open fun use(swatch: Swatch) = apply {
        mTargets.add(PaletteTarget(swatch))
    }

    open fun intoBackground(view: View?, swatchColor: Swatch.Color) = apply {
        kotlin.runCatching {
            assertTargetsIsNotEmpty()
        }.onSuccess {
            mTargets.last.targetsBackground?.add(Pair(view, swatchColor))
        }.onFailure {
            it.printStackTrace()
        }
    }

    open fun intoTextColor(textView: TextView?, swatchColor: Swatch.Color) = apply {
        kotlin.runCatching {
            assertTargetsIsNotEmpty()
        }.onSuccess {
            mTargets.last.targetsText?.add(Pair(textView, swatchColor))
        }.onFailure {
            it.printStackTrace()
        }
    }

    open fun crossFade(crossFade: Boolean) = apply {
        kotlin.runCatching {
            assertTargetsIsNotEmpty()
        }.onSuccess {
            mTargets.last.targetCrossFade = crossFade
        }.onFailure {
            it.printStackTrace()
        }
    }

    open fun crossFade(crossFade: Boolean, crossFadeDuration: Int) = apply {
        kotlin.runCatching {
            assertTargetsIsNotEmpty()
        }.onSuccess {
            mTargets.last.targetCrossFade = crossFade
            mTargets.last.targetCrossFadeDuration = crossFadeDuration
        }.onFailure {
            it.printStackTrace()
        }
    }

    open fun intoCallback(onPaletteLoaded: OnPaletteLoaded?) = apply {
        if (onPaletteLoaded != null) {
            mOnPaletteLoadedList.add(onPaletteLoaded)
        }
    }

    open fun skipPaletteCache(skipCache: Boolean) = apply {
        this.mSkipCache = skipCache
    }

    open fun setPaletteBuilderInterceptor(interceptor: PaletteBuilderInterceptor?) = apply {
        this.mPaletteBuilderInterceptor = interceptor
    }

    private fun assertTargetsIsNotEmpty() {
        if (mTargets.isEmpty()) {
            throw UnsupportedOperationException("You must specify a palette with use(swatch: Swatch).")
        }
    }

    /**
     * Apply the Palette Swatch to our current targets
     * @param palette the palette to apply
     * @param cacheHit true if the palette was retrieved from the cache, else false
     */
    private fun apply(palette: Palette?, cacheHit: Boolean) {
        mOnPaletteLoadedList.forEach { c ->
            c.invoke(palette)
        }
        palette?.let {
            mTargets.forEach { target ->
                val swatch = when (target.swatch) {
                    Swatch.VibrantDark -> palette.darkVibrantSwatch
                    Swatch.VibrantLight -> palette.lightVibrantSwatch
                    Swatch.Muted -> palette.mutedSwatch
                    Swatch.MutedDark -> palette.darkMutedSwatch
                    Swatch.MutedLight -> palette.lightMutedSwatch
                    Swatch.Dominant -> palette.dominantSwatch
                    else -> palette.vibrantSwatch
                } ?: return
                target.targetsBackground?.forEach { t ->
                    val color = getColor(swatch, t.second)
                    // Only crossFade if we're not coming from a cache hit.
                    if (!cacheHit && target.targetCrossFade) {
                        crossFadeTargetBackground(target, t, color)
                    } else {
                        t.first.setBackgroundColor(color)
                    }
                }
                target.targetsText?.forEach { t ->
                    val color = getColor(swatch, t.second)
                    t.first.setTextColor(color)
                }
                target.clear()
            }
            clear()
        }
    }

    private fun getColor(swatch: Palette.Swatch?, color: Swatch.Color): Int {
        if (swatch != null) {
            return when (color) {
                Swatch.Color.Argb -> swatch.rgb
                Swatch.Color.TitleText -> swatch.titleTextColor
                Swatch.Color.BodyText -> swatch.bodyTextColor
            }
        } else {
            Log.e(TAG, "error while generating Palette, null palette returned")
        }
        return 0
    }

    private fun crossFadeTargetBackground(target: PaletteTarget, t: Pair<View, Swatch.Color>, newColor: Int) {
        val oldColor = t.first.background
        val drawables = arrayOfNulls<Drawable>(2)
        drawables[0] = oldColor ?: ColorDrawable(t.first.solidColor)
        drawables[1] = ColorDrawable(newColor)
        val transitionDrawable = TransitionDrawable(drawables)
        t.first.background = transitionDrawable
        transitionDrawable.startTransition(target.targetCrossFadeDuration)
    }

    private fun clear() {
        mData = null
        mPaletteBuilderInterceptor = null
        mSkipCache = false
        mTargets.clear()
        mOnPaletteLoadedList.clear()
    }

    /**
     * 开始生成调色板
     */
    fun start(bitmap: Bitmap) {
        if (!mSkipCache && mData != null) {
            // 从缓存中取调色板
            CACHE[mData!!]?.also { palette ->
                apply(palette, true)
                return
            }
        }
        // 生成 Palette
        Palette.from(bitmap)
            .run {
                // 如果设置了拦截器
                mPaletteBuilderInterceptor?.invoke(this) ?: this
            }
            .generate { palette ->
                // 异步抽取图片色调
                if (!mSkipCache && mData != null && palette != null) {
                    // 存入缓存中
                    CACHE.put(mData!!, palette)
                }
                apply(palette, false)
            }
    }

    companion object {

        private const val TAG = "BitmapPalette"

        /** 缓存 */
        private val CACHE = LruCache<Any, Palette?>(40)

        fun getInstance(): PaletteKtx {
            return PaletteKtx()
        }

        /**
         * 清除
         */
        fun clear() {
            CACHE.evictAll()
        }
    }
}