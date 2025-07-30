package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model

data class HelloMessage(
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val language: String = "en"
)