# Module Structure Template Guide

## Overview
This guide explains how to structure modules using proven architectural patterns. Each module type serves a specific purpose and follows consistent patterns for maintainability and scalability. Use this as a template for organizing any Kotlin Multiplatform project with clean architecture principles.

## Module Types

### 1. Common Modules (`common/`)
Shared functionality across all features and platforms.

#### common_domain
- **Purpose**: Shared domain models and interfaces
- **Dependencies**: None (pure Kotlin)
- **Contents**: 
  - Base domain models
  - Common interfaces (e.g., `SessionIds`)
  - Shared value objects
  - Common exceptions

#### common_data
- **Purpose**: Shared data layer implementations
- **Dependencies**: `common_domain`
- **Contents**:
  - Base repository implementations
  - Common data sources
  - Shared DTOs and mappers
  - Network/database utilities

#### common_presentation
- **Purpose**: Shared presentation layer components
- **Dependencies**: `common_domain`
- **Contents**:
  - `MVIViewModel` base class
  - `SessionHandlerDelegate` and `SessionHandler`
  - Common UI components
  - Shared ViewModels and state classes

#### common_test
- **Purpose**: Shared testing utilities
- **Dependencies**: All common modules
- **Contents**:
  - Test utilities and helpers
  - Mock implementations
  - Common test fixtures
  - Testing extensions

### 2. Core Modules (`core/`)
Core business logic and infrastructure.

#### core_shared
- **Purpose**: Shared core components across platforms
- **Dependencies**: `common_domain`
- **Contents**:
  - Core business models
  - Shared utilities
  - Platform abstractions

#### core_server
- **Purpose**: Server-specific core components
- **Dependencies**: `core_shared`, `core_domain`
- **Contents**:
  - Server-side business logic
  - Authentication/authorization
  - Server utilities

#### core_data
- **Purpose**: Core data layer implementations
- **Dependencies**: `core_domain`, `common_data`
- **Contents**:
  - Core repository implementations
  - Database entities and DAOs
  - API clients and data sources

#### core_domain
- **Purpose**: Core business logic and use cases
- **Dependencies**: `common_domain`
- **Contents**:
  - Core use cases
  - Business rules and validation
  - Core domain models
  - Repository interfaces

#### core_presentation
- **Purpose**: Core presentation components
- **Dependencies**: `core_domain`, `common_presentation`
- **Contents**:
  - Core ViewModels
  - Navigation logic
  - Core UI state management

### 3. Feature Modules (`feature/`)
Feature-specific implementations following Clean Architecture.

#### Feature Structure Pattern
Each feature follows this structure:
```
feature/
└── [feature_name]/
    ├── [feature_name]_data/
    ├── [feature_name]_domain/
    ├── [feature_name]_presentation/
    ├── [feature_name]_shared/     # Optional: shared components
    └── [feature_name]_server/     # Optional: server components
```

#### [feature_name]_domain
- **Purpose**: Feature-specific business logic
- **Dependencies**: `common_domain`, `core_domain`
- **Contents**:
  - Feature use cases
  - Feature domain models
  - Repository interfaces
  - Business rules

#### [feature_name]_data
- **Purpose**: Feature data layer implementation
- **Dependencies**: `[feature_name]_domain`, `common_data`, `core_data`
- **Contents**:
  - Repository implementations
  - Data sources (local/remote)
  - DTOs and mappers
  - Feature-specific database entities

#### [feature_name]_presentation
- **Purpose**: Feature UI and presentation logic
- **Dependencies**: `[feature_name]_domain`, `common_presentation`, `core_presentation`
- **Contents**:
  - Feature ViewModels
  - UI state classes
  - Screen composables
  - Navigation components

#### [feature_name]_shared (Optional)
- **Purpose**: Shared components within the feature
- **Dependencies**: `[feature_name]_domain`
- **Contents**:
  - Shared models between client/server
  - Common utilities
  - Shared interfaces

