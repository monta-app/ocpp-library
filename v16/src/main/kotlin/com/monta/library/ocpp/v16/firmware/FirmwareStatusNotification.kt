package com.monta.library.ocpp.v16.firmware

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object FirmwareStatusNotificationFeature : Feature {
    override val name: String = "FirmwareStatusNotification"
    override val requestType: Class<out OcppRequest> = FirmwareStatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = FirmwareStatusNotificationConfirmation::class.java
}

enum class FirmwareStatusNotificationStatus {
    /**
     * New firmware has been downloaded by Charge Point.
     */
    Downloaded,

    /**
     * Charge point failed to download firmware
     */
    DownloadFailed,

    /**
     * Firmware is being downloaded.
     */
    Downloading,

    /**
     * Charge Point is not performing firmware update related tasks. Status Idle SHALL only be used as in a FirmwareStatusNotification.req that was triggered by a TriggerMessage.req
     */
    Idle,

    /**
     * Installation of new firmware has failed.
     */
    InstallationFailed,

    /**
     * Firmware is being installed.
     */
    Installing,

    /**
     * New firmware has successfully been installed in charge point.
     */
    Installed
}

data class FirmwareStatusNotificationRequest(
    /**
     * Required
     *
     * This contains the progress status of the firmware installation.
     */
    val status: FirmwareStatusNotificationStatus
) : OcppRequest

object FirmwareStatusNotificationConfirmation : OcppConfirmation
