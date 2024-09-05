package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import java.time.ZonedDateTime

object NotifyCustomerInformationFeature : Feature {
    override val name: String = "NotifyCustomerInformation"
    override val requestType: Class<out OcppRequest> = NotifyCustomerInformationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyCustomerInformationResponse::class.java
}

data class NotifyCustomerInformationRequest(
    /** (Part of) the requested data. No format specified in which the data is returned. Should be human readable. */
    val data: String,
    /** “to be continued” indicator. Indicates whether another part of the monitoringData follows in an upcoming notifyMonitoringReportRequest message. Default value when omitted is false. */
    val tbc: Boolean = false,
    /** Sequence number of this message. First message starts at 0. */
    val seqNo: Long,
    /** Timestamp of the moment this message was generated at the Charging Station. */
    val generatedAt: ZonedDateTime,
    /** The Id of the request. */
    val requestId: Long,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(data.length <= 512) {
            "data length > maximum 512 - ${data.length}"
        }
    }
}

data class NotifyCustomerInformationResponse(
    val customData: CustomData? = null
) : OcppConfirmation
