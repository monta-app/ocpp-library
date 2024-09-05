package com.monta.library.ocpp.v201.blocks.displaymessage

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object GetDisplayMessagesFeature : Feature {
    override val name: String = "GetDisplayMessages"
    override val requestType: Class<out OcppRequest> = GetDisplayMessagesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetDisplayMessagesResponse::class.java
}

data class GetDisplayMessagesRequest(
    /** If provided the Charging Station shall return Display Messages of the given ids. This field SHALL NOT contain more ids than set in "configkey-number-of-display-messages,NumberOfDisplayMessages.maxLimit" */
    val id: List<Long>? = null,
    /** The Id of this request. */
    val requestId: Long,
    val priority: Priority? = null,
    val state: State? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (id != null) {
            require(id.isNotEmpty()) {
                "id length < minimum 1 - ${id.size}"
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

data class GetDisplayMessagesResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Unknown
    }
}
