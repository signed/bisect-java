import net.ltgt.gradle.errorprone.errorprone

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("net.ltgt.errorprone") version "2.0.2"
}

tasks.compileJava {
    options.release.set(16)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone.disableWarningsInGeneratedCode.set(true)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val errorproneVersion = "2.9.0"
val truthVesion = "1.1.3"

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("com.google.truth:truth:$truthVesion")
    testImplementation("com.google.truth.extensions:truth-java8-extension:$truthVesion")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    errorprone("com.google.errorprone:error_prone_core:$errorproneVersion")
}

application {
    // Define the main class for the application.
    mainClass.set("bisect.java.App")
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
