package com.monta.library.ocpp.v16.extension.plugandcharge.model

data class CertificateHashDataChain(
    // Required
    // Indicates the type of the requested certificate(s).
    val certificateType: GetCertificateIdUse,
    // Required
    // Information to identify a certificate.
    val certificateHashData: CertificateHashData,
    // Optional
    // Information to identify the child certificate(s).
    // Max length: 4
    val childCertificateHashData: List<CertificateHashData>?
)
