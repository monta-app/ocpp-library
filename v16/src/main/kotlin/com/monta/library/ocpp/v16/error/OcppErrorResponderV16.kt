package com.monta.library.ocpp.v16.error

import com.monta.library.ocpp.common.error.OcppErrorCode
import com.monta.library.ocpp.common.error.OcppErrorResponder

object OcppErrorResponderV16 : OcppErrorResponder {
    override fun getInternalError(): OcppErrorCode {
        return MessageErrorCodeV16.InternalError
    }

    override fun getJsonFormatError(): OcppErrorCode {
        return MessageErrorCodeV16.FormationViolation
    }

    override fun getPropertyConstraintViolationError(): OcppErrorCode {
        return MessageErrorCodeV16.PropertyConstraintViolation
    }

    override fun getNotImplementedError(): OcppErrorCode {
        return MessageErrorCodeV16.NotImplemented
    }
}
