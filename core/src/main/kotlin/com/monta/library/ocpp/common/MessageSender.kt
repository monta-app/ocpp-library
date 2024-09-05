package com.monta.library.ocpp.common

import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.session.OcppSession

typealias OcppClientConnectionEvent = suspend (
    ocppSessionInfo: OcppSession.Info,
    isReconnecting: Boolean
) -> Unit

typealias OcppClientDisconnectionEvent = suspend (
    ocppSessionInfo: OcppSession.Info
) -> Boolean

typealias OcppServerEventListener = suspend (
    ocppSessionInfo: OcppSession.Info
) -> Unit

typealias OcppMessageListener<T> = suspend (
    messageSerializer: MessageSerializer,
    ocppSessionInfo: OcppSession.Info,
    message: T
) -> Unit
