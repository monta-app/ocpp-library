package com.monta.library.ocpp.v201.blocks.remotecontrol

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

/**
 * - F03 Remote Stop Transaction
 * - F04 Remote Stop ISO 15118 charging from CSMS
 */
object RequestStopTransactionFeature : Feature {
    override val name: String = "RequestStopTransaction"
    override val requestType: Class<out OcppRequest> = RequestStopTransactionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = RequestStopTransactionResponse::class.java
}

data class RequestStopTransactionRequest(
    /**
     * The identifier of the transaction which the Charging Station is requested to stop.
     **/
    val transactionId: String,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(transactionId.length <= 36) {
            "transactionId length > maximum 36 - ${transactionId.length}"
        }
    }
}

data class RequestStopTransactionResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected
    }
}
