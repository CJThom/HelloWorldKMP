package com.gpcasiapac.gpchelloworldkmp.feature.login.data.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.LoginResponseDto
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository.LoginResult

fun LoginResponseDto.toDomain(): LoginResult {
    return LoginResult(
        user = user.toDomain(),
        token = token.toDomain()
    )
}