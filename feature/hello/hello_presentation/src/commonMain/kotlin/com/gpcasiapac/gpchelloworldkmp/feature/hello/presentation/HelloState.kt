package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewState
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage

data class HelloState(
    val name: String = "",
    val message: HelloMessage? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState