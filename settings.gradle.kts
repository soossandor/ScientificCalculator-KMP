pluginManagement {
    repositories {
        google() // Ebből a tárolóból fogja letölteni az Android plugineket!
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "ScientificCalculator-KMP"

include(":desktop")
include(":shared")
include(":android")
