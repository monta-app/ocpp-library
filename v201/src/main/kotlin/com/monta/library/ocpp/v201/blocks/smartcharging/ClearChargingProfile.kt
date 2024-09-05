package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingProfilePurpose

object ClearChargingProfileFeature : Feature {
    override val name: String = "ClearChargingProfile"
    override val requestType: Class<out OcppRequest> = ClearChargingProfileRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ClearChargingProfileResponse::class.java
}

data class ClearChargingProfileRequest(
    /** The Id of the charging profile to clear. */
    val chargingProfileId: Long? = null,
    val chargingProfileCriteria: ChargingProfileCriteria? = null,
    val customData: CustomData? = null
) : OcppRequest {

    data class ChargingProfileCriteria(
        val customData: CustomData? = null,
        /** Identified_ Object. MRID. Numeric_ Identifier
         urn:x-enexis:ecdm:uid:1:569198
         Specifies the id of the EVSE for which to clear charging profiles. An evseId of zero (0) specifies the charging profile for the overall Charging Station. Absence of this parameter means the clearing applies to all charging profiles that match the other criteria in the request. */
        val evseId: Long? = null,
        val chargingProfilePurpose: ChargingProfilePurpose? = null,
        /** Charging_ Profile. Stack_ Level. Counter
         urn:x-oca:ocpp:uid:1:569230
         Specifies the stackLevel for which charging profiles will be cleared, if they meet the other criteria in the request. */
        val stackLevel: Long? = null
    )
}

data class ClearChargingProfileResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Unknown
    }
}
