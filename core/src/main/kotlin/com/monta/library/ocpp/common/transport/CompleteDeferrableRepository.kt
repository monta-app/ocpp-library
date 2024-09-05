package com.monta.library.ocpp.common.transport

import kotlinx.coroutines.CompletableDeferred
import java.util.concurrent.ConcurrentHashMap

class CompleteDeferrableRepository<T, U> {

    private val completableDeferredMap = ConcurrentHashMap<String, T>()

    /**
     * Creates a [CompletableDeferred] and assigns that along with some relevant info that is used in the
     * confirmation part of the request chain into a hashmap that is based on the uniqueId in the request
     * after it is stored in a temporary cache it is sent via WS and the [CompletableDeferred] is returned.
     *
     * If there is already a mapping for this combination of this identity and id (a duplicate) the entry will not be added
     * and null will be returned.
     */
    fun addRequest(
        identity: String,
        id: String,
        entryConstructor: (completableDeferred: CompletableDeferred<U>) -> T
    ): CompletableDeferred<U>? {
        // Create our CompletableDeferred
        val completableDeferred = CompletableDeferred<U>()
        // Add our CompletableDeferred to our map (so we can retrieve it when we get a response)
        val previous = completableDeferredMap.putIfAbsent(
            identity + id,
            entryConstructor(completableDeferred)
        )
        // Return our CompletableDeferred so we can await on the request
        return if (previous == null) completableDeferred else null
    }

    /**
     * Gets the cached request and promise created in [addRequest] and removes it from the map if it's present
     */
    fun getCachedDeferred(identity: String, id: String): T? {
        return completableDeferredMap.remove(identity + id)
    }
}
