import rs.school.rs.android2021task6.ConfigData
import rs.school.rs.android2021task6.Deps

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "rs.school.rs.android2021task6"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Deps.Ktx.core)
    implementation(Deps.Ktx.liveData)
    implementation(Deps.Ktx.viewModel)
    implementation(Deps.Ktx.fragment)

    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.Google.material)
    implementation(Deps.AndroidX.constraint)

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.junitUi)
    androidTestImplementation(Deps.Test.espresso)
}
