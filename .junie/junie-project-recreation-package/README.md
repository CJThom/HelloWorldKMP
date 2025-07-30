# Kotlin Multiplatform Architecture Template Package

## Overview
This package contains comprehensive documentation and templates for creating modern Kotlin Multiplatform applications using proven architectural patterns. Based on a sophisticated real-world project structure, it provides everything needed to build scalable, maintainable apps with clean architecture, MVI pattern, and modular design. It's specifically designed to help developers (including AI assistants like Junie) understand, implement, and maintain high-quality multiplatform projects.

## Package Contents

### 📋 Documentation Files

#### [01-ARCHITECTURE-OVERVIEW.md](01-ARCHITECTURE-OVERVIEW.md)
**Purpose**: High-level architectural overview and template foundation  
**Contents**: 
- Complete technology stack with versions
- Project structure and module organization patterns
- Key architectural patterns (MVI, Clean Architecture, Modular Design)
- Platform targets and build configuration templates
- Development practices and design decisions for scalable apps

**When to use**: Start here to understand the template architecture and technology choices for your project.

#### [02-MODULE-STRUCTURE-GUIDE.md](02-MODULE-STRUCTURE-GUIDE.md)
**Purpose**: Detailed module organization and dependency rules  
**Contents**:
- Module types (common, core, feature, application)
- Dependency flow rules and restrictions
- Package naming conventions
- Module creation checklist
- Best practices for modular architecture

**When to use**: When creating new modules or understanding module relationships.

#### [03-BUILD-CONFIGURATION-TEMPLATES.md](03-BUILD-CONFIGURATION-TEMPLATES.md)
**Purpose**: Complete build configuration templates  
**Contents**:
- Root project configuration (build.gradle.kts, settings.gradle.kts)
- Version catalog template (libs.versions.toml)
- Module-specific build templates
- Gradle properties configuration
- Customization guidelines

**When to use**: When setting up build configuration for new projects or modules.

#### [04-DEVELOPMENT-GUIDELINES.md](04-DEVELOPMENT-GUIDELINES.md)
**Purpose**: Comprehensive development practices and standards  
**Contents**:
- Code style and standards
- Architecture implementation guidelines
- Testing strategies and tools
- Logging practices with Kermit
- Error handling patterns
- Performance guidelines
- CI/CD setup
- Security considerations

**When to use**: For day-to-day development practices and code quality standards.

### 🛠️ Implementation Resources

#### [05-CODE-TEMPLATES.md](05-CODE-TEMPLATES.md)
**Purpose**: Ready-to-use code templates and snippets  
**Contents**:
- Base MVI architecture components (MVIViewModel, SessionHandler)
- Complete feature implementation templates (domain, data, presentation)
- Dependency injection templates with Koin
- Testing templates (ViewModel, Repository tests)
- Compose UI templates
- Usage instructions

**When to use**: When implementing new features or components following the established patterns.

#### [06-JUNIE-PROJECT-ANALYSIS.md](06-JUNIE-PROJECT-ANALYSIS.md)
**Purpose**: AI assistant-specific template analysis and guidelines  
**Contents**:
- Quick architecture template overview for AI understanding
- Key components and patterns to recognize in template-based projects
- Common issues and solutions when using these patterns
- AI assistant guidelines for code analysis and template-based suggestions
- Red flags and debugging tips for template implementations

**When to use**: Specifically designed for AI assistants to quickly understand and work with template-based architectures.

#### [07-KEY-PATTERNS-AND-CONVENTIONS.md](07-KEY-PATTERNS-AND-CONVENTIONS.md)
**Purpose**: Detailed implementation patterns and naming conventions  
**Contents**:
- Comprehensive naming conventions
- MVI pattern implementation examples
- Clean Architecture layer patterns
- Dependency injection patterns
- Error handling and testing patterns
- Logging and performance patterns
- Compose UI patterns

**When to use**: As a reference for consistent implementation across the project.

#### [08-SETUP-INSTRUCTIONS.md](08-SETUP-INSTRUCTIONS.md)
**Purpose**: Step-by-step project setup guide  
**Contents**:
- Prerequisites and environment setup
- Project initialization steps
- Configuration files setup
- Base architecture implementation
- Feature module creation
- Testing and CI/CD setup
- Platform-specific configuration
- Troubleshooting guide

**When to use**: When starting a new project from scratch based on this architecture.

## How to Use This Package

### For New Projects
1. **Start with**: [08-SETUP-INSTRUCTIONS.md](08-SETUP-INSTRUCTIONS.md)
2. **Reference**: [03-BUILD-CONFIGURATION-TEMPLATES.md](03-BUILD-CONFIGURATION-TEMPLATES.md) for build setup
3. **Implement**: Using [05-CODE-TEMPLATES.md](05-CODE-TEMPLATES.md) for base components
4. **Follow**: [07-KEY-PATTERNS-AND-CONVENTIONS.md](07-KEY-PATTERNS-AND-CONVENTIONS.md) for consistency
5. **Maintain**: Using [04-DEVELOPMENT-GUIDELINES.md](04-DEVELOPMENT-GUIDELINES.md) practices

### For Understanding Existing Projects
1. **Overview**: [01-ARCHITECTURE-OVERVIEW.md](01-ARCHITECTURE-OVERVIEW.md)
2. **Structure**: [02-MODULE-STRUCTURE-GUIDE.md](02-MODULE-STRUCTURE-GUIDE.md)
3. **Patterns**: [07-KEY-PATTERNS-AND-CONVENTIONS.md](07-KEY-PATTERNS-AND-CONVENTIONS.md)
4. **AI Analysis**: [06-JUNIE-PROJECT-ANALYSIS.md](06-JUNIE-PROJECT-ANALYSIS.md) (for AI assistants)

