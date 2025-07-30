# Code Templates and Snippets

## Overview
This document provides ready-to-use code templates for implementing the key architectural patterns found in the HouseHelper project. These templates can be used as starting points for creating similar functionality in new projects.

## Base Architecture Components

### 1. MVI Base Classes

#### MVIViewModel Base Class
```kotlin
package org.yourcompany.yourproject.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

interface ViewEvent
interface ViewState
interface ViewSideEffect

abstract class MVIViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> :
    ViewModel() {

    private val initialState: UiState by lazy { setInitialState() }
    abstract fun setInitialState(): UiState

    /**
     * By default, we don't wait for anything before onStart.
     * This makes it optional for ViewModels that do not require readiness checks.
     * ViewModels that need to wait can override this function.
     */
    protected open suspend fun awaitReadiness(): Boolean = true

    /**
     * Default timeout for readiness checks in milliseconds.
     * ViewModels can override this if they need a different timeout.
     */
    protected open val readinessTimeoutMillis: Long = 2000L

    private val _viewState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val viewState: StateFlow<UiState> = _viewState.onStart {
        val ready = awaitReadinessWithTimeout()
        if (ready) {
            onStart()
        } else {
            handleReadinessFailed()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = initialState
    )

    abstract fun onStart()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effect

    init {
        subscribeToEvents()
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        _viewState.update { currentState -> currentState.reducer() }
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    abstract fun handleEvents(event: Event)

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.emit(effectValue) }
    }

    /**
     * If awaitReadiness() returns false, handle that scenario here.
     * For example, show an error message or navigate away.
     */
    protected open fun handleReadinessFailed() {
        Logger.e("MVIViewModel: Readiness check failed")
        println("MVIViewModel: Readiness check failed")
        // By default, do nothing. Concrete ViewModels can override if needed.
        // Example:
        // setState { copy(errorMessage = "Initialization failed") }
        // setEffect { MyEffect.ShowError("Initialization failed") }
    }

    /**
     * Internal function to handle timeout in readiness checks.
     */
    private suspend fun awaitReadinessWithTimeout(): Boolean {
        return withTimeoutOrNull(readinessTimeoutMillis) {
            awaitReadiness()
        } ?: false
    }
}
```

#### Session Management Components
```kotlin
package org.yourcompany.yourproject.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.yourcompany.yourproject.common.domain.SessionIds

interface SessionHandlerDelegate<Session : SessionIds> {
    val sessionState: StateFlow<Session>
}

class SessionHandler<Session : SessionIds>(
    initialSession: Session,
    private val sessionFlow: Flow<Session>,
) : ViewModel(), SessionHandlerDelegate<Session> {
    // TODO: When koin allows for viewModelScope injection remove ViewModel() inheritance

    private val _sessionState: MutableStateFlow<Session> = MutableStateFlow(initialSession)
    override val sessionState: StateFlow<Session> = _sessionState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionFlow.collectLatest { session ->
                Logger.withTag("SessionHandler").d { session.toString() }
                _sessionState.update { session }
            }
        }
    }
}
```

#### Domain Base Interface
```kotlin
package org.yourcompany.yourproject.common.domain

interface SessionIds {
    val userId: String?
    val sessionId: String?
}
```

### 2. Feature Implementation Templates

#### Feature Domain Layer Template
```kotlin
// Domain Models
package org.yourcompany.yourproject.feature.yourfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class YourFeatureItem(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)

// Use Cases
package org.yourcompany.yourproject.feature.yourfeature.domain.usecase

import org.yourcompany.yourproject.feature.yourfeature.domain.model.YourFeatureItem
import org.yourcompany.yourproject.feature.yourfeature.domain.repository.YourFeatureRepository

class GetYourFeatureItemsUseCase(
    private val repository: YourFeatureRepository
) {
    suspend operator fun invoke(): Result<List<YourFeatureItem>> {
        return try {
            val items = repository.getItems()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class SaveYourFeatureItemUseCase(
    private val repository: YourFeatureRepository
) {
    suspend operator fun invoke(item: YourFeatureItem): Result<Unit> {
        return try {
            repository.saveItem(item)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Repository Interface
package org.yourcompany.yourproject.feature.yourfeature.domain.repository

import org.yourcompany.yourproject.feature.yourfeature.domain.model.YourFeatureItem
import kotlinx.coroutines.flow.Flow

interface YourFeatureRepository {
    suspend fun getItems(): List<YourFeatureItem>
    suspend fun getItemById(id: String): YourFeatureItem?
    suspend fun saveItem(item: YourFeatureItem)
    suspend fun deleteItem(id: String)
    fun observeItems(): Flow<List<YourFeatureItem>>
}
```

