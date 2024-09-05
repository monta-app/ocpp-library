package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData

object LogStatusNotificationFeature : Feature {
    override val name: String = "LogStatusNotification"
    override val requestType: Class<out OcppRequest> = LogStatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = LogStatusNotificationResponse::class.java
}

data class LogStatusNotificationRequest(
    val status: Status,
    /** The request id that was provided in GetLogRequest that started this log upload. This field is mandatory,
     unless the message was triggered by a TriggerMessageRequest AND there is no log upload ongoing. */
    val requestId: Long? = null,
    val customData: CustomData? = null
) : OcppRequest {

    enum class Status {
        BadMessage,
        Idle,
        NotSupportedOperation,
        PermissionDenied,
        Uploaded,
        UploadFailure,
        Uploading,
        AcceptedCanceled
    }
}

data class LogStatusNotificationResponse(
    val customData: CustomData? = null
) : OcppConfirmation
