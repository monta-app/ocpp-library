package com.monta.library.ocpp.v16.localauth

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16

class LocalListServerProfile : ProfileDispatcher {
    override val featureList: List<Feature> = listOf(
        GetLocalListVersionFeature,
        SendLocalAuthListFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV16.NotSupported)
    }

    interface Sender {
        suspend fun getLocalList(
            request: GetLocalListVersionRequest
        ): GetLocalListVersionConfirmation

        suspend fun sendLocalList(
            request: SendLocalListRequest
        ): SendLocalListConfirmation
    }
}

class LocalListClientProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        GetLocalListVersionFeature,
        SendLocalAuthListFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is GetLocalListVersionRequest -> listener.getLocalListVersion(ocppSessionInfo, request)
            is SendLocalListRequest -> listener.sendLocalList(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported)
        }
    }

    interface Listener {
        suspend fun getLocalListVersion(
            ocppSessionInfo: OcppSession.Info,
            request: GetLocalListVersionRequest
        ): GetLocalListVersionConfirmation

        suspend fun sendLocalList(
            ocppSessionInfo: OcppSession.Info,
            request: SendLocalListRequest
        ): SendLocalListConfirmation
    }
}
