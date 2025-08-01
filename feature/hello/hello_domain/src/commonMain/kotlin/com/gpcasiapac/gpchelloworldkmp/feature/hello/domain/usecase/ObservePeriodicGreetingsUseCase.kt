package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use case that provides a flow of periodic greeting messages.
 * Emits greeting messages at regular intervals to demonstrate
 * collecting from a Flow in the ViewModel's onStart function.
 */
class ObservePeriodicGreetingsUseCase {
    
    private val greetings = listOf(
        "Welcome to Hello World KMP!",
        "Hope you're having a great day!",
        "Keep coding and stay awesome!",
        "Kotlin Multiplatform is amazing!",
        "Thanks for using our app!"
    )
    
    operator fun invoke(): Flow<HelloMessage> = flow {
        var index = 0
        
        while (true) {
            // Wait before emitting the next greeting
            delay(10000) // Emit every 10 seconds
            
            // Emit the next greeting message
            emit(
                HelloMessage(
                    text = greetings[index % greetings.size],
                    timestamp = System.currentTimeMillis(),
                    language = "en"
                )
            )
            
            index++
        }
    }
}