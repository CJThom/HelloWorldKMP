package com.gpcasiapac.gpchelloworldkmp.common.data.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import com.gpcasiapac.gpchelloworldkmp.config.BuildConfig
import com.gpcasiapac.gpchelloworldkmp.common.data.json.JsonConfig

object HttpClientProvider {

    fun createHttpClient(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {

            install(Resources)
            install(WebSockets)

            install(ContentNegotiation) {
                json(JsonConfig.json)
            }

            install(Logging) {
                logger = CustomLogger
                level = LogLevel.ALL
            }

            defaultRequest {
                host = BuildConfig.HOST
                port = BuildConfig.PORT
            }
        }
    }

    private object CustomLogger : Logger {
        private const val LOG_TAG = "HttpClient"

        override fun log(message: String) {
            co.touchlab.kermit.Logger.d(LOG_TAG) { message }
        }
    }
}