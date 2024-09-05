package com.monta.library.ocpp.v16.firmware

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

/**
 * Central System can notify a Charge Point that it needs to update its firmware. The Central System
 * SHALL send an UpdateFirmware.req PDU to instruct the Charge Point to install new firmware. The
 * PDU SHALL contain a date and time after which the Charge Point is allowed to retrieve the new
 * firmware and the location from which the firmware can be downloaded.
 *
 * Upon receipt of an UpdateFirmware.req PDU, the Charge Point SHALL respond with a
 * UpdateFirmware.conf PDU. The Charge Point SHOULD start retrieving the firmware as soon as possible
 * after retrieve-date.
 */
object UpdateFirmwareFeature : Feature {
    override val name: String = "UpdateFirmware"
    override val requestType: Class<out OcppRequest> = UpdateFirmwareRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = UpdateFirmwareConfirmation::class.java
}

data class UpdateFirmwareRequest(
    /**
     * Required
     *
     * This contains a string containing a URI pointing to a location from which to retrieve the firmware.
     */
    val location: String,
    /**
     * Optional
     *
     * This specifies how many times Charge Point must try to download the firmware before giving up.
     * If this field is not present, it is left to Charge Point to decide how many times it wants to retry
     */
    val retries: Int? = null,
    /**
     * Required
     *
     * This contains the date and time after which the Charge Point must retrieve the (new) firmware.
     */
    val retrieveDate: ZonedDateTime,

    /**
     * Optional
     *
     * The interval in seconds after which a retry may be attempted.
     * If this field is not present, it is left to Charge Point to decide how long to wait between attempts.
     */
    val retryInterval: Int? = null
) : OcppRequest

object UpdateFirmwareConfirmation : OcppConfirmation
