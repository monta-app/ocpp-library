val libraryVersion: String by project
val javaToolChainVersion: String by project

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jlleitschuh.gradle.ktlint")
    id("java-library")
    id("maven-publish")
}

val libraryGroupId = "com.monta.library.ocpp"
val libraryArtifactId = "ocpp-core"

group = "$libraryGroupId:$libraryArtifactId"
version = libraryVersion

dependencies {
    // Main
    implementation(platform(kotlinlibs.platform))
    implementation(kotlinlibs.bundles.implementation)

    implementation(platform(coroutines.platform))
    implementation(coroutines.bundles.implementation)

    implementation(platform(jackson.platform))
    implementation(jackson.bundles.implementation)

    implementation(logback.bundles.implementation)
    // Testing
    testImplementation(platform(kotest.platform))
    testImplementation(kotest.bundles.test.implementation)
    testRuntimeOnly(kotest.bundles.test.runtime)
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(javaToolChainVersion.toInt())
}

tasks {
    test {
        useJUnitPlatform()
    }
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    processTestResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/monta-app/library-ocpp")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            groupId = libraryGroupId
            artifactId = libraryArtifactId
            version = libraryVersion
            from(components["java"])
            pom {
                name.set("Monta OCPP Core Library")
                url.set("https://github.com/monta-app/library-ocpp")
                scm {
                    connection.set("git@github.com:monta-app/library-ocpp.git")
                    url.set("https://github.com/monta-app/library-ocpp")
                }
            }
        }
    }
}
