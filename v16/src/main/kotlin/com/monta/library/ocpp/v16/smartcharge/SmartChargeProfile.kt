package com.monta.library.ocpp.v16.smartcharge

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16

class SmartChargeServerProfile : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        ClearChargingProfileFeature,
        GetCompositeScheduleFeature,
        SetChargingProfileFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV16.NotSupported)
    }

    interface Sender {
        suspend fun clearChargingProfile(
            request: ClearChargingProfileRequest
        ): ClearChargingProfileConfirmation

        suspend fun getCompositeSchedule(
            request: GetCompositeScheduleRequest
        ): GetCompositeScheduleConfirmation

        suspend fun setChargingProfile(
            request: SetChargingProfileRequest
        ): SetChargingProfileConfirmation
    }
}

class SmartChargeClientProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        ClearChargingProfileFeature,
        GetCompositeScheduleFeature,
        SetChargingProfileFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ClearChargingProfileRequest -> listener.clearChargingProfile(ocppSessionInfo, request)
            is GetCompositeScheduleRequest -> listener.getCompositeSchedule(ocppSessionInfo, request)
            is SetChargingProfileRequest -> listener.setChargingProfile(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported)
        }
    }

    interface Listener {
        suspend fun clearChargingProfile(
            ocppSessionInfo: OcppSession.Info,
            request: ClearChargingProfileRequest
        ): ClearChargingProfileConfirmation

        suspend fun getCompositeSchedule(
            ocppSessionInfo: OcppSession.Info,
            request: GetCompositeScheduleRequest
        ): GetCompositeScheduleConfirmation

        suspend fun setChargingProfile(
            ocppSessionInfo: OcppSession.Info,
            request: SetChargingProfileRequest
        ): SetChargingProfileConfirmation
    }
}
