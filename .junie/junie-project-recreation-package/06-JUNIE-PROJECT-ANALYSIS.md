# Junie Project Analysis Summary

## For AI Assistants: Understanding the HouseHelper Architecture

This document provides a high-level analysis specifically designed for AI assistants (like Junie) to quickly understand and work with projects based on the HouseHelper architecture.

## Quick Architecture Overview

### Core Architectural Patterns
1. **MVI (Model-View-Intent)**: Unidirectional data flow with reactive state management
2. **Clean Architecture**: Layered approach with clear dependency rules
3. **Modular Design**: Feature-based modules with shared common/core components
4. **Multiplatform**: Kotlin Multiplatform targeting Android, iOS, JVM, and Server

### Key Components to Look For

#### 1. Base Classes (Always in `common_presentation`)
- `MVIViewModel<Event, State, Effect>`: Base ViewModel with reactive state management
- `SessionHandlerDelegate<Session>`: Session management interface
- `SessionHandler<Session>`: Session management implementation
- `ViewEvent`, `ViewState`, `ViewSideEffect`: Base interfaces for MVI pattern

#### 2. Module Structure Pattern
```
feature/
└── [feature_name]/
    ├── [feature_name]_domain/     # Business logic, use cases
    ├── [feature_name]_data/       # Repository implementations, DTOs
    ├── [feature_name]_presentation/ # ViewModels, UI state, Compose screens
    ├── [feature_name]_shared/     # Optional: shared models
    └── [feature_name]_server/     # Optional: server endpoints
```

#### 3. Dependency Flow (Critical Rule)
```
Presentation → Domain ← Data
     ↓           ↓       ↓
   Common ← Core ← Feature
```

### Technology Stack Indicators

#### Build Files
- `libs.versions.toml`: Version catalog with all dependencies
- `build.gradle.kts`: Root project with JaCoCo and custom tasks
- `settings.gradle.kts`: Module declarations with type-safe accessors

#### Key Dependencies to Expect
- **Kotlin**: Latest stable (cross-platform development)
- **Compose Multiplatform**: Latest stable (UI framework)
- **Ktor**: Latest stable (networking client/server)
- **Koin**: Latest stable (dependency injection)
- **Room**: Latest stable (local database)
- **Kermit**: Latest stable (logging)
- **Kotest**: Latest stable (testing)

## Common Patterns to Recognize

### 1. MVI Implementation Pattern
```kotlin
// Always follows this structure:
sealed interface FeatureEvent : ViewEvent
data class FeatureState(...) : ViewState
sealed interface FeatureEffect : ViewSideEffect

class FeatureViewModel : MVIViewModel<FeatureEvent, FeatureState, FeatureEffect>()
```

### 2. Use Case Pattern
```kotlin
class SomeUseCase(private val repository: SomeRepository) {
    suspend operator fun invoke(...): Result<T> {
        return try {
            // Business logic
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 3. Repository Pattern
```kotlin
interface SomeRepository {
    suspend fun getData(): List<T>
    fun observeData(): Flow<List<T>>
}

class SomeRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SomeRepository {
    // Implementation with local-first approach
}
```

### 4. Koin DI Pattern
```kotlin
val featureDomainModule = module {
    factory { SomeUseCase(get()) }
}

val featureDataModule = module {
    single<SomeRepository> { SomeRepositoryImpl(get(), get()) }
}

val featurePresentationModule = module {
    viewModel { SomeViewModel(get()) }
}
```

## File Organization Patterns

### Package Structure
```
org.company.project.[module_type].[module_name].[layer]
```

### Source Sets (KMP)
```
src/
├── commonMain/kotlin/    # Shared code
├── commonTest/kotlin/    # Shared tests
├── androidMain/kotlin/   # Android-specific
├── iosMain/kotlin/       # iOS-specific
└── jvmMain/kotlin/       # JVM-specific
```

## Testing Patterns

### ViewModel Testing
- Uses Kotest with FreeSpec style
- Turbine for testing Flow emissions
- Mokkery for mocking (KMP compatible)
- Result pattern for error handling

### Test Structure
```kotlin
class SomeViewModelTest : FreeSpec({
    "SomeViewModel" - {
        "should handle event correctly" {
            // Given-When-Then structure
        }
    }
})
```

## Common Issues and Solutions

### 1. Module Dependencies
**Issue**: Circular dependencies or wrong dependency direction
**Solution**: Follow the dependency flow rules strictly

### 2. Platform-Specific Code
**Issue**: Platform-specific implementations needed
**Solution**: Use expect/actual declarations

### 3. State Management
**Issue**: Complex state updates
**Solution**: Use immutable data classes with copy() functions

### 4. Error Handling
**Issue**: Inconsistent error handling
**Solution**: Use Result<T> pattern consistently

## AI Assistant Guidelines

### When Analyzing Code
1. **Identify the module type** (common, core, feature)
2. **Check dependency directions** (ensure they follow the rules)
3. **Look for MVI pattern compliance** (Event, State, Effect)
4. **Verify testing coverage** (each module should have tests)

### When Suggesting Changes
1. **Maintain architectural consistency**
2. **Follow naming conventions**
3. **Respect module boundaries**
4. **Include appropriate tests**
5. **Update DI modules if needed**

### When Creating New Features
1. **Start with domain layer** (models, use cases, repository interfaces)
2. **Implement data layer** (repository, data sources, DTOs)
3. **Create presentation layer** (ViewModel, State, Events, Effects)
4. **Add UI components** (Compose screens)
5. **Set up DI modules**
6. **Write tests**

## Red Flags to Watch For

### Architecture Violations
- Domain layer depending on presentation/data layers
- Common modules depending on core/feature modules
- Missing MVI pattern implementation
- Direct database/network calls in ViewModels

### Code Quality Issues
- Missing error handling
- No logging
- Inconsistent naming
- Missing tests
- Hardcoded values

### Build Configuration Issues
- Missing dependencies in version catalog
- Incorrect module declarations
- Missing platform targets
- No test coverage configuration

## Quick Debugging Tips

### Build Issues
1. Check `settings.gradle.kts` for module declarations
2. Verify `libs.versions.toml` for dependency versions
3. Ensure proper plugin applications

### Runtime Issues
1. Check Koin module registrations
2. Verify ViewModel lifecycle handling
3. Look for coroutine scope issues

### Testing Issues
1. Ensure proper test dependencies
2. Check for missing mock setups
3. Verify coroutine test dispatchers

This analysis provides the foundation for understanding and working with HouseHelper-style architectures efficiently.