### For Adding New Features
1. **Plan**: Using [02-MODULE-STRUCTURE-GUIDE.md](02-MODULE-STRUCTURE-GUIDE.md)
2. **Implement**: Using [05-CODE-TEMPLATES.md](05-CODE-TEMPLATES.md)
3. **Follow**: [07-KEY-PATTERNS-AND-CONVENTIONS.md](07-KEY-PATTERNS-AND-CONVENTIONS.md)
4. **Test**: Using [04-DEVELOPMENT-GUIDELINES.md](04-DEVELOPMENT-GUIDELINES.md) testing guidelines

### For AI Assistants (like Junie)
1. **Quick Start**: [06-JUNIE-PROJECT-ANALYSIS.md](06-JUNIE-PROJECT-ANALYSIS.md)
2. **Deep Dive**: [01-ARCHITECTURE-OVERVIEW.md](01-ARCHITECTURE-OVERVIEW.md)
3. **Implementation**: [05-CODE-TEMPLATES.md](05-CODE-TEMPLATES.md) and [07-KEY-PATTERNS-AND-CONVENTIONS.md](07-KEY-PATTERNS-AND-CONVENTIONS.md)

## Template Architecture Highlights

### 🏗️ Core Patterns (Based on Real-World Implementation)
- **MVI (Model-View-Intent)**: Unidirectional data flow with reactive state management using proven ViewModel patterns
- **Clean Architecture**: Clear separation of concerns with dependency inversion and interface-based design
- **Modular Design**: Feature-based modules with shared common/core components for maximum reusability
- **Multiplatform**: Android, iOS, JVM, and Server targets with platform-specific optimizations

### 🛠️ Technology Stack
- **Kotlin**: Latest stable (Cross-platform development)
- **Compose Multiplatform**: Latest stable (Cross-platform UI)
- **Ktor**: Latest stable (Networking client/server)
- **Koin**: Latest stable (Dependency injection)
- **Room**: Latest stable (Local database)
- **Kermit**: Latest stable (Logging)
- **Kotest**: Latest stable (Testing)

### 📱 Platform Support
- **Android**: API 27+ (Primary mobile platform)
- **iOS**: iPhone and iPad support
- **JVM**: Desktop applications
- **Server**: Ktor-based backend

## Key Benefits

### 🎯 For Developers
- **Consistency**: Standardized patterns across all features
- **Scalability**: Modular architecture supports growth
- **Testability**: Comprehensive testing strategies
- **Maintainability**: Clear separation of concerns
- **Performance**: Optimized for multiplatform development

### 🤖 For AI Assistants
- **Quick Understanding**: Structured analysis documents
- **Pattern Recognition**: Clear architectural patterns
- **Implementation Guidance**: Ready-to-use templates
- **Best Practices**: Established conventions and guidelines
- **Error Prevention**: Common pitfalls and solutions

## Project Structure Example

```
YourProject/
├── common/                    # Shared across all features
│   ├── common_domain/        # Base domain models and interfaces
│   ├── common_data/          # Base repository patterns
│   ├── common_presentation/  # MVI base classes, session handling
│   └── common_test/          # Shared test utilities
├── core/                     # Core business logic
│   ├── core_domain/          # Core use cases and models
│   ├── core_data/            # Core repository implementations
│   └── core_presentation/    # Core ViewModels and UI
├── feature/                  # Feature-specific modules
│   └── messaging/            # Example feature
│       ├── messaging_domain/ # Feature business logic
│       ├── messaging_data/   # Feature data layer
│       └── messaging_presentation/ # Feature UI layer
├── composeApp/               # Main application
└── server/                   # Backend application
```

## Quality Assurance

### ✅ Testing Coverage
- Unit tests for all layers
- Integration tests for repositories
- ViewModel testing with Turbine
- UI testing with Compose testing

### 🔍 Code Quality
- JaCoCo test coverage reporting
- Static analysis integration
- Consistent code style enforcement
- Comprehensive documentation

### 🚀 CI/CD Integration
- Automated testing on pull requests
- Coverage validation
- Multi-platform build verification
- Deployment automation

## Getting Started

1. **Choose your starting point**:
   - New project: [08-SETUP-INSTRUCTIONS.md](08-SETUP-INSTRUCTIONS.md)
   - Understanding existing: [01-ARCHITECTURE-OVERVIEW.md](01-ARCHITECTURE-OVERVIEW.md)
   - AI assistant: [06-JUNIE-PROJECT-ANALYSIS.md](06-JUNIE-PROJECT-ANALYSIS.md)

2. **Follow the documentation** in the recommended order

3. **Use the templates** from [05-CODE-TEMPLATES.md](05-CODE-TEMPLATES.md)

4. **Maintain consistency** with [07-KEY-PATTERNS-AND-CONVENTIONS.md](07-KEY-PATTERNS-AND-CONVENTIONS.md)

## Support and Maintenance

This documentation package is designed to be:
- **Self-contained**: All necessary information included
- **Comprehensive**: Covers all aspects of the architecture
- **Practical**: Ready-to-use templates and examples
- **Maintainable**: Clear structure for updates and additions

## Version Information

- **Package Version**: 1.0.0
- **Based on**: HouseHelper project architecture
- **Target Kotlin**: Latest stable version
- **Last Updated**: 2025-07-21

---

**Note**: This package represents a snapshot of the HouseHelper architecture patterns and best practices. Adapt the templates and guidelines to fit your specific project requirements while maintaining the core architectural principles.