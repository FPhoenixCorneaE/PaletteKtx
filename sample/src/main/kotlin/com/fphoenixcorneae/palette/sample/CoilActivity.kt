package com.fphoenixcorneae.palette.sample

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.imageLoader
import coil.request.ImageRequest
import com.fphoenixcorneae.palette.PaletteKtx
import com.fphoenixcorneae.palette.Swatch
import com.fphoenixcorneae.palette.sample.databinding.ActivityCoilBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @desc：
 * @date：2021/11/03 14:01
 */
class CoilActivity : AppCompatActivity() {

    private var mViewBinding: ActivityCoilBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityCoilBinding.inflate(layoutInflater)
        setContentView(mViewBinding!!.root)

        mViewBinding?.apply {
            CoroutineScope(Dispatchers.IO).launch {
                val request = ImageRequest.Builder(this@CoilActivity)
                    .data(URL)
                    .build()
                val drawable = imageLoader.execute(request).drawable
                drawable?.let {
                    withContext(Dispatchers.Main) {
                        ivImage.setImageBitmap(it.toBitmap())
                        PaletteKtx.getInstance()
                            .data(URL)
                            .use(Swatch.Vibrant)
                            .intoBackground(tvVibrant, Swatch.Color.Argb)
                            .intoTextColor(tvVibrant, Swatch.Color.BodyText)
                            .crossFade(true)
                            .use(Swatch.VibrantDark)
                            .intoBackground(tvVibrantDark, Swatch.Color.Argb)
                            .intoTextColor(tvVibrantDark, Swatch.Color.BodyText)
                            .crossFade(false)
                            .use(Swatch.VibrantLight)
                            .intoBackground(tvVibrantLight, Swatch.Color.Argb)
                            .intoTextColor(tvVibrantLight, Swatch.Color.BodyText)
                            .crossFade(true, 1000)
                            .use(Swatch.Dominant)
                            .intoBackground(tvDominant, Swatch.Color.Argb)
                            .intoTextColor(tvDominant, Swatch.Color.BodyText)
                            .crossFade(true, 2000)
                            .use(Swatch.Muted)
                            .intoBackground(tvMuted, Swatch.Color.Argb)
                            .intoTextColor(tvMuted, Swatch.Color.BodyText)
                            .use(Swatch.MutedDark)
                            .intoBackground(tvMutedDark, Swatch.Color.Argb)
                            .intoTextColor(tvMutedDark, Swatch.Color.BodyText)
                            .crossFade(true, 2000)
                            .use(Swatch.MutedLight)
                            .intoBackground(tvMutedLight, Swatch.Color.Argb)
                            .intoTextColor(tvMutedLight, Swatch.Color.BodyText) // optional
                            .intoCallback { palette: Palette? ->
                                palette?.dominantSwatch?.run {
                                    tvSecond.background = ColorDrawable(rgb)
                                    tvSecond.setTextColor(bodyTextColor)
                                }
                            }
                            // optional: do stuff with the builder
                            .setPaletteBuilderInterceptor { builder ->
                                builder.resizeBitmapArea(300 * 100)
                            }
                            .start(it.toBitmap().copy(Bitmap.Config.ARGB_8888, true))
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }

    companion object {
        const val URL = R.mipmap.img
    }
}