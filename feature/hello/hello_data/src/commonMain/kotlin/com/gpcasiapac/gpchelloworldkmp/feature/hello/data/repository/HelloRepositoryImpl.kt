package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.repository

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSource
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.mapper.toDomain
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.mapper.toDto
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class HelloRepositoryImpl(
    private val networkDataSource: HelloNetworkDataSource
) : HelloRepository {
    
    override suspend fun fetchHelloMessage(name: String, language: String): DataResult<HelloMessage> {
        return when (val result = networkDataSource.getHelloMessageDto(name, language)) {
            is DataResult.Success -> {
                try {
                    DataResult.Success(result.data.toDomain())
                } catch (e: Exception) {
                    DataResult.Error.Client.Mapping(
                        message = "Failed to map hello message",
                        throwable = e
                    )
                }
            }
            is DataResult.Error -> result // Pass through the error from network layer
        }
    }
    
    override suspend fun fetchRandomGreeting(): DataResult<HelloMessage> {
        return when (val result = networkDataSource.getRandomGreetingDto()) {
            is DataResult.Success -> {
                try {
                    DataResult.Success(result.data.toDomain())
                } catch (e: Exception) {
                    DataResult.Error.Client.Mapping(
                        message = "Failed to map random greeting",
                        throwable = e
                    )
                }
            }
            is DataResult.Error -> result
        }
    }
    
    override suspend fun postHelloMessage(helloMessage: HelloMessage): DataResult<HelloMessage> {
        return try {
            val helloMessageDto = helloMessage.toDto()
            when (val result = networkDataSource.postHelloMessage(helloMessageDto)) {
                is DataResult.Success -> {
                    try {
                        DataResult.Success(result.data.toDomain())
                    } catch (e: Exception) {
                        DataResult.Error.Client.Mapping(
                            message = "Failed to map posted hello message",
                            throwable = e
                        )
                    }
                }
                is DataResult.Error -> result
            }
        } catch (e: Exception) {
            DataResult.Error.Client.UnexpectedError(
                message = "Failed to convert hello message to DTO",
                throwable = e
            )
        }
    }
}