package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class HelloMessageParams(
    val text: String,
    val language: String,
    val formattedTimestamp: String,
    val messageId: String,
    val isFromCurrentUser: Boolean
)