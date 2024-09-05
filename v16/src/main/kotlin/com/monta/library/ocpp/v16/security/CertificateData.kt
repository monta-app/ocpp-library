package com.monta.library.ocpp.v16.security

enum class HashAlgorithmEnumType {
    /**
     * SHA-256 hash algorithm.
     */
    SHA256,

    /**
     * SHA-384 hash algorithm.
     */
    SHA384,

    /**
     * SHA-512 hash algorithm.
     */
    SHA512
}

enum class CertificateUseEnumType {
    /**
     * Root certificate, used by the CA to sign the Central System and Charge Point certificate.
     */
    CentralSystemRootCertificate,

    /**
     * Root certificate for verification of the Manufacturer certificate.
     */
    ManufacturerRootCertificate
}

data class CertificateHashDataType(
    /**
     * Required.
     *
     * Used algorithms for the hashes provided.
     */
    val hashAlgorithm: HashAlgorithmEnumType,

    /**
     * Required.
     *
     * The hash of the issuer’s distinguished name (DN), that
     * must be calculated over the DER encoding of the issuer’s name field
     * in the certificate being checked. The hash is represented in hexbinary
     * format (i.e. each byte is represented by 2 hexadecimal digits).
     * Please refer to the OCSP specification: RFC6960.
     */
    val issuerNameHash: String,

    /**
     * Required.
     *
     * The hash of the DER encoded public key: the value
     * (excluding tag and length) of the subject public key field in the
     * issuer’s certificate. The hash is represented in hexbinary format (i.e.
     * each byte is represented by 2 hexadecimal digits).
     * Please refer to the OCSP specification: RFC6960.
     */
    val issuerKeyHash: String,

    /**
     * Required.
     *
     * The serial number as a hexadecimal string without leading
     * zeroes (and without the prefix 0x).
     *
     * For example: the serial number with decimal value 4095 will be
     * represented as “FFF”.
     *
     * Please note: The serial number of a certificate is a non-negative
     * integer of at most 20 bytes. Since this is too large to be handled as a
     * number in many system, it is represented as a string that contains
     * the hexadecimal representation of this number. The string shall not
     * have any leading zeroes.
     */
    val serialNumber: String
)
