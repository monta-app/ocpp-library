package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

/**
 * Central System can request a Charge Point to clear its Authorization Cache. The Central System SHALL
 * send a ClearCache.req PDU for clearing the Charge Point’s Authorization Cache.
 *
 * Upon receipt of a ClearCache.req PDU, the Charge Point SHALL respond with a ClearCache.conf PDU.
 * The response PDU SHALL indicate whether the Charge Point was able to clear its Authorization Cache.
 */
object ClearCacheFeature : Feature {
    override val name: String = "ClearCache"
    override val requestType: Class<out OcppRequest> = ClearCacheRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ClearCacheConfirmation::class.java
}

/**
 * No fields are defined
 */
object ClearCacheRequest : OcppRequest

enum class ClearCacheStatus {
    /**
     * Command has been executed.
     */
    Accepted,

    /**
     * Command has not been executed.
     */
    Rejected
}

data class ClearCacheConfirmation(
    /**
     * Required
     *
     * Accepted if the Charge Point has executed the request, otherwise rejected.
     */
    val status: ClearCacheStatus
) : OcppConfirmation
