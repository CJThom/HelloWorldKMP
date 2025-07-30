# Development Guidelines Template

## Overview
This document outlines proven development practices, coding standards, and workflows for building high-quality Kotlin Multiplatform applications. Based on real-world project experience, these guidelines ensure consistency, maintainability, and quality across any codebase using this architectural template.

## Code Style and Standards

### 1. Kotlin Code Style
- **Official Kotlin Code Style**: Use the official Kotlin coding conventions
- **Configuration**: Set `kotlin.code.style=official` in `gradle.properties`
- **IDE Setup**: Configure your IDE to use Kotlin official code style
- **Formatting**: Use automatic formatting with consistent indentation (4 spaces)

### 2. Package Structure
```
org.yourcompany.yourproject.[module_type].[module_name].[layer]
```

Examples:
- `org.yourcompany.yourproject.common.presentation`
- `org.yourcompany.yourproject.feature.messaging.domain`
- `org.yourcompany.yourproject.core.data`

### 3. File Organization
- **One class per file**: Each class should be in its own file
- **Meaningful names**: Use descriptive names for classes, functions, and variables
- **Consistent naming**: Follow Kotlin naming conventions
  - Classes: PascalCase (`UserRepository`)
  - Functions/Variables: camelCase (`getUserById`)
  - Constants: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`)

### 4. Import Organization
- **Explicit imports**: Avoid wildcard imports
- **Grouped imports**: Group imports by package
- **Unused imports**: Remove unused imports

## Architecture Guidelines

### 1. MVI Pattern Implementation
```kotlin
// ViewEvent - User interactions
sealed interface YourFeatureEvent : ViewEvent {
    data object LoadData : YourFeatureEvent
    data class UpdateItem(val id: String, val data: String) : YourFeatureEvent
}

// ViewState - UI state representation
data class YourFeatureState(
    val isLoading: Boolean = false,
    val items: List<YourItem> = emptyList(),
    val error: String? = null
) : ViewState

// ViewSideEffect - One-time effects
sealed interface YourFeatureEffect : ViewSideEffect {
    data class ShowError(val message: String) : YourFeatureEffect
    data class NavigateToDetail(val itemId: String) : YourFeatureEffect
}

// ViewModel implementation
class YourFeatureViewModel(
    private val useCase: YourFeatureUseCase
) : MVIViewModel<YourFeatureEvent, YourFeatureState, YourFeatureEffect>() {
    
    override fun setInitialState(): YourFeatureState = YourFeatureState()
    
    override fun onStart() {
        setEvent(YourFeatureEvent.LoadData)
    }
    
    override fun handleEvents(event: YourFeatureEvent) {
        when (event) {
            is YourFeatureEvent.LoadData -> loadData()
            is YourFeatureEvent.UpdateItem -> updateItem(event.id, event.data)
        }
    }
    
    private fun loadData() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val items = useCase.getItems()
                setState { copy(isLoading = false, items = items, error = null) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message) }
                setEffect { YourFeatureEffect.ShowError(e.message ?: "Unknown error") }
            }
        }
    }
}
```

### 2. Clean Architecture Layers

#### Domain Layer
- **Pure Kotlin**: No Android or platform dependencies
- **Business Logic**: Contains use cases and business rules
- **Interfaces**: Define repository and data source contracts
- **Models**: Domain models representing business entities

```kotlin
// Use Case example
class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<User> {
        return try {
            val user = userRepository.getUserById(userId)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Repository interface
interface UserRepository {
    suspend fun getUserById(id: String): User
    suspend fun saveUser(user: User)
    suspend fun deleteUser(id: String)
}
```

#### Data Layer
- **Repository Implementation**: Implement domain repository interfaces
- **Data Sources**: Local and remote data sources
- **DTOs**: Data transfer objects for API/database
- **Mappers**: Convert between DTOs and domain models

```kotlin
// Repository implementation
class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    
    override suspend fun getUserById(id: String): User {
        return try {
            // Try local first
            localDataSource.getUserById(id)
        } catch (e: Exception) {
            // Fallback to remote
            val userDto = remoteDataSource.getUserById(id)
            val user = userDto.toDomain()
            localDataSource.saveUser(user.toEntity())
            user
        }
    }
}

// Mapper extensions
fun UserDto.toDomain(): User = User(
    id = this.id,
    name = this.name,
    email = this.email
)

fun User.toEntity(): UserEntity = UserEntity(
    id = this.id,
    name = this.name,
    email = this.email
)
```

#### Presentation Layer
- **ViewModels**: Extend MVIViewModel base class
- **UI State**: Immutable state classes
- **Compose UI**: Reactive UI components
- **Navigation**: Type-safe navigation

### 3. Dependency Injection with Koin

#### Module Organization
```kotlin
// Domain module
val domainModule = module {
    factory { GetUserByIdUseCase(get()) }
    factory { SaveUserUseCase(get()) }
}

// Data module
val dataModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single { UserLocalDataSource(get()) }
    single { UserRemoteDataSource(get()) }
}

