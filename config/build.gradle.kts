import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.buildconfig)
}

kotlin {
    androidLibrary {
        namespace = "com.gpcasiapac.gpchelloworldkmp.config"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            // No dependencies needed for config
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// Load streamlined configuration
val configFile = File(rootDir, "config/application.properties")
val properties = Properties().apply {
    if (configFile.exists()) {
        load(FileInputStream(configFile))
    } else {
        throw GradleException("Config file not found: ${configFile.absolutePath}")
    }
}

// Get environment from system property or use default
val environment = System.getProperty("environment") ?: properties.getProperty("default.environment") ?: "mock"

buildConfig {
    useKotlinOutput { internalVisibility = false }
    
    forClass("BuildConfig") {
        packageName("com.gpcasiapac.gpchelloworldkmp.config")
        
        // Get configuration values using environment prefix
        val buildType = properties.getProperty("build.type") ?: "debug"
        val host = properties.getProperty("$environment.host") ?: "localhost"
        val port = properties.getProperty("$environment.port")?.toInt() ?: 8080
        val protocol = properties.getProperty("$environment.protocol") ?: "http"
        val useMockData = properties.getProperty("$environment.use_mock_data")?.toBoolean() ?: false
        
        // Construct API_BASE_URL from components (eliminates redundancy)
        val apiBaseUrl = "$protocol://$host:$port/api"
        
        val isDebug = when (buildType) {
            "debug" -> "true"
            "release" -> "false"
            else -> throw GradleException("Invalid build type: $buildType")
        }
        
        buildConfigField("String", "ENVIRONMENT", "\"$environment\"")
        buildConfigField("Boolean", "DEBUG", isDebug)
        buildConfigField("String", "HOST", "\"$host\"")
        buildConfigField("Int", "PORT", port)
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
        buildConfigField("Boolean", "USE_MOCK_DATA", useMockData)
    }
}