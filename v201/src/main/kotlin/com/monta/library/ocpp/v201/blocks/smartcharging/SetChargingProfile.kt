package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingProfile

object SetChargingProfileFeature : Feature {
    override val name: String = "SetChargingProfile"
    override val requestType: Class<out OcppRequest> = SetChargingProfileRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SetChargingProfileResponse::class.java
}

data class SetChargingProfileRequest(
    /** For TxDefaultProfile an evseId=0 applies the profile to each individual evse. For ChargingStationMaxProfile and ChargingStationExternalConstraints an evseId=0 contains an overal limit for the whole Charging Station. */
    val evseId: Long,
    val chargingProfile: ChargingProfile,
    val customData: CustomData? = null
) : OcppRequest

data class SetChargingProfileResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Rejected
    }
}
