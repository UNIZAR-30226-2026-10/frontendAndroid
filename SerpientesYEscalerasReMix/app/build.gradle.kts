plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix"
    compileSdk {
        version = release(35)
    }

    defaultConfig {
        applicationId = "com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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

    // Jetpack Compose
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.foundation.layout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.02.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navegación en Jetpack Compose
    val nav_version = "2.9.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Retrofit y red
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Para JSON
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Para debug
}