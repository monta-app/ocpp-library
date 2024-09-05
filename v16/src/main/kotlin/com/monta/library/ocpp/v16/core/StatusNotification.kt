package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

object StatusNotificationFeature : Feature {
    override val name: String = "StatusNotification"
    override val requestType: Class<out OcppRequest> = StatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = StatusNotificationConfirmation::class.java
}

enum class ChargePointErrorCode {
    ConnectorLockFailure,
    EVCommunicationError,
    GroundFailure,
    HighTemperature,
    InternalError,
    LocalListConflict,
    NoError,
    OtherError,
    OverCurrentFailure,
    OverVoltage,
    PowerMeterFailure,
    PowerSwitchFailure,
    ReaderFailure,
    ResetFailure,
    UnderVoltage,
    WeakSignal
}

enum class ChargePointStatus {
    Available,
    Preparing,
    Charging,
    SuspendedEVSE,
    SuspendedEV,
    Finishing,
    Reserved,
    Unavailable,
    Faulted
}

/**
 *
 */
data class StatusNotificationRequest(
    /**
     * TODO validate that connector is 0 or greater
     * Required
     * The id of the connector for which the status is reported. Id '0' (zero) is used if the status is for the Charge Point main controller.
     **/
    val connectorId: Int,
    /**
     * Required
     * This contains the error code reported by the Charge Point.
     **/
    val errorCode: ChargePointErrorCode,
    /**
     * TODO String[50]
     * Optional
     * Additional free format information related to the error.
     **/
    val info: String? = null,
    /**
     * Required
     * This contains the current status of the Charge Point.
     **/
    val status: ChargePointStatus,
    /**
     * Optional
     * The time for which the status is reported. If absent time of receipt of the message will be assumed.
     * TODO add a default method
     **/
    val timestamp: ZonedDateTime = ZonedDateTime.now(),
    /**
     * TODO String[255]
     * Optional
     * This identifies the vendor-specific implementation.
     **/
    val vendorId: String? = null,
    /**
     * TODO String[50]
     * Optional
     * This contains the vendor-specific error code.
     **/
    val vendorErrorCode: String? = null
) : OcppRequest

/**
 * Response to a [StatusNotificationRequest]
 */
object StatusNotificationConfirmation : OcppConfirmation
