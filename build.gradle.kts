plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") apply false
    // Linter
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1" apply false
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}
