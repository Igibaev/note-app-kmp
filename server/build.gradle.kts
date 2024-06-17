plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "org.example.project"
version = "1.0.0"
application {
    mainClass.set("org.example.project.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.11")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.11")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.11")
    implementation("io.ktor:ktor-server-cors:2.3.11")
    implementation("io.ktor:ktor-network-tls-certificates:2.3.11")

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
}