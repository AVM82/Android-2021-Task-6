import rs.school.rs.android2021task6.ConfigData
import rs.school.rs.android2021task6.Deps

plugins {
    id ("com.android.library")
    id ("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(Deps.glide)
    implementation(Deps.ExoPlayer.core)
    implementation(Deps.ExoPlayer.mediasession)
    implementation(Deps.ExoPlayer.ui)
    implementation(Deps.Google.hilt)
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.hilt)
    implementation(Deps.coroutines)
    kapt(Deps.Kapt.dagger)
    kapt(Deps.Kapt.hilt)
}
