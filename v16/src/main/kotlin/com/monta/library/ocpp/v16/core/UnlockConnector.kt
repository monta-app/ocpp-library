package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object UnlockConnectorFeature : Feature {
    override val name: String = "UnlockConnector"
    override val requestType: Class<out OcppRequest> = UnlockConnectorRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = UnlockConnectorConfirmation::class.java
}

/**
 * Status in response to [UnlockConnectorRequest]
 */
enum class UnlockStatus {
    /**
     * Connector has successfully been unlocked.
     */
    Unlocked,

    /**
     * Failed to unlock the connector.
     */
    UnlockFailed,

    /**
     * Charge Point has no connector lock.
     */
    NotSupported
}

/**
 * Central System can request a Charge Point to unlock a connector.
 * To do so, the Charge Point SHALL send an [UnlockConnectorRequest] PDU.
 *
 * The purpose of this message: Help EV drivers that have problems unplugging their cable from the Charge Point
 * in case of malfunction of the Connector cable retention.
 * When a EV driver calls the CPO help-desk, an operator could manually trigger the sending of an [UnlockConnectorRequest]
 * to the Charge Point, forcing a new attempt to unlock the connector.
 * Hopefully this time the connector unlocks and the EV driver can unplug the cable and drive away.
 *
 * The [UnlockConnectorRequest] SHOULD NOT be used to remotely stop a running transaction, use the Remote Stop Transaction instead.
 *
 * Upon receipt of an [UnlockConnectorRequest] PDU, the Charge Point SHALL respond with a [UnlockConnectorConfirmation] PDU.
 * The response PDU SHALL indicate whether the Charge Point was able to unlock its connector.
 *
 * If there was a transaction in progress on the specific connector,
 * then Charge Point SHALL finish the transaction first as described in Stop Transaction.
 */
data class UnlockConnectorRequest(
    /**
     * TODO validate that connector is 0 or greater
     * Required
     * This contains the identifier of the connector to be unlocked.
     **/
    val connectorId: Int
) : OcppRequest

/**
 * Response to a [UnlockConnectorRequest]
 */
data class UnlockConnectorConfirmation(
    /**
     * Required
     * This indicates whether the Charge Point has unlocked the connector.
     **/
    val status: UnlockStatus
) : OcppConfirmation
