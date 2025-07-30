# Template Setup Instructions

## Overview
This document provides step-by-step instructions for setting up a new project using proven Kotlin Multiplatform architectural patterns. Follow these steps to create a solid, scalable foundation for any type of application using clean architecture, MVI pattern, and modular design principles.

## Prerequisites

### Development Environment
- **IntelliJ IDEA** or **Android Studio** (latest stable version)
- **JDK 21** (Temurin distribution recommended)
- **Android SDK** (API 35 for compilation, minimum API 27)
- **Xcode** (for iOS development, macOS only)
- **Git** for version control

### Knowledge Requirements
- Kotlin programming language
- Kotlin Multiplatform basics
- Compose Multiplatform fundamentals
- Clean Architecture principles
- MVI pattern understanding

## Step 1: Project Initialization

### 1.1 Create New Project
1. Open IntelliJ IDEA or Android Studio
2. Create new project using "Kotlin Multiplatform" template
3. Choose project name and location
4. Select target platforms: Android, iOS, JVM, Server

### 1.2 Initial Project Structure
Create the following directory structure:
```
YourProject/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   └── libs.versions.toml
├── config/
├── common/
│   ├── common_data/
│   ├── common_domain/
│   ├── common_presentation/
│   └── common_test/
├── core/
│   ├── core_shared/
│   ├── core_server/
│   ├── core_data/
│   ├── core_domain/
│   └── core_presentation/
├── feature/
│   └── [your_first_feature]/
│       ├── [feature]_data/
│       ├── [feature]_domain/
│       └── [feature]_presentation/
├── composeApp/
└── server/
```

## Step 2: Configuration Files Setup

### 2.1 Root build.gradle.kts
Copy the root build configuration template from `03-BUILD-CONFIGURATION-TEMPLATES.md`:
- Configure plugins with version catalog aliases
- Set up JaCoCo for test coverage
- Create custom tasks for testing
- Apply JaCoCo configuration to subprojects

### 2.2 settings.gradle.kts
- Enable `TYPESAFE_PROJECT_ACCESSORS`
- Configure plugin management repositories
- Include all module declarations
- Set up dependency resolution management

### 2.3 gradle.properties
Configure project properties:
- Kotlin code style: official
- JVM memory settings
- Android configuration
- Ktor development mode

### 2.4 Version Catalog (gradle/libs.versions.toml)
Set up centralized dependency management:
- Define all version numbers
- Configure library dependencies
- Set up plugin declarations
- Create dependency bundles

## Step 3: Base Architecture Setup

### 3.1 Common Domain Module
Create `common/common_domain/`:
1. Set up build.gradle.kts for pure Kotlin module
2. Create base interfaces:
   - `SessionIds` interface
   - Common domain models
   - Base exceptions

### 3.2 Common Presentation Module
Create `common/common_presentation/`:
1. Set up build.gradle.kts with Compose dependencies
2. Implement base MVI classes:
   - `ViewEvent`, `ViewState`, `ViewSideEffect` interfaces
   - `MVIViewModel` base class
   - `SessionHandlerDelegate` and `SessionHandler`
3. Add common UI components

### 3.3 Common Data Module
Create `common/common_data/`:
1. Set up build.gradle.kts with networking dependencies
2. Create base repository patterns
3. Set up common data sources
4. Implement base DTOs and mappers

### 3.4 Common Test Module
Create `common/common_test/`:
1. Set up build.gradle.kts with testing dependencies
2. Create test utilities and helpers
3. Set up mock implementations
4. Create common test fixtures

## Step 4: Core Modules Setup

### 4.1 Core Domain Module
1. Create core business models
2. Implement core use cases
3. Define core repository interfaces
4. Set up business rules and validation

### 4.2 Core Data Module
1. Implement core repository implementations
2. Set up database entities and DAOs
3. Create API clients and data sources
4. Configure Room database

### 4.3 Core Presentation Module
1. Create core ViewModels
2. Set up navigation logic
3. Implement core UI state management
4. Create shared UI components

### 4.4 Core Server Module (Optional)
1. Set up server-side business logic
2. Implement authentication/authorization
3. Create server utilities
4. Configure Ktor components

## Step 5: Feature Module Creation

### 5.1 Feature Domain Module
For each feature, create `feature/[feature_name]/[feature_name]_domain/`:
1. Define feature-specific domain models
2. Create feature use cases
3. Define repository interfaces
4. Implement business rules

Example structure:
```kotlin
// Domain model
data class YourFeatureItem(
    val id: String,
    val title: String,
    val description: String
)

// Use case
class GetYourFeatureItemsUseCase(
    private val repository: YourFeatureRepository
) {
    suspend operator fun invoke(): Result<List<YourFeatureItem>> {
        // Implementation
    }
}

// Repository interface
interface YourFeatureRepository {
    suspend fun getItems(): List<YourFeatureItem>
    fun observeItems(): Flow<List<YourFeatureItem>>
}
```

