package com.gpcasiapac.gpchelloworldkmp.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel implementing the MVI (Model-View-Intent) pattern.
 * 
 * @param Event The type of events this ViewModel handles
 * @param State The type of UI state this ViewModel manages
 * @param Effect The type of side effects this ViewModel can emit
 */
abstract class MVIViewModel<Event : ViewEvent, State : ViewState, Effect : ViewSideEffect> : ViewModel() {

    // Current state of the UI
    private val _uiState = MutableStateFlow(setInitialState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    // Current state value for internal use
    val currentState: State
        get() = uiState.value

    // Channel for one-time side effects
    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    /**
     * Set the initial state of the ViewModel.
     * This method is called once when the ViewModel is created.
     */
    abstract fun setInitialState(): State

    /**
     * Handle incoming events from the UI.
     * This method should contain the business logic for processing events.
     */
    abstract fun handleEvents(event: Event)

    /**
     * Called when the ViewModel is started.
     * Override this method to perform initialization logic.
     */
    open fun onStart() {
        // Default implementation does nothing
    }

    /**
     * Send an event to be processed by the ViewModel.
     */
    fun setEvent(event: Event) {
        viewModelScope.launch {
            handleEvents(event)
        }
    }

    /**
     * Update the current state.
     */
    protected fun setState(reducer: State.() -> State) {
        val newState = currentState.reducer()
        _uiState.value = newState
    }

    /**
     * Emit a one-time side effect.
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }
}