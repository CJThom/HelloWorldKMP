package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class GetHelloMessageUseCase(
    private val helloRepository: HelloRepository
) {
    suspend operator fun invoke(name: String): UseCaseResult {
        val cleanName = name.trim().ifEmpty { "World" }
        
        // Business logic validation
        if (cleanName.length > 100) {
            return UseCaseResult.Error.InvalidName
        }
        
        return when (val result = helloRepository.fetchHelloMessage(cleanName)) {
            is DataResult.Success -> {
                // Business logic validation
                if (result.data.text.isBlank()) {
                    UseCaseResult.Error.MessageGenerationFailed
                } else {
                    UseCaseResult.Success(helloMessage = result.data)
                }
            }
            is DataResult.Error.Client.Database -> {
                UseCaseResult.Error.MessageUnavailable
            }
            is DataResult.Error.Client.Mapping -> {
                UseCaseResult.Error.MessageUnavailable
            }
            is DataResult.Error.Network.ConnectionError -> {
                UseCaseResult.Error.ServiceUnavailable
            }
            is DataResult.Error.Network.HttpError -> {
                when (result.code) {
                    400 -> UseCaseResult.Error.InvalidName
                    404 -> UseCaseResult.Error.MessageUnavailable
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
            data object InvalidName : Error("Name is too long or contains invalid characters. Please use a shorter name.")
            data object MessageGenerationFailed : Error("Failed to generate hello message. Please try again.")
            data object MessageUnavailable : Error("Unable to access message data. Please try again.")
            data object ServiceUnavailable : Error("Hello message service is currently unavailable. Please try again later.")
        }
    }
}