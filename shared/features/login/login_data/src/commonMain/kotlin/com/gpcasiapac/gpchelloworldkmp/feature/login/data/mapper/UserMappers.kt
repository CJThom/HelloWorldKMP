package com.gpcasiapac.gpchelloworldkmp.feature.login.data.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.login.data.dto.UserDto
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        isActive = isActive,
        createdAt = createdAt,
        lastLoginAt = lastLoginAt
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        isActive = isActive,
        createdAt = createdAt,
        lastLoginAt = lastLoginAt
    )
}