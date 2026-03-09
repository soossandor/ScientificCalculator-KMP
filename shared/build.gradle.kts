plugins {
    kotlin("multiplatform")
}

kotlin {
    jvmToolchain(21)

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // A hivatalos Kotlin Multiplatform BigNum könyvtár
                implementation("com.ionspin.kotlin:bignum:0.3.10")
            }
        }
        val jvmMain by getting
    }
}

repositories {
    mavenCentral()
}