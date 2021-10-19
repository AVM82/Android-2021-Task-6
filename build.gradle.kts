// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jlleitschuh.gradle.ktlint").version("10.2.0")
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(rs.school.rs.android2021task6.BuildPlugins.gradle)
        classpath(rs.school.rs.android2021task6.BuildPlugins.kotlin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

//    repositories {
        // Required to download KtLint
//        mavenCentral()
//    }

    // Optionally configure plugin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
