package com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository.LoginRepository

class IsLoggedInUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Boolean {
        return loginRepository.getCurrentUser().let { result ->
            when (result) {
                is com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult.Success -> true
                is com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult.Error -> false
            }
        }
    }
}
