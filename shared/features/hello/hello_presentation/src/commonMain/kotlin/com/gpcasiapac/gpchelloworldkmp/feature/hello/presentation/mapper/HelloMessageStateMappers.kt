package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper

import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageParams
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageState

// State → Params mapper (for components with 4+ properties)
internal fun HelloMessageState.toParams(timestamp: Long): HelloMessageParams {
    return HelloMessageParams(
        text = this.text,
        language = this.language,
        formattedTimestamp = this.formattedTimestamp,
        messageId = "msg_$timestamp",
        isFromCurrentUser = false // Default to false, could be determined by business logic
    )
}