package com.monta.library.ocpp.server

import com.monta.library.ocpp.common.OcppMessageListener
import com.monta.library.ocpp.common.OcppServerEventListener
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.session.OcppSessionRepository
import com.monta.library.ocpp.common.transport.OcppSettings

@Suppress("UNCHECKED_CAST")
abstract class BaseOcppServerBuilder<T> {

    protected var onConnect: OcppServerEventListener? = null
    protected var onDisconnect: OcppServerEventListener? = null
    protected var ocppSessionRepository: OcppSessionRepository? = null
    protected var sendMessage: OcppMessageListener<Message>? = null
    protected var settings: OcppSettings? = null
    protected val profiles: MutableSet<ProfileDispatcher> = mutableSetOf()

    fun onConnect(
        onConnect: OcppServerEventListener
    ): T {
        this.onConnect = onConnect
        return this as T
    }

    fun onDisconnect(
        onDisconnect: OcppServerEventListener
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

    fun gatewayMode(
        sendRequest: OcppMessageListener<Message.Request>,
        sendResponse: OcppMessageListener<Message.Response>,
        sendError: OcppMessageListener<Message.Error>
    ): T {
        this.sendMessage =
            { messageSerializer: MessageSerializer, ocppSessionInfo: OcppSession.Info, message: Message ->
                when (message) {
                    is Message.Request -> {
                        sendRequest(messageSerializer, ocppSessionInfo, message)
                    }

                    is Message.Response -> {
                        sendResponse(messageSerializer, ocppSessionInfo, message)
                    }

                    is Message.Error -> {
                        sendError(messageSerializer, ocppSessionInfo, message)
                    }
                }
            }
        return this as T
    }

    fun settings(
        settings: OcppSettings
    ): T {
        this.settings = settings
        return this as T
    }
}
