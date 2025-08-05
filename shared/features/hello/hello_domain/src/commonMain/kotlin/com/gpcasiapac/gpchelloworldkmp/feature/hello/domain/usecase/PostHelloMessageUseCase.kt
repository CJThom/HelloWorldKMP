package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class PostHelloMessageUseCase(
    private val helloRepository: HelloRepository
) {
    suspend operator fun invoke(helloMessage: HelloMessage): UseCaseResult {
        // Business logic validation
        val cleanMessage = helloMessage.text.trim()
        if (cleanMessage.isEmpty()) {
            return UseCaseResult.Error.EmptyMessage
        }
        
        if (cleanMessage.length > 500) {
            return UseCaseResult.Error.MessageTooLong
        }
        
        // Create validated hello message
        val validatedMessage = helloMessage.copy(text = cleanMessage)
        
        return when (val result = helloRepository.postHelloMessage(validatedMessage)) {
            is DataResult.Success -> {
                // Business logic validation
                if (result.data.text.isBlank()) {
                    UseCaseResult.Error.MessagePostFailed
                } else {
                    UseCaseResult.Success(helloMessage = result.data)
                }
            }
            is DataResult.Error.Client.Database -> {
                UseCaseResult.Error.MessagePostFailed
            }
            is DataResult.Error.Client.Mapping -> {
                UseCaseResult.Error.MessagePostFailed
            }
            is DataResult.Error.Network.ConnectionError -> {
                UseCaseResult.Error.ServiceUnavailable
            }
            is DataResult.Error.Network.HttpError -> {
                when (result.code) {
                    400 -> UseCaseResult.Error.EmptyMessage
                    413 -> UseCaseResult.Error.MessageTooLong
                    else -> UseCaseResult.Error.ServiceUnavailable
                }
            }
            is DataResult.Error -> {
                UseCaseResult.Error.ServiceUnavailable
            }
        }
    }
    
    sealed interface UseCaseResult {
        data class Success(val helloMessage: HelloMessage) : UseCaseResult
        
        sealed class Error(val message: String) : UseCaseResult {
            data object EmptyMessage : Error("Message cannot be empty. Please enter a message.")
            data object MessageTooLong : Error("Message is too long. Please use fewer than 500 characters.")
            data object MessagePostFailed : Error("Failed to post hello message. Please try again.")
            data object ServiceUnavailable : Error("Message posting service is currently unavailable. Please try again later.")
        }
    }
}