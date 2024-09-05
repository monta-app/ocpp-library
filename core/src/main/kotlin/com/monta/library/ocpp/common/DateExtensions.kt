package com.monta.library.ocpp.common

import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

fun Instant?.toZonedDateTime(): ZonedDateTime? {
    return this?.let { instant ->
        ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
    }
}
