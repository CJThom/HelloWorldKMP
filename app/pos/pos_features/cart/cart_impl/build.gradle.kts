plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidLibrary {
        namespace = "com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.app.pos.posFeatures.cart.cartApi)
            implementation(libs.koin.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.navigation.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}