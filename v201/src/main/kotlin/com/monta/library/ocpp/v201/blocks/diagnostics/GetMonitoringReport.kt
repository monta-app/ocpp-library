package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericDeviceModelStatus
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.Variable

object GetMonitoringReportFeature : Feature {
    override val name: String = "GetMonitoringReport"
    override val requestType: Class<out OcppRequest> = GetMonitoringReportRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetMonitoringReportResponse::class.java
}

data class GetMonitoringReportRequest(
    val componentVariable: List<ComponentVariable>? = null,
    /** The Id of the request. */
    val requestId: Long,
    /** This field contains criteria for components for which a monitoring report is requested */
    val monitoringCriteria: List<MonitoringCriterion>? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (componentVariable != null) {
            require(componentVariable.isNotEmpty()) {
                "componentVariable length < minimum 1 - ${componentVariable.size}"
            }
        }
        if (monitoringCriteria != null) {
            require(monitoringCriteria.size in 1..3) {
                "monitoringCriteria length not in range 1..3 - ${monitoringCriteria.size}"
            }
        }
    }

    data class ComponentVariable(
        val customData: CustomData? = null,
        val component: Component,
        val variable: Variable? = null
    )

    enum class MonitoringCriterion {
        ThresholdMonitoring,
        DeltaMonitoring,
        PeriodicMonitoring
    }
}

data class GetMonitoringReportResponse(
    val status: GenericDeviceModelStatus,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation
