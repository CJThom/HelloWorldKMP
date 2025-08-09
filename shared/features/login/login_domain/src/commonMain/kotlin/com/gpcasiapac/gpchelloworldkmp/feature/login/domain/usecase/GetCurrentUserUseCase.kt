package com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.User
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository.LoginRepository

class GetCurrentUserUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): DataResult<User> = loginRepository.getCurrentUser()
}
