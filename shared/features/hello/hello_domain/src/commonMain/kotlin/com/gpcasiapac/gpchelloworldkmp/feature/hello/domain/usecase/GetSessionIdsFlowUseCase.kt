package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloSessionIds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use case that provides a flow of session IDs for the Hello feature.
 * In a real application, this would typically fetch session data from a repository
 * that connects to authentication services, local storage, etc.
 */
class GetSessionIdsFlowUseCase {
    
    operator fun invoke(): Flow<HelloSessionIds> = flow {
        // Emit initial empty session
        emit(HelloSessionIds())
        
        // Simulate session initialization delay
        delay(1500)
        
        // Simulate successful authentication/session establishment
        emit(
            HelloSessionIds(
                userId = "user_123",
                sessionId = "session_abc_456",
                userName = "Hello User",
                isAuthenticated = true
            )
        )
        
        // In a real app, this flow would continue to emit session updates
        // as the user's authentication state changes
    }
}