// Presentation module
val presentationModule = module {
    viewModel { UserViewModel(get(), get()) }
}

// Platform-specific modules
val androidModule = module {
    single { AndroidSpecificDependency() }
}
```

#### DI Setup
```kotlin
// In Application class (Android)
class YourApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YourApplication)
            modules(
                domainModule,
                dataModule,
                presentationModule,
                androidModule
            )
        }
    }
}
```

## Testing Guidelines

### 1. Testing Strategy
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test component interactions
- **UI Tests**: Test user interactions and flows
- **Coverage Target**: Aim for 80%+ code coverage

### 2. Testing Tools
- **Kotest**: Primary testing framework
- **Mokkery**: Mocking for Kotlin Multiplatform
- **Turbine**: Testing Flow emissions
- **AssertK**: Fluent assertions

### 3. Test Structure
```kotlin
class UserViewModelTest : FreeSpec({
    
    "UserViewModel" - {
        val mockUseCase = mockk<GetUserByIdUseCase>()
        val viewModel = UserViewModel(mockUseCase)
        
        "should load user data on start" {
            // Given
            val expectedUser = User("1", "John", "john@example.com")
            coEvery { mockUseCase("1") } returns Result.success(expectedUser)
            
            // When
            viewModel.setEvent(UserEvent.LoadUser("1"))
            
            // Then
            viewModel.viewState.test {
                val state = awaitItem()
                state.user shouldBe expectedUser
                state.isLoading shouldBe false
            }
        }
        
        "should handle error when loading fails" {
            // Given
            val error = Exception("Network error")
            coEvery { mockUseCase("1") } returns Result.failure(error)
            
            // When
            viewModel.setEvent(UserEvent.LoadUser("1"))
            
            // Then
            viewModel.effect.test {
                val effect = awaitItem()
                effect shouldBe UserEffect.ShowError("Network error")
            }
        }
    }
})
```

### 4. Test Organization
- **Test packages**: Mirror main source package structure
- **Test utilities**: Shared test utilities in `common_test` module
- **Mock data**: Create reusable test fixtures
- **Test naming**: Use descriptive test names

## Logging Guidelines

### 1. Kermit Logger Usage
```kotlin
class YourClass {
    private val logger = Logger.withTag("YourClass")
    
    fun performOperation() {
        logger.d { "Starting operation" }
        
        try {
            // Operation logic
            logger.i { "Operation completed successfully" }
        } catch (e: Exception) {
            logger.e(e) { "Operation failed: ${e.message}" }
        }
    }
}
```

### 2. Log Levels
- **Debug (d)**: Detailed information for debugging
- **Info (i)**: General information about app flow
- **Warning (w)**: Potentially harmful situations
- **Error (e)**: Error events that might still allow app to continue

### 3. Logging Best Practices
- **Structured logging**: Use consistent log message formats
- **Sensitive data**: Never log sensitive information (passwords, tokens)
- **Performance**: Use lazy evaluation for log messages
- **Tags**: Use meaningful tags for filtering

## Error Handling

### 1. Result Pattern
```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

