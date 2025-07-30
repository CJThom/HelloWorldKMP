package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class HelloMessageDto(
    val message: String,
    val timestamp: Long,
    val language: String
)