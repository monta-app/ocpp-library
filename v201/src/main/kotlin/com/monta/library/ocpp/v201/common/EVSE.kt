package com.monta.library.ocpp.v201.common

data class EVSE(
    /** Identified_ Object. MRID. Numeric_ Identifier
     urn:x-enexis:ecdm:uid:1:569198
     EVSE Identifier. This contains a number (> 0) designating an EVSE of the Charging Station. */
    val id: Long,
    /** An id to designate a specific connector (on an EVSE) by connector index number. */
    val connectorId: Long? = null,
    // Stuff
    val customData: CustomData? = null
)
