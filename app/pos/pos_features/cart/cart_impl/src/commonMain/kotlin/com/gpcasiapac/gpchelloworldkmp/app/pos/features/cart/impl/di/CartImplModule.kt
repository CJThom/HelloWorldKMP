package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.CartFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.CartFeatureEntryImpl
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.data.InMemoryCartRepository
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.CartViewModel
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.repository.CartRepository
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase.AddToCartUseCase
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase.RemoveItemFromCartUseCase
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase.ObserveCartUseCase
import org.koin.core.module.dsl.viewModel

val cartImplModule = module {
    // Data binding
    single<CartRepository> { InMemoryCartRepository() }

    // Use cases
    single { AddToCartUseCase(get()) }
    single { RemoveItemFromCartUseCase(get()) }
    single { ObserveCartUseCase(get()) }

    // ViewModels
    viewModel { CartViewModel(get(), get(), get()) }

    // Feature entry
    single<CartFeatureEntry> { CartFeatureEntryImpl() }
}