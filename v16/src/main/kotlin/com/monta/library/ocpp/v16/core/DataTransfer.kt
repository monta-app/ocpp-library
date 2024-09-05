package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

/**
 * If a Charge Point needs to send information to the Central System for a function not supported by
 * OCPP, it SHALL use the DataTransfer.req PDU.
 *
 * The vendorId in the request SHOULD be known to the Central System and uniquely identify the
 * vendor-specific implementation. The VendorId SHOULD be a value from the reversed DNS namespace,
 * where the top tiers of the name, when reversed, should correspond to the publicly registered primary
 * DNS name of the Vendor organisation.
 *
 * Optionally, the messageId in the request PDU MAY be used to indicate a specific message or
 * implementation.
 *
 * The length of data in both the request and response PDU is undefined and should be agreed upon by all
 * parties involved.
 *
 * If the recipient of the request has no implementation for the specific vendorId it SHALL return a status
 * ‘UnknownVendor’ and the data element SHALL not be present. In case of a messageId mismatch (if
 * used) the recipient SHALL return status ‘UnknownMessageId’. In all other cases the usage of status
 * ‘Accepted’ or ‘Rejected’ and the data element is part of the vendor-specific agreement between the
 * parties involved.
 */
object DataTransferFeature : Feature {
    override val name: String = "DataTransfer"
    override val requestType: Class<out OcppRequest> = DataTransferRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = DataTransferConfirmation::class.java
}

data class DataTransferRequest(
    /**
     * String[255]
     *
     * Required
     *
     * This identifies the Vendor specific implementation
     */
    var vendorId: String,
    /**
     * String[50]
     *
     * Optional
     *
     * Additional identification field
     */
    var messageId: String? = null,
    /**
     * Optional
     *
     * Data without specified length or format.
     */
    var data: String? = null
) : OcppRequest

enum class DataTransferStatus {
    /**
     * Message has been accepted and the contained request is accepted.
     */
    Accepted,

    /**
     * Message has been accepted but the contained request is rejected.
     */
    Rejected,

    /**
     * Message could not be interpreted due to unknown messageId string.
     */
    UnknownMessageId,

    /**
     * Message could not be interpreted due to unknown vendorId string
     */
    UnknownVendorId
}

class DataTransferConfirmation(
    /**
     * Required
     *
     * This indicates the success or failure of the data transfer
     */
    val status: DataTransferStatus,
    /**
     * Optional
     *
     * Data in response to request.
     */
    val data: String? = null
) : OcppConfirmation
