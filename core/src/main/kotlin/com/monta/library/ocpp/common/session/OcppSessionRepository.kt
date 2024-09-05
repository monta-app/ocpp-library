package com.monta.library.ocpp.common.session

import java.util.concurrent.ConcurrentHashMap

class OcppSessionRepository {

    private val sessionSocketMap = ConcurrentHashMap<String, OcppSession>(1000)

    suspend fun add(identity: String, ocppSession: OcppSession): OcppSession {
        val previous = sessionSocketMap.put(identity, ocppSession)
        // if there was a previous session for this charge point, mark it as a duplicate and close it
        if (previous != null) {
            previous.markAsDuplicate()
            previous.close("duplicate")
        }
        return ocppSession
    }

    fun get(identity: String): OcppSession? {
        return sessionSocketMap[identity]
    }

    fun remove(identity: String): OcppSession? {
        return sessionSocketMap.remove(identity)
    }
}
