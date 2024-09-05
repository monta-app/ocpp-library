package com.monta.library.ocpp.v201.blocks.remotecontrol

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

/**
 * F05 Remotely Unlock Connector
 */
object UnlockConnectorFeature : Feature {
    override val name: String = "UnlockConnector"
    override val requestType: Class<out OcppRequest> = UnlockConnectorRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = UnlockConnectorResponse::class.java
}

data class UnlockConnectorRequest(
    /**
     * This contains the identifier of the EVSE for which a connector needs to be unlocked.
     **/
    val evseId: Long,
    /**
     * This contains the identifier of the connector that needs to be unlocked.
     **/
    val connectorId: Long,
    val customData: CustomData? = null
) : OcppRequest

data class UnlockConnectorResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Unlocked,
        UnlockFailed,
        OngoingAuthorizedTransaction,
        UnknownConnector
    }
}
