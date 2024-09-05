package com.monta.library.ocpp.v16.firmware

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object DiagnosticsStatusNotificationFeature : Feature {
    override val name: String = "DiagnosticsStatusNotification"
    override val requestType: Class<out OcppRequest> = DiagnosticsStatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = DiagnosticsStatusNotificationConfirmation::class.java
}

enum class DiagnosticsStatusNotificationStatus {
    /**
     * Charge Point is not performing diagnostics related tasks.
     * Status Idle SHALL only be used as in a DiagnosticsStatusNotification.req that was triggered by a TriggerMessage.req
     */
    Idle,

    /**
     * Diagnostics information has been uploaded.
     */
    Uploaded,

    /**
     * Uploading of diagnostics failed.
     */
    UploadFailed,

    /**
     * File is being uploaded
     */
    Uploading
}

data class DiagnosticsStatusNotificationRequest(
    /**
     * Required
     *
     * This contains the status of the diagnostics upload
     */
    val status: DiagnosticsStatusNotificationStatus
) : OcppRequest

object DiagnosticsStatusNotificationConfirmation : OcppConfirmation
