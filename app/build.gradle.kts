import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.rm.lemonbite"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.rm.lemonbite"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.19.0"))

    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")
    implementation("com.google.firebase:firebase-firestore")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // JSON Serialization
    implementation(libs.kotlinx.serialization.json)

    // Unit Test
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Debug
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

// Ktlint
ktlint {
    version.set("1.5.0")
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/generated/**")
        exclude("**/BuildConfig.*")
    }
}
