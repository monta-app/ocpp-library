package com.monta.library.ocpp.common.transport

import com.monta.library.ocpp.common.session.OcppSession
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class OcppSettings(
    /**
     * The default message timeout
     */
    var defaultMessageTimeout: Duration = 30.toDuration(DurationUnit.SECONDS),

    /**
     * The default size of the `send` channel
     */
    var defaultSendChannelSize: Int = 8,

    /**
     * should dates use nanoseconds
     */
    var nanoSecondDates: Boolean = false,

    var ocppExceptionHandler: (
        ocppSession: OcppSession.Info,
        context: CoroutineContext,
        exception: Throwable
    ) -> Unit = { _, _, _ -> }
)
