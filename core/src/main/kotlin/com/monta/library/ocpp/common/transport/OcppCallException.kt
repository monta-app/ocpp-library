package com.monta.library.ocpp.common.transport

import com.monta.library.ocpp.common.error.OcppErrorCode
import com.monta.library.ocpp.common.serialization.Message

data class OcppCallException(
    val errorCode: OcppErrorCode,
    val msg: String? = null,
    val throwable: Throwable? = null
) : Exception(msg ?: errorCode.getMessage(), throwable) {

    companion object {
        fun fromException(
            ocppErrorCode: OcppErrorCode,
            throwable: Throwable?
        ): OcppCallException {
            return OcppCallException(
                errorCode = ocppErrorCode,
                msg = null,
                throwable = throwable
            )
        }
    }

    fun toMessageError(
        uniqueId: String
    ): Message {
        return Message.Error(
            uniqueId = uniqueId,
            errorCode = errorCode.getName(),
            errorDescription = msg ?: errorCode.getMessage()
        )
    }
}
