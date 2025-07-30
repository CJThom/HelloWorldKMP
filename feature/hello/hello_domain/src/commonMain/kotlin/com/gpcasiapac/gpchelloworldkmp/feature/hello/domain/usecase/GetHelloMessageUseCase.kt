package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class GetHelloMessageUseCase(
    private val helloRepository: HelloRepository
) {
    suspend operator fun invoke(name: String): DataResult<HelloMessage> {
        val cleanName = name.trim().ifEmpty { "World" }
        return helloRepository.getHelloMessage(cleanName)
    }
}