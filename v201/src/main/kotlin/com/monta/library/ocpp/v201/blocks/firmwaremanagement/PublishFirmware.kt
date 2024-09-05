package com.monta.library.ocpp.v201.blocks.firmwaremanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericStatus
import com.monta.library.ocpp.v201.common.StatusInfo

object PublishFirmwareFeature : Feature {
    override val name: String = "PublishFirmware"
    override val requestType: Class<out OcppRequest> = PublishFirmwareRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = PublishFirmwareResponse::class.java
}

data class PublishFirmwareRequest(
    /** This contains a string containing a URI pointing to a
     location from which to retrieve the firmware. */
    val location: String,
    /** This specifies how many times Charging Station must try
     to download the firmware before giving up. If this field is not
     present, it is left to Charging Station to decide how many times it wants to retry. */
    val retries: Long? = null,
    /** The MD5 checksum over the entire firmware file as a hexadecimal string of length 32. */
    val checksum: String,
    /** The Id of the request. */
    val requestId: Long,
    /** The interval in seconds
     after which a retry may be
     attempted. If this field is not
     present, it is left to Charging
     Station to decide how long to wait
     between attempts. */
    val retryInterval: Long? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(location.length <= 512) {
            "location length > maximum 512 - ${location.length}"
        }
        require(checksum.length <= 32) {
            "checksum length > maximum 32 - ${checksum.length}"
        }
    }
}

data class PublishFirmwareResponse(
    val status: GenericStatus,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation
