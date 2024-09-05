package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.AttributeType
import com.monta.library.ocpp.v201.common.Component
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.Variable

object SetVariablesFeature : Feature {
    override val name: String = "SetVariables"
    override val requestType: Class<out OcppRequest> = SetVariablesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SetVariablesResponse::class.java
}

data class SetVariablesRequest(
    val setVariableData: List<SetVariableData>,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(setVariableData.isNotEmpty()) {
            "setVariableData length < minimum 1 - ${setVariableData.size}"
        }
    }

    data class SetVariableData(
        val attributeType: AttributeType = AttributeType.Actual,
        /**
         * Value to be assigned to attribute of variable.
         * The Configuration Variable "configkey-configuration-value-size,ConfigurationValueSize" can be used to limit SetVariableData.attributeValue and VariableCharacteristics.valueList.
         * The max size of these values will always remain equal.
         **/
        val attributeValue: String,
        val component: Component,
        val variable: Variable,
        val customData: CustomData? = null
    ) {

        init {
            require(attributeValue.length <= 1000) {
                "attributeValue length > maximum 1000 - ${attributeValue.length}"
            }
        }
    }
}

data class SetVariablesResponse(
    val setVariableResult: List<SetVariableResult>,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        require(setVariableResult.isNotEmpty()) {
            "setVariableResult length < minimum 1 - ${setVariableResult.size}"
        }
    }

    data class SetVariableResult(
        val attributeType: AttributeType = AttributeType.Actual,
        val attributeStatus: AttributeStatus,
        val attributeStatusInfo: StatusInfo? = null,
        val component: Component,
        val variable: Variable,
        val customData: CustomData? = null
    )

    enum class AttributeStatus {
        Accepted,
        Rejected,
        UnknownComponent,
        UnknownVariable,
        NotSupportedAttributeType,
        RebootRequired
    }
}
