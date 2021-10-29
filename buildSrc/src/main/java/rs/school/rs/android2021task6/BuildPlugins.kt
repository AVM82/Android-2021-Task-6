package rs.school.rs.android2021task6

object BuildPlugins {
    val gradle by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val hilt by lazy {"com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"}
}
