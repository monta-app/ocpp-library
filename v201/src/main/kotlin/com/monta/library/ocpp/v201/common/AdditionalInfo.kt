package com.monta.library.ocpp.v201.common

data class AdditionalInfo(
    /** This field specifies the additional IdToken. */
    val additionalIdToken: String,
    /** This defines the type of the additionalIdToken. This is a custom type, so the implementation needs to be agreed upon by all involved parties. */
    val type: String,
    val customData: CustomData? = null
) {
    init {
        require(additionalIdToken.length <= 36) {
            "additionalIdToken length > maximum 36 - ${additionalIdToken.length}"
        }
        require(type.length <= 50) {
            "type length > maximum 50 - ${type.length}"
        }
    }
}
