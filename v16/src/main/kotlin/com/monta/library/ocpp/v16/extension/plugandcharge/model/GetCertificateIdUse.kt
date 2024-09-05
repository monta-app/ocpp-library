package com.monta.library.ocpp.v16.extension.plugandcharge.model

enum class GetCertificateIdUse {
    // Use for certificate of the V2G Root
    V2GRootCertificate,

    // Use for certificate from an eMobility Service provider.
    // To support PnC charging with contracts from service providers that not derived their certificates from the V2G root.
    MORootCertificate,

    // ISO 15118 V2G certificate chain (excluding the V2GRootCertificate)
    V2GCertificateChain
}
