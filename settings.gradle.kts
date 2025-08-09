rootProject.name = "GPCHelloWorldKMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13556278/artifacts/repository")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        // Add AndroidX snapshot repository for Navigation 3 alpha
       // maven { setUrl("https://ci.android.com/builds/submitted/13556278/androidx_snapshot/latest/raw/repository/") }
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13556278/artifacts/repository")
        }
     //   maven { setUrl("https://ci.android.com/builds/submitted/13876626/androidx_snapshot/latest/raw/repository/androidx/compose/material3/adaptive/adaptive-navigation3-android") }

//        androidx/compose/material3/adaptive/adaptive-navigation3-android
      //  maven(url = "https://androidx.dev/snapshots/builds/13551459/artifacts/repository")
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
//}

// Configuration module
include(":config")

// Main application
include(":composeApp")


// Shared modules (used by all app)
include(":shared:common:common_data")
include(":shared:common:common_domain")
include(":shared:common:common_presentation")

include(":shared:core:core_data")
include(":shared:core:core_domain")
include(":shared:core:core_presentation")

include(":shared:features:login:login_domain")
// API/Impl layers for Login (data + presentation consolidated into impl)
include(":shared:features:login:login_api")
include(":shared:features:login:login_impl")

// POS Cart feature modules
include(":app:pos:pos_features:cart:cart_api")
include(":app:pos:pos_features:cart:cart_impl")

include(":shared:features:hello:hello_data")
include(":shared:features:hello:hello_domain")
include(":shared:features:hello:hello_presentation")

// Pickup app modules
include(":app:pickup:pickup_core:pickup_core_domain")
include(":app:pickup:pickup_core:pickup_core_data")

include(":app:pickup:pickup_features:orders:orders_domain")

include(":app:pickup:composePickup")

// POS app modules
include(":app:pos:pos_core:pos_core_domain")
include(":app:pos:pos_core:pos_core_data")

include(":app:pos:pos_features:cart:cart_domain")

include(":app:pos:composePos")