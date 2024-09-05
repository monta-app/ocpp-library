package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object ClearVariableMonitoringFeature : Feature {
    override val name: String = "ClearVariableMonitoring"
    override val requestType: Class<out OcppRequest> = ClearVariableMonitoringRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ClearVariableMonitoringResponse::class.java
}

data class ClearVariableMonitoringRequest(
    /** List of the monitors to be cleared, identified by there Id. */
    val id: List<Long>,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(id.isNotEmpty()) {
            "id length < minimum 1 - ${id.size}"
        }
    }
}

data class ClearVariableMonitoringResponse(
    val clearMonitoringResult: List<ClearMonitoringResult>,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        require(clearMonitoringResult.isNotEmpty()) {
            "clearMonitoringResult length < minimum 1 - ${clearMonitoringResult.size}"
        }
    }

    data class ClearMonitoringResult(
        val customData: CustomData? = null,
        val status: Status,
        /** Id of the monitor of which a clear was requested. */
        val id: Long,
        val statusInfo: StatusInfo? = null
    )

    enum class Status {
        Accepted,
        Rejected,
        NotFound
    }
}
