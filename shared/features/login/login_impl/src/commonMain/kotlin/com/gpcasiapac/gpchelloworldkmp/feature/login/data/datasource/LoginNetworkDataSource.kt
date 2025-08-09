package com.gpcasiapac.gpchelloworldkmp.feature.login.data.datasource

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.LoginRequestDto
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.LoginResponseDto
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.TokenDto
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.UserDto

interface LoginNetworkDataSource {

    suspend fun login(loginRequest: LoginRequestDto): DataResult<LoginResponseDto>

    suspend fun refreshToken(refreshToken: String): DataResult<TokenDto>

    suspend fun logout(): DataResult<Unit>

    suspend fun getCurrentUser(): DataResult<UserDto>

    suspend fun validateToken(token: String): DataResult<Boolean>

}
