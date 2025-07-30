# Key Patterns and Conventions

## Overview
This document outlines the specific patterns, conventions, and best practices used throughout the HouseHelper project. Following these ensures consistency and maintainability.

## Naming Conventions

### 1. Module Naming
- **Pattern**: `[module_type]_[module_name]`
- **Examples**:
  - `common_presentation`
  - `core_domain`
  - `messaging_data`
  - `shoppinglist_presentation`

### 2. Package Naming
- **Pattern**: `org.company.project.[module_type].[module_name].[layer]`
- **Examples**:
  - `org.cjthomson.househelper.common.presentation`
  - `org.cjthomson.househelper.feature.messaging.domain`
  - `org.cjthomson.househelper.core.data`

### 3. Class Naming Conventions

#### ViewModels
- **Pattern**: `[Feature]ViewModel`
- **Examples**: `MessageViewModel`, `ShoppingListViewModel`

#### Use Cases
- **Pattern**: `[Action][Entity]UseCase`
- **Examples**: `GetMessagesUseCase`, `SaveShoppingItemUseCase`

#### Repositories
- **Interface Pattern**: `[Entity]Repository`
- **Implementation Pattern**: `[Entity]RepositoryImpl`
- **Examples**: `MessageRepository`, `MessageRepositoryImpl`

#### Data Classes
- **Domain Models**: `[Entity]` (e.g., `Message`, `ShoppingItem`)
- **DTOs**: `[Entity]Dto` (e.g., `MessageDto`, `ShoppingItemDto`)
- **Entities**: `[Entity]Entity` (e.g., `MessageEntity`, `ShoppingItemEntity`)

#### MVI Components
- **Events**: `[Feature]Event` (e.g., `MessageEvent`, `ShoppingListEvent`)
- **States**: `[Feature]State` (e.g., `MessageState`, `ShoppingListState`)
- **Effects**: `[Feature]Effect` (e.g., `MessageEffect`, `ShoppingListEffect`)

## MVI Pattern Implementation

### 1. Event Definition Pattern
```kotlin
sealed interface FeatureEvent : ViewEvent {
    // User actions
    data object LoadData : FeatureEvent
    data object RefreshData : FeatureEvent
    
    // User interactions with data
    data class SelectItem(val id: String) : FeatureEvent
    data class UpdateItem(val item: DomainModel) : FeatureEvent
    data class DeleteItem(val id: String) : FeatureEvent
    
    // Navigation events
    data object NavigateBack : FeatureEvent
    data class NavigateToDetail(val id: String) : FeatureEvent
}
```

### 2. State Definition Pattern
```kotlin
data class FeatureState(
    // Loading states
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    
    // Data
    val items: List<DomainModel> = emptyList(),
    val selectedItem: DomainModel? = null,
    
    // Error handling
    val error: String? = null,
    
    // UI state
    val searchQuery: String = "",
    val isSearchActive: Boolean = false
) : ViewState
```

### 3. Effect Definition Pattern
```kotlin
sealed interface FeatureEffect : ViewSideEffect {
    // User feedback
    data class ShowError(val message: String) : FeatureEffect
    data class ShowSuccess(val message: String) : FeatureEffect
    data class ShowToast(val message: String) : FeatureEffect
    
    // Navigation
    data class NavigateToScreen(val route: String) : FeatureEffect
    data object NavigateBack : FeatureEffect
    
    // System interactions
    data class ShareContent(val content: String) : FeatureEffect
    data class OpenUrl(val url: String) : FeatureEffect
}
```

### 4. ViewModel Implementation Pattern
```kotlin
class FeatureViewModel(
    private val useCase1: UseCase1,
    private val useCase2: UseCase2
) : MVIViewModel<FeatureEvent, FeatureState, FeatureEffect>() {

    private val logger = Logger.withTag("FeatureViewModel")

    override fun setInitialState(): FeatureState = FeatureState()

    override fun onStart() {
        setEvent(FeatureEvent.LoadData)
    }

    override fun handleEvents(event: FeatureEvent) {
        when (event) {
            is FeatureEvent.LoadData -> loadData()
            is FeatureEvent.RefreshData -> refreshData()
            is FeatureEvent.SelectItem -> selectItem(event.id)
            // Handle other events...
        }
    }

    private fun loadData() {
        logger.d { "Loading data" }
        setState { copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            useCase1().fold(
                onSuccess = { data ->
                    setState { copy(isLoading = false, items = data) }
                },
                onFailure = { error ->
                    logger.e(error) { "Failed to load data" }
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect { FeatureEffect.ShowError(error.message ?: "Unknown error") }
                }
            )
        }
    }
}
```

## Clean Architecture Patterns

### 1. Domain Layer Patterns

