package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class HelloMessageState(
    val text: String,
    val language: String,
    val formattedTimestamp: String
)