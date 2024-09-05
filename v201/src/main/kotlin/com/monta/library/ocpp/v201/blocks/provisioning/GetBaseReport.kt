package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object GetBaseReportFeature : Feature {
    override val name: String = "GetBaseReport"
    override val requestType: Class<out OcppRequest> = GetBaseReportRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetBaseReportResponse::class.java
}

data class GetBaseReportRequest(
    /** The Id of the request. */
    val requestId: Long,
    val reportBase: ReportBase,
    val customData: CustomData? = null
) : OcppRequest {
    enum class ReportBase {
        ConfigurationInventory,
        FullInventory,
        SummaryInventory
    }
}

data class GetBaseReportResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected,
        NotSupported,
        EmptyResultSet
    }
}
