package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericDeviceModelStatus
import com.monta.library.ocpp.v201.common.StatusInfo

object SetMonitoringBaseFeature : Feature {
    override val name: String = "SetMonitoringBase"
    override val requestType: Class<out OcppRequest> = SetMonitoringBaseRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SetMonitoringBaseResponse::class.java
}

data class SetMonitoringBaseRequest(
    val monitoringBase: MonitoringBase,
    val customData: CustomData? = null
) : OcppRequest {

    enum class MonitoringBase {
        All,
        FactoryDefault,
        HardWiredOnly
    }
}

data class SetMonitoringBaseResponse(
    val status: GenericDeviceModelStatus,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation
