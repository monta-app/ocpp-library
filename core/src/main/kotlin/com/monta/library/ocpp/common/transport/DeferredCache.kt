package com.monta.library.ocpp.common.transport

import com.monta.library.ocpp.common.profile.OcppConfirmation
import kotlinx.coroutines.CompletableDeferred

/**
 * Used for storing request during their transport phase
 */
class DeferredCache(
    val uniqueId: String,
    val action: String,
    private val completableDeferred: CompletableDeferred<OcppConfirmation>
) {
    fun complete(value: OcppConfirmation): Boolean {
        return completableDeferred.complete(value)
    }

    fun completeExceptionally(exception: Throwable): Boolean {
        return completableDeferred.completeExceptionally(exception)
    }
}
