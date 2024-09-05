package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object ResetFeature : Feature {
    override val name: String = "Reset"
    override val requestType: Class<out OcppRequest> = ResetRequest::class.java
    override val confirmationType: Class<out OcppConfirmation?> = ResetConfirmation::class.java
}

/**
 * Result of [ResetRequest]
 */

enum class ResetStatus {

    /**
     * Command will be executed.
     */

    Accepted,

    /**
     * Command will not be executed.
     */

    Rejected
}

/**
 * Type of reset requested by [ResetRequest]
 */

enum class ResetType {
    /**
     * Full reboot of Charge Point software.
     */

    Hard,

    /**
     * Return to initial status, gracefully terminating any transactions in progress.
     */

    Soft
}

/**
 * The Central System SHALL send a [ResetRequest] PDU for requesting a Charge Point to reset itself.
 *
 * The Central System can request a hard or a soft reset.
 * Upon receipt of a [ResetRequest] PDU, the Charge Point SHALL respond with a [ResetConfirmation] PDU.
 * The response PDU SHALL include whether the Charge Point is will attempt to reset itself.
 *
 * At receipt of a soft reset, the Charge Point SHALL return to a state that behaves as just having been booted.
 * If any transaction is in progress it SHALL be terminated normally, before the reset, as in Stop Transaction.
 *
 * At receipt of a hard reset the Charge Point SHALL attempt to terminate any
 * transaction in progress normally as in StopTransaction and then perform a reboot.
 *
 * Persistent states: for example: Connector set to Unavailable shall persist.
 *
 */

data class ResetRequest(
    /**
     * Required
     *
     * This contains the type of reset that the Charge Point should perform.
     **/
    val type: ResetType
) : OcppRequest

data class ResetConfirmation(
    /**
     * Required
     *
     * This indicates whether the Charge Point is able to perform the reset.
     **/
    val status: ResetStatus
) : OcppConfirmation
