import rs.school.rs.android2021task6.ConfigData

plugins {
    id ("com.android.library")
    id ("kotlin-android")
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

dependencies {}
