package com.monta.library.ocpp.common.profile

interface Feature {
    val name: String
    val requestType: Class<out OcppRequest>
    val confirmationType: Class<out OcppConfirmation>
}
