package com.monta.library.ocpp.v201.blocks.tariffandcost

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import java.math.BigDecimal

object CostUpdatedFeature : Feature {
    override val name: String = "CostUpdated"
    override val requestType: Class<out OcppRequest> = CostUpdatedRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = CostUpdatedResponse::class.java
}

data class CostUpdatedRequest(
    /** Current total cost, based on the information known by the CSMS, of the transaction including taxes. In the currency configured with the configuration Variable: ["configkey-currency, Currency"] */
    val totalCost: BigDecimal,
    /** Transaction Id of the transaction the current cost are asked for. */
    val transactionId: String,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(transactionId.length <= 36) {
            "transactionId length > maximum 36 - ${transactionId.length}"
        }
    }
}

data class CostUpdatedResponse(
    val customData: CustomData? = null
) : OcppConfirmation
