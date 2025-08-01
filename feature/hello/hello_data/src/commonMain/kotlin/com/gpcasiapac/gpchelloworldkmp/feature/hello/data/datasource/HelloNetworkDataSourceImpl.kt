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
    
    override suspend fun postHelloMessage(helloMessageDto: HelloMessageDto): DataResult<HelloMessageDto> {
        return try {
            // Simulate network delay for POST request
            delay(1200)
            
            // Simulate validation - reject messages that are too short
            if (helloMessageDto.message.length < 3) {
                return DataResult.Error.Network.HttpError(
                    code = 400,
                    message = "Bad Request: Message is too short. Minimum 3 characters required."
                )
            }
            
            // Simulate server error for specific test case
            if (helloMessageDto.message.lowercase().contains("servererror")) {
                return DataResult.Error.Network.HttpError(
                    code = 500,
                    message = "Internal Server Error: Failed to process hello message"
                )
            }
            
            // Simulate successful POST - return the message with updated timestamp
            val postedMessage = helloMessageDto.copy(
                timestamp = System.currentTimeMillis()
            )
            
            DataResult.Success(postedMessage)
        } catch (e: Exception) {
            DataResult.Error.Network.UnknownError(
                throwable = e,
                message = "Failed to post hello message: ${e.message}"
            )
        }
    }
}