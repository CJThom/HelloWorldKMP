package com.gpcasiapac.gpchelloworldkmp.feature.cart.impl.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.cart.api.CartFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.feature.cart.impl.CartFeatureEntryImpl

val cartImplModule = module {
    single<CartFeatureEntry> { CartFeatureEntryImpl() }
}
