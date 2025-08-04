package com.gpcasiapac.gpchelloworldkmp.common.data.network

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

actual fun getDefaultHttpClientEngine(): HttpClientEngine = Android.create()