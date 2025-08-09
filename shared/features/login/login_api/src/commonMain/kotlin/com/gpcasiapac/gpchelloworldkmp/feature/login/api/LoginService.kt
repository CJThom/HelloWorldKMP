package com.gpcasiapac.gpchelloworldkmp.feature.login.api

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.User
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LoginUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LogoutUseCase

/**
 * Public, non-UI contract for Login feature. Other modules/apps can depend on this
 * to perform authentication-related operations without importing implementation.
 */
interface LoginService {
    suspend fun login(username: String, password: String): LoginUseCase.UseCaseResult
    suspend fun logout(): LogoutUseCase.UseCaseResult

    /** Retrieve the current user if authenticated */
    suspend fun getCurrentUser(): DataResult<User>

    /** Convenience helper */
    suspend fun isLoggedIn(): Boolean
}
