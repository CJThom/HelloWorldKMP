package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.DetailedMessageParams
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// Domain → State mapper
@OptIn(ExperimentalTime::class)
internal fun HelloMessage.toMessageState(): HelloMessageState {
    val instant = Instant.fromEpochMilliseconds(this.timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedTime = "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
    
    return HelloMessageState(
        text = this.text,
        language = this.language.uppercase(),
        formattedTimestamp = formattedTime
    )
}

// State → Params mapper (for components with 4+ properties)
internal fun HelloMessageState.toDetailedParams(timestamp: Long): DetailedMessageParams {
    return DetailedMessageParams(
        text = this.text,
        language = this.language,
        formattedTimestamp = this.formattedTimestamp,
        messageId = "msg_$timestamp",
        isFromCurrentUser = false // Default to false, could be determined by business logic
    )
}

// Legacy mapper for backward compatibility - now uses the new flow
@OptIn(ExperimentalTime::class)
internal fun HelloMessage.toDetailedParams(): DetailedMessageParams {
    return this.toMessageState().toDetailedParams(this.timestamp)
}

// Simple mapper for components with ≤4 params - now returns State directly
@OptIn(ExperimentalTime::class)
internal fun HelloMessage.toSimpleState(): HelloMessageState {
    return this.toMessageState()
}

// Legacy mapper for backward compatibility - now uses the new flow
@OptIn(ExperimentalTime::class)
internal fun HelloMessage.toSimpleParams(): Triple<String, String, String> {
    val state = this.toMessageState()
    return Triple(
        state.text,
        state.language,
        state.formattedTimestamp
    )
}