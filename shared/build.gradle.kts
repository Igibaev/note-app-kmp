import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
    }
    
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:2.0.0")
            implementation("io.ktor:ktor-client-core:2.0.0")
            implementation("io.ktor:ktor-client-content-negotiation:2.0.0")
            implementation("io.ktor:ktor-client-serialization:2.0.0")
            implementation("io.ktor:ktor-client-logging:2.0.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")

        }

        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.serialization)
        }

        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-core:2.0.0")
            implementation("io.ktor:ktor-client-content-negotiation:2.0.0")
            implementation("io.ktor:ktor-client-serialization:2.0.0")
            implementation("io.ktor:ktor-client-logging:2.0.0")
        }

        wasmJsMain.dependencies {
            implementation("io.ktor:ktor-client-core:3.0.0-wasm1")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-wasm1")
            implementation("io.ktor:ktor-client-content-negotiation:3.0.0-wasm1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-RC-wasm0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1-wasm0")
        }
    }
}

android {
    namespace = "org.example.project.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
