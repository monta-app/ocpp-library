package com.monta.library.ocpp.v16.extension

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.session.OcppSession

interface ExtensionProfileDispatcher {
    /**
     * When intercepting data transfer messages which vendor id should this profile listen for
     */
    val vendorId: String

    /**
     *  A list of all features that are under this profile
     */
    val featureNameMap: Map<String, Feature>

    private val requestClassToFeature: Map<Class<out OcppRequest>, Feature>
        get() = featureNameMap.values.associateBy { it.requestType }

    suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation

    fun getRequestType(name: String): Class<out OcppRequest>?

    fun getConfirmationType(name: String): Class<out OcppConfirmation>?

    fun getFeature(clazz: Class<out OcppRequest>): Feature? {
        return requestClassToFeature[clazz]
    }
}
