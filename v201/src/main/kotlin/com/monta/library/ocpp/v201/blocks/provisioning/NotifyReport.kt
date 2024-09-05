package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.AttributeType
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.Variable
import java.math.BigDecimal
import java.time.ZonedDateTime

object NotifyReportFeature : Feature {
    override val name: String = "NotifyReport"
    override val requestType: Class<out OcppRequest> = NotifyReportRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyReportResponse::class.java
}

data class NotifyReportRequest(
    /** The id of the GetReportRequest  or GetBaseReportRequest that requested this report */
    val requestId: Long,
    /** Timestamp of the moment this message was generated at the Charging Station. */
    val generatedAt: ZonedDateTime,
    val reportData: List<ReportData>? = null,
    /** “to be continued” indicator. Indicates whether another part of the report follows in an upcoming notifyReportRequest message. Default value when omitted is false. */
    val tbc: Boolean = false,
    /** Sequence number of this message. First message starts at 0. */
    val seqNo: Long,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (reportData != null) {
            require(reportData.isNotEmpty()) {
                "reportData length < minimum 1 - ${reportData.size}"
            }
        }
    }

    data class ReportData(
        val customData: CustomData? = null,
        val component: Component,
        val variable: Variable,
        val variableAttribute: List<VariableAttribute>,
        val variableCharacteristics: VariableCharacteristics? = null
    ) {

        init {
            require(variableAttribute.size in 1..4) {
                "variableAttribute length not in range 1..4 - ${variableAttribute.size}"
            }
        }
    }

    data class VariableAttribute(
        val customData: CustomData? = null,
        val type: AttributeType = AttributeType.Actual,
        /** Value of the attribute. May only be omitted when mutability is set to 'WriteOnly'.

         The Configuration Variable "configkey-reporting-value-size,ReportingValueSize" can be used to limit GetVariableResult.attributeValue, VariableAttribute.value and EventData.actualValue. The max size of these values will always remain equal. */
        val value: String? = null,
        val mutability: Mutability = Mutability.ReadWrite,
        /** If true, value will be persistent across system reboots or power down. Default when omitted is false. */
        val persistent: Boolean = false,
        /** If true, value that will never be changed by the Charging Station at runtime. Default when omitted is false. */
        val constant: Boolean = false
    ) {

        init {
            if (value != null) {
                require(value.length <= 2500) {
                    "value length > maximum 2500 - ${value.length}"
                }
            }
        }
    }

    enum class Mutability {
        ReadOnly,
        WriteOnly,
        ReadWrite
    }

    data class VariableCharacteristics(
        val customData: CustomData? = null,
        /** Unit of the variable. When the transmitted value has a unit, this field SHALL be included. */
        val unit: String? = null,
        val dataType: DataType,
        /** Minimum possible value of this variable. */
        val minLimit: BigDecimal? = null,
        /** Maximum possible value of this variable. When the datatype of this Variable is String, OptionList, SequenceList or MemberList, this field defines the maximum length of the (CSV) string. */
        val maxLimit: BigDecimal? = null,
        /** Allowed values when variable is Option/Member/SequenceList.

         * OptionList: The (Actual) Variable value must be a single value from the reported (CSV) enumeration list.

         * MemberList: The (Actual) Variable value  may be an (unordered) (sub-)set of the reported (CSV) valid values list.

         * SequenceList: The (Actual) Variable value  may be an ordered (priority, etc)  (sub-)set of the reported (CSV) valid values.

         This is a comma separated list.

         The Configuration Variable "configkey-configuration-value-size,ConfigurationValueSize" can be used to limit SetVariableData.attributeValue and VariableCharacteristics.valueList. The max size of these values will always remain equal. */
        val valuesList: String? = null,
        /** Flag indicating if this variable supports monitoring. */
        val supportsMonitoring: Boolean
    ) {

        init {
            if (unit != null) {
                require(unit.length <= 16) {
                    "unit length > maximum 16 - ${unit.length}"
                }
            }
            if (valuesList != null) {
                require(valuesList.length <= 1000) {
                    "valuesList length > maximum 1000 - ${valuesList.length}"
                }
            }
        }
    }

    @Suppress("EnumEntryName")
    enum class DataType {
        string,
        decimal,
        integer,
        dateTime,
        boolean,
        OptionList,
        SequenceList,
        MemberList
    }
}

data class NotifyReportResponse(
    val customData: CustomData? = null
) : OcppConfirmation
