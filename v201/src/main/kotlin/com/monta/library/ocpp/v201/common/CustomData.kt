package com.monta.library.ocpp.v201.common

class CustomData : HashMap<String, String?>() {

    val vendorId: String
        get() = requireNotNull(get("vendorId"))

    init {
        require(vendorId.length <= 255) {
            "vendorId length > maximum 255 - ${vendorId.length}"
        }
    }
}
