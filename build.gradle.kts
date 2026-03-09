plugins {
    kotlin("multiplatform") version "1.9.24" apply false
    kotlin("jvm") version "2.0.21"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}