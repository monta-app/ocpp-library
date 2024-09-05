package com.monta.library.ocpp.v201.blocks.reservation

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.IdToken
import com.monta.library.ocpp.v201.common.StatusInfo
import java.time.ZonedDateTime

object ReserveNowFeature : Feature {
    override val name: String = "ReserveNow"
    override val requestType: Class<out OcppRequest> = ReserveNowRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ReserveNowResponse::class.java
}

data class ReserveNowRequest(
    /** Id of reservation. */
    val id: Long,
    /** Date and time at which the reservation expires. */
    val expiryDateTime: ZonedDateTime,
    val connectorType: String? = null,
    val idToken: IdToken,
    /** This contains ID of the evse to be reserved. */
    val evseId: Long? = null,
    val groupIdToken: IdToken? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (connectorType != null) {
            require(connectorType in cg_array0) {
                "connectorType not in enumerated values - $connectorType"
            }
        }
    }

    companion object {
        private val cg_array0 = setOf(
            "cCCS1",
            "cCCS2",
            "cG105",
            "cTesla",
            "cType1",
            "cType2",
            "s309-1P-16A",
            "s309-1P-32A",
            "s309-3P-16A",
            "s309-3P-32A",
            "sBS1361",
            "sCEE-7-7",
            "sType2",
            "sType3",
            "Other1PhMax16A",
            "Other1PhOver16A",
            "Other3Ph",
            "Pan",
            "wInductive",
            "wResonant",
            "Undetermined",
            "Unknown"
        )
    }
}

data class ReserveNowResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Faulted,
        Occupied,
        Rejected,
        Unavailable
    }
}
