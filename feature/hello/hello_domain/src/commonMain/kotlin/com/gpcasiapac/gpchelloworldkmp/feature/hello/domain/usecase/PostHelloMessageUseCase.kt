package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class PostHelloMessageUseCase(
    private val helloRepository: HelloRepository
) {
    suspend operator fun invoke(helloMessage: HelloMessage): UseCaseResult {
        // Validate input - ensure message is not empty
        val cleanMessage = helloMessage.text.trim()
        if (cleanMessage.isEmpty()) {
            return UseCaseResult.Error.Validation("Message cannot be empty")
        }
        
        // Create validated hello message
        val validatedMessage = helloMessage.copy(text = cleanMessage)
        
        return when (val result = helloRepository.postHelloMessage(validatedMessage)) {
            is DataResult.Success -> {
                UseCaseResult.Success(helloMessage = result.data)
            }
            is DataResult.Error.Client -> {
                UseCaseResult.Error.Client
            }
            is DataResult.Error.Network -> {
                UseCaseResult.Error.Network
            }
        }
    }
    
    sealed interface UseCaseResult {
        data class Success(val helloMessage: HelloMessage) : UseCaseResult
        sealed class Error(val message: String) : UseCaseResult {
            data object Client : Error("Client error occurred")
            data object Network : Error("Network error occurred")
            data class Validation(val validationMessage: String) : Error(validationMessage)
        }
    }
}