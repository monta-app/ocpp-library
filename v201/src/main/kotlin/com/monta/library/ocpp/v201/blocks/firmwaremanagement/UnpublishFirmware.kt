package com.monta.library.ocpp.v201.blocks.firmwaremanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData

object UnpublishFirmwareFeature : Feature {
    override val name: String = "UnpublishFirmware"
    override val requestType: Class<out OcppRequest> = UnpublishFirmwareRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = UnpublishFirmwareResponse::class.java
}

data class UnpublishFirmwareRequest(

    /**
     * The MD5 checksum over the entire firmware file as a hexadecimal string of length 32.
     **/
    val checksum: String,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(checksum.length <= 32) {
            "checksum length > maximum 32 - ${checksum.length}"
        }
    }
}

data class UnpublishFirmwareResponse(
    val status: Status,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        DownloadOngoing,
        NoFirmware,
        Unpublished
    }
}
