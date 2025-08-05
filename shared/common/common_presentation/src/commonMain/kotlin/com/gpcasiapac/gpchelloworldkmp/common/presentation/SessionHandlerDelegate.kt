package com.gpcasiapac.gpchelloworldkmp.common.presentation

import com.gpcasiapac.gpchelloworldkmp.common.domain.SessionIds
import kotlinx.coroutines.flow.StateFlow

/**
 * Delegate interface for handling session state in ViewModels.
 * ViewModels can implement this interface by delegation to manage session state.
 */
interface SessionHandlerDelegate<Session : SessionIds> {
    val sessionState: StateFlow<Session>
}