package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageParams
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal fun HelloMessage.toParams(): HelloMessageParams {
    val instant = Instant.fromEpochMilliseconds(this.timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedTime = "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
    
    return HelloMessageParams(
        text = this.text,
        language = this.language.uppercase(),
        formattedTimestamp = formattedTime
    )
}