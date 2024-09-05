package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.Variable
import java.time.ZonedDateTime

object NotifyEventFeature : Feature {
    override val name: String = "NotifyEvent"
    override val requestType: Class<out OcppRequest> = NotifyEventRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyEventResponse::class.java
}

data class NotifyEventRequest(
    /** Timestamp of the moment this message was generated at the Charging Station. */
    val generatedAt: ZonedDateTime,
    /** “to be continued” indicator. Indicates whether another part of the report follows in an upcoming notifyEventRequest message. Default value when omitted is false. */
    val tbc: Boolean = false,
    /** Sequence number of this message. First message starts at 0. */
    val seqNo: Long,
    val eventData: List<EventData>,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(eventData.isNotEmpty()) {
            "eventData length < minimum 1 - ${eventData.size}"
        }
    }

    data class EventData(
        val customData: CustomData? = null,
        /** Identifies the event. This field can be referred to as a cause by other events. */
        val eventId: Long,
        /** Timestamp of the moment the report was generated. */
        val timestamp: ZonedDateTime,
        val trigger: Trigger,
        /** Refers to the Id of an event that is considered to be the cause for this event. */
        val cause: Long? = null,
        /** Actual value (_attributeType_ Actual) of the variable.

         The Configuration Variable "configkey-reporting-value-size,ReportingValueSize" can be used to limit GetVariableResult.attributeValue, VariableAttribute.value and EventData.actualValue. The max size of these values will always remain equal. */
        val actualValue: String,
        /** Technical (error) code as reported by component. */
        val techCode: String? = null,
        /** Technical detail information as reported by component. */
        val techInfo: String? = null,
        /** _Cleared_ is set to true to report the clearing of a monitored situation, i.e. a 'return to normal'. */
        val cleared: Boolean? = null,
        /** If an event notification is linked to a specific transaction, this field can be used to specify its transactionId. */
        val transactionId: String? = null,
        val component: Component,
        /** Identifies the VariableMonitoring which triggered the event. */
        val variableMonitoringId: Long? = null,
        val eventNotificationType: EventNotificationType,
        val variable: Variable
    ) {

        init {
            require(actualValue.length <= 2500) {
                "actualValue length > maximum 2500 - ${actualValue.length}"
            }
            if (techCode != null) {
                require(techCode.length <= 50) {
                    "techCode length > maximum 50 - ${techCode.length}"
                }
            }
            if (techInfo != null) {
                require(techInfo.length <= 500) {
                    "techInfo length > maximum 500 - ${techInfo.length}"
                }
            }
            if (transactionId != null) {
                require(transactionId.length <= 36) {
                    "transactionId length > maximum 36 - ${transactionId.length}"
                }
            }
        }
    }

    enum class Trigger {
        Alerting,
        Delta,
        Periodic
    }

    enum class EventNotificationType {
        HardWiredNotification,
        HardWiredMonitor,
        PreconfiguredMonitor,
        CustomMonitor
    }
}

data class NotifyEventResponse(
    val customData: CustomData? = null
) : OcppConfirmation
