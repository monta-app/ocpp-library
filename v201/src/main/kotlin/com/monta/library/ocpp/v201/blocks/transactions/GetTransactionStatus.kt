package com.monta.library.ocpp.v201.blocks.transactions

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData

object GetTransactionStatusFeature : Feature {
    override val name: String = "GetTransactionStatus"
    override val requestType: Class<out OcppRequest> = GetTransactionStatusRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetTransactionStatusResponse::class.java
}

data class GetTransactionStatusRequest(
    /** The Id of the transaction for which the status is requested. */
    val transactionId: String? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (transactionId != null) {
            require(transactionId.length <= 36) {
                "transactionId length > maximum 36 - ${transactionId.length}"
            }
        }
    }
}

data class GetTransactionStatusResponse(
    /** Whether the transaction is still ongoing. */
    val ongoingIndicator: Boolean? = null,
    /** Whether there are still message to be delivered. */
    val messagesInQueue: Boolean,
    val customData: CustomData? = null
) : OcppConfirmation
