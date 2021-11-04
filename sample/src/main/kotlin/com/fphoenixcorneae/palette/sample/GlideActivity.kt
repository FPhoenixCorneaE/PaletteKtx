package com.fphoenixcorneae.palette.sample

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.fphoenixcorneae.palette.PaletteKtx
import com.fphoenixcorneae.palette.Swatch
import com.fphoenixcorneae.palette.sample.databinding.ActivityGlideBinding

/**
 * @desc：
 * @date：2021/11/03 14:01
 */
class GlideActivity : AppCompatActivity() {

    private var mViewBinding: ActivityGlideBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityGlideBinding.inflate(layoutInflater)
        setContentView(mViewBinding!!.root)

        mViewBinding?.apply {
            Glide.with(this@GlideActivity)
                .asBitmap()
                .load(URL)
                .into(object : ImageViewTarget<Bitmap>(ivImage) {
                    override fun setResource(resource: Bitmap?) {
                        resource?.let { bitmap ->
                            ivImage.setImageBitmap(bitmap)
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
                                .intoCallback { palette: Palette? -> } // optional
                                // optional: do stuff with the builder
                                .setPaletteBuilderInterceptor { builder ->
                                    builder.resizeBitmapArea(300 * 100)
                                }
                                .start(bitmap)

                            tvSecond.postDelayed({
                                PaletteKtx.getInstance()
                                    .data(URL)
                                    .use(Swatch.Dominant)
                                    .intoBackground(tvSecond, Swatch.Color.Argb)
                                    .intoTextColor(tvSecond, Swatch.Color.BodyText)
                                    .crossFade(true, 2000)
                                    .skipPaletteCache(true)
                                    .start(bitmap)
                            }, 1500)
                        }
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }

    companion object {
        const val URL = R.mipmap.img_1
    }
}