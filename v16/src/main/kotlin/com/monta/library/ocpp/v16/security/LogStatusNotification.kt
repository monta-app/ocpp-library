package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object LogStatusNotificationFeature : Feature {
    override val name: String = "LogStatusNotification"
    override val requestType: Class<out OcppRequest> = LogStatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = LogStatusNotificationConfirmation::class.java
}

enum class UploadLogStatus {
    /**
     * A badly formatted packet or other protocol incompatibility was detected.
     */
    BadMessage,

    /**
     * The Charge Point is not uploading a log file. Idle SHALL only be used when the message was triggered by a
     * ExtendedTriggerMessage.
     */
    Idle,

    /**
     * The server does not support the operation
     */
    NotSupportedOperation,

    /**
     * Insufficient permissions to perform the operation.
     */
    PermissionDenied,

    /**
     * File has been uploaded successfully.
     */
    Uploaded,

    /**
     * Failed to upload the requested file.
     */
    UploadFailure,

    /**
     * File is being uploaded.
     */
    Uploading
}

data class LogStatusNotificationRequest(

    /**
     * Required
     *
     * This contains the status of the log upload.
     */
    val status: UploadLogStatus,

    /**
     * Optional.
     *
     * The request id that was provided in the GetLog.req that started this log upload.
     */
    val requestId: Int
) : OcppRequest

object LogStatusNotificationConfirmation : OcppConfirmation
