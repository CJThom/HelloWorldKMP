package com.gpcasiapac.gpchelloworldkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform