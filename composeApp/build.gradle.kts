import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.chaquoPython)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)

            implementation(libs.calf.file.picker)
            implementation(libs.calf.file.picker.coil)
            implementation(libs.coil.compose)
            implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.1")
            implementation("org.jetbrains.compose.ui:ui")
        }
    }
}

chaquopy {
    defaultConfig {
        version = libs.versions.python.get()

        pip {
            // Use the local repository for the Python packages.
            options("--extra-index-url", "libs/pip/local")

            // Dependencies for the llama-cpp-python package.
            install("typing-extensions")
            install("numpy")
            install("diskcache")
            install("jinja2")
            install("MarkupSafe")
            install("llama-cpp-python")

            // Dependencies for the huggingface_hub package.
            install("PyYAML")
            install("huggingface_hub")
        }
    }
    sourceSets {
        getByName("main") {
            srcDir("src/androidMain/python")
        }
    }
}

android {
    namespace = "io.rnoro.dotory"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.rnoro.dotory"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    debugImplementation(compose.uiTooling)
}