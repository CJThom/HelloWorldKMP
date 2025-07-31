package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class GetRandomGreetingUseCase(
    private val helloRepository: HelloRepository
) {
    suspend operator fun invoke(): UseCaseResult {
        return when (val result = helloRepository.fetchRandomGreeting()) {
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
        }
    }
}