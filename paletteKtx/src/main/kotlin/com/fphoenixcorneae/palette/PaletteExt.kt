package com.fphoenixcorneae.palette

import androidx.palette.graphics.Palette

/**
 * 调色板加载完毕
 */
typealias OnPaletteLoaded = (palette: Palette?) -> Unit

/**
 * 调色板生成器拦截器
 */
typealias PaletteBuilderInterceptor = (builder: Palette.Builder) -> Palette.Builder