plugins {
    kotlin("multiplatform")
    id("com.android.library") // Ez mondja meg, hogy ez egy Androidos könyvtár is
}

kotlin {
    jvmToolchain(21)

    jvm()
    androidTarget() // ÚJ: Irány a mobil!

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.ionspin.kotlin:bignum:0.3.10")
            }
        }
        val jvmMain by getting
        val androidMain by getting // ÚJ: Android specifikus forráskódok helye (ha lesznek)
    }
}

// ÚJ: Android alapbeállítások a shared modulnak
android {
    namespace = "hu.calculator.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

repositories {
    google() // ÚJ: Az Androidos csomagok innen jönnek!
    mavenCentral()
}