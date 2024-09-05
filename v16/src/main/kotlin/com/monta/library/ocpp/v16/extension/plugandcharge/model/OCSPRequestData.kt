package com.monta.library.ocpp.v16.extension.plugandcharge.model

/**
 * Contains the information needed to verify the EV Contract Certificate via OCSP.
 */
data class OCSPRequestData(
    // Required
    // Used algorithms for the hashes provided.
    val hashAlgorithm: HashAlgorithmType,
    // identifierString[0..128] Required
    // Hashed value of the Issuer DN (Distinguished Name).
    val issuerNameHash: String,
    // string[0..128] Required
    // Hashed value of the issuers public key
    val issuerKeyHash: String,
    // identifierString[0..40] Required
    // The serial number of the certificate.
    val serialNumber: String,
    // string[0..512] Required
    // This contains the responder URL (Case insensitive).
    val responderURL: String
)