### 5.2 Feature Data Module
Create `feature/[feature_name]/[feature_name]_data/`:
1. Implement repository interfaces
2. Create DTOs and database entities
3. Set up data sources (local/remote)
4. Implement mappers between layers

### 5.3 Feature Presentation Module
Create `feature/[feature_name]/[feature_name]_presentation/`:
1. Define MVI components (Event, State, Effect)
2. Implement ViewModel extending MVIViewModel
3. Create Compose UI screens
4. Set up navigation components

## Step 6: Dependency Injection Setup

### 6.1 Create Koin Modules
For each module, create DI configuration:

```kotlin
// Domain module
val featureDomainModule = module {
    factory { GetItemsUseCase(get()) }
    factory { SaveItemUseCase(get()) }
}

// Data module
val featureDataModule = module {
    single<ItemRepository> { ItemRepositoryImpl(get(), get()) }
}

// Presentation module
val featurePresentationModule = module {
    viewModel { ItemViewModel(get(), get()) }
}
```

### 6.2 Application Setup
In your main application class:
```kotlin
class YourApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YourApplication)
            modules(
                commonModules,
                coreModules,
                featureModules,
                platformModules
            )
        }
    }
}
```

## Step 7: Testing Setup

### 7.1 Configure Test Dependencies
Add testing libraries to each module:
- Kotest for test framework
- Mokkery for mocking
- Turbine for Flow testing
- AssertK for assertions

### 7.2 Create Test Structure
For each module, create corresponding test packages:
```
src/
├── commonMain/kotlin/
├── commonTest/kotlin/
├── androidTest/kotlin/
└── iosTest/kotlin/
```

### 7.3 Write Initial Tests
Create basic tests for:
- ViewModels (MVI behavior)
- Use cases (business logic)
- Repositories (data operations)

## Step 8: CI/CD Setup

### 8.1 GitHub Actions Configuration
Create `.github/workflows/` directory with:
1. `run_on_pull_request.yaml` - Test execution on PRs
2. `deploy_apks.yaml` - Build and deployment workflow

### 8.2 Test Coverage Configuration
1. Set up JaCoCo configuration files
2. Create coverage check scripts
3. Configure coverage thresholds

## Step 9: Platform-Specific Setup

### 9.1 Android Configuration
1. Set up Android application module
2. Configure manifest and permissions
3. Set up Android-specific dependencies
4. Create Android application class

### 9.2 iOS Configuration
1. Create iOS application entry point
2. Set up framework generation
3. Configure iOS-specific dependencies
4. Set up XCode project integration

### 9.3 Server Configuration (Optional)
1. Set up Ktor server application
2. Configure database connections
3. Set up API endpoints
4. Configure server deployment

## Step 10: Development Workflow

### 10.1 Feature Development Process
1. Start with domain layer (models, use cases)
2. Implement data layer (repository, data sources)
3. Create presentation layer (ViewModel, UI)
4. Write comprehensive tests
5. Set up dependency injection
6. Create UI components

### 10.2 Code Quality Checks
1. Run tests regularly: `./gradlew test`
2. Check coverage: `./gradlew jacocoTestReport`
3. Run static analysis tools
4. Follow code review process

### 10.3 Build and Deployment
1. Local builds: `./gradlew build`
2. Platform-specific builds:
   - Android: `./gradlew assembleDebug`
   - iOS: Build through XCode
   - Server: `./gradlew :server:run`

## Step 11: Best Practices

### 11.1 Code Organization
- Follow package naming conventions
- Maintain clear module boundaries
- Use consistent naming patterns
- Document public APIs

### 11.2 Error Handling
- Use Result pattern consistently
- Implement proper logging
- Handle edge cases gracefully
- Provide meaningful error messages

### 11.3 Performance Optimization
- Use appropriate coroutine dispatchers
- Implement proper caching strategies
- Optimize database queries
- Monitor memory usage

### 11.4 Security Considerations
- Validate all inputs
- Secure API communications
- Protect sensitive data
- Implement proper authentication

## Troubleshooting

### Common Issues

#### Build Issues
- **Problem**: Module not found
- **Solution**: Check `settings.gradle.kts` module declarations

#### Dependency Issues
- **Problem**: Version conflicts
- **Solution**: Use version catalog for consistency

#### Runtime Issues
- **Problem**: Koin injection failures
- **Solution**: Verify module registrations and dependencies

#### Testing Issues
- **Problem**: Flow testing failures
- **Solution**: Use proper test dispatchers and Turbine

### Getting Help
1. Check project documentation
2. Review similar implementations
3. Consult Kotlin Multiplatform documentation
4. Ask in community forums

## Next Steps

After completing the setup:
1. Implement your first feature following the patterns
2. Add comprehensive tests
3. Set up continuous integration
4. Plan for production deployment
5. Consider performance optimizations
6. Implement monitoring and analytics

This setup provides a solid foundation for building scalable, maintainable Kotlin Multiplatform applications using the HouseHelper architecture patterns.