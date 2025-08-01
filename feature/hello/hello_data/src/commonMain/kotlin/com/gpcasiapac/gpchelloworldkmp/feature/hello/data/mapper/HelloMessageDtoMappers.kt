package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto.HelloMessageDto
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage

fun HelloMessageDto.toDomain(): HelloMessage {
    return HelloMessage(
        text = this.message,
        timestamp = this.timestamp,
        language = this.language
    )
}