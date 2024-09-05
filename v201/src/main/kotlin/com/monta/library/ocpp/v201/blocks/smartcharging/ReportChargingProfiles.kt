package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingLimitSource
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingProfile

object ReportChargingProfilesFeature : Feature {
    override val name: String = "ReportChargingProfiles"
    override val requestType: Class<out OcppRequest> = ReportChargingProfilesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ReportChargingProfilesResponse::class.java
}

data class ReportChargingProfilesRequest(
    /** Id used to match the "getchargingprofilesrequest, GetChargingProfilesRequest" message with the resulting ReportChargingProfilesRequest messages. When the CSMS provided a requestId in the "getchargingprofilesrequest, GetChargingProfilesRequest", this field SHALL contain the same value. */
    val requestId: Long,
    val chargingLimitSource: ChargingLimitSource,
    val chargingProfile: List<ChargingProfile>,
    /** To Be Continued. Default value when omitted: false. false indicates that there are no further messages as part of this report. */
    val tbc: Boolean = false,
    /** The evse to which the charging profile applies. If evseId = 0, the message contains an overall limit for the Charging Station. */
    val evseId: Long,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(chargingProfile.isNotEmpty()) {
            "chargingProfile length < minimum 1 - ${chargingProfile.size}"
        }
    }
}

data class ReportChargingProfilesResponse(
    val customData: CustomData? = null
) : OcppConfirmation
