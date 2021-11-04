# PaletteKtx

[![](https://jitpack.io/v/FPhoenixCorneaE/PaletteKtx.svg)](https://jitpack.io/#FPhoenixCorneaE/PaletteKtx)

<div align="center">
    <img src="https://github.com/FPhoenixCorneaE/PaletteKtx/blob/main/screenshot/preview_palette_1.webp" width="320" align="top"/>
	<img src="https://github.com/FPhoenixCorneaE/PaletteKtx/blob/main/screenshot/preview_palette_2.webp" width="320" align="top" style="margin-left:15px"/>
</div>

### How to include it in your project:
**Step 1.** Add the JitPack repository to your build file

```kotlin
allprojects {
	repositories {
		maven("https://jitpack.io")
	}
}
```

**Step 2.** Add the dependency

```kotlin
dependencies {
	implementation("com.github.FPhoenixCorneaE:PaletteKtx:$latest")
}
```

### Glide Sample

```kotlin
Glide.with(this)
    .asBitmap()
    .load(imgData)
    .into(object : ImageViewTarget<Bitmap>(ivImage) {
        override fun setResource(resource: Bitmap?) {
            resource?.let { bitmap ->
                PaletteKtx.getInstance()
                    // palette cache key
                    .data(imgData)
                    // necessary: use Swatch color for View
                    .use(Swatch.Dominant)
                    // set View background
                    .intoBackground(tvSecond, Swatch.Color.Argb)
                    // set TextView textColor
                    .intoTextColor(tvSecond, Swatch.Color.BodyText)
                    // crossFade default is false, crossFadeDuration default is 300
                    .crossFade(true, 2000)
                    // default is false
                    .skipPaletteCache(true)
                    // palette loaded callback
                    .intoCallBack { palette: Palette? -> }
                    // palette builder interceptor
                    .setPaletteBuilderInterceptor { builder ->
                        builder.resizeBitmapArea(300 * 100)
                    }
                    .start(bitmap)
            }
        }
    })
```

### Coil Sample

```kotlin
CoroutineScope(Dispatchers.IO).launch {
    val request = ImageRequest.Builder(context)
        .data(imgData)
        .build()
    val drawable = imageLoader.execute(request).drawable
    drawable?.let {
        withContext(Dispatchers.Main) {
            ivImage.setImageBitmap(it.toBitmap())
            PaletteKtx.getInstance()
                .data(imgData)
                .use(Swatch.Vibrant)
                .intoBackground(tvVibrant, Swatch.Color.Argb)
                .intoTextColor(tvVibrant, Swatch.Color.BodyText)
                .crossFade(true)
                .use(Swatch.VibrantDark)
                .intoBackground(tvVibrantDark, Swatch.Color.Argb)
                .intoTextColor(tvVibrantDark, Swatch.Color.BodyText)
                .crossFade(false)
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
```