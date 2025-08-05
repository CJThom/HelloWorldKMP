package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model

import com.gpcasiapac.gpchelloworldkmp.common.domain.SessionIds

/**
 * Hello feature specific session identifiers.
 * Contains session information relevant to the Hello World feature.
 */
data class HelloSessionIds(
    val userId: String? = null,
    val sessionId: String? = null,
    val userName: String? = null,
    val isAuthenticated: Boolean = false
) : SessionIds