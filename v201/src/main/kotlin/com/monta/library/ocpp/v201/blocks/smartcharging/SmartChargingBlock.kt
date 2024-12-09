package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val smartChargingFeatures = listOf(
    // CP to CSMS
    ClearedChargingLimitFeature,
    NotifyChargingLimitFeature,
    NotifyEVChargingNeedsFeature,
    NotifyEVChargingScheduleFeature,
    ReportChargingProfilesFeature,
    // CSMS to CP
    ClearChargingProfileFeature,
    GetChargingProfilesFeature,
    GetCompositeScheduleFeature,
    SetChargingProfileFeature
)

class SmartChargingClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = smartChargingFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ClearChargingProfileRequest -> listener.clearChargingProfile(ocppSessionInfo, request)
            is GetChargingProfilesRequest -> listener.getChargingProfiles(ocppSessionInfo, request)
            is GetCompositeScheduleRequest -> listener.getCompositeSchedule(ocppSessionInfo, request)
            is SetChargingProfileRequest -> listener.setChargingProfile(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun clearChargingProfile(
            ocppSessionInfo: OcppSession.Info,
            request: ClearChargingProfileRequest
        ): ClearChargingProfileResponse

        suspend fun getChargingProfiles(
            ocppSessionInfo: OcppSession.Info,
            request: GetChargingProfilesRequest
        ): GetChargingProfilesResponse

        suspend fun getCompositeSchedule(
            ocppSessionInfo: OcppSession.Info,
            request: GetCompositeScheduleRequest
        ): GetCompositeScheduleResponse

        suspend fun setChargingProfile(
            ocppSessionInfo: OcppSession.Info,
            request: SetChargingProfileRequest
        ): SetChargingProfileResponse
    }

    interface Sender {
        suspend fun clearedChargingLimit(
            request: ClearedChargingLimitRequest
        ): ClearedChargingLimitResponse

        suspend fun notifyChargingLimit(
            request: NotifyChargingLimitRequest
        ): NotifyChargingLimitResponse

        suspend fun notifyEVChargingNeeds(
            request: NotifyEVChargingNeedsRequest
        ): NotifyEVChargingNeedsResponse

        suspend fun notifyEVChargingSchedule(
            request: NotifyEVChargingScheduleRequest
        ): NotifyEVChargingScheduleResponse

        suspend fun reportChargingProfiles(
            request: ReportChargingProfilesRequest
        ): ReportChargingProfilesResponse
    }
}

class SmartChargingServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = smartChargingFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ClearedChargingLimitRequest -> listener.clearedChargingLimit(ocppSessionInfo, request)
            is NotifyChargingLimitRequest -> listener.notifyChargingLimit(ocppSessionInfo, request)
            is NotifyEVChargingNeedsRequest -> listener.notifyEVChargingNeeds(ocppSessionInfo, request)
            is NotifyEVChargingScheduleRequest -> listener.notifyEVChargingSchedule(ocppSessionInfo, request)
            is ReportChargingProfilesRequest -> listener.reportChargingProfiles(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun clearedChargingLimit(
            ocppSessionInfo: OcppSession.Info,
            request: ClearedChargingLimitRequest
        ): ClearedChargingLimitResponse

        suspend fun notifyChargingLimit(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyChargingLimitRequest
        ): NotifyChargingLimitResponse

        suspend fun notifyEVChargingNeeds(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyEVChargingNeedsRequest
        ): NotifyEVChargingNeedsResponse

        suspend fun notifyEVChargingSchedule(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyEVChargingScheduleRequest
        ): NotifyEVChargingScheduleResponse

        suspend fun reportChargingProfiles(
            ocppSessionInfo: OcppSession.Info,
            request: ReportChargingProfilesRequest
        ): ReportChargingProfilesResponse
    }

    interface Sender {
        suspend fun clearChargingProfile(
            request: ClearChargingProfileRequest
        ): ClearChargingProfileResponse

        suspend fun getChargingProfiles(
            request: GetChargingProfilesRequest
        ): GetChargingProfilesResponse

        suspend fun getCompositeSchedule(
            request: GetCompositeScheduleRequest
        ): GetCompositeScheduleResponse

        suspend fun setChargingProfile(
            request: SetChargingProfileRequest
        ): SetChargingProfileResponse
    }
}
