# Kotlin Multiplatform Architecture Template Overview

## Template Summary
This architecture template is based on a sophisticated real-world Kotlin Multiplatform project that demonstrates modern cross-platform development practices. The template provides proven patterns for building scalable applications with clean architecture, featuring modular design, MVI pattern, and comprehensive multiplatform support. Use this as a foundation for any type of application requiring robust architecture and maintainable code structure.

## Technology Stack

> **Note**: Use the latest stable versions of all dependencies. Check the official documentation for current version numbers:
> - [Kotlin Releases](https://kotlinlang.org/docs/releases.html)
> - [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/releases)
> - [Ktor](https://ktor.io/docs/releases.html)
> - [Koin](https://github.com/InsertKoinIO/koin/releases)

### Core Technologies
- **Kotlin Multiplatform**: Latest stable (Cross-platform development)
- **Compose Multiplatform**: Latest stable (Cross-platform UI framework)
- **Java Target**: 21 (LTS version recommended)
- **Android**: Latest API level (compileSdk), API 27+ (minSdk), Latest API level (targetSdk)

### Networking & Communication
- **Ktor**: Latest stable (Client/Server HTTP + WebSockets)
- **Kotlinx Serialization**: Latest stable (JSON serialization)
- **Kotlinx Coroutines**: Latest stable (Async programming)

### Database & Storage
- **Room**: Latest stable (Local SQLite database)
- **MongoDB**: Latest stable (Server-side database)
- **DataStore**: Latest stable (Preferences storage)

### Dependency Injection & Architecture
- **Koin**: Latest stable (Dependency injection)
- **MVI Pattern**: Custom implementation with StateFlow/SharedFlow
- **Clean Architecture**: Layered approach (Data/Domain/Presentation)

### Testing & Quality
- **Kotest**: Latest stable (Testing framework)
- **Mokkery**: Latest stable (Mocking for KMP)
- **Turbine**: Latest stable (Flow testing)
- **JaCoCo**: Latest stable (Code coverage)
- **AssertK**: Latest stable (Assertions)

### Logging & Utilities
- **Kermit**: Latest stable (Multiplatform logging)
- **Kotlinx DateTime**: Latest stable (Date/time handling)
- **Moko Permissions**: Latest stable (Permission handling)

## Template Project Structure

### Module Organization Pattern
```
YourProject/
├── config/                     # Configuration module
├── composeApp/                 # Main application module
├── common/                     # Shared common modules
│   ├── common_data/           # Common data layer patterns
│   ├── common_domain/         # Common domain interfaces
│   ├── common_presentation/   # MVI base classes & session handling
│   └── common_test/           # Common test utilities
├── core/                      # Core business logic modules
│   ├── core_shared/           # Shared core components
│   ├── core_server/           # Server-specific core (optional)
│   ├── core_data/             # Core data implementations
│   ├── core_domain/           # Core use cases & models
│   └── core_presentation/     # Core ViewModels & UI state
├── feature/                   # Feature-specific modules (examples)
│   ├── feature_a/             # Any feature (users, products, etc.)
│   │   ├── feature_a_data/
│   │   ├── feature_a_domain/
│   │   └── feature_a_presentation/
│   └── feature_b/             # Another feature
│       ├── feature_b_shared/  # Optional: shared components
│       ├── feature_b_server/  # Optional: server endpoints
│       ├── feature_b_data/
│       ├── feature_b_domain/
│       └── feature_b_presentation/
└── server/                    # Standalone server application (optional)
```

### Layer Architecture
Each feature follows Clean Architecture principles:

1. **Data Layer**: Repository implementations, data sources, DTOs
2. **Domain Layer**: Business logic, use cases, domain models
3. **Presentation Layer**: ViewModels, UI state, side effects

## Key Architectural Patterns

### 1. MVI (Model-View-Intent) Pattern
- **MVIViewModel**: Base class for all ViewModels
- **ViewEvent**: User interactions and system events
- **ViewState**: UI state representation
- **ViewSideEffect**: One-time effects (navigation, toasts, etc.)

### 2. Session Management
- **SessionHandlerDelegate**: Interface for session handling
- **SessionHandler**: Implementation with StateFlow-based reactive updates
- **SessionIds**: Domain interface for session identification

### 3. Dependency Injection with Koin
- Modular DI setup across all layers
- Platform-specific implementations
- ViewModel injection with Compose integration

### 4. Reactive Programming
- StateFlow for state management
- SharedFlow for events and effects
- Coroutines for async operations
- Flow-based data streams

## Platform Targets

### Supported Platforms
- **Android**: Primary mobile platform
- **iOS**: iPhone and iPad support (x64, arm64, simulator arm64)
- **JVM**: Desktop and server applications
- **Server**: Ktor-based backend

### Platform-Specific Implementations
- Android: Activity Compose, Android-specific Koin modules
- iOS: Framework generation for XCode integration
- JVM: Desktop UI and server components

## Build Configuration

### Gradle Setup
- **Type-safe project accessors**: Enabled for better IDE support
- **Version catalogs**: Centralized dependency management
- **JaCoCo integration**: Comprehensive test coverage reporting
- **Multi-module testing**: Coordinated test execution

### Key Gradle Features
- Custom tasks for JVM-only testing
- Automated coverage reporting
- Platform-specific source sets
- Framework generation for iOS

## Development Practices

### Code Quality
- Kotlin official code style
- Comprehensive test coverage with JaCoCo
- Structured logging with Kermit
- Type-safe navigation and resources

### Testing Strategy
- Unit tests for all layers
- Integration tests for repositories
- ViewModel testing with Turbine
- Mocking with Mokkery for KMP compatibility

### CI/CD Integration
- GitHub Actions workflows
- Automated testing on pull requests
- Coverage reporting and validation
- Multi-platform build verification

## Key Template Design Decisions

1. **Modular Architecture**: Clear separation of concerns with feature-based modules for maximum reusability
2. **Clean Architecture**: Dependency inversion with domain-centric design following proven patterns
3. **Reactive UI**: StateFlow/SharedFlow for predictable state management with MVI pattern
4. **Type Safety**: Extensive use of sealed classes and type-safe builders for robust code
5. **Platform Abstraction**: Common interfaces with platform-specific implementations for true multiplatform support
6. **Modern Tooling**: Latest stable versions of all major dependencies for future-proof development
7. **Session Management**: Sophisticated session handling with reactive updates and readiness checks
8. **Interface-Based Design**: Use case interfaces with implementations for better testability and flexibility

This template architecture provides a solid, battle-tested foundation for scalable, maintainable, and testable multiplatform applications of any domain or complexity.