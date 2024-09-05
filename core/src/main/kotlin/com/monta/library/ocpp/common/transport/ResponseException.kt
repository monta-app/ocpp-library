package com.monta.library.ocpp.common.transport

data class ResponseException(
    val errorCode: String,
    val errorDescription: String,
    val errorDetails: String
) : Exception("$errorCode ($errorDescription)")
