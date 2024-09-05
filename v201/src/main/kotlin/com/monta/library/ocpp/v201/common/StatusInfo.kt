package com.monta.library.ocpp.v201.common

data class StatusInfo(
    /**
     * A predefined code for the reason why the status is returned in this response.
     * The string is case-insensitive.
     * */
    val reasonCode: String,
    /** Additional text to provide detailed information. */
    val additionalInfo: String? = null,
    val customData: CustomData? = null
) {

    init {
        require(reasonCode.length <= 20) {
            "reasonCode length > maximum 20 - ${reasonCode.length}"
        }
        if (additionalInfo != null) {
            require(additionalInfo.length <= 512) {
                "additionalInfo length > maximum 512 - ${additionalInfo.length}"
            }
        }
    }
}
