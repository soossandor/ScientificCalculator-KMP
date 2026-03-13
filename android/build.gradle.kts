plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "hu.calculator.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "hu.calculator.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    // ITT KÖTJÜK BE A KÖZÖS MATEMATIKAI MOTORUNKAT:
    implementation(project(":shared"))

    // Alap Android UI elemek
    implementation("androidx.appcompat:appcompat:1.6.1")
}

repositories {
    google()
    mavenCentral()
}