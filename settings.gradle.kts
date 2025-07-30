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
    }
}

dependencyResolutionManagement {
    repositories {
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

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// Configuration module
include(":config")

// Main application
include(":composeApp")

// Common modules
include(":common:common_data")
include(":common:common_domain")
include(":common:common_presentation")

// Core modules
include(":core:core_shared")
include(":core:core_data")
include(":core:core_domain")
include(":core:core_presentation")

// Feature modules
include(":feature:hello:hello_data")
include(":feature:hello:hello_domain")
include(":feature:hello:hello_presentation")