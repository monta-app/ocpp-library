package com.monta.library.ocpp.common.error

interface OcppErrorResponder {
    /**
     * Returns an OcppErrorCode representing an internal error.
     *
     * @return The OcppErrorCode object representing an internal error.
     */
    fun getInternalError(): OcppErrorCode

    /**
     * Returns an OcppErrorCode representing a JSON format error.
     *
     * @return The OcppErrorCode object representing a JSON format error.
     */
    fun getJsonFormatError(): OcppErrorCode

    /**
     * Retrieves an OcppErrorCode representing a property constraint violation error.
     * This error occurs when a property value violates a constraint or validation rule.
     * The returned OcppErrorCode object contains the name and message for the error.
     *
     * @return The OcppErrorCode object representing a property constraint violation error.
     */
    fun getPropertyConstraintViolationError(): OcppErrorCode

    /**
     * Retrieves an OcppErrorCode representing a not implemented error.
     * This error occurs when a requested feature or action is not implemented.
     *
     * @return The OcppErrorCode object representing a not implemented error.
     */
    fun getNotImplementedError(): OcppErrorCode
}
