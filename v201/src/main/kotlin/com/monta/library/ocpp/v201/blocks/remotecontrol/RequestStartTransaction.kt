package com.monta.library.ocpp.v201.blocks.remotecontrol

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.IdToken
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingProfile

/**
 * - F01 Remote Start Transaction - Cable Plugin First
 * - F02 Remote Start Transaction - Remote Start First
 */
object RequestStartTransactionFeature : Feature {
    override val name: String = "RequestStartTransaction"
    override val requestType: Class<out OcppRequest> = RequestStartTransactionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = RequestStartTransactionResponse::class.java
}

data class RequestStartTransactionRequest(

    val idToken: IdToken,
    /**
     * Id given by the server to this start request.
     * The Charging Station might return this in the "transactioneventrequest, TransactionEventRequest", letting the server know which transaction was started for this request.
     * Use to start a transaction.
     **/
    val remoteStartId: Long,
    /**
     * Number of the EVSE on which to start the transaction.
     * EvseId SHALL be > 0
     **/
    val evseId: Long? = null,
    val groupIdToken: IdToken? = null,
    val chargingProfile: ChargingProfile? = null,
    val customData: CustomData? = null
) : OcppRequest

data class RequestStartTransactionResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    /**
     * When the transaction was already started by the Charging Station before the [RequestStartTransactionRequest] was received, for example: cable plugged in first.
     * This contains the transactionId of the already started transaction.
     **/
    val transactionId: String? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        if (transactionId != null) {
            require(transactionId.length <= 36) {
                "transactionId length > maximum 36 - ${transactionId.length}"
            }
        }
    }

    enum class Status {
        Accepted,
        Rejected
    }
}
