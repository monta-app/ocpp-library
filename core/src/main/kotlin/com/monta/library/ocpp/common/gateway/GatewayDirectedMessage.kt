package com.monta.library.ocpp.common.gateway

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.session.OcppSession
import java.time.Instant

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
sealed class GatewayDirectedMessage(
    @JsonProperty("ocpp_session_info")
    open val ocppSessionInfo: OcppSession.Info,
    @JsonProperty("message_at")
    open val messageAt: Instant = Instant.now(),
    @JsonProperty("request_at")
    open val requestId: String? = null
) {
    /**
     * For sending a notification to other gateway nodes that we got a new connection
     * on a gateway.
     */
    data class Connect(
        override val ocppSessionInfo: OcppSession.Info,
        override val requestId: String
    ) : GatewayDirectedMessage(
        ocppSessionInfo = ocppSessionInfo,
        requestId = requestId
    )

    /**
     * A directed message being sent on a Redis Pub/Sub channel.
     *
     * Can be an OCPP *Request*: This will always come from the processor directed to the charge point. The response will be
     * published on Redis so that it arrives at the correct waiting processor node.
     *
     * Can be an OCPP *Response/Confirmation*:
     *      If it's coming from the charge point it will be forwarded to the waiting processor node. (see above)
     *      If it's coming from the processor it gets removed from the MessagesInTransitRepository and forwarded to the charge point.
     */
    data class OcppMessage(
        override val ocppSessionInfo: OcppSession.Info,
        override val requestId: String,
        @JsonProperty("message")
        val message: Message
    ) : GatewayDirectedMessage(
        ocppSessionInfo = ocppSessionInfo,
        requestId = requestId
    )

    /**
     * For forcing a disconnect.
     */
    data class Disconnect(
        override val ocppSessionInfo: OcppSession.Info,
        override val requestId: String
    ) : GatewayDirectedMessage(
        ocppSessionInfo = ocppSessionInfo,
        requestId = requestId
    )
}
