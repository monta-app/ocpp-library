package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericDeviceModelStatus
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.Variable

object GetReportFeature : Feature {
    override val name: String = "GetReport"
    override val requestType: Class<out OcppRequest> = GetReportRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetReportResponse::class.java
}

data class GetReportRequest(
    val componentVariable: List<ComponentVariable>? = null,
    /** The Id of the request. */
    val requestId: Long,
    /** This field contains criteria for components for which a report is requested */
    val componentCriteria: List<ComponentCriterion>? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (componentVariable != null) {
            require(componentVariable.isNotEmpty()) {
                "componentVariable length < minimum 1 - ${componentVariable.size}"
            }
        }
        if (componentCriteria != null) {
            require(componentCriteria.size in 1..4) {
                "componentCriteria length not in range 1..4 - ${componentCriteria.size}"
            }
        }
    }

    data class ComponentVariable(
        val customData: CustomData? = null,
        val component: Component,
        val variable: Variable? = null
    )

    enum class ComponentCriterion {
        Active,
        Available,
        Enabled,
        Problem
    }
}

data class GetReportResponse(
    val status: GenericDeviceModelStatus,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation
