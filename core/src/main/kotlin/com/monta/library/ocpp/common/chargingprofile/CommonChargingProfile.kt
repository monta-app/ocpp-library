package com.monta.library.ocpp.common.chargingprofile

import java.time.Instant

data class CommonChargingProfile(
    val id: Int,
    val transactionId: String? = null,
    val stackLevel: Int,
    val chargingProfilePurpose: CommonChargingProfilePurpose,
    val chargingProfileKind: ChargingProfileKind,
    val recurrencyKind: RecurrencyKind? = null,
    val validFrom: Instant? = null,
    val validTo: Instant? = null,
    val chargingSchedule: Schedule
) {
    data class Schedule(
        val id: Int,
        val duration: Int? = null,
        val startSchedule: Instant? = null,
        val chargingRateUnit: ChargingRateUnit,
        val chargingSchedulePeriod: List<Period>,
        var minChargingRate: Double? = null
    )

    data class Period(
        val startPeriod: Int,
        val limit: Double,
        val numberPhases: Int = 3
    )
}
