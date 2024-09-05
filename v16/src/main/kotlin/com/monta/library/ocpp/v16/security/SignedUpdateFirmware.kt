package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

/**
 * 1. The Central System sends a SignedUpdateFirmware.req message that contains the location of the firmware,
 * description the time after which it should be retrieved, and information on how many times the Charge Point should retry
 * downloading the firmware.
 *
 * 2. The Charge Point verifies the validity of the certificate against the Manufacturer root certificate.
 *
 * 3. If the certificate is not valid or could not be verified, the Charge Point aborts the firmware update process and
 * sends a SignedUpdateFirmware.conf with status InvalidCertificate (or status RevokedCertificate when the
 * certificate has been revoked) and a SecurityEventNotification.req with the security event
 * InvalidFirmwareSigningCertificate.
 * If the certificate is valid, the Charge Point starts downloading the firmware, and sends a
 * SignedFirmwareStatusNotification.req with status Downloading.
 *
 * 4. If the Firmware successfully downloaded, the Charge Point sends a SignedFirmwareStatusNotification.req with
 * status Downloaded.
 * Otherwise, it sends a SignedFirmwareStatusNotification.req with status DownloadFailed.
 *
 * 5. If the verification is successful, the Charge Point sends a SignedFirmwareStatusNotification.req with status
 * Installing.
 * If the verification of the firmware fails or if a signature is missing entirely, the Charge Point sends a
 * SignedFirmwareStatusNotification.req with status InvalidSignature and a SecurityEventNotification.req with the
 * security event InvalidFirmwareSignature.
 *
 * 6. If the installation is successful, the Charge Point sends a SignedFirmwareStatusNotification.req with status
 * Installed.
 * Otherwise, it sends a SignedFirmwareStatusNotification.req with status InstallationFailed.
 */
object SignedUpdateFirmwareFeature : Feature {
    override val name: String = "SignedUpdateFirmware"
    override val requestType: Class<out OcppRequest> = SignedUpdateFirmwareRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SignedUpdateFirmwareConfirmation::class.java
}

enum class UpdateFirmwareStatusEnumType {
    /**
     * Accepted this firmware update request. This does not mean the firmware update is successful, the Charge Point will now start
     * the firmware update process.
     */
    Accepted,

    /**
     * Firmware update request rejected.
     */
    Rejected,

    /**
     * Accepted this firmware update request, but in doing this has canceled an ongoing firmware update.
     */
    AcceptedCanceled,

    /**
     * The certificate is invalid.
     */
    InvalidCertificate,

    /**
     * Failure end state. The Firmware Signing certificate has been revoked.
     */
    RevokedCertificate
}

data class SignedUpdateFirmwareRequest(
    /**
     * Optional
     *
     * This specifies how many times Charge Point must try to download the
     * firmware before giving up. If this field is not present, it is left to Charge Point to
     * decide how many times it wants to retry.
     */
    val retries: Int? = null,

    /**
     * Optional
     *
     * The interval in seconds after which a retry may be attempted. If this
     * field is not present, it is left to Charge Point to decide how long to wait between
     * attempts.
     */
    val retryInterval: Int? = null,

    /**
     * Required.
     *
     * The Id of this request.
     */
    val requestId: Int,

    val firmware: FirmwareType
) : OcppRequest

data class FirmwareType(

    /**
     * Required. String[512]
     *
     * URI defining the origin of the firmware.
     */
    val location: String,

    /**
     * Required.
     *
     * Date and time at which the firmware shall be retrieved.
     */
    val retrieveDateTime: ZonedDateTime,

    /**
     * Optional.
     *
     * Date and time at which the firmware shall be installed.
     */
    val installDateTime: ZonedDateTime? = null,

    /**
     * Required. String[5500]
     *
     * Certificate with which the firmware was signed. PEM encoded X.509 certificate.
     */
    val signingCertificate: String,

    /**
     * Required. String[800]
     *
     * Base64 encoded firmware signature.
     */
    val signature: String
)

data class SignedUpdateFirmwareConfirmation(
    /**
     * Required
     *
     * This field indicates whether the Charge Point was able to accept the request.
     */
    val status: UpdateFirmwareStatusEnumType
) : OcppConfirmation
