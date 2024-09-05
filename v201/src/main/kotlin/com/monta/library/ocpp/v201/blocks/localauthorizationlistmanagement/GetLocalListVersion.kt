package com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData

object GetLocalListVersionFeature : Feature {
    override val name: String = "GetLocalListVersion"
    override val requestType: Class<out OcppRequest> = GetLocalListVersionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetLocalListVersionResponse::class.java
}

data class GetLocalListVersionRequest(
    val customData: CustomData? = null
) : OcppRequest

data class GetLocalListVersionResponse(
    /** This contains the current version number of the local authorization list in the Charging Station. */
    val versionNumber: Long,
    val customData: CustomData? = null
) : OcppConfirmation
