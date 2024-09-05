package com.monta.library.ocpp.v201.error

import com.monta.library.ocpp.common.error.OcppErrorCode
import com.monta.library.ocpp.common.error.OcppErrorResponder

object OcppErrorResponderV201 : OcppErrorResponder {
    override fun getInternalError(): OcppErrorCode {
        return MessageErrorCodeV201.InternalError
    }

    override fun getJsonFormatError(): OcppErrorCode {
        return MessageErrorCodeV201.FormatViolation
    }

    override fun getPropertyConstraintViolationError(): OcppErrorCode {
        return MessageErrorCodeV201.PropertyConstraintViolation
    }

    override fun getNotImplementedError(): OcppErrorCode {
        return MessageErrorCodeV201.NotImplemented
    }
}
