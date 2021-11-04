object Deps {
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 16
    const val targetSdkVersion = 30
    const val versionCode = 101
    const val versionName = "1.0.1"

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val paletteKtx = "androidx.palette:palette-ktx:1.0.0"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
    }

    object Kotlin {
        const val version = "1.5.30"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    object Glide {
        private const val version = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Coil {
        private const val version = "1.4.0"
        const val coil = "io.coil-kt:coil:$version"
    }

    object Coroutines{
        private const val version="1.5.2"
        const val core="org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android="org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }
}