// Usage
suspend fun fetchUser(id: String): Result<User> {
    return try {
        val user = apiService.getUser(id)
        Result.Success(user)
    } catch (e: Exception) {
        Result.Error(e)
    }
}
```

### 2. Exception Handling
- **Specific exceptions**: Catch specific exceptions when possible
- **Logging**: Always log exceptions with context
- **User feedback**: Provide meaningful error messages to users
- **Recovery**: Implement retry mechanisms where appropriate

## Performance Guidelines

### 1. Coroutines Best Practices
- **Structured concurrency**: Use proper coroutine scopes
- **Cancellation**: Handle coroutine cancellation properly
- **Dispatchers**: Use appropriate dispatchers for different tasks
  - `Dispatchers.Main`: UI updates
  - `Dispatchers.IO`: Network/database operations
  - `Dispatchers.Default`: CPU-intensive work

### 2. Memory Management
- **Lifecycle awareness**: Use lifecycle-aware components
- **Resource cleanup**: Properly dispose of resources
- **Memory leaks**: Avoid holding references to contexts

### 3. Database Optimization
- **Indexing**: Add appropriate database indexes
- **Batch operations**: Use batch operations for multiple inserts/updates
- **Pagination**: Implement pagination for large datasets

## CI/CD Guidelines

### 1. GitHub Actions Workflow
The project uses GitHub Actions for continuous integration:

```yaml
name: Run tests
on:
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle
      - name: Run tests with coverage
        run: ./gradlew jacocoTestReport
      - name: Check test coverage
        run: ./scripts/check_coverage.sh
```

### 2. Code Quality Checks
- **Test coverage**: Maintain minimum coverage thresholds
- **Static analysis**: Use lint tools and static analysis
- **Code review**: Require code reviews for all changes
- **Automated testing**: Run tests on every pull request

### 3. Deployment Process
- **Staging**: Deploy to staging environment first
- **Testing**: Run integration tests in staging
- **Production**: Deploy to production after approval
- **Rollback**: Have rollback procedures ready

## Documentation Standards

### 1. Code Documentation
- **KDoc**: Use KDoc for public APIs
- **Inline comments**: Explain complex logic
- **README files**: Maintain README files for modules
- **Architecture decisions**: Document important architectural decisions

### 2. API Documentation
- **OpenAPI**: Document REST APIs with OpenAPI/Swagger
- **Examples**: Provide usage examples
- **Error codes**: Document error responses
- **Versioning**: Document API versioning strategy

## Security Guidelines

### 1. Data Protection
- **Encryption**: Encrypt sensitive data at rest and in transit
- **Authentication**: Implement proper authentication mechanisms
- **Authorization**: Use role-based access control
- **Input validation**: Validate all user inputs

### 2. Network Security
- **HTTPS**: Use HTTPS for all network communications
- **Certificate pinning**: Implement certificate pinning for critical APIs
- **API keys**: Secure API keys and tokens
- **Rate limiting**: Implement rate limiting for APIs

## Version Control Guidelines

### 1. Git Workflow
- **Feature branches**: Use feature branches for development
- **Pull requests**: Use pull requests for code review
- **Commit messages**: Write clear, descriptive commit messages
- **Branch naming**: Use consistent branch naming conventions

### 2. Commit Message Format
```
type(scope): description

[optional body]

[optional footer]
```

Types: feat, fix, docs, style, refactor, test, chore

### 3. Release Management
- **Semantic versioning**: Use semantic versioning (MAJOR.MINOR.PATCH)
- **Release notes**: Maintain detailed release notes
- **Tagging**: Tag releases in version control
- **Changelog**: Keep an updated changelog

Following these guidelines ensures consistent, maintainable, and high-quality code across the entire project.