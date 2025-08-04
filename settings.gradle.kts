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

// Common modules
include(":common:common_data")
include(":common:common_domain")
include(":common:common_presentation")

// Core modules
include(":core:core_data")
include(":core:core_domain")
include(":core:core_presentation")

// Feature modules
include(":feature:hello:hello_data")
include(":feature:hello:hello_domain")
include(":feature:hello:hello_presentation")