package com.monta.library.ocpp.common.chargingprofile

/**
 * Shared definition between
 * 1.6
 * 2.0.1
 *
 * Unit of measure for the charging rate.
 */
enum class ChargingRateUnit {
    W,
    A,

    // for Alpitronic: an extension to OCPP capability is provided to control reactive power:
    // if “VAR” is specified as unit of measure, the profile will be treated as a reactive profile
    // and it will be added to a dedicated profile database.
    VAR
}