#### Feature Data Layer Template
```kotlin
// DTOs
package org.yourcompany.yourproject.feature.yourfeature.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class YourFeatureItemDto(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)

// Database Entity
package org.yourcompany.yourproject.feature.yourfeature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "your_feature_items")
data class YourFeatureItemEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)

// DAO
package org.yourcompany.yourproject.feature.yourfeature.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.yourcompany.yourproject.feature.yourfeature.data.local.entity.YourFeatureItemEntity

@Dao
interface YourFeatureItemDao {
    @Query("SELECT * FROM your_feature_items ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<YourFeatureItemEntity>>

    @Query("SELECT * FROM your_feature_items ORDER BY updatedAt DESC")
    suspend fun getAll(): List<YourFeatureItemEntity>

    @Query("SELECT * FROM your_feature_items WHERE id = :id")
    suspend fun getById(id: String): YourFeatureItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: YourFeatureItemEntity)

    @Delete
    suspend fun delete(item: YourFeatureItemEntity)

    @Query("DELETE FROM your_feature_items WHERE id = :id")
    suspend fun deleteById(id: String)
}

// Mappers
package org.yourcompany.yourproject.feature.yourfeature.data.mapper

import org.yourcompany.yourproject.feature.yourfeature.data.dto.YourFeatureItemDto
import org.yourcompany.yourproject.feature.yourfeature.data.local.entity.YourFeatureItemEntity
import org.yourcompany.yourproject.feature.yourfeature.domain.model.YourFeatureItem

fun YourFeatureItemDto.toDomain(): YourFeatureItem = YourFeatureItem(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun YourFeatureItem.toDto(): YourFeatureItemDto = YourFeatureItemDto(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun YourFeatureItemEntity.toDomain(): YourFeatureItem = YourFeatureItem(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun YourFeatureItem.toEntity(): YourFeatureItemEntity = YourFeatureItemEntity(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

// Repository Implementation
package org.yourcompany.yourproject.feature.yourfeature.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.yourcompany.yourproject.feature.yourfeature.data.local.dao.YourFeatureItemDao
import org.yourcompany.yourproject.feature.yourfeature.data.mapper.toDomain
import org.yourcompany.yourproject.feature.yourfeature.data.mapper.toEntity
import org.yourcompany.yourproject.feature.yourfeature.data.remote.YourFeatureApiService
import org.yourcompany.yourproject.feature.yourfeature.domain.model.YourFeatureItem
import org.yourcompany.yourproject.feature.yourfeature.domain.repository.YourFeatureRepository

class YourFeatureRepositoryImpl(
    private val localDao: YourFeatureItemDao,
    private val apiService: YourFeatureApiService
) : YourFeatureRepository {

    override suspend fun getItems(): List<YourFeatureItem> {
        return try {
            // Try to fetch from remote first
            val remoteItems = apiService.getItems()
            val domainItems = remoteItems.map { it.toDomain() }
            
            // Cache locally
            localDao.getAll().forEach { localDao.delete(it) }
            domainItems.forEach { localDao.insert(it.toEntity()) }
            
            domainItems
        } catch (e: Exception) {
            // Fallback to local data
            localDao.getAll().map { it.toDomain() }
        }
    }

    override suspend fun getItemById(id: String): YourFeatureItem? {
        return localDao.getById(id)?.toDomain()
    }

    override suspend fun saveItem(item: YourFeatureItem) {
        localDao.insert(item.toEntity())
        try {
            apiService.saveItem(item.toDto())
        } catch (e: Exception) {
            // Handle sync error - could implement retry logic
        }
    }

    override suspend fun deleteItem(id: String) {
        localDao.deleteById(id)
        try {
            apiService.deleteItem(id)
        } catch (e: Exception) {
            // Handle sync error
        }
    }

    override fun observeItems(): Flow<List<YourFeatureItem>> {
        return localDao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
```

