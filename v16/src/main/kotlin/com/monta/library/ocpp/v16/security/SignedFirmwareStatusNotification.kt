package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object SignedFirmwareStatusNotificationFeature : Feature {
    override val name: String = "SignedFirmwareStatusNotification"
    override val requestType: Class<out OcppRequest> = SignedFirmwareStatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SignedFirmwareStatusNotificationConfirmation::class.java
}

/**
 * Status of a firmware download.
 *
 * A value with "Intermediate state" in the description, is an intermediate state, update process is not finished.
 * A value with "Failure end state" in the description, is an end state, update process has stopped, update failed.
 * A value with "Successful end state" in the description, is an end state, update process has stopped, update
 * successful.
 *
 * FirmwareStatusEnumType is used by: SignedFirmwareStatusNotification.req
 */
enum class FirmwareStatusEnumType {
    /**
     * Intermediate state. New firmware has been downloaded by Charge Point.
     */
    Downloaded,

    /**
     * Failure end state. Charge Point failed to download firmware.
     */
    DownloadFailed,

    /**
     * Intermediate state. Firmware is being downloaded.
     */
    Downloading,

    /**
     * Intermediate state. Downloading of new firmware has been scheduled.
     */
    DownloadScheduled,

    /**
     * Intermediate state. Downloading has been paused.
     */
    DownloadPaused,

    /**
     * Charge Point is not performing firmware update related tasks. Status Idle SHALL only be used as in a
     * SignedFirmwareStatusNotification.req that was triggered by ExtendedTriggerMessage.req.
     */
    Idle,

    /**
     * Failure end state. Installation of new firmware has failed.
     */
    InstallationFailed,

    /**
     * Intermediate state. Firmware is being installed.
     */
    Installing,

    /**
     * Successful end state. New firmware has successfully been installed in Charge Point.
     */
    Installed,

    /**
     * Intermediate state. Charge Point is about to reboot to activate new firmware. This status MAY be omitted if a
     * reboot is an integral part of the installation and cannot be reported separately.
     */
    InstallRebooting,

    /**
     * Intermediate state. Installation of the downloaded firmware is scheduled to take place on installDateTime given
     * in SignedUpdateFirmware.req.
     */
    InstallScheduled,

    /**
     * Failure end state. Verification of the new firmware (e.g. using a checksum or some other means) has failed and
     * installation will not proceed. (Final failure state)
     */
    InstallVerificationFailed,

    /**
     * Failure end state. The firmware signature is not valid.
     */
    InvalidSignature,

    /**
     * Intermediate state. Provide signature successfully verified.
     */
    SignatureVerified
}

data class SignedFirmwareStatusNotificationRequest(
    /**
     * Required.
     *
     * This contains the progress status of the firmware installation.
     */
    val status: FirmwareStatusEnumType,

    /**
     * Optional.
     *
     * The request id that was provided in the SignedUpdateFirmware.req that
     * started this firmware update. This field is mandatory, unless the message
     * was triggered by a TriggerMessage.req or the ExtendedTriggerMessage.req AND
     * there is no firmware update ongoing.
     */
    val requestId: Int? = null
) : OcppRequest

object SignedFirmwareStatusNotificationConfirmation : OcppConfirmation
