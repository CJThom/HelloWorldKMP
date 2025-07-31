package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage

interface HelloRepository {
    suspend fun fetchHelloMessage(name: String, language: String = "en"): DataResult<HelloMessage>
    suspend fun fetchRandomGreeting(): DataResult<HelloMessage>
}