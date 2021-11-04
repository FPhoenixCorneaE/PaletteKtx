plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Deps.compileSdkVersion)
    buildToolsVersion(Deps.buildToolsVersion)

    defaultConfig {
        applicationId("com.fphoenixcorneae.palette.sample")
        minSdkVersion(Deps.minSdkVersion)
        targetSdkVersion(Deps.targetSdkVersion)
        versionCode = Deps.versionCode
        versionName = Deps.versionName
    }

    sourceSets {
        val main by getting
        main.java.srcDirs("src/main/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    packagingOptions {
        resources.excludes += "DebugProbesKt.bin"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(mapOf("path" to ":paletteKtx")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.coreKtx)

    implementation(Deps.Glide.glide)
    kapt(Deps.Glide.compiler)

    implementation(Deps.Coil.coil)

    implementation(Deps.Coroutines.core)
    implementation(Deps.Coroutines.android)
}

