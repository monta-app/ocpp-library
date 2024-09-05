package com.monta.library.ocpp.common.chargingprofile

/**
 * Shared definition between
 * 1.6
 * 2.0.1
 *
 * The charging profile type, is it relative to the transaction, absolute or recurring.
 *
 */
enum class ChargingProfileKind {
    Absolute,
    Recurring,
    Relative
}
