package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.AttributeType
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.Variable

object GetVariablesFeature : Feature {
    override val name: String = "GetVariables"
    override val requestType: Class<out OcppRequest> = GetVariablesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetVariablesResponse::class.java
}

data class GetVariablesRequest(
    val getVariableData: List<GetVariableData>,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(getVariableData.isNotEmpty()) {
            "getVariableData length < minimum 1 - ${getVariableData.size}"
        }
    }

    data class GetVariableData(
        val attributeType: AttributeType? = null,
        val component: Component,
        val variable: Variable,
        val customData: CustomData? = null
    )
}

data class GetVariablesResponse(
    val getVariableResult: List<GetVariableResult>,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        require(getVariableResult.isNotEmpty()) {
            "getVariableResult length < minimum 1 - ${getVariableResult.size}"
        }
    }

    data class GetVariableResult(
        val attributeStatus: AttributeStatus,
        val component: Component,
        val variable: Variable,
        val attributeStatusInfo: StatusInfo? = null,
        val attributeType: AttributeType = AttributeType.Actual,
        /**
         * Value of requested attribute type of component-variable. This field can only be empty when the given status is NOT accepted.
         * The Configuration Variable "configkey-reporting-value-size,ReportingValueSize" can be used to limit
         * GetVariableResult.attributeValue, VariableAttribute.value and EventData.actualValue. The max size of these values will always remain equal.
         **/
        val attributeValue: String? = null,
        val customData: CustomData? = null
    ) {

        init {
            if (attributeValue != null) {
                require(attributeValue.length <= 2500) {
                    "attributeValue length > maximum 2500 - ${attributeValue.length}"
                }
            }
        }
    }

    enum class AttributeStatus {
        Accepted,
        Rejected,
        UnknownComponent,
        UnknownVariable,
        NotSupportedAttributeType
    }
}