#### Feature Presentation Layer Template
```kotlin
// MVI Components
package org.yourcompany.yourproject.feature.yourfeature.presentation

import org.yourcompany.yourproject.common.presentation.ViewEvent
import org.yourcompany.yourproject.common.presentation.ViewState
import org.yourcompany.yourproject.common.presentation.ViewSideEffect
import org.yourcompany.yourproject.feature.yourfeature.domain.model.YourFeatureItem

// Events
sealed interface YourFeatureEvent : ViewEvent {
    data object LoadItems : YourFeatureEvent
    data object RefreshItems : YourFeatureEvent
    data class SaveItem(val item: YourFeatureItem) : YourFeatureEvent
    data class DeleteItem(val id: String) : YourFeatureEvent
    data class SelectItem(val item: YourFeatureItem) : YourFeatureEvent
}

// State
data class YourFeatureState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val items: List<YourFeatureItem> = emptyList(),
    val selectedItem: YourFeatureItem? = null,
    val error: String? = null
) : ViewState

// Side Effects
sealed interface YourFeatureEffect : ViewSideEffect {
    data class ShowError(val message: String) : YourFeatureEffect
    data class ShowSuccess(val message: String) : YourFeatureEffect
    data class NavigateToDetail(val itemId: String) : YourFeatureEffect
    data object NavigateBack : YourFeatureEffect
}

// ViewModel
package org.yourcompany.yourproject.feature.yourfeature.presentation

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import org.yourcompany.yourproject.common.presentation.MVIViewModel
import org.yourcompany.yourproject.feature.yourfeature.domain.usecase.GetYourFeatureItemsUseCase
import org.yourcompany.yourproject.feature.yourfeature.domain.usecase.SaveYourFeatureItemUseCase

class YourFeatureViewModel(
    private val getItemsUseCase: GetYourFeatureItemsUseCase,
    private val saveItemUseCase: SaveYourFeatureItemUseCase
) : MVIViewModel<YourFeatureEvent, YourFeatureState, YourFeatureEffect>() {

    private val logger = Logger.withTag("YourFeatureViewModel")

    override fun setInitialState(): YourFeatureState = YourFeatureState()

    override fun onStart() {
        setEvent(YourFeatureEvent.LoadItems)
    }

    override fun handleEvents(event: YourFeatureEvent) {
        when (event) {
            is YourFeatureEvent.LoadItems -> loadItems()
            is YourFeatureEvent.RefreshItems -> refreshItems()
            is YourFeatureEvent.SaveItem -> saveItem(event.item)
            is YourFeatureEvent.DeleteItem -> deleteItem(event.id)
            is YourFeatureEvent.SelectItem -> selectItem(event.item)
        }
    }

    private fun loadItems() {
        logger.d { "Loading items" }
        setState { copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            getItemsUseCase().fold(
                onSuccess = { items ->
                    logger.d { "Loaded ${items.size} items" }
                    setState { 
                        copy(
                            isLoading = false, 
                            items = items,
                            error = null
                        ) 
                    }
                },
                onFailure = { error ->
                    logger.e(error) { "Failed to load items" }
                    setState { 
                        copy(
                            isLoading = false, 
                            error = error.message
                        ) 
                    }
                    setEffect { YourFeatureEffect.ShowError(error.message ?: "Unknown error") }
                }
            )
        }
    }

    private fun refreshItems() {
        setState { copy(isRefreshing = true) }
        loadItems()
        setState { copy(isRefreshing = false) }
    }

    private fun saveItem(item: YourFeatureItem) {
        viewModelScope.launch {
            saveItemUseCase(item).fold(
                onSuccess = {
                    setEffect { YourFeatureEffect.ShowSuccess("Item saved successfully") }
                    setEvent(YourFeatureEvent.LoadItems) // Refresh list
                },
                onFailure = { error ->
                    logger.e(error) { "Failed to save item" }
                    setEffect { YourFeatureEffect.ShowError(error.message ?: "Failed to save item") }
                }
            )
        }
    }

    private fun deleteItem(id: String) {
        // Implementation similar to saveItem
    }

    private fun selectItem(item: YourFeatureItem) {
        setState { copy(selectedItem = item) }
        setEffect { YourFeatureEffect.NavigateToDetail(item.id) }
    }
}
```

### 3. Dependency Injection Templates

