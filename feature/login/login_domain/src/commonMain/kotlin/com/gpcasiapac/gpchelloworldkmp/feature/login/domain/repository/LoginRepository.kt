package com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.Token
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.User

interface LoginRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResult>
    suspend fun refreshToken(refreshToken: String): DataResult<Token>
    suspend fun logout(): DataResult<Unit>
    suspend fun getCurrentUser(): DataResult<User>
    suspend fun validateToken(token: String): DataResult<Boolean>
}

data class LoginResult(
    val user: User,
    val token: Token
)