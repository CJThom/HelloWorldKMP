package com.gpcasiapac.gpchelloworldkmp.feature.login.data.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.TokenDto
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.Token

fun TokenDto.toDomain(): Token {
    return Token(
        accessToken = accessToken,
        refreshToken = refreshToken,
        tokenType = tokenType,
        expiresIn = expiresIn,
        issuedAt = issuedAt,
        scope = scope
    )
}

fun Token.toDto(): TokenDto {
    return TokenDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        tokenType = tokenType,
        expiresIn = expiresIn,
        issuedAt = issuedAt,
        scope = scope
    )
}