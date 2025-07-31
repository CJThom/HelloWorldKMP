package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage

object HelloScreenContract {
    data class State(
        val name: String,
        val message: HelloMessage?,
        val isLoading: Boolean,
        val error: String?
    )
}