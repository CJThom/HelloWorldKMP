package com.gpcasiapac.gpchelloworldkmp.feature.login.impl.service

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginService
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.User
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository.LoginRepository
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.GetCurrentUserUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.IsLoggedInUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LoginUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LogoutUseCase

class LoginServiceImpl(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUser: GetCurrentUserUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : LoginService {

    override suspend fun login(username: String, password: String): LoginUseCase.UseCaseResult {
        return loginUseCase(username, password)
    }

    override suspend fun logout(): LogoutUseCase.UseCaseResult {
        return logoutUseCase()
    }

    override suspend fun getCurrentUser(): DataResult<User> {
        return getCurrentUser()
    }

    override suspend fun isLoggedIn(): Boolean {
        return isLoggedInUseCase()
    }
}
