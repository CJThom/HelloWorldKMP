plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidLibrary {
        namespace = "com.gpcasiapac.gpchelloworldkmp.feature.login.impl"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.features.login.loginApi)
            implementation(projects.shared.features.login.loginDomain)
            implementation(projects.shared.features.login.loginData)
            implementation(projects.shared.features.login.loginPresentation)

            implementation(libs.koin.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.navigation.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
