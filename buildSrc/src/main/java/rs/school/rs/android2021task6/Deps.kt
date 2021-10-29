package rs.school.rs.android2021task6

object Deps {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    object Ktx {
        const val core = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedataKtx}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodelKtx}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val junitUi = "androidx.test.ext:junit:${Versions.junitUi}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
        const val hilt = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLivecycle}"
        const val core = "androidx.core:core-ktx:${Versions.androidXcore}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    }

    object Kapt {
        const val dagger = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val hilt = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
    }

    object ExoPlayer {
        const val core = "com.google.android.exoplayer:exoplayer-core:${Versions.exoPlayer}"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoPlayer}"
        const val mediasession = "com.google.android.exoplayer:extension-mediasession:${Versions.exoPlayer}"
    }
}
