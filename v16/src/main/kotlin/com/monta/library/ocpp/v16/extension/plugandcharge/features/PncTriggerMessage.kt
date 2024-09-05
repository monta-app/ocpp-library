package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageConfirmation

/**
 * A PnC specific version of the trigger message. Only used for triggering a certificate signing
 * request for an ISO 15118 V2G certificate for the Charge Point.
 */
object PncTriggerMessageFeature : Feature {
    override val name: String = "TriggerMessage"
    override val requestType: Class<out OcppRequest> = PncTriggerMessageRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = TriggerMessageConfirmation::class.java
}

class PncTriggerMessageRequest : OcppRequest
