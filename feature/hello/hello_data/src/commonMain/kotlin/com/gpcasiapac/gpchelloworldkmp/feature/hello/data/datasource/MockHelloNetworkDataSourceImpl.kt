package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource

import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto.HelloMessageDto
import com.gpcasiapac.gpchelloworldkmp.common.data.json.loadJsonResource

@Serializable
data class HelloMessageTemplate(
    val language: String,
    val messageTemplate: String,
    val responses: List<String>
)

@Serializable
data class MockDataConfig(
    val helloMessages: List<HelloMessageTemplate>,
    val randomGreetings: List<String>
)

class MockHelloNetworkDataSourceImpl : HelloNetworkDataSource {
    
    override suspend fun getHelloMessageDto(name: String, language: String): DataResult<HelloMessageDto> {
        // Load JSON data like HTTP response deserialization
        val mockData = loadJsonResource<MockDataConfig>("mock_hello_messages.json")
        
        delay(800) // Similar to network delay
        
        val messageTemplate = mockData.helloMessages.find { it.language == language }
            ?: mockData.helloMessages.find { it.language == "en" }
            ?: mockData.helloMessages.first()
        
        val message = messageTemplate.messageTemplate.replace("{name}", name)
        
        return DataResult.Success(
            HelloMessageDto(
                message = message,
                timestamp = System.currentTimeMillis(),
                language = language
            )
        )
    }
    
    override suspend fun getRandomGreetingDto(): DataResult<HelloMessageDto> {
        // Load JSON data like HTTP response deserialization
        val mockData = loadJsonResource<MockDataConfig>("mock_hello_messages.json")
        
        delay(600) // Similar to network delay
        
        val randomGreeting = mockData.randomGreetings.random()
        
        return DataResult.Success(
            HelloMessageDto(
                message = randomGreeting,
                timestamp = System.currentTimeMillis(),
                language = "en"
            )
        )
    }
    
    override suspend fun postHelloMessage(helloMessageDto: HelloMessageDto): DataResult<HelloMessageDto> {
        delay(1000) // Similar to network delay for POST
        
        // Return updated message like a real POST response
        val postedMessage = helloMessageDto.copy(
            timestamp = System.currentTimeMillis()
        )
        
        return DataResult.Success(postedMessage)
    }
}