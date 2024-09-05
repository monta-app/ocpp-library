package com.monta.library.ocpp.v201.common

data class OCSPRequestData(
    val hashAlgorithm: HashAlgorithm,
    /** Hashed value of the Issuer DN (Distinguished Name). */
    val issuerNameHash: String,
    /** Hashed value of the issuers public key */
    val issuerKeyHash: String,
    /** The serial number of the certificate. */
    val serialNumber: String,
    /** This contains the responder URL (Case insensitive). */
    val responderURL: String,
    val customData: CustomData? = null
) {

    init {
        require(issuerNameHash.length <= 128) {
            "issuerNameHash length > maximum 128 - ${issuerNameHash.length}"
        }
        require(issuerKeyHash.length <= 128) {
            "issuerKeyHash length > maximum 128 - ${issuerKeyHash.length}"
        }
        require(serialNumber.length <= 40) {
            "serialNumber length > maximum 40 - ${serialNumber.length}"
        }
        require(responderURL.length <= 512) {
            "responderURL length > maximum 512 - ${responderURL.length}"
        }
    }
}
