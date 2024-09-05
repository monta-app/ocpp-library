package com.monta.library.ocpp.v16.error

import com.monta.library.ocpp.common.error.OcppErrorCode

enum class MessageErrorCodeV16(
    private val message: String
) : OcppErrorCode {
    NotImplemented("Requested Action is not known by receiver"),
    NotSupported("Requested Action is recognized but not supported by the receiver"),
    InternalError("An internal error occurred and the receiver was not able to process the requested Action successfully"),
    ProtocolError("Payload for Action is incomplete"),
    SecurityError("During the processing of Action a security issue occurred preventing receiver from completing the Action successfully"),
    FormationViolation("Payload for Action is syntactically incorrect or not conform the PDU structure for Action"),
    PropertyConstraintViolation("Payload is syntactically correct but at least one field contains an invalid value"),
    OccurrenceConstraintViolation("Payload for Action is syntactically correct but at least one of the fields violates occurrence constraints"),
    TypeConstraintViolation("Payload for Action is syntactically correct but at least one of the fields violates data type constraints (e.g. 'somestring': 12)"),
    GenericError("Any other error not covered by the previous ones");

    override fun getName(): String {
        return name
    }

    override fun getMessage(): String {
        return message
    }
}
