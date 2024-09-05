package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val diagnosticsFeatures = listOf(
    // CP to CSMS
    LogStatusNotificationFeature,
    NotifyCustomerInformationFeature,
    NotifyEventFeature,
    NotifyMonitoringReportFeature,
    // CSMS to CP
    ClearVariableMonitoringFeature,
    CustomerInformationFeature,
    GetLogFeature,
    GetMonitoringReportFeature,
    SetMonitoringBaseFeature,
    SetMonitoringLevelFeature,
    SetVariableMonitoringFeature
)

class DiagnosticsClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = diagnosticsFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ClearVariableMonitoringRequest -> listener.clearVariableMonitoring(ocppSessionInfo, request)
            is CustomerInformationRequest -> listener.customerInformation(ocppSessionInfo, request)
            is GetLogRequest -> listener.getLog(ocppSessionInfo, request)
            is GetMonitoringReportRequest -> listener.getMonitoringReport(ocppSessionInfo, request)
            is SetMonitoringBaseRequest -> listener.setMonitoringBase(ocppSessionInfo, request)
            is SetMonitoringLevelRequest -> listener.setMonitoringLevel(ocppSessionInfo, request)
            is SetVariableMonitoringRequest -> listener.setVariableMonitoring(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported)
        }
    }

    interface Listener {
        suspend fun clearVariableMonitoring(
            ocppSessionInfo: OcppSession.Info,
            request: ClearVariableMonitoringRequest
        ): ClearVariableMonitoringResponse

        suspend fun customerInformation(
            ocppSessionInfo: OcppSession.Info,
            request: CustomerInformationRequest
        ): CustomerInformationResponse

        suspend fun getLog(
            ocppSessionInfo: OcppSession.Info,
            request: GetLogRequest
        ): GetLogResponse

        suspend fun getMonitoringReport(
            ocppSessionInfo: OcppSession.Info,
            request: GetMonitoringReportRequest
        ): GetMonitoringReportResponse

        suspend fun setMonitoringBase(
            ocppSessionInfo: OcppSession.Info,
            request: SetMonitoringBaseRequest
        ): SetMonitoringBaseResponse

        suspend fun setMonitoringLevel(
            ocppSessionInfo: OcppSession.Info,
            request: SetMonitoringLevelRequest
        ): SetMonitoringLevelResponse

        suspend fun setVariableMonitoring(
            ocppSessionInfo: OcppSession.Info,
            request: SetVariableMonitoringRequest
        ): SetVariableMonitoringResponse
    }

    interface Sender {
        suspend fun logStatusNotification(
            request: LogStatusNotificationRequest
        ): LogStatusNotificationResponse

        suspend fun notifyCustomerInformation(
            request: NotifyCustomerInformationRequest
        ): NotifyCustomerInformationResponse

        suspend fun notifyEvent(
            request: NotifyEventRequest
        ): NotifyEventResponse

        suspend fun notifyMonitoringReport(
            request: NotifyMonitoringReportRequest
        ): NotifyMonitoringReportResponse
    }
}

class DiagnosticsServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = diagnosticsFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is LogStatusNotificationRequest -> listener.logStatusNotification(ocppSessionInfo, request)
            is NotifyCustomerInformationRequest -> listener.notifyCustomerInformation(ocppSessionInfo, request)
            is NotifyEventRequest -> listener.notifyEvent(ocppSessionInfo, request)
            is NotifyMonitoringReportRequest -> listener.notifyMonitoringReport(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported)
        }
    }

    interface Listener {
        suspend fun logStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: LogStatusNotificationRequest
        ): LogStatusNotificationResponse

        suspend fun notifyCustomerInformation(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyCustomerInformationRequest
        ): NotifyCustomerInformationResponse

        suspend fun notifyEvent(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyEventRequest
        ): NotifyEventResponse

        suspend fun notifyMonitoringReport(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyMonitoringReportRequest
        ): NotifyMonitoringReportResponse
    }

    interface Sender {
        suspend fun clearVariableMonitoring(
            request: ClearVariableMonitoringRequest
        ): ClearVariableMonitoringResponse

        suspend fun customerInformation(
            request: CustomerInformationRequest
        ): CustomerInformationResponse

        suspend fun getLog(
            request: GetLogRequest
        ): GetLogResponse

        suspend fun getMonitoringReport(
            request: GetMonitoringReportRequest
        ): GetMonitoringReportResponse

        suspend fun setMonitoringBase(
            request: SetMonitoringBaseRequest
        ): SetMonitoringBaseResponse

        suspend fun setMonitoringLevel(
            request: SetMonitoringLevelRequest
        ): SetMonitoringLevelResponse

        suspend fun setVariableMonitoring(
            request: SetVariableMonitoringRequest
        ): SetVariableMonitoringResponse
    }
}
