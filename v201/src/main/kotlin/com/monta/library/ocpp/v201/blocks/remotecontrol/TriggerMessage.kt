package com.monta.library.ocpp.v201.blocks.remotecontrol

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.EVSE
import com.monta.library.ocpp.v201.common.StatusInfo

/**
 * F06 Trigger Message
 */
object TriggerMessageFeature : Feature {
    override val name: String = "TriggerMessage"
    override val requestType: Class<out OcppRequest> = TriggerMessageRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = TriggerMessageResponse::class.java
}

data class TriggerMessageRequest(
    val requestedMessage: RequestedMessage,
    val evse: EVSE? = null,
    val customData: CustomData? = null
) : OcppRequest {
    enum class RequestedMessage {
        BootNotification,
        LogStatusNotification,
        FirmwareStatusNotification,
        Heartbeat,
        MeterValues,
        SignChargingStationCertificate,
        SignV2GCertificate,
        StatusNotification,
        TransactionEvent,
        SignCombinedCertificate,
        PublishFirmwareStatusNotification
    }
}

data class TriggerMessageResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected,
        NotImplemented
    }
}
