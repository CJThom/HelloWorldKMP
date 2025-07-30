package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage

interface HelloRepository {
    suspend fun getHelloMessage(name: String, language: String = "en"): DataResult<HelloMessage>
    suspend fun getRandomGreeting(): DataResult<HelloMessage>
}