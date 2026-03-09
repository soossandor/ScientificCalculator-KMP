plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0" // JavaFX plugin
    kotlin("jvm")
}

group = "hu.calculator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.graphics")
}

tasks.test {
    useJUnitPlatform()
}

application {
    // Ez lesz a programunk belépési pontja
    mainClass.set("hu.calculator.ui.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}