#### Use Case Pattern
```kotlin
class GetItemsUseCase(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(): Result<List<Item>> {
        return try {
            val items = repository.getItems()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// For use cases with parameters
class GetItemByIdUseCase(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(id: String): Result<Item> {
        return try {
            val item = repository.getItemById(id)
                ?: return Result.failure(Exception("Item not found"))
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

#### Repository Interface Pattern
```kotlin
interface ItemRepository {
    // Suspend functions for one-time operations
    suspend fun getItems(): List<Item>
    suspend fun getItemById(id: String): Item?
    suspend fun saveItem(item: Item)
    suspend fun deleteItem(id: String)
    
    // Flow functions for reactive data
    fun observeItems(): Flow<List<Item>>
    fun observeItemById(id: String): Flow<Item?>
}
```

### 2. Data Layer Patterns

#### Repository Implementation Pattern
```kotlin
class ItemRepositoryImpl(
    private val localDataSource: ItemLocalDataSource,
    private val remoteDataSource: ItemRemoteDataSource
) : ItemRepository {

    override suspend fun getItems(): List<Item> {
        return try {
            // Try remote first for fresh data
            val remoteItems = remoteDataSource.getItems()
            val domainItems = remoteItems.map { it.toDomain() }
            
            // Cache locally
            localDataSource.saveItems(domainItems.map { it.toEntity() })
            
            domainItems
        } catch (e: Exception) {
            // Fallback to local data
            localDataSource.getItems().map { it.toDomain() }
        }
    }

    override fun observeItems(): Flow<List<Item>> {
        return localDataSource.observeItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
```

#### Mapper Pattern
```kotlin
// Extension functions for mapping between layers
fun ItemDto.toDomain(): Item = Item(
    id = this.id,
    title = this.title,
    description = this.description,
    createdAt = Instant.fromEpochMilliseconds(this.createdAt)
)

fun Item.toDto(): ItemDto = ItemDto(
    id = this.id,
    title = this.title,
    description = this.description,
    createdAt = this.createdAt.toEpochMilliseconds()
)

fun ItemEntity.toDomain(): Item = Item(
    id = this.id,
    title = this.title,
    description = this.description,
    createdAt = Instant.fromEpochMilliseconds(this.createdAt)
)

fun Item.toEntity(): ItemEntity = ItemEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    createdAt = this.createdAt.toEpochMilliseconds()
)
```

## Dependency Injection Patterns

### 1. Module Organization Pattern
```kotlin
// Domain Module - Use cases and business logic
val featureDomainModule = module {
    factory { GetItemsUseCase(get()) }
    factory { SaveItemUseCase(get()) }
    factory { DeleteItemUseCase(get()) }
}

// Data Module - Repositories and data sources
val featureDataModule = module {
    single<ItemRepository> { ItemRepositoryImpl(get(), get()) }
    single { ItemLocalDataSource(get()) }
    single { ItemRemoteDataSource(get()) }
}

// Presentation Module - ViewModels
val featurePresentationModule = module {
    viewModel { ItemViewModel(get(), get(), get()) }
}
```

### 2. Platform-Specific Modules
```kotlin
// Android-specific dependencies
val androidModule = module {
    single { AndroidContext(androidContext()) }
    single { AndroidSpecificService() }
}

// iOS-specific dependencies (in iosMain)
val iosModule = module {
    single { IOSSpecificService() }
}
```

## Error Handling Patterns

### 1. Result Pattern Usage
```kotlin
// In Use Cases
suspend fun performOperation(): Result<Data> {
    return try {
        val data = repository.getData()
        Result.success(data)
    } catch (e: NetworkException) {
        Result.failure(NetworkError("Network unavailable"))
    } catch (e: DatabaseException) {
        Result.failure(DatabaseError("Database error"))
    } catch (e: Exception) {
        Result.failure(UnknownError("Unknown error occurred"))
    }
}

// In ViewModels
private fun handleResult(result: Result<Data>) {
    result.fold(
        onSuccess = { data ->
            setState { copy(data = data, isLoading = false, error = null) }
        },
        onFailure = { error ->
            logger.e(error) { "Operation failed" }
            setState { copy(isLoading = false, error = error.message) }
            setEffect { FeatureEffect.ShowError(error.message ?: "Unknown error") }
        }
    )
}
```

### 2. Custom Exception Types
```kotlin
sealed class AppException(message: String) : Exception(message)
class NetworkException(message: String) : AppException(message)
class DatabaseException(message: String) : AppException(message)
class ValidationException(message: String) : AppException(message)
class AuthenticationException(message: String) : AppException(message)
```

## Testing Patterns

### 1. ViewModel Testing Pattern
```kotlin
class FeatureViewModelTest : FreeSpec({
    
    "FeatureViewModel" - {
        val mockUseCase = mockk<GetItemsUseCase>()
        val viewModel = FeatureViewModel(mockUseCase)
        
        beforeEach {
            clearAllMocks()
        }
        
        "should load items on start" {
            // Given
            val expectedItems = listOf(createTestItem())
            coEvery { mockUseCase() } returns Result.success(expectedItems)
            
            // When & Then
            viewModel.viewState.test {
                val initialState = awaitItem()
                initialState.isLoading shouldBe true
                
                val loadedState = awaitItem()
                loadedState.isLoading shouldBe false
                loadedState.items shouldBe expectedItems
            }
        }
        
        "should handle error when loading fails" {
            // Given
            val error = Exception("Network error")
            coEvery { mockUseCase() } returns Result.failure(error)
            
            // When & Then
            viewModel.effect.test {
                val effect = awaitItem()
                effect shouldBe FeatureEffect.ShowError("Network error")
            }
        }
    }
})
```

### 2. Repository Testing Pattern
```kotlin
class ItemRepositoryTest : FreeSpec({
    
    "ItemRepository" - {
        val mockLocalDataSource = mockk<ItemLocalDataSource>()
        val mockRemoteDataSource = mockk<ItemRemoteDataSource>()
        val repository = ItemRepositoryImpl(mockLocalDataSource, mockRemoteDataSource)
        
        "should return remote data when available" {
            // Given
            val remoteItems = listOf(createTestItemDto())
            coEvery { mockRemoteDataSource.getItems() } returns remoteItems
            coEvery { mockLocalDataSource.saveItems(any()) } just Runs
            
            // When
            val result = repository.getItems()
            
            // Then
            result shouldHaveSize 1
            coVerify { mockLocalDataSource.saveItems(any()) }
        }
        
        "should fallback to local data when remote fails" {
            // Given
            val localItems = listOf(createTestItemEntity())
            coEvery { mockRemoteDataSource.getItems() } throws Exception("Network error")
            coEvery { mockLocalDataSource.getItems() } returns localItems
            
            // When
            val result = repository.getItems()
            
            // Then
            result shouldHaveSize 1
        }
    }
})
```

## Logging Patterns

### 1. Logger Usage Pattern
```kotlin
class SomeClass {
    private val logger = Logger.withTag("SomeClass")
    
    fun performOperation() {
        logger.d { "Starting operation" }
        
        try {
            // Operation logic
            logger.i { "Operation completed successfully" }
        } catch (e: Exception) {
            logger.e(e) { "Operation failed: ${e.message}" }
            throw e
        }
    }
    
    suspend fun performAsyncOperation() {
        logger.d { "Starting async operation" }
        
        val result = withContext(Dispatchers.IO) {
            // Async work
            logger.d { "Async work completed" }
            "result"
        }
        
        logger.i { "Async operation result: $result" }
    }
}
```

### 2. Structured Logging Pattern
```kotlin
// Use consistent log message formats
logger.d { "Loading items for user: $userId" }
logger.i { "Items loaded successfully: count=${items.size}" }
logger.w { "Slow operation detected: duration=${duration}ms" }
logger.e(exception) { "Failed to load items: userId=$userId, error=${exception.message}" }
```

## Compose UI Patterns

### 1. Screen Composable Pattern
```kotlin
@Composable
fun FeatureScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: FeatureViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FeatureEffect.NavigateBack -> onNavigateBack()
                is FeatureEffect.NavigateToDetail -> onNavigateToDetail(effect.id)
                is FeatureEffect.ShowError -> {
                    // Handle error display
                }
            }
        }
    }
    
    FeatureContent(
        state = state,
        onEvent = viewModel::setEvent
    )
}

@Composable
private fun FeatureContent(
    state: FeatureState,
    onEvent: (FeatureEvent) -> Unit
) {
    // UI implementation
}
```

### 2. State Handling Pattern
```kotlin
@Composable
fun LoadingStateHandler(
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    content: @Composable () -> Unit
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            ErrorContent(
                error = error,
                onRetry = onRetry
            )
        }
        else -> content()
    }
}
```

## Performance Patterns

### 1. Coroutine Usage Pattern
```kotlin
class SomeViewModel : MVIViewModel<Event, State, Effect>() {
    
    private fun performLongRunningTask() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            
            try {
                val result = withContext(Dispatchers.IO) {
                    // Long-running work on IO dispatcher
                    heavyOperation()
                }
                
                // Update UI on Main dispatcher
                setState { copy(isLoading = false, data = result) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message) }
            }
        }
    }
}
```

### 2. Flow Usage Pattern
```kotlin
class SomeRepository {
    
    fun observeData(): Flow<List<Item>> = flow {
        // Emit initial data
        emit(getInitialData())
        
        // Listen for updates
        dataSource.observeChanges()
            .map { it.toDomain() }
            .collect { emit(it) }
    }.flowOn(Dispatchers.IO)
}
```

These patterns ensure consistency, maintainability, and performance across the entire project architecture.