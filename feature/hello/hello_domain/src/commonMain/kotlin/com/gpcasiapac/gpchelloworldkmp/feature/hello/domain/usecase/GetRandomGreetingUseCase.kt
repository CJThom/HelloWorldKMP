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
                // Business logic validation
                if (result.data.text.isBlank()) {
                    UseCaseResult.Error.EmptyGreeting
                } else {
                    UseCaseResult.Success(helloMessage = result.data)
                }
            }
            is DataResult.Error.Client.Database -> {
                UseCaseResult.Error.GreetingUnavailable
            }
            is DataResult.Error.Client.Mapping -> {
                UseCaseResult.Error.GreetingUnavailable
            }
            is DataResult.Error.Network.ConnectionError -> {
                UseCaseResult.Error.ServiceUnavailable
            }
            is DataResult.Error.Network.HttpError -> {
                when (result.code) {
                    404 -> UseCaseResult.Error.NoGreetingsFound
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
            data object EmptyGreeting : Error("Received an empty greeting. Please try again.")
            data object NoGreetingsFound : Error("No greetings are available at the moment.")
            data object GreetingUnavailable : Error("Unable to access greeting data. Please try again.")
            data object ServiceUnavailable : Error("Greeting service is currently unavailable. Please try again later.")
        }
    }
}