package com.monta.library.ocpp.common.profile

import com.monta.library.ocpp.common.session.OcppSession

interface ProfileDispatcher {
    val featureList: List<Feature>
    suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation
}
