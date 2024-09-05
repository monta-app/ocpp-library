package com.monta.library.ocpp.client

import com.monta.library.ocpp.common.OcppClientConnectionEvent
import com.monta.library.ocpp.common.OcppClientDisconnectionEvent
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSessionRepository
import com.monta.library.ocpp.common.transport.OcppSettings

@Suppress("UNCHECKED_CAST")
abstract class BaseOcppClientBuilder<T> {

    protected var onConnect: OcppClientConnectionEvent? = null
    protected var onDisconnect: OcppClientDisconnectionEvent? = null
    protected var ocppSessionRepository: OcppSessionRepository? = null
    protected var settings: OcppSettings? = null
    protected val profiles: MutableSet<ProfileDispatcher> = mutableSetOf()
    protected var sendHook: suspend (String, String) -> String? = { _, message -> message }

    fun onConnect(
        onConnect: OcppClientConnectionEvent
    ): T {
        this.onConnect = onConnect
        return this as T
    }

    fun onDisconnect(
        onDisconnect: OcppClientDisconnectionEvent
    ): T {
        this.onDisconnect = onDisconnect
        return this as T
    }

    fun localMode(
        ocppSessionRepository: OcppSessionRepository
    ): T {
        this.ocppSessionRepository = ocppSessionRepository
        return this as T
    }

    fun settings(
        settings: OcppSettings
    ): T {
        this.settings = settings
        return this as T
    }

    fun addSendHook(
        sendHook: suspend (chargePointIdentity: String, message: String) -> String?
    ): BaseOcppClientBuilder<T> {
        this.sendHook = sendHook
        return this
    }
}