#### [feature_name]_server (Optional)
- **Purpose**: Server-side feature implementation
- **Dependencies**: `[feature_name]_domain`, `[feature_name]_shared`, `core_server`
- **Contents**:
  - Server endpoints/controllers
  - Server-side business logic
  - WebSocket handlers

### 4. Application Modules

#### composeApp
- **Purpose**: Main application entry point
- **Dependencies**: All presentation modules, core modules
- **Contents**:
  - Application class
  - Main activity/entry points
  - Root navigation
  - DI setup
  - Platform-specific implementations

#### server
- **Purpose**: Standalone server application
- **Dependencies**: All server modules, core modules
- **Contents**:
  - Server application entry point
  - Ktor configuration
  - Database setup
  - Server DI configuration

#### config
- **Purpose**: Configuration and build utilities
- **Dependencies**: None
- **Contents**:
  - Build configuration
  - Shared constants
  - Environment-specific settings

## Module Dependencies Rules

### Dependency Flow
```
Presentation Layer
       ↓
Domain Layer (Use Cases)
       ↓
Data Layer (Repositories)
```

### Allowed Dependencies
- **Presentation** → Domain (✅)
- **Data** → Domain (✅)
- **Domain** → Domain (✅ within same layer)
- **Common** → Common (✅)
- **Core** → Common (✅)
- **Feature** → Core (✅)
- **Feature** → Common (✅)

### Forbidden Dependencies
- **Domain** → Presentation (❌)
- **Domain** → Data (❌)
- **Common** → Core (❌)
- **Common** → Feature (❌)
- **Core** → Feature (❌)

## Module Naming Conventions

### Package Structure
```
org.cjthomson.househelper.[module_type].[module_name].[layer]
```

Examples:
- `org.cjthomson.househelper.common.presentation`
- `org.cjthomson.househelper.core.domain`
- `org.cjthomson.househelper.feature.messaging.data`

### File Organization
```
src/
├── commonMain/kotlin/org/cjthomson/househelper/[module]/
├── commonTest/kotlin/org/cjthomson/househelper/[module]/
├── androidMain/kotlin/org/cjthomson/househelper/[module]/
├── iosMain/kotlin/org/cjthomson/househelper/[module]/
└── jvmMain/kotlin/org/cjthomson/househelper/[module]/
```

## Template Best Practices

### 1. Single Responsibility
Each module should have a single, well-defined responsibility that can be easily understood and maintained.

### 2. Minimal Dependencies
Keep module dependencies to a minimum. Only depend on what you actually need to avoid tight coupling.

### 3. Interface Segregation
Define clear interfaces between modules. Avoid exposing internal implementation details to maintain clean boundaries.

### 4. Platform Abstraction
Use expect/actual declarations for platform-specific implementations to maintain true multiplatform compatibility.

### 5. Template-Based Development
- Follow the established patterns consistently across all features
- Use the MembersScreenViewModel pattern as the gold standard for ViewModels
- Implement interface-based use cases with custom result types
- Apply session management patterns where applicable

### 6. Testing Strategy
- Each module should have comprehensive tests following the template patterns
- Use shared test utilities from `common_test`
- Mock dependencies at module boundaries
- Test ViewModels using the established patterns with Turbine

### 7. Documentation
- Document module purpose and responsibilities
- Maintain clear README files for complex modules
- Document public APIs and interfaces
- Reference template patterns in code comments

## Module Creation Checklist

When creating a new module:

1. ✅ Create appropriate directory structure
2. ✅ Set up `build.gradle.kts` with correct dependencies
3. ✅ Define package structure following conventions
4. ✅ Create necessary source sets (commonMain, commonTest, etc.)
5. ✅ Add module to `settings.gradle.kts`
6. ✅ Update dependent modules' build files
7. ✅ Create initial interfaces and base classes
8. ✅ Set up DI modules if needed
9. ✅ Add basic tests
10. ✅ Document module purpose and API

This modular structure ensures clean separation of concerns, testability, and maintainability while supporting the multiplatform nature of the project.