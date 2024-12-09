package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val provisioningFeatures = listOf(
    // CP to CSMS
    BootNotificationFeature,
    NotifyReportFeature,
    // CSMS to CP
    GetBaseReportFeature,
    GetReportFeature,
    GetVariablesFeature,
    ResetFeature,
    SetNetworkProfileFeature,
    SetVariablesFeature
)

class ProvisioningClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = provisioningFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is GetBaseReportRequest -> listener.getBaseReport(ocppSessionInfo, request)
            is GetReportRequest -> listener.getReport(ocppSessionInfo, request)
            is GetVariablesRequest -> listener.getVariables(ocppSessionInfo, request)
            is ResetRequest -> listener.reset(ocppSessionInfo, request)
            is SetNetworkProfileRequest -> listener.setNetworkProfile(ocppSessionInfo, request)
            is SetVariablesRequest -> listener.setVariables(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun getBaseReport(
            ocppSessionInfo: OcppSession.Info,
            request: GetBaseReportRequest
        ): GetBaseReportResponse

        suspend fun getReport(
            ocppSessionInfo: OcppSession.Info,
            request: GetReportRequest
        ): GetReportResponse

        suspend fun getVariables(
            ocppSessionInfo: OcppSession.Info,
            request: GetVariablesRequest
        ): GetVariablesResponse

        suspend fun reset(
            ocppSessionInfo: OcppSession.Info,
            request: ResetRequest
        ): ResetResponse

        suspend fun setNetworkProfile(
            ocppSessionInfo: OcppSession.Info,
            request: SetNetworkProfileRequest
        ): SetNetworkProfileResponse

        suspend fun setVariables(
            ocppSessionInfo: OcppSession.Info,
            request: SetVariablesRequest
        ): SetVariablesResponse
    }

    interface Sender {
        suspend fun bootNotification(
            request: BootNotificationRequest
        ): BootNotificationResponse

        suspend fun notifyReport(
            request: NotifyReportRequest
        ): NotifyReportResponse
    }
}

class ProvisioningServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {
    override val featureList = provisioningFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is BootNotificationRequest -> listener.bootNotification(ocppSessionInfo, request)
            is NotifyReportRequest -> listener.notifyReport(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun bootNotification(
            ocppSessionInfo: OcppSession.Info,
            request: BootNotificationRequest
        ): BootNotificationResponse

        suspend fun notifyReport(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyReportRequest
        ): NotifyReportResponse
    }

    interface Sender {
        suspend fun getBaseReport(
            request: GetBaseReportRequest
        ): GetBaseReportResponse

        suspend fun getReport(
            request: GetReportRequest
        ): GetReportResponse

        suspend fun getVariables(
            request: GetVariablesRequest
        ): GetVariablesResponse

        suspend fun reset(
            request: ResetRequest
        ): ResetResponse

        suspend fun setNetworkProfile(
            request: SetNetworkProfileRequest
        ): SetNetworkProfileResponse

        suspend fun setVariables(
            request: SetVariablesRequest
        ): SetVariablesResponse
    }
}