#### Koin Module Templates
```kotlin
// Domain Module
package org.yourcompany.yourproject.feature.yourfeature.di

import org.koin.dsl.module
import org.yourcompany.yourproject.feature.yourfeature.domain.usecase.GetYourFeatureItemsUseCase
import org.yourcompany.yourproject.feature.yourfeature.domain.usecase.SaveYourFeatureItemUseCase

val yourFeatureDomainModule = module {
    factory { GetYourFeatureItemsUseCase(get()) }
    factory { SaveYourFeatureItemUseCase(get()) }
}

// Data Module
package org.yourcompany.yourproject.feature.yourfeature.di

import org.koin.dsl.module
import org.yourcompany.yourproject.feature.yourfeature.data.repository.YourFeatureRepositoryImpl
import org.yourcompany.yourproject.feature.yourfeature.domain.repository.YourFeatureRepository

val yourFeatureDataModule = module {
    single<YourFeatureRepository> { YourFeatureRepositoryImpl(get(), get()) }
}

// Presentation Module
package org.yourcompany.yourproject.feature.yourfeature.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.yourcompany.yourproject.feature.yourfeature.presentation.YourFeatureViewModel

val yourFeaturePresentationModule = module {
    viewModel { YourFeatureViewModel(get(), get()) }
}
```

### 4. Testing Templates

#### ViewModel Test Template
```kotlin
package org.yourcompany.yourproject.feature.yourfeature.presentation

import app.cash.turbine.test
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yourcompany.yourproject.feature.yourfeature.domain.model.YourFeatureItem
import org.yourcompany.yourproject.feature.yourfeature.domain.usecase.GetYourFeatureItemsUseCase

class YourFeatureViewModelTest : FreeSpec({
    
    "YourFeatureViewModel" - {
        val mockGetItemsUseCase = mockk<GetYourFeatureItemsUseCase>()
        val viewModel = YourFeatureViewModel(mockGetItemsUseCase, mockk())
        
        "should load items on start" {
            runTest {
                // Given
                val expectedItems = listOf(
                    YourFeatureItem("1", "Title 1", "Description 1", 0L, 0L),
                    YourFeatureItem("2", "Title 2", "Description 2", 0L, 0L)
                )
                coEvery { mockGetItemsUseCase() } returns Result.success(expectedItems)
                
                // When
                viewModel.viewState.test {
                    val initialState = awaitItem()
                    initialState.isLoading shouldBe true
                    
                    val loadedState = awaitItem()
                    loadedState.isLoading shouldBe false
                    loadedState.items shouldBe expectedItems
                    loadedState.error shouldBe null
                }
            }
        }
        
        "should handle error when loading fails" {
            runTest {
                // Given
                val error = Exception("Network error")
                coEvery { mockGetItemsUseCase() } returns Result.failure(error)
                
                // When & Then
                viewModel.effect.test {
                    val effect = awaitItem()
                    effect shouldBe YourFeatureEffect.ShowError("Network error")
                }
                
                viewModel.viewState.test {
                    val state = awaitItem()
                    state.error shouldBe "Network error"
                    state.isLoading shouldBe false
                }
            }
        }
    }
})
```

### 5. Compose UI Templates

#### Screen Composable Template
```kotlin
package org.yourcompany.yourproject.feature.yourfeature.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.yourcompany.yourproject.feature.yourfeature.presentation.*

@Composable
fun YourFeatureScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: YourFeatureViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is YourFeatureEffect.NavigateToDetail -> onNavigateToDetail(effect.itemId)
                is YourFeatureEffect.NavigateBack -> onNavigateBack()
                is YourFeatureEffect.ShowError -> {
                    // Handle error (show snackbar, etc.)
                }
                is YourFeatureEffect.ShowSuccess -> {
                    // Handle success message
                }
            }
        }
    }
    
    YourFeatureContent(
        state = state,
        onEvent = viewModel::setEvent
    )
}

@Composable
private fun YourFeatureContent(
    state: YourFeatureState,
    onEvent: (YourFeatureEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.items) { item ->
                    YourFeatureItemCard(
                        item = item,
                        onClick = { onEvent(YourFeatureEvent.SelectItem(item)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun YourFeatureItemCard(
    item: YourFeatureItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
```

## Usage Instructions

1. **Copy the base classes** (MVIViewModel, SessionHandler) to your `common_presentation` module
2. **Adapt the package names** to match your project structure
3. **Use the feature templates** as starting points for new features
4. **Customize the domain models** to match your business requirements
5. **Implement the data layer** with your specific data sources
6. **Create UI components** using the Compose templates
7. **Set up dependency injection** using the Koin module templates
8. **Add tests** using the testing templates

These templates provide a solid foundation for implementing the HouseHelper architecture patterns in any new project.