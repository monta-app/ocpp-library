package com.monta.library.ocpp.common.serialization

import com.monta.library.ocpp.common.error.OcppErrorCode

sealed class ParsingResult<T> {
    data class Success<T>(
        val value: T
    ) : ParsingResult<T>()

    data class Failure<T>(
        val uniqueId: String,
        val messageErrorCode: OcppErrorCode,
        val throwable: Throwable
    ) : ParsingResult<T>() {
        fun toError(): Message.Error {
            return Message.Error(
                uniqueId = this.uniqueId,
                errorCode = this.messageErrorCode.getName(),
                errorDescription = this.messageErrorCode.getMessage()
            )
        }
    }
}
