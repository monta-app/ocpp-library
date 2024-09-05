package com.monta.library.ocpp.v201.blocks.firmwaremanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData

object FirmwareStatusNotificationFeature : Feature {
    override val name: String = "FirmwareStatusNotification"
    override val requestType: Class<out OcppRequest> = FirmwareStatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = FirmwareStatusNotificationResponse::class.java
}

data class FirmwareStatusNotificationRequest(
    val status: Status,
    /** The request id that was provided in the
     UpdateFirmwareRequest that started this firmware update.
     This field is mandatory, unless the message was triggered by a TriggerMessageRequest AND there is no firmware update ongoing. */
    val requestId: Long? = null,
    val customData: CustomData? = null
) : OcppRequest {

    enum class Status {
        Downloaded,
        DownloadFailed,
        Downloading,
        DownloadScheduled,
        DownloadPaused,
        Idle,
        InstallationFailed,
        Installing,
        Installed,
        InstallRebooting,
        InstallScheduled,
        InstallVerificationFailed,
        InvalidSignature,
        SignatureVerified
    }
}

data class FirmwareStatusNotificationResponse(
    val customData: CustomData? = null
) : OcppConfirmation
