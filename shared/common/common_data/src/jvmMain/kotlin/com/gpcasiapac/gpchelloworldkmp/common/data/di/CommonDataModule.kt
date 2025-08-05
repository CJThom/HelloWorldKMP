package com.gpcasiapac.gpchelloworldkmp.common.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import com.gpcasiapac.gpchelloworldkmp.common.data.network.HttpClientProvider

actual val coreHttpClientModule: Module = module {
    single<HttpClient> {
        HttpClientProvider.createHttpClient(CIO.create())
    }
}

actual val commonDataModules: List<Module>
    get() = listOf(
        coreHttpClientModule,
    )