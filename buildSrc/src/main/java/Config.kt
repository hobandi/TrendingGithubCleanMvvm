object Config {

    object Android {
        // Android sdk and version
        const val androidMinSdkVersion = 23
        const val androidTargetSdkVersion = 30
        const val androidCompileSdkVersion = 30
        const val androidBuildToolsVersion = "30.0.2"
    }

    object ClassPaths {
        const val androidGradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
        const val kotlinGradle =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val navigationSafArgsGradle =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.gradleNavigationArgVersion}"
        const val ktLint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktLintVersion}"

        const val jitPackUrl = "https://jitpack.io"
        const val googleUrl = "https://maven.google.com/"
        const val pluginGradle = "https://plugins.gradle.org/m2/"
        const val pluginKtLint = "org.jlleitschuh.gradle.ktlint"
    }

    object Plugins {
        const val kotlin = "kotlin"
        const val javaLibrary = "java-library"
        const val android = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val navigationSafArgs = "androidx.navigation.safeargs.kotlin"
        const val kotlinKapt = "kotlin-kapt"
        const val kotlinParcelize = "kotlin-parcelize"
        const val androidLibrary = "com.android.library"
    }

    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}
