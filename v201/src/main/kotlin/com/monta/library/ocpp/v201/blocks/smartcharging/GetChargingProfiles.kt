package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingLimitSource
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingProfilePurpose

object GetChargingProfilesFeature : Feature {
    override val name: String = "GetChargingProfiles"
    override val requestType: Class<out OcppRequest> = GetChargingProfilesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetChargingProfilesResponse::class.java
}

data class GetChargingProfilesRequest(
    /** Reference identification that is to be used by the Charging Station in the "reportchargingprofilesrequest, ReportChargingProfilesRequest" when provided. */
    val requestId: Long,
    /** For which EVSE installed charging profiles SHALL be reported. If 0, only charging profiles installed on the Charging Station itself (the grid connection) SHALL be reported. If omitted, all installed charging profiles SHALL be reported. */
    val evseId: Long? = null,
    val chargingProfile: ChargingProfileCriterion,
    val customData: CustomData? = null
) : OcppRequest {

    data class ChargingProfileCriterion(
        val customData: CustomData? = null,
        val chargingProfilePurpose: ChargingProfilePurpose? = null,
        /** Charging_ Profile. Stack_ Level. Counter
         urn:x-oca:ocpp:uid:1:569230
         Value determining level in hierarchy stack of profiles. Higher values have precedence over lower values. Lowest level is 0. */
        val stackLevel: Long? = null,
        /** List of all the chargingProfileIds requested. Any ChargingProfile that matches one of these profiles will be reported. If omitted, the Charging Station SHALL not filter on chargingProfileId. This field SHALL NOT contain more ids than set in "configkey-charging-profile-entries,ChargingProfileEntries.maxLimit" */
        val chargingProfileId: List<Long>? = null,
        /** For which charging limit sources, charging profiles SHALL be reported. If omitted, the Charging Station SHALL not filter on chargingLimitSource. */
        val chargingLimitSource: List<ChargingLimitSource>? = null
    ) {

        init {
            if (chargingProfileId != null) {
                require(chargingProfileId.isNotEmpty()) {
                    "chargingProfileId length < minimum 1 - ${chargingProfileId.size}"
                }
            }
            if (chargingLimitSource != null) {
                require(chargingLimitSource.size in 1..4) {
                    "chargingLimitSource length not in range 1..4 - ${chargingLimitSource.size}"
                }
            }
        }
    }
}

data class GetChargingProfilesResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        NoProfiles
    }
}
