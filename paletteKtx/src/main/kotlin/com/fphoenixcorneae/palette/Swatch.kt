package com.fphoenixcorneae.palette

/**
 * @desc：
 * @date：2021/11/02 15:33
 */
sealed class Swatch {
    /**
     * 鲜艳色
     */
    object Vibrant : Swatch()

    /**
     * 鲜艳色中的暗色
     */
    object VibrantDark : Swatch()

    /**
     * 鲜艳色中的亮色
     */
    object VibrantLight : Swatch()

    /**
     * 柔和色
     */
    object Muted : Swatch()

    /**
     * 柔和色中的暗色
     */
    object MutedDark : Swatch()

    /**
     * 柔和色中的亮色
     */
    object MutedLight : Swatch()

    /**
     * 调色板中占主导地位的样本
     */
    object Dominant : Swatch()

    /**
     * @desc：
     * @date：2021/11/02 15:29
     */
    sealed class Color {
        /**
         * 颜色的RGB值
         */
        object Argb : Color()

        /**
         * 颜色上面的标题文本颜色的RGB值
         */
        object TitleText : Color()

        /**
         * 颜色上面的正文文本颜色的RGB值
         */
        object BodyText : Color()
    }
}