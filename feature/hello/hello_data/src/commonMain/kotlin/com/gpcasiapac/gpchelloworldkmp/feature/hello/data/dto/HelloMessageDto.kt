package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HelloMessageDto(

    @SerialName("message")
    val message: String,

    @SerialName("timestamp")
    val timestamp: Long,

    @SerialName("language")
    val language: String

)