package com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object ClearCacheFeature : Feature {
    override val name: String = "ClearCache"
    override val requestType: Class<out OcppRequest> = ClearCacheRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ClearCacheResponse::class.java
}

data class ClearCacheRequest(
    val customData: CustomData? = null
) : OcppRequest

data class ClearCacheResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Rejected
    }
}
