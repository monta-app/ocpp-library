package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object ExtendedTriggerMessageFeature : Feature {
    override val name: String = "ExtendedTriggerMessage"
    override val requestType: Class<out OcppRequest> = ExtendedTriggerMessageRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ExtendedTriggerMessageConfirmation::class.java
}

enum class MessageTriggerEnumType {
    /**
     * To trigger BootNotification.req.
     */
    BootNotification,

    /**
     * To trigger LogStatusNotification.req.
     */
    LogStatusNotification,

    /**
     * To trigger SignedFirmwareStatusNotification.req (So the status of the secure firmware update introduced in this document).
     */
    FirmwareStatusNotification,

    /**
     * To trigger Heartbeat.req.
     */
    Heartbeat,

    /**
     * To trigger MeterValues.req.
     */
    MeterValues,

    /**
     * To trigger a SignCertificate.req with certificateType: ChargePointCertificate.
     */
    SignChargePointCertificate,

    /**
     * To trigger SatusNotification.req.
     */
    StatusNotification
}

data class ExtendedTriggerMessageRequest(
    /**
     * Required
     *
     * Type of the message to be triggered.
     */
    val requestedMessage: MessageTriggerEnumType,

    /**
     * Optional
     *
     * Only filled in when request applies to a specific connector.
     */
    val connectorId: Int? = null
) : OcppRequest

enum class TriggerMessageStatusEnumType {
    /**
     * Requested message will be sent.
     */
    Accepted,

    /**
     * Requested message will not be sent.
     */
    Rejected,

    /**
     * Requested message cannot be sent because it is either not implemented or unknown.
     */
    NotImplemented
}

data class ExtendedTriggerMessageConfirmation(
    /**
     * Required
     *
     * Indicates whether the Charge Point will send the requested notification or not.
     */
    val status: TriggerMessageStatusEnumType
) : OcppConfirmation
