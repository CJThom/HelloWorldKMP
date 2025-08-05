package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto.HelloMessageDto
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage

fun HelloMessage.toDto(): HelloMessageDto {
    return HelloMessageDto(
        message = this.text,
        timestamp = this.timestamp,
        language = this.language
    )
}