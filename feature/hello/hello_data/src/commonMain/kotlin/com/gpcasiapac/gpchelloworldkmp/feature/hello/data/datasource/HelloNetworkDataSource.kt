package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto.HelloMessageDto

interface HelloNetworkDataSource {

    suspend fun getHelloMessageDto(name: String, language: String): DataResult<HelloMessageDto>

    suspend fun getRandomGreetingDto(): DataResult<HelloMessageDto>

    suspend fun postHelloMessage(helloMessageDto: HelloMessageDto): DataResult<HelloMessageDto>

}