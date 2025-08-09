//import org.jetbrains.compose.desktop.application.dsl.TargetFormat
//import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
//import org.jetbrains.kotlin.gradle.dsl.JvmTarget

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // Navigation 3 dependencies for Android only

            implementation(libs.androidx.navigation3.ui)
            implementation(libs.androidx.material3.adaptive.navigation3)
            implementation(libs.androidx.lifecycle.viewmodel.navigation3)
            implementation(libs.androidx.navigation3.runtime)
        }
        commonMain.dependencies {
            implementation(projects.config)
            
            implementation(projects.shared.common.commonData)
            implementation(projects.shared.common.commonDomain)
            implementation(projects.shared.common.commonPresentation)

            implementation(projects.shared.core.coreData)
            implementation(projects.shared.core.coreDomain)
            implementation(projects.shared.core.corePresentation)

            implementation(projects.shared.features.hello.helloData)
            implementation(projects.shared.features.hello.helloDomain)
            implementation(projects.shared.features.hello.helloPresentation)

            implementation(projects.shared.features.login.loginDomain)
            // Login API/Impl (data + presentation consolidated into impl)
            implementation(projects.shared.features.login.loginApi)
            implementation(projects.shared.features.login.loginImpl)

            // POS Cart API/Impl
            implementation(projects.app.pos.posFeatures.cart.cartApi)
            implementation(projects.app.pos.posFeatures.cart.cartImpl)


            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.koin.compose)

            implementation(libs.navigation.compose)
            
            implementation(libs.kotlinx.serialization.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.gpcasiapac.gpchelloworldkmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.gpcasiapac.gpchelloworldkmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        getByName("debug") {
            // Configuration now comes from environment-specific properties in config module
        }
        getByName("release") {
            isMinifyEnabled = false
            // Configuration now comes from environment-specific properties in config module
        }
        create("staging") {
            // Configuration now comes from environment-specific properties in config module
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.gpcasiapac.gpchelloworldkmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.gpcasiapac.gpchelloworldkmp"
            packageVersion = "1.0.0"
        }
    }
}
