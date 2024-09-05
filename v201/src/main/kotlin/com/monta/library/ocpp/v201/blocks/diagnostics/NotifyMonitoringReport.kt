package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.Variable
import java.math.BigDecimal
import java.time.ZonedDateTime

object NotifyMonitoringReportFeature : Feature {
    override val name: String = "NotifyMonitoringReport"
    override val requestType: Class<out OcppRequest> = NotifyMonitoringReportRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyMonitoringReportResponse::class.java
}

data class NotifyMonitoringReportRequest(
    val monitor: List<MonitoringData>? = null,
    /** The id of the GetMonitoringRequest that requested this report. */
    val requestId: Long,
    /** “to be continued” indicator. Indicates whether another part of the monitoringData follows in an upcoming notifyMonitoringReportRequest message. Default value when omitted is false. */
    val tbc: Boolean = false,
    /** Sequence number of this message. First message starts at 0. */
    val seqNo: Long,
    /** Timestamp of the moment this message was generated at the Charging Station. */
    val generatedAt: ZonedDateTime,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (monitor != null) {
            require(monitor.isNotEmpty()) {
                "monitor length < minimum 1 - ${monitor.size}"
            }
        }
    }

    data class MonitoringData(
        val customData: CustomData? = null,
        val component: Component,
        val variable: Variable,
        val variableMonitoring: List<VariableMonitoring>
    ) {

        init {
            require(variableMonitoring.isNotEmpty()) {
                "variableMonitoring length < minimum 1 - ${variableMonitoring.size}"
            }
        }
    }

    data class VariableMonitoring(
        val customData: CustomData? = null,
        /** Identifies the monitor. */
        val id: Long,
        /** Monitor only active when a transaction is ongoing on a component relevant to this transaction. */
        val transaction: Boolean,
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
        val severity: Long
    )

    enum class Monitor {
        UpperThreshold,
        LowerThreshold,
        Delta,
        Periodic,
        PeriodicClockAligned
    }
}

data class NotifyMonitoringReportResponse(
    val customData: CustomData? = null
) : OcppConfirmation
