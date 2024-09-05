package com.monta.library.ocpp.v201.blocks.displaymessage

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.MessageContent
import java.time.ZonedDateTime

object NotifyDisplayMessagesFeature : Feature {
    override val name: String = "NotifyDisplayMessages"
    override val requestType: Class<out OcppRequest> = NotifyDisplayMessagesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyDisplayMessagesResponse::class.java
}

data class NotifyDisplayMessagesRequest(
    val messageInfo: List<MessageInfo>? = null,
    /** The id of the "getdisplaymessagesrequest,GetDisplayMessagesRequest" that requested this message. */
    val requestId: Long,
    /** "to be continued" indicator. Indicates whether another part of the report follows in an upcoming NotifyDisplayMessagesRequest message. Default value when omitted is false. */
    val tbc: Boolean = false,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (messageInfo != null) {
            require(messageInfo.isNotEmpty()) {
                "messageInfo length < minimum 1 - ${messageInfo.size}"
            }
        }
    }

    data class MessageInfo(
        val customData: CustomData? = null,
        val display: Component? = null,
        /** Identified_ Object. MRID. Numeric_ Identifier
         urn:x-enexis:ecdm:uid:1:569198
         Master resource identifier, unique within an exchange context. It is defined within the OCPP context as a positive Integer value (greater or equal to zero). */
        val id: Long,
        val priority: Priority,
        val state: State? = null,
        /** Message_ Info. Start. Date_ Time
         urn:x-enexis:ecdm:uid:1:569256
         From what date-time should this message be shown. If omitted: directly. */
        val startDateTime: ZonedDateTime? = null,
        /** Message_ Info. End. Date_ Time
         urn:x-enexis:ecdm:uid:1:569257
         Until what date-time should this message be shown, after this date/time this message SHALL be removed. */
        val endDateTime: ZonedDateTime? = null,
        /** During which transaction shall this message be shown.
         Message SHALL be removed by the Charging Station after transaction has
         ended. */
        val transactionId: String? = null,
        val message: MessageContent
    ) {

        init {
            if (transactionId != null) {
                require(transactionId.length <= 36) {
                    "transactionId length > maximum 36 - ${transactionId.length}"
                }
            }
        }
    }

    enum class Priority {
        AlwaysFront,
        InFront,
        NormalCycle
    }

    enum class State {
        Charging,
        Faulted,
        Idle,
        Unavailable
    }
}

data class NotifyDisplayMessagesResponse(
    val customData: CustomData? = null
) : OcppConfirmation
