package com.monta.library.ocpp.v16.localauth

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object GetLocalListVersionFeature : Feature {
    override val name: String = "GetLocalListVersion"
    override val requestType: Class<out OcppRequest> = GetLocalListVersionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetLocalListVersionConfirmation::class.java
}

object GetLocalListVersionRequest : OcppRequest

class GetLocalListVersionConfirmation(
    /**
     * Required
     *
     * This contains the current version number of the local authorization list in the Charge Point
     */
    val listVersion: Int
) : OcppConfirmation
