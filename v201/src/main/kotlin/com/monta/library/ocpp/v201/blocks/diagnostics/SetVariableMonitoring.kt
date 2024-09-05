package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.Variable
import java.math.BigDecimal

object SetVariableMonitoringFeature : Feature {
    override val name: String = "SetVariableMonitoring"
    override val requestType: Class<out OcppRequest> = SetVariableMonitoringRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SetVariableMonitoringResponse::class.java
}

data class SetVariableMonitoringRequest(
    val setMonitoringData: List<SetMonitoringData>,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(setMonitoringData.isNotEmpty()) {
            "setMonitoringData length < minimum 1 - ${setMonitoringData.size}"
        }
    }

    data class SetMonitoringData(
        val customData: CustomData? = null,
        /** An id SHALL only be given to replace an existing monitor. The Charging Station handles the generation of id's for new monitors. */
        val id: Long? = null,
        /** Monitor only active when a transaction is ongoing on a component relevant to this transaction. Default = false. */
        val transaction: Boolean = false,
        /** Value for threshold or delta monitoring.
         For Periodic or PeriodicClockAligned this is the interval in seconds. */
        val value: BigDecimal,
        val type: Monitor,
        /** The severity that will be assigned to an event that is triggered by this monitor. The severity range is 0-9, with 0 as the highest and 9 as the lowest severity level.

         The severity levels have the following meaning: +
         *0-Danger* +
         Indicates lives are potentially in danger. Urgent attention is needed and action should be taken immediately. +
         *1-Hardware Failure* +
         Indicates that the Charging Station is unable to continue regular operations due to Hardware issues. Action is required. +
         *2-System Failure* +
         Indicates that the Charging Station is unable to continue regular operations due to software or minor hardware issues. Action is required. +
         *3-Critical* +
         Indicates a critical error. Action is required. +
         *4-Error* +
         Indicates a non-urgent error. Action is required. +
         *5-Alert* +
         Indicates an alert event. Default severity for any type of monitoring event.  +
         *6-Warning* +
         Indicates a warning event. Action may be required. +
         *7-Notice* +
         Indicates an unusual event. No immediate action is required. +
         *8-Informational* +
         Indicates a regular operational event. May be used for reporting, measuring throughput, etc. No action is required. +
         *9-Debug* +
         Indicates information useful to developers for debugging, not useful during operations. */
        val severity: Long,
        val component: Component,
        val variable: Variable
    )

    enum class Monitor {
        UpperThreshold,
        LowerThreshold,
        Delta,
        Periodic,
        PeriodicClockAligned
    }
}

data class SetVariableMonitoringResponse(
    val setMonitoringResult: List<SetMonitoringResult>,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        require(setMonitoringResult.isNotEmpty()) {
            "setMonitoringResult length < minimum 1 - ${setMonitoringResult.size}"
        }
    }

    data class SetMonitoringResult(
        val customData: CustomData? = null,
        /** Id given to the VariableMonitor by the Charging Station. The Id is only returned when status is accepted. Installed VariableMonitors should have unique id's but the id's of removed Installed monitors should have unique id's but the id's of removed monitors MAY be reused. */
        val id: Long? = null,
        val statusInfo: StatusInfo? = null,
        val status: Status,
        val type: Monitor,
        val component: Component,
        val variable: Variable,
        /** The severity that will be assigned to an event that is triggered by this monitor. The severity range is 0-9, with 0 as the highest and 9 as the lowest severity level.

         The severity levels have the following meaning: +
         *0-Danger* +
         Indicates lives are potentially in danger. Urgent attention is needed and action should be taken immediately. +
         *1-Hardware Failure* +
         Indicates that the Charging Station is unable to continue regular operations due to Hardware issues. Action is required. +
         *2-System Failure* +
         Indicates that the Charging Station is unable to continue regular operations due to software or minor hardware issues. Action is required. +
         *3-Critical* +
         Indicates a critical error. Action is required. +
         *4-Error* +
         Indicates a non-urgent error. Action is required. +
         *5-Alert* +
         Indicates an alert event. Default severity for any type of monitoring event.  +
         *6-Warning* +
         Indicates a warning event. Action may be required. +
         *7-Notice* +
         Indicates an unusual event. No immediate action is required. +
         *8-Informational* +
         Indicates a regular operational event. May be used for reporting, measuring throughput, etc. No action is required. +
         *9-Debug* +
         Indicates information useful to developers for debugging, not useful during operations. */
        val severity: Long
    )

    enum class Status {
        Accepted,
        UnknownComponent,
        UnknownVariable,
        UnsupportedMonitorType,
        Rejected,
        Duplicate
    }

    enum class Monitor {
        UpperThreshold,
        LowerThreshold,
        Delta,
        Periodic,
        PeriodicClockAligned
    }
}
