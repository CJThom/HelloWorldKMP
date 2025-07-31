package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource

import kotlinx.coroutines.delay
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto.HelloMessageDto

class HelloNetworkDataSourceImpl : HelloNetworkDataSource {
    
    private val greetings = listOf(
        "Hello", "Hi", "Hey", "Greetings", "Welcome", 
        "Good day", "Howdy", "Salutations"
    )
    
    override suspend fun getHelloMessageDto(name: String, language: String): DataResult<HelloMessageDto> {
        return try {
            // Simulate network delay
            delay(1000)
            
            // Simulate occasional network errors for demo
            if (name.lowercase() == "error") {
                return DataResult.Error.Network.ConnectionError(
                    message = "Failed to connect to greeting service"
                )
            }
            
            val message = when (language) {
                "es" -> "¡Hola, $name! ¡Bienvenido a KMP!"
                "fr" -> "Bonjour, $name! Bienvenue à KMP!"
                "de" -> "Hallo, $name! Willkommen bei KMP!"
                else -> "Hello, $name! Welcome to KMP!"
            }
            
            DataResult.Success(
                HelloMessageDto(
                    message = message,
                    timestamp = System.currentTimeMillis(),
                    language = language
                )
            )
        } catch (e: Exception) {
            DataResult.Error.Network.UnknownError(
                throwable = e,
                message = "Unexpected error occurred: ${e.message}"
            )
        }
    }
    
    override suspend fun getRandomGreetingDto(): DataResult<HelloMessageDto> {
        return try {
            delay(800)
            
            val randomGreeting = greetings.random()
            val message = "$randomGreeting from KMP!"
            
            DataResult.Success(
                HelloMessageDto(
                    message = message,
                    timestamp = System.currentTimeMillis(),
                    language = "en"
                )
            )
        } catch (e: Exception) {
            DataResult.Error.Network.UnknownError(
                throwable = e,
                message = "Failed to fetch random greeting: ${e.message}"
            )
        }
    }
}