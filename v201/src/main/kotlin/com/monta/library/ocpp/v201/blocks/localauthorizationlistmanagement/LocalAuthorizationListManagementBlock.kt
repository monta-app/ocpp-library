package com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val localAuthorizationListManagementFeatures = listOf(
    // CP to CSMS: none
    // CSMS to CP
    ClearCacheFeature,
    GetLocalListVersionFeature,
    SendLocalListFeature
)

class LocalAuthorizationListManagementClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = localAuthorizationListManagementFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ClearCacheRequest -> listener.clearCache(ocppSessionInfo, request)
            is GetLocalListVersionRequest -> listener.getLocalListVersion(ocppSessionInfo, request)
            is SendLocalListRequest -> listener.sendLocalList(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported)
        }
    }

    interface Listener {
        suspend fun clearCache(
            ocppSessionInfo: OcppSession.Info,
            request: ClearCacheRequest
        ): ClearCacheResponse

        suspend fun getLocalListVersion(
            ocppSessionInfo: OcppSession.Info,
            request: GetLocalListVersionRequest
        ): GetLocalListVersionResponse

        suspend fun sendLocalList(
            ocppSessionInfo: OcppSession.Info,
            request: SendLocalListRequest
        ): SendLocalListResponse
    }
}

class LocalAuthorizationListManagementServerDispatcher : ProfileDispatcher {

    override val featureList = localAuthorizationListManagementFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV201.NotSupported)
    }

    interface Sender {
        suspend fun clearCache(
            request: ClearCacheRequest
        ): ClearCacheResponse

        suspend fun getLocalListVersion(
            request: GetLocalListVersionRequest
        ): GetLocalListVersionResponse

        suspend fun sendLocalList(
            request: SendLocalListRequest
        ): SendLocalListResponse
    }
}
