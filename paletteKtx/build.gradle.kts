plugins {
    id("com.android.library")
    kotlin("android")
    id("com.github.dcendents.android-maven")
}

group = "com.github.FPhoenixCorneaE"

android {
    compileSdkVersion(Deps.compileSdkVersion)
    buildToolsVersion(Deps.buildToolsVersion)

    defaultConfig {
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
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.coreKtx)
    api(Deps.AndroidX.paletteKtx)
}