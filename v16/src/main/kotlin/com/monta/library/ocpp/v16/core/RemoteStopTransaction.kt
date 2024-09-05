package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object RemoteStopTransactionFeature : Feature {
    override val name: String = "RemoteStopTransaction"
    override val requestType: Class<out OcppRequest> = RemoteStopTransactionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = RemoteStopTransactionConfirmation::class.java
}

data class RemoteStopTransactionRequest(
    val transactionId: Int
) : OcppRequest

data class RemoteStopTransactionConfirmation(
    val status: RemoteStartStopStatus
) : OcppConfirmation
