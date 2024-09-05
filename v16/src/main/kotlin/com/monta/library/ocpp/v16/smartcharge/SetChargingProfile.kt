package com.monta.library.ocpp.v16.smartcharge

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object SetChargingProfileFeature : Feature {
    override val name: String = "SetChargingProfile"
    override val requestType: Class<out OcppRequest> = SetChargingProfileRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SetChargingProfileConfirmation::class.java
}

enum class SetChargingProfileStatus {
    Accepted,
    Rejected,
    NotSupported
}

data class SetChargingProfileRequest(
    val connectorId: Int,
    val csChargingProfiles: ChargingProfile
) : OcppRequest

data class SetChargingProfileConfirmation(
    val status: SetChargingProfileStatus
) : OcppConfirmation
