package com.gpcasiapac.gpchelloworldkmp.common.data.network

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

actual fun getDefaultHttpClientEngine(): HttpClientEngine = CIO.create()