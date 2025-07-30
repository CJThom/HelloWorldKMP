package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

class GetRandomGreetingUseCase(
    private val helloRepository: HelloRepository
) {
    suspend operator fun invoke(): DataResult<HelloMessage> {
        return helloRepository.getRandomGreeting()
    }
}