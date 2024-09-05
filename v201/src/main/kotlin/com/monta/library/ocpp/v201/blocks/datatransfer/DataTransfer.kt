package com.monta.library.ocpp.v201.blocks.datatransfer

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object DataTransferFeature : Feature {
    override val name: String = "DataTransfer"
    override val requestType: Class<out OcppRequest> = DataTransferRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = DataTransferResponse::class.java
}

data class DataTransferRequest(
    /** May be used to indicate a specific message or implementation. */
    val messageId: String? = null,
    /** Data without specified length or format. This needs to be decided by both parties (Open to implementation). */
    val data: Any? = null,
    /** This identifies the Vendor specific implementation */
    val vendorId: String,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (messageId != null) {
            require(messageId.length <= 50) {
                "messageId length > maximum 50 - ${messageId.length}"
            }
        }
        require(vendorId.length <= 255) {
            "vendorId length > maximum 255 - ${vendorId.length}"
        }
    }
}

data class DataTransferResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    /** Data without specified length or format, in response to request. */
    val data: Any? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected,
        UnknownMessageId,
        UnknownVendorId
    }
}
