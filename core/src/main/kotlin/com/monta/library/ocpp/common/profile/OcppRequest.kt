package com.monta.library.ocpp.common.profile

interface OcppRequest {

    /**
     * Return the OCPP action for the request. Used for logging, exceptions and debugging.
     *
     * By convention all our request classes end with "Request" suffix.
     */
    fun actionName(): String {
        val className = this::class.java.simpleName
        if (className.endsWith("Request")) {
            return className.dropLast(7)
        }
        return className
    }
}
