package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.smartcharge.ChargingProfile

object RemoteStartTransactionFeature : Feature {
    override val name: String = "RemoteStartTransaction"
    override val requestType: Class<out OcppRequest> = RemoteStartTransactionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = RemoteStartTransactionConfirmation::class.java
}

/**
 * The result of a [RemoteStartTransactionRequest] or [RemoteStopTransactionRequest]*/

enum class RemoteStartStopStatus {
    /**
     * Command will be executed.*/

    Accepted,

    /**
     * Command will not be executed.*/

    Rejected
}

/**
 * TODO finish me
 * The Central System MAY include a ChargingProfile in the RemoteStartTransaction request. The purpose of this ChargingProfile SHALL be set to TxProfile. If accepted, the Charge Point SHALL use this ChargingProfile for the transaction.
 *
 * NOTE: If a Charge Point without support for Smart Charging receives a [RemoteStartTransactionRequest] with a
 * Charging Profile, this parameter SHOULD be ignored.
 */

data class RemoteStartTransactionRequest(
    /**
     * TODO validate that connector is 0 or greater
     *
     * Optional
     *
     * Number of the connector on which to start the transaction. connectorId SHALL be > 0
     **/
    val connectorId: Int? = null,
    /**
     * TODO String[20]
     *
     * Required
     *
     * The identifier that Charge Point must use to start a transaction.
     **/
    val idTag: String,
    /**
     * Optional
     *
     * Charging Profile to be used by the Charge Point for the requested transaction.
     * ChargingProfilePurpose MUST be set to TxProfile
     **/
    val chargingProfile: ChargingProfile? = null
) : OcppRequest

/**
 * This contains the field definitions of the RemoteStartTransaction.conf PDU sent from Charge Point to Central System.
 **/
data class RemoteStartTransactionConfirmation(
    /**
     * Required
     * Status indicating whether Charge Point accepts the request to start a transaction.
     **/
    val status: RemoteStartStopStatus
) : OcppConfirmation
