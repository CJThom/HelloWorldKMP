package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// Domain → State mapper
@OptIn(ExperimentalTime::class)
internal fun HelloMessage.toState(): HelloMessageState {
    val instant = Instant.fromEpochMilliseconds(this.timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedTime = "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
    
    return HelloMessageState(
        text = this.text,
        language = this.language.uppercase(),
        formattedTimestamp = formattedTime
    )
}