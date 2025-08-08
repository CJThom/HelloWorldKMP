package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.CartFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.CartFeatureEntryImpl

val cartImplModule = module {
    single<CartFeatureEntry> { CartFeatureEntryImpl() }
}