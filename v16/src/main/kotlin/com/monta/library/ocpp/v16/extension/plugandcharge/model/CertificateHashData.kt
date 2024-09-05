package com.monta.library.ocpp.v16.extension.plugandcharge.model

data class CertificateHashData(
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
    val serialNumber: String
)
