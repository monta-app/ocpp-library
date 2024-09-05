package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

object BootNotificationFeature : Feature {
    override val name: String = "BootNotification"
    override val requestType: Class<out OcppRequest> = BootNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = BootNotificationConfirmation::class.java
}

data class BootNotificationRequest(
    /**
     * String[25]
     *
     * Optional
     *
     * This contains a value that identifies the serial number of the Charge Box inside the Charge Point
     *
     * Note: deprecated, will be removed in future version
     */
    @Deprecated("Deprecated, will be removed in future version")
    val chargeBoxSerialNumber: String? = null,
    /**
     * String[20]
     *
     * Required
     *
     * This contains a value that identifies the model of the ChargePoint
     */
    val chargePointModel: String,
    /**
     * String[25]
     *
     * Optional
     *
     * This contains a value that identifies the serial number of the Charge Point
     */
    val chargePointSerialNumber: String? = null,
    /**
     * String[20]
     *
     * Required
     *
     * This contains a value that identifies the vendor of the ChargePoint
     */
    val chargePointVendor: String,
    /**
     * String[50]
     *
     * Optional
     *
     * This contains the firmware version of the Charge Point
     */
    val firmwareVersion: String? = null,
    /**
     * String[20]
     *
     * Optional
     *
     * This contains the ICCID of the modem’s SIM card
     */
    val iccid: String? = null,
    /**
     * String[20]
     *
     * Optional
     *
     * This contains the IMSI of the modem’s SIM card
     */
    val imsi: String? = null,
    /**
     * String[25]
     *
     * Optional
     *
     * This contains the serial number of the main power meter of the Charge Point
     */

    val meterSerialNumber: String? = null,
    /**
     * String[25]
     *
     * Optional
     *
     * This contains the type of the main power meter of the Charge Point
     */
    val meterType: String? = null
) : OcppRequest

enum class RegistrationStatus {
    /**
     * Charge point is accepted by Central System.
     */
    Accepted,

    /**
     * Central System is not yet ready to accept the Charge Point.
     * Central System may send messages to retrieve information or prepare the Charge Point.
     */
    Pending,

    /**
     * Charge point is not accepted by Central System.
     * This may happen when the Charge Point id is not known by Central System.
     */
    Rejected
}

data class BootNotificationConfirmation(
    /**
     * Required
     *
     * This contains the Central System’s current time
     */
    val currentTime: ZonedDateTime,
    /**
     * Required
     *
     * When RegistrationStatus is Accepted, this contains the heartbeat interval in seconds.
     * If the Central System returns something other than Accepted, the value of the interval
     * field indicates the minimum wait time before sending a next BootNotification request
     *
     */
    val interval: Int,
    /**
     * Required
     *
     * This contains whether the Charge Point has been registered within the System Central
     */
    val status: RegistrationStatus
) : OcppConfirmation
