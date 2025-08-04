package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource

import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto.HelloMessageDto
import com.gpcasiapac.gpchelloworldkmp.config.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

@Serializable
data class HelloMessageRequest(
    val name: String,
    val language: String
)

@Serializable
data class PostMessageRequest(
    val message: String,
    val language: String
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val code: Int? = null
)

class HelloNetworkDataSourceImpl(
    private val httpClient: HttpClient
) : HelloNetworkDataSource {
    
    companion object {
        private const val API_PATH = "/api/v1"
        private const val HELLO_ENDPOINT = "$API_PATH/hello"
        private const val RANDOM_GREETING_ENDPOINT = "$API_PATH/greetings/random"
        private const val POST_MESSAGE_ENDPOINT = "$API_PATH/messages"
    }
    
    
    // Real HTTP client implementation using Ktor
    private suspend fun makeHttpRequest(
        endpoint: String,
        method: String = "GET",
        body: Any? = null
    ): DataResult<HelloMessageDto> {
        return try {
            val response = when (method.uppercase()) {
                "GET" -> httpClient.get(endpoint)
                "POST" -> httpClient.post(endpoint) {
                    contentType(ContentType.Application.Json)
                    if (body != null) {
                        setBody(body)
                    }
                }
                else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<HelloMessageDto>()
                    DataResult.Success(responseBody)
                }
                HttpStatusCode.BadRequest -> {
                    DataResult.Error.Network.HttpError(
                        code = response.status.value,
                        message = "Bad Request: ${response.bodyAsText()}"
                    )
                }
                HttpStatusCode.InternalServerError -> {
                    DataResult.Error.Network.HttpError(
                        code = response.status.value,
                        message = "Internal Server Error: ${response.bodyAsText()}"
                    )
                }
                else -> {
                    DataResult.Error.Network.HttpError(
                        code = response.status.value,
                        message = "HTTP Error: ${response.status.description}"
                    )
                }
            }
        } catch (e: Exception) {
            DataResult.Error.Network.UnknownError(
                throwable = e,
                message = "Network request failed: ${e.message}"
            )
        }
    }
    
    override suspend fun getHelloMessageDto(name: String, language: String): DataResult<HelloMessageDto> {
        // Simulate error trigger for demo purposes
        if (name.lowercase() == "error") {
            return DataResult.Error.Network.ConnectionError(
                message = "Failed to connect to greeting service"
            )
        }
        
        val request = HelloMessageRequest(name = name, language = language)
        return makeHttpRequest(
            endpoint = HELLO_ENDPOINT,
            method = "POST",
            body = request
        )
    }
    
    override suspend fun getRandomGreetingDto(): DataResult<HelloMessageDto> {
        return makeHttpRequest(
            endpoint = RANDOM_GREETING_ENDPOINT,
            method = "GET"
        )
    }
    
    override suspend fun postHelloMessage(helloMessageDto: HelloMessageDto): DataResult<HelloMessageDto> {
        val request = PostMessageRequest(
            message = helloMessageDto.message,
            language = helloMessageDto.language
        )
        return makeHttpRequest(
            endpoint = POST_MESSAGE_ENDPOINT,
            method = "POST",
            body = request
        )
    }
}