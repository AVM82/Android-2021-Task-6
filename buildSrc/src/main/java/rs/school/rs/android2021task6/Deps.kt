package rs.school.rs.android2021task6

object Deps {
    object Ktx {
        const val core = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedataKtx}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodelKtx}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val junitUi = "androidx.test.ext:junit:${Versions.junitUi}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
    }
}
